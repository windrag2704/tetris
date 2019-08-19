package game

class Field {
    private val gameArea = Matrix(10, 22)
    private val nextShapeArea = Matrix(4, 2)
    private val shapes = Shapes()
    private var offsetX = 4
    private var offsetY = 0
    val nextShapeNumber: Int
        get() = shapes.nextShapeNumber
    var shapeSolved: Boolean = false

    fun getShapeNeighbors(st: Int): Array<IntArray> {
        return shapes.getNeighboursCells(st)
    }

    fun spawnShape(): Boolean {
        shapes.setRandom()
        offsetX = 4
        offsetY = 0
        nextShapeArea.clear()
        insertNextShape()
        if (!checkNext(shapes.getPosition(offsetX, offsetY))) {
            return false
        }
        insertShape(false)
        shapeSolved = false
        return true
    }

    fun clear() {
        gameArea.clear()
        nextShapeArea.clear()
    }

    fun getNextShapeCellValue(y: Int, x: Int): Int {
        return nextShapeArea[y, x]
    }

    fun getCellValue(y: Int, x: Int): Int {
        return gameArea[y, x]
    }

    private fun insertNextShape() {
        nextShapeArea.clear()
        val nextShape = shapes.nextShape
        for (i in 0..3) {
            val y = nextShape[i][0]
            val x = nextShape[i][1]
            nextShapeArea[y, x] = shapes.nextShapeNumber
        }
    }

    fun moveDown(): Boolean {
        var result = false
        removeShape()
        if (checkNext(shapes.getPosition(offsetX, offsetY + 1))) {
            offsetY++
            result = true
        }
        insertShape(!result)
        return result
    }

    fun moveLeft(): Boolean {
        var result = false
        removeShape()
        if (checkNext(shapes.getPosition(offsetX - 1, offsetY))) {
            offsetX--
            result = true
        }
        insertShape(false)
        return result
    }

    fun moveRight(): Boolean {
        var result = false
        removeShape()
        if (checkNext(shapes.getPosition(offsetX + 1, offsetY))) {
            offsetX++
            result = true
        }
        insertShape(false)
        return result
    }

    fun rotateShape(): Boolean {
        var result = false
        removeShape()
        if (checkNext(shapes.getRotateCoordinates(offsetX, offsetY))) {
            shapes.rotate()
            result = true
        }
        insertShape(false)
        return result
    }

    private fun checkNext(nextPosition: Array<IntArray>): Boolean {
        for (i in 0..3) {
            if (nextPosition[i][1] > 9 || nextPosition[i][1] < 0 || nextPosition[i][0] > 21 || nextPosition[i][0] < 0) {
                return false
            }
            if (gameArea[nextPosition[i][0], nextPosition[i][1]] in 1..7) {
                return false
            }

        }
        return true
    }

    private fun checkFullLine(): Int {
        var full = true
        for (i in 0..21) {
            for (j in 0..9) {
                if (gameArea.get(i, j) == 0) {
                    full = false
                    break
                }
            }
            if (full) {
                return i
            }
            full = true
        }
        return 22
    }

    fun removeFullLines(): Int {
        var result = 0
        var temp: Int
        do {
            temp = checkFullLine()
            if (temp == 22) break
            gameArea.removeLine(temp)
            result++
            for (i in temp - 1 downTo 0) {
                gameArea.moveLine(i, i + 1)
            }
        } while (true)
        return result
    }

    private fun removeShape() {
        val figurePoints = shapes.getPosition(offsetX, offsetY)
        for (i in 0..3) {
            val x = figurePoints[i][1]
            val y = figurePoints[i][0]
            gameArea[y, x] = 0
        }
    }

    private fun insertShape(isLanded: Boolean) {
        val figurePoints = shapes.getPosition(offsetX, offsetY)
        for (i in 0..3) {
            val x = figurePoints[i][1]
            val y = figurePoints[i][0]
            gameArea[y, x] = if (isLanded) shapes.shapeNumber else shapes.shapeNumber + 10
        }
    }

    fun getHorizontalMoveRange(st: Int): Int = 10 - shapes.getWidth(st)

    private fun pprojection(offX: Int, st: Int): Pair<Int, Array<IntArray>> {
        var y = 2
        while (checkNext(shapes.getPosition(offX, y, st))) y++
        return Pair(y - 1, shapes.getPosition(offX, y - 1, st))
    }

    fun projection(offX: Int, st: Int) = pprojection(offX, st).second

    fun height(offX: Int, st: Int) = pprojection(offX, st).first

    fun getStatesCount(): Int {
        return shapes.statesCount
    }

    fun getBottomCells(st: Int) = shapes.getBottomCells(st)
}
