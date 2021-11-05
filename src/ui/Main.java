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
import java.util.Arrays;


public class Main extends Application {
    private AudioProcessor as;
    private String inline;
    private String outline;

    public void setInline(String inline) {
        this.inline = inline;
        System.out.println(this.inline);
    }

    public void setOutline(String outline) {
        this.outline = outline;
        System.out.println(this.outline);
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
             Button buttonstop = new Button("stop");
             ToolBar tb = new ToolBar(buttonstart,buttonstop, new Separator(), new Label("input mixer"));
             ComboBox<String> cbinput = new ComboBox<>();
                Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> cbinput.getItems().add(e.getName())); // on remplit la combo box avec les differents mixers disponibles et l'utilisateur devra choisir un mixeur d'input
                        tb.getItems().add(cbinput);
             Button buttonvaliderinput = new Button("valider");
                buttonvaliderinput.setOnAction(event -> setInline(cbinput.getValue()));
                        tb.getItems().add(buttonvaliderinput);
                        tb.getItems().add(new Separator());

                        tb.getItems().add(new Label("output mixer"));
             ComboBox<String> cboutput = new ComboBox<>();
                Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> cboutput.getItems().add(e.getName())); //on remplit la combo box avec les differents mixers disponibles et l'utilisateur devra choisir un mixeur d'output
                        tb.getItems().add(cboutput); //on ajoute cette combo box à la fenêtre
             Button buttonvalideroutput = new Button("valider");
                buttonvalideroutput.setOnAction(event -> setOutline(cboutput.getValue()));
                        tb.getItems().add(buttonvalideroutput);
                        tb.getItems().add(new Separator());


             buttonstart.setOnAction(event -> System.out.println("start" ));
             buttonstop.setOnAction(event -> AudioIO.stopAudioProcessing(as));
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

