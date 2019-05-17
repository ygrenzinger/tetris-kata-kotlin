package com.kata.tetris.domain

import com.kata.tetris.domain.tetromino.ShapeFactory
import com.kata.tetris.domain.tetromino.ShapeTest
import com.kata.tetris.fakeUpdateUI
import com.kata.tetris.infra.FileShapeLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.function.Supplier

internal class TetrisTest {

    private val shapeLoader = FileShapeLoader()
    private val shapeFactory = ShapeFactory(shapeLoader)

    @Test
    fun playing_tetris_until_gameover() {
        val tetris = Tetris(fakeUpdateUI, Supplier { ShapeTest.createShapeT() })
        tetris.startGame()
        while (tetris.isOnGoingGame()) {
            tetris.applyCommand(Command.DOWN)
            tetris.updateGame()
        }
        assertThat(tetris.gameOver).isTrue()
    }

}