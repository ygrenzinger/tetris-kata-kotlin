package com.kata.tetris.domain

import org.junit.jupiter.api.Test

import org.assertj.core.api.Assertions.assertThat

internal class ScoreTest {

    @Test
    fun should_score_100_for_each_line_removed() {
        val score = Score.buildScoreForLinesRemoved(3)
        assertThat(score.toString()).isEqualTo("300")
    }

    @Test
    fun should_score_400_if_4_lines_removed() {
        val score = Score.buildScoreForLinesRemoved(4)
        assertThat(score.toString()).isEqualTo("800")
    }

    @Test
    fun should_add_two_score() {
        assertThat(Score(300) + Score(400)).isEqualTo(Score(700))
    }

}