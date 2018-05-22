import java.util.Timer;
import java.util.TimerTask;

public class Tetris {
    private Field field = new Field();
    private boolean pause = false;
    Timer timer;
    public Tetris() {
        Controller.field = field;
    }
    public void launch() {
        field.start();
        start();
    }
    public void pause() {
        field.pause();
        if (pause) {
            start();
        } else {
            stop();
        }
    }
    public void stop() {
        pause = true;
        timer.cancel();
        timer = null;
    }
    public void start() {
        pause = false;
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
                       }, 0, 1000
        );
    }
}
