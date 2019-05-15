package com.kata.tetris.domain

import com.kata.tetris.domain.field.Field
import com.kata.tetris.domain.tetromino.Shape
import com.kata.tetris.domain.tetromino.Tetromino
import com.kata.tetris.domain.tetromino.Tetromino.Companion.createNewTetrominoAtTop

import java.util.function.Supplier

class Tetris(private val updateUI: UpdateUI, private val shapeSupplier: Supplier<Shape>) {
    private var tetromino: Tetromino = createNewTetrominoAtTop()
    internal var field: Field = Field(tetromino)
        private set
    private var score: Score = Score()
    private var started: Boolean = false
    var gameOver: Boolean = false
        private set

    fun isOnGoingGame() = started && !gameOver

    fun startGame() {
        started = true
        field = field.startGame()
        updateUI()
    }

    fun updateGame() {
        val result = field.moveDownAutomatically(shapeSupplier)
        field = result.field
        score += result.score
        gameOver = result.full
        updateUI()
    }

    fun applyCommand(command: Command) {
        when (command) {
            Command.ROTATE -> field.rotateTetromino()
            Command.LEFT -> field.moveTetrominoLeft()
            Command.RIGHT -> field.moveTetrominoRight()
            Command.DOWN -> field.moveTetrominoDown()
        }.ifPresent {
            field = it
            updateUI()
        }
    }

    private fun updateUI() {
        this.updateUI.updateUI(this.field, score, gameOver)
    }

    private fun createNewTetrominoAtTop() = createNewTetrominoAtTop(shapeSupplier.get())
}
