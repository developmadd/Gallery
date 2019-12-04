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

    private Context context;


    GalleryPresenter(GalleryContract.Model model,
                     Context context) {
        this.model = model;
        this.context = context;
    }

    @Override
    public void setView(GalleryContract.View view) {
        this.view = view;
    }



    @Override
    public void onPermissionsResult(int[] grantResults) {
        if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            getAlbumList();
        } else {
            view.showPermissionError();
        }
    }

    @Override
    public void requestAlbumList() {

        if( view != null ) {
            int permissionCheckRead = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.getViewFragment().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
                }
            } else {
                view.hidePermissionError();
                view.showLoadingProgress();
                getAlbumList();
            }
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
        if( view != null ){
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
