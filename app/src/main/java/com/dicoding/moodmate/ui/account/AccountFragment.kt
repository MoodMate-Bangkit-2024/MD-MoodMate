package com.dicoding.moodmate.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.moodmate.databinding.FragmentAccountBinding
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.welcome.WelcomeActivity

class AccountFragment : Fragment() {
    private val viewModel by viewModels<AccountViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finish()
            }
        }

        setupAction()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}
