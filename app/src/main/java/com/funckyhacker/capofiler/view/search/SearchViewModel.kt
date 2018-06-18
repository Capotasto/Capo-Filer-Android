package com.funckyhacker.capofiler.view.search

import com.funckyhacker.capofiler.view.adapter.MainLinearAdapter
import rx.Observable
import java.io.File

abstract class SearchViewModel {

    abstract val linearAdapter: MainLinearAdapter

    abstract fun search(query: String): Observable<List<File>>

    abstract fun init()

    abstract fun setData(files: List<File>)
}
