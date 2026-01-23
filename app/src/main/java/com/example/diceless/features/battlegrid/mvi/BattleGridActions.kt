package com.example.diceless.features.battlegrid.mvi

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard

sealed class BattleGridActions {
    data class OnLifeIncreased(val player: PlayerData) : BattleGridActions()
    data class OnLifeDecreased(val player: PlayerData) : BattleGridActions()
    data class OnCounterSelected(val player: PlayerData, val counter: CounterData) : BattleGridActions()
    data class OnCounterToggled(val player: PlayerData, val counter: CounterData) : BattleGridActions()
    data class OnCounterIncrement(val player: PlayerData, val counter: CounterData) : BattleGridActions()
    data class OnCounterDecrement(val player: PlayerData, val counter: CounterData) : BattleGridActions()
    data class OnCommanderDamageChanged(
        val receivingPlayer: PlayerData, // Quem está recebendo o dano
        val playerName: String,     // O ID de quem causou o dano
        val amount: Int                  // A quantidade (+1 ou -1)
    ) : BattleGridActions()
    data object OnRestart: BattleGridActions()
    data class OnStartingLifeChanged(val life: Int) : BattleGridActions()
    data class OnAllowSelfCommanderDamageChanged(val enabled: Boolean) : BattleGridActions()
    data class OnLinkCommanderDamageToLifeChanged(val enabled: Boolean) : BattleGridActions()
    data object ToggleMonarchCounter: BattleGridActions()
    data class OnUpdateScheme(val schemeEnum: SchemeEnum) : BattleGridActions()
    data class OnBackgroundSelected(val player: PlayerData, val card: ScryfallCard) : BattleGridActions()
}
