package com.madd.madd.gallery.DI;

import android.app.Application;

import com.madd.madd.gallery.UI.Fragments.GalleryEditor.GalleryEditorModule;
import com.madd.madd.gallery.UI.Fragments.PictureEditor.EditorPictureModule;
import com.madd.madd.gallery.UI.Fragments.Album.AlbumModule;
import com.madd.madd.gallery.UI.Fragments.Gallery.GalleryModule;


public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .galleryModule(new GalleryModule())
                .albumModule(new AlbumModule())
                .galleryEditorModule(new GalleryEditorModule())
                .editorPictureModule(new EditorPictureModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
