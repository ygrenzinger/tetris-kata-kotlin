package com.kata.tetris.domain.tetromino

import com.kata.tetris.domain.field.Field
import com.kata.tetris.domain.field.Position
import java.util.*

data class Tetromino(private val shape: Shape,
                     private val rowPosition: Int,
                     private val columnPosition: Int,
                     private val orientation: Orientation) {
    companion object {
        fun createNewTetrominoAtTop(shape: Shape) = Tetromino(shape, Field.DEFAULT_HEIGHT - 1, 3)
    }
    private val positions: Set<Position>

    init {
        this.positions = computeCurrentPositions(shape, rowPosition, columnPosition, orientation)
    }

    constructor(shape: Shape, rowPosition: Int, columnPosition: Int) : this(shape, rowPosition, columnPosition, Orientation.NORTH)

    fun positionsOnField(): Set<Position> {
        return positions
    }

    fun rotate(): Tetromino {
        return Tetromino(shape, rowPosition, columnPosition, orientation.next())
    }

    fun leftWallKick(): Tetromino {
        var future = this
        for (i in 0 until shape.leftWallKickPossibleShift(orientation)) {
            future = future.moveRight()
        }
        return future.rotate()
    }

    fun rightWallKick(): Tetromino {
        var future = this
        for (i in 0 until shape.rightWallKickPossibleShift(orientation)) {
            future = future.moveLeft()
        }
        return future.rotate()
    }

    fun moveLeft(): Tetromino {
        return Tetromino(shape, rowPosition, columnPosition - 1, orientation)
    }

    fun moveRight(): Tetromino {
        return Tetromino(shape, rowPosition, columnPosition + 1, orientation)
    }

    fun moveDown(): Tetromino {
        return Tetromino(shape, rowPosition - 1, columnPosition, orientation)
    }

    private fun computeCurrentPositions(shape: Shape, rowPosition: Int, columnPosition: Int, orientation: Orientation): Set<Position> {
        val blocks = shape.blocksByOrientation(orientation)
        val positions = mutableSetOf<Position>()
        for (i in 0 until shape.size) {
            for (j in 0 until shape.size) {
                if (blocks[i][j]) {
                    val row = rowPosition - i
                    val column = columnPosition + j
                    positions.add(Position(row, column))
                }
            }
        }
        return positions
    }
}
