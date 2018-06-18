package com.funckyhacker.capofiler.di

import android.app.Activity
import com.funckyhacker.capofiler.view.search.SearchActivity
import com.funckyhacker.capofiler.view.search.SearchViewModel
import com.funckyhacker.capofiler.view.search.SearchViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SearchModule {
    @Binds
    @IntoMap
    @ActivityKey(SearchActivity::class)
    internal abstract fun bindInjectorFactory(
            builder: SearchComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    internal abstract fun bindMainViewModel(viewModel: SearchViewModelImpl): SearchViewModel

}
