import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Controller {
    public static GraphicsContext gc;
    public static Field field;
    public static void refresh() {
        gc.clearRect(0,0,300,600);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (field.getField().get(i,j)==1) {
                    gc.fillRect(30*j,30*i,25,25);
                }
            }
        }
        if (field.isGameOver()) {
            Font font = new Font("Game Over",35);
            gc.setFont(font);
            gc.setFill(Color.RED);
            gc.fillText("Game Over",50,100);
        }
    }
    public static void moveRight() {
        field.moveRight();
    }
    public static void moveLeft() {
        field.moveLeft();
    }
    public static void rotate() {
        field.rotate();
    }
}
