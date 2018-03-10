package com.funckyhacker.fileexplorer.di;

import com.funckyhacker.fileexplorer.App;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = { AndroidSupportInjectionModule.class, AppModule.class, MainModule.class})
public interface AppComponent extends AndroidInjector<App> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<App> {
  }
}
