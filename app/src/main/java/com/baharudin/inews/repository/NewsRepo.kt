package com.baharudin.inews.repository

import com.baharudin.inews.data.model.headline.Article
import com.baharudin.inews.data.model.headline.NewsResponse
import com.baharudin.inews.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepo {
    //----REMOTE-----//
    suspend fun getHeadlines(countryCode : String, pageNumber : Int) : Response<NewsResponse>
    suspend fun getSource(countryCode: String, pageNumber: Int) : Response<NewsResponse>
    suspend fun searchNews(searchQuery : String, pageNumber: Int) : Response<NewsResponse>
    suspend fun getBusinessCategory(countryCode: String, category : String) : Response<NewsResponse>
    suspend fun getEntertainmentCategory(countryCode: String, category : String) : Response<NewsResponse>
    suspend fun getHealthCategory(countryCode: String, category : String) : Response<NewsResponse>
    suspend fun getScienceCategory(countryCode: String, category : String) : Response<NewsResponse>
    suspend fun getSportCategory(countryCode: String, category : String) : Response<NewsResponse>
    suspend fun getTechCategory(countryCode: String, category : String) : Response<NewsResponse>


    //----LOCAL----//
    suspend fun insertBookmark(article: Article) : Result<String>
    fun getAllBookmark() : Flow<List<Article>>
    suspend fun deleteNews(article: Article)
}