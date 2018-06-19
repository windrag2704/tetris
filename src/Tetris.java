import java.util.Timer;
import java.util.TimerTask;

class Tetris {
    private Field field = new Field();
    private boolean pause = true;
    private int period = 1000;
    private int delay = 1000;
    private boolean gameOver = false;
    private Timer timer;

    Tetris() {
        Controller.field = field;
    }

    void launch() {
        field.start();
        start();
        pause = false;
    }

    void figureFall() {
        if (pause || period == 50) return;
        period = 50;
        delay = 0;
        stop();
        start();
    }

    void moveLeft() {
        field.moveLeft();
    }

    void moveRight() {
        field.moveRight();
    }

    void rotate() {
        field.rotate();
    }

    void restart() {
        field.restart();
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
        field.pause();
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
                field.moveDown();
                Controller.refresh();
                if (field.isGameOver()) {
                    gameOver = field.isGameOver();
                    timer.cancel();
                    timer = null;
                }
            }
        }, delay, period);
    }
}
