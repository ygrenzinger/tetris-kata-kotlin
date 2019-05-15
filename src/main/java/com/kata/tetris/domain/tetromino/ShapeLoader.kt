package com.kata.tetris.domain.tetromino

interface ShapeLoader {
    fun loadShape(shapeType: ShapeType): Shape
}
