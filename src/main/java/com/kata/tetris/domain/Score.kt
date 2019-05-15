package com.kata.tetris.domain


data class Score(private val value: Int) {

    companion object {
        fun buildScoreForLinesRemoved(nbOfLineRemoved: Int): Score {
            return if (nbOfLineRemoved == 4) {
                Score(800)
            } else {
                Score(nbOfLineRemoved * 100)
            }
        }
    }

    constructor() : this(0)

    override fun toString(): String {
        return value.toString()
    }

    operator fun plus(score: Score) = Score(value + score.value)
}
