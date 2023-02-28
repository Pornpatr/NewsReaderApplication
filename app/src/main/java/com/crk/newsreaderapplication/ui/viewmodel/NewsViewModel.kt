package com.crk.newsreaderapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.crk.newsreaderapplication.data.repository.NewsRepository
import com.crk.newsreaderapplication.domain.model.Article
import com.crk.newsreaderapplication.domain.model.NewResponse
import com.crk.newsreaderapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
    val News: MutableLiveData<Resource<NewResponse>> = MutableLiveData()
    var pageNumber = 1
    var NewResponse: NewResponse? = null

    lateinit var articles: LiveData<PagedList<Article>>

    init {
        getNews("abc-news")

    }

    private fun getNews(sources: String) = viewModelScope.launch {
        News.postValue(Resource.Loading())
        val response = newsRepository.getNews(sources, pageNumber)
        News.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<NewResponse>): Resource<NewResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                pageNumber++
                if (NewResponse == null) {
                    NewResponse = resultResponse
                } else {
                    val oldArticle = NewResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(NewResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }

    fun getSavedArticles() = newsRepository.getAllArticles()

    fun getNews(): LiveData<PagedList<Article>> {
        return articles
    }
}
