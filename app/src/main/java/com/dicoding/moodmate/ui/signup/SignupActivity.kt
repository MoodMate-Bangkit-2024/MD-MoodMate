package com.dicoding.moodmate.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.moodmate.databinding.ActivitySignupBinding
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorResponse.observe(this) { errorResponse ->
            if (!errorResponse.error!!) {
                AlertDialog.Builder(this).apply {
                    setTitle("Berhasil!")
                    setMessage("Akun sudah jadi nih. Yuk, login dan berbagi cerita!.")
                    setPositiveButton("Lanjut") { _, _ ->
                        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                        finish()
                    }
                    create()
                    show()
                }

            } else if (errorResponse.message == "Email is already taken") {
                AlertDialog.Builder(this).apply {
                    setTitle("Gagal!")
                    setMessage("Email sudah terdaftar.")
                    setPositiveButton("Lanjut") { _, _ ->
                    }
                    create()
                    show()
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Gagal!")
                    setMessage("Terjadi kesalahan.")
                    setPositiveButton("Lanjut") { _, _ ->
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.register(name, email, password)
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(300)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }
}