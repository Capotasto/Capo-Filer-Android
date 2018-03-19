package com.funckyhacker.fileexplorer;

import java.io.File;
import java.util.List;
import javax.inject.Inject;

public class MainViewModelImpl extends MainViewModel {

  private MainView view;
  private MainAdapter adapter;

  @Inject
  public MainViewModelImpl() {
  }

  @Override public void init(MainView view) {
    this.view = view;
    adapter = new MainAdapter();
    view.setAdapter(adapter);
  }

  @Override public void setData(List<File> files) {
    adapter.setData(files);
  }
}
