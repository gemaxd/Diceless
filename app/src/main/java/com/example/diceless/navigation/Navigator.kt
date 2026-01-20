package com.example.diceless.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(
    private val backStack: MutableList<NavKey>
) {
    fun navigate(route: NavKey) {
        backStack.add(route)
    }

    fun pop() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }
}
