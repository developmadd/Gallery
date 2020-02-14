package com.madd.madd.gallery.UI.Fragments.GalleryEditor;


import dagger.Module;
import dagger.Provides;

@Module
public class GalleryEditorModule {

    @Provides
    public GalleryEditorContract.Presenter providePresenter(){
        return new GalleryEditorPresenter();
    }

}
