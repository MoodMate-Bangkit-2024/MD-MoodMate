package com.dicoding.moodmate.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.data.pref.ChatModel
import com.dicoding.moodmate.databinding.FragmentChatBinding
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.data.util.Result

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var chatAdapter: ChatAdapter
    private var author: String? = null
    private var currentUserToken: String? = null
    private var currentUserId: String? = null

    private val listDemo = mutableListOf<ChatModel>(

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        author = arguments?.getString("author")
        author?.let {
            viewModel.getChatRoom(it)
            observeCurrentUser()
        }

        setupMessageEditTextListener()
        observeSendMessageResult()
        observeChatMessages()
    }

    private fun observeCurrentUser() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            currentUserId = user.userId
            currentUserToken = user.token
            Log.d("ChatFragment", "Current user token: $currentUserToken, ID: $currentUserId")
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(currentUserId ?: "")
        binding.rvChat.adapter = chatAdapter
        binding.rvChat.layoutManager = LinearLayoutManager(context)
    }

    private fun setupMessageEditTextListener() {
        binding.MessageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnSend.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSend.setOnClickListener {
            val message = binding.MessageEditText.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.MessageEditText.text?.clear()
            }
        }
    }

    private fun sendMessage(message: String) {
        viewModel.sendMessage(message)
        chatAdapter.setData(listDemo)
    }

    private fun observeSendMessageResult() {
        viewModel.sendMessageResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Tampilkan indikator loading (jika diperlukan)
                }
                is Result.Success -> {
                    val data = result.data.data
                    val chatUser = ChatModel("SEND", data?.prompt ?:"")
                    val chatBot = ChatModel("RECEIVE", data?.moodmateResponse ?:"")
                    listDemo.add(chatUser)
                    listDemo.add(chatBot)
                    author?.let { viewModel.reloadChatRoom(it) }
                    chatAdapter.setData(listDemo)
                    binding.rvChat.smoothScrollToPosition(listDemo.size - 1)
                }
                is Result.Error -> {
                    Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeChatMessages() {
        viewModel.chatRoom.observe(viewLifecycleOwner) { messages ->
            messages?.let {
                chatAdapter.setData(listDemo)
                binding.rvChat.smoothScrollToPosition(listDemo.size - 1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
