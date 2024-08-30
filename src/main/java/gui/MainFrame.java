package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Collision Detection");
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        Image img = new ImageIcon("src/main/resources/4293815.png").getImage();
        this.setSize(img.getWidth(null) + 100, img.getHeight(null) + 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        RayDetectionTestPanel contents = new RayDetectionTestPanel(img);

        this.add(contents);

        this.setVisible(true);
    }


}
