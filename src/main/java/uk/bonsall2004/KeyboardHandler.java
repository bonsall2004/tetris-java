package uk.bonsall2004;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

  public static boolean UpPressed, DownPressed, LeftPressed, RightPressed, pausePressed;

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W) {
      UpPressed = true;
    }
    if (code == KeyEvent.VK_A) {
      LeftPressed = true;
    }
    if (code == KeyEvent.VK_S) {
      DownPressed = true;
    }
    if (code == KeyEvent.VK_D) {
      RightPressed = true;
    }
    if (code == KeyEvent.VK_P || code == KeyEvent.VK_SPACE) {
      pausePressed = !pausePressed;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }
}
