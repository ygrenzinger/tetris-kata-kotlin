package com.kata.tetris.infra

import com.kata.tetris.domain.tetromino.Orientation
import com.kata.tetris.domain.tetromino.Shape
import com.kata.tetris.domain.tetromino.ShapeTest.Companion.assertShapeForOrientation
import org.junit.jupiter.api.Test

import java.net.URISyntaxException
import java.nio.file.Paths

import com.kata.tetris.domain.tetromino.ShapeType.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows

internal class ShapeLoaderTest {

    private val shapeLoader = FileShapeLoader()

    @Test
    fun should_load_I_shape() {
        val filename = "I.json"
        val shape = loadShape(filename)
        assertThat(shape.type).isEqualTo(I)
        assertThat(shape.size).isEqualTo(4)
        assertThat(shape.shapeByOrientations).hasSize(4)
        val northShape = arrayOf("    \n", "xxxx\n", "    \n", "    \n")
        assertShapeForOrientation(shape, Orientation.NORTH, northShape)
        val eastShape = arrayOf("  x \n", "  x \n", "  x \n", "  x \n")
        assertShapeForOrientation(shape, Orientation.EAST, eastShape)
        val southShape = arrayOf("    \n", "    \n", "xxxx\n", "    \n")
        assertShapeForOrientation(shape, Orientation.SOUTH, southShape)
        val westShape = arrayOf(" x  \n", " x  \n", " x  \n", " x  \n")
        assertShapeForOrientation(shape, Orientation.WEST, westShape)
    }

    @Test
    fun should_load_J_shape() {
        val filename = "J.json"
        val shape = loadShape(filename)
        assertThat(shape.type).isEqualTo(J)
        assertThat(shape.size).isEqualTo(3)
        assertThat(shape.shapeByOrientations).hasSize(4)
        val northShape = arrayOf("x  \n", "xxx\n", "   \n")
        assertShapeForOrientation(shape, Orientation.NORTH, northShape)
        val eastShape = arrayOf(" xx\n", " x \n", " x \n")
        assertShapeForOrientation(shape, Orientation.EAST, eastShape)
        val southShape = arrayOf("   \n", "xxx\n", "  x\n")
        assertShapeForOrientation(shape, Orientation.SOUTH, southShape)
        val westShape = arrayOf(" x \n", " x \n", "xx \n")
        assertShapeForOrientation(shape, Orientation.WEST, westShape)
    }

    @Test
    fun should_load_L_shape() {
        val filename = "L.json"
        val shape = loadShape(filename)
        assertThat(shape.type).isEqualTo(L)
        assertThat(shape.size).isEqualTo(3)
        assertThat(shape.shapeByOrientations).hasSize(4)
        val northShape = arrayOf("  x\n", "xxx\n", "   \n")
        assertShapeForOrientation(shape, Orientation.NORTH, northShape)
        val eastShape = arrayOf(" x \n", " x \n", " xx\n")
        assertShapeForOrientation(shape, Orientation.EAST, eastShape)
        val southShape = arrayOf("   \n", "xxx\n", "x  \n")
        assertShapeForOrientation(shape, Orientation.SOUTH, southShape)
        val westShape = arrayOf("xx \n", " x \n", " x \n")
        assertShapeForOrientation(shape, Orientation.WEST, westShape)
    }

    @Test
    fun should_load_O_shape() {
        val filename = "O.json"
        val shape = loadShape(filename)
        assertThat(shape.type).isEqualTo(O)
        assertThat(shape.size).isEqualTo(4)
        assertThat(shape.shapeByOrientations).hasSize(4)
        val shapeToString = arrayOf(" xx \n", " xx \n", "    \n", "    \n")
        assertShapeForOrientation(shape, Orientation.NORTH, shapeToString)
        assertShapeForOrientation(shape, Orientation.EAST, shapeToString)
        assertShapeForOrientation(shape, Orientation.SOUTH, shapeToString)
        assertShapeForOrientation(shape, Orientation.WEST, shapeToString)
    }

    @Test
    fun should_load_S_shape() {
        val filename = "S.json"
        val shape = loadShape(filename)
        assertThat(shape.type).isEqualTo(S)
        assertThat(shape.size).isEqualTo(3)
        assertThat(shape.shapeByOrientations).hasSize(4)
        val northShape = arrayOf(" xx\n", "xx \n", "   \n")
        assertShapeForOrientation(shape, Orientation.NORTH, northShape)
        val eastShape = arrayOf(" x \n", " xx\n", "  x\n")
        assertShapeForOrientation(shape, Orientation.EAST, eastShape)
        val southShape = arrayOf("   \n", " xx\n", "xx \n")
        assertShapeForOrientation(shape, Orientation.SOUTH, southShape)
        val westShape = arrayOf("x  \n", "xx \n", " x \n")
        assertShapeForOrientation(shape, Orientation.WEST, westShape)
    }

    @Test
    fun should_load_Z_shape() {
        val filename = "Z.json"
        val shape = loadShape(filename)
        assertThat(shape.type).isEqualTo(Z)
        assertThat(shape.size).isEqualTo(3)
        assertThat(shape.shapeByOrientations).hasSize(4)
        val northShape = arrayOf("xx \n", " xx\n", "   \n")
        assertShapeForOrientation(shape, Orientation.NORTH, northShape)
        val eastShape = arrayOf("  x\n", " xx\n", " x \n")
        assertShapeForOrientation(shape, Orientation.EAST, eastShape)
        val southShape = arrayOf("   \n", "xx \n", " xx\n")
        assertShapeForOrientation(shape, Orientation.SOUTH, southShape)
        val westShape = arrayOf(" x \n", "xx \n", "x  \n")
        assertShapeForOrientation(shape, Orientation.WEST, westShape)
    }

    @Test
    fun should_load_T_shape() {
        val filename = "T.json"
        val shape = loadShape(filename)
        assertThat(shape.type).isEqualTo(T)
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

    @Test
    fun should_throw_an_exception_when_file_does_not_exist() {
        assertThrows(RuntimeException::class.java) { shapeLoader.loadShape(Paths.get("")) }
    }

    private fun loadShape(filename: String): Shape {
        try {
            return shapeLoader.loadShape(Paths.get(Shape::class.java.getResource(filename).toURI()))
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

    }

}