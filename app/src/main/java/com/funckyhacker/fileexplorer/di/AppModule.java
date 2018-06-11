package com.funckyhacker.fileexplorer.di;

import com.funckyhacker.fileexplorer.util.PageManger;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(subcomponents = {
    MainComponent.class,
    SearchComponent.class
})
public class AppModule {

  @Singleton @Provides
  public PageManger providePageManager() {
    return new PageManger();
  }
}
