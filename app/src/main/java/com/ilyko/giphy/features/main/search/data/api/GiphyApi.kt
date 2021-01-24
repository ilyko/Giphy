package com.ilyko.giphy.features.main.search.data.api

import com.ilyko.giphy.BuildConfig.API_KEY
import com.ilyko.giphy.features.main.search.data.api.response.GifResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("gifs/search")
    suspend fun searchGif(@Query("api_key") apiKey: String = API_KEY,
                          @Query("q") query: String? = null,
                          @Query("limit") limit: Int? = null,
                          @Query("offset") offset: Int? = null
    ): Response<GifResponse>
}
