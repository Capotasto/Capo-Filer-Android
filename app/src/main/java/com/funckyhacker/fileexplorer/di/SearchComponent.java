package com.funckyhacker.fileexplorer.di;

import com.funckyhacker.fileexplorer.view.search.SearchActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface SearchComponent extends AndroidInjector<SearchActivity>{
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<SearchActivity> {
  }
}
