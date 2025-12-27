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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RocketListState {
    object Loading : RocketListState()
    data class Success(val rockets: List<RocketEntity>) : RocketListState()
    data class Error(val message: String) : RocketListState()
    object Empty : RocketListState()
}

class RocketListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RocketRepository(application)

    private val _state = MutableStateFlow<RocketListState>(RocketListState.Loading)
    val state: StateFlow<RocketListState> = _state

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _showOnlyActive = MutableStateFlow(false)
    val showOnlyActive = _showOnlyActive.asStateFlow()

    private var allRockets: List<RocketEntity> = emptyList()

    init {
        loadRockets()
    }

    fun loadRockets() {
        viewModelScope.launch {
            _state.value = RocketListState.Loading
            try {
                allRockets = repository.getRockets()
                updateFilteredList()
            } catch (e: Exception) {
                _state.value = RocketListState.Error("Error al cargar. Verifica tu conexión")
            }
        }
    }

    fun filter(query: String) {
        _searchText.value = query
        updateFilteredList()
    }

    fun toggleActiveFilter(isActive: Boolean) {
        _showOnlyActive.value = isActive
        updateFilteredList()
    }

    private fun updateFilteredList() {
        val filtered = allRockets.filter {
            it.name.contains(_searchText.value, ignoreCase = true) &&
                    (!_showOnlyActive.value || it.active)
        }

        if (allRockets.isNotEmpty() && filtered.isEmpty()) {
            _state.value = RocketListState.Empty
        } else {
            _state.value = RocketListState.Success(filtered)
        }
    }

    companion object {
        fun provideFactory(application: Application): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RocketListViewModel(application) as T
                }
            }
        }
    }
}
