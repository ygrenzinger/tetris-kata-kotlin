package com.kata.tetris

import com.kata.tetris.domain.Command
import com.kata.tetris.domain.Tetris
import com.kata.tetris.domain.tetromino.RandomTetrominoGenerator
import com.kata.tetris.domain.tetromino.ShapeFactory
import com.kata.tetris.infra.FileShapeLoader
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Size
import org.assertj.core.api.Assertions.assertThat
import java.util.function.Supplier

class JqwikPropertyTest {

    private val shapeLoader = FileShapeLoader()
    private val shapeFactory = ShapeFactory(shapeLoader)
    private val generator = RandomTetrominoGenerator(shapeFactory)

    @Property
    @JvmSuppressWildcards(suppress = true)
    fun playing_tetris_until_gameover(@ForAll @Size(100) commands: List<Command>) {
        val tetris = Tetris(fakeUpdateUI, Supplier { generator.randomShape() })
        assertThat(tetris.isOnGoingGame()).isFalse()
        tetris.startGame()
        assertThatGameIsOverOrOnGoing(tetris)
        commands.forEach {
            tetris.applyCommand(it)
            tetris.updateGame()
            assertThatGameIsOverOrOnGoing(tetris)
        }
    }

    private fun assertThatGameIsOverOrOnGoing(tetris: Tetris) {
        if (tetris.gameOver) {
            println("Game Over")
            assertThat(tetris.isOnGoingGame()).isFalse()
        } else {
            assertThat(tetris.isOnGoingGame()).isTrue()
        }
    }
}