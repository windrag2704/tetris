package game

import java.util.*

class Tetris {
    private val field = Field()
    private var level = 1000
    private var period = 1000
    private var delay = 1000
    private var difficulty = 0
    private var acceleration = 0.98
    private var timer: Timer? = null
    val nextShapeNumber: Int
        get() = this.field.nextShapeNumber
    var isPause = false
        private set
    var isStopped = true
        private set
    var score = 0
        private set
    var isGameOver = false
        private set
    var readyToSolve: Boolean
        set(value) {
            this.field.shapeSolved = !value
        }
        get() = !this.field.shapeSolved
    val statesCount: Int
        get() = this.field.getStatesCount()

    fun getShapeNeighbors(st: Int): Array<IntArray> {
        return field.getShapeNeighbors(st)
    }

    fun getBottomCells(st: Int) = field.getBottomCells(st)

    fun horizontalMoveRange(st: Int): Int {
        return field.getHorizontalMoveRange(st)
    }


    fun getProjection(offX: Int, st: Int) = field.projection(offX, st)

    fun getFallHeight(offX: Int, st: Int): Int {
        return field.height(offX, st)
    }

    fun setDifficulty(difficulty: Int) {
        this.difficulty = difficulty
    }

    fun start() {
        field.spawnShape()
        acceleration = 1 - (difficulty + 1) * 0.02
//        level = 1000 - difficulty * 99
        level = 1
        period = level
        delay = level
        isStopped = false
        startTimer()
    }

    fun figureFall() {
        if (isStopped || isPause || isGameOver || period <= 50) return
        period = 50
        delay = 0
        stopTimer()
        startTimer()
    }

    @Synchronized
    private fun moveDown() {
        if (isPause || isGameOver || isStopped) return
        if (!field.moveDown()) {
            val removedLines = field.removeFullLines()
            score += removedLines
            isGameOver = !field.spawnShape()
//            if (removedLines > score % 5) {
//                for (i in 0 until removedLines / 5 + ((score - removedLines) % 5 + removedLines % 5) / 5) {
//                    level = (level * acceleration).roundToInt()
//                }
//                period = level
//                delay = level
//                stopTimer()
//                startTimer()
//            }
        }
    }

    fun getCellValue(y: Int, x: Int): Int {
        return field.getCellValue(y, x)
    }

    fun getNextShapeCellValue(y: Int, x: Int): Int {
        return field.getNextShapeCellValue(y, x)
    }

    @Synchronized
    fun moveLeft(): Boolean {
        if (isPause || isGameOver || isStopped) return false
        return field.moveLeft()
    }

    @Synchronized
    fun moveRight(): Boolean {
        if (isPause || isGameOver || isStopped) return false
        return field.moveRight()
    }

    @Synchronized
    fun rotate(): Boolean {
        if (isPause || isGameOver || isStopped) return false
        return field.rotateShape()
    }

    fun stop() {
        field.clear()
        stopTimer()
        score = 0
        isStopped = true
        isGameOver = false
        isPause = false
    }

    fun restart() {
        stop()
        start()
    }

    fun figureRestore() {
        if (isGameOver || isStopped || isPause || period == level) return
        period = level
        delay = level
        stopTimer()
        startTimer()
    }

    fun pause() {
        if (isGameOver || isStopped) return
        isPause = !isPause
        if (isPause) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    private fun startTimer() {
        if (timer != null) {
            timer = null
        }
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                moveDown()
                if (isGameOver) {
                    timer!!.cancel()
                    timer = null
                }
            }
        }, delay.toLong(), period.toLong())
    }
}
