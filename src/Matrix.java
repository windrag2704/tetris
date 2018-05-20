public class Matrix {
    private int[] matrix;
    private int width;
    private int height;
    public Matrix(int width, int height) {
        matrix = new int[width*height];
        this.height = height;
        this.width = width;
        for (int i = 0; i < width*height;i++) {
            matrix[i] = 0;
        }
    }
    public int get(int y, int x) {
        return matrix[y*width + x];
    }
    public void set(int y, int x, int value) {
        matrix[y*width + x] = value;
    }
    public void clear() {
        for (int i = 0; i < width * height; i++) {
            matrix[i] = 0;
        }
    }
}
