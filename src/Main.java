import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Tetris");
        Scene scene = new Scene(root, 800, 629);
        scene.setFill(Color.rgb(0x33, 0x33, 0x33));
        primaryStage.setScene(scene);
        Tetris tetris = new Tetris();

        Image leftImg = new Image("/res/left.png");
        Image rightImg = new Image("/res/right.png");

        BorderPane borderPane = new BorderPane();
        GridPane right = new GridPane();
        GridPane left = new GridPane();

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

        Canvas canvas = new Canvas(300, 600);
        Canvas nextFigure = new Canvas(120, 60);
        Text score = new Text();
        Text info = new Text();

        right.add(nextFigure, 1, 1);
        right.add(score, 1, 3);
        left.add(info, 1, 1);

        score.setWrappingWidth(120);
        score.setFont(new Font(60));
        score.setFill(Color.valueOf("#aaaaaa"));
        score.setTextAlignment(TextAlignment.CENTER);

        info.setWrappingWidth(120);
        info.setFont(new Font(20));
        info.setTextAlignment(TextAlignment.CENTER);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcNext = nextFigure.getGraphicsContext2D();

        Controller.game = tetris;
        Controller.gc = gc;
        Controller.gcNext = gcNext;
        Controller.score = score;
        Controller.info = info;
        Controller.refresh();

        MenuBar menuBar = new MenuBar();
        Menu menuGame = new Menu("Game");
        Menu menuAbout = new Menu("About");
        menuBar.getMenus().addAll(menuGame, menuAbout);

        MenuItem startGame = new MenuItem("Start");
        MenuItem stopGame = new MenuItem("Stop");
        stopGame.setVisible(false);

        menuGame.getItems().add(startGame);
        menuGame.getItems().add(stopGame);

        startGame.setOnAction(event -> {
            startGame.setVisible(false);
            stopGame.setVisible(true);
            tetris.start();
        });

        stopGame.setOnAction(event -> {
            startGame.setVisible(true);
            stopGame.setVisible(false);
            tetris.stop();
        });

        MenuItem restartGame = new MenuItem("Restart");
        menuGame.getItems().add(restartGame);
        restartGame.setOnAction(event -> tetris.restart());

        MenuItem exitGame = new MenuItem("Exit");
        menuGame.getItems().add(exitGame);
        exitGame.setOnAction(event -> System.exit(0));

        borderPane.setLeft(left);
        borderPane.setRight(right);
        borderPane.setCenter(canvas);
        borderPane.setTop(menuBar);
        root.getChildren().add(borderPane);

        primaryStage.show();

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
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
