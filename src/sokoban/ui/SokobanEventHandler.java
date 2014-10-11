package sokoban.ui;

import application.Main.SokobanPropertyType;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import properties_manager.PropertiesManager;
import sokoban.file.SokobanFileLoader;
import sokoban.game.SokobanGameStateManager;
import xml_utilities.InvalidXMLFileFormatException;

public class SokobanEventHandler{
    private final String SOKOBAN_DATA_DIR = "../Sokoban_draft/data/";
    private final String SOKOBAN_SAVE_PATH = "../Sokoban_draft/";
    private SokobanUI ui;
    private  int totalWinPoint = 0;
    private static ArrayList<int[][]> saveList = new ArrayList<int[][]>();
    private int step = 0;
    public int saveGrid[][];
    private int printPane[][];
    private int winGrid[][];
    private int newSaveGrid[][];
    private Pane newGamePane;
    private Image sokobanAnimationImage = new Image("file:images/Sokoban.png"); 
    private double t = 0;
    
    
    
    private static GridRenderer gamePane;
    GridRenderer TestgamePane = new GridRenderer();
    /**
     * Constructor that simply saves the ui for later.
     *
     * @param initUI
     */
    
    
    
    private Timeline animation;
    
    public void moveMan(){
            double x = gamePane.getX();
            double y = gamePane.getY();
            animation.setCycleCount (Timeline.INDEFINITE);
            gamePane.getgc().drawImage(sokobanAnimationImage, x+t , y+t, gamePane. getW(),gamePane.getH() );
                            
    
    }
    
    
    public SokobanEventHandler(SokobanUI initUI) {
        ui = initUI;
       GridRenderer TestgamePane = new GridRenderer();
        gamePane = new GridRenderer();
         //saveGrid = new int[][];
         step = 0;
       //GridRenderer gamePane = new GridRenderer();
    }

    /**
     * This method responds to when the user wishes to switch between the Game,
     * Stats, and Help screens.
     *
     * @param uiState The ui state, or screen, that the user wishes to switch
     * to.
     */
    public void respondToSwitchScreenRequest(SokobanUI.SokobanUIState uiState) {

        ui.changeWorkspace(uiState);
    }
    public void playWalkSound(){
    try{
        Media someSound = new Media(getClass().getResource(SOKOBAN_DATA_DIR +"walk.wav").toString());
        playMedia(someSound);
    }catch(Exception ex){
        //GeeLogger.error(getClass(), ex.getLocalizedMessage(), ex);
    }

}
        public void playPushSound(){
    try{
        Media someSound = new Media(getClass().getResource(SOKOBAN_DATA_DIR +"boxMove.wav").toString());
 
        playMedia(someSound);
    }catch(Exception ex){
        //GeeLogger.error(getClass(), ex.getLocalizedMessage(), ex);
    }

}
    private void playMedia(Media m){
    if (m != null){
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
    }
}
    public void respondToSelectLevelRequest(BorderPane mainPane, String level)  throws FileNotFoundException, IOException{

        //String filePath ="file:data/6.sok";
             
      
         step = 0;
        File sokFile = new File( "C:/Users/zzxx/Desktop/netBean/hw3/Sokoban_draft/"+ level+".file");
        if(!sokFile.exists())
        sokFile = new File(SOKOBAN_DATA_DIR  + level+".sok");

        byte[] bytes = new byte[Long.valueOf(sokFile.length()).intValue()];
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        FileInputStream fis = new FileInputStream(sokFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

                    // HERE IT IS, THE ONLY READY REQUEST WE NEED
                    bis.read(bytes);
                    bis.close();

                    // NOW WE NEED TO LOAD THE DATA FROM THE BYTE ARRAY
                    DataInputStream dis = new DataInputStream(bais);

                    // NOTE THAT WE NEED TO LOAD THE DATA IN THE SAME
                    // ORDER AND FORMAT AS WE SAVED IT
                    // FIRST READ THE GRID DIMENSIONS
                    int initGridColumns = dis.readInt();
                    int initGridRows = dis.readInt();
                    int[][] newGrid = new int[initGridColumns][initGridRows];

                    // AND NOW ALL THE CELL VALUES
                    for (int i = 0; i < initGridColumns; i++) {
                        for (int j = 0; j < initGridRows; j++) {
                            newGrid[i][j] = dis.readInt();                            
                        }
                    }
                    
                  // GridRenderer gamePane = new GridRenderer();
                    gamePane.grid = newGrid;

                    gamePane.gridColumns = initGridColumns;
                    System.out.println("initGridColumns gamePane" + gamePane.gridColumns );
                    gamePane.gridRows = initGridRows;
                     System.out.println("initGridrows gamePane" + gamePane.gridRows );
                    saveGrid = new int[gamePane.gridColumns][gamePane.gridRows];
                    winGrid =  new int[gamePane.gridColumns][gamePane.gridRows];
                    printPane = new int[gamePane.gridColumns][gamePane.gridRows];
                    newSaveGrid= new int[gamePane.gridColumns][gamePane.gridRows];
                     totalWinPoint = 0;
                    for(int i =0; i <gamePane.gridColumns; i ++)
                           for(int j = 0; j<gamePane.gridRows; j++)
                           {
                               if(gamePane.grid[i][j]==3)
                               {
                                  totalWinPoint++;
                               }
                              saveGrid[i][j] = gamePane.grid[i][j];
                               winGrid[i][j] = gamePane.grid[i][j];
                               System.out.print(saveGrid[i][j]);
                           }
                    //System.out.println(totalWinPoint);
                   
                    saveList.add(saveGrid);
                  
                    gamePane.repaint();
                
        Pane newGamePane = new Pane();       
        System.out.println(level);
        
            //gamePane.onKeyPressedProperty()->{
        
        //}
        
        
        
        gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                
                try {
                    keyPressed(ke, mainPane,level);
                } catch (IOException ex) {
                    Logger.getLogger(SokobanEventHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        
        

            gamePane.setOnMouseClicked(mouseEvent -> {
            // FIGURE OUT THE CORRESPONDING COLUMN & ROW
            double w = gamePane.getWidth() / gamePane.gridColumns;
            double col = mouseEvent.getX() / w;
            double h = gamePane.getHeight() / gamePane.gridRows;
            double row = mouseEvent.getY() / h;
            // GET THE VALUE IN THAT CELL
            if((gamePane.grid[(int) col][(int) row]==0 || gamePane.grid[(int) col][(int) row]==3 )&& gamePane.grid[(int) col+1][(int) row] ==4){
                 gamePane.grid[(int) col][(int) row]=4;
                  gamePane.grid[(int) col+1][(int) row] = 0;
                  animation = new Timeline(new KeyFrame(Duration.millis(5), e -> moveMan()));
                  play(SOKOBAN_DATA_DIR+"walk.wav");
            }
            if((gamePane.grid[(int) col][(int) row]==0 || gamePane.grid[(int) col][(int) row]==3 ) &&gamePane.grid[(int) col-1][(int) row]==4){
                 gamePane.grid[(int) col][(int) row]=4;
                  gamePane.grid[(int) col-1][(int) row] = 0;

                  play(SOKOBAN_DATA_DIR+"walk.wav");
            }
                     
             if((gamePane.grid[(int) col][(int) row]==0 || gamePane.grid[(int) col][(int) row]==3 )&&gamePane.grid[(int) col][(int) row+1]==4){
                  gamePane.grid[(int) col][(int) row]=4;
                   gamePane.grid[(int) col][(int) row+1] = 0;

                  play(SOKOBAN_DATA_DIR+"walk.wav");
             }
              if((gamePane.grid[(int) col][(int) row]==0 || gamePane.grid[(int) col][(int) row]==3 ) &&gamePane.grid[(int) col][(int) row-1]==4){
               gamePane.grid[(int) col][(int) row]=4;
                gamePane.grid[(int) col][(int) row-1] = 0;

                  play(SOKOBAN_DATA_DIR+"walk.wav");
              }
              

            //push box
             if(gamePane.grid[(int) col][(int) row]==2 && gamePane.grid[(int) col+1][(int) row] ==4 && (gamePane.grid[(int) col-1][(int) row] ==0 || gamePane.grid[(int) col-1][(int) row] == 3))
             {gamePane.grid[(int) col][(int) row] =4;
              gamePane.grid[(int) col-1][(int) row] = 2;
               gamePane.grid[(int) col+1][(int) row] = 0;

                  play(SOKOBAN_DATA_DIR+"boxMove.wav");
             }
                     
             else if( gamePane.grid[(int) col][(int) row] == 2 && gamePane.grid[(int) col-1][(int) row]==4 &&(gamePane.grid[(int) col+1][(int) row] ==0 || gamePane.grid[(int) col+1][(int) row] == 3))
             {
                 gamePane.grid[(int) col][(int) row] =4;
                 gamePane.grid[(int) col-1][(int) row] = 0;
                gamePane.grid[(int) col+1][(int) row] = 2;
 
                  play(SOKOBAN_DATA_DIR+"/boxMove.wav");
             }
             else if(gamePane.grid[(int) col][(int) row] ==2 && gamePane.grid[(int) col][(int) row+1]==4  && (gamePane.grid[(int) col][(int) row-1] ==0 || gamePane.grid[(int) col][(int) row-1] == 3))
             {
                 gamePane.grid[(int) col][(int) row] =4;
                 gamePane.grid[(int) col][(int) row-1] = 2;
                gamePane.grid[(int) col][(int) row+1] = 0;
        
                  play(SOKOBAN_DATA_DIR+"boxMove.wav");
             }    
                            
             else if( gamePane.grid[(int) col][(int) row]==2 && gamePane.grid[(int) col][(int) row-1]==4 && (gamePane.grid[(int) col][(int) row+1] ==0 || gamePane.grid[(int) col][(int) row+1] == 3))
                {
                 gamePane.grid[(int) col][(int) row] =4;
                 gamePane.grid[(int) col][(int) row-1] = 0;
                gamePane.grid[(int) col][(int) row+1] = 2;

                   play(SOKOBAN_DATA_DIR+"boxMove.wav");
                }    
               

              for(int i =0; i <gamePane.gridColumns; i ++){
                for(int j = 0; j<gamePane.gridRows; j++)
                {

                    if(winGrid[i][j]== 3 && gamePane.grid[i][j]==0)
                        gamePane.grid[i][j] = 3;
                }
              }
           //   mainPane.getChildren().retainAll(gamePane);
            gamePane.repaint();
    
            if(Arrays.deepEquals(gamePane.grid, saveList.get(step)))
                  System.out.print("SAME   ");
            else
            {
                  System.out.print("NOT SAME    ");
                           

                           for(int i =0; i <gamePane.gridColumns; i ++){
                                for(int j = 0; j<gamePane.gridRows; j++){
                                    newSaveGrid[i][j] = gamePane.grid[i][j];
                                }
                                    }
                           saveList.add(step, newSaveGrid);
                            step++;
            }

            
             int winPoint= 0;
             boolean lose = false;
             int btotalWinPoint = totalWinPoint;
            for(int i =0; i <gamePane.gridColumns; i ++){
                for(int j = 0; j<gamePane.gridRows; j++)
                {
                    if(winGrid[i][j] == 3 && gamePane.grid[i][j]==2 )
                        winPoint++;
                    if(winGrid[i][j]== 3 && gamePane.grid[i][j]==0)
                        gamePane.grid[i][j] = 3;
                 
                    
                    
                    
                    if(gamePane.grid[i][j]==2 && winGrid[i][j] != 3 &&((gamePane.grid[i][j+1]==1 && gamePane.grid[i+1][j]==1) || (gamePane.grid[i][j-1]== 1 &&gamePane.grid[i-1][j]==1)||
                            (gamePane.grid[i+1][j]== 1 &&gamePane.grid[i][j-1]==1)||(gamePane.grid[i-1][j]== 1 &&gamePane.grid[i][j+1]==1)))
                    {
                      System.out.println("YOU LOSE" );
                             Stage dialogStage = new Stage();
                      dialogStage.initModality(Modality.WINDOW_MODAL);
                      //dialogStage.initOwner(primaryStage);
                      BorderPane exitPane = new BorderPane();
                      HBox optionPane = new HBox();
                       optionPane.setAlignment(Pos.CENTER);
                      Button okButton = new Button("OK");
                      optionPane.setSpacing(175.0);
                      optionPane.getChildren().addAll(okButton);
        
                      Label exitLabel = new Label("YOU LOSE!");
                      exitPane.setCenter(exitLabel);
                      exitPane.setBottom(optionPane);
                      Scene scene = new Scene(exitPane, 250, 100);
                      dialogStage.setScene(scene);
                      dialogStage.show();
                      okButton.setOnAction(e -> {
            dialogStage.close();
        });
                    }
                    }
          }
            System.out.println(winPoint+ "  total  " + btotalWinPoint);
            if(btotalWinPoint == winPoint){
                System.out.println("you win");
               System.out.println("YOU WIN" );
                             Stage dialogStage = new Stage();
                      dialogStage.initModality(Modality.WINDOW_MODAL);
                      //dialogStage.initOwner(primaryStage);
                      BorderPane exitPane = new BorderPane();
                      HBox optionPane = new HBox();
                       optionPane.setAlignment(Pos.CENTER);
                      Button okButton = new Button("OK");
                      optionPane.setSpacing(175.0);
                      optionPane.getChildren().addAll(okButton);
        
                      Label exitLabel = new Label("YOU WIN!");
                      exitPane.setCenter(exitLabel);
                      exitPane.setBottom(optionPane);
                      Scene scene = new Scene(exitPane, 250, 100);
                      dialogStage.setScene(scene);
                      dialogStage.show();
                       okButton.setOnAction(e -> {
            dialogStage.close();
        });
                      
                      
                       
                
                
            }
            TestgamePane.gridColumns = gamePane.gridColumns;
            TestgamePane.gridRows = gamePane.gridRows;
           // System.out.println(TestgamePane.gridColumns);
            TestgamePane.grid = new int[TestgamePane.gridColumns][TestgamePane.gridRows];
            for(int i =0; i <gamePane.gridColumns; i ++){
                for(int j = 0; j<gamePane.gridRows; j++)
                {
                    TestgamePane.grid [i][j]= gamePane.grid[i][j];
                }
            }
          //gamePane.copyPane(gamePane);
       });

         //saveList.add(step, newSaveGrid);
         //System.out.print("SAVE LIST    " +saveList.get(step)[1][1]);
        newGamePane.getChildren().add(gamePane);
        mainPane.setCenter(newGamePane);
        
         FileOutputStream fos = new FileOutputStream(level + "save");
                    DataOutputStream dos = new DataOutputStream(fos);
                    // FIRST WRITE THE DIMENSIONS
                    dos.writeInt(gamePane.gridColumns);
                    dos.writeInt(gamePane.gridRows);
                    // AND NOW ALL THE CELL VALUES
                    for (int i = 0; i < gamePane.gridColumns; i++) {
                        for (int j = 0; j < gamePane.gridRows; j++) {
                            dos.writeInt(gamePane.grid[i][j]);
                        }
                    }
        

    }
    
    public void keyPressed(KeyEvent ke,BorderPane mainPane,String level) throws IOException
    {
        // GET THE KEY THAT WAS PRESSED IN ASSOCIATION WITH
        // THIS METHOD CALL.
    	//int keyCode = ke.getKeyCode();
       KeyCode keyCode = ke.getCode();

        // IS CONTROL-C PRESSED?
        if (keyCode == KeyCode.W|| keyCode == KeyCode.UP) 
            moveUp(mainPane,level);
        else if (keyCode == KeyCode.S || keyCode == KeyCode.DOWN) 
            moveDown(mainPane,level);    
         else if (keyCode == KeyCode.D|| keyCode == KeyCode.RIGHT)
             moveRight(mainPane,level);
         else if (keyCode == KeyCode.A || keyCode == KeyCode.LEFT)
            moveLeft(mainPane,level);
            
            
            
            }
    
    
    
    public void moveRight(BorderPane mainPane, String level) throws IOException{
      int col = 0, row = 0;  
                 for(int i =0; i <gamePane.gridColumns; i ++){
                        for(int j = 0; j<gamePane.gridRows; j++)
                                if(gamePane.grid[i][j]==4)
                                {col = i;
                                row= j;}
                                
                 }
                 if( gamePane.grid[col+1][row] ==0 || gamePane.grid[(int) col+1][(int) row] ==3){
                 gamePane.grid[(int) col][(int) row]=0;
                 gamePane.grid[(int) col+1][(int) row] = 4;
                 play(SOKOBAN_DATA_DIR+"walk.wav");
                 }
                 
                 else if(gamePane.grid[(int) col+1][(int) row]==2 && (gamePane.grid[(int) col+2][(int) row] == 0 || gamePane.grid[(int) col+2][(int) row] == 3))
                {
                    gamePane.grid[(int) col +1 ][(int) row] =4;
                    gamePane.grid[(int) col +2 ][(int) row] = 2;
                    gamePane.grid[(int) col ][(int) row] = 0;
                  play(SOKOBAN_DATA_DIR+"boxMove.wav");
             }
                 
                 if(gamePane.grid[col][row]==0 && winGrid[col][row]==3)
                     gamePane.grid[col][row] =3;
                 

                 check(mainPane, level);
    }
     public void moveUp(BorderPane mainPane,String level) throws IOException{
               int col = 0, row = 0;  
                 for(int i =0; i <gamePane.gridColumns; i ++){
                        for(int j = 0; j<gamePane.gridRows; j++)
                                if(gamePane.grid[i][j]==4)
                                {col = i;
                                row= j;}
                                
                 }
                 if( gamePane.grid[col][row-1] ==0 || gamePane.grid[(int) col][(int) row-1] ==3){
                 gamePane.grid[(int) col][(int) row]=0;
                 gamePane.grid[(int) col][(int) row-1] = 4;
                 play(SOKOBAN_DATA_DIR+"walk.wav");
                 }
                 
                   else if(gamePane.grid[(int) col][(int) row-1]==2 && (gamePane.grid[(int) col][(int) row-2]== 0 || gamePane.grid[(int) col][(int) row-2] == 3))
                {
                    gamePane.grid[(int) col ][(int) row-1] =4;
                    gamePane.grid[(int) col ][(int) row - 2] = 2;
                    gamePane.grid[(int) col ][(int) row] = 0;
                  play(SOKOBAN_DATA_DIR+"boxMove.wav");
             }
                 
                 
                 if(gamePane.grid[col][row]==0 && winGrid[col][row]==3)
                     gamePane.grid[col][row] =3;
      check(mainPane,level);
    }
      public void moveLeft(BorderPane mainPane,String level) throws IOException{
                int col = 0, row = 0;  
                 for(int i =0; i <gamePane.gridColumns; i ++){
                        for(int j = 0; j<gamePane.gridRows; j++)
                                if(gamePane.grid[i][j]==4)
                                {col = i;
                                row= j;}
                                
                 }
                 if( gamePane.grid[col-1][row] ==0 || gamePane.grid[(int) col-1][(int) row] ==3){
                 gamePane.grid[(int) col][(int) row]=0;
                 gamePane.grid[(int) col-1][(int) row] = 4;
                 play(SOKOBAN_DATA_DIR+"walk.wav");
                 }
                 
                  else if(gamePane.grid[(int) col-1][(int) row]==2 && (gamePane.grid[(int) col-2][(int) row] == 0 || gamePane.grid[(int) col-2][(int) row] == 3))
                {
                    gamePane.grid[(int) col -1][(int) row] = 4;
                    gamePane.grid[(int) col -2][(int) row ] = 2;
                    gamePane.grid[(int) col ][(int) row] = 0;
                  play(SOKOBAN_DATA_DIR+"boxMove.wav");
             }
                 
                 if(gamePane.grid[col][row]==0 && winGrid[col][row]==3)
                     gamePane.grid[col][row] =3;
        check(mainPane,level);
    }
       public void moveDown(BorderPane mainPane, String level) throws IOException{
                 int col = 0, row = 0;  
                 for(int i =0; i <gamePane.gridColumns; i ++){
                        for(int j = 0; j<gamePane.gridRows; j++)
                                if(gamePane.grid[i][j]==4)
                                {col = i;
                                row= j;}
                                
                 }
                 if( gamePane.grid[col][row+1] ==0 || gamePane.grid[(int) col][(int) row+1] ==3){
                 gamePane.grid[(int) col][(int) row]=0;
                 gamePane.grid[(int) col][(int) row+1] = 4;
                 play(SOKOBAN_DATA_DIR+"walk.wav");
                 }
                 
                  else if(gamePane.grid[(int) col][(int) row+1]==2 && (gamePane.grid[(int) col][(int) row+2] == 0 || gamePane.grid[(int) col][(int) row+2] == 3))
                {
                    gamePane.grid[(int) col ][(int) row+1] =4;
                    gamePane.grid[(int) col ][(int) row + 2] = 2;
                    gamePane.grid[(int) col ][(int) row] = 0;
                  play(SOKOBAN_DATA_DIR+"boxMove.wav");
             }
                 
                 if(gamePane.grid[col][row]==0 && winGrid[col][row]==3)
                     gamePane.grid[col][row] =3;
        check(mainPane, level);
    }
       
       public void check(BorderPane mainPane, String level) throws FileNotFoundException, IOException{
           
                         for(int i =0; i <gamePane.gridColumns; i ++){
                for(int j = 0; j<gamePane.gridRows; j++)
                {

                    if(winGrid[i][j]== 3 && gamePane.grid[i][j]==0)
                        gamePane.grid[i][j] = 3;
                }
              }
           //   mainPane.getChildren().retainAll(gamePane);
            gamePane.repaint();
    
            if(Arrays.deepEquals(gamePane.grid, saveList.get(step))){
                  System.out.print("SAME   ");
                  //System.out.
            }
            else
            {
                  System.out.print("NOT SAME    ");
                           

                           for(int i =0; i <gamePane.gridColumns; i ++){
                                for(int j = 0; j<gamePane.gridRows; j++){
                                    newSaveGrid[i][j] = gamePane.grid[i][j];
                                }
                                    }
                           saveList.add(step, newSaveGrid);
                            step++;
            }

            
             int winPoint= 0;
             boolean lose = false;
             int btotalWinPoint = totalWinPoint;
            for(int i =0; i <gamePane.gridColumns; i ++){
                for(int j = 0; j<gamePane.gridRows; j++)
                {
                    if(winGrid[i][j] == 3 && gamePane.grid[i][j]==2 )
                        winPoint++;
                    if(winGrid[i][j]== 3 && gamePane.grid[i][j]==0)
                        gamePane.grid[i][j] = 3;
                 
                    
                    
                    
                    if(gamePane.grid[i][j]==2 && winGrid[i][j] != 3 &&((gamePane.grid[i][j+1]==1 && gamePane.grid[i+1][j]==1) || (gamePane.grid[i][j-1]== 1 &&gamePane.grid[i-1][j]==1)||
                            (gamePane.grid[i+1][j]== 1 &&gamePane.grid[i][j-1]==1)||(gamePane.grid[i-1][j]== 1 &&gamePane.grid[i][j+1]==1)))
                    {
                      System.out.println("YOU LOSE" );
                             Stage dialogStage = new Stage();
                      dialogStage.initModality(Modality.WINDOW_MODAL);
                      //dialogStage.initOwner(primaryStage);
                      BorderPane exitPane = new BorderPane();
                      HBox optionPane = new HBox();
                       optionPane.setAlignment(Pos.CENTER);
                      Button okButton = new Button("OK");
                      optionPane.setSpacing(175.0);
                      optionPane.getChildren().addAll(okButton);
        
                      Label exitLabel = new Label("YOU LOSE!");
                      exitPane.setCenter(exitLabel);
                      exitPane.setBottom(optionPane);
                      Scene scene = new Scene(exitPane, 250, 100);
                      dialogStage.setScene(scene);
                      dialogStage.show();
                      okButton.setOnAction(e -> {
            dialogStage.close();
        });
                    }
                    }
          }
            System.out.println(winPoint+ "  total  " + btotalWinPoint);
            if(btotalWinPoint == winPoint){
                System.out.println("you win");
               System.out.println("YOU WIN" );
                             Stage dialogStage = new Stage();
                      dialogStage.initModality(Modality.WINDOW_MODAL);
                      //dialogStage.initOwner(primaryStage);
                      BorderPane exitPane = new BorderPane();
                      HBox optionPane = new HBox();
                       optionPane.setAlignment(Pos.CENTER);
                      Button okButton = new Button("OK");
                      optionPane.setSpacing(175.0);
                      optionPane.getChildren().addAll(okButton);
        
                      Label exitLabel = new Label("YOU WIN!");
                      exitPane.setCenter(exitLabel);
                      exitPane.setBottom(optionPane);
                      Scene scene = new Scene(exitPane, 250, 100);
                      dialogStage.setScene(scene);
                      dialogStage.show();
                       okButton.setOnAction(e -> {
            dialogStage.close();
        });
                      
                      
                       
                
                
            }
            TestgamePane.gridColumns = gamePane.gridColumns;
            TestgamePane.gridRows = gamePane.gridRows;
           // System.out.println(TestgamePane.gridColumns);
            TestgamePane.grid = new int[TestgamePane.gridColumns][TestgamePane.gridRows];
            for(int i =0; i <gamePane.gridColumns; i ++){
                for(int j = 0; j<gamePane.gridRows; j++)
                {
                    TestgamePane.grid [i][j]= gamePane.grid[i][j];
                }
            }
        //newGamePane.getChildren().add(gamePane);
       mainPane.setCenter(gamePane);
               
       
       FileOutputStream fos = new FileOutputStream( level + "save.sok");
                    DataOutputStream dos = new DataOutputStream(fos);
                    // FIRST WRITE THE DIMENSIONS
                    dos.writeInt(gamePane.gridColumns);
                    dos.writeInt(gamePane.gridRows);
                    // AND NOW ALL THE CELL VALUES
                    for (int i = 0; i < gamePane.gridColumns; i++) {
                        for (int j = 0; j < gamePane.gridRows; j++) {
                            dos.writeInt(gamePane.grid[i][j]);
                        }
                    }

        

       } 

    
    
    
    public static void play(String filename)
{
    try
    {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(filename)));
        clip.start();
    }
    catch (Exception exc)
    {
        exc.printStackTrace(System.out);
    }
}
    
 
    
    public void respondToUndoRequest(BorderPane mainPane){
       // System.out.println(totalWinPoint);
        if(step >=1)
            step= step-1;
        if(step==0)
            step = 0;
        System.out.println("STEP     " +step);

           for(int i =0; i <gamePane.gridColumns; i ++){
                for(int j = 0; j<gamePane.gridRows; j++){
                    gamePane.grid[i][j] = saveList.get(step-1)[i][j];
                   }
            }
        gamePane.grid = Arrays.copyOf(saveList.get(step), saveList.get(step).length);
           
        gamePane.repaint();
 
        Pane newPane = new Pane();
        newPane.getChildren().add(gamePane);
        mainPane.setCenter(newPane);
        
    }
    /**
     * This method responds to when the user presses the new game method.
     */
    public void respondToNewGameRequest() {
        SokobanGameStateManager gsm = ui.getGSM();
        gsm.startNewGame();
    }

    /**
     * This method responds to when the user requests to exit the application.
     *
     * @param window The window that the user has requested to close.
     */
    public void respondToExitRequest(Stage primaryStage) {
        // ENGLIS IS THE DEFAULT
        String options[] = new String[]{"Yes", "No"};
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        options[0] = props.getProperty(SokobanPropertyType.DEFAULT_YES_TEXT);
        options[1] = props.getProperty(SokobanPropertyType.DEFAULT_NO_TEXT);
        String verifyExit = props.getProperty(SokobanPropertyType.DEFAULT_EXIT_TEXT);

        // NOW WE'LL CHECK TO SEE IF LANGUAGE SPECIFIC VALUES HAVE BEEN SET
        if (props.getProperty(SokobanPropertyType.YES_TEXT) != null) {
            options[0] = props.getProperty(SokobanPropertyType.YES_TEXT);
            options[1] = props.getProperty(SokobanPropertyType.NO_TEXT);
            verifyExit = props.getProperty(SokobanPropertyType.EXIT_REQUEST_TEXT);
        }

        // FIRST MAKE SURE THE USER REALLY WANTS TO EXIT
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        HBox optionPane = new HBox();
        Button yesButton = new Button(options[0]);
        Button noButton = new Button(options[1]);
        optionPane.setSpacing(175.0);
        optionPane.getChildren().addAll(yesButton, noButton);
        
        Label exitLabel = new Label(verifyExit);
        exitPane.setCenter(exitLabel);
        exitPane.setBottom(optionPane);
        Scene scene = new Scene(exitPane, 250, 100);
        dialogStage.setScene(scene);
        dialogStage.show();
        // WHAT'S THE USER'S DECISION?
        yesButton.setOnAction(e -> {
            // YES, LET'S EXIT
            System.exit(0);
        });
        noButton.setOnAction(e -> {
            dialogStage.close();
        });

    }

}
    class GridRenderer extends Canvas {
        private GraphicsContext gc;
         private GraphicsContext ec;
        // PIXEL DIMENSIONS OF EACH CELL
        int cellWidth;
        int cellHeight;
        public int gridColumns;
        public int gridRows;
        public  int grid[][];
        int x;
        int y;
        int sokobanX;
        int sokobanY;
        double w ;
        double h ;
        
        // images
        Image wallImage = new Image("file:images/wall.png");
        Image boxImage = new Image("file:images/box.png");
        Image placeImage = new Image("file:images/place.png");
        Image sokobanImage = new Image("file:images/Sokoban.png");


        /**
         * Default constructor.
         */
        public void copyPane(GridRenderer target){
         this.gc = target.gc;

        this.gridColumns = target.gridColumns;
        this.gridRows = target.gridRows;
        this.grid = target.grid;
        }
        public int getColumns()
        {
            return gridColumns;
        }
        
        public int getRows(){
            return gridRows;
        }
        public GridRenderer() {
            this.setWidth(815);
            this.setHeight(717);
            
            repaint();
            
        }

        public int getX()
        {
            return sokobanX;
        }
        public int getY(){
            return sokobanY;
        }
        
        public double getW(){
            return w;
        }
        public double getH(){
            return h;
        }
        
        public GraphicsContext getgc(){
            return gc;
        }
        public void repaint() {
            gc = this.getGraphicsContext2D();
            gc.clearRect(0, 0, this.getWidth(), this.getHeight());
            
            ec = this.getGraphicsContext2D();
            ec.clearRect(0, 0, this.getWidth(), this.getHeight());

            // CALCULATE THE GRID CELL DIMENSIONS
            w = this.getWidth() / gridColumns;
            h = this.getHeight() / gridRows;

            gc = this.getGraphicsContext2D();
            ec = this.getGraphicsContext2D();
            // NOW RENDER EACH CELL
            x = 0;
            y = 0;
            for (int i = 0; i < gridColumns; i++) {
                y = 0;
                for (int j = 0; j < gridRows; j++) {
                    // DRAW THE CELL
                    gc.setFill(Color.LIGHTBLUE);
                    gc.strokeRoundRect(x, y, w, h, 10, 10);

                    switch (grid[i][j]) {
                        case 0:
                            gc.strokeRoundRect(x, y, w, h, 10, 10);
                            break;
                        case 1:
                            gc.drawImage(wallImage, x, y, w, h);
                            break;
                        case 2:
                            gc.drawImage(boxImage, x, y, w, h);
                            break;
                        case 3:
                             ec.drawImage(placeImage, x, y, w, h);
                             //gc.strokeRoundRect(x, y, w, h, 10, 10);
                            break;
                        case 4:
                            gc.drawImage(sokobanImage, x, y, w, h);
                            sokobanX=x;
                            sokobanY=y;
                            break;
                    }
                        y+=h;
                }
                x+=w;
            }
            
        }

    }
