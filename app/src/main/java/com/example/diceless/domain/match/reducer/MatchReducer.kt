package com.example.diceless.domain.match.reducer

import javax.inject.Inject

class MatchReducer @Inject constructor() {
    fun reduce(
        state: MatchState,
        action: MatchAction
    ): MatchState {

        return when (action) {
            is MatchAction.MatchUpdated -> {
                state.copy(
                    matchData = action.updatedMatch,
                    players = action.players ?: state.players
                )
            }

            is MatchAction.ToggleMonarchCounter -> {
                state.copy(
                    showMonarchSymbol = !state.showMonarchSymbol
                )
            }

            is MatchAction.CheckMatchEnd -> {
                state.copy(
                    winnerId = checkMatchEnd(state)
                )
            }

            is MatchAction.InitialPlayersLoaded -> {
                state.copy(
                    players = action.players
                )
            }

            is MatchAction.OnMatchRestarted -> {
                state.copy(
                    players = action.updatedPlayers,
                    winnerId = null
                )
            }

            is MatchAction.StateUpdate -> {
                action.state
            }

            else -> state
        }
    }

    private fun checkMatchEnd(state: MatchState): String? {
        return if (state.players.size > 1){
            val alive = state.players.filter { it.life > 0 }

            if (alive.size == 1) {
                alive.first().playerPosition.name
            } else {
                null
            }
        } else {
            null
        }
    }
}