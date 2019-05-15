package com.kata.tetris.ui

import com.kata.tetris.domain.Tetris
import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

import com.kata.tetris.domain.Command.*

class KeyEventHandler(private val tetris: Tetris) : EventHandler<KeyEvent> {

    override fun handle(key: KeyEvent) {
        if (tetris.isOnGoingGame()) {
            if (key.code == KeyCode.UP) {
                tetris.applyCommand(ROTATE)
            }
            if (key.code == KeyCode.RIGHT) {
                tetris.applyCommand(RIGHT)
            }
            if (key.code == KeyCode.LEFT) {
                tetris.applyCommand(LEFT)
            }
            if (key.code == KeyCode.DOWN) {
                tetris.applyCommand(DOWN)
            }
        }

    }
}
