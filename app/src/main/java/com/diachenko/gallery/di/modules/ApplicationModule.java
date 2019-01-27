package com.diachenko.gallery.di.modules;

import android.app.Application;
import android.content.Context;

import com.diachenko.gallery.GalleryApplication;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;

@Module(includes = AndroidInjectionModule.class)
public  abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract Application application(GalleryApplication app);
}
