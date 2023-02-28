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
import com.crk.newsreaderapplication.domain.model.Article
import com.crk.newsreaderapplication.ui.adapters.SavedNewsAdapter
import com.crk.newsreaderapplication.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlin.random.Random


class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: SavedNewsAdapter
    val TAG = "SavedNewsFragment"
    var deletedArticle: Article? = null


    private fun setupRecyclerView() {
        newsAdapter = SavedNewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
        newsAdapter.onDeleteClickListener { article ->
            if (article.id == null) {
                article.id = Random.nextInt(0, 1000)
            }
            deletedArticle = article
            viewModel.deleteArticle(article)
            Snackbar.make(requireView(), "Removed Successfully", Snackbar.LENGTH_SHORT).apply {
                setAction("undo") {
                    deletedArticle?.let {
                        viewModel.insertArticle(it)
                    }
                }
                show()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        viewModel = (activity as MainActivity).viewModel
        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "" + it.size)
            newsAdapter.differ.submitList(it)
            rvSavedNews.visibility = View.VISIBLE
            shimmerFrameLayout2.stopShimmer()
            shimmerFrameLayout2.visibility = View.GONE
        })
    }

}