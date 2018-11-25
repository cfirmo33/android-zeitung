package com.segunfamisa.zeitung.ui.newspreferences

import androidx.lifecycle.ViewModel
import com.segunfamisa.zeitung.di.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class NewsPreferencesModule {

    @Provides
    @IntoMap
    @ViewModelKey(NewsPreferencesViewModel::class)
    fun bindNewsPreferencesViewModel(viewModel: NewsPreferencesViewModel): ViewModel = viewModel
}
