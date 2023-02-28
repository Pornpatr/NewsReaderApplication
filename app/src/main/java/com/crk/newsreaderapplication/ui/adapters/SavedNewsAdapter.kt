package com.crk.newsreaderapplication.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crk.newsreaderapplication.R
import com.crk.newsreaderapplication.databinding.ItemSavedNewsBinding
import com.crk.newsreaderapplication.domain.model.Article

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {


    private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

    }


    inner class SavedNewsViewHolder(var view: ItemSavedNewsBinding) :
        RecyclerView.ViewHolder(view.root)

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemSavedNewsBinding>(
            inflater,
            R.layout.item_saved_news,
            parent,
            false
        )
        return SavedNewsViewHolder(view)

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.view.article = article
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                article.let { article ->
                    it(article)
                }
            }
        }
        holder.view.ivDelete.setOnClickListener {
            onArticleDeleteClick?.let {
                if (article != null) {
                    it(article)
                }
            }
        }


    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null
    private var onArticleDeleteClick: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    fun onDeleteClickListener(listener: (Article) -> Unit) {
        onArticleDeleteClick = listener
    }


}