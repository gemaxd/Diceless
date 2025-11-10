package com.example.diceless.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ACTION, RESULT, STATE> : ViewModel() {

    private val _screen = MutableSharedFlow<RESULT>()
    val screen: SharedFlow<RESULT> = _screen

    private val _uiState: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<STATE> = _uiState

    abstract val initialState: STATE

    val currentState: STATE get() = uiState.value

    private val _results = MutableSharedFlow<RESULT>()
    val results: SharedFlow<RESULT> = _results.asSharedFlow()

    abstract fun onAction(action: ACTION)

    fun emitScreenResult(result: RESULT) = viewModelScope.launch {
        _screen.emit(result)
    }


    protected fun updateUiState(newState: STATE.() -> STATE) {
        _uiState.value = uiState.value.newState()
    }
}