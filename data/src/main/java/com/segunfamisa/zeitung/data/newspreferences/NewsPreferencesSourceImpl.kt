package com.segunfamisa.zeitung.data.newspreferences

import android.content.SharedPreferences
import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error
import javax.inject.Inject

internal class NewsPreferencesSourceImpl @Inject constructor(
    private val prefs: SharedPreferences
) : NewsPreferencesSource {

    companion object {
        private const val keyCategories = "news_categories"
        private const val keySources = "news_sources_ids"
    }

    override fun getNewsPreferences(): Either<Error, List<String>> {
        val preferences = mutableListOf<String>().apply {
            addAll(getCategories())
            addAll(getSources())
        }.toList()

        return Either.right(preferences)
    }

    override fun addNewsSource(sourceId: String) {
        val sources = getSources().apply {
            add(sourceId)
        }
        prefs.edit()
            .putStringSet(keySources, sources)
            .apply()
    }

    override fun addNewsCategory(category: String) {
        val categories = getCategories().apply {
            add(category)
        }
        prefs.edit()
            .putStringSet(keyCategories, categories)
            .apply()
    }

    private fun getCategories() = prefs.getStringSet(keyCategories, setOf()) ?: setOf()

    private fun getSources() = prefs.getStringSet(keySources, setOf()) ?: setOf()
}
