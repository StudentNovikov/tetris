package main;

import java.awt.*;
import java.awt.image.BufferedImage;

class Shape implements IDrawable {

    private BufferedImage block;
    private int[][] coordinates;
    private Board board;
    private int deltaX = 0;
    private int x, y;
    private long time, lastTime;
    private int normalSpeed = 800;
    private int currentSpeed;
    private boolean collision = false;
    private boolean moveX = false;
    private int color;

    Shape(BufferedImage block, int[][] coordinates, Board board, int color) {
        this.block = block;
        this.coordinates = coordinates;
        this.board = board;
        this.color = color;
        time = 0;
        lastTime = System.currentTimeMillis();
        currentSpeed = normalSpeed;
        x = 4;
        y = 0;
    }

    int[][] getCoordinates() {
        return coordinates;
    }

    int getColor() {
        return color;
    }

    void setDeltaX(int x) {
        this.deltaX = x;

    }

    BufferedImage getBlock() {
        return block;
    }

    void update() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (collision) {

            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[row].length; col++) {
                    if (coordinates[row][col] != 0) {
                        board.getBoard()[y + row][x + col] = color;
                    }
                }
            }

            checkLine();
            board.setNextShape();
        }


        if (((x + deltaX) >= 0) && ((x + deltaX + coordinates[0].length < 11))) {

            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[row].length; col++) {
                    if (coordinates[row][col] != 0) {
                        if (board.getBoard()[y + row][x + deltaX + col] != 0) {
                            moveX = false;
                        }
                    }
                }
            }

            if (moveX) {
                x += deltaX;
            }
        }

        if (y + 1 + coordinates.length < 20) {

            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[row].length; col++) {
                    if (coordinates[row][col] != 0) {
                        if (board.getBoard()[y + row + 1][col + x] != 0) {
                            collision = true;
                        }
                    }
                }
            }


            if (time > currentSpeed) {
                y++;
                time = 0;
            }
        } else {
            collision = true;
        }
        moveX = true;
        deltaX = 0;
    }

    public void render(Graphics g) {

        for (int row = 0; row < coordinates.length; row++) {
            for (int col = 0; col < coordinates[row].length; col++) {
                if (coordinates[row][col] == 1) {
                    g.drawImage(block, board.getBlockSize() * (col + x), board.getBlockSize() * (row + y), null);
                }
            }
        }

    }

    void speedDown() {
        currentSpeed = 150;
    }

    void speedNormal() {
        currentSpeed = normalSpeed;
    }

    void rotate() {
        if (collision) {
            return;
        }
        int[][] rotatedMatrix;
        rotatedMatrix = getTransposeMatrix(coordinates);
        rotatedMatrix = getReverseMatrix(rotatedMatrix);

        if ((x + rotatedMatrix[0].length > 10) || (y + rotatedMatrix.length > 19)) {
            return;
        }

        for (int row = 0; row < rotatedMatrix.length; row++) {
            for (int col = 0; col < rotatedMatrix[0].length; col++) {
                if (board.getBoard()[y + row][x + col] != 0) {
                    return;
                }
            }
        }
        coordinates = rotatedMatrix;

    }

    private void checkLine() {
        int height = board.getBoard().length - 1;

        for (int i = height; i > 0; i--) {

            int count = 0;

            for (int j = 0; j < board.getBoard()[0].length; j++) {

                if (board.getBoard()[i][j] != 0) {
                    count++;
                }

                board.getBoard()[height][j] = board.getBoard()[i][j];
            }
            if (count < board.getBoard()[0].length) {
                height--;
            }
        }
    }

    private int[][] getTransposeMatrix(int[][] matrix) {

        int[][] newMatrix = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[j][i] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    private int[][] getReverseMatrix(int[][] matrix) {
        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++) {
            int[] m = matrix[i];
            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = m;
        }
        return matrix;
    }
}
