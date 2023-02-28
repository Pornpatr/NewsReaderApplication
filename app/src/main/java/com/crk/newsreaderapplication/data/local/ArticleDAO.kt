package com.crk.newsreaderapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.crk.newsreaderapplication.domain.model.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}