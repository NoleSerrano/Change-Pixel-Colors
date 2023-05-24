import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorSample {

  public static void main(String args[]) {
    JFrame frame = new JFrame("JColorChooser Popup");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container contentPane = frame.getContentPane();

    final JLabel label = new JLabel("I Love Swing", JLabel.CENTER);
    label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
    contentPane.add(label, BorderLayout.SOUTH);

    final JColorChooser colorChooser = new JColorChooser(label
        .getBackground());
    colorChooser.setBorder(BorderFactory
        .createTitledBorder("Pick Foreground Color"));

    ColorSelectionModel model = colorChooser.getSelectionModel();
    ChangeListener changeListener = new ChangeListener() {
      public void stateChanged(ChangeEvent changeEvent) {
        Color newForegroundColor = colorChooser.getColor();
        label.setForeground(newForegroundColor);
      }
    };
    model.addChangeListener(changeListener);
    contentPane.add(colorChooser, BorderLayout.CENTER);

    frame.pack();
    frame.setVisible(true);
  }
}

           
         
    