package com.example.diceless.domain.match.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MatchStore(
    private val reducer: MatchReducer,
    private val middleware: MatchMiddleware,
    initialState: MatchState
) {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    suspend fun dispatch(action: MatchAction) {
        // 1 — REDUCER (estado imediato)
        val newState = reducer.reduce(_state.value, action)
        _state.value = newState

        // 2 — MIDDLEWARE (efeitos)
        middleware.process(action, newState, ::dispatch)
    }
}