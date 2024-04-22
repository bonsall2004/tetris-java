package uk.bonsall2004;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    JFrame window = new JFrame("Tetris | Bonsall2004");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);

    TetrisPanel panel = new TetrisPanel();
    window.add(panel);
    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);

    panel.LaunchTetris();
  }
}