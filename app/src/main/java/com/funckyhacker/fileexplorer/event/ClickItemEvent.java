package com.funckyhacker.fileexplorer.event;

import java.io.File;

public class ClickItemEvent {
  public File file;

  public ClickItemEvent(File file) {
    this.file = file;
  }
}
