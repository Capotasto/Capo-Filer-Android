package com.funckyhacker.fileexplorer.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

  public static String getItemDate(long dateLong) {
    Date fileDate = new Date(dateLong);
    Calendar fileCal = Calendar.getInstance();
    fileCal.setTime(fileDate);

    SimpleDateFormat sdf;
    //Check Today
    if (isToday(fileCal)) {
      sdf  = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
      return "Today " + sdf.format(fileDate);
    }

    //Check Yesterday
    if (isYesterday(fileCal)) {
      sdf  = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
      return "Yesterday " + sdf.format(fileDate);
    }

    //Check year
    if (isSameYear(fileCal)) {
      sdf = new SimpleDateFormat("MMMM dd", Locale.ENGLISH);
      return sdf.format(fileDate);
    }

    sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
    return sdf.format(fileDate);
  }

  public static boolean isToday(Calendar cal) {
    Calendar today = Calendar.getInstance();
    return today.get(Calendar.ERA) == cal.get(Calendar.ERA) &&
        today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
        today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR);
  }

  public static boolean isYesterday(Calendar cal) {
    Calendar yesCal = cal;
    yesCal.add(Calendar.DATE, 1);
    Calendar today = Calendar.getInstance();
    return today.get(Calendar.ERA) == yesCal.get(Calendar.ERA) &&
        today.get(Calendar.YEAR) == yesCal.get(Calendar.YEAR) &&
        today.get(Calendar.DAY_OF_YEAR) == yesCal.get(Calendar.DAY_OF_YEAR);
  }

  public static boolean isSameYear(Calendar cal) {
    Calendar today = Calendar.getInstance();
    return today.get(Calendar.ERA) == cal.get(Calendar.ERA) &&
        today.get(Calendar.YEAR) == cal.get(Calendar.YEAR);
  }
}
