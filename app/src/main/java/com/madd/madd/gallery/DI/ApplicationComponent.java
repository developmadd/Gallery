package com.madd.madd.gallery.DI;


import com.madd.madd.gallery.Edition.Editor.EditorFragment;
import com.madd.madd.gallery.Edition.Editor.EditorModule;
import com.madd.madd.gallery.Edition.PictureEditor.EditorPictureFragment;
import com.madd.madd.gallery.Edition.PictureEditor.EditorPictureModule;
import com.madd.madd.gallery.MainActivity;
import com.madd.madd.gallery.UserGallery.Album.AlbumFragment;
import com.madd.madd.gallery.UserGallery.Album.AlbumModule;
import com.madd.madd.gallery.UserGallery.Gallery.GalleryFragment;
import com.madd.madd.gallery.UserGallery.Gallery.GalleryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {  ApplicationModule.class,
                        GalleryModule.class,
                        AlbumModule.class,
                        EditorModule.class,
                        EditorPictureModule.class} )
public interface ApplicationComponent {

    void inject(MainActivity target);
    void inject(GalleryFragment target);
    void inject(AlbumFragment target);
    void inject(EditorFragment target);
    void inject(EditorPictureFragment target);

}
