package ua.company.Bart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by B'Art on 05.01.2017.
 */
public class Field extends JPanel implements ActionListener {

    Image currentLevel = new ImageIcon("res/lvl1.png").getImage();

    Player pl = new Player();
    ArrayList<Enemy> enemy_list = new ArrayList<Enemy>();
    Thread Spawn_Enemy_Balls = new Thread(new Enemy(0,0,this));

    Timer time = new Timer(30,this);

    public Field(){
        time.start();
        Spawn_Enemy_Balls.start();
        setFocusable(true);
        addKeyListener(new Key_Event());
    }

    @Override
    public void paint(Graphics grph){
        grph = (Graphics2D) grph;
        grph.drawImage(currentLevel,0,0,null);
        grph.drawImage(pl.player,pl.x,pl.y,null);
        Font font = new Font("Arial",Font.ITALIC,20);
        grph.setFont(font);
        grph.setColor(Color.DARK_GRAY);
        grph.drawString("Количество килов: "+pl.point,30,18);
        Iterator<Enemy> i = enemy_list.iterator();
        Random rnd = new Random();
        while (i.hasNext()){
            Enemy en = i.next();
            switch (rnd.nextInt(3)){
                case 0:en.Enemy_Icon=en.Enemy_Black;;
                case 1:en.Enemy_Icon=en.Enemy_Blue;;
                case 2:en.Enemy_Icon=en.Enemy_Red;;
            }
            grph.drawImage(en.Enemy_Icon,en.x,en.y,null);
        }
    }

    public class Key_Event extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            pl.key_Pressed(e);
        }
    }

    public void check_crash(){
        Iterator<Enemy> e = enemy_list.iterator();
        Enemy en = e.next();
        if(pl.recTangle().intersects(en.recTangle())){
            e.remove();
            pl.point++;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        pl.move();
        check_crash();
        repaint();
    }
}
