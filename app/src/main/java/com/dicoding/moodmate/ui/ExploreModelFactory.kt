package com.dicoding.moodmate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.moodmate.data.di.ExploreInjection
import com.dicoding.moodmate.data.repository.ExploreRepository
import com.dicoding.moodmate.ui.explore.article.ArticleViewModel
import com.dicoding.moodmate.ui.explore.movie.MovieViewModel
import com.dicoding.moodmate.ui.explore.video.VideoViewModel

class ExploreModelFactory(private val repository: ExploreRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                ArticleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(VideoViewModel::class.java) -> {
                VideoViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ExploreModelFactory? = null
        @JvmStatic
        fun getInstance(): ExploreModelFactory {
            if (INSTANCE == null) {
                synchronized(ExploreModelFactory::class.java) {
                    INSTANCE = ExploreModelFactory(ExploreInjection.provideRepository())
                }
            }
            return INSTANCE as ExploreModelFactory
        }
    }
}
