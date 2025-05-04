package com.vendas_casi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vendas_casi.data.UserPreferences
import com.vendas_casi.data.repository.VendaRepository
import com.vendas_casi.ui.screens.historico.HistoricoScreen
import com.vendas_casi.ui.screens.vendas.VendaScreen
import com.vendas_casi.ui.theme.VendasCASITheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var repository: VendaRepository
    private lateinit var userPreferences: UserPreferences
    private var keepSplashScreenOn = true
    private var initializationJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { keepSplashScreenOn }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initializeAppComponents()

        setContent {
            VendasCASITheme {
                AppContent(
                    repository = repository,
                    userPreferences = userPreferences
                )
            }
        }
    }

    private fun initializeAppComponents() {
        initializationJob = CoroutineScope(Dispatchers.IO).launch {
            repository = VendaRepository(this@MainActivity)
            userPreferences = UserPreferences(this@MainActivity)

            delay(1000)

            withContext(Dispatchers.Main) {
                keepSplashScreenOn = false
            }
        }
    }

    override fun onDestroy() {
        initializationJob?.cancel()
        super.onDestroy()
    }
}

@Composable
private fun AppContent(
    repository: VendaRepository,
    userPreferences: UserPreferences
) {
    var showWelcome by remember { mutableStateOf(true) }
    var showHistory by remember { mutableStateOf(false) }

    if (showWelcome) {
        WelcomeScreen {
            showWelcome = false
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            if (showHistory) {
                HistoricoScreen(
                    vendas = repository.carregarVendas(),
                    onBack = { showHistory = false }
                )
            } else {
                VendaScreen(
                    repository = repository,
                    userPreferences = userPreferences,
                    onNavigateToHistorico = { showHistory = true }
                )
            }
        }
    }
}

@Composable
private fun WelcomeScreen(
    onLoadingComplete: () -> Unit
) {
    val scale = remember { Animatable(0.9f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(600))
        scale.animateTo(
            targetValue = 1.05f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
        scale.animateTo(1f, tween(300))

        delay(800)

        alpha.animateTo(0f, tween(300))
        onLoadingComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_casi),
            contentDescription = "Bem-vindo",
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}