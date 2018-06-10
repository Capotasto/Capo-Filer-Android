package com.funckyhacker.fileexplorer.view.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import com.funckyhacker.fileexplorer.R;
import com.funckyhacker.fileexplorer.databinding.ActivitySearchBinding;
import com.funckyhacker.fileexplorer.event.ClickItemEvent;
import dagger.android.AndroidInjection;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class SearchActivity extends RevealActivity implements SearchView {

  public static final String EXTRA_FILE = "ex_file";

  @Inject SearchViewModel viewModel;
  private ActivitySearchBinding binding;
  private CompositeSubscription subscription;

  public static Intent createIntent(Context context) {
    return new Intent(context, SearchActivity.class);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
    subscription = new CompositeSubscription();
    viewModel.init();
    initSearchView();
    initListView();
  }

  @Override protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override public Point getRevealCenterPoint() {
    return new Point(0.90f, 0.05f);
  }

  @Subscribe
  public void onClickItemEvent(ClickItemEvent event) {
    Intent intent = new Intent();
    intent.putExtra(EXTRA_FILE, event.file);
    setResult(RESULT_OK, intent);
    finish();
  }

  private void initListView() {
    binding.listView.setLayoutManager(new LinearLayoutManager(this));
    binding.listView.setAdapter(viewModel.getLinearAdapter());
  }

  private void initSearchView() {
    binding.searchView.performClick();
    binding.searchView.setIconifiedByDefault(false);
    binding.searchView.setFocusable(true);
    binding.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        Timber.i("onQueryTextSubmit: %s", query);
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {
        Timber.i("onQueryTextChange: %s", newText);
        subscription.add(viewModel.search(newText)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                r -> viewModel.setData(r),
                e -> Timber.w("Search Error: %s", e.getMessage()),
                () -> Timber.d("Search Done")
            )
        );
        return false;
      }
    });
  }
}
