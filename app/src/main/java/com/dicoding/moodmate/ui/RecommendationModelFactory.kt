package com.dicoding.moodmate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.moodmate.data.di.RecommendationInjection
import com.dicoding.moodmate.data.repository.RecommendationRepository
import com.dicoding.moodmate.ui.recommendation.articleRec.ArticleRecViewModel
import com.dicoding.moodmate.ui.recommendation.movieRec.MovieRecViewModel
import com.dicoding.moodmate.ui.recommendation.music.MusicViewModel


class RecommendationModelFactory(private val repository: RecommendationRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ArticleRecViewModel::class.java) -> {
                ArticleRecViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MusicViewModel::class.java) -> {
                MusicViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MovieRecViewModel::class.java) -> {
                MovieRecViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RecommendationModelFactory? = null

        @JvmStatic
        fun getInstance(): RecommendationModelFactory {
            if (INSTANCE == null) {
                synchronized(RecommendationModelFactory::class.java) {
                    INSTANCE =
                        RecommendationModelFactory(RecommendationInjection.provideRepository())
                }
            }
            return INSTANCE as RecommendationModelFactory
        }
    }
}