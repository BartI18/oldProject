package ua.company.Bart;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame JF = new JFrame("Fanny Balls");
        Image icon = new ImageIcon("res/icon.png").getImage();
        JF.setSize(800,670);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.setIconImage(icon);
        JF.add(new Field());
        JF.setLocation(300,30);
        JF.setVisible(true);
    }
}