package com.madd.madd.gallery.Edition.Editor;

import java.util.Collections;
import java.util.List;

public class EditorPresenter implements EditorContract.Presenter {

    private EditorContract.View view;


    EditorPresenter( ) {

    }

    @Override
    public void setView(EditorContract.View view) {
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
    public void selectPicture(int position) {
        if( view != null ) {
            List<String> pictureList = view.getPictureList();
            view.setSelectedPicture(position);
            if (!pictureList.isEmpty()) {
                view.loadPicture(pictureList.get(position));
                view.showEditionButton();
            } else {
                view.returnSelectedPictureList();
            }
        }
    }

    @Override
    public void editPicture(String editedPath) {
        if(editedPath.isEmpty()){
            deletePicture();
        } else {
            refreshPicture(editedPath);
        }
    }


    private void deletePicture() {
        int selectedPicture = view.getSelectedPicture();
        view.getPictureList().remove(selectedPicture);
        showPictureList();
    }

    private void refreshPicture(String path) {
        int selectedPicture = view.getSelectedPicture();
        view.getPictureList().set(selectedPicture, path);
        view.refreshPicture(selectedPicture);
    }


    @Override
    public void dragPicture(int fromPosition, int toPosition) {
        List<String> imageList = view.getPictureList();
        if (toPosition < imageList.size()){
            Collections.swap(imageList, fromPosition, toPosition);
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
