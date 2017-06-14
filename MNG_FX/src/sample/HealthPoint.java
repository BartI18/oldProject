package sample;

import java.awt.*;
import java.util.Random;

/**
 * Created by B'Art on 21/02/2017.
 */
public class HealthPoint implements Runnable{

    //coordinations hp
    int x = 0;
    int y = 0;
    int x_r;
    int y_b;

    Level_x lvl;
    Random rnd = new Random();

    Image hp = Graphics_Image.player_hp;

    public HealthPoint(int x,int y,Level_x level_x){
        this.x = x;
        this.y = y;
        lvl = level_x;
    }

    public Rectangle rectangle(){
        x_r = hp.getWidth(null);
        y_b = hp.getHeight(null);
        return new Rectangle(x,y,x_r,y_b);
    }

    public void move(){
        y += Player.Acceleration_Of_Gravity;
    }

    @Override
    public void run() {
        while (true)
        try {
            Thread.sleep(5000+rnd.nextInt(5000));
            lvl.list_hp.add(new HealthPoint(10+rnd.nextInt(1050), 1050, lvl));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
