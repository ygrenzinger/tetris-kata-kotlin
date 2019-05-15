package com.kata.tetris.domain.tetromino

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ShapeFormTest {

    @Test
    fun equality_and_hash_code_works_as_expected() {
        val first = ShapeForm(arrayOf(
                booleanArrayOf(false, true, false),
                booleanArrayOf(true, true, false)
        ))
        val second = ShapeForm(arrayOf(
                booleanArrayOf(false, true, false),
                booleanArrayOf(true, true, false)
        ))
        val third = ShapeForm(arrayOf(
                booleanArrayOf(false, true, false),
                booleanArrayOf(true, false, false))
        )
        assertThat(first == second).isTrue()
        assertThat(first.hashCode() == second.hashCode()).isTrue()

        assertThat(first == third).isFalse()
        assertThat(first.hashCode() == third.hashCode()).isFalse()
    }

}