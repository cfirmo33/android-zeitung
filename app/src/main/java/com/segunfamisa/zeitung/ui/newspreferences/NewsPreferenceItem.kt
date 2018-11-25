package com.segunfamisa.zeitung.ui.newspreferences

import com.segunfamisa.zeitung.core.entities.Source

sealed class NewsPreferenceItem
data class NewsCategory(val category: String, val isChecked: Boolean) : NewsPreferenceItem()
data class NewsSource(val source: Source, val isChecked: Boolean) : NewsPreferenceItem()
