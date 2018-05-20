import java.util.Timer;
import java.util.TimerTask;

public class Tetris {
    private Field field = new Field();
    public Tetris() {
        Controller.field = field;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               field.moveDown();
                               Controller.refresh();
                           }
                       }, 0, 100
        );
    }
}
