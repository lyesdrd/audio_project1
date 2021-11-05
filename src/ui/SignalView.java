package ui;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;

import javax.sound.sampled.*;

public class SignalView extends LineChart<Number,Number> {

    public SignalView(Axis<Number> axis, Axis<Number> axis1) {
        super(axis, axis1);
    }
}
