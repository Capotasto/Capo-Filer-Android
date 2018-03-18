package com.funckyhacker.fileexplorer;

import javax.inject.Inject;

public class MainViewModelImpl extends MainViewModel {

  private MainView view;

  @Inject
  public MainViewModelImpl() {
  }

  @Override public void init(MainView view) {
    this.view = view;
  }
}
