package com.madd.madd.gallery.UI.Fragments.PictureEditor;

import android.graphics.Bitmap;

import com.fenchtose.nocropper.CropResult;

public interface EditorPictureContract {

    interface View {

        void loadPictureBitmap(Bitmap bitmap);

        void requestWritePermission();
        void showLoadProgress();
        void hideLoadProgress();
        void showPermissionError();
        void showEditionError();

        void returnSelectedPictureList(String editedPath);

        String getPicturePath();
        CropResult getCropResult();
        Bitmap getBitmap();
        boolean areWritePermissionGranted();
    }

    interface Presenter {

        void setView(EditorPictureContract.View view);

        void setWritePermissions(boolean grantedPermissions);
        void loadPicture();

        void deletePicture();
        void rotatePicture();
        void finishEdition();
    }

    interface Model {

    }

}
