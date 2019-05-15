package com.kata.tetris.domain.tetromino

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.assertj.core.api.Assertions.assertThat
import java.util.*

internal class RandomTetrominoGeneratorTest {

    lateinit var mockShapeFactory: ShapeFactory
    lateinit var generator: RandomTetrominoGenerator

    @BeforeEach
    fun before() {
        mockShapeFactory = mockk()

        val shapes = Arrays.asList(ShapeTest.createShapeT(), ShapeTest.createShapeI())
        val time = System.currentTimeMillis()
        every { mockShapeFactory.allShapes() } returns shapes
        println("Time elapsed in ms : " + (System.currentTimeMillis() - time))
        generator = RandomTetrominoGenerator(mockShapeFactory)
    }

    @Test
    fun should_first_fill_bags_of_tetrimino() {
        verify(exactly = 1) { mockShapeFactory.allShapes() }
    }

    @Test
    fun should_return_a_sequence_of_shapes_permuted_randomly() {
        assertThat(generator.randomShape()).isIn(ShapeTest.createShapeI(), ShapeTest.createShapeT())
        assertThat(generator.randomShape()).isIn(ShapeTest.createShapeI(), ShapeTest.createShapeT())
    }

    @Test
    fun should_refill_bag_when_empty() {
        for (i in 0..2) {
            generator.randomShape()
        }
        verify(exactly = 2) { mockShapeFactory.allShapes() }
    }
}