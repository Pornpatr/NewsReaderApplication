package com.crk.newsreaderapplication.data.repository.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.crk.newsreaderapplication.domain.model.Article
import kotlinx.coroutines.CoroutineScope


class ArticleDataSourceFactory(private val scope: CoroutineScope) :
    DataSource.Factory<Int, Article>() {
    val articleDataSourceLiveData = MutableLiveData<ArticleDataSource>()

    override fun create(): DataSource<Int, Article> {
        val newArticleDataSource = ArticleDataSource(scope)
        articleDataSourceLiveData.postValue(newArticleDataSource)
        return newArticleDataSource
    }

}