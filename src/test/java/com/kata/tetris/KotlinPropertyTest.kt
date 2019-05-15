package com.kata.tetris

import com.kata.tetris.domain.Command
import com.kata.tetris.domain.Tetris
import com.kata.tetris.domain.tetromino.RandomTetrominoGenerator
import com.kata.tetris.domain.tetromino.ShapeFactory
import com.kata.tetris.infra.FileShapeLoader
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec
import java.util.function.Supplier

class KotlinPropertyTest : StringSpec() {

    private val shapeLoader = FileShapeLoader()
    private val shapeFactory = ShapeFactory(shapeLoader)
    private val generator = RandomTetrominoGenerator(shapeFactory)

    init {
        "Testing Tetris correctness" {
            val genCommand = Gen.enum<Command>()
            //TODO : configure the size of list https://github.com/kotlintest/kotlintest
            val list = Gen.list(genCommand)
            forAll(100, list) { commands: List<Command> ->
                val tetris = Tetris(fakeUpdateUI, Supplier { generator.randomShape() })
                tetris.startGame()
                commands.forEach {
                    tetris.applyCommand(it)
                    tetris.updateGame()
                }
                isInCorrectState(tetris)
            }
        }

    }

    private fun isInCorrectState(tetris: Tetris): Boolean {
        return if (tetris.gameOver) {
            !tetris.isOnGoingGame()
        } else {
            tetris.isOnGoingGame()
        }
    }
}