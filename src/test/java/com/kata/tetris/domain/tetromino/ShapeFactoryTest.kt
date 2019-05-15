package com.kata.tetris.domain.tetromino

import com.kata.tetris.infra.FileShapeLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ShapeFactoryTest {
    private val shapeFactory = ShapeFactory(FileShapeLoader())
    private val shapes = shapeFactory.allShapes().associateBy { it.type }

    @Test
    fun should_load_all_tetromino_shapes() {
        assertThat(shapes).hasSize(7)
        assertThat(shapes).containsOnlyKeys(*ShapeType.values())
    }

    @ParameterizedTest
    @EnumSource(ShapeType::class)
    fun should_only_have_4_moving_blocks_for_any_shape_and_orientation(shapeType: ShapeType) {
        val shape = shapes[shapeType]!!

        Orientation.values().forEach { orientation ->
            val numberOfMovingBlocks = shape.blocksByOrientation(orientation)
                    .sumBy { row ->
                        row.count { it }
                    }
            assertThat(numberOfMovingBlocks).isEqualTo(4)
        }
    }

}