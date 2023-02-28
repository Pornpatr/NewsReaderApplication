package com.crk.newsreaderapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crk.newsreaderapplication.domain.model.Article


@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDAO

    companion object {
        @Volatile
        private var articleDbInstance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = articleDbInstance ?: synchronized(LOCK) {
            articleDbInstance ?: createDatabaseInstance(context).also {
                articleDbInstance = it
            }
        }

        private fun createDatabaseInstance(context: Context) =
            Room.databaseBuilder(
                context, ArticleDatabase::class.java,
                "article.db"
            ).fallbackToDestructiveMigration().build()

    }
}