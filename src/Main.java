import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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
        scene.setFill(Color.rgb(0x33,0x33,0x33));
        primaryStage.setScene(scene);
        Tetris tetris = new Tetris();

        BorderPane borderPane = new BorderPane();
        StackPane stackPane = new StackPane();
        GridPane grid = new GridPane();

        Canvas canvas = new Canvas(300, 600);
        Canvas left = new Canvas(250, 600);
        Canvas right = new Canvas(250, 600);
        Canvas nextFigure = new Canvas(120,60);
        Text info = new Text();
        info.setFont(new Font(20));
        info.setFill(Color.rgb(0xaa,0xaa,0xaa));
        info.setTextAlignment(TextAlignment.CENTER);
        Controller.score = info;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcLeft = left.getGraphicsContext2D();
        GraphicsContext gcRight = right.getGraphicsContext2D();
        GraphicsContext gcNext = nextFigure.getGraphicsContext2D();
        stackPane.getChildren().add(right);
        stackPane.getChildren().add(grid);
        grid.setPrefHeight(150);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(400);
        grid.add(nextFigure,0,0);
        grid.add(info,0,1);

        gcLeft.setFill(Color.DARKRED);
        gcLeft.fillRect(0, 0, 250, 600);
        gcRight.setFill(Color.DARKRED);
        gcRight.fillRect(0, 0, 250, 600);
        Controller.gc = gc;
        Controller.gcNext = gcNext;

        MenuBar menuBar = new MenuBar();
        Menu menuGame = new Menu("Game");
        Menu menuAbout = new Menu("About");
        menuBar.getMenus().addAll(menuGame, menuAbout);

        MenuItem startGame = new MenuItem("Start");
        menuGame.getItems().add(startGame);
        startGame.setOnAction(event -> {
            startGame.setDisable(true);
            tetris.launch();
        });

        MenuItem restartGame = new MenuItem("Restart");
        menuGame.getItems().add(restartGame);
        restartGame.setOnAction(event -> tetris.restart() );

        MenuItem exitGame = new MenuItem("Exit");
        menuGame.getItems().add(exitGame);
        exitGame.setOnAction(event -> System.exit(0));

        borderPane.setLeft(left);
        borderPane.setRight(stackPane);
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
