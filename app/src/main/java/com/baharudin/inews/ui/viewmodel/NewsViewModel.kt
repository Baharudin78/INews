package com.baharudin.inews.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.inews.data.model.headline.Article
import com.baharudin.inews.data.model.headline.NewsResponse
import com.baharudin.inews.repository.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import com.baharudin.inews.utils.Result
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsRepository : NewsRepo
) :ViewModel(){

    val topHeadlines : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var topHeadlinePage = 1

    val sourceNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var sourcePage = 1

    val searchNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var searchPage = 1

    init {
        getTopHeadline("id")
        getSource("us")
    }
    fun getTopHeadline(countryCode:String) = viewModelScope.launch {
        topHeadlines.postValue(Result.Loading())
        val response = newsRepository.getHeadlines(countryCode,topHeadlinePage)
        topHeadlines.postValue(handleTopHeadline(response))
    }
    fun getSource(countryCode: String) = viewModelScope.launch {
        sourceNews.postValue(Result.Loading())
        val response = newsRepository.getSource(countryCode, sourcePage)
        sourceNews.postValue(handleSource(response))
    }

    private fun handleSource(response: Response<NewsResponse>): Result<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }

    fun getSearchNews(searchQuery:String) = viewModelScope.launch {
        searchNews.postValue(Result.Loading())
        val response = newsRepository.searchNews(searchQuery, searchPage)
        searchNews.postValue(handleSearchNews(response))

    }
    fun insertBookmark(articles : Article) = viewModelScope.launch {
        newsRepository.insertBookmark(articles)
    }
    fun getAllBookmark() = newsRepository.getAllBookmark()
    fun deleteBookmark(article: Article) = viewModelScope.launch {
        newsRepository.deleteNews(article)
    }
    private fun handleTopHeadline(response : Response<NewsResponse>) : Result<NewsResponse> {
        if (response.isSuccessful) {
            response.body().let { result ->
               return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }
    private fun handleSearchNews(response: Response<NewsResponse>) : Result<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }
}