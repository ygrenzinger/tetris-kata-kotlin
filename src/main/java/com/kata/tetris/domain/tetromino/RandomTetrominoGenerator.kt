package com.kata.tetris.domain.tetromino

import java.util.LinkedList
import java.util.Random

// Following wiki https://tetris.fandom.com/wiki/Random_Generator
class RandomTetrominoGenerator(private val shapeFactory: ShapeFactory) {
    private val bagOfShapes = LinkedList<Shape>()

    init {
        fillBagOfShapes()
    }

    private fun fillBagOfShapes() {
        val shapeTypeShapeMap = shapeFactory.allShapes()
        bagOfShapes.addAll(shapeTypeShapeMap)
    }

    fun randomShape(): Shape {
        if (bagOfShapes.isEmpty()) {
            fillBagOfShapes()
        }
        return bagOfShapes.removeAt((0 until bagOfShapes.size).random())
    }
}
