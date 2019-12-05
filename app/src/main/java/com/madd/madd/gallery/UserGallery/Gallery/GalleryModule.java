package com.madd.madd.gallery.UserGallery.Gallery;

import android.content.ContentResolver;
import android.content.Context;

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
