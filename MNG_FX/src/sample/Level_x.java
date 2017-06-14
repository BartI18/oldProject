package sample;

import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by B'Art on 17.01.2017.
 */
public class Level_x extends JPanel implements ActionListener {

    JFrame JF = new JFrame();

    //координаты нашего поля
    int y_first = 0;
    int y_second = 768;

    //поток для создание панелек
    Thread Spawn_panel = new Thread(new Panel(100,1200,this));
    Thread Spawn_balls = new Thread(new Surprises(100,1200,this));
    Thread Spawn_health = new Thread(new HealthPoint(100,1200,this));

    //массив панелек
    ArrayList<Panel> listpanel = new ArrayList<Panel>();
    ArrayList<Surprises> listballs = new ArrayList<Surprises>();
    ArrayList<HealthPoint> list_hp = new ArrayList<>();


    //таймер для обновления кадров
    Timer main_timer = new Timer(15, this);

    Player player = new Player();

    PrintWriter printWriter;
    BufferedReader bufferedReader;

    String name;
    String pathIconPlayer;
    int lastdistance;
    int coins;

    File playerInfo = new File("C:/Users/B'Art/Desktop/MNG_resource/player.txt");   //"C:/Users/B'Art/Desktop/MNG_resource/player.txt"



    public Level_x() {

        JF.setSize(1366,768);
        JF.add(this);
        JF.setUndecorated(true); //убирает границы экрана, делает полноэкранный режим окна
        JF.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setFocusable(true);
        JF.setVisible(true);

        main_timer.start();
        Spawn_panel.start();
        Spawn_balls.start();
        Spawn_health.start();

        addKeyListener(new Key_Check());
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(Graphics_Image.level_1, 0, y_first, null);
        g.drawImage(Graphics_Image.level_1,0,y_second,null);
        g.drawImage(Graphics_Image.danger,0,0,null);
        g.drawImage(Graphics_Image.player_icon, player.x, player.y, null);
        g.drawImage(Graphics_Image.yellowBall,35,100,null);

       for(int i=1;i<=player.Health;i++)
           g.drawImage(Graphics_Image.pointLine,0,700,i*25,720,0,0,i*25,17,null);
            
        Iterator<Panel> iter_panel = listpanel.iterator();
        while (iter_panel.hasNext()) {
            Panel pn = iter_panel.next();
            if (pn.y <= -700)
                iter_panel.remove();
            else {
                g.drawImage(pn.currentPanel, pn.x, pn.y, null);
                if(player.rectangle_player().intersects(pn.rectangle_panel())) {
                    player.d_y=4;
                    player.y = pn.y - 66;
                }
                pn.move_panel();
            }
        }

        Iterator<Surprises> iter_surp = listballs.iterator();
        while (iter_surp.hasNext()) {
            Surprises surprise_ball = iter_surp.next();
            if (surprise_ball.y <= -1050)
                iter_surp.remove();
            else {
                g.drawImage(surprise_ball.currentBall, surprise_ball.x, surprise_ball.y, null);
                surprise_ball.move_ball();
            }
            if(surprise_ball.rectangle_ball().intersects(player.rectangle_player())){
                player.MagicPoint++;
                iter_surp.remove();
            }
        }

        Iterator<HealthPoint> iter_hp = list_hp.iterator();
        while (iter_hp.hasNext()) {
            HealthPoint healthPoint = iter_hp.next();
            if (healthPoint.y <= -1050)
                iter_hp.remove();
            else {
                g.drawImage(healthPoint.hp, healthPoint.x, healthPoint.y, null);
                healthPoint.move();
                if(player.rectangle_player().intersects(healthPoint.rectangle())) {
                    if(player.Health<5)
                    player.Health++;
                    iter_hp.remove();
                }
            }
        }

        g.setFont(new Font("Arial",Font.BOLD,14));
        g.setColor(Color.ORANGE);
        g.drawString("Distance: "+player.distance,1200,720);
        g.setFont(new Font("Arial",Font.BOLD,36));
        g.drawString(String.valueOf(player.MagicPoint),90,135);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(y_second>=0){
            y_first+=player.Acceleration_Of_Gravity;
            y_second+=player.Acceleration_Of_Gravity;
        }else{
            y_first=0;
            y_second=768;
        }

        player.Player_move();

        if(player.y<=65) {
            JOptionPane.showMessageDialog(null, "You try :)", "Game over", 1);
            readInfoFile();
            coins = coins + (int) Math.log(player.distance)*player.MagicPoint;
            writeInFile();
            if(player.distance>lastdistance)
                JOptionPane.showMessageDialog(null,"Congratulations! You set a new record !!!");
            JF.setVisible(false);
        }

        if(player.y>702) {
            player.Health--;
            player.y = 300;
        }
        //fightPlayerWithPanel();
       // fightPlayerWithBalls();

        repaint();
    }

    ///////////////////////////////////////
    ////// столконовение с панелями //////
    /////////////////////////////////////
    private void fightPlayerWithPanel(){
       Iterator<Panel> i = listpanel.iterator();
       while (i.hasNext()){
           Panel pa = i.next();
           if(pa.rectangle_panel().intersects(player.rectangle_player())){
               player.y = pa.y - 66;
           }
       }
    }


    private void fightPlayerWithBalls(){
        Iterator<Surprises> surprisesIterator = listballs.iterator();
          while (surprisesIterator.hasNext()){
              Surprises surprises = surprisesIterator.next();
              if(surprises.rectangle_ball().intersects(player.rectangle_player())){
                    player.MagicPoint++;
                    surprisesIterator.remove();
              }
          }
    }


    private void readInfoFile(){
        try {
            if(playerInfo.exists()){
            bufferedReader = new BufferedReader(new FileReader(playerInfo));
                name = bufferedReader.readLine();
                lastdistance = Integer.parseInt(bufferedReader.readLine());
                coins = Integer.parseInt(bufferedReader.readLine());
                pathIconPlayer = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeInFile(){
        try {
            if(!playerInfo.exists())

                playerInfo.createNewFile();
            printWriter = new PrintWriter(playerInfo);
            printWriter.println(name);

            //Check if player distance biggest then last distance
            if(player.distance>lastdistance)
                printWriter.println(player.distance);
            else
                printWriter.println(lastdistance);

                printWriter.println(coins);
                printWriter.println(pathIconPlayer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }


    private class Key_Check extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                main_timer.stop();
                JF.setVisible(false);
            }
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}
