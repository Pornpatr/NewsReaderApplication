package com.crk.newsreaderapplication.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crk.newsreaderapplication.R
import com.crk.newsreaderapplication.domain.model.Article
import java.text.SimpleDateFormat
import java.util.*


fun shareNews(context: Context?, article: Article) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, article.urlToImage)
        putExtra(Intent.EXTRA_STREAM, article.urlToImage)
        putExtra(Intent.EXTRA_TITLE, article.title)
        type = "image/*"
    }
    context?.startActivities(arrayOf(Intent.createChooser(intent, "Share New On")))
}

fun getCircularDrawable(context: Context): CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 56f
        setTint(context.resources.getColor(R.color.purple_500))
        start()
    }
}

fun ImageView.loadImage(url: String, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        imageView.loadImage(url!!, getCircularDrawable(imageView.context))

    }
}

@BindingAdapter("app:dateFormatted")
fun setDateFormatted(view: TextView, dateString: String?) {
    if (!dateString.isNullOrEmpty()) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
//        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(date)
        view.text = formattedDate
    }
}




