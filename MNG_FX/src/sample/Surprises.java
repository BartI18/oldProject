package sample;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by B'Art on 19.01.2017.
 */
public class Surprises implements Runnable{
    private int randomColor;
    int x=0;
    int y=0;
    int x_b;
    int y_r;
    Level_x lvl;
    Random rnd = new Random();

    Image currentBall = Graphics_Image.yellowBall;

    public Surprises(int x,int y, Level_x lvl){
        this.x = x;
        this.y = y;
        this.lvl = lvl;
        randomColor = rnd.nextInt(4);
        switch (randomColor){
            case 0:   {currentBall=Graphics_Image.yellowBall; break;}
            case 1:   {currentBall=Graphics_Image.greenBall; break;}
            case 2:   {currentBall=Graphics_Image.blackBall; break;}
            case 3:   {currentBall=Graphics_Image.redBall; break;}
        }


    }

    public void move_ball(){
        y+=Player.Acceleration_Of_Gravity;
    }

    public Rectangle rectangle_ball(){
        x_b=currentBall.getWidth(null);
        y_r=currentBall.getHeight(null);
        return new Rectangle(x,y,x_b,y_r);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500 + rnd.nextInt(400));
                lvl.listballs.add(new Surprises(70+rnd.nextInt(1100), 900, lvl));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
