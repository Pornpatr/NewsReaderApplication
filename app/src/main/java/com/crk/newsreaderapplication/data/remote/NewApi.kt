package com.crk.newsreaderapplication.data.remote


import com.crk.newsreaderapplication.domain.model.NewResponse
import com.crk.newsreaderapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewApi {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("sources") sources: String = "abc-news",
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewResponse>
}