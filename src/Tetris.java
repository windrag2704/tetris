import java.util.Timer;
import java.util.TimerTask;

class Tetris {
    private Field field = new Field();
    private boolean pause = true;
    private int period = 1000;
    private int delay = 1000;
    private int score = 0;
    private boolean gameOver = false;
    private Timer timer;

    void launch() {
        field.spawnShape();
        Controller.refresh();
        start();
        pause = false;
    }

    void figureFall() {
        if (pause || gameOver || period == 50) return;
        period = 50;
        delay = 0;
        stop();
        start();
    }

    boolean isGameOver() {
        return gameOver;
    }

    Integer getScore() {
        return score;
    }

    void moveDown() {
        if (pause || gameOver) return;
        if (!field.moveDown()) {
            score += field.removeFullLines();
            gameOver = !field.spawnShape();
        }
        Controller.refresh();
    }

    int getCellValue(int y, int x) {
        return field.getCellValue(y, x);
    }

    int getNextShapeCellValue(int y, int x) {
        return field.getNextShapeCellValue(y, x);
    }

    void moveLeft() {
        if (pause || gameOver) return;
        field.moveLeft();
        Controller.refresh();
    }

    void moveRight() {
        if (pause || gameOver) return;
        field.moveRight();
        Controller.refresh();
    }

    void rotate() {
        if (pause || gameOver) return;
        field.rotateShape();
        Controller.refresh();
    }

    void restart() {
        field.clear();
        period = 1000;
        delay = 1000;
        stop();
        start();
    }

    void figureRestore() {
        if (pause || period == 1000) return;
        period = 1000;
        delay = 1000;
        stop();
        start();
    }

    void pause() {
        if (gameOver) return;
        pause = !pause;
        if (pause) {
            stop();
        } else {
            start();
        }
    }

    private void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void start() {
        if (timer != null) {
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                moveDown();
                if (gameOver) {
                    timer.cancel();
                    timer = null;
                }
            }
        }, delay, period);
    }
}
