package main;

import javax.swing.*;

public class Window {

    private static final int HEIGHT = 880;
    private static final int WIDTH = 455;

    private Window() {
        JFrame window = new JFrame("Super game");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        Board board = new Board();
        window.add(board);
        window.addKeyListener(board);
        window.setVisible(true);
    }

    public static void main(String args[]) {
        new Window();
    }

}
