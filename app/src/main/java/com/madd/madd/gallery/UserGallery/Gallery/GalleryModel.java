package com.madd.madd.gallery.UserGallery.Gallery;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryModel implements GalleryContract.Model {


    private ContentResolver contentResolver;

    public GalleryModel(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public void getAlbumList(AlbumListRequest albumListRequest) {
        List<Album> albumList = new ArrayList<>();

        List<String> bucketsLabel = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA
        };
        String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Cursor cursor = contentResolver.query(
                uri,
                projection,
                null,
                null,
                orderBy);
        if(cursor != null){
            File file;
            while (cursor.moveToNext()){
                String bucketPath = cursor.getString(cursor.getColumnIndex(projection[0]));
                String firstImage = cursor.getString(cursor.getColumnIndex(projection[1]));
                file = new File(firstImage);
                if (file.exists() && !bucketsLabel.contains(bucketPath)) {
                    bucketsLabel.add(bucketPath);
                    albumList.add(new Album(bucketPath, firstImage));
                }
            }
            cursor.close();
        }
        if( !albumList.isEmpty() ) {
            albumListRequest.onSuccess(albumList);
        } else {
            albumListRequest.onError("Sin albums");
        }
    }


}
