package com.funckyhacker.fileexplorer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.funckyhacker.fileexplorer.util.FileUtils;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class MainViewModelImpl extends MainViewModel {

  private MainView view;
  private MainAdapter adapter;
  private String currentPath;
  private PageManger pageManger;
  private boolean isNoFiles;

  @Inject
  public MainViewModelImpl(PageManger pageManger) {
    this.pageManger = pageManger;
  }

  @Override public void init(MainView view) {
    this.view = view;
    adapter = new MainAdapter();
    view.setAdapter(adapter);
  }

  @Override public void setData(@Nullable List<File> files) {
    setNofiles(files == null || files.isEmpty());
    adapter.setData(files);
  }

  @Override public String getCurrentPath() {
    return currentPath;
  }

  @Override public int getPageSize() {
    return pageManger.size();
  }

  @Override public void setCurrentPath(String currentPath) {
    this.currentPath = currentPath;
    notifyPropertyChanged(BR.currentPath);
  }

  @Override public void setFilesToList(@NonNull String name) {
    File file = FileUtils.getFilesFromName(name);
    if (file == null) {
      return;
    }
    pageManger.push(getCurrentPath());
    setCurrentPath(file.getAbsolutePath());
    setData(Arrays.asList(file.listFiles()));
  }

  @Override public void setFilesToList(@NonNull File file) {
    pageManger.push(getCurrentPath());
    setCurrentPath(file.getAbsolutePath());
    setData(Arrays.asList(file.listFiles()));
  }

  @Override public void popItem() {
    setCurrentPath(pageManger.pop());
    File file = new File(getCurrentPath());
    setData(Arrays.asList(file.listFiles()));
  }

  @Override public boolean isNoFiles() {
    return isNoFiles;
  }

  public void setNofiles(boolean noFiles) {
    isNoFiles = noFiles;
    notifyPropertyChanged(BR.noFiles);
  }
}
