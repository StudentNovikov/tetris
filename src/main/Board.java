package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

class Board extends JPanel implements KeyListener {

    private BufferedImage blocks;
    private final int blockSize = 45;
    private final int boardWidth = 10;
    private final int boardHeight = 19;
    private Shape[] shapes = new Shape[7];
    private Shape currentShape;
    private int[][] board = new int[boardHeight][boardWidth];
    private Timer timer;
    private boolean gameOver = false;

    Board() {

        try {
            blocks = ImageIO.read(Board.class.getResource("/borders.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int FPS = 60;
        int delay = 1000 / FPS;
        timer = new Timer(delay, e -> {
            update();
            repaint();
        });

        shapes[0] = new Shape(blocks.getSubimage(0, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1, 1} //longShape
        }, this, 1);

        timer.start();

        shapes[1] = new Shape(blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][]{
                {1, 1, 0},
                {0, 1, 1}     //zShape
        }, this, 2);

        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize), new int[][]{
                {0, 1, 1},
                {1, 1, 0}     //sShape
        }, this, 3);

        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {0, 0, 1}     //jShape
        }, this, 4);

        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {1, 0, 0}     //lShape
        }, this, 5);

        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {0, 1, 0}     //tShape
        }, this, 6);

        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize), new int[][]{
                {1, 1},
                {1, 1}     //oShape
        }, this, 7);

        setNextShape();
    }

    int[][] getBoard() {
        return board;
    }

    int getBlockSize() {
        return blockSize;
    }

    private void update() {
        currentShape.update();
        if (gameOver) {
            timer.stop();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        currentShape.render(g);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != 0) {
                    g.drawImage(blocks.getSubimage((board[row][col] - 1) * blockSize, 0, blockSize, blockSize), col * blockSize, row * blockSize, null);
                }
            }
        }

        for (int i = 0; i < boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }

        for (int j = 0; j < boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }

    }

    void setNextShape() {

        int index = (int) (Math.random() * shapes.length);
        currentShape = new Shape(shapes[index].getBlock(), shapes[index].getCoordinates(), this, shapes[index].getColor());

        for (int row = 0; row < currentShape.getCoordinates().length; row++) {
            for (int col = 0; col < currentShape.getCoordinates()[row].length; col++) {
                if (currentShape.getCoordinates()[row][col] != 0) {
                    if (board[row][col + 4] != 0){
                        gameOver = true;
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.setDeltaX(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.setDeltaX(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotate();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedNormal();
        }
    }
}
