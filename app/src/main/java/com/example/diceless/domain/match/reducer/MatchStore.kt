package com.example.diceless.domain.match.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MatchStore(
    private val reducer: MatchReducer,
    private val middleware: MatchMiddleware,
    initialState: MatchState,
    private val scope: CoroutineScope
) {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun dispatch(action: MatchAction) {
        scope.launch {

            // 1️⃣ Middleware primeiro (intercepta intenção)
            middleware.process(
                action = action,
                getState = { _state.value },
                scope = scope,
                dispatch = ::dispatch
            )

            // 2️⃣ Reducer aplica mudança
            val newState = reducer.reduce(_state.value, action)
            _state.value = newState
        }
    }
}