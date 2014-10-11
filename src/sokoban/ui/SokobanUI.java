package sokoban.ui;

import application.Main;
import application.Main.SokobanPropertyType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;

import sokoban.file.SokobanFileLoader;
import sokoban.game.SokobanGameData;
import sokoban.game.SokobanGameStateManager;
import application.Main.SokobanPropertyType;
import java.util.logging.Level;
import java.util.logging.Logger;
import properties_manager.PropertiesManager;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JScrollPane;

public class SokobanUI extends Pane {

    /**
     * The SokobanUIState represents the four screen states that are possible
     * for the Sokoban game application. Depending on which state is in current
     * use, different controls will be visible.
     */
    public enum SokobanUIState {

        SPLASH_SCREEN_STATE, PLAY_GAME_STATE, VIEW_STATS_STATE, VIEW_HELP_STATE,
        HANG1_STATE, HANG2_STATE, HANG3_STATE, HANG4_STATE, HANG5_STATE, HANG6_STATE,
    }

    // mainStage
    private Stage primaryStage;

    // mainPane
    public BorderPane mainPane;
    private BorderPane hmPane;

    // SplashScreen
    private ImageView splashScreenImageView;
    private Pane splashScreenPane;
    private Label splashScreenImageLabel;
    private HBox levelSelectionPane;
    private ArrayList<Button> levelButtons;

    // NorthToolBar
    public HBox northToolbar;
    private Button gameButton = new Button("Game");
    private Button statsButton = new Button("Stats");;
    private Button helpButton = new Button("help");;
    private Button exitButton = new Button("exit");;
    private Button undo =new Button("undo");
    private Button time;
    // GamePane
    private Label SokobanLabel;
    private Button newGameButton;
    private HBox letterButtonsPane;
    private HashMap<Character, Button> letterButtons;
    private BorderPane gamePanel = new BorderPane();

    //StatsPane
    private ScrollPane statsScrollPane;
    private JEditorPane statsPane;
    
    //HelpPane
    private BorderPane helpPanel;
    private JScrollPane helpScrollPane;
    private JEditorPane helpPane;
    private Button homeButton;
    private Pane workspace;

    // Padding
    private Insets marginlessInsets;

    // Image path
    private String ImgPath = "file:images/";

    // mainPane weight && height
    private int paneWidth;
    private int paneHeigth;

    // THIS CLASS WILL HANDLE ALL ACTION EVENTS FOR THIS PROGRAM
    private SokobanEventHandler eventHandler;
    private SokobanErrorHandler errorHandler;
    private SokobanDocumentManager docManager;

    
    
    private String level;
    SokobanGameStateManager gsm;

    public SokobanUI() {
        gsm = new SokobanGameStateManager(this);
        eventHandler = new SokobanEventHandler(this);
        errorHandler = new SokobanErrorHandler(primaryStage);
        docManager = new SokobanDocumentManager(this);
        initMainPane();
        initSplashScreen();
    }

    public void SetStage(Stage stage) {
        primaryStage = stage;
    }

    public BorderPane GetMainPane() {
        return this.mainPane;
    }

    public SokobanGameStateManager getGSM() {
        return gsm;
    }

    public SokobanDocumentManager getDocManager() {
        return docManager;
    }

    public SokobanErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public JEditorPane getHelpPane() {
        return helpPane;
    }

    public void initMainPane() {
        marginlessInsets = new Insets(5, 5, 5, 5);
        mainPane = new BorderPane();

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        paneWidth = Integer.parseInt(props
                .getProperty(SokobanPropertyType.WINDOW_WIDTH));
        paneHeigth = Integer.parseInt(props
                .getProperty(SokobanPropertyType.WINDOW_HEIGHT));
        mainPane.resize(paneWidth, paneHeigth);
        mainPane.setPadding(marginlessInsets);
    }

    public void initSplashScreen() {
        mainPane.getChildren().clear();
        // INIT THE SPLASH SCREEN CONTROLS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String splashScreenImagePath = props
                .getProperty(SokobanPropertyType.SPLASH_SCREEN_IMAGE_NAME);
        props.addProperty(SokobanPropertyType.INSETS, "5");
        String str = props.getProperty(SokobanPropertyType.INSETS);
        
        paneWidth = Integer.parseInt(props
                .getProperty(SokobanPropertyType.WINDOW_WIDTH));
        paneHeigth = Integer.parseInt(props
                .getProperty(SokobanPropertyType.WINDOW_HEIGHT));


        ArrayList<String> levels = props
                .getPropertyOptionsList(SokobanPropertyType.LEVEL_OPTIONS);
        ArrayList<String> levelImages = props
                .getPropertyOptionsList(SokobanPropertyType.LEVEL_IMAGE_NAMES);
        ArrayList<String> levelFiles = props
                .getPropertyOptionsList(SokobanPropertyType.LEVEL_FILES);
        //splashScreenPane.backgroundProperty();
        //splashScreenPane.setBackground(splashScreenImage);
        //splashScreenPane.setStyle("-fx-background-image: url('file:images/SokobanSplashScreen.png')");
        
        //splashScreenImageLabel.setOnMouseClicked(
        //e->{
          //  initLevelPane();
        //});
        

        //Button levelSectionButton = new Button("new Game");
        //levelSectionButton.setOnAction(new EventHandler<ActionEvent>() {

          //      @Override
            //    public void handle(ActionEvent event) {
              //      initLevelPane();
               // }
            //});
        //splashScreenPane.getChildren().add(levelSectionButton);
        //levelSelectionPane = new HBox();
     
        GridPane levelSelectionPane = new GridPane();
        //levelSelectionPane.setSpacing(10.0);
        levelSelectionPane.setAlignment(Pos.CENTER);
        levelSelectionPane.relocate(0, paneHeigth/2);
        levelSelectionPane.setStyle("-fx-background-image: url('file:images/SokobanSplashScreen.png')");
         //add key listener
        levelButtons = new ArrayList<Button>();
        for (int i = 0; i < levels.size(); i++) {

            //GET THE LIST OF LEVEL OPTIONS
            String level = levels.get(i);
            String levelImageName = levelImages.get(i);
            
            Image levelImage = loadImage(levelImageName);
            ImageView levelImageView = new ImageView(levelImage);
            levelImageView.setFitHeight(USE_PREF_SIZE);
            levelImageView.setFitWidth(134);
            levelImageView.setFitHeight(134);
             //AND BUILD THE BUTTON
            Label buttonLabel = new Label("Level "+ level);
            Button levelButton = new Button();
            
            
            //levelButton.setAlignment(Pos.CENTER);
            levelButton.setGraphic(levelImageView);
            

            levelButton.setOnAction(e->{
               
                

                try {
                     initGameScreen();
                    eventHandler.respondToSelectLevelRequest(mainPane, level);
                    // mainPane.setBottom((Node) eventHandler.respondToSelectLevelRequest(mainPane, level));
                    //mainPane.getChildren().add((Node) eventHandler.respondToSelectLevelRequest(mainPane, level));
                } catch (IOException ex) {
                    Logger.getLogger(SokobanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            // CONNECT THE BUTTON TO THE EVENT HANDLER
            //levelButton.setOnAction(new EventHandler<ActionEvent>() {

               //@Override
                //public void handle(ActionEvent event) {
                    // TODO
              //     eventHandler.respondToSelectLevelRequest(level);
                //}
            //});


            if(i>4)
                levelSelectionPane.add(levelButton,i-4,1);
   
            else
                levelSelectionPane.add(levelButton,i,0);

            //if(i > 0)
            //levelButton.setDisable(true);
        }

        mainPane.setCenter(levelSelectionPane);
    }

    /**
     * This method initializes the language-specific game controls, which
     * includes the three primary game screens.
     */
    public void initSokobanUI() {
        // FIRST REMOVE THE SPLASH SCREEN
        mainPane.getChildren().clear();

        // GET THE UPDATED TITLE
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String title = props.getProperty(SokobanPropertyType.GAME_TITLE_TEXT);
        primaryStage.setTitle(title);

        // THEN ADD ALL THE STUFF WE MIGHT NOW USE
        initNorthToolbar();

        // OUR WORKSPACE WILL STORE EITHER THE GAME, STATS,
        // OR HELP UI AT ANY ONE TIME
        initWorkspace();
        initGameScreen();
        initStatsPane();
        initHelpPane();

        // WE'LL START OUT WITH THE GAME SCREEN
        changeWorkspace(SokobanUIState.PLAY_GAME_STATE);

    }
    private void initGameScreen(){
       mainPane.getChildren().clear();
       initNorthToolbar();
        //Pane gamePane = new Pane();
        //PropertiesManager props = PropertiesManager.getPropertiesManager();
       // gamePane.loadPage(gamePane, SokoabnPropertyType.GAME_FILE_NAME);
        //		HTMLDocument gameDoc = (HTMLDocument) gamePane.getDocument();
        //gamePane.
        //mainPane.getChildren().add(gamePane);
    }
    
    private void initStatsPane(){
        mainPane.getChildren().clear();
        initNorthToolbar();
        ScrollPane statsPane = new ScrollPane();
    }
    
    private void initHelpPane(){
        mainPane.getChildren().clear();
        initNorthToolbar();
        
    }

    /**
     * This function initializes all the controls that go in the north toolbar.
     */
   public void initNorthToolbar() {
        // MAKE THE NORTH TOOLBAR, WHICH WILL HAVE FOUR BUTTONS
        northToolbar = new HBox();
        northToolbar.setStyle("-fx-background-color:lightgray");
        northToolbar.setAlignment(Pos.CENTER);
        northToolbar.setPadding(marginlessInsets);
        northToolbar.setSpacing(10.0);

        // MAKE AND INIT THE GAME BUTTON
        
        undo = initToolbarButton(northToolbar,
                SokobanPropertyType.GAME_IMG_NAME);
        Image undoimage = new Image("file:images/undo.png");
        ImageView undoView = new ImageView(undoimage);
        undo.setGraphic(undoView);
        //setTooltip(gameButton, SokobanPropertyType.GAME_TOOLTIP);
        undo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler
                        .respondToUndoRequest(mainPane);
            }
        });
        Button backButton = new Button();
        backButton = initToolbarButton(northToolbar,
                SokobanPropertyType.STATS_IMG_NAME);
        Image backimage = new Image("file:images/back.png");
        ImageView backView = new ImageView(backimage);
        backButton.setGraphic(backView);
        //setTooltip(statsButton, SokobanPropertyType.STATS_TOOLTIP);
        
        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler
                        .respondToSwitchScreenRequest(SokobanUIState.VIEW_STATS_STATE);
              initSplashScreen();
            }

        });
        
        // MAKE AND INIT THE STATS BUTTON
        
        statsButton = initToolbarButton(northToolbar,
                SokobanPropertyType.STATS_IMG_NAME);
        Image statsimage = new Image("file:images/Stats.png");
        ImageView statsView = new ImageView(statsimage);
        statsButton.setGraphic(statsView);
        //setTooltip(statsButton, SokobanPropertyType.STATS_TOOLTIP);
        
        statsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler
                        .respondToSwitchScreenRequest(SokobanUIState.VIEW_STATS_STATE);
               initStatsPane();
            }

        });
        // MAKE AND INIT THE HELP BUTTON
        //helpButton = initToolbarButton(northToolbar,
          //      SokobanPropertyType.HELP_IMG_NAME);
        //Image helpimage = new Image("file:images/help.png");
        //ImageView helpView = new ImageView(helpimage);
        //helpButton.setGraphic(helpView);
        //setTooltip(helpButton, SokobanPropertyType.HELP_TOOLTIP);
        //helpButton.setOnAction(new EventHandler<ActionEvent>() {

          //  @Override
            //public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
              //  eventHandler
               //         .respondToSwitchScreenRequest(SokobanUIState.VIEW_HELP_STATE);
            //}

        //});
        Thread1 timeLine = new Thread1();
        timeLine.start();
        //timeLine.timeLabel();
       // Label time = new Label();
        //time.setStyle("-fx-background-image: url('file:images/time.png')");
        //time = new Button("time");
        //time = initToolbarButton(northToolbar,
                //SokobanPropertyType.TIME_IMG_NAME);
        //Image timeImage = new Image("file:images/time.png");
        //ImageView timeView = new ImageView(timeImage);
        //time.setBackground(Background.timeImage);
        //Label timeLabel = new Label("Time"+timeLine.getTime());
        
        //northToolbar.getChildren().add(timeLine.timeLabel());
       // int timer;
        
        //timeline = new Timeline();
        
        //Image timeimage = new Image("file:images/time.png");
        //ImageView timeView = new ImageView(timeimage);
        //time.setGraphic(timeView);
        
        //setTooltip(helpButton, SokobanPropertyType.HELP_TOOLTIP);
        

        // MAKE AND INIT THE EXIT BUTTON
        //exitButton = initToolbarButton(northToolbar,
         //       SokobanPropertyType.EXIT_IMG_NAME);
        //Image exitimage = new Image("file:images/exit.png");
        //ImageView exitView = new ImageView(exitimage);
        //exitButton.setGraphic(exitView);
        //setTooltip(exitButton, SokobanPropertyType.EXIT_TOOLTIP);
        //exitButton.setOnAction(new EventHandler<ActionEvent>() {

          //  @Override
            //public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
              //  eventHandler.respondToExitRequest(primaryStage);
            //}

      //  });
        
        // AND NOW PUT THE NORTH TOOLBAR IN THE FRAME
        mainPane.setTop(northToolbar);
                    northToolbar.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                
                try {
                    eventHandler.keyPressed(ke, mainPane,level);
                } catch (IOException ex) {
                    Logger.getLogger(SokobanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
       
    }

    /**
     * This method helps to initialize buttons for a simple toolbar.
     *
     * @param toolbar The toolbar for which to add the button.
     *
     * @param prop The property for the button we are building. This will
     * dictate which image to use for the button.
     *
     * @return A constructed button initialized and added to the toolbar.
     */
    private Button initToolbarButton(HBox toolbar, SokobanPropertyType prop) {
        // GET THE NAME OF THE IMAGE, WE DO THIS BECAUSE THE
        // IMAGES WILL BE NAMED DIFFERENT THINGS FOR DIFFERENT LANGUAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imageName = props.getProperty(prop);

        // LOAD THE IMAGE
        Image image = loadImage(imageName);
        ImageView imageIcon = new ImageView(image);

        // MAKE THE BUTTON
        Button button = new Button();
        button.setGraphic(imageIcon);
        button.setPadding(marginlessInsets);

        // PUT IT IN THE TOOLBAR
        toolbar.getChildren().add(button);

        // AND SEND BACK THE BUTTON
        return button;
    }

    /**
     * The workspace is a panel that will show different screens depending on
     * the user's requests.
     */
    private void initWorkspace() {
        // THE WORKSPACE WILL GO IN THE CENTER OF THE WINDOW, UNDER THE NORTH
        // TOOLBAR
        workspace = new Pane();
        mainPane.setCenter(workspace);
        mainPane.getChildren().add(workspace);
        System.out.println("in the initWorkspace");
    }


    public Image loadImage(String imageName) {
        Image img = new Image(ImgPath + imageName);
        return img;
    }

    /**
     * This function selects the UI screen to display based on the uiScreen
     * argument. Note that we have 3 such screens: game, stats, and help.
     *
     * @param uiScreen The screen to be switched to.
     */
    public void changeWorkspace(SokobanUIState uiScreen) {
        switch (uiScreen) {
            case VIEW_HELP_STATE:
                mainPane.setCenter(helpPanel);
                break;
            case PLAY_GAME_STATE:
                mainPane.setCenter(gamePanel);
                break;
            case VIEW_STATS_STATE:
                mainPane.setCenter(statsScrollPane);
                break;
            default:
        }

    }


}
    class Thread1 extends Thread  implements Runnable {
         SokobanUI ui;
        int sec=0;
        
        int min=0;
    //private Node northToolbar;
        public void run() {
        int i = 1;
        while (i < 100000) {
            //return i;
            sec++;
            if (sec == 60)
            {sec=0;
             min++;
            }
            
            try {
            Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }
            i++;
                        }
                           }
        public Label timeLabel(Object node){
            Label label = new Label();
            label.setStyle("-fx-background-image: url('file:images/time.png')");
            label.setText(min + ": " + sec);
            
            //label
            //ui.northToolbar.getChildren().add(label);
            //ui.mainPane.setTop(northToolbar);
            return label;
            
        }
        public int getMin(){
            return min;
        }
        public int getSec(){
            return sec;
        }
        public String getTime(){
            return min+": "+sec;
        }
}