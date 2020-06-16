import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class Gui {
  private String input;
  private int deltaY;

  public Gui() {
    this.input = "";
    this.deltaY = 0;
  }

  public void createGUI() {
    JFrame frame = new JFrame("Calculator");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    JLabel label = new JLabel("");
    label.setBounds(0, 8, 210, 30);
    label.setVisible(true);
    label.setOpaque(true);
    label.setBackground(Color.PINK);
    frame.getContentPane().add(label);

    char[][] buttonText1 = {
      {'7', '8', '9'},
      {'4', '5', '6'},
      {'1', '2', '3'}
    };
    JButton button1;
    for (int i = 0; i < 3; i++) {
      deltaY = 50 * (i + 1);
      for (int j = 0; j < 3; j++) {
        button1 = new JButton(String.valueOf(buttonText1[i][j]));
        button1.setBounds(50 * j, 50 * j + deltaY, 50, 50);
        deltaY -= 50;
        button1.setFocusable(false);
        addActionsToButtons(button1, label);
        frame.getContentPane().add(button1);
      }
    }

    char[][] buttonText2 = {{'<', '=', '/', '*'}};
    JButton button2;
    for (int i = 1; i < 5; i++) {
      button2 = new JButton(String.valueOf(buttonText2[0][i - 1]));
      button2.setBounds(150, 50 * i, 50, 50);
      button2.setFocusable(false);
      addActionsToButtons(button2, label);
      frame.getContentPane().add(button2);
    }

    char[][] buttonText3 = {{'0', '+', '-'}};
    JButton button3;
    for (int i = 0; i < 3; i++) {
      button3 = new JButton(String.valueOf(buttonText3[0][i]));
      button3.setBounds(50 * i, 200, 50, 50);
      button3.setFocusable(false);
      addActionsToButtons(button3, label);
      frame.getContentPane().add(button3);
    }
    // button of random color for all button on the pane
    JButton button4 = new JButton("Push me");
    button4.setBounds(50, 250, 100, 50);
    button4.setOpaque(true);
    button4.setFocusable(false);
    addActionListener(frame, button4);
    frame.getContentPane().add(button4);

    frame.setPreferredSize(new Dimension(210, 335));
    frame.pack();
    frame.setVisible(true);
  }

  private void addActionListener(JFrame frame, JButton button) {
    button.addActionListener(e -> {
      Component[] components = frame.getContentPane().getComponents();
      for (Component component : components) {
        if (component instanceof JButton) {
          MyThread thread = new MyThread((JButton) component);
          thread.start();
        }
      }
    });
  }

  private void addActionsToButtons(JButton button, JLabel label) {
    JButton finalButton = button;
    button.addActionListener(e -> {
      if (input.length() > 0 && button.getText().equals("<")) {
        input = input.substring(0, input.length() - 1);
        label.setText(input);
        return;
      } else if (input.length() == 0 && button.getText().equals("<")) {
        return;
      }

      if (button.getText().equals("=")) {
        Calculator calculator = new Calculator(input);
        BigInteger res = calculator.calc();
        if (calculator.getCodeException() != 0) {
          label.setText("Error");
          return;
        }
        label.setText(String.valueOf(res));
      } else if (label.getText().isEmpty()) {
        label.setText(finalButton.getText());
        input = finalButton.getText();
      } else {
        input = label.getText().concat(finalButton.getText());
        label.setText(input);
      }

    });
  }
}
