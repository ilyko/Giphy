package com.ilyko.giphy.features.main.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ilyko.giphy.core.base.data.network.ResponseResult
import com.ilyko.giphy.features.main.search.data.api.entity.GifImage
import com.ilyko.giphy.features.main.search.data.api.entity.GifObject
import com.ilyko.giphy.features.main.search.data.api.entity.GifProperties
import com.ilyko.giphy.features.main.search.data.api.entity.SearchResult
import com.ilyko.giphy.domain.entity.error.NetworkErrorEntity
import com.ilyko.giphy.features.main.search.domain.SearchInteractor
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.internal.toImmutableList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @MockK
    lateinit var searchInteractor: SearchInteractor

    lateinit var viewModel: SearchViewModel

    val dispatcher = TestCoroutineDispatcher()


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        viewModel = SearchViewModel(searchInteractor)

    }


    @Test
    fun submitRequest_verifyCall() {
        val searchResult = SearchResult(
            gifObjects = listOf(
                GifObject(
                    "GIF",
                    "qwerty",
                    "urll",
                    GifImage(GifProperties("url"))
                )
            )
        )
        coEvery { searchInteractor.search(any()) } returns ResponseResult.Success(searchResult)
        viewModel.submitRequest("qqq")
        coVerify(exactly = 1) { searchInteractor.search(any()) }
    }

    @Test
    fun submitRequest_success(){
        val searchResult = SearchResult(
            gifObjects = listOf(
                GifObject(
                    "GIF",
                    "qwerty",
                    "urll",
                    GifImage(GifProperties("url"))
                )
            )
        )
        coEvery { searchInteractor.search(any()) } returns ResponseResult.Success(searchResult)
        viewModel.submitRequest("qqq")

        assert(viewModel.viewState.searchResult?.toImmutableList() == searchResult.gifObjects)
        assert(viewModel.eventsQueue.value?.size == null)
    }

    @Test
    fun submitRequest_failed(){
        coEvery { searchInteractor.search(any()) } returns ResponseResult.Error(NetworkErrorEntity.NotFoundError)
        viewModel.submitRequest("qqq")
        assert(viewModel.viewState.searchResult?.size == 0)
        assert(viewModel.eventsQueue.value?.size == 1)
    }

}