package com.dicoding.moodmate.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.R
import com.dicoding.moodmate.databinding.FragmentExploreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvExplore: RecyclerView
    private val list = ArrayList<Explore>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvExplore = binding.rvExplore
        rvExplore.setHasFixedSize(true)
        rvExplore.layoutManager = LinearLayoutManager(context)

        loadExploreData()

        return root
    }

    private fun loadExploreData() {
        viewLifecycleOwner.lifecycleScope.launch {
            list.addAll(getListExplore())
            showRecyclerList()
        }
    }

    private suspend fun getListExplore(): ArrayList<Explore> {
        return withContext(Dispatchers.IO) {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listExplore = ArrayList<Explore>()
            for (i in dataName.indices) {
                val explore = Explore(dataName[i], dataPhoto.getResourceId(i, -1))
                listExplore.add(explore)
            }
            dataPhoto.recycle()
            listExplore
        }
    }

    private fun showRecyclerList() {
        rvExplore.layoutManager = LinearLayoutManager(context)
        val exploreAdapter = ExploreAdapter(list)
        rvExplore.adapter = exploreAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}