package com.funckyhacker.capofiler.view.search

import android.os.Environment
import android.text.TextUtils
import com.funckyhacker.capofiler.view.adapter.MainLinearAdapter
import rx.Emitter
import rx.Observable
import java.io.File
import java.util.*
import javax.inject.Inject

class SearchViewModelImpl @Inject
constructor() : SearchViewModel() {

    override lateinit var linearAdapter: MainLinearAdapter
        private set

    override fun search(query: String): Observable<List<File>> {
        return Observable.create({ e ->
            val result = ArrayList<File>()

            val rootDir = File(Environment.getExternalStorageDirectory().path)
            if (!rootDir.isDirectory || rootDir.listFiles() == null || rootDir.listFiles().isEmpty()) {
                e.onError(Throwable("Something wrong your External Storage"))
            }
            searchDir(result, rootDir, query)
            e.onNext(result)
        }, Emitter.BackpressureMode.DROP)
    }

    override fun init() {
        linearAdapter = MainLinearAdapter()
    }

    override fun setData(files: List<File>) {
        linearAdapter.setData(files)
    }

    private fun searchDir(result: MutableList<File>, root: File, query: String) {
        for (file in root.listFiles()) {
            if (TextUtils.isEmpty(file.name)) {
                continue
            }
            if (file.name.contains(query) || file.name.toLowerCase().contains(query)) {
                result.add(file)
            }
            if (file.isDirectory) {
                searchDir(result, file, query)//Notice: Recursive Call
            }
        }
    }
}
