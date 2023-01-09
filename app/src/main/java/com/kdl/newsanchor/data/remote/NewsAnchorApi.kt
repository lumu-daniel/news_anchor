package com.kdl.newsanchor.data.remote

import com.kdl.newsanchor.BuildConfig
import com.kdl.newsanchor.data.remote.dtos.NewsItemDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAnchorApi {

    @GET("v2/everything")
    suspend fun getNewsItems(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): NewsItemDTO


}
