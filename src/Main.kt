
import controller.Controller
import game.Tetris
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import solver.Solver

class Main : Application() {

    private val tetris = Tetris()

    override fun start(primaryStage: Stage) {
        val root = Group()
        primaryStage.title = "game.Tetris"
        val scene = Scene(root, 800.0, 629.0)
        scene.fill = Color.rgb(0x33, 0x33, 0x33)
        primaryStage.scene = scene

        val center = Canvas(300.0, 600.0)
        val right = GridPane()
        val left = GridPane()
        prepareLayout(left, right)

        val nextFigure = Canvas(120.0, 60.0)
        val score = Text()
        right.add(nextFigure, 1, 1)
        right.add(score, 1, 3)

        val info = Text()
        left.add(info, 1, 1)

        score.wrappingWidth = 120.0
        score.font = Font(60.0)
        score.fill = Color.valueOf("#aaaaaa")
        score.textAlignment = TextAlignment.CENTER

        info.wrappingWidth = 120.0
        info.font = Font(20.0)
        info.textAlignment = TextAlignment.CENTER

        val gc = center.graphicsContext2D
        val gcNext = nextFigure.graphicsContext2D

        Controller.game = tetris
        Controller.gc = gc
        Controller.gcNext = gcNext
        Controller.score = score
        Controller.info = info

        val menuBar = prepareMenu()

        val borderPane = BorderPane()
        borderPane.left = left
        borderPane.right = right
        borderPane.center = center
        borderPane.top = menuBar
        root.children.add(borderPane)

        setKeyEvents(scene)

        object : AnimationTimer() {
            override fun handle(now: Long) {
                Controller.refresh()
            }

        }.start()

        primaryStage.setOnCloseRequest { System.exit(0) }
        primaryStage.isResizable = false
        primaryStage.show()
    }

    private fun setKeyEvents(scene: Scene) {
        scene.setOnKeyPressed { event ->
            val code = event.code.toString()
            if (code == "LEFT") {
                tetris.moveLeft()
            }
            if (code == "RIGHT") {
                tetris.moveRight()
            }
            if (code == "UP") {
                tetris.rotate()
            }
            if (code == "DOWN") {
                tetris.figureFall()
            }
            if (code == "SPACE") {
                tetris.pause()
            }
        }

        scene.setOnKeyReleased { event ->
            val code = event.code.toString()
            if (code == "DOWN") {
                tetris.figureRestore()
            }
        }

    }

    private fun prepareLayout(left: GridPane, right: GridPane) {
        val leftImg = Image("/res/left.png")
        val rightImg = Image("/res/right.png")
        right.prefHeight(600.0)
        right.prefWidth(250.0)
        right.background = Background(
                BackgroundImage(
                        rightImg,
                        null,
                        null,
                        null,
                        null
                )
        )
        right.columnConstraints.addAll(
                ColumnConstraints(65.0),
                ColumnConstraints(120.0),
                ColumnConstraints(65.0)
        )
        right.rowConstraints.addAll(
                RowConstraints(80.0),
                RowConstraints(60.0),
                RowConstraints(330.0),
                RowConstraints(60.0),
                RowConstraints(70.0)
        )

        left.prefHeight = 600.0
        left.prefWidth = 250.0
        left.background = Background(
                BackgroundImage(
                        leftImg, null, null, null, null
                )
        )
        left.columnConstraints.addAll(
                ColumnConstraints(65.0),
                ColumnConstraints(120.0),
                ColumnConstraints(65.0)
        )
        left.rowConstraints.addAll(
                RowConstraints(100.0),
                RowConstraints(20.0),
                RowConstraints(430.0)
        )

    }

    private fun prepareMenu(): MenuBar {
        val menuBar = MenuBar()
        val menuGame = Menu("Game")
        val menuAbout = Menu("About")
        menuBar.menus.addAll(menuGame, menuAbout)

        val startGame = MenuItem("Start")
        val startWithSolving = MenuItem("Start & Solve")
        val stopGame = MenuItem("Stop")
        val difficulty = Menu("Difficulty")
        val restartGame = MenuItem("Restart")
        val solve = MenuItem("Solve")
        val exitGame = MenuItem("Exit")
        stopGame.isVisible = false

        val difficultyGroup = ToggleGroup()
        for (i in 0..10) {
            val diffItem = RadioMenuItem(Integer.toString(i))
            diffItem.toggleGroup = difficultyGroup
            diffItem.setOnAction { tetris.setDifficulty(i) }
            difficulty.items.add(diffItem)
            if (i == 0) diffItem.isSelected = true
        }

        menuGame.items.add(startGame)
        menuGame.items.add(startWithSolving)
        menuGame.items.add(stopGame)
        menuGame.items.add(difficulty)
        menuGame.items.add(restartGame)
        menuGame.items.add(solve)
        menuGame.items.add(exitGame)

        startGame.setOnAction {
            startGame.isVisible = false
            startGame.isDisable = true
            stopGame.isVisible = true
            tetris.start()
        }
        startGame.accelerator = KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN)

        startWithSolving.setOnAction {
            tetris.restart()
            Solver(tetris).start()
        }
        startWithSolving.accelerator = KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN)

        stopGame.setOnAction {
            startGame.isVisible = true
            startGame.isDisable = false
            stopGame.isVisible = false
            tetris.stop()
        }
        stopGame.accelerator = KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)

        restartGame.setOnAction { tetris.restart() }
        restartGame.accelerator = KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN)
        solve.setOnAction {
            val solver = Solver(tetris)
            solver.start()
        }
        solve.accelerator = KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN)

        exitGame.setOnAction { System.exit(0) }
        exitGame.accelerator = KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN)
        return menuBar
    }


    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}

