package com.baharudin.inews.repository

import com.baharudin.inews.data.local.dao.NewsDao
import com.baharudin.inews.data.model.Article
import com.baharudin.inews.data.model.NewsResponse
import com.baharudin.inews.data.remote.api.NewsApi
import com.baharudin.inews.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepoImplementation @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
) : NewsRepo {

    override suspend fun getHeadlines(countryCode: String, pageNumber: Int) : Response<NewsResponse> =
        newsApi.getAllHeadlines(countryCode, pageNumber)

    override suspend fun searchNews(searchQuery: String, pageNumber: Int)  : Response<NewsResponse> =
        newsApi.searchNews(searchQuery, pageNumber)


    override suspend fun insertBookmark(article: Article): Result<String> {
        return try {
            newsDao.insertBookmark(article)
            Result.Sucess("News Saved in bookmark")
        }catch (e : Exception) {
            e.printStackTrace()
            Result.Error("Something wrong ")
        }
    }

    override  fun getAllBookmark(): Flow<List<Article>> = newsDao.getAllBookmark()

    override suspend fun deleteNews(article: Article) {
        try {
            newsDao.deleteBookmark(article)
            return
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }

}