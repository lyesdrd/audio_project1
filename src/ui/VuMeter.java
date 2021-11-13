package ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class VuMeter extends Canvas {

    private double level=0;


    public VuMeter() {
    }

    public VuMeter(double v, double v1) {
        super(v, v1);

    }

    public void setLevel(double level){
        this.level=level*400;
    }

    public void update(double Level) {
        getGraphicsContext2D().clearRect(100, 550 - this.level, 100, this.level);
        setLevel(Level);
        if (this.level > 280) {
            getGraphicsContext2D().setFill(Color.GREEN);
            getGraphicsContext2D().fillRect(100, 550 - 200, 100, 200);
            getGraphicsContext2D().setFill(Color.ORANGE);
            getGraphicsContext2D().fillRect(100, 550 - 280, 100, 80);
            getGraphicsContext2D().setFill(Color.RED);
            getGraphicsContext2D().fillRect(100, 550 - level, 100, level-280);}
            else{
                    if (level > 200){
                        getGraphicsContext2D().setFill(Color.GREEN);
                        getGraphicsContext2D().fillRect(100, 550 - 200, 100, 200);
                        getGraphicsContext2D().setFill(Color.ORANGE);
                        getGraphicsContext2D().fillRect(100, 550 - level, 100, level-200);
                    }
                            else{
                                getGraphicsContext2D().setFill(Color.GREEN);
                                getGraphicsContext2D().fillRect(100, 550 - this.level, 100, this.level);

                            }
                }
    }


}
