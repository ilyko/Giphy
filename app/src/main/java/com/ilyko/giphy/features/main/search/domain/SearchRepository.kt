package com.ilyko.giphy.features.main.search.domain

import com.ilyko.giphy.core.base.data.network.ResponseResult
import com.ilyko.giphy.features.main.search.data.api.request.SearchRequest
import com.ilyko.giphy.features.main.search.data.api.entity.SearchResult

interface SearchRepository {
    suspend fun getGifList(searchRequest: SearchRequest): ResponseResult<SearchResult>
}
