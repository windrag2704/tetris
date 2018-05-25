import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Tetris");
        Scene scene = new Scene(root,800,625);
        primaryStage.setScene(scene);
        Pane pane = new Pane();
        Canvas canvas = new Canvas(300,600);
        BorderPane borderPane = new BorderPane();
        root.getChildren().add(borderPane);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Canvas left = new Canvas(250,600);
        GraphicsContext leftgc = left.getGraphicsContext2D();
        leftgc.setFill(Color.DARKRED);
        Canvas right = new Canvas(250,600);
        GraphicsContext rightgc = right.getGraphicsContext2D();
        rightgc.setFill(Color.DARKRED);
        leftgc.fillRect(0,0,250,600);
        rightgc.fillRect(0,0,250,600);
        borderPane.setRight(right);
        borderPane.setLeft(left);
        Controller.gc = gc;
        Canvas background = new Canvas(300,600);
        pane.getChildren().addAll(canvas,background);
        borderPane.setCenter(pane);
        GraphicsContext gcBack = background.getGraphicsContext2D();
        gcBack.setFill(Color.rgb(0x33,0x33,0x33));
        gcBack.fillRect(0,0,300,600);
        MenuBar menuBar = new MenuBar();
        Menu menuGame = new Menu("Game");
        Menu menuAbout = new Menu("About");
        menuBar.getMenus().addAll(menuGame, menuAbout);
        borderPane.setTop(menuBar);
        Tetris tetris = new Tetris();
        MenuItem startGame = new MenuItem("Start");
        startGame.setOnAction(event -> {
            startGame.setDisable(true);
            tetris.launch();
        });
        menuGame.getItems().add(startGame);
        MenuItem restartGame = new MenuItem("Restart");
        menuGame.getItems().add(restartGame);
        restartGame.setOnAction(event -> {
            tetris.restart();
        });
        MenuItem exitGame = new MenuItem("Exit");
        menuGame.getItems().add(exitGame);
        exitGame.setOnAction(event -> {
            System.exit(0);
        });
        primaryStage.show();
        canvas.toFront();
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
    }


    public static void main(String[] args) {
        launch(args);
    }
}
