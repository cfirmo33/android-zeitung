package com.segunfamisa.zeitung.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.segunfamisa.zeitung.data.di.DataModule
import com.segunfamisa.zeitung.util.CoroutineDispatcherProvider
import com.segunfamisa.zeitung.util.DefaultCoroutineDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class])
abstract class AppModule {

    @Binds
    abstract fun bindCoroutinesDispatcher(dispatcherProvider: DefaultCoroutineDispatcherProvider): CoroutineDispatcherProvider

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideSharedPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
}
