package com.brokyfood.app.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokyfood.app.data.model.Food
import com.brokyfood.app.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private var allFoods: List<Food> = emptyList()

    init {
        loadFoods()
    }

    private fun loadFoods() {
        viewModelScope.launch {
            try {
                repository.getAllFoods().collect { foods ->
                    allFoods = foods
                    _uiState.update { currentState ->
                        currentState.copy(foods = foods)
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading foods", e)
                _uiState.update { currentState ->
                    currentState.copy(foods = emptyList())
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            try {
                val filteredFoods = if (query.isBlank()) {
                    allFoods
                } else {
                    allFoods.filter { food ->
                        food.name.contains(query, ignoreCase = true)
                    }
                }
                _uiState.update { currentState ->
                    currentState.copy(foods = filteredFoods)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error filtering foods", e)
            }
        }
    }
}

data class HomeUiState(
    val foods: List<Food> = emptyList()
) 