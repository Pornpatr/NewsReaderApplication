package com.crk.newsreaderapplication.domain.model

import com.google.gson.annotations.SerializedName

data class NewResponse(
    @SerializedName("articles")
    var articles: MutableList<Article>,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResult: Int?
)