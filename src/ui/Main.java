package ui;

import audio.AudioIO;
import audio.AudioProcessor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import java.util.Arrays;


public class Main extends Application {
    private AudioProcessor as;
    private String inline;
    private String outline;
    private int frameSize ;
    private int sampleRate;

    public void setAll(String inline,String outline,String framesize,String samplerate){
        this.inline= inline;
        this.outline= outline;
        this.sampleRate= Integer.parseInt(samplerate);
        System.out.println(sampleRate);
        this.frameSize= Integer.parseInt(framesize);
    }

    public void setAs (AudioProcessor as) {
        this.as = as;
    }


        @Override
        public void start(Stage primaryStage) {
             try {
                 BorderPane root = new BorderPane();
                 root.setTop(createToolbar());
                 root.setBottom(createStatusbar());
                 root.setCenter(createMainContent());
                 Scene scene = new Scene(root,1500,800);
                 primaryStage.setScene(scene);
                 primaryStage.setTitle("The JavaFX audio processor");
                 primaryStage.show();
                 } catch(Exception e) {e.printStackTrace();}
             }

         private Node createToolbar(){

             Button buttonstart = new Button("start");
             Button buttoncontinue = new Button("continue");
             Button buttonstop = new Button("stop");
             ToolBar tb = new ToolBar(buttonstart,buttoncontinue,buttonstop, new Separator(), new Label("input mixer"));


             ComboBox<String> cbinput = new ComboBox<>();
                Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> cbinput.getItems().add(e.getName())); // on remplit la combo box avec les differents mixers disponibles et l'utilisateur devra choisir un mixeur d'input
                        tb.getItems().add(cbinput);
                        tb.getItems().add(new Separator());



                        tb.getItems().add(new Label("output mixer"));
             ComboBox<String> cboutput = new ComboBox<>();
                Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> cboutput.getItems().add(e.getName())); //on remplit la combo box avec les differents mixers disponibles et l'utilisateur devra choisir un mixeur d'output
                        tb.getItems().add(cboutput); //on ajoute cette combo box à la fenêtre
                        tb.getItems().add(new Separator());


             TextField framesize =new TextField("");
                        tb.getItems().add(new Label("framesize"));
                        tb.getItems().add(framesize);
                        tb.getItems().add(new Separator());

             TextField samplerate =new TextField("");
                        tb.getItems().add(new Label("framerate"));
                        tb.getItems().add(samplerate);
                        tb.getItems().add(new Separator());

             Button buttonvalider = new Button("valider");
                buttonvalider.setOnAction(event -> setAll(cbinput.getValue(),cboutput.getValue(),framesize.getText(),samplerate.getText()));
                        tb.getItems().add(buttonvalider);

             buttonstart.setOnAction(event -> {
                 try {
                     setAs(AudioIO.startAudioProcessing(this.inline,this.outline,this.sampleRate,this.frameSize));
                 } catch (LineUnavailableException e) {
                     e.printStackTrace();
                 }
             });
             buttonstop.setOnAction(event -> AudioIO.stopAudioProcessing(as));
             buttoncontinue.setOnAction(event -> AudioIO.continuet(as));

             return tb;

        }

         private Node createStatusbar(){
             HBox statusbar = new HBox();
             statusbar.getChildren().addAll(new Label("Name:"), new TextField(" "));
             return statusbar;
             }

         private Node createMainContent(){
             Group g = new Group();
             // ici en utilisant g.getChildren().add(...) vous pouvez ajouter tout ´el´ement graphique souhait´e de type Node
             return g;
             }

 }

