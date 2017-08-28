package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Alex on 26.08.2017.
 */
public class Board extends JPanel implements KeyListener {

    private BufferedImage blocks;

    private final int blockSize = 45;

    private final int boardWidth = 15;
    private final int boardHeight = 30;
    private Shape[] shapes = new Shape[7];
    private Shape currentShape;

    private int[][] board = new int[boardWidth][boardHeight];

    private Timer timer;

    private final int FPS = 60;

    private final int delay = 1000 / FPS;

    public Board() {


        try {
            blocks = ImageIO.read(Board.class.getResource("/borders.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });

        shapes[0] = new Shape(blocks.getSubimage(0, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1, 1} //longShape
        }, this);

        timer.start();

        shapes[1] = new Shape(blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][]{
                {1, 1, 0},
                {0, 1, 1}     //zShape
        }, this);

        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize), new int[][]{
                {0, 1, 1},
                {1, 1, 0}     //sShape
        }, this);

        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {0, 0, 1}     //jShape
        }, this);

        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {1, 0, 0}     //lShape
        }, this);

        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize), new int[][]{
                {1, 1, 1},
                {0, 1, 0}     //tShape
        }, this);

        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize), new int[][]{
                {1, 1},
                {1, 1}     //oShape
        }, this);

        setNextShape();


    }

    public void update() {
        currentShape.update();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        currentShape.render(g);

        for (int i = 0; i < boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }

        for (int j = 0; j < boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }

    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setNextShape() {

        int index = (int) (Math.random()*shapes.length);

        Shape newShape = new Shape(shapes[index].getBlock(),shapes[index].getCoords(),this);

        currentShape = newShape;
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
