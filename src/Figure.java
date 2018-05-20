public class Figure {
    final private Point[] I = new Point[4];
    final private Point[] Z = new Point[4];
    final private Point[] L = new Point[4];
    final private Point[] Li = new Point[4];
    final private Point[] T = new Point[4];
    final private Point[] O = new Point[4];
    final private Point[] Zi = new Point[4];
    private Point[] current;
    public Figure() {
        init();
    }
    private void init() {
        I[0] = new Point(3, 0);
        I[1] = new Point(4, 0);
        I[2] = new Point(5, 0);
        I[3] = new Point(6, 0);
        Z[0] = new Point(4, 0);
        Z[1] = new Point(5, 0);
        Z[2] = new Point(5, 1);
        Z[3] = new Point(6, 1);
        L[0] = new Point(4, 1);
        L[1] = new Point(4, 0);
        L[2] = new Point(5, 0);
        L[3] = new Point(6, 0);
        Li[0] = new Point(4, 0);
        Li[1] = new Point(5, 0);
        Li[2] = new Point(6, 0);
        Li[3] = new Point(6, 1);
        T[0] = new Point(4, 0);
        T[1] = new Point(5, 0);
        T[2] = new Point(6, 0);
        T[3] = new Point(5, 1);
        O[0] = new Point(4, 0);
        O[1] = new Point(5, 0);
        O[2] = new Point(5, 1);
        O[3] = new Point(4, 1);
        Zi[0] = new Point(4, 1);
        Zi[1] = new Point(5, 1);
        Zi[2] = new Point(5, 0);
        Zi[3] = new Point(6, 0);
    }
    public void setRandom() {
        init();
        int temp = (int)(Math.random() * 10) % 7;
        switch (temp) {
            case 0:
                current = I;
                break;
            case 1:
                current = Z;
                break;
            case 2:
                current = Zi;
                break;
            case 3:
                current = L;
                break;
            case 4:
                current = Li;
                break;
            case 5:
                current = O;
                break;
            case 6:
                current = T;
                break;
        }
    }
    public Point[] getNextPosition(Point offset) {
        Point[] result = new Point[4];
        for (int i = 0; i < 4; i++) {
            result[i] = new Point(current[i].getX()+offset.getX(),current[i].getY()+offset.getY());
        }
        return result;
    }
    public void setPosition(Point offset) {
        for (int i = 0; i < 4; i++) {
            current[i].setX(current[i].getX() + offset.getX());
            current[i].setY(current[i].getY() + offset.getY());
        }
    }
    public Point[] getPosition() {
        return current;
    }
}
