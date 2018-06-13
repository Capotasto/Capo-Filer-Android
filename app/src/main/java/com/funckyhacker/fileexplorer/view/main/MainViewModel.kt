package com.funckyhacker.fileexplorer.view.main

import android.content.ContentResolver
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.net.Uri
import com.funckyhacker.fileexplorer.view.adapter.MainGridAdapter
import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter
import java.io.File

abstract class MainViewModel : BaseObservable() {

    @get:Bindable
    abstract var currentPath: String

    abstract val pageSize: Int

    @get:Bindable
    abstract val isNoFiles: Boolean

    abstract var layoutType: Int

    abstract var isSearchMode: Boolean

    abstract val linearAdapter: MainLinearAdapter

    abstract val gridAdapter: MainGridAdapter

    abstract fun init(view: MainView)

    abstract fun setData(files: List<File>?)

    abstract fun setFilesToList(name: String)

    abstract fun setFilesToList(file: File)

    abstract fun popItem()

    abstract fun sendIntent(resolver: ContentResolver, file: File, uri: Uri)

}
