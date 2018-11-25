package com.segunfamisa.zeitung.domain.newscategories

import arrow.core.Either
import arrow.core.orNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.domain.common.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPreferredNewsCategoriesTest {

    private val repository: NewsCategoriesRepository = mock()

    private val useCase = GetPreferredNewsCategories(categoriesRepository = repository)

    @Test
    fun `use case gets preferred news categories from repository`() = runBlocking {
        // given that the repository returns a list of categories
        val categories = listOf("business", "technology")
        whenever(repository.getNewsCategories()).thenReturn(
            Either.right(Result(data = categories))
        )

        // when we invoke use case
        val result = useCase(Unit)

        // then the categories we get should be exactly the one from the repository
        val resultCategories = result.orNull()?.data

        // then assert that the result is not null
        assertEquals(
            "resulting categories should never be null",
            true,
            resultCategories != null
        )

        assertEquals(categories, resultCategories!!)
    }
}
