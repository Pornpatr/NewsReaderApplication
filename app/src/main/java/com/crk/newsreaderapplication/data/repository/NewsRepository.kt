package com.crk.newsreaderapplication.data.repository

import com.crk.newsreaderapplication.data.local.ArticleDatabase
import com.crk.newsreaderapplication.data.remote.RetrofitClient
import com.crk.newsreaderapplication.domain.model.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getNews(sources: String, pageNumber: Int) =
        RetrofitClient.api.getNews(sources, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().insert(article)
    suspend fun delete(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getAllArticles() = db.getArticleDao().getArticles()
}