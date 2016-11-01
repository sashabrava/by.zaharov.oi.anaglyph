package by.zaharov.oi.anaglyph;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

class MainFrame {
    private final Anaglyph anaglyph = new Anaglyph();
    private final JFrame frame = new JFrame("Building anaglyph image");
    private final JPanel panelCentral = new JPanel();
    private String leftName = null;
    private String rightName = null;

    MainFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame.setLayout(new BorderLayout());
        frame.add(panelCentral, BorderLayout.CENTER);
        JPanel panelNorth = new JPanel();
        frame.add(panelNorth, BorderLayout.NORTH);
        JButton upLeft = new JButton("Upload left image");
        JButton upRight = new JButton("Upload right image");
        JButton buildImage = new JButton("Build image");

        upLeft.addActionListener(arg0 -> {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.setCurrentDirectory(new File("D:/"));
            int ret = fileOpen.showDialog(null, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                leftName = file.getPath();
                upLeft.setBackground(Color.GREEN);
                // TODO Auto-generated method stub

            }

        });
        upRight.addActionListener(e -> {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.setCurrentDirectory(new File("D:/"));
            int ret = fileOpen.showDialog(null, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                rightName = file.getPath();
                upRight.setBackground(Color.GREEN);
                // TODO Auto-generated method stub

            }
        });

        buildImage.addActionListener(arg0 -> {
            if (leftName != null && rightName != null) {
                BufferedImage image = anaglyph.buildImage(leftName, rightName);
                if (image != null) {
                    ImageIcon icon = new ImageIcon(image);
                    JLabel label = new JLabel(icon);
                    panelCentral.removeAll();
                    panelCentral.add(label);
                    frame.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
                    frame.pack();
                    buildImage.setBackground(Color.GREEN);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Left and right images have different size", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    buildImage.setBackground(Color.RED);
                }
            } else {
                JOptionPane.showMessageDialog(null, "At least one of the images is undefined", "Error",
                        JOptionPane.ERROR_MESSAGE);
                buildImage.setBackground(Color.RED);
            }

        });
        panelNorth.add(upLeft);
        panelNorth.add(upRight);
        panelNorth.add(buildImage);
        frame.setVisible(true);
        frame.pack();
    }
}
