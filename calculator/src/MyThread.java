import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class MyThread extends Thread {
  private JButton button;

  private int speedR = 1;
  private int speedG = 1;
  private int speedB = 1;

  private int accR = 2;
  private int accG = 2;
  private int accB = 2;

  public MyThread(JButton button) {
    this.button = button;
  }

  @Override
  public void run() {
    int startR = 0;
    int startG = 0;
    int startB = 0;

    int finalR = ThreadLocalRandom.current().nextInt(0, 256);
    int finalG = ThreadLocalRandom.current().nextInt(0, 256);
    int finalB = ThreadLocalRandom.current().nextInt(0, 256);

    speedR = (finalR - startR) / 10;
    accR = speedR / 10;
    speedG = (finalG - startG) / 10;
    accG = speedG / 10;
    speedB = (finalB - startB) / 10;
    accB = speedB / 10;

    while (!isStopped(startR, finalR) || !isStopped(startG, finalG)
      || !isStopped(startB, finalB)) {

      if (!isStopped(startR, finalR)) {
        speedR += accR;
        startR += speedR;
      }
      if (!isStopped(startG, finalG)) {
        speedG += accG;
        startG += speedG;
      }
      if (!isStopped(startB, finalB)) {
        speedB += accB;
        startB += speedB;
      }
      try {
        button.setBackground(new Color(startR, startG, startB));
        button.setForeground(new Color(255 - startR, 255 - startG, 255 - startB));
      } catch (IllegalArgumentException e) {
        break;
      }
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean isStopped(int startValue, int finalValue) {
    if (Math.abs(finalValue - startValue) < 3) {
      return true;
    }
    return false;
  }
}