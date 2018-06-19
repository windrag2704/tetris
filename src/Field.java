class Field {
    private Matrix field = new Matrix(10, 20);
    private Shapes shapes = new Shapes();
    private int offsetX = 4;
    private int offsetY = 0;
    private boolean gameOver = false;
    private boolean pause = false;
    private Matrix nextShapeField = new Matrix(4, 2);
    private int score;

    void start() {
        shapes.setRandom();
        offsetX = 4;
        offsetY = 0;
        if (!checkNext(shapes.getPosition(offsetX, offsetY))) {
            gameOver = true;
            return;
        }
        insertShape();
        nextShapeField.clear();
        insertNextShape();

        Controller.refresh();
    }

    void restart() {
        gameOver = false;
        field.clear();
        start();
    }

    int getNextShapeCellValue(int y, int x) {
        return nextShapeField.get(y, x);
    }

    int getCellValue(int y, int x) {
        return field.get(y, x);
    }

    private void insertNextShape() {
        nextShapeField.clear();
        int[][] nextShape = shapes.getNextShape();
        for (int i = 0; i < 4; i++) {
            int y = nextShape[i][0];
            int x = nextShape[i][1];
            nextShapeField.set(y, x, shapes.getNextShapeNumber());
        }
    }

    Integer getScore() {
        return score;
    }

    void moveDown() {
        if (gameOver || pause) return;
        removeShape();
        if (checkNext(shapes.getPosition(offsetX, offsetY + 1))) {
            offsetY++;
            insertShape();
        } else {
            insertShape();
            removeFullLines();
            start();
        }
        Controller.refresh();
    }

    void moveLeft() {
        if (gameOver || pause) return;
        removeShape();
        if (checkNext(shapes.getPosition(offsetX - 1, offsetY))) {
            offsetX--;
        }
        insertShape();
        Controller.refresh();
    }

    void moveRight() {
        if (gameOver || pause) return;
        removeShape();
        if (checkNext(shapes.getPosition(offsetX + 1, offsetY))) {
            offsetX++;
        }
        insertShape();
        Controller.refresh();
    }

    void rotate() {
        if (gameOver || pause) return;
        removeShape();
        if (checkNext(shapes.getRotateCoordinates(offsetX, offsetY))) {
            shapes.rotate();
        }
        insertShape();
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

    private void removeShape() {
        int[][] figurePoints = shapes.getPosition(offsetX, offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            field.set(y, x, 0);
        }
    }

    private void insertShape() {
        int[][] figurePoints = shapes.getPosition(offsetX, offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            field.set(y, x, shapes.getShapeNumber());
        }
    }

    boolean isGameOver() {
        return gameOver;
    }

    void pause() {
        pause = !pause;
    }
}
