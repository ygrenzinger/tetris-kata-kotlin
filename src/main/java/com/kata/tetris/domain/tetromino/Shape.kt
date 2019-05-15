package com.kata.tetris.domain.tetromino

data class Shape(val type: ShapeType, val size: Int, val shapeByOrientations: Map<Orientation, ShapeForm>) {

    internal fun blocksByOrientation(orientation: Orientation): Array<BooleanArray> {
        return shapeByOrientations.getValue(orientation).blocks
    }

    internal fun shapeForOrientationToString(orientation: Orientation): String {
        val blocks = blocksByOrientation(orientation)
        val builder = StringBuilder()
        for (row in blocks) {
            for (block in row) {
                if (block) {
                    builder.append('x')
                } else {
                    builder.append(' ')
                }
            }
            builder.append('\n')
        }
        return builder.toString()
    }

    internal fun leftWallKickPossibleShift(orientation: Orientation): Int {
        val shape = blocksByOrientation(orientation)
        var shift = 0
        while (emptyColumn(shape, shift)) {
            shift++
        }
        return shift
    }

    internal fun rightWallKickPossibleShift(orientation: Orientation): Int {
        val shape = blocksByOrientation(orientation)
        var shift = 0
        while (emptyColumn(shape, size - 1 - shift)) {
            shift++
        }
        return shift
    }

    private fun emptyColumn(shape: Array<BooleanArray>, column: Int): Boolean {
        return shape.all { !it[column] }
    }

}
