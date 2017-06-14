package sample;

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


/**
 * Created by B'Art on 06/03/2017.
 */


public class Menu extends Application {

    private Button B_newGame;
    private Button B_options;
    private Button B_continue;
    private Button B_exit;
    private Text T_coins;

    private Label background;
    private Group root;

   // public static void main(String[] args) {
  //      Application.launch(args);
   // }

    public Menu(){
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        Scene scene = new Scene(root, 1366, 768, Color.WHITE);
        background = new Label();
        background.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("back.png"))));


        B_newGame = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("newgame.png"))));
        B_continue = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("continue.png"))));
        B_options = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("options.png"))));
        B_exit = new Button(null,new ImageView(new Image(getClass().getResourceAsStream("exit.png"))));

        T_coins = new Text(56,43,"456abh");
        T_coins.setFont(new Font("Arial",30));
        T_coins.setStroke(Color.BROWN);

        setButtonLocations();

        B_exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
                //System.exit(228);
            }
        });

        root.getChildren().add(background);
        root.getChildren().add(B_newGame);
        root.getChildren().add(B_continue);
        root.getChildren().add(B_options);
        root.getChildren().add(B_exit);
        root.getChildren().add(T_coins);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void setButtonLocations(){
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

}
