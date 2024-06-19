package com.compfest16.sea_salon.features.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compfest16.sea_salon.di.Module
import com.compfest16.sea_salon.features.presentation.design_system.SEASalonTheme
import com.compfest16.sea_salon.features.presentation.navigation.MainNavigation
import com.compfest16.sea_salon.features.presentation.screen.auth_section.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(listOf(Module.repository, Module.viewModel))
        }

        enableEdgeToEdge()
        setContent {
            SEASalonTheme {
                MainNavigation()
            }
        }
    }
}