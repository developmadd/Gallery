package com.madd.madd.gallery.UI.Fragments.PictureEditor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;

import com.fenchtose.nocropper.CropInfo;
import com.fenchtose.nocropper.CropResult;
import com.fenchtose.nocropper.ScaledCropper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class EditorPicturePresenter implements EditorPictureContract.Presenter {

    private EditorPictureContract.View view;

    private int rotationCount = 0;
    private Bitmap originalBitmap;

    public EditorPicturePresenter( ) {

    }

    @Override
    public void setView(EditorPictureContract.View view) {
        this.view = view;
    }




    @Override
    public void finishEdition() {
        if( view != null ) {

            if ( !view.areWritePermissionGranted() ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.requestWritePermission();
                }
            } else {
                executePictureEdition();
            }

        }
    }


    @Override
    public void setWritePermissions(boolean grantedPermissions) {
        if ( grantedPermissions ) {
            executePictureEdition();
        } else {
            view.showPermissionError();
        }
    }


    @Override
    public void loadPicture() {

        view.showLoadProgress();

        rotationCount = 0;
        Bitmap bitmap = BitmapFactory.decodeFile(view.getPicturePath());
        bitmap = fixBitmapOrientation(view.getPicturePath(),bitmap);
        originalBitmap = bitmap;

        int maxP = Math.max(bitmap.getWidth(), bitmap.getHeight());
        float scale1280 = (float)maxP / 1280;
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int)(bitmap.getWidth()/scale1280),
                (int)(bitmap.getHeight()/scale1280), true);
        view.loadPictureBitmap(bitmap);

        view.hideLoadProgress();
    }

    @Override
    public void deletePicture() {
        view.returnSelectedPictureList("");
    }

    @Override
    public void rotatePicture() {
        Bitmap bitmap = view.getBitmap();
        bitmap = rotateBitmap(bitmap, 90);
        view.loadPictureBitmap(bitmap);
        rotationCount++;
    }









    private ScaledCropper prepareCropForImage() {
        CropResult cropResult = view.getCropResult();
        if (cropResult.getCropInfo() == null) {
            return null;
        }

        Bitmap bitmap = view.getBitmap();
        float scale;
        if (rotationCount % 2 == 0) {
            scale = (float) originalBitmap.getWidth()/bitmap.getWidth();
        } else {
            scale = (float) originalBitmap.getWidth()/bitmap.getHeight();
        }

        CropInfo cropInfo = cropResult.getCropInfo().rotate90XTimes(bitmap.getWidth(), bitmap.getHeight(), rotationCount);
        return new ScaledCropper(cropInfo, originalBitmap, scale);
    }

    private void executePictureEdition() {
        ScaledCropper cropper = prepareCropForImage();
        if (cropper == null) {
            return;
        }

        Bitmap bitmap = cropper.cropBitmap();
        if ( rotationCount % 4 != 0 ){
            bitmap = rotateBitmap(bitmap, 90 * rotationCount);
        }
        if ( bitmap != null ) {
            String path = saveBitmapOnLocalStorage(bitmap);
            if( path != null ) {
                view.returnSelectedPictureList(path);
                return;
            }
        }
        view.showEditionError();
    }









    // Bitmap utilities
    private static String saveBitmapOnLocalStorage(Bitmap bitmap) {

        if ( bitmap == null ){
            return null;
        }
        // Get local directory for pictures
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "");
        if(!directory.exists() && !directory.mkdirs() ) {
            return null;
        }
        // Create local path for new picture
        String localPath = directory.getAbsolutePath() + "/" + UUID.randomUUID().toString() + ".jpeg";
        // Create picture in local path
        File newFile = new File(localPath);
        try {
            // Create and compress picture in local path
            int intents = 0,currQuality = 50;
            float bytes;
            do {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, currQuality, stream);
                currQuality /= 2;
                if ( intents++ > 5 ) return null;
                bytes = ((float)stream.toByteArray().length)/1000000;
            } while ( bytes > 1 );

            OutputStream fileOutputStream = new FileOutputStream(newFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, currQuality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            return localPath;
        } catch (IOException error) {
            return null;
        }

    }

    private static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private static Bitmap fixBitmapOrientation(String path, Bitmap bitmap){
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);
        } catch (IOException ignored) {
            // Ignored code block
        }
        if( ei != null ) {
            int orientation = ei.getAttributeInt( ExifInterface.TAG_ORIENTATION , ExifInterface.ORIENTATION_UNDEFINED );
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    break;
            }
        }
        return bitmap;
    }


    public interface GetEditedPath{
        void editedPath(String editedPath);
    }
}
