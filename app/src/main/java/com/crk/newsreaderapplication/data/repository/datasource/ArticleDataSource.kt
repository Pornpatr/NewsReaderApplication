package com.crk.newsreaderapplication.data.repository.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.crk.newsreaderapplication.data.remote.RetrofitClient
import com.crk.newsreaderapplication.domain.model.Article
import com.crk.newsreaderapplication.domain.model.NewResponse
import com.crk.newsreaderapplication.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ArticleDataSource(val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>() {
    val News: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val pageNumber = 1
    val NewResponse: NewResponse? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getNews(
                    "abc-news",
                    1,
                    Constants.API_KEY
                )
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            News.postValue(it)
                            callback.onResult(it, null, 2)
                        }
                    }
                }

            } catch (exception: Exception) {
                Log.e("DataSource ::", exception.message.toString())
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try {
            scope.launch {
                val response =
                    RetrofitClient.api.getNews(
                        "abc-news",
                        params.requestedLoadSize,
                        Constants.API_KEY
                    )
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            callback.onResult(it, params.key + 1)

                        }
                    }
                }
            }

        } catch (exception: Exception) {
            Log.e("DataSource ::", exception.message.toString())

        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        TODO("Not yet implemented")
    }

}

