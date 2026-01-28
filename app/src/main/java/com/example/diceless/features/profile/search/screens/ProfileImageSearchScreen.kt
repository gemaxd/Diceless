package com.example.diceless.features.profile.search.screens

import android.R.attr.visible
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.domain.model.CardFace
import com.example.diceless.domain.model.ImageUris
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.model.toBackgroundProfile
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchListState
import com.example.diceless.features.profile.search.mvi.ProfileImageSearchActions
import com.example.diceless.features.profile.search.viewmodel.ProfileImageSearchViewModel
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.RESULT_CARD_SELECTED
import com.example.diceless.navigation.RESULT_PLAYER_USED
import com.example.diceless.navigation.ResultStore
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImageSearchScreen(
    resultStore: ResultStore,
    viewModel: ProfileImageSearchViewModel = hiltViewModel(),
    playerData: PlayerData
){
    var nomeCard by remember { mutableStateOf("") }
    val onUiEvent = viewModel::onAction

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        when (val state = viewModel.state) {
                            is ProfileImageSearchListState.Loading -> {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                            is ProfileImageSearchListState.Success -> {
                                LazyColumn(modifier = Modifier.fillMaxWidth()) {

                                    itemsIndexed(
                                       items = state.cards,
                                        key = { _, card -> card.cardId }
                                    ) {  _, card ->

                                        var visible by remember { mutableStateOf(false) }

                                        LaunchedEffect(card.cardId) {
                                            visible = true
                                        }

                                        this@Column.AnimatedVisibility(
                                            visible = visible,
                                            enter = fadeIn() + slideInVertically { it / 3 }
                                        ) {
                                            CardItem(
                                                resultStore = resultStore,
                                                playerData = playerData,
                                                card = card,
                                                onUiEvent = onUiEvent
                                            )
                                        }
                                    }
                                }
                            }
                            is ProfileImageSearchListState.Error -> {
                                Text(
                                    text = state.message,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            else -> {}
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.8f to Color.Transparent,
                                    1.0f to Color.Black
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 50.dp, start = 24.dp, end = 24.dp),
                    value = nomeCard,
                    onValueChange = {
                        nomeCard = it
                        onUiEvent(
                            ProfileImageSearchActions.OnSearchQueryChanged(nomeCard)
                        )
                    },
                    label = { Text("Nome do card") },
                    colors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedContainerColor = Color.Black,
                        focusedContainerColor = Color.Black,
                    ),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    )
}

@Composable
fun CardItem(
    resultStore: ResultStore,
    playerData: PlayerData,
    card: ScryfallCard,
    onUiEvent: (ProfileImageSearchActions) -> Unit
) {
    val navigator = LocalNavigator.current

    val imageUrl = card.imageUris?.artCrop
        ?: card.cardFaces?.firstOrNull()?.imageUris?.large

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageUrl != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
            ) {

                AsyncImage(
                    model = imageUrl,
                    contentDescription = card.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.4f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            resultStore.setResult(RESULT_CARD_SELECTED, card.toBackgroundProfile())
                            resultStore.setResult(RESULT_PLAYER_USED, playerData)
                            navigator.pop()
                        },
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(all = 8.dp),
                    text = card.name,
                    color = Color.White
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Pincel",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = card.artistName,
                            color = Color.White
                        )
                    }


                    Button(
                        onClick = {
                            onUiEvent(ProfileImageSearchActions.OnLoadAllPrints(card.printsSearchUri))
                        }
                    ) {
                        Text(text = "View all prints", color = Color.White)
                    }
                }
            }

        } else {
            Box(
                modifier = Modifier
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OverlayPreview(){

    Box(modifier = Modifier.size(64.dp)) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Text(text = "conteudo")
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.5f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
    }

}
