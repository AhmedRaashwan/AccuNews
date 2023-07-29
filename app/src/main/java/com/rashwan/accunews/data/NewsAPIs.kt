package com.rashwan.accunews.data

import com.rashwan.accunews.entities.NewsEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIs {

    @GET("top-headlines")
    suspend fun loadNews(
        @Query("apiKey") apikey: String? = null,
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,

        ): NewsEntity
}
