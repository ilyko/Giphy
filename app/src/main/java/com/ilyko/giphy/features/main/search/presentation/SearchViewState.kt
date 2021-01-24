package com.ilyko.giphy.features.main.search.presentation

import androidx.paging.PagedList
import com.ilyko.giphy.features.main.search.data.api.entity.GifObject

data class SearchViewState(
    val searchResult: PagedList<GifObject>?
)
