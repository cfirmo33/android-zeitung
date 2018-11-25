package com.segunfamisa.zeitung.data.newspreferences

import arrow.core.Either
import com.segunfamisa.zeitung.data.di.qualifiers.DataSource
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result
import com.segunfamisa.zeitung.domain.newscategories.NewsPreferencesRepository
import javax.inject.Inject

internal class NewsPreferencesRepositoryImpl @Inject constructor(
    @DataSource(type = "local") private val preferencesSource: NewsPreferencesSource
) : NewsPreferencesRepository {

    override fun getNewsPreferences(): Either<Error, Result<List<String>>> =
        preferencesSource.getNewsPreferences()
            .map {
                Result(data = it)
            }

    override fun addNewsCategory(category: String) = preferencesSource.addNewsCategory(category = category)

    override fun addNewsSource(sourceId: String) = preferencesSource.addNewsSource(sourceId = sourceId)
}
