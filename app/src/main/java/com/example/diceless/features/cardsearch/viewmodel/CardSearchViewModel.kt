package com.example.diceless.features.cardsearch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.data.CardRepository
import com.example.diceless.features.cardsearch.mvi.CardImageState
import com.example.diceless.features.cardsearch.mvi.CardSearchActions
import com.example.diceless.features.cardsearch.mvi.CardSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardSearchViewModel @Inject constructor() : BaseViewModel<CardSearchActions, Unit, CardSearchState>() {
    override val initialState: CardSearchState
        get() = TODO("Not yet implemented")

    override fun onAction(action: CardSearchActions) {
        TODO("Not yet implemented")
    }

    var state by mutableStateOf<CardImageState>(CardImageState.Idle)
        private set

    fun buscar(nomeCard: String) {
        if (nomeCard.isBlank()) {
            state = CardImageState.Error("Digite o nome do card")
            return
        }

        state = CardImageState.Loading
        viewModelScope.launch {
            val result = CardRepository.buscarImagem(nomeCard)
            state = result.fold(
                onSuccess = { CardImageState.Success(it) },
                onFailure = { CardImageState.Error("Card não encontrado") }
            )
        }
    }
}