package com.example.diceless.features.battlegrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.features.AppViewModel
import com.example.diceless.features.common.theme.DiceLessButtonColors
import com.example.diceless.features.common.theme.DicelessTheme
import com.example.diceless.navigation.NavigationRoot
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val appViewModel: AppViewModel = hiltViewModel()

            DicelessTheme {
                Surface {
                    NavigationRoot()
                }
            }
        }
    }
}