package com.madd.madd.gallery.UI.Fragments.Gallery;

import java.util.List;

public interface GalleryContract {

    interface View {

        void showPermissionError();
        void hidePermissionError();

        void requestReadPermission();
        void showAlbumList(List<Album> albumList);
        void showEmptyListError();

        void showLoadingProgress();
        void hideLoadingProgress();

        void showSelectedPictureCounter(int counter);
        void openAlbum(List<String> selectedPictureList, boolean multipleSelection, String albumName);
        void returnSelectedPictureList(List<String> selectedPictureList);

        List<String> getSelectedPictureList();
        boolean getMultipleSelection();
        boolean areReadPermissionGranted();
    }

    interface Presenter {
        void setView(GalleryContract.View view);

        void requestAlbumList();
        void setReadPermissions(boolean grantedPermissions);

        void openAlbum(String albumName);
        void updateSelectedPictureList(List<String> updatedSelectedPictureList);
        void returnSelectedPictureList();
    }

    interface Model {
        void getAlbumList(AlbumListRequest albumListRequest);

        interface AlbumListRequest{
            void onSuccess(List<Album> albumList);
            void onError(String error);
        }
    }

}
