package com.example.diceless.ui.playergrid


import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.getDefaultCounterData

// Modelo simples de estado (de data/models/PlayerState.kt)
// Modelo de estado (de data/models/PlayerState.kt)
//data class PlayerData(
//    val playerPosition: PositionEnum,
//    val name: String,
//    var life: Int = 40,
//    var counters: List<CounterData> = getDefaultCounterData()
//)

//// Enum de rotação (de ui/Rotation.kt)
//enum class PlayerRotation(val degrees: Float) {
//    NONE(0f),     // Jogador 1
//    RIGHT(90f),   // Jogador 2 (4 jogadores)
//    INVERTED(180f), // Jogador 2 (2 jogadores) ou 3
//    LEFT(270f)    // Jogador 4
//}
//
//// ViewModel stub (de ui/battlegrid/BattlegridViewModel.kt)
//class BattlegridViewModel {
//    val players = MutableStateFlow(listOf(
//        PlayerState("Jogador 1"),
//        PlayerState("Jogador 2"),
//        PlayerState("Jogador 3"),
//        PlayerState("Jogador 4")
//    ))
//}
//
//// Composable principal para o Battlegrid
//@Composable
//fun BattlegridScreen(viewModel: BattlegridViewModel) {
//    val players = viewModel.players.collectAsState().value
//    val isLandscape = LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
//
//    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
//        val maxWidth = constraints.maxWidth.toFloat()
//        val maxHeight = constraints.maxHeight.toFloat()
//        val quadrantWidth = maxWidth / 2
//        val quadrantHeight = maxHeight / 2
//
//        // Posiciona cada PlayerGrid em um quadrante
//        players.forEachIndexed { index, playerState ->
//            val rotation = when (index) {
//                0 -> PlayerRotation.NONE
//                1 -> if (players.size == 4) PlayerRotation.RIGHT else PlayerRotation.INVERTED
//                2 -> PlayerRotation.INVERTED
//                3 -> PlayerRotation.LEFT
//                else -> PlayerRotation.NONE
//            }
//            val position = when (index) {
//                0 -> Modifier.offset(0.dp, 0.dp) // Top-left
//                1 -> Modifier.offset(quadrantWidth.dp, 0.dp) // Top-right
//                2 -> Modifier.offset(quadrantWidth.dp, quadrantHeight.dp) // Bottom-right
//                3 -> Modifier.offset(0.dp, quadrantHeight.dp) // Bottom-left
//                else -> Modifier
//            }
//
//            PlayerGrid(
//                playerState = playerState,
//                rotation = rotation,
//                isFocused = index == 3, // Foco no Jogador 4
//                modifier = Modifier
//                    .size(quadrantWidth.dp, quadrantHeight.dp)
//                    .then(position)
//            )
//        }
//
//        // MiddleMenu central (simples FAB, sem rotação)
//        FloatingActionButton(
//            onClick = { /* Ações globais */ },
//            modifier = Modifier.align(Alignment.Center)
//        ) {
//            Text("Menu")
//        }
//    }
//}
//
//// Composable para PlayerGrid individual
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun PlayerGrid(
//    playerState: PlayerState,
//    rotation: PlayerRotation,
//    isFocused: Boolean = false,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxSize(0.5f)
//            .background(if (isFocused) Color(0xFFBBDEFB) else Color.White) // Azul claro se focado
//            .rotate(rotation.degrees) // Aplica rotação do enum
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(playerState.name, style = MaterialTheme.typography.titleLarge)
//
//            val pagerState = rememberPagerState()
//            HorizontalPager(
//                count = 3,
//                state = pagerState,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) { page ->
//                when (page) {
//                    0 -> LifePage(playerState)
//                    1 -> CommanderDamagePage(playerState)
//                    2 -> MarkersPage(playerState, isFocused)
//                }
//            }
//
//            // Indicadores de página (tabs)
//            TabRow(
//                selectedTabIndex = pagerState.currentPage,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Tab(selected = pagerState.currentPage == 0, onClick = { /* Swipe to 0 */ }) { Text("Vida") }
//                Tab(selected = pagerState.currentPage == 1, onClick = { /* Swipe to 1 */ }) { Text("Dano Cmdr") }
//                Tab(selected = pagerState.currentPage == 2, onClick = { /* Swipe to 2 */ }) { Text("Marcadores") }
//            }
//        }
//    }
//}
//
//// Página de Vida


// Página de Dano de Commander


// Página de Marcadores
//@Composable
//fun MarkersPage(playerData: PlayerData) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Marcadores", style = MaterialTheme.typography.titleMedium)
//        Spacer(Modifier.height(8.dp))
//        MarkerItem("Veneno", playerData.poisonCounters) { delta -> playerData.poisonCounters += delta }
//        MarkerItem("Energia", playerData.energyCounters) { delta -> playerData.energyCounters += delta }
//        MarkerItem("Experiência", playerData.experienceCounters) { delta -> playerData.experienceCounters += delta }
//    }
//}
//
//// Item de Marcador genérico

//
//// Preview para visualização
//@Preview(showBackground = true, device = "id:pixel_5")
//@Composable
//fun BattlegridPreview() {
//    MaterialTheme {
//        BattlegridScreen(BattlegridViewModel())
//    }
//}