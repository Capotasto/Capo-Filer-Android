package com.funckyhacker.fileexplorer.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getItemDate(dateLong: Long): String {
        val fileDate = Date(dateLong)
        val fileCal = Calendar.getInstance()
        fileCal.time = fileDate

        val sdf: SimpleDateFormat
        //Check Today
        if (isToday(fileCal)) {
            sdf = SimpleDateFormat("h:mm a", Locale.ENGLISH)
            return "Today " + sdf.format(fileDate)
        }

        //Check Yesterday
        if (isYesterday(fileCal)) {
            sdf = SimpleDateFormat("h:mm a", Locale.ENGLISH)
            return "Yesterday " + sdf.format(fileDate)
        }

        //Check year
        if (isSameYear(fileCal)) {
            sdf = SimpleDateFormat("MMMM dd", Locale.ENGLISH)
            return sdf.format(fileDate)
        }

        sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        return sdf.format(fileDate)
    }

    private fun isToday(cal: Calendar): Boolean {
        val today = Calendar.getInstance()
        return today.get(Calendar.ERA) == cal.get(Calendar.ERA) &&
                today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
    }

    private fun isYesterday(cal: Calendar): Boolean {
        cal.add(Calendar.DATE, 1)
        val today = Calendar.getInstance()
        return today.get(Calendar.ERA) == cal.get(Calendar.ERA) &&
                today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
    }

    private fun isSameYear(cal: Calendar): Boolean {
        val today = Calendar.getInstance()
        return today.get(Calendar.ERA) == cal.get(Calendar.ERA) && today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
    }
}
