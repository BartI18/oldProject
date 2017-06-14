package sample;


        //https://oauth.vk.com/authorize?client_id=5909565&display=page&redirect_uri=
        // https://oauth.vk.com/blank.html&scope=friends&response_type=token&v=5.62&state=123456
        //мой токен для вк)

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {

    private Button B_newGame;
    private Button B_options;
    private Button B_continue;
    private Button B_exit;
    private Text T_name;

    private Label background;
    private Group root;


    //Special data in profile
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    File playerInfo = new File("C:/Users/B'Art/Desktop/MNG_resource/player.txt");
    private String namePlayer;
    private int distance;
    private Label coins;
    private String pathIconPlayer;
    private File F_iconPlayer;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        Scene scene = new Scene(root, 1366, 768, Color.WHITE);
        background = new Label();
        background.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("back.png"))));
        T_name = new Text(40,73,"");
        coins = new Label();

        readInFile();

        T_name.setText("Hi "+namePlayer);
        T_name.setFont(new Font("Arial",50));
        T_name.setStroke(Color.AQUAMARINE);
        T_name.setFill(Color.TOMATO);

       //
       // T_coins.setFont(new Font("Arial",30));
       // T_coins.setStroke(Color.BROWN);

        setButtonLocations();

        B_newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              new Level_x();
              //primaryStage.close();
            }
        });

        B_exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               // primaryStage.close();
                System.exit(228);
            }
        });

        root.getChildren().add(background);
        root.getChildren().add(B_newGame);
        root.getChildren().add(B_continue);
        root.getChildren().add(B_options);
        root.getChildren().add(B_exit);
        root.getChildren().add(T_name);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void setButtonLocations(){
        B_newGame = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("newgame.png"))));
        B_continue = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("continue.png"))));
        B_options = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("options.png"))));
        B_exit = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("exit.png"))));

        B_newGame.setLayoutX(945);
        B_newGame.setLayoutY(360);
        B_newGame.setMinSize(400,99);
        B_newGame.setMaxSize(400,99);
        B_newGame.setStyle("-fx-background-radius: 2em;");

        B_continue.setLayoutX(945);
        B_continue.setLayoutY(480);
        B_continue.setMinSize(400,99);
        B_continue.setMaxSize(400,99);
        B_continue.setStyle("-fx-background-radius: 2em;");

        B_options.setLayoutX(945);
        B_options.setLayoutY(600);
        B_options.setMinSize(400,99);
        B_options.setMaxSize(400,99);
        B_options.setStyle("-fx-background-radius: 2em;");

        B_exit.setLayoutX(945);
        B_exit.setLayoutY(15);
        B_exit.setMinSize(400,99);
        B_exit.setMaxSize(400,99);
        B_exit.setStyle("-fx-background-radius: 2em;");


    }

    void readInFile(){
        try {
            if(playerInfo.exists()){
                bufferedReader = new BufferedReader(new FileReader(playerInfo));
                namePlayer = bufferedReader.readLine();
                distance = Integer.parseInt(bufferedReader.readLine());
                coins.setText(bufferedReader.readLine());
                if(coins.getText()==null){
                    coins.setText("0");
                }
                pathIconPlayer = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!playerInfo.exists()) {
            namePlayer = "Unnamed";
            distance = 0;
            coins.setText("0");
            pathIconPlayer = null;
        }
    }

    void writeInFile(){
        try {
            printWriter = new PrintWriter(playerInfo);
            printWriter.println(namePlayer);
            printWriter.println(distance);
            printWriter.println(coins.getText());
            printWriter.print(pathIconPlayer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }
    }

}