package com.funckyhacker.fileexplorer.view.search;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;

public abstract class RevealActivity extends AppCompatActivity {

  private static final int DEFAULT_ANIMATION_DURATION = 500;

  private boolean reverseRevealStarted;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      startReveal();
    }
  }

  public abstract Point getRevealCenterPoint();

  private void startReveal() {
    final View rootLayout = getRootView();
    if (rootLayout != null) {
      rootLayout.setVisibility(View.INVISIBLE);

      ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
      if (viewTreeObserver.isAlive()) {
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @TargetApi(Build.VERSION_CODES.LOLLIPOP)
          @Override
          public void onGlobalLayout() {
            circularRevealActivity(rootLayout, false);
            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        });
      }
    }
  }

  private void reverseReveal() {

    final View rootLayout = getRootView();

    if (rootLayout != null) {
      Animator circularReveal = circularRevealActivity(rootLayout, true);
      reverseRevealStarted = true;
      circularReveal.addListener(new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
          rootLayout.setVisibility(View.INVISIBLE);
          RevealActivity.super.finish();
          reverseRevealStarted = false;

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
      });
    }
  }

  @Nullable
  private View getRootView() {
    Window window = getWindow();
    return window != null ? window.getDecorView() : null;
  }

  private Animator circularRevealActivity(View rootLayout,  boolean reverse) {

    Point point = getRevealCenterPoint();

    int cx = (int) (rootLayout.getWidth() * point.x);
    int cy = (int) (rootLayout.getHeight() * point.y);

    float finalRadius = (float) Math.sqrt(Math.pow(rootLayout.getWidth(), 2) + Math.pow(rootLayout.getHeight(), 2));

    Animator animator;
    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    animator = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, reverse ? finalRadius : 0, reverse ? 0 : finalRadius);
    //}
    //else {
    //  animator = ObjectAnimator.ofFloat(rootLayout, View.ALPHA, reverse ? 1 : 0, reverse ? 0 : 1);
    //}
    animator.setDuration(DEFAULT_ANIMATION_DURATION);
    animator.setInterpolator(new DecelerateInterpolator());
    rootLayout.setVisibility(View.VISIBLE);
    animator.start();
    return animator;
  }

  @Override
  public void finish() {
    if (!reverseRevealStarted) {
      reverseReveal();
    }
  }

  protected static class Point {
    private final float x;
    private final float y;

    public Point(float x, float y) {
      this.x = x;
      this.y = y;
    }
  }
}
