package com.segunfamisa.zeitung.data.sources.remote

import com.segunfamisa.zeitung.data.sources.remote.entities.Article
import com.segunfamisa.zeitung.data.sources.remote.entities.Source
import com.segunfamisa.zeitung.data.sources.remote.entities.SourceMinimal
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.text.SimpleDateFormat

class ApiServiceTest {

    companion object {

        private val mockApi = MockApi()
        private lateinit var server: MockWebServer

        @JvmStatic
        @BeforeClass
        fun setUpServer() {
            server = mockApi.createServer()
            server.start()
        }

        @JvmStatic
        @AfterClass
        fun tearDownServer() {
            server.shutdown()
        }
    }

    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
    private val urlProvider = object : UrlProvider {
        override fun getBaseUrl(): String {
            return server.url("/").toString()
        }
    }
    private val interceptor = AuthorizationInterceptor(apiKeyProvider = ApiKeyProviderImpl())
    private val apiServiceCreator = ApiServiceCreator(urlProvider = urlProvider, authorizationInterceptor = interceptor)

    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = apiServiceCreator.createService()
    }

    @Test
    fun `get top headlines successfully`() = runBlocking {
        // given that the request completes successfully
        server.setDispatcher(mockApi.successDispatcher)

        // when we call get headlines
        val headlines = apiService.getHeadlines().await()

        // then verify that the headlines (as read from the json resource files) correspond to the expected ones
        val response = headlines.body()!!
        val expectedArticle0 = Article(
            source = SourceMinimal(id = "the-guardian-au", name = "The Guardian (AU)"),
            author = "",
            title = "French protester killed in accident at anti-fuel tax blockade",
            description = "Driver accelerated when panicked by ‘yellow vest’ demonstration in Savoie, minister says",
            url = "https://www.theguardian.com/world/2018/nov/17/" +
                "french-protester-killed-accident-anti-fuel-tax-blockade",
            imageUrl = "https://i.guim.co.uk/img/media/064945cbe81d630e66bc2b92a29412f97613b0a2/0_349_5239_3143/" +
                "master/5239.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft" +
                "&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctZGVmYXVsdC5wbmc" +
                "&s=488a1caf375d069b30d90bdbfe371a88",
            publishedAt = sdf.parse("2018-11-17T11:45:00Z"),
            content = "A motorist has accidentally hit and killed a… [+801 chars]"
        )

        assertEquals(expectedArticle0, response.articles[0])
        assertEquals(20, response.articles.size)
        assertEquals(20, response.totalResults)
    }

    @Test
    fun `sources are fetched successfully`() = runBlocking {
        // given that the request completes successfully
        server.setDispatcher(mockApi.successDispatcher)

        // when we call get sources
        val sources = apiService.getSources().await()

        // then verify that the source (as read from the json resource) correspond to the expected ones
        val response = sources.body()!!
        val expectedSource0 = Source(
            id = "abc-news",
            name = "ABC News",
            description = "Your trusted source for breaking news, analysis, exclusive interviews..ABCNews.com.",
            url = "https://abcnews.go.com",
            category = "general",
            language = "en",
            country = "us"
        )

        assertEquals(expectedSource0, response.sources[0])
        assertEquals(14, response.sources.size)
    }
}