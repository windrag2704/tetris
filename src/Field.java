public class Field {
    private Matrix field = new Matrix(10,20);
    private Figure figure = new Figure();
    public Field() {
        figure.setRandom();
        insertFigure();
    }
    public Matrix getField() {
        return field;
    }
    public void moveDown() {
        removeFigure();
        if (checkNext(figure.getNextPosition(new Point(0,1)))){
            figure.setPosition(new Point(0,1));
            insertFigure();
        } else {
            insertFigure();
            figure.setRandom();
            insertFigure();
        }
        Controller.refresh();
    }
    public void moveLeft() {
        removeFigure();
        if (checkNext(figure.getNextPosition(new Point(-1,0)))) {
            figure.setPosition(new Point(-1,0));
        }
        insertFigure();
        Controller.refresh();
    }
    public void moveRight() {
        removeFigure();
        if (checkNext(figure.getNextPosition(new Point(1,0)))) {
            figure.setPosition(new Point(1,0));
        }
        insertFigure();
        Controller.refresh();
    }
    public void rotate() {

    }
    private boolean checkNext(Point[] nextPosition) {
        for (int i = 0; i < 4; i++) {
           if (nextPosition[i].getX() > 9 || nextPosition[i].getX() < 0 || nextPosition[i].getY() > 19) {
                return false;
           }
           if (field.get(nextPosition[i].getY(), nextPosition[i].getX()) == 1) {
               return false;
           }

        }
        return true;
    }
    public void clearField() {
        field.clear();
    }
    public void removeFigure() {
        Point[] figurePoints = figure.getPosition();
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i].getX();
            int y = figurePoints[i].getY();
            field.set(y, x, 0);
        }
    }
    public void insertFigure() {
        Point[] figurePoints = figure.getPosition();
        for (int i = 0; i < 4; i++) {
            int x = figurePoints[i].getX();
            int y = figurePoints[i].getY();
            field.set(y,x,1);
        }
    }
}
