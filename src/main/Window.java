package main;

import javax.swing.*;

/**
 * Created by Alex on 26.08.2017.
 */
public class Window {

    public static final int HEIGHT = 880;
    public static final int WIDTH = 455;
    private JFrame window;
    private Board board;


    Window() {
        window = new JFrame("Tetris game");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        board = new Board();
        window.add(board);
        window.addKeyListener(board);
        window.setVisible(true);
    }

    public static void main(String args[]) {
        new Window();
    }

}
