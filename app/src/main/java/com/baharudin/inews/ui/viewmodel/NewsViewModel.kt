package com.baharudin.inews.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.inews.data.model.NewsResponse
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

    val searchNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var searchPage = 1

    init {
        getTopHeadline("id")
    }
    fun getTopHeadline(countryCode:String) = viewModelScope.launch {
        topHeadlines.postValue(Result.Loading())
        val response = newsRepository.getHeadlines(countryCode,topHeadlinePage)
        topHeadlines.postValue(handleTopHeadline(response))
    }

    fun getSearchNews(searchQuery:String) = viewModelScope.launch {
        searchNews.postValue(Result.Loading())
        val response = newsRepository.searchNews(searchQuery, searchPage)
        searchNews.postValue(handleSearchNews(response))

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