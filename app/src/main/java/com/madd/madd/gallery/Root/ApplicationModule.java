package com.madd.madd.gallery.Root;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;
    public ApplicationModule( Application application ){
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return application;
    }

    @Singleton
    @Provides
    public ContentResolver getContentResolver(Context context){
        return context.getContentResolver();
    }
}
