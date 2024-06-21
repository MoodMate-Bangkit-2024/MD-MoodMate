package com.dicoding.moodmate.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.moodmate.R
import com.dicoding.moodmate.databinding.FragmentAccountBinding
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.welcome.WelcomeActivity

class AccountFragment : Fragment() {

    private val viewModel by viewModels<AccountViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.getSession().isLogin) {
            binding.username.text = viewModel.getSession().name
            binding.emailUser.text = viewModel.getSession().email
        }
        setupAction()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            ViewModelFactory.refresh()
            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
