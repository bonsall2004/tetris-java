package tetromino;

import uk.bonsall2004.KeyboardHandler;
import uk.bonsall2004.PlayManager;

import java.awt.*;

public class Tetromino {
  public Block[] b = new Block[4];
  public Block[] tempB = new Block[4];
  public int direction = 1; // (1/2/3/4)
  public boolean active = true;
  public boolean deactivating = false;
  int autoDropCounter = 0;
  boolean leftCollision, rightCollision, bottomCollision;
  boolean rotCollision;
  private int deactivationCounter = 0;

  public void getDirection1() {
  }

  public void getDirection2() {
  }

  public void getDirection3() {
  }

  public void getDirection4() {
  }

  public void create(Color col) {
    for (int i = 0; i < 4; i++) {
      b[i] = new Block(col);
      tempB[i] = new Block(col);
    }
  }

  public void checkMovementCollision() {
    leftCollision = false;
    rightCollision = false;
    bottomCollision = false;
    checkStaticBlockCollision();

    for (int i = 0; i < b.length; i++) {
      if (b[i].x <= PlayManager.left_x) leftCollision = true;
      if (b[i].x + Block.SIZE >= PlayManager.right_x) rightCollision = true;
      if (b[i].y + Block.SIZE >= PlayManager.bottom_y) bottomCollision = true;
    }
  }

  public void checkRotationCollision() {
    rotCollision = false;
    checkStaticBlockCollision();

    for (int i = 0; i < tempB.length; i++) {
      if (tempB[i].x <= PlayManager.left_x) rotCollision = true;
      if (tempB[i].x + Block.SIZE >= PlayManager.right_x) rotCollision = true;
      if (tempB[i].y + Block.SIZE >= PlayManager.bottom_y) rotCollision = true;
    }
  }

  public void checkStaticBlockCollision() {
    for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
      int targetX = PlayManager.staticBlocks.get(i).x;
      int targetY = PlayManager.staticBlocks.get(i).y;

      for (int j = 0; j < b.length; j++) {
        if (b[j].y + Block.SIZE == targetY && b[j].x == targetX) bottomCollision = true;
        if (b[j].x - Block.SIZE == targetX && b[j].y == targetY) leftCollision = true;
        if (b[j].x + Block.SIZE == targetX && b[j].y == targetY) rightCollision = true;
      }
    }
  }

  public void setXY(int x, int y) {

  }

  public void updateXY(int direction) {
    checkRotationCollision();
    if (!rotCollision && !bottomCollision) {
      this.direction = direction;
      b = tempB;
    }
  }

  private void deactivate() {
    deactivationCounter++;
    if (deactivationCounter >= 25) {
      deactivationCounter = 0;
      checkMovementCollision();

      if (bottomCollision) {
        deactivating = false;
        active = false;
      }
    }
  }

  public void update() {
    if (deactivating) deactivate();
    if (KeyboardHandler.UpPressed && active) {
      switch (direction) {
        case 1:
          getDirection2();
          break;
        case 2:
          getDirection3();
          break;
        case 3:
          getDirection4();
          break;
        case 4:
          getDirection1();
          break;
      }
      KeyboardHandler.UpPressed = false;
    }

    checkMovementCollision();
    if (KeyboardHandler.DownPressed && active) {
      if (deactivating) active = false;
      if (!bottomCollision) {
        for (int i = 0; i < 4; i++) {
          b[i].y += Block.SIZE;
        }
        autoDropCounter = 0;
        KeyboardHandler.DownPressed = false;
      }
    }

    if (KeyboardHandler.LeftPressed && active) {
      if (!leftCollision) {
        for (int i = 0; i < 4; i++) {
          b[i].x -= Block.SIZE;
        }
        KeyboardHandler.LeftPressed = false;
      }
    }

    if (KeyboardHandler.RightPressed && active) {
      if (!rightCollision) {
        for (int i = 0; i < 4; i++) {
          b[i].x += Block.SIZE;
        }
        KeyboardHandler.RightPressed = false;
      }
    }
    if (!bottomCollision) {
      autoDropCounter++;
      if (autoDropCounter == PlayManager.dropInterval) {
        for (int i = 0; i < 4; i++) {
          b[i].y += Block.SIZE;
        }
        autoDropCounter = 0;
      }
    } else {
      deactivating = true;
    }
  }

  public void draw(Graphics2D g2) {
    g2.setColor(b[0].color);
    int margin = 1;
    for (int i = 0; i < 4; i++) {
      g2.fillRect(b[i].x + margin, b[i].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
    }

  }
}
