package com.crk.newsreaderapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crk.newsreaderapplication.MainActivity
import com.crk.newsreaderapplication.R
import com.crk.newsreaderapplication.ui.adapters.ArticleAdapter
import com.crk.newsreaderapplication.ui.viewmodel.NewsViewModel
import com.crk.newsreaderapplication.utils.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_news.*
import kotlin.random.Random

class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: ArticleAdapter
    val TAG = "Newsfragment"

    private fun setupRecyclerView() {
        newsAdapter = ArticleAdapter()
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_NewsFragment_to_articleFragment,
                bundle
            )
        }
        newsAdapter.onSaveClickListener {
            if (it.id == null) {
                it.id = Random.nextInt(0, 1000);
            }
            viewModel.insertArticle(it)
            Snackbar.make(requireView(), "Saved", Snackbar.LENGTH_SHORT).show()
        }
        newsAdapter.onDeleteClickListener {
            if (it.id == null) {
                it.id = Random.nextInt(0, 1000);
            }
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(), "Removed", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        viewModel.News.observe(viewLifecycleOwner, Observer { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.data?.let { news ->
                        rvNews.visibility = View.VISIBLE
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.message?.let { message ->
                        Log.e(TAG, "Error :: $message")
                    }
                }
                is Resource.Loading -> {
                    shimmerFrameLayout.startShimmer()
                }
            }
        })
    }
}