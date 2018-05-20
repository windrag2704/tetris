import javafx.scene.canvas.GraphicsContext;

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
    }
    public static void moveRight() {
        field.moveRight();
    }
    public static void moveLeft() {
        field.moveLeft();
    }
}
