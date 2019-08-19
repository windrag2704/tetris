package controller

import game.Tetris
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Text

object Controller {
    var gc: GraphicsContext? = null
    var game: Tetris? = null
    var gcNext: GraphicsContext? = null
    var score: Text? = null
    var info: Text? = null

    private fun drawRect(i: Int, j: Int, figure: Int, gc: GraphicsContext, offX: Int, offY: Int) {
        if (figure > 0) {
            when (figure) {
                1, 11 -> gc.fill = Color.CYAN
                2, 12 -> gc.fill = Color.rgb(255, 50, 0)
                3, 13 -> gc.fill = Color.BLUE
                4, 14 -> gc.fill = Color.YELLOW
                5, 15 -> gc.fill = Color.GREEN
                6, 16 -> gc.fill = Color.PURPLE
                7, 17 -> gc.fill = Color.RED
            }
            gc.fillRect((30 * j + 2 + offX).toDouble(), (30 * i + 2 + offY).toDouble(), 26.0, 26.0)
        }
    }

    fun refresh() {
        val tetris = game ?: return
        gc?.clearRect(0.0, 0.0, 300.0, 600.0)
        for (i in 2..21) {
            for (j in 0..9) {
                drawRect(i - 2, j, tetris.getCellValue(i, j), gc ?: return,
                        0, 0)
            }
        }
        gcNext?.clearRect(0.0, 0.0, 120.0, 60.0)
        val offX: Int
        val offY: Int
        when (tetris.nextShapeNumber) {
            1 -> {
                offX = 0
                offY = 15
            }
            4 -> {
                offX = 30
                offY = 0
            }
            else -> {
                offX = 15
                offY = 0
            }
        }
        for (i in 0..1) {
            for (j in 0..3) {
                drawRect(i, j, tetris.getNextShapeCellValue(i, j), gcNext ?: return,
                        offX, offY)
            }
        }
        score?.text = String.format("%03d", game?.score)

        when {
            tetris.isStopped -> {
                info?.fill = Color.valueOf("#aaaaaa")
                info?.text = "STOPPED"
            }
            tetris.isGameOver -> {
                info?.fill = Color.RED
                info?.text = "GAME OVER"
            }
            tetris.isPause -> {
                info?.fill = Color.valueOf("#aaaaaa")
                info?.text = "PAUSE"
            }
            else -> info?.text = ""
        }
    }
}
