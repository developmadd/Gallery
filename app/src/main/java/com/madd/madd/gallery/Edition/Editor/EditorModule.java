package com.madd.madd.gallery.Edition.Editor;


import dagger.Module;
import dagger.Provides;

@Module
public class EditorModule {

    @Provides
    public EditorContract.Presenter providePresenter(){
        return new EditorPresenter();
    }

}
