package com.kata.tetris.domain.tetromino

enum class Orientation {
    NORTH, EAST, SOUTH, WEST;

    operator fun next(): Orientation {
        val values = values()
        val index = (this.ordinal + 1) % values.size
        return values[index]
    }
}
