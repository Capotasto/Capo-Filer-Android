package com.funckyhacker.fileexplorer.view.main

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import com.funckyhacker.fileexplorer.BR
import com.funckyhacker.fileexplorer.R
import com.funckyhacker.fileexplorer.util.FileUtils
import com.funckyhacker.fileexplorer.util.PageManger
import com.funckyhacker.fileexplorer.view.adapter.MainGridAdapter
import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter
import java.io.File
import java.util.*
import javax.inject.Inject

class MainViewModelImpl @Inject
constructor(private val pageManger: PageManger) : MainViewModel() {

    override val rootFiles: List<File>?
        get() {
            if (!FileUtils.isExternalStorageReadable || !FileUtils.isExternalStorageWritable) {
                view.showErrorDialog(R.string.error_msg_sdcard)
                return null
            }
            if (TextUtils.isEmpty(currentPath)) {
                currentPath = Environment.getExternalStorageDirectory().path
            }
            return FileUtils.getFilesFromDir(File(currentPath))
        }

    private lateinit var view: MainView

    override lateinit var linearAdapter: MainLinearAdapter
        private set

    override lateinit var gridAdapter: MainGridAdapter
        private set

    override var currentPath: String = ""
        set(currentPath) {
            field = currentPath
            notifyPropertyChanged(BR.currentPath)
        }

    override var layoutType: Int = 0

    override var isNoFiles: Boolean = false
        set(noFiles) {
            field = noFiles
            notifyPropertyChanged(BR.noFiles)
        }
    override var isSearchMode: Boolean = false

    override val pageSize: Int
        get() = pageManger.size()

    override fun init(view: MainView) {
        this.view = view
        linearAdapter = MainLinearAdapter()
        gridAdapter = MainGridAdapter()
        view.setAdapter(linearAdapter)
    }

    override fun setData(files: List<File>?) {
        isNoFiles = files == null || files.isEmpty()
        linearAdapter.setData(files)
        gridAdapter.setData(files)
    }

    override fun setFilesToList(name: String) {
        val file = FileUtils.getFilesFromName(name) ?: return
        pageManger.push(currentPath)
        currentPath = file.absolutePath
        setData(Arrays.asList(*file.listFiles()))
    }

    override fun setFilesToList(file: File) {
        pageManger.push(currentPath)
        currentPath = file.absolutePath
        setData(Arrays.asList(*file.listFiles()))
    }

    override fun popItem() {
        currentPath = pageManger.pop()
        val file = File(currentPath)
        setData(Arrays.asList(*file.listFiles()))
    }

    override fun sendIntent(resolver: ContentResolver, file: File, uri: Uri) {
        val mimeType = FileUtils.getMimeType(resolver, file)
        if (TextUtils.isEmpty(mimeType)) {
            view.showSnackBar("Couldn't show the preview: " + file.name)
            return
        }
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(uri, mimeType)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        view.startActivity(intent)
    }
}
