package solver

import game.Tetris

class Solver(private var tetris: Tetris) : Thread() {
    override fun run() {
        while (!tetris.isGameOver) {
            sleep(10)
            if (!tetris.readyToSolve) continue
            var max = 0
            var offset = 0
            var maxState = 0
            for (st in 0 until tetris.statesCount) {
                val neighbors = tetris.getShapeNeighbors(st)
                val bottom = tetris.getBottomCells(st)
                for (i in 0..tetris.horizontalMoveRange(st)) {
                    val metric =
                            getMetric(tetris.getFallHeight(i, st), i, neighbors, bottom, tetris.getProjection(i, st))
                    if (metric > max) {
                        offset = i
                        max = metric
                        maxState = st
                    }
                }
            }
            while (maxState != 0) {
                if (tetris.rotate()) maxState--
            }
            offset -= 4
            while (offset != 0) {
                when {
                    offset > 0 -> {
                        tetris.moveRight()
                        offset--
                    }
                    offset < 0 -> {
                        tetris.moveLeft()
                        offset++
                    }
                    else -> {
                    }
                }
            }
            tetris.readyToSolve = false
        }
    }

    private fun getMetric(y: Int, x: Int, neighbors: Array<IntArray>,
                          bottom: Array<IntArray>, shape: Array<IntArray>): Int {
        var metric = 0
        for (cell in neighbors) {
            if (cell[0] + y !in 0..21 || cell[1] + x !in 0..9 ||
                    tetris.getCellValue(cell[0] + y, cell[1] + x) in 1..7) metric++
        }
        for (cell in bottom) {
            if (cell[0] + y in 0..21 && tetris.getCellValue(cell[0] + y, cell[1] + x) == 0) metric -= 3
        }
        if (fullLine(shape)) metric += 10
        return metric + y
    }

    private fun fullLine(shape: Array<IntArray>): Boolean {
        val linesFill = mutableMapOf<Int, Int>()
        for (cell in shape) {
            linesFill.merge(cell[0], 1) { t, u -> t + u }

        }
        for (key in linesFill.keys) {
            for (i in 0..9) {
                if (tetris.getCellValue(key, i) in 1..7)
                    linesFill.merge(key, 1) { t, u -> t + u }
            }
        }
        for (entry in linesFill) {
            if (entry.value == 10) {
                return true
            }
        }
        return false
    }
}
