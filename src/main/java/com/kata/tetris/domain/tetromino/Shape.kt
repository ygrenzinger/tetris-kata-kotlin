package com.kata.tetris.domain.tetromino

data class Shape(val type: ShapeType, val size: Int, val shapeByOrientations: Map<Orientation, ShapeForm>) {

    internal fun blocksByOrientation(orientation: Orientation) =
            shapeByOrientations.getValue(orientation).blocks

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
        val predicate: (Int) -> Boolean = { emptyColumn(shape, it) }
        return possibleShift(predicate)
    }

    internal fun rightWallKickPossibleShift(orientation: Orientation): Int {
        val shape = blocksByOrientation(orientation)
        val predicate: (Int) -> Boolean = { emptyColumn(shape, size - 1 - it) }
        return possibleShift(predicate)
    }

    private fun possibleShift(predicate: (Int) -> Boolean) =
            generateSequence(0, { it + 1 })
                .takeWhile(predicate)
                .lastOrNull()?:0

    private fun emptyColumn(shape: Array<BooleanArray>, column: Int) =
            shape.all { !it[column] }

}
