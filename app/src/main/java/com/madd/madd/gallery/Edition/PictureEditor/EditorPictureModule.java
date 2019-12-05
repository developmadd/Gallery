package com.madd.madd.gallery.Edition.PictureEditor;


import dagger.Module;
import dagger.Provides;

@Module
public class EditorPictureModule {

    @Provides
    EditorPictureContract.Presenter provideEditorPicturePresent(){
        return new EditorPicturePresenter();
    }



}
