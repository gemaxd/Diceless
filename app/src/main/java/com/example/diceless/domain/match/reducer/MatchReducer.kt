package com.example.diceless.domain.match.reducer

import javax.inject.Inject

class MatchReducer @Inject constructor() {
    fun reduce(
        state: MatchState,
        action: MatchAction
    ): MatchState {

        return when (action) {
            is MatchAction.OnLifeChanged -> {
                val updatedPlayers = state.players.map {
                    if (it.playerPosition == action.player.playerPosition) {
                        it.copy(life = it.life + action.delta)
                    } else {
                        it
                    }
                }

                state.copy(
                    players = updatedPlayers
                )
            }

            is MatchAction.ChangeScheme -> {
                state.copy(
                    scheme = action.scheme
                )
            }

            MatchAction.ToggleMonarchCounter -> {
                state.copy(
                    showMonarchSymbol = !state.showMonarchSymbol
                )
            }

            is MatchAction.SchemeLoaded -> {
                state.copy(
                    scheme = action.scheme
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

            is MatchAction.UpdateMatchState -> {
                state.copy(
                    matchData = action.matchData
                )
            }

            is MatchAction.OnMatchRestarted -> {
                state.copy(
                    players = action.updatedPlayers,
                    winnerId = null
                )
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