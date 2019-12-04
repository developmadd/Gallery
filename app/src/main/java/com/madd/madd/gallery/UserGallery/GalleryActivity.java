package com.madd.madd.gallery.UserGallery;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.madd.madd.gallery.R;
import com.madd.madd.gallery.UserGallery.Gallery.GalleryFragment;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        Intent intent = getIntent();
        boolean multipleSelection = intent.getBooleanExtra("multiple_selection",false);

        GalleryFragment gallery = new GalleryFragment();
        gallery.setMultipleSelection(multipleSelection);
        gallery.getSelectedPictureList(selectedPictureList -> {
            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("path_list", (ArrayList<String>)selectedPictureList);
            setResult(GalleryActivity.RESULT_OK, resultIntent);
            finish();
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.CTNR_Main, gallery).commit();

    }



}
