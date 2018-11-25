package com.segunfamisa.zeitung.di

import android.app.Application
import com.segunfamisa.zeitung.di.viewmodel.ViewModelModule
import com.segunfamisa.zeitung.ui.newspreferences.NewsPreferencesComponent
import com.segunfamisa.zeitung.ui.newspreferences.NewsPreferencesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun withApp(app: Application): Builder
    }

    fun with(preferencesModule: NewsPreferencesModule): NewsPreferencesComponent
}
