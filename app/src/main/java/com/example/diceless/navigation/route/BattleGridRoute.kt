package com.example.diceless.navigation.route

internal sealed class BattleGridRoute(
    val route: String
) {
    object BattleGrid : BattleGridRoute("battle_grid")
}