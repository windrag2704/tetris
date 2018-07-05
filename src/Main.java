import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

    private static Tetris tetris = new Tetris();

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Tetris");
        Scene scene = new Scene(root, 800, 629);
        scene.setFill(Color.rgb(0x33, 0x33, 0x33));
        primaryStage.setScene(scene);

        Canvas center = new Canvas(300, 600);
        GridPane right = new GridPane();
        GridPane left = new GridPane();
        prepareLayout(left, right);

        Canvas nextFigure = new Canvas(120, 60);
        Text score = new Text();
        right.add(nextFigure, 1, 1);
        right.add(score, 1, 3);

        Text info = new Text();
        left.add(info, 1, 1);

        score.setWrappingWidth(120);
        score.setFont(new Font(60));
        score.setFill(Color.valueOf("#aaaaaa"));
        score.setTextAlignment(TextAlignment.CENTER);

        info.setWrappingWidth(120);
        info.setFont(new Font(20));
        info.setTextAlignment(TextAlignment.CENTER);

        GraphicsContext gc = center.getGraphicsContext2D();
        GraphicsContext gcNext = nextFigure.getGraphicsContext2D();

        Controller.game = tetris;
        Controller.gc = gc;
        Controller.gcNext = gcNext;
        Controller.score = score;
        Controller.info = info;
        Controller.refresh();

        MenuBar menuBar = prepareMenu();

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(left);
        borderPane.setRight(right);
        borderPane.setCenter(center);
        borderPane.setTop(menuBar);
        root.getChildren().add(borderPane);

        setKeyEvents(scene);

        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setKeyEvents(Scene scene) {
        scene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (code.equals("LEFT")) {
                tetris.moveLeft();
            }
            if (code.equals("RIGHT")) {
                tetris.moveRight();
            }
            if (code.equals("UP")) {
                tetris.rotate();
            }
            if (code.equals("DOWN")) {
                tetris.figureFall();
            }
            if (code.equals("SPACE")) {
                tetris.pause();
            }
        });

        scene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            if (code.equals("DOWN")) {
                tetris.figureRestore();
            }
        });

    }

    private void prepareLayout(GridPane left, GridPane right) {
        Image leftImg = new Image("/res/left.png");
        Image rightImg = new Image("/res/right.png");
        right.prefHeight(600);
        right.prefWidth(250);
        right.setBackground(
                new Background(
                        new BackgroundImage(
                                rightImg,
                                null,
                                null,
                                null,
                                null
                        )
                )
        );
        right.getColumnConstraints().addAll(new ColumnConstraints(65), new ColumnConstraints(120), new ColumnConstraints(65));
        right.getRowConstraints().addAll(
                new RowConstraints(80),
                new RowConstraints(60),
                new RowConstraints(330),
                new RowConstraints(60),
                new RowConstraints(70)
        );

        left.setPrefHeight(600);
        left.setPrefWidth(250);
        left.setBackground(
                new Background(
                        new BackgroundImage(
                                leftImg,
                                null,
                                null,
                                null,
                                null
                        )
                )
        );
        left.getColumnConstraints().addAll(
                new ColumnConstraints(65),
                new ColumnConstraints(120),
                new ColumnConstraints(65)
        );
        left.getRowConstraints().addAll(
                new RowConstraints(100),
                new RowConstraints(20),
                new RowConstraints(430)
        );

    }

    private MenuBar prepareMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menuGame = new Menu("Game");
        Menu menuAbout = new Menu("About");
        menuBar.getMenus().addAll(menuGame, menuAbout);

        MenuItem startGame = new MenuItem("Start");
        MenuItem stopGame = new MenuItem("Stop");
        MenuItem difficulty = new Menu("Difficulty");
        MenuItem restartGame = new MenuItem("Restart");
        MenuItem exitGame = new MenuItem("Exit");
        stopGame.setVisible(false);

        ToggleGroup difficultyGroup = new ToggleGroup();
        for (int i = 0; i < 6; i++) {
            final int difficult = i;
            RadioMenuItem diffItem = new RadioMenuItem(Integer.toString(i));
            diffItem.setToggleGroup(difficultyGroup);
            diffItem.setOnAction(event -> tetris.setDifficulty(difficult));
            ((Menu) difficulty).getItems().add(diffItem);
            if (i == 0) diffItem.setSelected(true);
        }

        menuGame.getItems().add(startGame);
        menuGame.getItems().add(stopGame);
        menuGame.getItems().add(difficulty);
        menuGame.getItems().add(restartGame);
        menuGame.getItems().add(exitGame);

        startGame.setOnAction(event -> {
            startGame.setVisible(false);
            startGame.setDisable(true);
            stopGame.setVisible(true);
            tetris.start();
        });
        startGame.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        stopGame.setOnAction(event -> {
            startGame.setVisible(true);
            startGame.setDisable(false);
            stopGame.setVisible(false);
            tetris.stop();
        });
        stopGame.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        restartGame.setOnAction(event -> tetris.restart());
        restartGame.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));

        exitGame.setOnAction(event -> System.exit(0));
        exitGame.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
