package com.segunfamisa.zeitung.domain.newscategories

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddPreferredNewsSourceUseCaseTest {

    val repository: NewsPreferencesRepository = mock()

    val useCase = AddPreferredNewsSourceUseCase(newsPreferencesRepository = repository)

    @Test
    fun `correct news preference repository method is called when we execute`() = runBlocking {
        // given a news source id
        val newsSourceId = "techcrunch-us"

        // when we add a preferred news source
        useCase.invoke(param = newsSourceId)

        // then verify that we call the right method
        verify(repository).addNewsSource(sourceId = "techcrunch-us")
    }
}