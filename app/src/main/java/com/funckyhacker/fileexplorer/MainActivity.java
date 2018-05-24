package com.funckyhacker.fileexplorer;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.funckyhacker.fileexplorer.databinding.ActivityMainBinding;
import com.funckyhacker.fileexplorer.event.ClickItemEvent;
import com.funckyhacker.fileexplorer.util.FileUtils;
import dagger.android.AndroidInjection;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import timber.log.Timber;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements MainView {

  @Inject MainViewModel viewModel;

  private ActivityMainBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.setViewModel(viewModel);
    setSupportActionBar(binding.toolBar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeButtonEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    binding.listView.setLayoutManager(new LinearLayoutManager(this));
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
    binding.listView.addItemDecoration(dividerItemDecoration);
    viewModel.init(this);

    initDrawer();

  }

  @Override protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
    MainActivityPermissionsDispatcher.enableAccessStorageWithPermissionCheck(this);
  }

  @Override protected void onResume() {
    super.onResume();
    if (!FileUtils.isExternalStorageReadable() || !FileUtils.isExternalStorageWritable()) {
      new MaterialDialog.Builder(this)
          .positiveText(android.R.string.ok)
          .negativeText(android.R.string.cancel)
          .build();
    }
    if (TextUtils.isEmpty(viewModel.getCurrentPath())) {
       viewModel.setCurrentPath(Environment.getExternalStorageDirectory().getPath());
    }
    List<File> files = FileUtils.getFilesFromDir(new File(viewModel.getCurrentPath()));
    if (files == null) {
      return;
    }
    viewModel.setData(files);
  }

  @Override public void onBackPressed() {
    if (viewModel.getPageSize() == 0) {
      super.onBackPressed();
      return;
    }
    viewModel.popItem();
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case android.R.id.home:
      binding.drawerLayout.openDrawer(GravityCompat.START);
      return true;
    }

    return super.onOptionsItemSelected(item);

  }

  @Subscribe
  public void onClickItemEvent(ClickItemEvent event) {
    if (event.file.isDirectory()) {
      viewModel.setFilesToList(event.file);
      return;
    }
    Uri apkURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", event.file);
    viewModel.sendIntent(getContentResolver(), event.file, apkURI);
  }

  private void initDrawer() {
    binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

      @Override public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        //Timber.d("onDrawerSlide");
      }

      @Override public void onDrawerOpened(@NonNull View drawerView) {
        Timber.d("onDrawerOpened");


      }

      @Override public void onDrawerClosed(@NonNull View drawerView) {
        Timber.d("onDrawerClosed");

      }

      @Override public void onDrawerStateChanged(int newState) {
        Timber.d("onDrawerStateChanged");

      }
    });

    binding.navView.setNavigationItemSelectedListener(item -> {
      // set item as selected to persist highlight
      item.setChecked(true);
      // close drawer when item is tapped
      binding.drawerLayout.closeDrawers();
      switch (item.getItemId()) {
      case R.id.nav_download:
        viewModel.setFilesToList(Environment.DIRECTORY_DOWNLOADS);
        break;
      case R.id.nav_picture:
        viewModel.setFilesToList(Environment.DIRECTORY_PICTURES);
        break;
      case R.id.nav_audio:
        viewModel.setFilesToList(Environment.DIRECTORY_MUSIC);
        break;
      case R.id.nav_video:
        viewModel.setFilesToList(Environment.DIRECTORY_MOVIES);
        break;
      }

      return true;
    });
  }

  @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
  protected void enableAccessStorage() {

  }

  @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
  protected void onStorageDenied() {
    Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
  }


  @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
  protected void onStorageNeverAskAgain() {
    Toast.makeText(this, R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
  }

  @Override public void setAdapter(MainAdapter adapter) {
    binding.listView.setAdapter(adapter);
  }

  @Override public void startActivity(Intent intent) {
    super.startActivity(intent);
  }

  @Override public void showSnackBar(String message) {
    Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT);
    snackbar.show();
  }
}
