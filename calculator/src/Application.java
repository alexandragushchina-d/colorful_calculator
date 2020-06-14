import javax.swing.*;

public class Application {

  public static void main(String[] args) {
    Gui gui = new Gui();
    JFrame.setDefaultLookAndFeelDecorated(true);
    javax.swing.SwingUtilities.invokeLater(() -> gui.createGUI());
  }
}
