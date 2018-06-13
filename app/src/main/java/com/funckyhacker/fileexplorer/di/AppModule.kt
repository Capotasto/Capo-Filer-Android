package com.funckyhacker.fileexplorer.di

import com.funckyhacker.fileexplorer.util.PageManger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    (MainComponent::class),
    (SearchComponent::class)
])
class AppModule {

    @Singleton
    @Provides
    fun providePageManager(): PageManger {
        return PageManger()
    }
}
