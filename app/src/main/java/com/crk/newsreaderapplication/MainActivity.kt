package com.crk.newsreaderapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.crk.newsreaderapplication.data.local.ArticleDatabase
import com.crk.newsreaderapplication.data.repository.NewsRepository
import com.crk.newsreaderapplication.ui.viewmodel.NewsViewModel
import com.crk.newsreaderapplication.ui.viewmodel.NewsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvider)[NewsViewModel::class.java]

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        bottomNavigationView.setupWithNavController(navController)
    }

}
