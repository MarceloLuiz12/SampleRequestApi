package com.example.samplerequestapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApiViewModel(private val apiService: ApiService) : ViewModel() {

    private val _apiResponse = MutableStateFlow<String?>(null)
    val apiResponse: StateFlow<String?> get() = _apiResponse.asStateFlow()

    fun requestApi(cep: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getCepDetails(cep)
                val result = "CEP: ${response.cep}\nLogradouro: ${response.logradouro}\nComplemento: ${response.complemento}"
                _apiResponse.value = result
            } catch (e: Exception) {
                _apiResponse.value = "Erro na requisição: ${e.message}"
            }
        }
    }
}
