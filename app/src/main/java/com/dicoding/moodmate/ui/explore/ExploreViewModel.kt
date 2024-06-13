package com.dicoding.moodmate.ui.explore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.moodmate.R

class ExploreViewModel(application: Application) : AndroidViewModel(application) {

    private val _exploreList = MutableLiveData<List<Explore>>()
    val exploreList: LiveData<List<Explore>> get() = _exploreList

    init {
        loadExploreData()
    }

    private fun loadExploreData() {
        val context = getApplication<Application>().applicationContext
        val dataName = context.resources.getStringArray(R.array.data_name)
        val dataPhoto = context.resources.obtainTypedArray(R.array.data_photo)

        val listExplore = ArrayList<Explore>()
        for (i in dataName.indices) {
            val explore = Explore(dataName[i], dataPhoto.getResourceId(i, -1))
            listExplore.add(explore)
        }
        dataPhoto.recycle()

        _exploreList.value = listExplore
    }
}
