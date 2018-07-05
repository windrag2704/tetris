class Matrix {
    private int[] matrix;
    private int width;
    private int height;

    Matrix(int width, int height) {
        matrix = new int[width * height];
        this.height = height;
        this.width = width;
        for (int i = 0; i < width * height; i++) {
            matrix[i] = 0;
        }
    }

    synchronized int get(int y, int x) {
        return matrix[y * width + x];
    }

    synchronized void set(int y, int x, int value) {
        matrix[y * width + x] = value;
    }

    void clear() {
        for (int i = 0; i < width * height; i++) {
            matrix[i] = 0;
        }
    }

    void removeLine(int lineNum) {
        for (int i = 0; i < width; i++) {
            matrix[lineNum * width + i] = 0;
        }
    }

    void moveLine(int src, int dest) {
        System.arraycopy(matrix,src*width,matrix, dest*width,width);
    }
}
