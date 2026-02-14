package com.example.diceless.features.battlegrid.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.diceless.R
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.extensions.paddingBasedOnPosition
import com.example.diceless.common.extensions.vertical
import com.example.diceless.common.utils.getCorrectOrientation
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.features.battlegrid.mvi.BattleGridState
import com.example.diceless.features.battlegrid.viewmodel.BattleGridViewModel
import com.example.diceless.features.common.components.pager.InnerHorizontalPager
import com.example.diceless.features.common.components.pager.InnerVerticalPager
import com.example.diceless.features.middlemenu.mvi.BattleGridSheetState
import com.example.diceless.features.middlemenu.presentation.components.BattleGridBottomSheetsHost
import com.example.diceless.features.middlemenu.presentation.components.BottomMenuComponent
import com.example.diceless.features.middlemenu.presentation.components.MiddleMenuComponent
import com.example.diceless.navigation.RESULT_CARD_SELECTED
import com.example.diceless.navigation.RESULT_PLAYER_USED
import com.example.diceless.navigation.ResultStore
import com.example.diceless.presentation.battlegrid.components.draggable.MonarchDraggableComponent
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun BattleGridScreen(
    resultStore: ResultStore,
    viewmodel: BattleGridViewModel = hiltViewModel()
) {
    val state by viewmodel.state.collectAsState()
    val onUiEvent = viewmodel::onAction

    LaunchedEffect(resultStore) {
        val card = resultStore.getResult<BackgroundProfileData>(RESULT_CARD_SELECTED)
        val playerData = resultStore.getResult<PlayerData>(RESULT_PLAYER_USED)

        playerData?.let { player ->
            card?.let { selectedCard ->
                onUiEvent(
                    BattleGridActions.OnBackgroundSelected(
                        player = player,
                        card = selectedCard
                    )
                )
            }
        }
    }

    BattleGridContent(
        uiState = state,
        players = state.activePlayers,
        selectedScheme = state.selectedScheme,
        onAction = onUiEvent
    )
}

@ExperimentalMaterial3Api
@Composable
fun BattleGridContent(
    uiState: BattleGridState,
    players: List<PlayerData>,
    selectedScheme: SchemeEnum,
    onAction: (BattleGridActions) -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.fireworks)
    )

    var expandedMiddleMenu by remember { mutableStateOf(false) }
    var currentSheet by remember { mutableStateOf<BattleGridSheetState>(BattleGridSheetState.None) }

    BoxWithConstraints(
        modifier = Modifier
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        val maxHeight = maxHeight

        AnimatedContent(targetState = selectedScheme){ scheme ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                players.forEachIndexed { index, playerState ->
                    val orientation =
                        getCorrectOrientation(playerState.playerPosition, scheme = scheme)

                    orientation?.let { orient ->
                        Card(
                            modifier = Modifier
                                .align(orient.alignment.align)
                                .fillMaxWidth(orient.proportion.width)
                                .fillMaxHeight(orient.proportion.height),
                            shape = RectangleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black
                            )
                        ) {
                            when {
                                playerState.playerPosition.name == uiState.winnerId -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        LottieAnimation(
                                            composition = composition,
                                            iterations = LottieConstants.IterateForever,
                                            modifier = Modifier
                                                .rotate(orient.rotation.degrees)
                                                .vertical()
                                                .fillMaxSize(),
                                            contentScale = ContentScale.FillWidth
                                        )

                                        Text(
                                            modifier = Modifier
                                                .rotate(orient.rotation.degrees)
                                                .vertical()
                                                .align(Alignment.Center),
                                            text = "Victory".uppercase(Locale.ROOT),
                                            fontSize = 45.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            letterSpacing = (-1.5).sp
                                        )
                                    }
                                }
                                playerState.isDefeated() -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .rotate(orient.rotation.degrees)
                                                .vertical()
                                                .align(Alignment.Center),
                                            text = "Defeated".uppercase(Locale.ROOT),
                                            fontSize = 45.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            letterSpacing = (-1.5).sp
                                        )
                                    }
                                }
                                else -> {
                                    IndividualGridContent(
                                        isDamageLinked = uiState.linkCommanderDamageToLife,
                                        playerData = playerState,
                                        rotation = orient.rotation,
                                        onAction = onAction
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            visible = uiState.showMonarchSymbol,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 500
                )
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 400)
            )
        ) {
            MonarchDraggableComponent(schemeEnum = selectedScheme, maxHeight)
        }

        MiddleMenuComponent(
            uiState = uiState,
            expanded = expandedMiddleMenu,
            onUiEvent = onAction,
            onToggle = { expandedMiddleMenu = !expandedMiddleMenu },
            onHistory = { currentSheet = BattleGridSheetState.History },
            onRestart = { currentSheet = BattleGridSheetState.Restart },
            onSettings = { currentSheet = BattleGridSheetState.Settings },
            onSchemes = { currentSheet = BattleGridSheetState.Schemes }
        )

        BottomMenuComponent(
            modifier = Modifier.align(Alignment.BottomCenter),
            isExpanded = expandedMiddleMenu,
            onUiEvent = onAction
        )

        BattleGridBottomSheetsHost(
            currentSheet = currentSheet,
            modalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismiss = {
                currentSheet = BattleGridSheetState.None
            }
        )
    }
}

@Composable
fun IndividualGridContent(
    modifier: Modifier = Modifier,
    isDamageLinked: Boolean,
    playerData: PlayerData,
    rotation: RotationEnum,
    onAction: (BattleGridActions) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .paddingBasedOnPosition(playerData.playerPosition, rotation)
            .background(Color.Transparent),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            when (rotation) {
                RotationEnum.NONE, RotationEnum.INVERTED -> {
                    InnerHorizontalPager(isDamageLinked, playerData, rotation, onAction)
                }

                RotationEnum.RIGHT, RotationEnum.LEFT -> {
                    InnerVerticalPager(isDamageLinked, playerData, rotation, onAction)
                }
            }
        }
    }
}
