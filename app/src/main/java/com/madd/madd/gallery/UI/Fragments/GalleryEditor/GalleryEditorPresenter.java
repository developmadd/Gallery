package com.madd.madd.gallery.UI.Fragments.GalleryEditor;

import java.util.Collections;
import java.util.List;

public class GalleryEditorPresenter implements GalleryEditorContract.Presenter {

    private GalleryEditorContract.View view;


    public GalleryEditorPresenter( ) {

    }

    @Override
    public void setView(GalleryEditorContract.View view) {
        this.view = view;
    }

    @Override
    public void showPictureList() {
        if( view != null ){
            view.refreshPictures();
            selectPicture(0);
        }
    }

    @Override
    public void selectPicture(int selectPictureIndex) {
        if( view != null ) {
            List<String> pictureList = view.getPictureList();
            view.setSelectedPicture(selectPictureIndex);
            if (!pictureList.isEmpty()) {
                view.loadPicture(pictureList.get(selectPictureIndex));
                view.showEditionButton();
            } else {
                view.returnSelectedPictureList();
            }
        }
    }

    @Override
    public void editPicture(String editedPath) {
        int selectedPicture = view.getSelectedPicture();

        if(editedPath.isEmpty()){
            view.getPictureList().remove(selectedPicture);
            this.showPictureList();
        } else {
            view.getPictureList().set(selectedPicture, editedPath);
            view.refreshPicture(selectedPicture);
        }
    }




    @Override
    public void dragPicture(int fromPosition, int toPosition) {
        List<String> pictureList = view.getPictureList();
        if (toPosition < pictureList.size()){
            Collections.swap(pictureList, fromPosition, toPosition);
            view.refreshPictures(fromPosition, toPosition);
        }
    }

    @Override
    public void openPictureEditor() {
        String selectedPicturePath = view.getPictureList().get(view.getSelectedPicture());
        view.editPicture(selectedPicturePath);
    }


    @Override
    public void returnSelectedPictureList() {
        view.returnSelectedPictureList();
    }



}
