package com.funckyhacker.capofiler.view.main

import android.content.Intent
import android.support.annotation.StringRes
import com.funckyhacker.capofiler.view.adapter.MainLinearAdapter

interface MainView {

    fun setAdapter(adapter: MainLinearAdapter)

    fun startActivity(intent: Intent)

    fun showSnackBar(message: String)

    fun showErrorDialog(@StringRes messageId: Int)
}
