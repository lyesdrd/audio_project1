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
        getGraphicsContext2D().setFill(Color.BLACK);
        getGraphicsContext2D().fillText("Signal level",100,120);
        getGraphicsContext2D().setFill(Color.GREY);
        getGraphicsContext2D().fillRect(100, 550 - 400, 52, 400);


    }

    public void setLevel(double level){
        this.level=level*400;
    }

    public void update(double Level) {
        getGraphicsContext2D().clearRect(100, 550 - this.level, 50, this.level);
        getGraphicsContext2D().setFill(Color.GREY);
        getGraphicsContext2D().fillRect(100, 550 - 400, 52, 400);
        setLevel(Level);
        if (this.level > 280) {
            getGraphicsContext2D().setFill(Color.GREEN);
            getGraphicsContext2D().fillRect(101, 550 - 200, 50, 200);
            getGraphicsContext2D().setFill(Color.ORANGE);
            getGraphicsContext2D().fillRect(101, 550 - 280, 50, 80);
            getGraphicsContext2D().setFill(Color.RED);
            getGraphicsContext2D().fillRect(101, 550 - level, 50, level-280);}
            else{
                    if (level > 200){
                        getGraphicsContext2D().setFill(Color.GREEN);
                        getGraphicsContext2D().fillRect(101, 550 - 200, 50, 200);
                        getGraphicsContext2D().setFill(Color.ORANGE);
                        getGraphicsContext2D().fillRect(101, 550 - level, 50, level-200);
                    }
                            else{
                                getGraphicsContext2D().setFill(Color.GREEN);
                                getGraphicsContext2D().fillRect(101, 550 - this.level, 50, this.level);

                            }
                }
    }


}
