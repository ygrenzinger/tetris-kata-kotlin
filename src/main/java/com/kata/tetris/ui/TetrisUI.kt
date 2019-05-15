package com.kata.tetris.ui

import com.kata.tetris.domain.Score
import com.kata.tetris.domain.UpdateUI
import com.kata.tetris.domain.field.Block
import com.kata.tetris.domain.field.Field
import com.kata.tetris.domain.field.Field.Companion.DEFAULT_HEIGHT
import com.kata.tetris.domain.field.Field.Companion.DEFAULT_WIDTH
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import java.util.*

class TetrisUI : GridPane(), UpdateUI {
    private val cellGrid: List<List<Label>>
    private val labelScore: Label

    init {
        this.cellGrid = initGrid()
        this.labelScore = initLabelScore()
        initGridPane(this.labelScore)
    }

    override fun updateUI(field: Field, score: Score, gameOver: Boolean) {
        Platform.runLater {
            updatingGrid(field)
            if (gameOver) {
                labelScore.text = "Lost at $score"
            } else {
                labelScore.text = SCORE_LABEL_TEXT + score
            }
        }
    }

    private fun updatingGrid(field: Field) {
        for (row in 0 until DEFAULT_HEIGHT) {
            for (column in 0 until DEFAULT_WIDTH) {
                updatingBlock(field, row, column)
            }
        }
    }

    private fun updatingBlock(field: Field, row: Int, column: Int) {
        when (field.blockAt(row, column)) {
            Block.FIXED -> cellGrid[row][column].style = "-fx-background-color : grey"
            Block.EMPTY -> cellGrid[row][column].style = "-fx-background-color : white"
            else -> cellGrid[row][column].style = "-fx-background-color : blue"
        }
    }

    private fun initGridPane(labelScore: Label) {
        hgap = GRID_SIZE_GAP.toDouble()
        vgap = GRID_SIZE_GAP.toDouble()
        alignment = Pos.CENTER

        style = "-fx-background-color : black"
        setPrefSize(
                (DEFAULT_WIDTH * (CELL_SIZE + GRID_SIZE_GAP)).toDouble(),
                (DEFAULT_HEIGHT * (CELL_SIZE + GRID_SIZE_GAP) + 30).toDouble()
        )
        add(labelScore, 0, DEFAULT_HEIGHT, DEFAULT_WIDTH, 1)
    }

    private fun initGrid(): List<List<Label>> {
        val cellGrid = ArrayList<List<Label>>()
        for (row in DEFAULT_HEIGHT - 1 downTo 0) {
            val cellRow = ArrayList<Label>()
            cellGrid.add(cellRow)
            for (column in 0 until DEFAULT_WIDTH) {
                val label = Label()

                label.style = "-fx-background-color : white"
                label.setPrefSize(CELL_SIZE.toDouble(), CELL_SIZE.toDouble())

                cellRow.add(label)
                add(label, column, row)
            }
        }
        return cellGrid
    }

    companion object {

        private const val GRID_SIZE_GAP = 3
        private const val CELL_SIZE = 20
        private const val STYLE_SCORE = "-fx-text-fill: WHITE;" +
                "-fx-font-size: 26 px;" +
                "-fx-text-alignment: center"
        private const val SCORE_LABEL_TEXT = "SCORE : "

        private fun initLabelScore(): Label {
            val labelScore = Label(SCORE_LABEL_TEXT + 0)
            labelScore.style = STYLE_SCORE
            return labelScore
        }
    }
}
