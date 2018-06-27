import java.util.Timer;
import java.util.TimerTask;

class Tetris {
    private Field field = new Field();
    private boolean pause = false;
    private boolean stopped = true;
    private int level = 1000;
    private int period = 1000;
    private int delay = 1000;
    private int score = 0;
    private boolean gameOver = false;
    private Timer timer;

    void start() {
        field.spawnShape();
        stopped = false;
        Controller.refresh();
        startTimer();
    }

    boolean isStopped() {
        return stopped;
    }

    void figureFall() {
        if (stopped || pause || gameOver || period <= 50) return;
        period = 50;
        delay = 0;
        stopTimer();
        startTimer();
    }

    boolean isGameOver() {
        return gameOver;
    }

    Integer getScore() {
        return score;
    }

    void moveDown() {
        if (pause || gameOver || stopped) return;
        System.out.println(level);
        if (!field.moveDown()) {
            int removedLines = field.removeFullLines();
            score += removedLines;
            gameOver = !field.spawnShape();
            if (removedLines > score % 5) {
                for (int i = 0; i < removedLines / 5 + ((score - removedLines) % 5 + removedLines % 5) / 5; i++) {
                    level = (int) (level * 0.9);
                }
                period = level;
                delay = level;
                stopTimer();
                startTimer();
            }
        }
        Controller.refresh();
    }

    int getCellValue(int y, int x) {
        return field.getCellValue(y, x);
    }

    int getNextShapeCellValue(int y, int x) {
        return field.getNextShapeCellValue(y, x);
    }

    int getNextShapeNumber() {
        return field.getNextShapeNumber();
    }

    boolean isPause() {
        return pause;
    }

    void moveLeft() {
        if (pause || gameOver || stopped) return;
        field.moveLeft();
        Controller.refresh();
    }

    void moveRight() {
        if (pause || gameOver || stopped) return;
        field.moveRight();
        Controller.refresh();
    }

    void rotate() {
        if (pause || gameOver || stopped) return;
        field.rotateShape();
        Controller.refresh();
    }

    void stop() {
        field.clear();
        period = 1000;
        level = 1000;
        delay = 1000;
        stopTimer();
        score = 0;
        stopped = true;
        gameOver = false;
        pause = false;
        Controller.refresh();
    }

    void restart() {
        field.clear();
        period = 1000;
        level = 1000;
        delay = 1000;
        stopTimer();
        startTimer();
    }

    void figureRestore() {
        if (gameOver || stopped || pause || period == level) return;
        period = level;
        delay = level;
        stopTimer();
        startTimer();
    }

    void pause() {
        if (gameOver || stopped) return;
        pause = !pause;
        if (pause) {
            stopTimer();
        } else {
            startTimer();
        }
        Controller.refresh();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void startTimer() {
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
                    Controller.refresh();
                }
            }
        }, delay, period);
    }
}
