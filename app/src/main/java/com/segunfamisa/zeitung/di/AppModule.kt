package com.segunfamisa.zeitung.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.segunfamisa.zeitung.data.di.DataModule
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class])
class AppModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
}
