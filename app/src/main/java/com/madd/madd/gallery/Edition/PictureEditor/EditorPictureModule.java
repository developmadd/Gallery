package com.madd.madd.gallery.Edition.PictureEditor;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class EditorPictureModule {

    @Provides
    EditorPictureContract.Presenter provideEditorPicturePresent(Context context){
        return new EditorPicturePresenter(context);
    }



}
