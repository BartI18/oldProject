package ua.company.Bart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by B'Art on 05.01.2017.
 */
public class Enemy implements Runnable{
    int x,y;
    Field field;
    Image Enemy_Black = new ImageIcon("res/enemy/enemyBlack.png").getImage();
    Image Enemy_Blue = new ImageIcon("res/enemy/enemyBlue.png").getImage();
    Image Enemy_Red = new ImageIcon("res/enemy/enemyRed.png").getImage();
    Image Enemy_Icon = Enemy_Black;
    int x_r = Enemy_Icon.getWidth(null);
    int y_b = Enemy_Icon.getHeight(null);

    public Enemy(int x,int y,Field field){
        this.x = x;
        this.y = y;
        this.field = field;
    }

    public Rectangle recTangle(){
        return new Rectangle(x,y,x_r,y_b);
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            Iterator<Enemy> e = field.enemy_list.iterator();
            if(e.hasNext()==false) {
                field.enemy_list.add(new Enemy(40+(rnd.nextInt(680)), 40+rnd.nextInt(520), field));
            }
        }
    }
}