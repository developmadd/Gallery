package com.madd.madd.gallery.Root;

import android.app.Application;

import com.madd.madd.gallery.Edition.Editor.EditorModule;
import com.madd.madd.gallery.Edition.PictureEditor.EditorPictureModule;
import com.madd.madd.gallery.UserGallery.Album.AlbumModule;
import com.madd.madd.gallery.UserGallery.Gallery.GalleryModule;


public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .galleryModule(new GalleryModule())
                .albumModule(new AlbumModule())
                .editorModule(new EditorModule())
                .editorPictureModule(new EditorPictureModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
