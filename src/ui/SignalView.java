package ui;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class SignalView extends LineChart<Number,Number>  {

    private Axis<Number> Xaxis;

    public Axis<Number> getYaxis() {
        return Yaxis;
    }

    private Axis<Number> Yaxis;


    public void setXaxis(Axis<Number> xaxis) {
        Xaxis = xaxis;
    }

    public void setYaxis(Axis<Number> yaxis) {
         Yaxis= yaxis;
    }

    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
    }


    public void updateData(double[] buffer ){
        getData().removeAll(this.getData());
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("entré");

        for (int i=0;i <buffer.length;i++){
            dataSeries1.getData().add(new XYChart.Data<>( i, 1/(i+1)));
        }
        getData().add(dataSeries1);

    }

    public Axis<Number> getXaxis() {
        return Xaxis;
    }
}
