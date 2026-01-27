package com.example.diceless.features.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.presentation.battlegrid.components.button.CounterControlButton
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.Route

@Composable
fun CountersGrid(
    playerData: PlayerData,
    rotationEnum: RotationEnum,
    onAction: (BattleGridActions) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        when (rotationEnum) {
            RotationEnum.NONE, RotationEnum.INVERTED -> {
                HorizontalCountersGridContent(
                    playerData = playerData,
                    rotationEnum = rotationEnum,
                    onAction = onAction
                )
            }

            RotationEnum.RIGHT, RotationEnum.LEFT -> {
                VerticalCountersGridContent(
                    playerData = playerData,
                    rotationEnum = rotationEnum,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun VerticalCountersGridContent(
    playerData: PlayerData,
    rotationEnum: RotationEnum,
    onAction: (BattleGridActions) -> Unit
) {
    val navigator = LocalNavigator.current

    val selectedCounters = playerData.counters.filter { it.isSelected }
    val allCounters = playerData.counters

    val middleIndex = (allCounters.size / 2).coerceAtLeast(0)
    val firstHalf = allCounters.take(middleIndex)
    val secondHalf = allCounters.drop(middleIndex)

    val verticalScrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = maxWidth.value.dp
        val maxHeight = maxHeight.value.dp

        Column(
            modifier = Modifier.verticalScroll(verticalScrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (rotationEnum == RotationEnum.RIGHT) {
                Button(
                    modifier = Modifier.vertical().rotate(rotationEnum.degrees),
                    onClick = { navigator.navigate(Route.ProfileImageSearch(
                        playerData = playerData,
                        onCardSelected = { selectedCard ->
                            onAction(
                                BattleGridActions.OnBackgroundSelected(
                                    player = playerData,
                                    card = selectedCard
                                )
                            )
                        }
                    )) },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Search image")
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    modifier = Modifier.vertical().rotate(rotationEnum.degrees),
                    onClick = { navigator.navigate(Route.ProfileImageList(
                        playerData = playerData,
                        onProfileSelected = { selectedCard ->
                            onAction(
                                BattleGridActions.OnBackgroundSelected(
                                    player = playerData,
                                    card = selectedCard
                                )
                            )
                        }
                    )) },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Load profile")
                }

                HorizontalDivider(
                    modifier = Modifier.padding(
                        vertical = maxWidth / 5,
                        horizontal = maxHeight / 10
                    ),
                    thickness = 2.dp
                )

                selectedCounters.forEach { it ->
                    VerticalCounterContent(
                        counter = it,
                        rotationEnum = rotationEnum,
                        onClick = { onAction(BattleGridActions.OnCounterToggled(playerData, it)) }
                    )
                }

                if (selectedCounters.isNotEmpty()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = maxWidth / 5,
                            horizontal = maxHeight / 10
                        ),
                        thickness = 2.dp
                    )
                }

                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        firstHalf.forEach { it ->
                            VerticalCounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        secondHalf.forEach { it ->
                            VerticalCounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        firstHalf.forEach { it ->
                            VerticalCounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                    Column {
                        secondHalf.forEach { it ->
                            VerticalCounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                if (selectedCounters.isNotEmpty()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = maxWidth / 5,
                            horizontal = maxHeight / 10
                        ),
                        thickness = 2.dp
                    )
                }

                selectedCounters.forEach { it ->
                    VerticalCounterContent(
                        counter = it,
                        rotationEnum = rotationEnum,
                        onClick = { onAction(BattleGridActions.OnCounterToggled(playerData, it)) }
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(
                        vertical = maxWidth / 5,
                        horizontal = maxHeight / 10
                    ),
                    thickness = 2.dp
                )

                Button(
                    modifier = Modifier.vertical().rotate(rotationEnum.degrees),
                    onClick = { navigator.navigate(Route.ProfileImageList(
                        playerData = playerData,
                        onProfileSelected = { selectedCard ->
                            onAction(
                                BattleGridActions.OnBackgroundSelected(
                                    player = playerData,
                                    card = selectedCard
                                )
                            )
                        }
                    )) },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Load profile")
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    modifier = Modifier.vertical().rotate(rotationEnum.degrees),
                    onClick = { navigator.navigate(Route.ProfileImageSearch(
                        playerData = playerData,
                        onCardSelected = { selectedCard ->
                            onAction(
                                BattleGridActions.OnBackgroundSelected(
                                    player = playerData,
                                    card = selectedCard
                                )
                            )
                        }
                    )) },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Search image")
                }
            }
        }
    }
}

@Composable
fun HorizontalCountersGridContent(
    playerData: PlayerData,
    rotationEnum: RotationEnum,
    onAction: (BattleGridActions) -> Unit
) {
    val navigator = LocalNavigator.current

    val selectedCounters = playerData.counters.filter { it.isSelected }
    val allCounters = playerData.counters

    val middleIndex = (allCounters.size / 2).coerceAtLeast(0)
    val firstHalf = allCounters.take(middleIndex)
    val secondHalf = allCounters.drop(middleIndex)

    val horizontalScrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        val maxHeight = maxHeight.value.dp
        val maxWidth = maxWidth.value.dp

        Row(
            modifier = Modifier.horizontalScroll(horizontalScrollState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { navigator.navigate(Route.ProfileImageSearch(
                    playerData = playerData,
                    onCardSelected = { selectedCard ->
                        onAction(
                            BattleGridActions.OnBackgroundSelected(
                                player = playerData,
                                card = selectedCard
                            )
                        )
                    }
                )) },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Search image")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = { navigator.navigate(
                    Route.ProfileImageList(
                        playerData = playerData,
                        onProfileSelected = { selectedCard ->
                            onAction(
                                BattleGridActions.OnBackgroundSelected(
                                    player = playerData,
                                    card = selectedCard
                                )
                            )
                        }
                    )
                ) },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Load Profile")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            VerticalDivider(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = maxHeight / 3
                ),
                thickness = 2.dp
            )

            if (rotationEnum == RotationEnum.NONE) {
                selectedCounters.forEach { it ->
                    Spacer(modifier = Modifier.padding(2.dp))
                    CounterControlButton(
                        counter = it,
                        onToggle = {
                            onAction(BattleGridActions.OnCounterToggled(playerData, it))
                        },
                        onIncrement = {
                            onAction(BattleGridActions.OnCounterIncrement(playerData, it))
                        },
                        onDecrement = {
                            onAction(BattleGridActions.OnCounterDecrement(playerData, it))
                        }
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                }

                if (selectedCounters.isNotEmpty()) {
                    VerticalDivider(
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = maxHeight / 3
                        ),
                        thickness = 2.dp
                    )
                }

                Column {
                    Row {
                        firstHalf.forEach { it ->
                            CounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                    Row {
                        secondHalf.forEach { it ->
                            CounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            } else {
                Column {
                    Row {
                        firstHalf.forEach { it ->
                            CounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                    Row {
                        secondHalf.forEach { it ->
                            CounterContent(
                                counter = it,
                                rotationEnum = rotationEnum,
                                onClick = {
                                    onAction(
                                        BattleGridActions.OnCounterSelected(
                                            playerData,
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                if (selectedCounters.isNotEmpty()) {
                    VerticalDivider(
                        modifier = Modifier.padding(
                            horizontal = maxWidth / 10,
                            vertical = maxHeight / 3
                        ),
                        thickness = 2.dp
                    )
                }

                selectedCounters.forEach { it ->
                    Spacer(modifier = Modifier.padding(2.dp))
                    CounterControlButton(
                        rotationEnum= rotationEnum,
                        counter = it,
                        onToggle = {
                            onAction(BattleGridActions.OnCounterToggled(playerData, it))
                        },
                        onIncrement = {
                            onAction(BattleGridActions.OnCounterIncrement(playerData, it))
                        },
                        onDecrement = {
                            onAction(BattleGridActions.OnCounterDecrement(playerData, it))
                        }
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }
        }
    }
}

@Composable
fun VerticalCounterContent(
    counter: CounterData,
    rotationEnum: RotationEnum,
    onClick: () -> Unit
) {
    Column {
        IconButton(
            modifier = Modifier
                .vertical()
                .padding(horizontal = 16.dp)
                .rotate(rotationEnum.degrees),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                contentDescription = counter.id,
                imageVector = counter.icon,
                tint = if (counter.isSelected) Color.Black else Color.Gray
            )
        }
    }
}

@Composable
fun CounterContent(
    counter: CounterData,
    rotationEnum: RotationEnum,
    onClick: () -> Unit
) {

    IconButton(
        modifier = Modifier
            .rotate(rotationEnum.degrees)
            .padding(8.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            contentDescription = counter.id,
            imageVector = counter.icon,
            tint = if (counter.isSelected) Color.Black else Color.Gray
        )
    }
}
