package com.ilyko.giphy.features.main.search.data.api.response

import com.google.gson.annotations.SerializedName

data class GifResponse(
    @SerializedName("data")
    val gifObjects: List<GifObjectResponse>,
    val pagination: PaginationResponse
)

data class PaginationResponse(
    val offset: Int,
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int
)

data class GifObjectResponse(
    val type: String,
    val id: String,
    val url: String,
    val images: GifImageResponse
)

data class GifImageResponse(
    @SerializedName("fixed_height")
    val fixedHeight: GifPropertiesResponse
)

data class GifPropertiesResponse(
    val url: String
)


