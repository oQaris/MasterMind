package com.pryanik.mastermind.logic.impl

import com.pryanik.mastermind.logic.Encoder

class MinimaxSieveEncoder : Encoder {

    override fun nextAnswer(guess: String): Pair<Int, Int> {
        return 2 to 2
    }

    override fun reset() {

    }
}