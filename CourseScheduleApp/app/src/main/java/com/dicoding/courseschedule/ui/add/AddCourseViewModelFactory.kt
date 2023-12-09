package com.dicoding.courseschedule.ui.add

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.data.DataRepository

class AddCourseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataRepository.getInstance(context)?.let { AddCourseViewModel(it) } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        fun createFactory(context: Context): AddCourseViewModelFactory {
            return AddCourseViewModelFactory(context)
        }
    }
}