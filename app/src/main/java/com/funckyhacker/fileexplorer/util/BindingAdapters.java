package com.funckyhacker.fileexplorer.util;

import android.content.ContentResolver;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.funckyhacker.fileexplorer.GlideApp;
import com.funckyhacker.fileexplorer.R;
import java.io.File;

public class BindingAdapters {

  @BindingAdapter("file") public static void setFile(ImageView imageView, File file) {
    ContentResolver resolver = imageView.getContext().getContentResolver();
    switch (FileUtils.getMimeType(resolver, file)) {
    case "image/jpeg":
      GlideApp.with(imageView.getContext()).load(Uri.fromFile(file)).placeholder(R.drawable.ic_jpg).into(imageView);
      break;
    case "image/png":
      GlideApp.with(imageView.getContext()).load(Uri.fromFile(file)).placeholder(R.drawable.ic_png).into(imageView);
      break;
    default:
      setDrawableRes(imageView, FileUtils.getFileIconRes(resolver, file));
      break;
    }
  }

  private static void setDrawableRes(ImageView imageView, @DrawableRes int id) {
    GlideApp.with(imageView.getContext()).load(id).placeholder(R.drawable.ic_unknown).into(imageView);
  }
}
