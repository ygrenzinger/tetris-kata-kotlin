package com.kata.tetris.domain.tetromino

class ShapeFactory(shapeLoader: ShapeLoader) {

    private val shapes: List<Shape>

    init {
        this.shapes = loadShapes(shapeLoader)
    }

    private fun loadShapes(shapeLoader: ShapeLoader): List<Shape> {
        return ShapeType.values().map { shapeLoader.loadShape(it) }
    }

    fun allShapes():List<Shape> {
        return shapes
    }
}
