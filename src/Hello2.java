import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Hello2 extends Application {
    double[] buffer;

    public void setBuffer(double[] buffer) {
        this.buffer = buffer;
    }

    @Override
        public void start(Stage primaryStage) throws Exception{

             primaryStage.setTitle("Hello world");
             Group root = new Group();

             LineChart<Number,Number> lineChart= new LineChart<>(new NumberAxis(),new NumberAxis());
             XYChart.Series dataSeries1 = new XYChart.Series();
             dataSeries1.setName("entré");
             for (int i=0;i <15;i++){
                 dataSeries1.getData().add(new XYChart.Data<>( i, i*0.001));}
             lineChart.getData().add(dataSeries1);
             root.getChildren().add(lineChart);

             Pane pane = new Pane(root);
             Scene theScene = new Scene(pane, 600, 400,true);
             primaryStage.setScene(theScene);

             primaryStage.show();
             }


         public static void main(String[] args) {
             launch(args);
             }
}

