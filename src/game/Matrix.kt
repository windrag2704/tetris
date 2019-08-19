package game

internal class Matrix(private val width: Int, private val height: Int) {
    private val matrix: IntArray = IntArray(width * height)

    init {
        for (i in 0 until width * height) {
            matrix[i] = 0
        }
    }

    operator fun get(y: Int, x: Int): Int {
        return matrix[y * width + x]
    }

    @Synchronized
    operator fun set(y: Int, x: Int, value: Int) {
        matrix[y * width + x] = value
    }

    fun clear() {
        for (i in 0 until width * height) {
            matrix[i] = 0
        }
    }

    fun removeLine(lineNum: Int) {
        for (i in 0 until width) {
            matrix[lineNum * width + i] = 0
        }
    }

    fun moveLine(src: Int, dest: Int) {
        System.arraycopy(matrix, src * width, matrix, dest * width, width)
    }
}
