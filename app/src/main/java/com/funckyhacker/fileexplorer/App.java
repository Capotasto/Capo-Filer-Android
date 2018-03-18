package com.funckyhacker.fileexplorer;

import com.facebook.stetho.Stetho;
import com.funckyhacker.fileexplorer.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {

  @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.builder().create(this);
  }

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      // Timber
      Timber.plant(new Timber.DebugTree());
      // stetho
      Stetho.initializeWithDefaults(this);
    }
  }
}
