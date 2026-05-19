package com.example.datastoreexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastoreexample.data.model.UserSettings
import com.example.datastoreexample.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<UserSettings> =
        repository.settingsFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UserSettings()
            )

    fun onUsernameChange(username: String) {
        viewModelScope.launch {
            repository.updateUsername(username)
        }
    }

    fun onBoldTextChange(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateBoldText(enabled)
        }
    }

    fun onFontSizeChange(size: Int) {
        viewModelScope.launch {
            repository.updateFontSize(size)
        }
    }
}