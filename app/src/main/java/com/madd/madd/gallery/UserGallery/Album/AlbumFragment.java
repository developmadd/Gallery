package com.madd.madd.gallery.UserGallery.Album;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madd.madd.gallery.R;
import com.madd.madd.gallery.DI.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements AlbumContract.View {

    @Inject AlbumContract.Presenter presenter;

    @BindView(R.id.CTNR_Album) RecyclerView recyclerView;
    @BindView(R.id.PB_Album) ProgressBar progressBar;
    @BindView(R.id.TV_Album_Message) TextView textViewMessage;
    @BindView(R.id.BTN_Album_Accept) Button buttonAccept;

    private PictureAdapter adapter;

    public AlbumFragment() { }

    private String albumName;
    private List<String> albumPictureList = new ArrayList<>();
    private List<String> selectedPictureList = new ArrayList<>();
    private boolean multipleSelection = false;
    private AlbumPresenter.ReturnSelectedPictureList returnSelectedPictureList;



    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public void setSelectedPictureList(List<String> selectedPictureList) {
        this.selectedPictureList.addAll(selectedPictureList);
    }
    public void setMultipleSelection(boolean multipleSelection) {
        this.multipleSelection = multipleSelection;
    }
    public void getSelectedPictureList(AlbumPresenter.ReturnSelectedPictureList returnSelectedPictureList) {
        this.returnSelectedPictureList = returnSelectedPictureList;
    }











    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this,v);
        ((App) Objects.requireNonNull(getActivity()).getApplication()).getComponent().inject(this);

        loadView();


        presenter.setView(this);
        presenter.requestPictureList(albumName);
        return v;
    }







    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    private void loadView(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new PictureAdapter(albumPictureList, selectedPictureList ,
                (path, position) -> presenter.selectPicture(path,position)
        );
        recyclerView.setAdapter(adapter);


        buttonAccept.setOnClickListener(view -> {
            presenter.returnSelectedPictureList();
            getFragmentManager().popBackStack();
        });

    }


























    @Override
    public void showPictureList(List<String> selectedImageList, List<String> pictureList) {
        albumPictureList.addAll(pictureList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyListError() {
        textViewMessage.setText("No se han podido cargar las fotografías de este album");
        textViewMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshPictureAtPosition(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void showSelectedPictureCounter(int counter) {
        buttonAccept.setText("Ok(" + counter + ")");
    }

    @Override
    public void returnSelectedPictureList(List<String> selectedPictureList){
        returnSelectedPictureList.selectedPictureList(selectedPictureList);
    }




    @Override
    public List<String> getSelectedPictureList() {
        return selectedPictureList;
    }

    @Override
    public List<String> getAlbumPictureList() {
        return albumPictureList;
    }

    @Override
    public boolean getMultipleSelection() {
        return multipleSelection;
    }


}
