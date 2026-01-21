package com.example.diceless.data.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SettingsRepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "magic_life_counter_prefs"
        private const val KEY_STARTING_LIFE = "starting_life"
        private const val KEY_LINK_COMMANDER_DAMAGE = "link_commander_damage_to_life"
        private const val KEY_ALLOW_SELF_DAMAGE = "allow_self_commander_damage"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getStartingLife(): Int = prefs.getInt(KEY_STARTING_LIFE, 40)
    fun getLinkCommanderDamageToLife(): Boolean = prefs.getBoolean(KEY_LINK_COMMANDER_DAMAGE, false)
    fun getAllowSelfCommanderDamage(): Boolean = prefs.getBoolean(KEY_ALLOW_SELF_DAMAGE, false)

    fun saveStartingLife(life: Int) {
        prefs.edit { putInt(KEY_STARTING_LIFE, life) }
    }

    fun saveLinkCommanderDamageToLife(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_LINK_COMMANDER_DAMAGE, enabled) }
    }

    fun saveAllowSelfCommanderDamage(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_ALLOW_SELF_DAMAGE, enabled) }
    }
}