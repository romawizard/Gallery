package com.diachenko.gallery.di.components;

import com.diachenko.gallery.GalleryApplication;
import com.diachenko.gallery.di.modules.ActivityBuilderModule;
import com.diachenko.gallery.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        ActivityBuilderModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {


    void inject(GalleryApplication application);


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(GalleryApplication application);

        ApplicationComponent build();
    }
}
