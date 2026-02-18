package com.example.diceless.features.profile.list.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.model.toBackgroundProfile
import com.example.diceless.domain.usecase.GetAllProfileImagesUseCase
import com.example.diceless.domain.usecase.InsertPlayerWithBackgroundUseCase
import com.example.diceless.domain.usecase.SearchForCardsUseCase
import com.example.diceless.features.profile.list.mvi.ProfileImageListActions
import com.example.diceless.features.profile.list.mvi.ProfileImageListResult
import com.example.diceless.features.profile.list.mvi.ProfileImageListState
import com.example.diceless.features.profile.list.mvi.ProfileListState
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchListState
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchActions
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileImageListViewModel @Inject constructor(
    private val profilesImagesUseCase: GetAllProfileImagesUseCase
) : BaseViewModel<ProfileImageListActions, ProfileImageListResult, ProfileImageListState>() {

    override val initialState: ProfileImageListState
        get() = ProfileImageListState()

    private val _events = MutableSharedFlow<ProfileImageListResult>()
    val events = _events.asSharedFlow()

    var state by mutableStateOf<ProfileListState>(ProfileListState.Idle)
        private set

    init {
        onAction(ProfileImageListActions.OnProfileImagesLoad)
    }

    override fun onAction(action: ProfileImageListActions) {
        when (action) {
            is ProfileImageListActions.OnProfileImagesLoad -> {
                onLoadProfileImages()
            }

            is ProfileImageListActions.OnProfileImageSelected -> {
                viewModelScope.launch {
                    _events.emit(
                        value = ProfileImageListResult.OnImageSelect(
                            action.playerData,
                            action.backgroundProfileData
                        )
                    )
                }
            }
        }
    }

    private fun onLoadProfileImages() {
        viewModelScope.launch {
            state = ProfileListState.Loading

            val result = profilesImagesUseCase.invoke()

            state = result.fold(
                onSuccess = { ProfileListState.Success(it) },
                onFailure = {
                    ProfileListState.Error(it.message ?: "Erro") }
            )
        }
    }
}