import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Tetris");
        Scene scene = new Scene(root,300,600);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(300,600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        primaryStage.show();
        Controller.gc = gc;
        Tetris tetris = new Tetris();
        scene.setOnKeyPressed(event -> {
               String code = event.getCode().toString();
               if (code.equals("LEFT")) {
                   Controller.moveLeft();
               }
               if (code.equals("RIGHT")) {
                   Controller.moveRight();
               }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
