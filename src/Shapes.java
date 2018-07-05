import java.util.concurrent.ThreadLocalRandom;

class Shapes {
    private int[][][] current;
    private int figureNumber = 0;
    private int[][][][] figures =
            {
                    {//I
                            {
                                    {0, 0}, {0, 1}, {0, 2}, {0, 3}
                            },
                            {
                                    {-1, 1}, {0, 1}, {1, 1}, {2, 1}
                            }
                    },
                    {//L
                            {
                                    {1, 0}, {0, 0}, {0, 1}, {0, 2}
                            },
                            {
                                    {-1, 0}, {-1, 1}, {0, 1}, {1, 1}
                            },
                            {
                                    {1, 0}, {1, 1}, {1, 2}, {0, 2}
                            },
                            {
                                    {-1, 1}, {0, 1}, {1, 1}, {1, 2}
                            }
                    },
                    {//J
                            {
                                    {0, 0}, {0, 1}, {0, 2}, {1, 2}
                            },
                            {
                                    {-1, 1}, {0, 1}, {1, 1}, {1, 0}
                            },
                            {
                                    {0, 0}, {1, 0}, {1, 1}, {1, 2}
                            },
                            {
                                    {-1, 2}, {-1, 1}, {0, 1}, {1, 1}
                            }
                    },
                    {//O
                            {
                                    {0, 0}, {0, 1}, {1, 1}, {1, 0}
                            }
                    },
                    {//S
                            {
                                    {1, 0}, {1, 1}, {0, 1}, {0, 2}
                            },
                            {
                                    {-1, 1}, {0, 1}, {0, 2}, {1, 2}
                            },
                    },
                    {//T
                            {
                                    {0, 0}, {0, 1}, {0, 2}, {1, 1}
                            },
                            {
                                    {0, 0}, {0, 1}, {1, 1}, {-1, 1}
                            },
                            {
                                    {0, 0}, {0, 1}, {0, 2}, {-1, 1}
                            },
                            {
                                    {-1, 1}, {0, 1}, {0, 2}, {1, 1}
                            }
                    },
                    {//Z
                            {
                                    {0, 0}, {0, 1}, {1, 1}, {1, 2}
                            },
                            {
                                    {-1, 2}, {0, 2}, {0, 1}, {1, 1}
                            }
                    }
            };
    private int state = 0;
    private int next = ThreadLocalRandom.current().nextInt(0,7);
    private int nextShapeNumber;

    void rotate() {
        state = (state + 1) % current.length;
    }

    int[][] getRotateCoordinates(int offsetX, int offsetY) {
        int[][] result = new int[current[0].length][2];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = current[(state + 1) % current.length][i][0] + offsetY;
            result[i][1] = current[(state + 1) % current.length][i][1] + offsetX;
        }
        return result;
    }

    int[][] getPosition(int offsetX, int offsetY) {
        int[][] result = new int[current[0].length][2];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = current[state][i][0] + offsetY;
            result[i][1] = current[state][i][1] + offsetX;
        }
        return result;
    }

    int getShapeNumber() {
        return figureNumber;
    }

    void setRandom() {
        state = 0;
        int temp = next;
        next = ThreadLocalRandom.current().nextInt(0, 700000000) / 100000000;
        nextShapeNumber = next + 1;
        figureNumber = temp + 1;
        current = figures[temp];
    }
    int getNextShapeNumber() {
        return nextShapeNumber;
    }
    int[][] getNextShape() {
        return figures[next][0];
    }
}
