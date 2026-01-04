package com.example.diceless.features.cardsearch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.domain.usecase.SearchForCardsUseCase
import com.example.diceless.features.cardsearch.mvi.CardListState
import com.example.diceless.features.cardsearch.mvi.CardSearchActions
import com.example.diceless.features.cardsearch.mvi.CardSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardSearchViewModel @Inject constructor(
    private val searchCardUseCase: SearchForCardsUseCase
) : BaseViewModel<CardSearchActions, Unit, CardSearchState>() {
    override val initialState: CardSearchState
        get() = CardSearchState()

    override fun onAction(action: CardSearchActions) {

    }

    var state by mutableStateOf<CardListState>(CardListState.Idle)
        private set

    fun querySearchForCards(query: String) {
        if (query.length >= 3 ){
            if (query.isBlank()) {
                state = CardListState.Error("Digite o nome do card")
                return
            }

            state = CardListState.Loading
            viewModelScope.launch {
                val result = searchCardUseCase.invoke(query)
                state = result.fold(
                    onSuccess = { CardListState.Success(it) },
                    onFailure = { CardListState.Error(it.message ?: "Erro") }
                )
            }
        }
    }
}