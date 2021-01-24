package com.ilyko.giphy.features.main.search.data.repositories

import com.ilyko.giphy.core.base.data.BaseRepository
import com.ilyko.giphy.core.base.data.network.ErrorHandler
import com.ilyko.giphy.core.base.data.network.ResponseResult
import com.ilyko.giphy.core.base.data.network.mapResponseResult
import com.ilyko.giphy.core.library.dispatcher.DispatcherProvider
import com.ilyko.giphy.features.main.search.data.api.GiphyApi
import com.ilyko.giphy.features.main.search.data.api.request.SearchRequest
import com.ilyko.giphy.features.main.search.data.repositories.mapper.toModel
import com.ilyko.giphy.features.main.search.data.api.entity.SearchResult
import com.ilyko.giphy.features.main.search.domain.SearchRepository


class SearchRepositoryImpl(
    private val api: GiphyApi,
    errorHandler: ErrorHandler,
    dispatcherProvider: DispatcherProvider
) : BaseRepository(errorHandler, dispatcherProvider), SearchRepository {

    override suspend fun getGifList(searchRequest: SearchRequest): ResponseResult<SearchResult> {
        return execute { api.searchGif(query = searchRequest.query, limit = searchRequest.limit, offset = searchRequest.offset) }
            .mapResponseResult { response ->
                response.toModel().let {
                    ResponseResult.Success(it)
                }
            }
    }
}
