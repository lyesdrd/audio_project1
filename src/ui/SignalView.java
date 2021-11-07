package ui;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class SignalView extends LineChart<Number,Number> /*implements Runnable*/ {

    private Axis<Number> Xaxis;
    private Axis<Number> Yaxis;
    LineChart lineChart;

    public void setXaxis(Axis<Number> xaxis) {
        Xaxis = xaxis;
    }

    public void setYaxis(Axis<Number> yaxis) {
         Yaxis= yaxis;
    }

    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
    }

    public void makeChart(){
        this.lineChart = new LineChart(Xaxis, Yaxis);
    }

    public void updateData(double[] buffer ){
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("entré");

        for (int i=0;i <buffer.length;i++){
            dataSeries1.getData().add(new XYChart.Data( i, buffer[i]));
        }
        lineChart.getData().clear();
        lineChart.getData().add(dataSeries1);

    }

/*
    @Override
    public void run() {
        AnimationTimer tim =new AnimationTimer() {
            @Override
            public void handle(long l) {
                as
                updateData(as.getInputSignal().getSampleBuffer());
            }
        };
    }*/
}
