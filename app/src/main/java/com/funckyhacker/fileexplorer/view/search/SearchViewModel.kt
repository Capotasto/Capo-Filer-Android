package com.funckyhacker.fileexplorer.view.search

import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter
import rx.Observable
import java.io.File

abstract class SearchViewModel {

    abstract val linearAdapter: MainLinearAdapter

    abstract fun search(query: String): Observable<List<File>>

    abstract fun init()

    abstract fun setData(files: List<File>)
}
