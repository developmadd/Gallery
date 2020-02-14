package com.madd.madd.gallery.DI;


import com.madd.madd.gallery.UI.Fragments.GalleryEditor.GalleryEditorFragment;
import com.madd.madd.gallery.UI.Fragments.GalleryEditor.GalleryEditorModule;
import com.madd.madd.gallery.UI.Fragments.PictureEditor.EditorPictureFragment;
import com.madd.madd.gallery.UI.Fragments.PictureEditor.EditorPictureModule;
import com.madd.madd.gallery.MainActivity;
import com.madd.madd.gallery.UI.Fragments.Album.AlbumFragment;
import com.madd.madd.gallery.UI.Fragments.Album.AlbumModule;
import com.madd.madd.gallery.UI.Fragments.Gallery.GalleryFragment;
import com.madd.madd.gallery.UI.Fragments.Gallery.GalleryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {  ApplicationModule.class,
                        GalleryModule.class,
                        AlbumModule.class,
                        GalleryEditorModule.class,
                        EditorPictureModule.class} )
public interface ApplicationComponent {

    void inject(MainActivity target);
    void inject(GalleryFragment target);
    void inject(AlbumFragment target);
    void inject(GalleryEditorFragment target);
    void inject(EditorPictureFragment target);

}
