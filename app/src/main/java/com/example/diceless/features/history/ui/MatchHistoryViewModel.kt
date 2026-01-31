package com.example.diceless.features.history.ui

import androidx.lifecycle.viewModelScope
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.domain.usecase.FetchMatchHistoriesUseCase
import com.example.diceless.domain.usecase.GetCurrentOpenMatchUseCase
import com.example.diceless.features.history.mvi.MatchHistoryActions
import com.example.diceless.features.history.mvi.MatchHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchHistoryViewModel @Inject constructor(
    private val fetchCurrentOpenMatchUseCase: GetCurrentOpenMatchUseCase,
    private val fetchMatchHistoriesUseCase: FetchMatchHistoriesUseCase
) : BaseViewModel<MatchHistoryActions, Unit, MatchHistoryState>() {
    private val _state = MutableStateFlow(MatchHistoryState())
    val state: StateFlow<MatchHistoryState> = _state

    override val initialState: MatchHistoryState
        get() = MatchHistoryState()

    override fun onAction(action: MatchHistoryActions) {
        when(action){
            is MatchHistoryActions.OnLoadCurrentHistory -> {
                fetchHistoriesForCurrentMatch()
            }
        }
    }

    private fun fetchHistoriesForCurrentMatch(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val matchData = fetchCurrentOpenMatchUseCase()

            matchData?.let {
                val histories = fetchMatchHistoriesUseCase(matchData.id)

                _state.value = _state.value.copy(
                    matchData = matchData,
                    histories = histories,
                    isLoading = false
                )
            } ?: run {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}