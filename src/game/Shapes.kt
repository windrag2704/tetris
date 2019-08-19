package game

import java.util.concurrent.ThreadLocalRandom

internal class Shapes {
    private var current = arrayOf(arrayOf(intArrayOf()))
    var shapeNumber = 0
        private set
    private val figures = arrayOf(
            arrayOf(//I
                    arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(0, 3)),
                    arrayOf(intArrayOf(-1, 0), intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(2, 0))),
            arrayOf(//L
                    arrayOf(intArrayOf(1, 0), intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2)),
                    arrayOf(intArrayOf(-1, 0), intArrayOf(-1, 1), intArrayOf(0, 1), intArrayOf(1, 1)),
                    arrayOf(intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(1, 2), intArrayOf(0, 2)),
                    arrayOf(intArrayOf(-1, 0), intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(1, 1))),
            arrayOf(//J
                    arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(1, 2)),
                    arrayOf(intArrayOf(-1, 1), intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(1, 0)),
                    arrayOf(intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(1, 2)),
                    arrayOf(intArrayOf(-1, 1), intArrayOf(-1, 0), intArrayOf(0, 0), intArrayOf(1, 0))),
            arrayOf(//O
                    arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(1, 0))),
            arrayOf(//S
                    arrayOf(intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(0, 1), intArrayOf(0, 2)),
                    arrayOf(intArrayOf(-1, 0), intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(1, 1))),
            arrayOf(//T
                    arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(1, 1)),
                    arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(-1, 1)),
                    arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(-1, 1)),
                    arrayOf(intArrayOf(-1, 0), intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(1, 0))),
            arrayOf(//Z
                    arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(1, 2)),
                    arrayOf(intArrayOf(-1, 1), intArrayOf(0, 1), intArrayOf(0, 0), intArrayOf(1, 0))
            )
    )
    private var state = 0
    private var next = ThreadLocalRandom.current().nextInt(0, 7)
    var nextShapeNumber: Int = 0
        private set
    val nextShape: Array<IntArray>
        get() = figures[next][0]
    val statesCount: Int
        get() = current.size

    fun rotate() {
        state = (state + 1) % current.size
    }

    fun getWidth(st: Int): Int =
            when (shapeNumber - 1) {
                0 -> if (st == 0) 4 else 1
                3 -> 2
                else -> if (st % 2 == 0) 3 else 2
            }

    fun getRotateCoordinates(offsetX: Int, offsetY: Int): Array<IntArray> {
        val result = Array(current[0].size) { IntArray(2) }
        for (i in result.indices) {
            result[i][0] = current[(state + 1) % current.size][i][0] + offsetY
            result[i][1] = current[(state + 1) % current.size][i][1] + offsetX
        }
        return result
    }

    fun getPosition(offsetX: Int, offsetY: Int, st: Int = this.state): Array<IntArray> {
        val result = Array(current[0].size) { IntArray(2) }
        for (i in result.indices) {
            result[i][0] = current[st][i][0] + offsetY
            result[i][1] = current[st][i][1] + offsetX
        }
        return result
    }

    fun setRandom() {
        state = 0
        val temp = next
        next = ThreadLocalRandom.current().nextInt(0, 700000000) / 100000000
        nextShapeNumber = next + 1
        shapeNumber = temp + 1
        current = figures[temp]
    }

    fun removeSame(element: IntArray, array: MutableList<IntArray>) {
        val it = array.iterator()
        while (it.hasNext()) {
            if (element.contentEquals(it.next())) it.remove()
        }
    }

    fun getBottomCells(st: Int): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        result += getPosition(0, 1, st)
        for (element in current[st]) {
            removeSame(element, result)
        }
        return result.toTypedArray()
    }


    fun getNeighboursCells(st: Int): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        result += getPosition(0, 1, st)
        result += getPosition(-1, 0, st)
        result += getPosition(1, 0, st)
        for (element in current[st]) {
            removeSame(element, result)
        }
        return result.toTypedArray()
    }
}
