package com.manarimjesse.diceless.features.profile.list.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.manarimjesse.diceless.core.viewmodel.BaseViewModel
import com.manarimjesse.diceless.domain.usecase.GetAllProfileImagesUseCase
import com.manarimjesse.diceless.features.profile.list.mvi.ProfileImageListActions
import com.manarimjesse.diceless.features.profile.list.mvi.ProfileImageListResult
import com.manarimjesse.diceless.features.profile.list.mvi.ProfileImageListState
import com.manarimjesse.diceless.features.profile.list.mvi.ProfileListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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