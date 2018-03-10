package com.funckyhacker.fileexplorer.di;

import com.funckyhacker.fileexplorer.App;
import dagger.Component;
import dagger.android.AndroidInjector;

@Component
public interface AppComponent extends AndroidInjector<App> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<App> {
  }
}
