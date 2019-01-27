package com.diachenko.gallery.di.modules;

import com.diachenko.gallery.di.components.MainActivitySubComponent;
import com.diachenko.gallery.ui.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = MainActivitySubComponent.class)
public abstract class ActivityBuilderModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity.class)
    abstract AndroidInjector.Factory<?>
    bindMainActivityInjectorFactory(MainActivitySubComponent.Builder builder);

}
