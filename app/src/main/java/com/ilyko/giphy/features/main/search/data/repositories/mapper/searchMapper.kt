package com.ilyko.giphy.features.main.search.data.repositories.mapper

import com.ilyko.giphy.features.main.search.data.api.response.*
import com.ilyko.giphy.features.main.search.data.api.entity.*

internal fun GifResponse.toModel(): SearchResult {
    return SearchResult(
        pagination = pagination.toModel(),
        gifObjects = gifObjects.map { it.toModel() }
    )
}

internal fun PaginationResponse.toModel(): Pagination {
    return Pagination(offset, totalCount, count)
}

internal fun GifObjectResponse.toModel(): GifObject {
    return GifObject(type, id, url, images.toModel())
}

internal fun GifImageResponse.toModel(): GifImage {
    return GifImage(fixedHeight.toModel())
}

internal fun GifPropertiesResponse.toModel(): GifProperties {
    return GifProperties(url)
}