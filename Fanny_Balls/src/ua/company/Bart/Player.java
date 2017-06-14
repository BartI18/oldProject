package ua.company.Bart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * Created by B'Art on 05.01.2017.
 */
public class Player {

    Image player = new ImageIcon("res/player.png").getImage();
    //наши координаты
    int x=35,y=35;
    //наша скорость
    int X_speed = 3;
    int Y_speed = 0;
    int x_r = player.getWidth(null);
    int y_b = player.getHeight(null);
    int point = 0;

    public Rectangle recTangle(){
        return new Rectangle(x,y,x_r,y_b);
    }

    public void move(){
        x+=X_speed;
        y+=Y_speed;
     }


    public void key_Pressed(KeyEvent e) {
        int press_key = e.getKeyCode();
        if(press_key==KeyEvent.VK_D)
        {
            X_speed = 7;
            Y_speed = 0;
        }
        if(press_key==KeyEvent.VK_A)
        {
            X_speed = -7;
            Y_speed = 0;
        }
        if(press_key==KeyEvent.VK_W)
        {
            Y_speed = -7;
            X_speed = 0;
        }
        if(press_key==KeyEvent.VK_S)
        {
            Y_speed = 7;
            X_speed = 0;
        }
    }

}