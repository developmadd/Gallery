package com.madd.madd.gallery.UserGallery.Album;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.madd.madd.gallery.UserGallery.Album.AlbumContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumModel implements AlbumContract.Model {

    private ContentResolver contentResolver;

    public AlbumModel( ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }


    @Override
    public void getPictureList( String albumName, PictureListRequest pictureListRequest) {
        if( albumName.equals("this_album_exists")){
            List<String> imageList = new ArrayList<>();
            imageList.add("0");
            imageList.add("1");
            imageList.add("2");
            pictureListRequest.onSuccess(imageList);
            return;
        }
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" =?";
        String orderBy = MediaStore.Images.Media.DATE_ADDED+" DESC";

        List<String> imageList = new ArrayList<>();

        if(contentResolver != null) {
            Cursor cursor = contentResolver.query(uri, projection, selection, new String[]{albumName}, orderBy);

            if (cursor != null) {
                File file;
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(projection[0]));
                    file = new File(path);
                    if (file.exists() && !imageList.contains(path)) {
                        imageList.add(path);
                    }
                }
                cursor.close();
            }
        }

        if( !imageList.isEmpty() ) {
            pictureListRequest.onSuccess(imageList);
        } else {
            pictureListRequest.onError("Lista vacia");
        }
    }
}
