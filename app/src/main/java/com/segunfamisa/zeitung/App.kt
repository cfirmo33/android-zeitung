package com.segunfamisa.zeitung

import android.app.Application
import com.segunfamisa.zeitung.di.AppComponent
import com.segunfamisa.zeitung.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .withApp(app = this)
            .build()
    }
}
