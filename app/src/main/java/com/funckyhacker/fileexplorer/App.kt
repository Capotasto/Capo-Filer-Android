package com.funckyhacker.fileexplorer

import com.facebook.stetho.Stetho
import com.funckyhacker.fileexplorer.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Timber
            Timber.plant(Timber.DebugTree())
            // stetho
            Stetho.initializeWithDefaults(this)
        }
    }
}
