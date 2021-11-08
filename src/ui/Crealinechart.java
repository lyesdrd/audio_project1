package ui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Crealinechart {


    public static LineChart create(double[] buff){
        LineChart<Number,Number> lineChart= new LineChart<>(new NumberAxis(),new NumberAxis());
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("entré");
        for (int i=0;i < buff.length;i++){

            dataSeries1.getData().add(new XYChart.Data<>( i, buff[i]*32768.0));}
        lineChart.getData().add(dataSeries1);
        return lineChart;
    }
}
