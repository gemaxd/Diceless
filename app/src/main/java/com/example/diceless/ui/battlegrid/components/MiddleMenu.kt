package com.example.diceless.ui.battlegrid.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.MenuItemEnum
import com.example.diceless.domain.model.MenuItem

@Composable
fun MiddleMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandMiddleMenu: () -> Unit,
    firstRow: List<MenuItem> = emptyList(),
    secondRow: List<MenuItem> = emptyList()
) {
    val translation by animateFloatAsState(if (expanded) 0f else -50f, label = "translation")
    val translationExtremity by animateFloatAsState(if (expanded) 0f else -100f, label = "translationExtremity")
    val scale by animateFloatAsState(if (expanded) 1f else 0f, label = "scale")
    val controlIcon = if (expanded) Icons.Outlined.Close else Icons.Outlined.Add

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .background(Color.DarkGray, RoundedCornerShape(25)),
            verticalAlignment = Alignment.CenterVertically
        ){
            firstRow.forEachIndexed { index, option ->
                AnimatedVisibility(visible = expanded) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .offset(x = if (index == 0) translationExtremity.dp else translation.dp)
                            .scale(scale)
                    ){
                        option.icon()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(25))
                    .clickable {
                        onExpandMiddleMenu.invoke()
                    },
                contentAlignment = Alignment.Center
            ){
                AnimatedContent(targetState = controlIcon, label = "centerIcon") { icon ->
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = icon,
                        contentDescription = "Expand middle menu",
                        tint = Color.White
                    )
                }
            }

            secondRow.forEachIndexed { index,  option ->
                AnimatedVisibility(visible = expanded) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .offset(x = if (index == 0) translationExtremity.dp else translation.dp)
                            .scale(scale)
                    ){
                        option.icon()
                    }
                }
            }
        }
    }
}

@Composable
fun MiddleMenuSolo(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandMiddleMenu: () -> Unit,
    firstRow: List<MenuItem> = emptyList()
) {
    val translation by animateFloatAsState(if (expanded) 0f else -50f, label = "translation")
    val translationExtremity by animateFloatAsState(if (expanded) 0f else -100f, label = "translationExtremity")
    val scale by animateFloatAsState(if (expanded) 1f else 0f, label = "scale")
    val controlIcon = if (expanded) Icons.Outlined.Close else Icons.Outlined.Add

    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .background(Color.DarkGray, RoundedCornerShape(25)),
            verticalAlignment = Alignment.Top
        ){
            firstRow.forEachIndexed { index, option ->
                AnimatedVisibility(visible = expanded) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .offset(x = if (index == 0) translationExtremity.dp else translation.dp)
                            .scale(scale)
                    ){
                        option.icon()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(25))
                    .clickable {
                        onExpandMiddleMenu.invoke()
                    },
                contentAlignment = Alignment.Center
            ){
                AnimatedContent(targetState = controlIcon, label = "centerIcon") { icon ->
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = icon,
                        contentDescription = "Expand middle menu",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MiddleMenuPreview() {
    MiddleMenu(
        expanded = true,
        onExpandMiddleMenu = {},
        firstRow = listOf(
                MenuItem(
                    action = {},
                    menuItemEnum = MenuItemEnum.HISTORY
                ),
                MenuItem(
                    icon = {
                        Icon(
                            contentDescription = "",
                            imageVector = Icons.Outlined.Search,
                        )
                    },
                    action = {},
                    menuItemEnum = MenuItemEnum.HISTORY
                ),
            )

    )
}
