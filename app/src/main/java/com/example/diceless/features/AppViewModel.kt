package com.example.diceless.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceless.core.domain.usecase.bootstrap.BootstrapAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val bootstrapAppUseCase: BootstrapAppUseCase
): ViewModel() {
    init {
        viewModelScope.launch {
            bootstrapAppUseCase.invoke()
        }
    }
}