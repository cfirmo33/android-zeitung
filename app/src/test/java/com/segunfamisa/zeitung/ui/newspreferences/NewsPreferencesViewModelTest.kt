package com.segunfamisa.zeitung.ui.newspreferences

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.TestCoroutineDispatcherProvider
import com.segunfamisa.zeitung.core.entities.Category
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result
import com.segunfamisa.zeitung.domain.newscategories.GetAllCategoriesUseCase
import com.segunfamisa.zeitung.domain.newscategories.GetUserNewsPreferencesUseCase
import com.segunfamisa.zeitung.domain.newssources.GetNewsSourcesUseCase
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesQueryParam
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsPreferencesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getAllCategoriesUseCase: GetAllCategoriesUseCase = mock()
    private val getSourcesUseCase: GetNewsSourcesUseCase = mock()
    private val getUserNewsPreferencesUseCase: GetUserNewsPreferencesUseCase = mock()

    @Test
    fun `news preferences are all unchecked when user has no matching preferences`() = runBlocking {
        // given that the use case can load sources successfully
        val source1 = createSource()
        val sources = listOf(source1)
        whenever(getSourcesUseCase.invoke(param = NewsSourcesQueryParam()))
            .thenReturn(Either.right(Result(data = sources)))

        // given that the categories use case loads successfully
        val categories = listOf(Category.Business(), Category.Entertainment())
        whenever(getAllCategoriesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = categories)))

        // given that the user does not have any preferences
        whenever(getUserNewsPreferencesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = listOf<String>())))

        // when we create the view model and check the preferences
        val viewModel = createViewModel().apply {
            init()
        }
        val preferences = viewModel.newsPreferences.value

        // then verify that we merge and emit the preferences
        val expectedPreferences = listOf(
            NewsCategory(category = Category.Business().category, isChecked = false),
            NewsCategory(category = Category.Entertainment().category, isChecked = false),
            NewsSource(source = source1, isChecked = false)
        )
        assertEquals(expectedPreferences, preferences!!)
    }

    @Test
    fun `news preference is checked when user has matching preferred category`() = runBlocking {
        // given that the use case can load sources successfully
        val source1 = createSource()
        val sources = listOf(source1)
        whenever(getSourcesUseCase.invoke(param = NewsSourcesQueryParam()))
            .thenReturn(Either.right(Result(data = sources)))

        // given that the categories use case loads successfully
        val categories = listOf(Category.Business(), Category.Entertainment())
        whenever(getAllCategoriesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = categories)))

        // given that the user does not has previously selected business category
        whenever(getUserNewsPreferencesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = listOf(Category.Business().category))))

        // when we create the view model and check the preferences
        val viewModel = createViewModel().apply {
            init()
        }
        val preferences = viewModel.newsPreferences.value

        // then verify that we emit the business category as checked
        val expectedPreferences = listOf(
            NewsCategory(category = Category.Business().category, isChecked = true),
            NewsCategory(category = Category.Entertainment().category, isChecked = false),
            NewsSource(source = source1, isChecked = false)
        )
        assertEquals(
            "only the business category should be checked",
            expectedPreferences,
            preferences!!
        )
    }

    @Test
    fun `news preference is checked when user has matching preferred news source`() = runBlocking {
        // given that the use case can load sources successfully
        val source1 = createSource()
        val sources = listOf(source1)
        whenever(getSourcesUseCase.invoke(param = NewsSourcesQueryParam()))
            .thenReturn(Either.right(Result(data = sources)))

        // given that the categories use case loads successfully
        val categories = listOf(Category.Business(), Category.Entertainment())
        whenever(getAllCategoriesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = categories)))

        // given that the user does not has previously selected a news source
        whenever(getUserNewsPreferencesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = listOf(source1.id))))

        // when we create the view model and check the preferences
        val viewModel = createViewModel().apply {
            init()
        }
        val preferences = viewModel.newsPreferences.value

        // then verify that we emit the business category as checked
        val expectedPreferences = listOf(
            NewsCategory(category = Category.Business().category, isChecked = false),
            NewsCategory(category = Category.Entertainment().category, isChecked = false),
            NewsSource(source = source1, isChecked = true)
        )
        assertEquals(
            "only the matching news source should be marked as checked",
            expectedPreferences,
            preferences!!
        )
    }

    @Test
    fun `loading progress is shown at the beginning and hidden when the results are fetched`() = runBlocking {
        // given that the use case can load sources successfully
        val source1 = createSource()
        val sources = listOf(source1)
        whenever(getSourcesUseCase.invoke(param = NewsSourcesQueryParam()))
            .thenReturn(Either.right(Result(data = sources)))

        // given that the categories use case loads successfully
        val categories = listOf(Category.Business(), Category.Entertainment())
        whenever(getAllCategoriesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = categories)))

        // given that the user does not has previously selected a news source
        whenever(getUserNewsPreferencesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = listOf(source1.id))))

        // given a view model
        val viewModel = createViewModel()

        // given that we are observing the view model
        val observer = mock<Observer<Boolean>>()
        viewModel.loading.observeForever(observer)

        // when we init the view model
        viewModel.init()

        // then verify that we show the loading first and dismiss it after
        val inOrder = inOrder(observer)
        inOrder.verify(observer).onChanged(true)
        inOrder.verify(observer).onChanged(false)
    }

    @Test
    fun `loading progress is shown at the beginning and hidden if an error occurs`() = runBlocking {
        // given that the use case can load sources successfully
        val source1 = createSource()
        whenever(getSourcesUseCase.invoke(param = NewsSourcesQueryParam()))
            .thenReturn(Either.left(Error(message = "Unable to fetch news sources")))

        // given that the categories use case loads successfully
        whenever(getAllCategoriesUseCase.invoke(param = Unit))
            .thenReturn(Either.left(Error(message = "Unable to fetch categories")))

        // given that the user does not has previously selected a news source
        whenever(getUserNewsPreferencesUseCase.invoke(param = Unit))
            .thenReturn(Either.right(Result(data = listOf(source1.id))))

        // given a view model
        val viewModel = createViewModel()

        // given that we are observing the view model
        val observer = mock<Observer<Boolean>>()
        viewModel.loading.observeForever(observer)

        // when we init the view model
        viewModel.init()

        // then verify that we show the loading first and dismiss it after
        val inOrder = inOrder(observer)
        inOrder.verify(observer).onChanged(true)
        inOrder.verify(observer).onChanged(false)
    }

    private fun createViewModel(): NewsPreferencesViewModel = NewsPreferencesViewModel(
        getAllCategoriesUseCase = getAllCategoriesUseCase,
        getSourcesUseCase = getSourcesUseCase,
        getUserNewsPreferencesUseCase = getUserNewsPreferencesUseCase,
        dispatcherProvider = TestCoroutineDispatcherProvider()
    )

    private fun createSource(): Source = Source(
        id = "id 1",
        language = "language 1",
        country = "country 1",
        category = "category 1",
        description = "description 1",
        url = "url 1",
        name = "name 1"
    )
}
