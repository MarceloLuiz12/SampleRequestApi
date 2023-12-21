package com.example.samplerequestapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class ApiViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            return ApiViewModel(apiService = apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
