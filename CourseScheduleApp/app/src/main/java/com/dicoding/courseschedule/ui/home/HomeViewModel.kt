package com.dicoding.courseschedule.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryType

class HomeViewModel(private val repository: DataRepository): ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()
    var nearestSchedule: LiveData<Course?>

    init {
        _queryType.value = QueryType.CURRENT_DAY
        nearestSchedule = repository.getNearestSchedule()
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
        loadNearestSchedule()
    }

    fun loadNearestSchedule() {
        val queryType = _queryType.value ?: return

        repository.getNearestSchedule().observeForever { result ->
            result?.let {
                val updatedNearestSchedule = MutableLiveData<Course?>()
                updatedNearestSchedule.value = it

                nearestSchedule = updatedNearestSchedule
            }
        }
    }
}
