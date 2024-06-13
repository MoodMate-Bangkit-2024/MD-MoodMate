package com.dicoding.moodmate.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var exploreAdapter: ExploreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exploreAdapter = ExploreAdapter(arrayListOf())
        binding.rvExplore.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = exploreAdapter
        }

        exploreViewModel.exploreList.observe(viewLifecycleOwner, Observer { exploreList ->
            exploreList?.let {
                exploreAdapter.setExploreData(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
