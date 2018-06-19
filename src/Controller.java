import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class Controller {
    static GraphicsContext gc;
    static Field field;
    static GraphicsContext gcNext;
    static Text score;
    private static void drawRect(int i, int j, int figure, GraphicsContext gc) {
        if (figure > 0) {
                    switch (figure) {
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
                    gc.fillRect(30 * j + 2, 30 * i + 2, 26, 26);
                }
    }

    static void refresh() {
        gc.clearRect(0, 0, 300, 600);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                drawRect(i,j,field.getCellValue(i,j),gc);
            }
        }
        gcNext.clearRect(0,0,120,60);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                drawRect(i, j, field.getNextShapeCellValue(i,j),gcNext);
            }
        }
        score.setText(field.getScore().toString());

        if (field.isGameOver()) {
            Font font = new Font("Game Over", 35);
            gc.setFont(font);
            gc.setFill(Color.RED);
            gc.fillText("Game Over", 50, 100);
        }
    }
}
