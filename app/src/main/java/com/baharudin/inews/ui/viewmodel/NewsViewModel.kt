package com.baharudin.inews.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.inews.data.model.headline.Article
import com.baharudin.inews.data.model.headline.NewsResponse
import com.baharudin.inews.repository.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import com.baharudin.inews.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository : NewsRepo
) :ViewModel(){

    val topHeadlines : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    private var topHeadlinePage = 1

    val sourceNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    private var sourcePage = 1

    val getBusiness : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var category = "business"

    val getScience : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    private var categoryScience = "science"

    val getSportCategory : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    private var categorySport = "sports"

    val getEntertainmentCategory : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    private var categoryEntertainment = "entertainment"

    val getHealthCategory : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    private var categoryHealth = "health"

    val getTechCategory : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    private var categoryTech = "technology"

    val searchNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var searchPage = 1

    val newsBookmark = newsRepository.getAllBookmark()

    init {
        getTopHeadline("id")
        getSource("us")
        getBusinessCategory("id")
        getScienceCategory("id")
        getSportCategory("id")
        getEntertainmentCategory("id")
        getHealthCategory("id")
        getTechCategory("id")
    }

    private fun getTopHeadline(countryCode:String) = viewModelScope.launch {
        topHeadlines.postValue(Result.Loading())
        val response = newsRepository.getHeadlines(countryCode,topHeadlinePage)
        topHeadlines.postValue(handleTopHeadline(response))
    }

    private fun getSource(countryCode: String) = viewModelScope.launch {
        sourceNews.postValue(Result.Loading())
        val response = newsRepository.getSource(countryCode, sourcePage)
        sourceNews.postValue(handleSource(response))
    }

    fun getBusinessCategory(countryCode: String ) = viewModelScope.launch {
        getBusiness.postValue(Result.Loading())
        val response = newsRepository.getBusinessCategory(countryCode, category)
        getBusiness.postValue(handleBusiness(response))
    }

    fun getScienceCategory(countryCode: String) =  viewModelScope.launch {
        getScience.postValue(Result.Loading())
        val response = newsRepository.getScienceCategory(countryCode, categoryScience)
        getScience.postValue(handleScienceCategory(response))
    }

    fun getSportCategory(countryCode: String) = viewModelScope.launch {
        getSportCategory.postValue(Result.Loading())
        val response = newsRepository.getSportCategory(countryCode, categorySport)
        getSportCategory.postValue(handleSportCategory(response))
    }

    fun getEntertainmentCategory(countryCode: String) = viewModelScope.launch {
        getEntertainmentCategory.postValue(Result.Loading())
        val response = newsRepository.getEntertainmentCategory(countryCode, categoryEntertainment)
        getEntertainmentCategory.postValue(handleEntertainment(response))
    }

    fun getHealthCategory(countryCode: String) = viewModelScope.launch {
        getHealthCategory.postValue(Result.Loading())
        val response = newsRepository.getHealthCategory(countryCode, categoryHealth)
        getHealthCategory.postValue(handleHealth(response))
    }

    fun getTechCategory(countryCode: String) = viewModelScope.launch {
        getTechCategory.postValue(Result.Loading())
        val response = newsRepository.getTechCategory(countryCode, categoryTech)
        getTechCategory.postValue(handleTech(response))
    }



    //---------HANDLE RESPONSE ----------------------//


    private fun handleTech(response: Response<NewsResponse>): Result<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }

    private fun handleHealth(response: Response<NewsResponse>): Result<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }

    private fun handleEntertainment(response: Response<NewsResponse>): Result<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }

    private fun handleSportCategory(response: Response<NewsResponse>): Result<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }

    private fun handleScienceCategory(response: Response<NewsResponse>): Result<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
    }


    private fun handleBusiness(response: Response<NewsResponse>) : Result<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Sucess(result)
            }
        }
        return Result.Error(response.message())
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

    fun saveBookmark(article: Article) = viewModelScope.launch {
        newsRepository.insertBookmark(article)
    }
}