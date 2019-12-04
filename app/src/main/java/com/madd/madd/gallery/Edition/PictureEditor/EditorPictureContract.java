package com.madd.madd.gallery.Edition.PictureEditor;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import com.fenchtose.nocropper.CropResult;

import java.util.List;

public interface EditorPictureContract {

    interface View {

        void loadPictureBitmap(Bitmap bitmap);

        void showLoadProgress();
        void hideLoadProgress();
        void showPermissionError();
        void showEditionError();

        void returnSelectedPictureList(String editedPath);

        String getPicturePath();
        Fragment getViewFragment();
        CropResult getCropResult();
        Bitmap getBitmap();
    }

    interface Presenter {

        void setView(EditorPictureContract.View view);

        void onPermissionsResult(int[] grantResults);
        void loadPicture();

        void deletePicture();
        void rotatePicture();
        void finishEdition();
    }

    interface Model {

    }

}
