package com.example.diceless.features.middlemenu.mvi

sealed class BattleGridSheetState {
    object None : BattleGridSheetState()
    object History : BattleGridSheetState()
    object Settings : BattleGridSheetState()
    object Restart : BattleGridSheetState()
    object DiceRoll : BattleGridSheetState()
    object Schemes : BattleGridSheetState()
}