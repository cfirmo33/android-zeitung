package com.segunfamisa.zeitung.ui.newspreferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.core.orNull
import com.segunfamisa.zeitung.core.entities.Category
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result
import com.segunfamisa.zeitung.domain.newscategories.GetAllCategoriesUseCase
import com.segunfamisa.zeitung.domain.newscategories.GetUserNewsPreferencesUseCase
import com.segunfamisa.zeitung.domain.newssources.GetNewsSourcesUseCase
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesQueryParam
import com.segunfamisa.zeitung.util.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsPreferencesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getSourcesUseCase: GetNewsSourcesUseCase,
    private val getUserNewsPreferencesUseCase: GetUserNewsPreferencesUseCase,
    private val dispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private var parentJob = Job()
    private var getSourcesJob = Job()

    private var scope = CoroutineScope(dispatcherProvider.main() + parentJob)

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _newsPreferences = MutableLiveData<List<NewsPreferenceItem>>()
    val newsPreferences: LiveData<List<NewsPreferenceItem>>
        get() = _newsPreferences

    fun init() {
        loadPreferences()
    }

    private fun loadPreferences() {
        showLoading(show = true)
        getSourcesJob = scope.launch(dispatcherProvider.io()) {
            val sources = getSourcesUseCase.invoke(param = NewsSourcesQueryParam())
            val categories = getAllCategoriesUseCase.invoke(param = Unit)
            val userPreferences = getUserNewsPreferencesUseCase.invoke(param = Unit)

            transform(sources, categories, userPreferences)
                .fold(ifLeft = { error ->
                    showLoading(show = false)
                    _error.postValue(error)
                }, ifRight = { result ->
                    showLoading(show = false)
                    _newsPreferences.postValue(result)
                })
        }
    }

    private fun transform(
        sources: Either<Error, Result<List<Source>>>,
        categories: Either<Error, Result<out List<Category>>>,
        userPreferences: Either<Error, Result<out List<String>>>
    ): Either<Error, List<NewsPreferenceItem>> {
        val sourceData = sources.orNull()?.data ?: listOf()
        val categoriesData = categories.orNull()?.data ?: listOf()
        val userPreferencesData = userPreferences.orNull()?.data ?: listOf()

        val out = listOf(
            sourceData
                .map { source ->
                    NewsSource(
                        source = source,
                        isChecked = userPreferencesData.contains(source.id)
                    )
                },
            categoriesData
                .map { it ->
                    NewsCategory(
                        category = it.category,
                        isChecked = userPreferencesData.contains(it.category)
                    )
                }
        ).flatten()

        return if (out.isEmpty()) {
            Either.left(Error(message = "Unable to load complete result"))
        } else {
            Either.right(out)
        }
    }

    private fun showLoading(show: Boolean) {
        _loading.postValue(show)
    }
}
