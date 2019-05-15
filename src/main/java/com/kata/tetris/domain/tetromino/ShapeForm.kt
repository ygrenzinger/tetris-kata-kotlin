package com.kata.tetris.domain.tetromino

data class ShapeForm(val blocks: Array<BooleanArray>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShapeForm

        if (!blocks.contentDeepEquals(other.blocks)) return false

        return true
    }

    override fun hashCode(): Int {
        return blocks.contentDeepHashCode()
    }
}