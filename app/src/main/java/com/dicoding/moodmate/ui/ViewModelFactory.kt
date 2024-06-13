package com.dicoding.moodmate.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.moodmate.data.repository.UserRepository
import com.dicoding.moodmate.data.di.Injection
import com.dicoding.moodmate.ui.account.AccountViewModel
import com.dicoding.moodmate.ui.login.LoginViewModel
import com.dicoding.moodmate.ui.signup.SignupViewModel
import com.dicoding.moodmate.ui.welcome.WelcomeViewModel
import com.dicoding.moodmate.MainViewModel
import com.dicoding.moodmate.ui.explore.article.ArticleViewModel
import com.dicoding.moodmate.ui.home.HomeViewModel

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }

        fun refresh() {
            INSTANCE = null
            Injection.refreshRepository()
        }
    }
}
