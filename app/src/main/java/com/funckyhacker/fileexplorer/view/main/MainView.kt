package com.funckyhacker.fileexplorer.view.main

import android.content.Intent
import com.funckyhacker.fileexplorer.view.adapter.MainLinearAdapter

interface MainView {

    fun setAdapter(adapter: MainLinearAdapter)

    fun startActivity(intent: Intent)

    fun showSnackBar(message: String)
}
