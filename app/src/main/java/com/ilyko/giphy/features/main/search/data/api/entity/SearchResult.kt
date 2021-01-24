package com.ilyko.giphy.features.main.search.data.api.entity

data class SearchResult(
    val pagination: Pagination? = null,
    val gifObjects: List<GifObject> = listOf()
)

data class Pagination(
    val offset: Int,
    val totalCount: Int,
    val count: Int
)

data class GifObject(
    val type: String,
    val id: String,
    val url: String,
    val images: GifImage
)

data class GifImage(
    val fixedHeight: GifProperties
)

data class GifProperties(
    val url: String
)
