package com.kata.tetris.domain.field

import com.kata.tetris.domain.Score
import com.kata.tetris.domain.field.Block.*
import com.kata.tetris.domain.field.Field.Companion.DEFAULT_WIDTH
import com.kata.tetris.domain.field.Field.Companion.DEFAULT_HEIGHT
import com.kata.tetris.domain.tetromino.ShapeTest
import com.kata.tetris.domain.tetromino.Tetromino
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*
import java.util.function.Supplier

internal class FieldTest {

    companion object {

        fun moveAsPossible(field: Field, moveAction: (Field) -> Optional<Field>): Field {
            var nextField = field
            var possible = moveAction.invoke(nextField)
            while (possible.isPresent) {
                nextField = possible.get()
                possible = moveAction.invoke(nextField)
            }
            return nextField
        }
    }

    private val shapeT = ShapeTest.createShapeT()
    private val shapeI = ShapeTest.createShapeI()

    @Test
    fun should_have_only_empty_blocks() {
        val field = Field(Tetromino.createNewTetrominoAtTop(shapeT))

        (1 until DEFAULT_HEIGHT).forEach { row ->
            (1 until DEFAULT_WIDTH).forEach { column ->
                assertThat(field.blockAt(row, column)).isEqualTo(EMPTY)
            }
        }
    }

    @Test
    fun should_place_some_tetromino_at_top() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeT)

        val field = Field(tetromino).startGame()

        assertThat(field.blockAt(23, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 3)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 5)).isEqualTo(MOVING)
    }

    @Test
    fun should_rotate_some_tetromino() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeT)

        val field = Field(tetromino).rotateTetromino().get()

        assertThat(field.blockAt(23, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 5)).isEqualTo(MOVING)
    }

    @Test
    fun should_move_down_some_tetromino() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeT)
        val field = Field(tetromino).moveTetrominoDown().get()

        assertThat(field.blockAt(22, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 3)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 5)).isEqualTo(MOVING)
    }

    @Test
    fun should_move_some_tetromino_until_reaching_the_ground() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeT)

        val field = moveAsPossible(Field(tetromino)) { it.moveTetrominoDown() }

        assertThat(field.blockAt(1, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(0, 3)).isEqualTo(MOVING)
        assertThat(field.blockAt(0, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(0, 5)).isEqualTo(MOVING)
    }

    @Test
    fun should_move_some_tetromino_to_the_left() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeT)
        val field = Field(tetromino).moveTetrominoLeft().get()

        assertThat(field.blockAt(23, 3)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 2)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 3)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 4)).isEqualTo(MOVING)
    }

    @Test
    fun should_not_move_some_tetromino_passed_the_left_limits_of_the_field() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeI).rotate()

        val field = moveAsPossible(Field(tetromino)) { it.moveTetrominoLeft() }

        assertThat(field.blockAt(23, 0)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 0)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 0)).isEqualTo(MOVING)
        assertThat(field.blockAt(20, 0)).isEqualTo(MOVING)
    }

    @Test
    fun should_move_some_tetromino_to_the_right() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeT)

        val field = Field(tetromino).moveTetrominoRight().get()

        assertThat(field.blockAt(23, 5)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 4)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 5)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 6)).isEqualTo(MOVING)
    }

    @Test
    fun should_not_move_some_tetromino_passed_the_right_limits_of_the_field() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeI).rotate()

        val field = moveAsPossible(Field(tetromino)) { it.moveTetrominoRight() }

        assertThat(field.blockAt(23, 9)).isEqualTo(MOVING)
        assertThat(field.blockAt(22, 9)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 9)).isEqualTo(MOVING)
        assertThat(field.blockAt(20, 9)).isEqualTo(MOVING)
    }

    @Test
    fun should_do_left_wall_kick_when_rotating_if_possible() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeI).rotate()

        val field = moveAsPossible(Field(tetromino)) { it.moveTetrominoLeft() }.rotateTetromino().get()

        assertThat(field.blockAt(21, 0)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 1)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 2)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 3)).isEqualTo(MOVING)
    }

    @Test
    fun should_do_right_wall_kick_when_rotating_if_possible() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeI).rotate()

        val field = moveAsPossible(Field(tetromino)) { it.moveTetrominoRight() }.rotateTetromino().get()

        assertThat(field.blockAt(21, 6)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 7)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 8)).isEqualTo(MOVING)
        assertThat(field.blockAt(21, 9)).isEqualTo(MOVING)
    }

    @Test
    fun should_fix_tetromino_which_have_reached_the_ground() {
        //given
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeT)
        val field = moveAsPossible(Field(tetromino)) { it.moveTetrominoDown() }

        //when
        val result = field.moveDownAutomatically(Supplier { shapeT })

        //then
        assertThat(result.score).isEqualTo(Score(0))
        assertThat(result.full).isFalse()
        assertThat(result.field.blockAt(1, 4)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(0, 3)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(0, 4)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(0, 5)).isEqualTo(FIXED)
    }

    @Test
    fun should_fix_tetromino_which_have_reached_fixed_blocks() {
        //given
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeI).rotate()
        var field = moveAsPossible(Field(tetromino)) { it.moveTetrominoDown() }
        field = field.moveDownAutomatically(Supplier { shapeT }).field

        //when
        field = moveAsPossible(field) { it.moveTetrominoDown() }
        val result = field.moveDownAutomatically(Supplier { shapeT })

        //then
        assertThat(result.score).isEqualTo(Score(0))
        assertThat(result.full).isFalse()
        assertThat(result.field.blockAt(5, 4)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(4, 3)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(4, 4)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(4, 5)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(3, 5)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(2, 5)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(1, 5)).isEqualTo(FIXED)
        assertThat(result.field.blockAt(0, 5)).isEqualTo(FIXED)
    }

    @Test
    fun should_remove_full_lines_and_return_the_number_of_lines() {
        val tetromino = Tetromino.createNewTetrominoAtTop(shapeI).rotate()
        var field = Field(tetromino).startGame()

        //given the 5 first lines except first column
        field = (0..4).flatMap { row ->
            (1 until DEFAULT_WIDTH).map { Position(row, it) }
        }.fold(field, { f, p -> f.fixedBlockAt(p) })
        (0..4).forEach { row ->
            (1 until DEFAULT_WIDTH).forEach { column ->
                assertThat(field.blockAt(row, column)).isEqualTo(FIXED)
            }
            assertThat(field.blockAt(row, 0)).isEqualTo(EMPTY)
        }

        //when fixing on the ground
        field = moveAsPossible(field) { it.moveTetrominoLeft() }
        field = moveAsPossible(field) { it.moveTetrominoDown() }
        val result = field.moveDownAutomatically(Supplier { shapeT })

        //then
        assertThat(result.score).isEqualTo(Score(800))
        assertThat(result.full).isFalse()
        // The first 4 lines has been removed and the remaining lines is now at bottom
        (1 until DEFAULT_WIDTH).forEach { column ->
            assertThat(result.field.blockAt(0, column)).isEqualTo(FIXED)
            (1..4).forEach { row ->
                assertThat(result.field.blockAt(row, column)).isEqualTo(EMPTY)
            }
        }
    }

}