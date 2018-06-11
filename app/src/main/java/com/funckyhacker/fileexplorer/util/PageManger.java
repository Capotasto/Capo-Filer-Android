package com.funckyhacker.fileexplorer.util;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton
public class PageManger {

  private Deque<String> stack;

  @Inject
  public PageManger() {
    stack = new ArrayDeque<>();
  }

  public void push(String path) {
    Timber.i("Pushed Item: %s", path);
    stack.offerFirst(path);
  }

  public String pop() {
    String path = stack.poll();
    Timber.i("Popped Item: %s", path);
    return path;
  }

  public void clearAll() {
    stack.clear();
  }

  public int size() {
    return stack.size();
  }
}
