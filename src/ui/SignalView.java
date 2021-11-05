package ui;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class SignalView extends LineChart<Number,Number>  {

    private Axis<Number> Xaxis;
    private Axis<Number> Yaxis;

    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
    }

    public void updateData(Axis<Number> newax){
        this.Xaxis= newax;
    }

    public LineChart<Number,Number> makeChart(){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("No of employees");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Revenue per employee");

        LineChart lineChart = new LineChart(xAxis, yAxis);

        return lineChart;
    }





}
