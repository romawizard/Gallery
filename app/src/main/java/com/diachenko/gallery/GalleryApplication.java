package com.diachenko.gallery;

import android.app.Activity;
import android.app.Application;

import com.diachenko.gallery.di.components.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class GalleryApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    private void initDagger() {
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }
}
