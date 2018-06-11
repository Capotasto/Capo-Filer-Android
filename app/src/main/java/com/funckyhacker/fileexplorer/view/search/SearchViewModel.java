package com.funckyhacker.fileexplorer.view.search;

import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter;
import java.io.File;
import java.util.List;
import rx.Observable;

public abstract class SearchViewModel {

  public abstract Observable<List<File>> search(String query);

  public abstract void init();

  public abstract MainLinearAdapter getLinearAdapter();

  public abstract void setData(List<File> files);
}
