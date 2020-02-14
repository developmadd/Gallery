package com.madd.madd.gallery;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import com.madd.madd.gallery.UI.Activities.EditorActivity;
import com.madd.madd.gallery.UI.Activities.GalleryActivity;
import com.madd.madd.gallery.DI.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.BTN_Open_Editor)  Button buttonEditor;
    @BindView(R.id.BTN_Open_Gallery)  Button buttonGallery;
    @BindView(R.id.CB_Gallery_Multiple_Selection)  CheckBox checkBox;


    private List<String> picturePathList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App)getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);



        buttonEditor.setOnClickListener(view -> {
            if( !picturePathList.isEmpty() ) {
                openEditor();
            } else {
                Toast.makeText(this,"Selecciona fotografias desde la galería",Toast.LENGTH_LONG).show();
            }
        });

        buttonGallery.setOnClickListener(view -> {
            openGallery();
        });

    }


    private void openGallery(){

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("multiple_selection",checkBox.isChecked());
        startActivityForResult(intent,1);

    }

    private void openEditor(){

        Intent intent = new Intent(this, EditorActivity.class);
        intent.putStringArrayListExtra("path_list",(ArrayList<String>)picturePathList);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

            if (data != null) {
                picturePathList = data.getStringArrayListExtra("path_list");
                Toast.makeText(this, "Lista actualizada", Toast.LENGTH_LONG).show();
            }

    }


}
