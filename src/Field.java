public class Field {
    private Matrix field = new Matrix(10,20);
    private Figure figure = new Figure();
    private int offsetX = 4;
    private int offsetY = 0;
    private boolean gameOver = false;
    public void start() {
        figure.setRandom();
        insertFigure();
        Controller.refresh();
    }
    public Matrix getField() {
        return field;
    }
    public void moveDown() {
        if (gameOver) return;
        removeFigure();
        if (checkNext(figure.getPosition(offsetX,offsetY + 1))){
            offsetY++;
            insertFigure();
        } else {
            insertFigure();
            figure.setRandom();
            offsetX = 4;
            offsetY = 0;
            if (!checkNext(figure.getPosition(offsetX,offsetY))) {
                gameOver = true;
                return;
            }
            insertFigure();
        }
        Controller.refresh();
    }
    public void moveLeft() {
        if (gameOver) return;
        removeFigure();
        if (checkNext(figure.getPosition(offsetX -1,offsetY))) {
            offsetX--;
        }
        insertFigure();
        Controller.refresh();
    }
    public void moveRight() {
        if (gameOver) return;
        removeFigure();
        if (checkNext(figure.getPosition(offsetX + 1,offsetY))) {
            offsetX++;
        }
        insertFigure();
        Controller.refresh();
    }
    public void rotate() {
        if (gameOver) return;
        removeFigure();
        if (checkNext(figure.getRotateCoordinates(offsetX,offsetY))){
            figure.rotate();
        } else {
        }
        insertFigure();
    }
    private boolean checkNext(int[][] nextPosition) {
        for (int i = 0; i < 4; i++) {
           if (nextPosition[i][1] > 9 || nextPosition[i][1] < 0 || nextPosition[i][0] > 19 || nextPosition[i][0] < 0) {
               return false;
           }
           if (field.get(nextPosition[i][0], nextPosition[i][1]) == 1) {
               return false;
           }

        }
        return true;
    }
    public void clearField() {
        field.clear();
    }
    public void removeFigure() {
        int[][] figurePoints = figure.getPosition(offsetX,offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            field.set(y, x, 0);
        }
    }
    public void insertFigure() {
        int[][] figurePoints = figure.getPosition(offsetX,offsetY);
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i][1];
            int y = figurePoints[i][0];
            field.set(y,x,1);
        }
    }
    public boolean isGameOver() {
        return gameOver;
    }
}
