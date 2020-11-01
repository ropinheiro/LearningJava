package com.rpinheiro;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String... args) {
        createGUI();
    }

    private static JFrame createGUI() {
        // Prepare Frame and layouy
        JFrame frame = new JFrame("Title Case Converter App");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create the Input TextField
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(300, 40));
        input.setVisible(true);
        frame.add(input);

        // Create the Button
        JButton convertButton = new JButton("Convert");
        convertButton.setVisible(true);
        frame.add(convertButton);

        // Crate the Output Label
        JLabel output = new JLabel();
        output.setPreferredSize(new Dimension(300,40));
        output.setVisible(true);
        frame.add(output);

        // By clicking Button, we convert TextField contents to a TitleCase capitalization.
        // The output will be put in the Label.
        convertButton.addActionListener(event -> {
            output.setText(Converter.ToTitleCase(input.getText()));
        });

        // Pack contents in Frame and show them
        frame.pack();
        frame.setLocationRelativeTo(null); // Center frame in the screen
        frame.setVisible(true);
        return frame;
    }
}