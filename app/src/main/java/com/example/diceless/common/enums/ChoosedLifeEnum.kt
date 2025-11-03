package com.example.diceless.common.enums

enum class ChosenLifeEnum(val textLife: String) {
    TWENTY_LIFE(textLife = "20"),
    THIRTY_LIFE(textLife = "30"),
    FOURTY_LIFE(textLife = "40");

    companion object {
        /**
         * Verifica se o valor é:
         * - Não vazio
         * - Não igual a nenhum textLife existente
         */
        fun isCustomLifeValid(input: String): Boolean {
            if (input.isBlank()) return false
            return values().none { it.textLife == input.trim() }
        }

        /**
         * (Opcional) Retorna o enum se existir, ou null se for custom
         */
        fun fromTextLife(text: String): ChosenLifeEnum? {
            return values().find { it.textLife == text }
        }

        fun contains(text: String): Boolean {
            return values().contains(fromTextLife(text))
        }
    }
}