package com.kata.tetris.domain.tetromino

import org.junit.jupiter.api.Test

import com.kata.tetris.domain.tetromino.Orientation.*
import org.assertj.core.api.Assertions.assertThat

internal class OrientationTest {

    @Test
    fun should_rotate_through_all_orientation() {
        var orientation = NORTH
        orientation = orientation.next()
        assertThat(orientation).isEqualTo(EAST)
        orientation = orientation.next()
        assertThat(orientation).isEqualTo(SOUTH)
        orientation = orientation.next()
        assertThat(orientation).isEqualTo(WEST)
        orientation = orientation.next()
        assertThat(orientation).isEqualTo(NORTH)
    }

}