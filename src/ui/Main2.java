package ui;

import audio.AudioIO;
import audio.AudioIO2;
import audio.AudioProcessor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import thandler.ThreadHandler;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import java.util.Arrays;


import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class Main2 extends Application{

        private AudioProcessor as=new AudioProcessor();
        private String inline;
        private String outline;
        private int frameSize = 1024;
        private int sampleRate = 44100;
        private ThreadHandler tHand= new ThreadHandler();
        SignalView chart1 = new SignalView(new NumberAxis(),new NumberAxis("s",-1.,1.,0.1));
        VuMeter vu =new VuMeter(800,800);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                refresh();
            }
        };





        public void setAll(String inline,String outline,String framesize,String samplerate){
            this.inline= inline;
            this.outline= outline;
            this.sampleRate= Integer.parseInt(samplerate);
            System.out.println(sampleRate);
            this.frameSize= Integer.parseInt(framesize);
            try {
                this.tHand.setThreadSon(AudioIO2.createThread(inline,outline,sampleRate,frameSize,as));
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }




        @Override
        public void start(Stage primaryStage) {
            try {
                BorderPane root = new BorderPane();
                root.setTop(createToolbar());
                root.setBottom(createStatusbar());
                root.setCenter(createMainContent());
                root.setLeft(createLeftContent());
                root.setAlignment(root.getLeft(), Pos.CENTER);
                root.setAlignment(root.getCenter(), Pos.BOTTOM_LEFT);
                Scene scene = new Scene(root,1500,800);
                primaryStage.setScene(scene);
                primaryStage.setTitle("The JavaFX audio processor");
                primaryStage.show();
            } catch(Exception e) {e.printStackTrace();}
        }

        private Node createToolbar() throws LineUnavailableException {
            /**boutons gestions d'apli **/
            Button buttonstart = new Button("start");
            Button buttonstop = new Button("stop");
            ToolBar tb = new ToolBar(buttonstart,buttonstop, new Separator(), new Label("input mixer"));

            /**combo boxs */
            ComboBox<String> cbinput = new ComboBox<>();
            Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> cbinput.getItems().add(e.getName())); // on remplit la combo box avec les differents mixers disponibles et l'utilisateur devra choisir un mixeur d'input
            tb.getItems().add(cbinput);
            tb.getItems().add(new Separator());



            tb.getItems().add(new Label("output mixer"));
            ComboBox<String> cboutput = new ComboBox<>();
            Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> cboutput.getItems().add(e.getName())); //on remplit la combo box avec les differents mixers disponibles et l'utilisateur devra choisir un mixeur d'output
            tb.getItems().add(cboutput); //on ajoute cette combo box à la fenêtre
            tb.getItems().add(new Separator());

            /**text field */
            TextField framesize =new TextField("1024");
            tb.getItems().add(new Label("framesize"));
            tb.getItems().add(framesize);
            tb.getItems().add(new Separator());

            TextField samplerate =new TextField("44100");
            tb.getItems().add(new Label("samplerate"));
            tb.getItems().add(samplerate);
            tb.getItems().add(new Separator());

            /**boutons validation des choix */
           /* Button buttonvalider = new Button("valider");
            buttonvalider.setOnAction(event -> setAll(cbinput.getValue(),cboutput.getValue(),framesize.getText(),samplerate.getText()));
            tb.getItems().add(buttonvalider); */



            /**Action des boutons de gestion d'app */
            buttonstart.setOnAction(event -> {start(cbinput.getValue(),cboutput.getValue(),framesize.getText(),samplerate.getText()); });
            buttonstop.setOnAction(event -> stop(tHand,as));

            return tb;

        }

        private Node createStatusbar(){
            HBox statusbar = new HBox();
            statusbar.getChildren().addAll(new Label("Name:"), new TextField(" "));
            return statusbar;
        }

        private Node createMainContent(){
            Group g = new Group();  // ici en utilisant g.getChildren().add(...) vous pouvez ajouter tout element graphique souhaite de type Node
            g.getChildren().add(vu);
            return g;
        }

        private Node createLeftContent(){
            Group g = new Group();  // ici en utilisant g.getChildren().add(...) vous pouvez ajouter tout element graphique souhaite de type Node
            chart1.setTitle("Audio signal graph");
            g.getChildren().add(chart1);
            return g;
        }
        public void refresh(){
            chart1.updateData(as.getInputSignal().getSampleBuffer());
            vu.update(as.getInputSignal().getdBlevel());
            //on update les valeurs du graphque à l'aide du signal d'entré
        }


        public void stop(ThreadHandler t,AudioProcessor as){
            AudioIO2.stopAudioProcessing(tHand,as);
            timer.stop();
        }
        public void start(String cbinput,String cboutput,String framesize,String samplerate){
                setAll(cbinput,cboutput,framesize,samplerate);
                tHand.startThread(/*timer*/);
                timer.start();

    }
    }



