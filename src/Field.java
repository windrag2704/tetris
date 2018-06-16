class Field {
    private Matrix field = new Matrix(10, 20);
    private Figure figure = new Figure();
    private int offsetX = 4;
    private int offsetY = 0;
    private boolean gameOver = false;
    private boolean pause = false;
    private Matrix nextFigureField = new Matrix(4, 2);
    private int score;

    void start() {
        figure.setRandom();
        offsetX = 4;
        offsetY = 0;
        if (!checkNext(figure.getPosition(offsetX, offsetY))) {
            gameOver = true;
            return;
        }
        insertFigure();
        nextFigureField.clear();
        insertNextFigure();

        Controller.refresh();
    }

    void restart() {
        gameOver = false;
        field.clear();
        start();
    }

    int getNextFigureCellValue(int y, int x) {
        return nextFigureField.get(y, x);
    }

    int getCellValue(int y, int x) {
        return field.get(y, x);
    }

    private void insertNextFigure() {
        nextFigureField.clear();
        int[][] nextFigure = figure.getNextFigure();
        for (int i = 0; i < 4; i++) {
            int y = nextFigure[i][0];
            int x = nextFigure[i][1];
            nextFigureField.set(y, x, figure.getNextFigureNumber());
        }
    }

    public Integer getScore() {
        return score;
    }

    void moveDown() {
        if (gameOver || pause) return;
        removeFigure();
        if (checkNext(figure.getPosition(offsetX, offsetY + 1))) {
            offsetY++;
            insertFigure();
        } else {
            insertFigure();
            removeFullLines();
            start();
        }
        Controller.refresh();
    }

    void moveLeft() {
        if (gameOver || pause) return;
        removeFigure();
        if (checkNext(figure.getPosition(offsetX - 1, offsetY))) {
            offsetX--;
        }
        insertFigure();
        Controller.refresh();
    }

    void moveRight() {
        if (gameOver || pause) return;
        removeFigure();
        if (checkNext(figure.getPosition(offsetX + 1, offsetY))) {
            offsetX++;
        }
        insertFigure();
        Controller.refresh();
    }

    void rotate() {
        if (gameOver || pause) return;
        removeFigure();
        if (checkNext(figure.getRotateCoordinates(offsetX, offsetY))) {
            figure.rotate();
        }
        insertFigure();
        Controller.refresh();
    }

    private boolean checkNext(int[][] nextPosition) {
        for (int i = 0; i < 4; i++) {
            if (nextPosition[i][1] > 9 || nextPosition[i][1] < 0 || nextPosition[i][0] > 19 || nextPosition[i][0] < 0) {
                return false;
            }
            if (field.get(nextPosition[i][0], nextPosition[i][1]) > 0) {
                return false;
            }

        }
        return true;
    }

    private int checkFullLine() {
        boolean full = true;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (field.get(i, j) == 0) {
                    full = false;
                    break;
                }
            }
            if (full) {
                return i;
            }
            full = true;
        }
        return 20;
    }

    private void removeFullLines() {
        int temp;
        do {
            temp = checkFullLine();
            if (temp == 20) break;
            field.removeLine(temp);
            score++;
            for (int i = temp - 1; i >= 0; i--) {
                field.moveLine(i, i + 1);
            }
        } while (true);
        Controller.refresh();
    }

    private void removeFigure() {
        int[][] figurePoints = figure.getPosition(offsetX, offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            field.set(y, x, 0);
        }
    }

    private void insertFigure() {
        int[][] figurePoints = figure.getPosition(offsetX, offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            field.set(y, x, figure.getFigureNumber());
        }
    }

    boolean isGameOver() {
        return gameOver;
    }

    void pause() {
        pause = !pause;
    }
}
