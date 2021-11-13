package ui;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;


import javax.sound.sampled.*;
import java.util.ArrayList;

public class SignalView extends LineChart<Number,Number>  {


    private  XYChart.Series dataSeries;
    private double[] range=new double[201];


    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
        dataSeries = new Series();
        dataSeries.setName("entré");
        getData().add(this.dataSeries);
        setHorizontalGridLinesVisible(false);
        setAnimated(false);

    }

    @Override
    protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
    }

    public void updateData(double[] buffer ){
        dataSeries.getData().clear();
        for (int i=0;i <buffer.length;i++){
            dataSeries.getData().add(new XYChart.Data<>( i, buffer[i]));
        }


    }

}
