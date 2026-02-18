package com.example.diceless.features.history.ui

import androidx.lifecycle.viewModelScope
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.usecase.FetchAllMatchUseCase
import com.example.diceless.domain.usecase.FetchMatchHistoriesUseCase
import com.example.diceless.domain.usecase.GetCurrentOpenMatchUseCase
import com.example.diceless.domain.usecase.GetMatchByIdUseCase
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
    private val fetchMatchHistoriesUseCase: FetchMatchHistoriesUseCase,
    private val fetchAllMatchUseCase: FetchAllMatchUseCase,
    private val fetchMatchByIdUseCase: GetMatchByIdUseCase
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

            is MatchHistoryActions.OnLoadAllMatches -> {
                fetchMatches()
            }

            is MatchHistoryActions.OnLoadHistoryById -> {
                fetchHistoryById(action.matchId)
            }
        }
    }

    private fun fetchHistoriesForCurrentMatch(){
        viewModelScope.launch {
            val matchData = fetchCurrentOpenMatchUseCase()

            matchData?.let {
                fetchHistoryByMatch(matchData)
            } ?: run {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun fetchHistoryById(matchId: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val matchData = fetchMatchByIdUseCase(matchId)

            matchData?.let {
                val histories = fetchMatchHistoriesUseCase(matchData.id)

                _state.value = _state.value.copy(
                    matchData = matchData,
                    currentHistories = histories,
                    isLoading = false
                )
            } ?: run {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun fetchHistoryByMatch(matchData: MatchData) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val histories = fetchMatchHistoriesUseCase(matchData.id)

            _state.value = _state.value.copy(
                matchData = matchData,
                currentHistories = histories,
                isLoading = false
            )
        }
    }

    private fun fetchMatchById(matchId: Long){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val matchData = fetchMatchByIdUseCase(matchId)

            _state.value = _state.value.copy(
                matchData = matchData,
                isLoading = false
            )
        }
    }

    private fun fetchMatches(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val matches = fetchAllMatchUseCase()

            _state.value = _state.value.copy(
                matchList = matches,
                isLoading = false
            )
        }
    }
}