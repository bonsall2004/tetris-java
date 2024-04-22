package uk.bonsall2004;

import tetromino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayManager {
  public static int left_x;
  public static int right_x;
  public static int top_y;
  public static int bottom_y;
  public static int score = 0;
  public static int dropInterval = 60; // in frames
  public static ArrayList<Block> staticBlocks = new ArrayList<>();
  final int WIDTH = 360;
  final int HEIGHT = 600;
  final int TETROMINO_START_X;
  final int TETROMINO_START_Y;
  final int NEXT_TETROMINO_X;
  final int NEXT_TETROMINO_Y;
  Tetromino currentTetromino;
  Tetromino nextTetromino;


  public PlayManager() {
    left_x = (TetrisPanel.WIDTH / 2) - (WIDTH / 2);
    right_x = left_x + WIDTH;
    top_y = 50;
    bottom_y = top_y + HEIGHT;

    TETROMINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
    TETROMINO_START_Y = top_y + Block.SIZE;

    NEXT_TETROMINO_X = right_x + 105;
    NEXT_TETROMINO_Y = top_y + 500;

    currentTetromino = randomTetromino();
    currentTetromino.setXY(TETROMINO_START_X, TETROMINO_START_Y);
    nextTetromino = randomTetromino();
    nextTetromino.setXY(NEXT_TETROMINO_X, NEXT_TETROMINO_Y);
  }

  private Tetromino randomTetromino() {
    Tetromino tetromino = null;
    int i = new Random().nextInt(7);
    tetromino = switch (i) {
      case 0 -> new Tetromino_L1();
      case 1 -> new Tetromino_L2();
      case 2 -> new Tetromino_Square();
      case 3 -> new Tetromino_T();
      case 4 -> new Tetromino_Z1();
      case 5 -> new Tetromino_Z2();
      case 6 -> new Tetromino_Bar();
      default -> tetromino;
    };
    return tetromino;
  }

  public void update() {
    if (!currentTetromino.active) {
      staticBlocks.addAll(Arrays.asList(currentTetromino.b).subList(0, 4));
      currentTetromino = nextTetromino;
      currentTetromino.setXY(TETROMINO_START_X, TETROMINO_START_Y);
      nextTetromino = randomTetromino();
      nextTetromino.setXY(NEXT_TETROMINO_X, NEXT_TETROMINO_Y);

      checkDelete();

    } else {
      currentTetromino.update();
    }
  }

  public void checkDelete() {
    int x = left_x;
    int y = top_y;
    int blockCount = 0;

    while (x < right_x && y < bottom_y) {
      for (int i = 0; i < staticBlocks.size(); i++) {
        if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
          blockCount++;
        }
      }
      x += Block.SIZE;

      if (x >= right_x) {

        if (blockCount >= 12) {
          for (int i = staticBlocks.size() - 1; i > -1; i--) {
            if (staticBlocks.get(i).y == y) {
              staticBlocks.remove(i);
            }
          }
          for (int i = 0; i < staticBlocks.size(); i++) {
            if (staticBlocks.get(i).y < y) staticBlocks.get(i).y += Block.SIZE;
          }
          score += 10;
        }

        blockCount = 0;
        x = left_x;
        y += Block.SIZE;
      }
    }
  }

  public void draw(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(4f));
    g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

    int paddingX = 15;
    int paddingY = 35;
    int x = right_x + 30;
    int y = bottom_y - 200;
    g2.drawRect(x, y, 200, 204);
    g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.drawString("Next:", x + paddingX, y + paddingY);

    y = top_y;
    g2.setColor(Color.white);
    g2.drawRect(x, y - 4, 200, 375);
    g2.drawString("Score: " + score, x + paddingX, y + paddingY);


    if (currentTetromino != null) {
      currentTetromino.draw(g2);
    }

    if (nextTetromino != null) {
      nextTetromino.draw(g2);
    }

    for (int i = 0; i < staticBlocks.size(); i++) {
      staticBlocks.get(i).draw(g2);
    }

    if (KeyboardHandler.pausePressed) {
      g2.setColor(Color.YELLOW);
      g2.setFont(g2.getFont().deriveFont(50f));
      x = left_x + 70;
      y = top_y + 320;
      g2.drawString("Paused", x, y);
    }
  }

}
