package com.segunfamisa.zeitung.data.newspreferences

import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error

internal interface NewsPreferencesSource {

    fun getNewsPreferences(): Either<Error, List<String>>

    fun addNewsSource(sourceId: String)

    fun addNewsCategory(category: String)
}
