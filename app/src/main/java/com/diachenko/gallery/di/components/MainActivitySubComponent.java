package com.diachenko.gallery.di.components;

import com.diachenko.gallery.di.ActivityScope;
import com.diachenko.gallery.di.modules.MainActivityModule;
import com.diachenko.gallery.ui.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivitySubComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<MainActivity>{}
}
