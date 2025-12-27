package com.example.m07_0488_endterm_pr01_evafioen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.m07_0488_endterm_pr01_evafioen.data.local.RocketEntity
import com.example.m07_0488_endterm_pr01_evafioen.data.repository.RocketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
sealed class RocketDetailState {
    object Loading : RocketDetailState()
    data class Success(val rocket: RocketEntity) : RocketDetailState()
    data class Error(val message: String) : RocketDetailState()
}

class RocketDetailViewModel(
    application: Application,
    private val rocketId: String
) : AndroidViewModel(application) {

    private val repository = RocketRepository(application)

    private val _state = MutableStateFlow<RocketDetailState>(RocketDetailState.Loading)
    val state: StateFlow<RocketDetailState> = _state

    init {
        loadRocketDetails()
    }
    private fun loadRocketDetails() {
        viewModelScope.launch {
            _state.value = RocketDetailState.Loading
            val rocket = repository.getRocketById(rocketId)
            if (rocket != null) {
                _state.value = RocketDetailState.Success(rocket)
            } else {
                _state.value = RocketDetailState.Error("Cohete no encontrado")
            }
        }
    }
    companion object {
        fun provideFactory(
            application: Application,
            rocketId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RocketDetailViewModel(application, rocketId) as T
            }
        }
    }
}