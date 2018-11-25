package com.segunfamisa.zeitung.domain.newscategories

import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result

interface NewsPreferencesRepository {
    /**
     * returns a list of news preferences for a user
     */
    fun getNewsPreferences(): Either<Error, Result<List<String>>>

    fun addNewsCategory(category: String)

    fun addNewsSource(sourceId: String)
}
