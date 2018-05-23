package com.funckyhacker.fileexplorer;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
    setNoFiles(files == null || files.isEmpty());
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

  @Override public void sendIntent(ContentResolver resolver, File file) {
    String mimeType = FileUtils.getMimeType(resolver, file);
    if (TextUtils.isEmpty(mimeType)) {
      view.showSnackBar("Couldn't show the preview: " + file.getName());
      return;
    }
    Intent pictureActionIntent = new Intent();
    pictureActionIntent.setAction(Intent.ACTION_VIEW);
    pictureActionIntent.setDataAndType(Uri.fromFile(file), mimeType);
    view.startActivity(pictureActionIntent);
  }

  public void setNoFiles(boolean noFiles) {
    isNoFiles = noFiles;
    notifyPropertyChanged(BR.noFiles);
  }
}
