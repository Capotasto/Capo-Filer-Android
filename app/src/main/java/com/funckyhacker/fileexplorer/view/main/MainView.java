package com.funckyhacker.fileexplorer.view.main;

import android.content.Intent;
import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter;

public interface MainView {

  void setAdapter(MainLinearAdapter adapter);

  void startActivity(Intent intent);

  void showSnackBar(String message);
}
