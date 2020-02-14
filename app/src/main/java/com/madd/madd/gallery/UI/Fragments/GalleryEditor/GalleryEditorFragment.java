package com.madd.madd.gallery.UI.Fragments.GalleryEditor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import com.madd.madd.gallery.UI.Fragments.PictureEditor.EditorPictureFragment;
import com.madd.madd.gallery.R;
import com.madd.madd.gallery.DI.App;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryEditorFragment extends Fragment implements GalleryEditorContract.View {


    @Inject GalleryEditorContract.Presenter presenter;

    @BindView(R.id.CTNR_Editor_Picture_List )  RecyclerView recyclerView;
    @BindView(R.id.IV_Editor )  ImageView imageView;
    @BindView(R.id.BTN_Editor )  ImageButton buttonEdition;
    @BindView(R.id.BTN_Editor_Accept ) Button buttonAccept;



    private PhotoAdapter adapter;

    private List<String> pictureList;
    private GalleryEditorPresenter.ReturnSelectedPictureList returnSelectedPictureList;
    private int selectedPicture = 0;

    public void setPicturePathList(List<String> picturePathList) {
        this.pictureList = picturePathList;
    }
    public void getSelectedPictureList(GalleryEditorPresenter.ReturnSelectedPictureList returnSelectedPictureList) {
        this.returnSelectedPictureList = returnSelectedPictureList;
    }

    public GalleryEditorFragment() { }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_editor, container, false);
        ButterKnife.bind(this,v);
        ((App) Objects.requireNonNull(getActivity()).getApplication()).getComponent().inject(this);

        loadView();

        return v;
    }




    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.showPictureList();
    }

    private void loadView(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new PhotoAdapter(pictureList, new PhotoAdapter.AdapterEvents() {
            @Override
            public void onPictureDrag(int fromPosition, int toPosition) {
                presenter.dragPicture(fromPosition,toPosition);
            }

            @Override
            public void onPictureClick(int position) {
                presenter.selectPicture(position);
            }
        });
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PhotoItemTouchAdapter(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setItemTouchHelper(itemTouchHelper);

        buttonEdition.setOnClickListener(view -> {
            presenter.openPictureEditor();
        });

        buttonAccept.setOnClickListener( view -> {
            presenter.returnSelectedPictureList();
        });

    }








    @Override
    public void showEditionButton() {
        buttonEdition.setVisibility(View.VISIBLE);
    }


    @Override
    public void loadPicture(String path) {
        Glide.with(imageView).load(path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop().into( imageView );
    }

    @Override
    public void editPicture(String path) {
        EditorPictureFragment editorPicture = new EditorPictureFragment();
        editorPicture.setPicturePath(path);
        editorPicture.setGetEditedPath(editedPath -> {
            presenter.editPicture(editedPath);
        });
        getFragmentManager().beginTransaction().replace(R.id.CTNR_Main,editorPicture).addToBackStack(null).commit();
    }


    @Override
    public void returnSelectedPictureList() {
        returnSelectedPictureList.selectedPictureList(pictureList);
    }



    @Override
    public void refreshPictures(int fromPosition, int toPosition) {
        adapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void refreshPicture(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void refreshPictures() {
        adapter.notifyDataSetChanged();
    }





    @Override
    public List<String> getPictureList() {
        return pictureList;
    }

    @Override
    public int getSelectedPicture() {
        return selectedPicture;
    }

    @Override
    public void setSelectedPicture(int pSelectedPicture) {
        selectedPicture = pSelectedPicture;
    }

}
