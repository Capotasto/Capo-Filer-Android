package com.funckyhacker.fileexplorer;

import java.io.File;
import java.util.List;

public abstract class MainViewModel {

  public abstract void init(MainView view);

  public abstract void setData(List<File> files);
}
