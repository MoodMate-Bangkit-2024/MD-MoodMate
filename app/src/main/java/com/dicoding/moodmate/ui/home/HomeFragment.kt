package com.dicoding.moodmate.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.IntentCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.R
import com.dicoding.moodmate.data.pref.dataStore
import com.dicoding.moodmate.data.response.JournalData
import com.dicoding.moodmate.data.response.SingleJournalData
import com.dicoding.moodmate.databinding.FragmentHomeBinding
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.account.AccountViewModel
import com.dicoding.moodmate.ui.journal.JournalAdapter
import com.dicoding.moodmate.ui.journal.JournalAddUpdateActivity
import com.dicoding.moodmate.ui.journal.JournalViewModel
import com.dicoding.moodmate.ui.setting.SettingActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.map

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: JournalAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    private val accountViewModel: AccountViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val viewModel: JournalViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            when (result.resultCode) {
                JournalAddUpdateActivity.RESULT_ADD -> {
                    val journal = result.data?.getParcelableExtra<JournalData>(JournalAddUpdateActivity.EXTRA_JOURNAL)
                    if (journal != null) {
                        adapter.addItem(journal)
                        binding.rvJournals.smoothScrollToPosition(adapter.itemCount - 1)
                        showSnackbarMessage("Satu Jurnal berhasil ditambahkan")
                        checkRecyclerViewEmpty()
                    }
                }

                JournalAddUpdateActivity.RESULT_UPDATE -> {
                    val journal = result.data?.getParcelableExtra<JournalData>(JournalAddUpdateActivity.EXTRA_JOURNAL)
                    val position = result.data?.getIntExtra(JournalAddUpdateActivity.EXTRA_POSITION, 0) ?: 0
                    if (journal != null) {
                        adapter.updateItem(position, journal)
                        binding.rvJournals.smoothScrollToPosition(position)
                        showSnackbarMessage("Satu Jurnal berhasil diubah")
                        checkRecyclerViewEmpty()
                    }
                }

                JournalAddUpdateActivity.RESULT_DELETE -> {
                    val position = result.data?.getIntExtra(JournalAddUpdateActivity.EXTRA_POSITION, 0) ?: 0
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu Jurnal berhasil dihapus")
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()

        if (savedInstanceState != null) {
            val list = savedInstanceState.getParcelableArrayList<JournalData>(EXTRA_STATE)
            if (list != null) {
                adapter.listJournals = list
            }
        }

        accountViewModel.getSession().name ?.let { name ->
            val greeting = getString(R.string.greeting, name)
            binding.tvGreeting.text = greeting
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dark_mode, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dark_mode -> {
                Intent(activity, SettingActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = JournalAdapter(object : JournalAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedJournal: JournalData, position: Int?) {
                val journal = SingleJournalData(
                    selectedJournal.createdAt!!,
                    selectedJournal.updatedAt,
                    selectedJournal.title!!,
                    selectedJournal.text!!,
                    selectedJournal.mood!!,
                    selectedJournal.author!!,
                    selectedJournal.id!!,
                    selectedJournal.v!!
                )

                val intent = Intent(activity, JournalAddUpdateActivity::class.java)

                intent.putExtra(JournalAddUpdateActivity.EXTRA_JOURNAL, journal)
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

    private fun loadJournalsAsync(token : String) {
        context?.dataStore!!.data.map { pref ->
        }

        homeViewModel.loadJournals(
            token
        )

        homeViewModel.journals.observe(viewLifecycleOwner) { journals ->
            Log.d("Journal Get", "loadJournalsAsync: $journals")

            if (journals != null) {
                adapter.listJournals = ArrayList(journals)
                binding.tvEmptyMessage.visibility = if (journals.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun checkRecyclerViewEmpty() {
        binding.tvEmptyMessage.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
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

    override fun onResume() {
        super.onResume()
        viewModel.user.observe(viewLifecycleOwner){
            loadJournalsAsync(it.token)
        }
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}
