package sample;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by B'Art on 19.01.2017.
 */
public class Panel implements Runnable{

    //координаты панелек
    int x=0;
    int y=0;
    int x_b;
    int y_r;

    private int randomColor = 0;

    Level_x lvl;
    Random rnd = new Random();

    Image currentPanel = Graphics_Image.panelYellow;


    public Panel(int x,int y,Level_x level){
        this.x = x;
        this.y = y;
        lvl = level;
        randomColor = rnd.nextInt(2);
        if(randomColor==1)
            currentPanel=Graphics_Image.panelGreen;
        else currentPanel=Graphics_Image.panelYellow;
    }

    public void move_panel(){
        y+=Player.Acceleration_Of_Gravity;
    }

    public Rectangle rectangle_panel(){
        x_b=currentPanel.getWidth(null);
        y_r=currentPanel.getHeight(null);
        return new Rectangle(x,y,x_b,y_r);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500 + rnd.nextInt(200));
                lvl.listpanel.add(new Panel(10+rnd.nextInt(1050), 1050, lvl));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}