package com.madd.madd.gallery.UI.Fragments.Gallery;

import android.content.ContentResolver;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryModule {

    @Provides
    GalleryContract.Presenter provideGalleryPresenter(GalleryContract.Model model){
        return new GalleryPresenter(model);
    }

    @Provides
    GalleryContract.Model provideGalleryModel(ContentResolver contentResolver){
        return new GalleryModel(contentResolver);
    }


}
