package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application {

    private String adressTextFull = "";
    String tokenVK = "";
    String IDVK = "";
    private int countNotes = 7;
    private File config = new File("C:/Users/Public/NotesVK/config.txt");
    private Group root;
    Stage OurMainStage;
    Scene MainScene;

    ArrayList<Button> buttonsOpenBeta = new ArrayList<>();
    ArrayList<Button> buttonsEditBeta = new ArrayList<>();
    ArrayList<Button> buttonsDeleteBeta = new ArrayList<>();
    Label[] textsNotes;


    String[] ID_Code;           //идентификатор заметкок
    String[] Title;             //заголовки заметок
    String[] AdressOurNotes;    //URL адреса заметок

    Group welcomeGroup = new Group();
    Scene welcomeScene = new Scene(welcomeGroup,240,240, Color.AZURE);


    ScrollBar scrollBar = new ScrollBar();
    MenuBar menuBar = new MenuBar();
    Menu mainMenu = new Menu("Menu");
    MenuItem itemAdd = new MenuItem("Add");
    MenuItem itemSetting = new MenuItem("Setting");
    MenuItem itemLog_out = new MenuItem("Logout");


    @Override
    public void start(Stage primaryStage) throws Exception{

        primarySetting(primaryStage);        //первоначальные необходимые настройки

        getInformationFromFile();           //считывание данных из файла

        primaryStage.show();


    }

    //Первоначальные настройки
    protected void primarySetting(Stage primaryStage){
        OurMainStage = primaryStage;
        root = new Group();
        MainScene = new Scene(root, 240, 240,Color.AZURE);

        scrollBar.setLayoutX(226);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefSize(10,240);

        menuBar.setPrefWidth(226);
        menuBar.getMenus().addAll(mainMenu);

        primaryStage.setTitle("Notes");


        //primaryStage.setResizable(false);

    }


    //Инициализация всех полей после считывания данных из файла либо вход через VK
    protected void initAndShowNotes() throws IOException {
        getCount();

        setID_CodeAndTitle(countNotes);     //Получение ID заметок и их заголовки

        setNoteInField(countNotes);

        OpenNotes(countNotes);

        EditNotes(countNotes);

        DeleteNotes(countNotes);

        AddNotes();

        root.getChildren().add(scrollBar);
        root.getChildren().add(menuBar);
        root.getChildren().addAll(textsNotes);
        root.getChildren().addAll(buttonsOpenBeta);
        root.getChildren().addAll(buttonsEditBeta);
        root.getChildren().addAll(buttonsDeleteBeta);

    }


    //Получение данных из файла или первоначальная заставка
    protected void getInformationFromFile() throws IOException {
        File folder = new File("C:/Users/Public/NotesVK/");
        folder.mkdir();
        if(!config.exists()){
            setWelcomeParam();
        } else {
            readFileConfig();
            initAndShowNotes();

            OurMainStage.setScene(MainScene);
        }

    }

    //метод для чтения данных из файла
    protected void readFileConfig() throws IOException{

        BufferedReader readerConfig = new BufferedReader(new FileReader(config));
        String s = readerConfig.readLine();
        tokenVK = s;
        while (s!=null){
            s=readerConfig.readLine();
            if(s!=null){
                IDVK=s;
            }
        }
    }

    //запись данных в файл
    protected void  printFileConfig() throws IOException {
        PrintWriter writerDataIntoFile = new PrintWriter(new FileWriter(config),true);
        if(!config.exists())
            config.createNewFile();
        writerDataIntoFile.println(tokenVK);
        writerDataIntoFile.write(IDVK);
        writerDataIntoFile.close();
    }

    //Получение числа заметок
    protected void getCount() throws IOException {
        URL url = new URL("https://api.vk.com/method/notes.get.xml?user_id="+IDVK+"&access_token=" +
                tokenVK+"&v=5.63");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

        adressTextFull="";
        String s = bufferedReader.readLine();
        while (s!=null){
            s=bufferedReader.readLine();
            if(s!=null){
                adressTextFull+=s;
            }
        }

        int endCount = adressTextFull.indexOf("</count>");
        countNotes= Integer.parseInt(adressTextFull.substring(18,endCount));
    }

    protected void setID_CodeAndTitle(int count){
        String temp = adressTextFull;
        ID_Code = new String[count];
        Title = new String[count];
        AdressOurNotes = new String[count];
        for (int i = 0; i < count; i++) {
            ID_Code[i] = "";
            Title[i]="";
            int beginNumber = 0;
            int endNumber = 0;
            beginNumber = temp.indexOf("<id>");
            endNumber = temp.indexOf("</id>");
            ID_Code[i]=temp.substring(beginNumber+4,endNumber);
            beginNumber = temp.indexOf("<title>");
            endNumber = temp.indexOf("</title>");
            Title[i]=temp.substring(beginNumber+7,endNumber);
            beginNumber = temp.indexOf("<view_url>");
            endNumber = temp.indexOf("</view_url>");
            AdressOurNotes[i] = temp.substring(beginNumber+10,endNumber);
            temp=temp.substring(endNumber+11);
        }
    }

    //установление заметок на экран
    protected void setNoteInField(int count){
        //Инициализация массивов объектов
        Button tempButton;
        textsNotes=new Label[count];

        scrollBar.setValue(0.0);



        //Инициализация полей
        for (int i = 0; i < count; i++) {
            textsNotes[i]= new Label(Title[i]);
            textsNotes[i].setLayoutX(50);
            textsNotes[i].setLayoutY(100*i+30);
            textsNotes[i].setPrefSize(150,30);
            textsNotes[i].setTextFill(Color.BLUEVIOLET);
            textsNotes[i].setStyle("-fx-font: bold italic 12pt Arial;");

            buttonsOpenBeta.add(new Button("",new ImageView(new Image(getClass().getResourceAsStream("icon20x20.png")))));
            tempButton = buttonsOpenBeta.get(i);
            tempButton.setLayoutX(40);
            tempButton.setLayoutY(100 * i + 65);
            tempButton.setStyle("-fx-background-radius: 15;");

            buttonsEditBeta.add(new Button("",new ImageView(new Image(getClass().getResourceAsStream("edit20x20.png")))));
            tempButton=buttonsEditBeta.get(i);
            tempButton.setLayoutX(90);
            tempButton.setLayoutY(100*i+65);
            tempButton.setStyle("-fx-background-radius: 15;");


            buttonsDeleteBeta.add(new Button("",new ImageView(new Image(getClass().getResourceAsStream("trashIcon20x20.png")))));
            tempButton = buttonsDeleteBeta.get(i);
            tempButton.setLayoutX(140);
            tempButton.setLayoutY(100*i+65);
            tempButton.setStyle("-fx-background-radius: 15;");


            //смещение по заметкам
            scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    for (int i = 0; i < count; i++) {
                        textsNotes[i].setLayoutY(100*i-(Double) newValue+30);
                        Button tempButtonEdit = buttonsEditBeta.get(i);
                        tempButtonEdit.setLayoutY(100*i-(Double) newValue+65);
                        Button tempButtonDelete = buttonsDeleteBeta.get(i);
                        tempButtonDelete.setLayoutY(100*i-(Double) newValue+65);
                        Button tempButtonOpen = buttonsOpenBeta.get(i);
                        tempButtonOpen.setLayoutY(100*i-(Double) newValue+65);
                    }
                }
            });

        }


        if((count-3)>0)
            scrollBar.setMax(100*(count-3)+100);
        else scrollBar.setVisible(false);

    }

    protected void EditNotes(int count){
        for (int i = 0; i <count ; i++) {
            int finalI = i;
            Button tempButtonEdit = buttonsEditBeta.get(i);
            tempButtonEdit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ActionPressButtonEdit(ID_Code[finalI]);
                }
            });
        }
    }

    //Действия, происходящие при нажатии на кнопку "Edit"
    protected void ActionPressButtonEdit(String ID){
        Group EditRoot = new Group();
        Button SendEdit = new Button("",new ImageView(new Image(getClass().getResourceAsStream("right20x20.png"))));
        Button buttonBack = new Button("Back");
        TextField EditTitle = new TextField();
        TextArea EditText = new TextArea();
        Label titleLabel = new Label("Title");
        Label textLabel = new Label("Text");

        EditTitle.setLayoutX(30);
        EditTitle.setLayoutY(40);
        EditTitle.setPrefWidth(155);
        EditText.setLayoutX(30);
        EditText.setLayoutY(100);
        EditText.setPrefSize(180,80);
        EditText.setWrapText(true);
        titleLabel.setLayoutX(75);
        titleLabel.setLayoutY(20);
        titleLabel.setTextFill(Color.BLUEVIOLET);
        titleLabel.setStyle("-fx-font: bold italic 10pt Arial;");
        textLabel.setLayoutX(75);
        textLabel.setLayoutY(80);
        textLabel.setTextFill(Color.BLUEVIOLET);
        textLabel.setStyle("-fx-font: bold italic 10pt Arial;");
        SendEdit.setLayoutX(190);
        SendEdit.setLayoutY(38);
        SendEdit.setStyle("-fx-background-radius: 60;");
        buttonBack.setPrefSize(240,25);
        buttonBack.setLayoutY(215);

        EditRoot.getChildren().addAll(EditText,EditTitle,SendEdit,buttonBack,titleLabel,textLabel);
        OurMainStage.setScene(new Scene(EditRoot,240,240,Color.AZURE));

        SendEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String ourEditTitle = EditTitle.getText();
                    String ourEditText = EditText.getText();

                    for (int i = 0; i < ourEditTitle.length(); i++) {
                        if(ourEditTitle.charAt(0)==' '){
                            ourEditTitle=ourEditTitle.substring(1);
                        }
                        if(ourEditTitle.charAt(i)==' '){
                            ourEditTitle=ourEditTitle.substring(0,i)+"%20"+ourEditTitle.substring(i+1,ourEditTitle.length());
                        }
                    }

                    for (int i = 0; i < ourEditText.length(); i++) {
                        if(ourEditText.charAt(0)==' '){
                            ourEditText=ourEditText.substring(1);
                        }
                        if(ourEditText.charAt(i)==' '){
                            ourEditText=ourEditText.substring(0,i)+"%20"+ourEditText.substring(i+1,ourEditText.length());
                        }
                    }


                    //Создание запроса к VK_API
                    URL EditNote = new URL("https://api.vk.com/method/" +
                            "notes.edit?note_id="+ID+"&title="+ourEditTitle+"&text="+ourEditText+"&access_token=" +
                            "66b58f944b3c27a1ee9af3e599f09089b3c83e0ac567b471ba00b819e78bff890cbce1a79f21d6fb8c731&v=5.63");
                    EditNote.openStream();


                    //Получение Самих заметок от запроса к VK_API
                    getCount();

                    Thread.sleep(800);
                    String temp = adressTextFull;
                    for (int i = 0; i < countNotes; i++) {
                        int beginNumber = temp.indexOf("<title>");
                        int endNumber = temp.indexOf("</title>");
                        Title[i]=temp.substring(beginNumber+7,endNumber);
                        textsNotes[i].setText(Title[i]);
                        endNumber = temp.indexOf("</view_url>");
                        temp=temp.substring(endNumber+11);
                    }

                    OurMainStage.setScene(MainScene);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OurMainStage.setScene(MainScene);
            }
        });
    }

    protected void OpenNotes(int count){
        for (int i = 0; i < count; i++) {
            int finalI = i;
            Button tempButtonOpen = buttonsOpenBeta.get(i);
            tempButtonOpen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        ActionPressButtonOpen(AdressOurNotes[finalI],Title[finalI],finalI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    protected void ActionPressButtonOpen(String AddressNote, String titleCurrent, int finalI) throws IOException {
        Group groupOpen = new Group();
        Scene sceneOpen = new Scene(groupOpen,240,240,Color.AZURE);
        TextArea textNote = new TextArea();
        TextField titleNote = new TextField();
        Button buttonBack = new Button("Back");
        Button SendEdit = new Button("",new ImageView(new Image(getClass().getResourceAsStream("edit20x20.png"))));
        Label titleLabel = new Label("Title");
        Label textLabel = new Label("Text");

        String textNoteTemp = "";
        String textInTextArea = "";
        int beginIndex = 0;
        int endIndex = 0;
        int j=0;

        //Создаём URL и переходим по нему, ссылка ведёт на саму заметку
        URL addressOurNote = new URL(AddressNote);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(addressOurNote.openStream()));

        //Избавляемся он ненужных строк в парсинге страницы
        String temp = bufferedReader.readLine();
        for (int i = 0; i < 51; i++) {
            temp =bufferedReader.readLine();
        }
        while (temp!=null){
            temp=bufferedReader.readLine();
            if(temp!=null&&j<11){
                textNoteTemp+=temp;
                j++;
            }
        }

        beginIndex = textNoteTemp.indexOf("<div class=\"wiki_body");
        endIndex = textNoteTemp.indexOf("</div><div class=\"cb");
        textInTextArea = textNoteTemp.substring(beginIndex+23,endIndex-1);



        titleNote.setText(titleCurrent);
        titleNote.setEditable(false);
        titleNote.setLayoutX(30);
        titleNote.setLayoutY(40);
        titleNote.setPrefSize(155,20);
        textNote.setText(textInTextArea);
        textNote.setEditable(false);
        textNote.setLayoutX(30);
        textNote.setWrapText(true);
        textNote.setLayoutY(100);
        textNote.setPrefSize(180,80);
        titleLabel.setLayoutX(75);
        titleLabel.setLayoutY(20);
        titleLabel.setTextFill(Color.BLUEVIOLET);
        titleLabel.setStyle("-fx-font: bold italic 10pt Arial;");
        textLabel.setLayoutX(75);
        textLabel.setLayoutY(80);
        textLabel.setTextFill(Color.BLUEVIOLET);
        textLabel.setStyle("-fx-font: bold italic 10pt Arial;");
        SendEdit.setLayoutX(190);
        SendEdit.setLayoutY(38);
        SendEdit.setStyle("-fx-background-radius: 60;");
        SendEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ActionPressButtonEdit(ID_Code[finalI]);
            }
        });
        buttonBack.setPrefSize(240,25);
        buttonBack.setLayoutY(215);
        buttonBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OurMainStage.setScene(MainScene);
            }
        });

        groupOpen.getChildren().addAll(textNote,titleNote,buttonBack,titleLabel,textLabel,SendEdit);


        OurMainStage.setScene(sceneOpen);
    }

    protected void DeleteNotes(int count){
        for (int i = 0; i < count; i++) {
            int finalI = i;
            Button tempButtonDelete = buttonsDeleteBeta.get(i);
            tempButtonDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ActionPressButtonDelete(ID_Code[finalI],finalI);
                }
            });
        }
    }

    protected void ActionPressButtonDelete(String ID, int i){
        Group groupDelete = new Group();
        Scene sceneDelete = new Scene(groupDelete,240,240,Color.AZURE);
        Button buttonYes = new Button("",new ImageView(new Image(getClass().getResourceAsStream("yes30x30.png"))));
        Button buttonNo = new Button("",new ImageView(new Image(getClass().getResourceAsStream("no30x30.png"))));
        Button buttonBack = new Button("Back");
        Label warningLabel = new Label("Are you sure?");


        warningLabel.setLayoutX(60);
        warningLabel.setLayoutY(50);
        warningLabel.setTextFill(Color.BLUEVIOLET);
        warningLabel.setStyle("-fx-font: bold italic 14pt Arial;");
        buttonYes.setLayoutX(70);
        buttonYes.setLayoutY(120);
        buttonYes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL urlDeleteNote = new URL("https://api.vk.com/method/notes.delete.xml?note_id="+ID+"&access_token="+tokenVK
                    +"&v=5.63");
                    urlDeleteNote.openStream();
                    textsNotes[i].setTextFill(Color.GRAY);
                    Button tempButton = buttonsOpenBeta.get(i);
                    tempButton.setDisable(true);
                    tempButton = buttonsEditBeta.get(i);
                    tempButton.setDisable(true);
                    tempButton = buttonsDeleteBeta.get(i);
                    tempButton.setDisable(true);
                    OurMainStage.setScene(MainScene);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonYes.setStyle("-fx-background-radius: 90");
        buttonNo.setLayoutX(130);
        buttonNo.setLayoutY(120);
        buttonNo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OurMainStage.setScene(MainScene);
            }
        });
        buttonNo.setStyle("-fx-background-radius: 90; -fx-background-fill: BLUEVIOLET");
        buttonBack.setPrefSize(240,25);
        buttonBack.setLayoutY(215);
        buttonBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OurMainStage.setScene(MainScene);
            }
        });

        groupDelete.getChildren().addAll(buttonYes,buttonNo,buttonBack,warningLabel);

        OurMainStage.setScene(sceneDelete);
    }

    protected void setWelcomeParam(){
        Label welcLabel = new Label("Welcome!");
        Button OauthButton = new Button("Login");
        Button SettingButton = new Button("Setting");

        OauthButton.setLayoutX(40);
        OauthButton.setLayoutY(80);
        OauthButton.setPrefSize(150,25);
        OauthButton.setStyle("-fx-background-radius: 20");
        SettingButton.setLayoutX(40);
        SettingButton.setLayoutY(150);
        SettingButton.setPrefSize(150,25);
        SettingButton.setStyle("-fx-background-radius: 20");
        welcLabel.setLayoutX(70);
        welcLabel.setLayoutY(35);
        welcLabel.setStyle("-fx-text-fill: BLUEVIOLET;-fx-font: bold italic 13pt Arial");

        OauthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OauthWithVK();
            }
        });

        welcomeGroup.getChildren().addAll(welcLabel,OauthButton,SettingButton);
        OurMainStage.setScene(welcomeScene);
    }

    protected void OauthWithVK  (){
        Group root = new Group();
        Scene OauthScene = new Scene(root,240,240,Color.AZURE);
        WebView OauthWebVK = new WebView();
        Button sendTokenAndID = new Button("",new ImageView(new Image(getClass().getResourceAsStream("right20x20.png"))));
        TextField urlAddress= new TextField("");

        OauthWebVK.setLayoutX(20);
        OauthWebVK.setLayoutY(50);
        OauthWebVK.getEngine().load("https://oauth.vk.com/authorize?client_id=5909565&display=mobile" +     //oauth.vk.com/blank.html
                "&redirect_uri=https://vk.com/&scope=notes,offline&response_type=token&v=5.62&state=123456");
        OauthWebVK.setPrefSize(200,180);
        sendTokenAndID.setLayoutX(180);
        sendTokenAndID.setLayoutY(10);
        urlAddress.setLayoutX(20);
        urlAddress.setLayoutY(10);
        urlAddress.setText(OauthWebVK.getEngine().getLocation());
        urlAddress.setEditable(false);

        sendTokenAndID.setOnAction(new EventHandler<ActionEvent>() {
            String temp = "";
            int beginIndex = 0;
            int endIndex = 0;
            @Override
            public void handle(ActionEvent event) {
                temp = OauthWebVK.getEngine().getLocation();
                //считываем токен
                beginIndex=temp.indexOf("#access_token=");
                endIndex=temp.indexOf("&expires_in");
                if((beginIndex!=-1)&&(endIndex!=-1))
                    tokenVK=temp.substring(beginIndex+14,endIndex);
                //считываем ID
                beginIndex=temp.indexOf("&user_id=");
                endIndex=temp.indexOf("&state=");
                if((beginIndex!=-1)&&(endIndex!=-1))
                    IDVK=temp.substring(beginIndex+9,endIndex);

                if((IDVK!="")&&tokenVK!="") {
                    try {
                        printFileConfig();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    OurMainStage.setScene(MainScene);
                    try {
                        initAndShowNotes();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        root.getChildren().addAll(OauthWebVK,sendTokenAndID,urlAddress);
        OurMainStage.setScene(OauthScene);
        OurMainStage.show();

    }


    /*
        Menu methods
     */
    protected void AddNotes(){
        mainMenu.getItems().addAll(itemAdd,itemSetting,itemLog_out);
        itemAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionByAddingNewNotes();
            }
        });
    }

    protected void actionByAddingNewNotes(){
        Group AddRoot = new Group();
        Button SendAdd = new Button("",new ImageView(new Image(getClass().getResourceAsStream("add20x20.png"))));
        Button buttonBack = new Button("Back");
        TextField AddTitle = new TextField();
        TextArea AddText = new TextArea();
        Label titleLabel = new Label("Title");
        Label textLabel = new Label("Text");

        AddTitle.setLayoutX(30);
        AddTitle.setLayoutY(40);
        AddTitle.setPrefWidth(155);
        AddText.setLayoutX(30);
        AddText.setLayoutY(100);
        AddText.setPrefSize(180,80);
        AddText.setWrapText(true);
        titleLabel.setLayoutX(75);
        titleLabel.setLayoutY(20);
        titleLabel.setTextFill(Color.BLUEVIOLET);
        titleLabel.setStyle("-fx-font: bold italic 10pt Arial;");
        textLabel.setLayoutX(75);
        textLabel.setLayoutY(80);
        textLabel.setTextFill(Color.BLUEVIOLET);
        textLabel.setStyle("-fx-font: bold italic 10pt Arial;");
        SendAdd.setLayoutX(190);
        SendAdd.setLayoutY(38);
        SendAdd.setStyle("-fx-background-radius: 90;");
        buttonBack.setPrefSize(240,25);
        buttonBack.setLayoutY(215);
        AddRoot.getChildren().addAll(AddTitle,AddText,SendAdd,buttonBack,titleLabel,textLabel);
        OurMainStage.setScene(new Scene(AddRoot,240,240,Color.AZURE));

        SendAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String ourAddTitle = AddTitle.getText();
                    String ourAddText = AddText.getText();

                    for (int i = 0; i < ourAddTitle.length(); i++) {
                        if(ourAddTitle.charAt(0)==' '){
                            ourAddTitle=ourAddTitle.substring(1);
                        }
                        if(ourAddTitle.charAt(i)==' '){
                            ourAddTitle=ourAddTitle.substring(0,i)+"%20"+ourAddTitle.substring(i+1,ourAddTitle.length());
                        }
                    }

                    for (int i = 0; i < ourAddText.length(); i++) {
                        if(ourAddText.charAt(0)==' '){
                            ourAddText=ourAddText.substring(1);
                        }
                        if(ourAddText.charAt(i)==' '){
                            ourAddText=ourAddText.substring(0,i)+"%20"+ourAddText.substring(i+1,ourAddText.length());
                        }
                    }

                    URL urlAddNewNote = new URL("https://api.vk.com/method/notes.add?&title="+ourAddTitle+"&text="+
                            ourAddText+"&access_token="+tokenVK+"&v=5.63");
                    urlAddNewNote.openStream();

                    OurMainStage.setScene(MainScene);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OurMainStage.setScene(MainScene);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
