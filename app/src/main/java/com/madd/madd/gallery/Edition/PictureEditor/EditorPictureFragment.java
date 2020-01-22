package com.madd.madd.gallery.Edition.PictureEditor;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fenchtose.nocropper.CropResult;
import com.fenchtose.nocropper.CropperView;
import com.madd.madd.gallery.R;
import com.madd.madd.gallery.DI.App;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditorPictureFragment extends Fragment implements EditorPictureContract.View {

    @Inject EditorPictureContract.Presenter presenter;

    @BindView(R.id.CRV_Editor_Picture ) CropperView imageView;
    @BindView(R.id.BTN_Editor_Rotate )  ImageButton buttonRotate;
    @BindView(R.id.BTN_Editor_Delete)   ImageButton buttonDelete;
    @BindView(R.id.BTN_Editor_Edit )    Button buttonEdit;
    @BindView(R.id.PB_Editor )          ProgressBar progressBar;


    private String picturePath;
    private EditorPicturePresenter.GetEditedPath getEditedPath;
    private Bitmap bitmap;

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    public void setGetEditedPath(EditorPicturePresenter.GetEditedPath getEditedPath) {
        this.getEditedPath = getEditedPath;
    }

    public EditorPictureFragment( ) { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_editor_picture, container, false);
        ButterKnife.bind(this,v);
        ((App) Objects.requireNonNull(getActivity()).getApplication()).getComponent().inject(this);

        loadView();


        presenter.setView(this);
        presenter.loadPicture();
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    private void loadView(){

        imageView.setGestureEnabled(true);
        imageView.setDebug(true);
        imageView.setGridCallback(new CropperView.GridCallback() {
            @Override
            public boolean onGestureStarted() {
                return true;
            }

            @Override
            public boolean onGestureCompleted() {
                return false;
            }
        });
        imageView.setMaxZoom(imageView.getWidth() * 2 / 1280f);

        buttonDelete.setOnClickListener( view -> {
            presenter.deletePicture();
        });

        buttonRotate.setOnClickListener( view -> {
            presenter.rotatePicture();
        });

        buttonEdit.setOnClickListener( view -> {
            presenter.finishEdition();
        });

    }











    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean grantedPermissions = (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        presenter.setWritePermissions(grantedPermissions);
    }



    @Override
    public void loadPictureBitmap(Bitmap pBitmap) {
        bitmap = pBitmap;
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void requestWritePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
    }

    @Override
    public void showLoadProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showPermissionError() {
        Toast.makeText(getContext(),"Sin permisos", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEditionError() {
        Toast.makeText(getContext(),"Error editando imagen", Toast.LENGTH_LONG).show();
    }


    @Override
    public void returnSelectedPictureList(String editedPath) {
        getEditedPath.editedPath(editedPath);
        getFragmentManager().popBackStack();
    }








    @Override
    public String getPicturePath() {
        return picturePath;
    }


    @Override
    public CropResult getCropResult() {
        return imageView.getCropInfo();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean areWritePermissionGranted() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


}
