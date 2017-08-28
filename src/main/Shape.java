package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Alex on 26.08.2017.
 */
public class Shape {

    private BufferedImage block;
    private int[][] coords;
    private Board board;
    private int deltaX = 0;
    private int x, y;
    private long time, lastTime;
    private int normalSpeed = 800;
    private int speedDown = 250;
    private int currentSpeed;
    private boolean collision = false;

    public Shape(BufferedImage block, int[][] coords, Board board) {
        this.block = block;
        this.coords = coords;
        this.board = board;
        time = 0;
        lastTime = System.currentTimeMillis();
        currentSpeed = normalSpeed;
        x = 4;
        y = 0;
    }

    public int[][] getCoords(){
        return coords;
    }

    public BufferedImage getBlock(){
        return block;
    }



    public void update() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (collision) {
            board.setNextShape();
        }

        if (((x + deltaX) >= 0) && ((x + deltaX + coords[0].length < 11))) {
            x += deltaX;
        }

        if (y + 1 + coords.length < 20) {

            if (time > currentSpeed) {
                y++;
                time = 0;
            }
        } else {
            collision = true;
        }

        deltaX = 0;
    }

    public void render(Graphics g) {

        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[row].length; col++) {
                if (coords[row][col] == 1) {
                    g.drawImage(block, board.getBlockSize() * (col + x), board.getBlockSize() * (row + y), null);
                }
            }
        }

    }

    public void setDeltaX(int x) {
        this.deltaX = x;

    }

    public void speedDown() {
        currentSpeed = speedDown;
    }

    public void speedNormal() {
        currentSpeed = normalSpeed;
    }

    public void rotate() {
        int[][] rotatedMatrix = null;
        rotatedMatrix = getTransponseMatrix(coords);
        rotatedMatrix = getReverseMatrix(rotatedMatrix);


        if ((x + rotatedMatrix[0].length > 10) || (y + rotatedMatrix.length > 19)) {
            return;
        }
        coords = rotatedMatrix;
        //  coords = getReverseMatrix(getTransponseMatrix(coords));
    }

    private int[][] getTransponseMatrix(int[][] matrix) {

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
