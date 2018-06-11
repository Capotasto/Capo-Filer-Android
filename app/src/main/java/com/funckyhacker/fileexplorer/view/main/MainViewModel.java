package com.funckyhacker.fileexplorer.view.main;

import android.content.ContentResolver;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.funckyhacker.fileexplorer.view.adapter.MainGridAdapter;
import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter;
import java.io.File;
import java.util.List;

public abstract class MainViewModel extends BaseObservable {

  public abstract void init(MainView view);

  public abstract void setData(List<File> files);

  @Bindable public abstract String getCurrentPath();

  public abstract int getPageSize();

  public abstract void setCurrentPath(String currentPath);

  public abstract void setFilesToList(@NonNull String name);

  public abstract void setFilesToList(@NonNull File file);

  public abstract void popItem();

  @Bindable public abstract boolean isNoFiles();

  public abstract void sendIntent(ContentResolver resolver, File file, Uri uri);

  public abstract int getLayoutType();

  public abstract void setLayoutType(int type);

  public abstract boolean isSearchMode();

  public abstract void setSearchMode(boolean isSearchMode);

  public abstract MainLinearAdapter getLinearAdapter();

  public abstract MainGridAdapter getGridAdapter();

}
