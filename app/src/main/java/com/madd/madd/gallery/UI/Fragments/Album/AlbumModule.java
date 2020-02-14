package com.madd.madd.gallery.UI.Fragments.Album;

import android.content.ContentResolver;

import dagger.Module;
import dagger.Provides;

@Module
public class AlbumModule {

    @Provides
    public AlbumContract.Presenter provideAlbumPresenter(AlbumContract.Model model){
        return new AlbumPresenter(model);
    }

    @Provides
    public AlbumContract.Model provideAlbumModel(ContentResolver contentResolver){
        return new AlbumModel(contentResolver);
    }


}
