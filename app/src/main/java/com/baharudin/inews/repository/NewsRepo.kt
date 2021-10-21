package com.baharudin.inews.repository

import com.baharudin.inews.data.model.Article
import com.baharudin.inews.data.model.NewsResponse
import com.baharudin.inews.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepo {
    suspend fun getHeadlines(countryCode : String, pageNumber : Int) : Response<NewsResponse>
    suspend fun searchNews(searchQuery : String, pageNumber: Int)
    suspend fun insertBookmark(article: Article) : Result<String>
    suspend fun getAllBookmark() : Flow<List<Article>>
    suspend fun deleteNews(article: Article)
}