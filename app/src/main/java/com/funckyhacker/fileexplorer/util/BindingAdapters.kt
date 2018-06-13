package com.funckyhacker.fileexplorer.util

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.funckyhacker.fileexplorer.R
import com.funckyhacker.fileexplorer.di.GlideApp
import java.io.File

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("file")
    fun setFile(imageView: ImageView, file: File) {
        val resolver = imageView.context.contentResolver
        when (FileUtils.getMimeType(resolver, file)) {
            "image/jpeg" -> GlideApp.with(imageView.context).load(Uri.fromFile(file)).placeholder(R.drawable.ic_jpg).into(imageView)
            "image/png" -> GlideApp.with(imageView.context).load(Uri.fromFile(file)).placeholder(R.drawable.ic_png).into(imageView)
            else -> setDrawableRes(imageView, FileUtils.getFileIconRes(resolver, file))
        }
    }

    private fun setDrawableRes(imageView: ImageView, @DrawableRes id: Int) {
        GlideApp.with(imageView.context).load(id).placeholder(R.drawable.ic_unknown).into(imageView)
    }
}
