package com.segunfamisa.zeitung.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun withApp(app: Application): Builder
    }
}
