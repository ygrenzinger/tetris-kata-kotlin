package com.kata.tetris.domain.tetromino

import org.junit.jupiter.api.Test

import java.util.HashMap

import org.assertj.core.api.Assertions.assertThat

class ShapeTest {

    @Test
    fun should_create_shape() {
        val shape = createShapeT()
        assertThat(shape.type).isEqualTo(ShapeType.T)
        assertThat(shape.size).isEqualTo(3)
        assertThat(shape.shapeByOrientations).hasSize(4)
        val northShape = arrayOf(" x \n", "xxx\n", "   \n")
        assertShapeForOrientation(shape, Orientation.NORTH, northShape)
        val eastShape = arrayOf(" x \n", " xx\n", " x \n")
        assertShapeForOrientation(shape, Orientation.EAST, eastShape)
        val southShape = arrayOf("   \n", "xxx\n", " x \n")
        assertShapeForOrientation(shape, Orientation.SOUTH, southShape)
        val westShape = arrayOf(" x \n", "xx \n", " x \n")
        assertShapeForOrientation(shape, Orientation.WEST, westShape)
    }

    companion object {

        fun assertShapeForOrientation(shape: Shape, north: Orientation, expected: Array<String>) {
            val expectedString = expected.joinToString("")
            assertThat(shape.shapeForOrientationToString(north)).isEqualTo(expectedString)
        }

        fun createShapeT(): Shape {
            val shapeByOrientations = HashMap<Orientation, ShapeForm>()
            val northShape = arrayOf(booleanArrayOf(false, true, false), booleanArrayOf(true, true, true), booleanArrayOf(false, false, false))
            shapeByOrientations[Orientation.NORTH] = ShapeForm(northShape)
            val eastShape = arrayOf(booleanArrayOf(false, true, false), booleanArrayOf(false, true, true), booleanArrayOf(false, true, false))
            shapeByOrientations[Orientation.EAST] = ShapeForm(eastShape)
            val southShape = arrayOf(booleanArrayOf(false, false, false), booleanArrayOf(true, true, true), booleanArrayOf(false, true, false))
            shapeByOrientations[Orientation.SOUTH] = ShapeForm(southShape)
            val westShape = arrayOf(booleanArrayOf(false, true, false), booleanArrayOf(true, true, false), booleanArrayOf(false, true, false))
            shapeByOrientations[Orientation.WEST] = ShapeForm(westShape)
            return Shape(ShapeType.T, 3, shapeByOrientations)
        }

        fun createShapeI(): Shape {
            val shapeByOrientations = HashMap<Orientation, ShapeForm>()
            val northShape = arrayOf(booleanArrayOf(false, false, false, false), booleanArrayOf(true, true, true, true), booleanArrayOf(false, false, false, false), booleanArrayOf(false, false, false, false))
            shapeByOrientations[Orientation.NORTH] = ShapeForm(northShape)
            val eastShape = arrayOf(booleanArrayOf(false, false, true, false), booleanArrayOf(false, false, true, false), booleanArrayOf(false, false, true, false), booleanArrayOf(false, false, true, false))
            shapeByOrientations[Orientation.EAST] = ShapeForm(eastShape)
            val southShape = arrayOf(booleanArrayOf(false, false, false, false), booleanArrayOf(false, false, false, false), booleanArrayOf(true, true, true, true), booleanArrayOf(false, false, false, false))
            shapeByOrientations[Orientation.SOUTH] = ShapeForm(southShape)
            val westShape = arrayOf(booleanArrayOf(false, true, false, false), booleanArrayOf(false, true, false, false), booleanArrayOf(false, true, false, false), booleanArrayOf(false, true, false, false))
            shapeByOrientations[Orientation.WEST] = ShapeForm(westShape)
            return Shape(ShapeType.I, 4, shapeByOrientations)
        }
    }

}