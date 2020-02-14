package com.madd.madd.gallery.UI.Fragments.Album;



import java.util.List;

public interface AlbumContract {

    interface View {
        void showPictureList(List<String> selectedImageList, List<String> pictureList);
        void showEmptyListError();
        void showLoadingProgress();
        void hideLoadingProgress();
        void refreshPictureAtPosition(int position);
        void showSelectedPictureCounter(int counter);
        void returnSelectedPictureList(List<String> selectedPictureList);

        List<String> getSelectedPictureList();
        List<String> getAlbumPictureList();
        boolean getMultipleSelection();
    }

    interface Presenter {
        void setView(AlbumContract.View view);

        void requestPictureList(String albumName);
        void selectPicture(String path,int position);
        void returnSelectedPictureList();


    }

    interface Model {
        void getPictureList(String albumName, PictureListRequest pictureListRequest);
        interface PictureListRequest{
            void onSuccess(List<String> pictureList);
            void onError(String error);
        }
    }

}
