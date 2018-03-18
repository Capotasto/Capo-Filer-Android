package com.funckyhacker.fileexplorer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.funckyhacker.fileexplorer.databinding.ActivityMainBinding;
import dagger.android.AndroidInjection;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainView {

  private ActivityMainBinding binding;

  @Inject MainViewModel viewModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    setSupportActionBar(binding.toolBar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeButtonEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

    viewModel.init(this);

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

      return true;
    });
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
}
