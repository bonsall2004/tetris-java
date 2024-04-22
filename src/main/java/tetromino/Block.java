package tetromino;

import java.awt.*;

public class Block extends Rectangle {

  public static final int SIZE = 30;
  public int x, y;
  public Color color;

  public Block(Color col) {
    this.color = col;
  }

  public void draw(Graphics2D g2) {
    g2.setColor(color);
    g2.fillRect(x + 1, y + 1, SIZE - 2, SIZE - 2);

  }

}
