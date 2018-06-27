import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

class Controller {
    static GraphicsContext gc;
    static Tetris game;
    static GraphicsContext gcNext;
    static Text score;
    static Text info;

    private static void drawRect(int i, int j, int figure, GraphicsContext gc, int offX, int offY) {
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
            gc.fillRect(30 * j + 2 + offX, 30 * i + 2 + offY, 26, 26);
        }
    }

    static void refresh() {
        gc.clearRect(0, 0, 300, 600);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                drawRect(i, j, game.getCellValue(i, j), gc, 0, 0);
            }
        }
        gcNext.clearRect(0, 0, 120, 60);
        int offX;
        int offY;
        switch (game.getNextShapeNumber()) {
            case 1:
                offX = 0;
                offY = 15;
                break;
            case 4:
                offX = 30;
                offY = 0;
                break;
            default:
                offX = 15;
                offY = 0;
                break;
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                drawRect(i, j, game.getNextShapeCellValue(i, j), gcNext, offX, offY);
            }
        }
        score.setText(String.format("%03d", game.getScore()));

        if (game.isStopped()) {
            info.setFill(Color.valueOf("#aaaaaa"));
            info.setText("STOPPED");
        } else if (game.isGameOver()) {
            info.setFill(Color.RED);
            info.setText("GAME OVER");
        } else if (game.isPause()) {
            info.setFill(Color.valueOf("#aaaaaa"));
            info.setText("PAUSE");
        } else {
            info.setText("");
        }
    }
}
