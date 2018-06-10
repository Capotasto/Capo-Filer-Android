package com.funckyhacker.fileexplorer;

import android.content.Intent;

public interface MainView {

  void setAdapter(MainLinearAdapter adapter);

  void startActivity(Intent intent);

  void showSnackBar(String message);
}
