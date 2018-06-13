package com.funckyhacker.fileexplorer.util

import android.content.ContentResolver
import android.net.Uri
import android.os.Environment
import android.support.annotation.DrawableRes
import android.webkit.MimeTypeMap
import com.funckyhacker.fileexplorer.R
import org.apache.commons.io.comparator.NameFileComparator
import java.io.File
import java.math.BigDecimal
import java.util.*

object FileUtils {

    private const val SIZE_KB = 1024.0
    private const val SIZE_MB = (1024 * 1024).toDouble()
    private const val SIZE_GB = (1024 * 1024 * 1024).toDouble()

    /* Checks if external storage is available for read and write */
    val isExternalStorageWritable: Boolean
        get() {
            return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        }

    /* Checks if external storage is available to at least read */
    val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    /**
     * Return the String of file size with Unit
     *
     * @param size the long value of the byte
     * @return String of file size with Unit
     */
    fun getSizeStr(size: Long): String {
        if (SIZE_KB > size) {
            return size.toString() + " B"
        } else if (SIZE_MB > size && size >= SIZE_KB) {
            var dsize = size.toDouble()
            dsize /= SIZE_KB
            val bi = BigDecimal(dsize.toString())
            val value = bi.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            return value.toString() + " KB"
        } else if (SIZE_GB > size && size >= SIZE_MB) {
            var dsize = size.toDouble()
            dsize /= SIZE_MB
            val bi = BigDecimal(dsize.toString())
            val value = bi.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            return value.toString() + " MB"
        } else {
            var dsize = size.toDouble()
            dsize /= SIZE_GB
            val bi = BigDecimal(dsize.toString())
            val value = bi.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            return value.toString() + " GB"
        }
    }

    /**
     * Return list of the file from external storage
     * @param file folder name
     * @return list of the file
     */
    fun getFilesFromDir(file: File): List<File>? {
        return if (!file.isDirectory || file.listFiles() == null) null else ArrayList(Arrays.asList(*file.listFiles()))
    }

    fun getFilesFromName(name: String): File? {
        val rootDir = File(Environment.getExternalStorageDirectory().path)
        if (!rootDir.isDirectory || rootDir.listFiles() == null || rootDir.listFiles().isEmpty()) {
            return null
        }
        for (file in rootDir.listFiles()) {
            if (file.absolutePath.contains(name)) {
                return file
            }
        }
        return null
    }

    fun getMimeType(contentResolver: ContentResolver, file: File): String {
        if (file.isDirectory) {
            return "application/directory"
        }
        val mimeType: String?
        mimeType = if (Uri.fromFile(file).scheme == ContentResolver.SCHEME_CONTENT) {
            contentResolver.getType(Uri.fromFile(file))
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(file.absolutePath)
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        }
        return mimeType ?: ""
    }

    fun getSortedListByName(files: List<File>?): List<File>? {
        if (files != null) {
            Collections.sort(files, NameFileComparator.NAME_COMPARATOR)
        }
        return files
    }

    fun getSortedListByDate(files: List<File>?): List<File>? {
        if (files != null) {
            Collections.sort(files) { o1, o2 -> if (o1.lastModified() > o2.lastModified()) -1 else 1 }
        }
        return files
    }

    @DrawableRes
    fun getFileIconRes(resolver: ContentResolver, file: File): Int {
        return when (FileUtils.getMimeType(resolver, file)) {
            "image/jpeg" -> R.drawable.ic_jpg
            "image/png" -> R.drawable.ic_png
            "application/directory" -> R.drawable.ic_folder
            "application/pdf" -> R.drawable.ic_pdf
            "audio/mp4", "video/mp4", "application/mp4" -> R.drawable.ic_mp4
            "audio/mpeg" -> R.drawable.ic_mp3
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> R.drawable.ic_xls
            "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> R.drawable.ic_doc
            "text/plain" -> R.drawable.ic_text
            else -> R.drawable.ic_unknown
        }
    }
}
