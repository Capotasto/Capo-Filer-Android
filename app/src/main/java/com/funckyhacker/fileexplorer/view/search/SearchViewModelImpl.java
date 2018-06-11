package com.funckyhacker.fileexplorer.view.search;

import android.os.Environment;
import android.text.TextUtils;
import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Emitter;
import rx.Observable;
import timber.log.Timber;

public class SearchViewModelImpl extends SearchViewModel {

  private MainLinearAdapter linearAdapter;

  @Inject
  public SearchViewModelImpl() {}

  @Override public Observable<List<File>> search(String query) {
    return Observable.create(e -> {
      long start = System.currentTimeMillis();
      List<File> result = new ArrayList<>();

      File rootDir = new File(Environment.getExternalStorageDirectory().getPath());
      if (!rootDir.isDirectory() || rootDir.listFiles() == null || rootDir.listFiles().length <= 0) {
        e.onError(new Throwable("Something wrong your External Storage"));
      }
      searchDir(result, rootDir, query, e);
      e.onNext(result);
      long end = System.currentTimeMillis();
      Timber.d("Search Elapsed Time: %d", end - start);
    }, Emitter.BackpressureMode.DROP);
  }

  @Override public void init() {
    linearAdapter = new MainLinearAdapter();
  }

  @Override public MainLinearAdapter getLinearAdapter() {
    return linearAdapter;
  }

  @Override public void setData(List<File> files) {
    linearAdapter.setData(files);
  }

  private void searchDir(List<File> result, File root, String query, Emitter<List<File>> e) {
    for (File file : root.listFiles()) {
      if (TextUtils.isEmpty(file.getName())){
        continue;
      }
      if (file.getName().contains(query) || file.getName().toLowerCase().contains(query)) {
        result.add(file);
      }
      if (file.isDirectory()) {
        searchDir(result, file, query, e);//Notice: Recursive Call
      }
    }
  }
}
