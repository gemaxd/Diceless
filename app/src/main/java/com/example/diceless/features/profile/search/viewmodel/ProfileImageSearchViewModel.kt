package com.example.diceless.features.profile.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.model.toBackgroundProfile
import com.example.diceless.domain.usecase.InsertPlayerWithBackgroundUseCase
import com.example.diceless.domain.usecase.SearchForCardsUseCase
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchListState
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchActions
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileImageSearchViewModel @Inject constructor(
    private val searchCardUseCase: SearchForCardsUseCase,
    private val insertPlayerWithBackground: InsertPlayerWithBackgroundUseCase
) : BaseViewModel<ProfileImageSearchActions, Unit, ProfileImageSearchState>() {
    override val initialState: ProfileImageSearchState
        get() = ProfileImageSearchState()

    override fun onAction(action: ProfileImageSearchActions) {
        when (action) {
            is ProfileImageSearchActions.OnImageSelect -> {
                profileSave(action.playerData, action.backgroundProfile)
            }
            is ProfileImageSearchActions.OnSearchQueryChanged -> {
                querySearchForCards(action.query)
            }
        }

    }

    var state by mutableStateOf<ProfileImageSearchListState>(ProfileImageSearchListState.Idle)
        private set

    fun profileSave(player: PlayerData, backgroundProfile: ScryfallCard){
        viewModelScope.launch {
            insertPlayerWithBackground(player, backgroundProfile.toBackgroundProfile())
        }
    }

    fun querySearchForCards(query: String) {
        if (query.length >= 3 ){
            if (query.isBlank()) {
                state = ProfileImageSearchListState.Error("Digite o nome do card")
                return
            }

            state = ProfileImageSearchListState.Loading
            viewModelScope.launch {
                val result = searchCardUseCase.invoke(query)
                state = result.fold(
                    onSuccess = { ProfileImageSearchListState.Success(it) },
                    onFailure = { ProfileImageSearchListState.Error(it.message ?: "Erro") }
                )
            }
        }
    }
}