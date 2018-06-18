package com.funckyhacker.capofiler.di

import com.funckyhacker.capofiler.util.PageManger
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
