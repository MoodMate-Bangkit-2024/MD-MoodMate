package com.dicoding.moodmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.moodmate.databinding.ActivityMainBinding
import com.dicoding.moodmate.ui.account.AccountFragment
import com.dicoding.moodmate.ui.chat.ChatFragment
import com.dicoding.moodmate.ui.explore.ExploreFragment
import com.dicoding.moodmate.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()

        binding.navMenu.setOnItemSelectedListener{
            when(it.itemId){
                R.id.navigation_home -> changeFragment(HomeFragment())

                R.id.navigation_chat -> changeFragment(ChatFragment())

                R.id.navigation_explore -> changeFragment(ExploreFragment())

                R.id.navigation_account -> changeFragment(AccountFragment())
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment){
        val transactionManager = supportFragmentManager
        val transactionFragment = transactionManager.beginTransaction()

        transactionFragment.replace(R.id.fragment_container, fragment)
        transactionFragment.commit()
    }
}