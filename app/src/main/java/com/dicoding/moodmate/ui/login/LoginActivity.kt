package com.dicoding.moodmate.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.moodmate.MainActivity
import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.databinding.ActivityLoginBinding
import com.dicoding.moodmate.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getSession().isLogin) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.login(email, password)
        }

        viewModel.loginResponseLiveData.observe(this) { result ->
            if (result != null) {
                viewModel.saveSession(
                    UserModel(
                        email = binding.emailEditText.text.toString(),
                        token = result.loginResult?.token.toString(),
                        isLogin = true
                    )
                )
                val action = Intent(this, MainActivity::class.java)
                AlertDialog.Builder(this).apply {
                    setTitle("Yeay!")
                    setMessage("Login berhasil. Sudah tidak sabar berbagi cerita ya?")
                    setPositiveButton("Lanjut") { _, _ ->
                        startActivity(action)
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Gagal!")
                    .setMessage("Login gagal. Silahkan coba lagi.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        viewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(300)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }
}
