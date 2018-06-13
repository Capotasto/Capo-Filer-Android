package com.funckyhacker.fileexplorer.util

import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageManger @Inject
constructor() {

    private val stack: Deque<String>

    init {
        stack = ArrayDeque()
    }

    fun push(path: String) {
        Timber.i("Pushed Item: %s", path)
        stack.offerFirst(path)
    }

    fun pop(): String {
        val path = stack.poll()
        Timber.i("Popped Item: %s", path)
        return path
    }

    fun clearAll() {
        stack.clear()
    }

    fun size(): Int {
        return stack.size
    }
}
