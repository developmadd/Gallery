package com.madd.madd.gallery.UserGallery.Gallery;



import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.List;


public class GalleryPresenter implements GalleryContract.Presenter {

    private GalleryContract.View view;
    private GalleryContract.Model model;



    GalleryPresenter(GalleryContract.Model model) {
        this.model = model;
    }

    @Override
    public void setView(GalleryContract.View view) {
        this.view = view;
    }




    @Override
    public void requestAlbumList() {

        if( view != null ) {
            if ( !view.areReadPermissionGranted() ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.requestReadPermission();
                }
                view.showPermissionError();
            } else {
                view.hidePermissionError();
                view.showLoadingProgress();
                getAlbumList();
            }
        }
    }

    @Override
    public void setReadPermissions(boolean grantedPermissions) {
        if( grantedPermissions ){
            view.hidePermissionError();
            view.showLoadingProgress();
            getAlbumList();
        } else {
            view.showPermissionError();
        }
    }


    private void getAlbumList() {
        model.getAlbumList(new GalleryContract.Model.AlbumListRequest() {
            @Override
            public void onSuccess(List<Album> albumList) {
                if( view != null ){
                    view.showAlbumList(albumList);
                    view.hideLoadingProgress();
                }
            }

            @Override
            public void onError(String error) {
                if( view != null ){
                    view.showEmptyListError();
                    view.hideLoadingProgress();
                }
            }
        });
    }


    @Override
    public void openAlbum(String albumName) {
        if( view != null ) {
            view.openAlbum(view.getSelectedPictureList(),view.getMultipleSelection(),albumName);
        }
    }

    @Override
    public void updateSelectedPictureList(List<String> updatedSelectedPictureList) {
        if( view != null ) {
            List<String> selectedPictureList = view.getSelectedPictureList();
            selectedPictureList.clear();
            selectedPictureList.addAll(updatedSelectedPictureList);
            view.showSelectedPictureCounter(selectedPictureList.size());
        }
    }

    @Override
    public void returnSelectedPictureList() {
        if( view != null ) {
            view.returnSelectedPictureList(view.getSelectedPictureList());
        }
    }


}
