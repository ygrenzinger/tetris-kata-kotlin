package com.kata.tetris.domain.field

import com.kata.tetris.domain.Score
import com.kata.tetris.domain.field.Block.*
import com.kata.tetris.domain.tetromino.Shape
import com.kata.tetris.domain.tetromino.Tetromino
import java.util.*
import java.util.function.Supplier

data class Field(private val movingBlocks: Set<Position>,
                 private val fixedBlocks: Set<Position>,
                 private val tetromino: Tetromino) {

    data class MoveDownAutomaticallyResult(val score: Score, val field: Field, val full: Boolean)

    companion object {
        const val DEFAULT_HEIGHT = 24
        const val DEFAULT_WIDTH = 10
    }

    constructor(tetromino: Tetromino) : this(setOf(), setOf(), tetromino)

    fun startGame(): Field {
        return placeTetromino(tetromino).get()
    }

    fun rotateTetromino(): Optional<Field> {
        return actOnTretromino {
            when {
                isTetrominoLeaningOnLeftWall(tetromino) -> tetromino.leftWallKick()
                isTetrominoLeaningOnRightWall(tetromino) -> tetromino.rightWallKick()
                else -> tetromino.rotate()
            }
        }
    }

    fun moveTetrominoLeft(): Optional<Field> {
        return actOnTretromino { tetromino.moveLeft() }
    }

    fun moveTetrominoRight(): Optional<Field> {
        return actOnTretromino { tetromino.moveRight() }
    }

    fun moveTetrominoDown(): Optional<Field> {
        return actOnTretromino { tetromino.moveDown() }
    }

    fun moveDownAutomatically(shapeSupplier: Supplier<Shape>): MoveDownAutomaticallyResult {
        return moveTetrominoDown()
                .map { MoveDownAutomaticallyResult(Score(), it, false) }
                .orElseGet { fixTetromino(shapeSupplier) }
    }

    private fun actOnTretromino(action: (tetromino: Tetromino) -> Tetromino): Optional<Field> {
        val nextTetromino = action.invoke(tetromino)
        return placeTetromino(nextTetromino)
    }

    private fun placeTetromino(tetromino: Tetromino): Optional<Field> {
        if (!isPossiblePosition(tetromino)) {
            return Optional.empty()
        }
        return Optional.of(Field(tetromino.positionsOnField(), fixedBlocks, tetromino))
    }

    private fun fixTetromino(shapeSupplier: Supplier<Shape>): MoveDownAutomaticallyResult {
        val result = removeLines(fixedBlocks + movingBlocks)
        val field = Field(setOf(), result.second, Tetromino.createNewTetrominoAtTop(shapeSupplier.get()))
        return field.placeTetromino(field.tetromino)
                .map { MoveDownAutomaticallyResult(Score.buildScoreForLinesRemoved(result.first), it, false) }
                .orElse(MoveDownAutomaticallyResult(Score.buildScoreForLinesRemoved(result.first), field, true))
    }

    private fun removeLines(fixedBlocks: Set<Position>): Pair<Int, Set<Position>> {
        val firstFullRow: Int? = (0..DEFAULT_HEIGHT).firstOrNull { isRowFull(fixedBlocks, it) }
        return if (firstFullRow != null) {
            removeLines(fixedBlocks, firstFullRow, 0)
        } else {
            Pair(0, fixedBlocks);
        }
    }

    private fun removeLines(fixedBlocks: Collection<Position>,
                            rowIndex: Int,
                            numberOfLinesRemoved: Int) : Pair<Int, Set<Position>> {
        if (isRowEmpty(fixedBlocks, rowIndex)) {
            return Pair(numberOfLinesRemoved, fixedBlocks.toSet())
        }
        if (isRowFull(fixedBlocks, rowIndex)) {
            val newFixedBlocks = moveDownAllUpperLines(fixedBlocks.filter { it.row != rowIndex }, rowIndex)
            return removeLines(newFixedBlocks, rowIndex, numberOfLinesRemoved+1)
        }
        return removeLines(fixedBlocks, rowIndex+1, numberOfLinesRemoved)
    }

    private fun isRowFull(fixedBlocks: Collection<Position>, row: Int): Boolean {
        return countFixedBlocksOnRow(fixedBlocks, row) == DEFAULT_WIDTH
    }

    private fun isRowEmpty(fixedBlocks: Collection<Position>, row: Int): Boolean {
        return countFixedBlocksOnRow(fixedBlocks, row) == 0
    }

    private fun countFixedBlocksOnRow(fixedBlocks: Collection<Position>, row: Int) =
            (0..DEFAULT_WIDTH).filter { fixedBlocks.contains(Position(row, it)) }.count()

    private fun moveDownAllUpperLines(fixedBlocks: List<Position>, row: Int): List<Position> {
        return fixedBlocks.map { if (it.row > row) { Position(it.row - 1, it.column) } else { it } }
    }

    private fun isPossiblePosition(tetromino: Tetromino) = tetromino.positionsOnField().all { this.isPossiblePosition(it) }

    private fun isPossiblePosition(position: Position) = isInsideField(position) && isNotOverlappingFixedBlock(position)

    private fun isNotOverlappingFixedBlock(position: Position) = blockAt(position) != FIXED

    private fun isInsideField(position: Position) = position.row >= 0 && position.column >= 0 && position.column < DEFAULT_WIDTH

    private fun isTetrominoLeaningOnRightWall(tetromino: Tetromino) = tetromino.positionsOnField().any { it.column == DEFAULT_WIDTH - 1 }

    private fun isTetrominoLeaningOnLeftWall(tetromino: Tetromino) = tetromino.positionsOnField().any { it.column == 0 }

    internal fun fixedBlockAt(position: Position): Field {
        return Field(movingBlocks, fixedBlocks + position, tetromino)
    }

    internal fun blockAt(row: Int, column: Int) = blockAt(Position(row, column))

    private fun blockAt(position: Position): Block {
        val block = when {
            movingBlocks.contains(position) -> MOVING
            fixedBlocks.contains(position) -> FIXED
            else -> EMPTY
        }
        return block
    }

}
