package uk.bonsall2004;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JPanel implements Runnable {
  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;
  final int FPS = 60;
  volatile Thread gameThread;
  PlayManager playManager;

  public TetrisPanel() {
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.black);
    this.setLayout(null);

    this.addKeyListener(new KeyboardHandler());
    this.setFocusable(true);

    playManager = new PlayManager();
  }

  public void LaunchTetris() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  private void update() {
    if (!KeyboardHandler.pausePressed) {
      playManager.update();
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    playManager.draw(g2);
  }

  @Override
  public void run() {
    double drawInterval = (double) 1000000000 / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    while (gameThread != null) {
      currentTime = System.nanoTime();

      delta += (currentTime - lastTime) / drawInterval;
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
      }
    }
  }
}
