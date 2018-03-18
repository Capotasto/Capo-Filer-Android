package com.funckyhacker.fileexplorer.di;

import android.app.Activity;
import com.funckyhacker.fileexplorer.MainActivity;
import com.funckyhacker.fileexplorer.MainViewModel;
import com.funckyhacker.fileexplorer.MainViewModelImpl;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainModule  {
  @Binds @IntoMap @ActivityKey(MainActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindInjectorFactory(
      MainComponent.Builder builder);

  @Binds abstract MainViewModel bindMainViewModel(MainViewModelImpl viewModel);

}
