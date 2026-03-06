package com.example.diceless.domain.match.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
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

    private val actionChannel = Channel<MatchAction>(Channel.UNLIMITED)

    init {
        startProcessing()
    }

    fun dispatch(action: MatchAction) {
        scope.launch {
            actionChannel.send(action)
        }
    }

    private fun startProcessing() {
        scope.launch {
            for (action in actionChannel) {

                // 1 — REDUCER
                val newState = reducer.reduce(_state.value, action)
                _state.value = newState

                // 2 — MIDDLEWARE
                middleware.process(
                    action = action,
                    getState = { _state.value },
                    scope = scope,
                    dispatch = ::dispatch
                )
            }
        }
    }
}