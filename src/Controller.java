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
                if (field.getField().get(i,j) > 0) {
                    switch(field.getField().get(i,j)) {
                        case 1:
                            gc.setFill(Color.CYAN);
                            break;
                        case 2:
                            gc.setFill(Color.ORANGE);
                            break;
                        case 3:
                            gc.setFill(Color.BLUE);
                            break;
                        case 4:
                            gc.setFill(Color.YELLOW);
                            break;
                        case 5:
                            gc.setFill(Color.GREEN);
                            break;
                        case 6:
                            gc.setFill(Color.PURPLE);
                            break;
                        case 7:
                            gc.setFill(Color.RED);
                            break;
                    }
                    gc.fillRect(30*j + 2,30*i + 2,26,26);
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
}
