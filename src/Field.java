class Field {
    private Matrix gameArea = new Matrix(10, 20);
    private Matrix nextShapeArea = new Matrix(4, 2);
    private Shapes shapes = new Shapes();
    private int offsetX = 4;
    private int offsetY = 0;
    private int score;

    boolean spawnShape() {
        shapes.setRandom();
        offsetX = 4;
        offsetY = 0;
        nextShapeArea.clear();
        insertNextShape();
        if (!checkNext(shapes.getPosition(offsetX, offsetY))) {
            return false;
        }
        insertShape();
        return true;
    }

    void clear() {
        gameArea.clear();
        nextShapeArea.clear();
    }

    int getNextShapeCellValue(int y, int x) {
        return nextShapeArea.get(y, x);
    }

    int getCellValue(int y, int x) {
        return gameArea.get(y, x);
    }

    private void insertNextShape() {
        nextShapeArea.clear();
        int[][] nextShape = shapes.getNextShape();
        for (int i = 0; i < 4; i++) {
            int y = nextShape[i][0];
            int x = nextShape[i][1];
            nextShapeArea.set(y, x, shapes.getNextShapeNumber());
        }
    }

    Integer getScore() {
        return score;
    }

    boolean moveDown() {
        boolean result = false;
        removeShape();
        if (checkNext(shapes.getPosition(offsetX, offsetY + 1))) {
            offsetY++;
            result = true;
        }
        insertShape();
        return result;
    }

    void moveLeft() {
        removeShape();
        if (checkNext(shapes.getPosition(offsetX - 1, offsetY))) {
            offsetX--;
        }
        insertShape();
    }

    void moveRight() {
        removeShape();
        if (checkNext(shapes.getPosition(offsetX + 1, offsetY))) {
            offsetX++;
        }
        insertShape();
    }

    void rotateShape() {
        removeShape();
        if (checkNext(shapes.getRotateCoordinates(offsetX, offsetY))) {
            shapes.rotate();
        }
        insertShape();
    }

    private boolean checkNext(int[][] nextPosition) {
        for (int i = 0; i < 4; i++) {
            if (nextPosition[i][1] > 9 || nextPosition[i][1] < 0 || nextPosition[i][0] > 19 || nextPosition[i][0] < 0) {
                return false;
            }
            if (gameArea.get(nextPosition[i][0], nextPosition[i][1]) > 0) {
                return false;
            }

        }
        return true;
    }

    private int checkFullLine() {
        boolean full = true;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameArea.get(i, j) == 0) {
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

    int removeFullLines() {
        int result = 0;
        int temp;
        do {
            temp = checkFullLine();
            if (temp == 20) break;
            gameArea.removeLine(temp);
            result++;
            for (int i = temp - 1; i >= 0; i--) {
                gameArea.moveLine(i, i + 1);
            }
        } while (true);
        return result;
    }

    private void removeShape() {
        int[][] figurePoints = shapes.getPosition(offsetX, offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            gameArea.set(y, x, 0);
        }
    }

    private void insertShape() {
        int[][] figurePoints = shapes.getPosition(offsetX, offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            gameArea.set(y, x, shapes.getShapeNumber());
        }
    }
    int getNextShapeNumber() {
        return shapes.getNextShapeNumber();
    }
}
