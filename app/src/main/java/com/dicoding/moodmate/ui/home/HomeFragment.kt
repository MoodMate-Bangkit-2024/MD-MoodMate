package com.dicoding.moodmate.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.databinding.FragmentHomeBinding
import com.dicoding.moodmate.ui.journal.JournalAddUpdateActivity
import com.dicoding.moodmate.ui.journal.adapter.JournalAdapter
import com.dicoding.moodmate.ui.journal.db.JournalHelper
import com.dicoding.moodmate.ui.journal.entitiy.Journal
import com.dicoding.moodmate.ui.journal.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: JournalAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            when (result.resultCode) {
                JournalAddUpdateActivity.RESULT_ADD -> {
                    val journal = result.data?.getParcelableExtra<Journal>(JournalAddUpdateActivity.EXTRA_JOURNAL) as Journal
                    adapter.addItem(journal)
                    binding.rvJournals.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu Jurnal berhasil ditambahkan")
                    checkRecyclerViewEmpty()
                }
                JournalAddUpdateActivity.RESULT_UPDATE -> {
                    val journal = result.data?.getParcelableExtra<Journal>(JournalAddUpdateActivity.EXTRA_JOURNAL) as Journal
                    val position = result.data?.getIntExtra(JournalAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, journal)
                    binding.rvJournals.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu Jurnal berhasil diubah")
                    checkRecyclerViewEmpty()
                }
                JournalAddUpdateActivity.RESULT_DELETE -> {
                    val position = result.data?.getIntExtra(JournalAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                    checkRecyclerViewEmpty()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        loadJournalsAsync()

        if (savedInstanceState != null) {
            val list = savedInstanceState.getParcelableArrayList<Journal>(EXTRA_STATE)
            if (list != null) {
                adapter.listJournals = list
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = JournalAdapter(object : JournalAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedJournal: Journal?, position: Int?) {
                val intent = Intent(activity, JournalAddUpdateActivity::class.java)
                intent.putExtra(JournalAddUpdateActivity.EXTRA_JOURNAL, selectedJournal)
                intent.putExtra(JournalAddUpdateActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        binding.rvJournals.adapter = adapter
        binding.rvJournals.layoutManager = LinearLayoutManager(context)
        binding.rvJournals.setHasFixedSize(true)

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.rvJournals.addItemDecoration(itemDecoration)
    }

    private fun setupFab() {
        binding.addJournal.setOnClickListener {
            val intent = Intent(activity, JournalAddUpdateActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun loadJournalsAsync() {
        lifecycleScope.launch {
            val journalHelper = JournalHelper.getInstance(requireContext())
            journalHelper.open()
            val deferredJournals = async(Dispatchers.IO) {
                val cursor = journalHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val journals = deferredJournals.await()
            if (journals.size > 0) {
                adapter.listJournals = journals
                binding.tvEmptyMessage.visibility = View.GONE // Sembunyikan pesan kosong
            } else {
                adapter.listJournals = ArrayList()
                binding.tvEmptyMessage.visibility = View.VISIBLE // Tampilkan pesan kosong
            }
            journalHelper.close()
        }
    }

    private fun checkRecyclerViewEmpty() {
        if (adapter.itemCount == 0) {
            binding.tvEmptyMessage.visibility = View.VISIBLE
        } else {
            binding.tvEmptyMessage.visibility = View.GONE
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvJournals, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listJournals)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}
