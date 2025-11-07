package com.example.diceless.navigation.route

internal sealed class CardSearchRoute(
    val route: String
) {
    object CardSearch : CardSearchRoute("card_search")
}