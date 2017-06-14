package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by B'Art on 17.01.2017.
 */
public class Player{
    protected static final int Acceleration_Of_Gravity = -5;
    private final int Acceleration_Characters = 9;
    protected int x=400,y=100;
    protected int x_r,y_b;
    protected int d_x=0,d_y=4;
    protected int key_press;
    protected int MagicPoint=0;
    protected int distance = 0;
    protected int Health = 5;

    public Rectangle rectangle_player(){
        x_r = Graphics_Image.player_icon.getWidth(null);
        y_b = Graphics_Image.player_icon.getHeight(null);
        return new Rectangle(x,y,x_r,y_b);
    }

    protected void Player_move(){
        if(x<=0)
            x=1360;
        if(x>1360)
            x=0;
        if(y>=705)
            y=705;
        x+=d_x;
        y+=d_y;
        distance+=d_y;
    }

    public void keyPressed(KeyEvent e) {
        key_press = e.getKeyCode();
        if(key_press==KeyEvent.VK_RIGHT){
            d_x=Acceleration_Characters;
        }
        if(key_press==KeyEvent.VK_LEFT){
            d_x=-Acceleration_Characters;
        }

    }

    public void keyReleased(KeyEvent e) {
         d_x=0;
    }

}