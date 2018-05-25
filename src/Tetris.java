import java.util.Timer;
import java.util.TimerTask;

public class Tetris {
    private Field field = new Field();
    private boolean pause = true;
    private int period = 1000;
    private int delay = 1000;
    Timer timer;

    public Tetris() {
        Controller.field = field;
    }

    public void launch() {
        field.start();
        start();
        pause = false;
    }

    public void figureFall() {
        if (period == 50) return;
        period = 50;
        delay = 0;
        stop();
        start();
    }
    public void moveLeft() {
        field.moveLeft();
    }
    public void moveRight() {
        field.moveRight();
    }
    public void rotate() {
        field.rotate();
    }
    public void restart() {
        field.restart();
        period = 1000;
        delay = 1000;
        stop();
        start();
    }

    public void figureRestore() {
        if (period == 1000) return;
        period = 1000;
        delay = 1000;
        stop();
        start();
    }

    public void pause() {
        pause = !pause;
        field.pause();
        if (pause) {
            start();
        } else {
            stop();
        }
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }

    public void start() {
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
                    timer.cancel();
                }
            }
        }, delay, period);
    }
}
