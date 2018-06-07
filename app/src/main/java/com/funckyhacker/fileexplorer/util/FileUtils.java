package com.funckyhacker.fileexplorer.util;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;
import com.funckyhacker.fileexplorer.R;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.comparator.NameFileComparator;

public class FileUtils {

  private static final double SIZE_KB = 1024;
  private static final double SIZE_MB = 1024 * 1024;
  private static final double SIZE_GB = 1024 * 1024 * 1024;

  /**
   * Return the String of file size with Unit
   *
   * @param size the long value of the byte
   * @return String of file size with Unit
   */
  public static String getSizeStr(long size) {
    if (SIZE_KB > size) {
      return size + " B";
    } else if (SIZE_MB > size && size >= SIZE_KB) {
      double dsize = size;
      dsize = dsize / SIZE_KB;
      BigDecimal bi = new BigDecimal(String.valueOf(dsize));
      double value = bi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
      return value + " KB";
    } else if (SIZE_GB > size && size >= SIZE_MB) {
      double dsize = size;
      dsize = dsize / SIZE_MB;
      BigDecimal bi = new BigDecimal(String.valueOf(dsize));
      double value = bi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
      return value + " MB";
    } else {
      double dsize = size;
      dsize = dsize / SIZE_GB;
      BigDecimal bi = new BigDecimal(String.valueOf(dsize));
      double value = bi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
      return value + " GB";
    }
  }

  /**
   * Return list of the file from external storage
   * @param file folder name
   * @return list of the file
   */
  public static List<File> getFilesFromDir(@NonNull File file) {
    if (!file.isDirectory() || file.listFiles() == null) {
      return null;
    }
    return new ArrayList<>(Arrays.asList(file.listFiles()));
  }

  public static File getFilesFromName(@NonNull String name) {
    File rootDir = new File(Environment.getExternalStorageDirectory().getPath());
    if (!rootDir.isDirectory() || rootDir.listFiles() == null || rootDir.listFiles().length <= 0) {
      return null;
    }
    for (File file : rootDir.listFiles()) {
      if (file.getAbsolutePath().contains(name)) {
        return file;
      }
    }
    return null;
  }

  public static String getMimeType(ContentResolver contentResolver, File file) {
    if (file.isDirectory()) {
      return "application/directory";
    }
    String mimeType;
    if (Uri.fromFile(file).getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
      mimeType = contentResolver.getType(Uri.fromFile(file));
    } else {
      String fileExtension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
      mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
    }
    if (mimeType == null) {
      return "";
    }
    return mimeType;
  }

  /* Checks if external storage is available for read and write */
  public static boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  /* Checks if external storage is available to at least read */
  public static boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      return true;
    }
    return false;
  }

  public static List<File> getSortedListByName(List<File> files) {
    if (files != null) {
      Collections.sort(files, NameFileComparator.NAME_COMPARATOR);
    }
    return files;
  }

  public static List<File> getSortedListByDate(List<File> files) {
    if (files != null) {
      Collections.sort(files, (o1, o2) -> o1.lastModified() > o2.lastModified() ? -1 : 1);
    }
    return files;
  }

  @DrawableRes public static int getFileIconRes(ContentResolver resolver, File file) {
    switch (FileUtils.getMimeType(resolver, file)) {
    case "image/jpeg":
      return R.drawable.ic_jpg;
    case "image/png":
      return R.drawable.ic_png;
    case "application/directory":
      return R.drawable.ic_folder;
    case "application/pdf":
      return R.drawable.ic_pdf;
    case "audio/mp4":
    case "video/mp4":
    case "application/mp4":
      return R.drawable.ic_mp4;
    case "audio/mpeg":
      return R.drawable.ic_mp3;
    case "application/vnd.ms-excel":
    case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
      return R.drawable.ic_xls;
    case "application/msword":
    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
      return R.drawable.ic_doc;
    case "text/plain":
      return R.drawable.ic_text;
    default:
      return R.drawable.ic_unknown;
    }
  }
}
