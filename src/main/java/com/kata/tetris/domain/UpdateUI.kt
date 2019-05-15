package com.kata.tetris.domain

import com.kata.tetris.domain.field.Field

interface UpdateUI {

    fun updateUI(field: Field, score: Score, gameOver: Boolean)

}
