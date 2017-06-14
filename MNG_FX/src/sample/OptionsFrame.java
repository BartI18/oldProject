package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by B'Art on 18/02/2017.
 */
public class OptionsFrame extends JFrame{

    JButton changeName = new JButton("Change Name");
    JButton changeIconProfile = new JButton("Change Icon");
    File playerInfo = new File("C:/Users/B'Art/Desktop/MNG_resource/player.txt");
    PrintWriter printWriter;

    BufferedReader bufferedReader;

    //This is data in file about player(name, distance,point)
    String name;
    int distance;
    int point;
    String pathInImageProfile;
    private JFileChooser jFileChooser1;

    public OptionsFrame(){
        super("Options");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(450,250);
        setResizable(false);
        setLocation(450,250);
        setVisible(true);
        setLayout(new BorderLayout());

        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readInfoFile();
                name =  JOptionPane.showInputDialog(null,"Введите ваше имя");
                writeInFile(name);
            }
        });

        changeIconProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int j = jFileChooser1.showOpenDialog(null);
                    if(j==jFileChooser1.APPROVE_OPTION) {
                        pathInImageProfile = String.valueOf(jFileChooser1.getSelectedFile().getAbsoluteFile());
                        writeInFile(name);
                    }
            }
        });

        add(changeName,BorderLayout.CENTER);
        add(changeIconProfile,BorderLayout.NORTH);
    }

    //Write in file information (name player)

    private void writeInFile(String nameW){
        try {
            if(nameW!=null) {
                printWriter = new PrintWriter(playerInfo);
                printWriter.println(nameW);
                printWriter.println(distance);
                printWriter.println(point);
                printWriter.print(pathInImageProfile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }
    }


    private void readInfoFile(){
        String temp;
        try {
            bufferedReader = new BufferedReader(new FileReader(playerInfo));
            if(playerInfo.exists()){
                temp = bufferedReader.readLine();
                name = temp;
                temp = bufferedReader.readLine();
                distance = Integer.parseInt((temp));
                temp = bufferedReader.readLine();
                point = Integer.parseInt((temp));
                temp = bufferedReader.readLine();
                pathInImageProfile = temp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
