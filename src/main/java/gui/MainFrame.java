package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Collision Detection");
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        Image spike = new ImageIcon("src/main/resources/4293815.png").getImage();
        Image ball = new ImageIcon("src/main/resources/ball.png").getImage();
        this.setSize(spike.getWidth(null) + 100, spike.getHeight(null) + 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        TriangulationTestPanel contents = new TriangulationTestPanel(ball);

        this.add(contents);

        this.setVisible(true);
    }


}
