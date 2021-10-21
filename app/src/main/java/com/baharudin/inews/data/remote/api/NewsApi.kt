package com.baharudin.inews.data.remote.api

import com.baharudin.inews.data.model.NewsResponse
import com.baharudin.inews.utils.Constant.API_KEY
import com.baharudin.inews.utils.Constant.API_VERSION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("$API_VERSION/top-headlines")
    suspend fun getAllHeadlines(
        @Query("country")
        countryCode : String = "id",
        @Query("page")
        pageNumber : Int = 1,
        @Query("apiKey")
        apiKey : String = API_KEY
    ) : Response<NewsResponse>
    @GET("$API_VERSION/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery :String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ) : Response<NewsResponse>
}