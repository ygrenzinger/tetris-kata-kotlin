package com.kata.tetris.domain

import com.kata.tetris.domain.field.Block
import com.kata.tetris.domain.field.Field
import com.kata.tetris.domain.tetromino.ShapeFactory
import com.kata.tetris.domain.tetromino.ShapeTest
import com.kata.tetris.fakeUpdateUI
import com.kata.tetris.infra.FileShapeLoader
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import java.util.function.Supplier
import java.util.stream.IntStream

internal class TetrisTest {

    private val shapeLoader = FileShapeLoader()
    private val shapeFactory = ShapeFactory(shapeLoader)

    @Test
    fun playing_tetris_until_gameover() {
        val tetris = Tetris(fakeUpdateUI, Supplier{ ShapeTest.createShapeT() })
        tetris.startGame()
        while (tetris.isOnGoingGame()) {
            tetris.applyCommand(Command.DOWN)
            tetris.updateGame()
        }
        assertThat(tetris.gameOver).isTrue()
    }

}