import java.util.Timer;
import java.util.TimerTask;

public class Tetris {
    private Field field = new Field();
    public Tetris() {
        Controller.field = field;
        field.start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               field.moveDown();
                               Controller.refresh();
                               if (field.isGameOver()){
                                   timer.cancel();
                               }
                           }
                       }, 0, 100
        );
    }
}
