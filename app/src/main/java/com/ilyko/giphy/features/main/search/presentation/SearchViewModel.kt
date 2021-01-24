package com.ilyko.giphy.features.main.search.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.ilyko.giphy.core.base.data.network.data
import com.ilyko.giphy.core.base.data.network.errorEntity
import com.ilyko.giphy.core.base.data.network.isSuccess
import com.ilyko.giphy.core.base.presentation.view_model.BaseViewModel
import com.ilyko.giphy.core.library.live_data.delegate
import com.ilyko.giphy.core.library.live_data.mapDistinct
import com.ilyko.giphy.features.main.search.data.api.request.SearchRequest
import com.ilyko.giphy.features.main.search.data.api.entity.GifObject
import com.ilyko.giphy.features.main.search.domain.SearchInteractor
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())
    var viewState: SearchViewState by liveState.delegate()

    val searchResult: LiveData<PagedList<GifObject>?> = liveState.mapDistinct {
        it.searchResult
    }


    fun submitRequest(query: String?) {
        val model = SearchRequest(
            query = query
        )
        viewModelScope.launch {
            val result = initDataSource(model)
            viewState = viewState.copy(searchResult = result)
        }
    }

    private fun createInitialViewState(): SearchViewState {
        return SearchViewState(
            searchResult = null
        )
    }

    private fun initDataSource(searchRequest: SearchRequest): PagedList<GifObject> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(searchRequest.limit ?: PAGE_SIZE)
            .build()

        val dataSource = GifDataSource(
            interactor = searchInteractor,
            searchRequest = searchRequest
        )
        val executor = ArchTaskExecutor.getMainThreadExecutor()
        return PagedList.Builder(dataSource, config)
            .setFetchExecutor(executor)
            .setNotifyExecutor(executor)
            .build()
    }

    inner class GifDataSource(
        private val interactor: SearchInteractor,
        private val searchRequest: SearchRequest
    ) : PageKeyedDataSource<Int, GifObject>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, GifObject>
        ) {
            viewModelScope.launch {
                try {
                    val gifs = interactor.search(searchRequest)

                    if (gifs.isSuccess()) {
                        callback.onResult(gifs.data().gifObjects, null, 2)
                    } else {
                        offerBaseError(gifs.errorEntity())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GifObject>) {
            // do nothing
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GifObject>) {
            viewModelScope.launch {
                try {
                    val offset = params.key * (searchRequest.limit ?: PAGE_SIZE)

                    val gifs =
                        interactor.search(searchRequest.copy(offset = offset))

                    if (gifs.isSuccess()) {
                        callback.onResult(gifs.data().gifObjects, params.key + 1)
                    } else {
                        offerBaseError(gifs.errorEntity())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 25
    }
}
