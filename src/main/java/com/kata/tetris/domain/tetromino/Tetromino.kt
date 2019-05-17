package com.kata.tetris.domain.tetromino

import com.kata.tetris.domain.field.Field
import com.kata.tetris.domain.field.Position

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

    fun positionsOnField() = positions

    fun rotate() = Tetromino(shape, rowPosition, columnPosition, orientation.next())

    fun leftWallKick() =
            (0..shape.leftWallKickPossibleShift(orientation))
                    .fold(this, { tetromino, _ -> tetromino.moveRight() })
                    .rotate()

    fun rightWallKick() =
            (0..shape.rightWallKickPossibleShift(orientation))
                    .fold(this, { tetromino, _ -> tetromino.moveLeft() })
                    .rotate()

    fun moveLeft() = Tetromino(shape, rowPosition, columnPosition - 1, orientation)

    fun moveRight() = Tetromino(shape, rowPosition, columnPosition + 1, orientation)

    fun moveDown() = Tetromino(shape, rowPosition - 1, columnPosition, orientation)

    private fun computeCurrentPositions(shape: Shape, rowPosition: Int, columnPosition: Int, orientation: Orientation): Set<Position> {
        val blocks = shape.blocksByOrientation(orientation)
        return (0 until shape.size)
                .flatMap { i -> (0 until shape.size).map { j -> Pair(i, j) } }
                .filter { (i,j) -> blocks[i][j] }
                .map { (i,j) ->  Position(rowPosition - i, columnPosition + j) }
                .toSet()
    }
}
