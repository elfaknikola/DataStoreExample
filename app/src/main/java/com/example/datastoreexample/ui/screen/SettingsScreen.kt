package com.example.datastoreexample.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastoreexample.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val settings by viewModel.settings.collectAsState()

    var usernameInput by remember(settings.username) {
        mutableStateOf(settings.username)
    }

    LaunchedEffect(usernameInput) {
        delay(500)

        if (usernameInput != settings.username) {
            viewModel.onUsernameChange(usernameInput)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "User Settings",
            fontSize = 24.sp
        )

        OutlinedTextField(
            value = usernameInput,
            onValueChange = { usernameInput = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        /*OutlinedTextField(
            value = settings.username,
            onValueChange = viewModel::onUsernameChange,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )*/

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Bold Text")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = settings.boldText,
                onCheckedChange = viewModel::onBoldTextChange
            )
        }

        Text("Font size: ${settings.fontSize}")

        Slider(
            value = settings.fontSize.toFloat(),
            onValueChange = {
                viewModel.onFontSizeChange(it.toInt())
            },
            valueRange = 12f..30f,
            steps = 17
        )

        Text(
            text = "Preview text for ${settings.username.ifBlank { "Guest" }}",
            fontSize = settings.fontSize.sp,
            fontWeight = if (settings.boldText) FontWeight.Bold else FontWeight.Normal
        )
    }
}