package com.funckyhacker.capofiler.di

import com.funckyhacker.capofiler.view.search.SearchActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface SearchComponent : AndroidInjector<SearchActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SearchActivity>()
}
