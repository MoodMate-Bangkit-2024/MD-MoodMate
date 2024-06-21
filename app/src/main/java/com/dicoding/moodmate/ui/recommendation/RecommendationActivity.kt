package com.dicoding.moodmate.ui.recommendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.moodmate.R
import com.dicoding.moodmate.databinding.ActivityRecommendationBinding
import com.dicoding.moodmate.ui.explore.article.ArticleActivity
import com.dicoding.moodmate.ui.explore.movie.MovieActivity
import com.dicoding.moodmate.ui.recommendation.articleRec.ArticleRecActivity
import com.dicoding.moodmate.ui.recommendation.movieRec.MovieRecActivity
import com.dicoding.moodmate.ui.recommendation.music.MusicActivity

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val type = intent.getStringExtra("TYPE")?:""

        binding.music.setOnClickListener(){
            val intent = Intent(this, MusicActivity::class.java)
            intent.putExtra("TYPE", type)
            startActivity(intent)
        }
        binding.movie.setOnClickListener(){
            val intent = Intent(this, MovieRecActivity::class.java)
            intent.putExtra("TYPE", type)
            startActivity(intent)
        }
        binding.article.setOnClickListener(){
            val intent = Intent(this, ArticleRecActivity::class.java)
            intent.putExtra("TYPE", type)
            startActivity(intent)
        }
    }
}