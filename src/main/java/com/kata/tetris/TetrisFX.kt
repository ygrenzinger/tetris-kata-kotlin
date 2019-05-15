package com.kata.tetris

import com.kata.tetris.domain.Tetris
import com.kata.tetris.domain.tetromino.RandomTetrominoGenerator
import com.kata.tetris.domain.tetromino.ShapeFactory
import com.kata.tetris.infra.FileShapeLoader
import com.kata.tetris.ui.KeyEventHandler
import com.kata.tetris.ui.TetrisUI
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit.SECONDS
import java.util.function.Supplier

class TetrisFX : Application() {

    private lateinit var tetris: Tetris
    private lateinit var tetrisUI: TetrisUI
    private lateinit var randomTetrominoGenerator: RandomTetrominoGenerator

    private lateinit var updateUIHandle: ScheduledFuture<*>
    private lateinit var scheduler: ScheduledExecutorService

    private val update = {
        tetris.updateGame()
        if (tetris.gameOver) {
            stop()
        }
    }

    override fun start(stage: Stage) {
        val scene = Scene(tetrisUI)
        scene.addEventHandler(KeyEvent.KEY_PRESSED, KeyEventHandler(tetris))
        stage.scene = scene
        stage.title = "Tetris"
        stage.show()
        tetris.startGame()
        scheduler = Executors.newScheduledThreadPool(1)
        updateUIHandle = scheduler.scheduleAtFixedRate(update, 1, 1, SECONDS)
    }

    override fun init() {
        val shapeLoader = FileShapeLoader()
        val shapeFactory = ShapeFactory(shapeLoader)
        randomTetrominoGenerator = RandomTetrominoGenerator(shapeFactory)
        tetrisUI = TetrisUI()
        tetris = Tetris(tetrisUI, Supplier { randomTetrominoGenerator.randomShape() })
    }

    override fun stop() {
        updateUIHandle.cancel(true)
        scheduler.shutdown()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(TetrisFX::class.java)
        }
    }
}
