package com.funckyhacker.fileexplorer.util;

import android.os.Environment;
import android.support.annotation.NonNull;
import eu.medsea.mimeutil.MimeUtil2;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

  private static final double SIZE_KB = 1024;
  private static final double SIZE_MB = 1024 * 1024;
  private static final double SIZE_GB = 1024 * 1024 * 1024;

  private static MimeUtil2 mimeUtil;

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

  public static File getFilesFromName(@NonNull String root, @NonNull String name) {
    File rootDir = new File(root);
    if (!rootDir.isDirectory() || rootDir.listFiles() == null || rootDir.listFiles().length <= 0) {
      return null;
    }
    for (File file : rootDir.listFiles()) {
      if(file.getAbsolutePath().contains(name)) {
        return file;
      }
    }
    return null;
  }

  public static String getMimeType(File file) {
    if (mimeUtil == null) {
      mimeUtil = new MimeUtil2();
      //Tricky: This task is very heavy
      mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
    }
    return MimeUtil2.getMostSpecificMimeType(mimeUtil.getMimeTypes(file)).toString();
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
