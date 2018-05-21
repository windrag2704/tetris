import java.util.concurrent.ThreadLocalRandom;

public class Figure {
    private int[][][] current;
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
                                    {-1, 1}, {0, 1}, {0, 2}, {1, 1}
                            },
                            {
                                    {0, 0}, {0, 1}, {0, 2}, {-1, 1}
                            },
                            {
                                    {0, 0}, {0, 1}, {1, 1}, {-1, 1}
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

    public void rotate() {
        state = (state + 1) % current.length;
    }

    public int[][] getRotateCoordinates(int offsetX, int offsetY) {
        int[][] result = new int[current[0].length][2];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = current[(state + 1) % current.length][i][0] + offsetY;
            result[i][1] = current[(state + 1) % current.length][i][1] + offsetX;
        }
        return result;
    }

    public int[][] getPosition(int offsetX, int offsetY) {
        int[][] result = new int[current[0].length][2];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = current[state][i][0] + offsetY;
            result[i][1] = current[state][i][1] + offsetX;
        }
        return result;
    }

    public void setRandom() {
        state = 0;
        int temp = ThreadLocalRandom.current().nextInt(0,7);
        current = figures[temp];
    }

    public void reset() {
        state = 0;
    }
}
