package com.ilyko.giphy.features.main.search.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ilyko.giphy.core.base.data.network.AppErrorHandler
import com.ilyko.giphy.core.base.data.network.data
import com.ilyko.giphy.core.base.data.network.errorEntity
import com.ilyko.giphy.core.library.dispatcher.DispatcherProviderImpl
import com.ilyko.giphy.features.main.search.data.api.GiphyApi
import com.ilyko.giphy.features.main.search.data.api.request.SearchRequest
import com.ilyko.giphy.features.main.search.data.api.response.*
import com.ilyko.giphy.features.main.search.data.api.entity.GifImage
import com.ilyko.giphy.features.main.search.data.api.entity.GifObject
import com.ilyko.giphy.features.main.search.data.api.entity.GifProperties
import com.ilyko.giphy.features.main.search.data.api.entity.SearchResult
import com.ilyko.giphy.domain.entity.error.NetworkErrorEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchRepositoryImplTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var api: GiphyApi

    private lateinit var searchRepositoryImpl: SearchRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchRepositoryImpl = SearchRepositoryImpl(
            api,
            AppErrorHandler(),
            DispatcherProviderImpl()
        )
    }

    @Test
    fun submitRequest_Success() {
        runBlocking {
            val list = SearchResult(
                gifObjects = listOf(
                    GifObject(
                        "GIF",
                        "qwerty",
                        "urll",
                        GifImage(GifProperties("url"))
                    )
                )
            )
            val apiResult = GifResponse(
                gifObjects = listOf(
                    GifObjectResponse(
                        "GIF",
                        "qwerty",
                        "urll",
                        GifImageResponse(GifPropertiesResponse("url"))
                    )
                ),
                pagination = PaginationResponse(offset = 0, totalCount = 1, count = 1)
            )
            val apiResponse = Response.success(apiResult)
            val searchRequest = SearchRequest("qqq")

            coEvery {
                api.searchGif(
                    query = searchRequest.query,
                    limit = searchRequest.limit,
                    offset = searchRequest.offset
                )
            } returns apiResponse
            val result = searchRepositoryImpl.getGifList(searchRequest)
            assert(list.gifObjects == result.data().gifObjects)
        }
    }

    @Test
    fun submitRequest_Error() {
        runBlocking {
            val errorApiResponse = Response.error<GifResponse>(404, "error".toResponseBody())
            val searchRequest = SearchRequest("qqq")
            coEvery { api.searchGif(
                query = searchRequest.query,
                limit = searchRequest.limit,
                offset = searchRequest.offset)
            } returns errorApiResponse
            val result = searchRepositoryImpl.getGifList(searchRequest)
            assert(result.errorEntity() == NetworkErrorEntity.NotFoundError)
        }
    }
}
