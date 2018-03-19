package com.funckyhacker.fileexplorer.util;

import android.os.Environment;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
   * @param folder folder name
   * @return list of the file
   */
  public static List<File> getFiles(String folder) {
    List<File> files = new ArrayList<>();
    String path = Environment.getExternalStorageDirectory().getPath();
    File directory = new File(path);
    for (File file : directory.listFiles()) {
      if (file.getAbsolutePath().contains(folder)){
        files = new ArrayList<>(Arrays.asList(file.listFiles()));
      }
    }
    return files;
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
    if (Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      return true;
    }
    return false;
  }
}
