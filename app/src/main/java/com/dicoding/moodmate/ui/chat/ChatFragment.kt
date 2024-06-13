package com.dicoding.moodmate.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSendButton()
    }

    private fun setupRecyclerView() {
        val adapter = ChatAdapter()
        binding.rvChat.adapter = adapter
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())

        chatViewModel.chatMessages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
            binding.rvChat.scrollToPosition(messages.size - 1)
        }
    }

    private fun setupSendButton() {
        binding.btnSend.setOnClickListener {
            val message = binding.MessageEditText.text.toString()
            if (message.isNotBlank()) {
                chatViewModel.sendMessage(message)
                binding.MessageEditText.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
