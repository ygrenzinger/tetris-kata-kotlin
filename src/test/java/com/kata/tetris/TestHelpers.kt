package com.kata.tetris

import com.kata.tetris.domain.Score
import com.kata.tetris.domain.UpdateUI
import com.kata.tetris.domain.field.Field

val fakeUpdateUI = object : UpdateUI {
    override fun updateUI(field: Field, score: Score, gameOver: Boolean) {

    }
}