import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String... args) {
        createGUI();
    }

    private static JFrame createGUI() {
        // Prepare Frame and Panel
        JFrame frame = new JFrame("Titlecase Converter App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);

        // Create the Input TextField
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(300, 40));
        input.setVisible(true);
        panel.add(input);

        // Create the Button
        JButton convertButton = new JButton("Convert");
        convertButton.setVisible(true);
        panel.add(convertButton);

        // Crate the Output Label
        JLabel output = new JLabel();
        output.setPreferredSize(new Dimension(300,40));
        output.setVisible(true);
        panel.add(output);

        // By clicking Button, copy TextField's contents to the Label
        convertButton.addActionListener(event -> {
            output.setText(input.getText());
        });

        // Pack contents in Frame and show them
        frame.pack();
        panel.setVisible(true);
        frame.setVisible(true);
        return frame;
    }
}
