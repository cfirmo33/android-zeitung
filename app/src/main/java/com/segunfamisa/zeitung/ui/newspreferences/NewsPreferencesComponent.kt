package com.segunfamisa.zeitung.ui.newspreferences

import dagger.Subcomponent

@Subcomponent(modules = [NewsPreferencesModule::class])
interface NewsPreferencesComponent {

    fun inject(fragment: NewsPreferencesFragment)
}
