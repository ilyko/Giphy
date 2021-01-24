package com.ilyko.giphy.features.main.search.data.api.request

data class SearchRequest(
    val query: String? = null,
    val limit: Int? = null,
    val offset: Int = 0
)