package com.crk.newsreaderapplication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("source")
    var source: Source?,
    @SerializedName("urlToImage")
    var urlToImage: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("author")
    var author: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("publishedAt")
    var publishedAt: String? = null,
) : Serializable