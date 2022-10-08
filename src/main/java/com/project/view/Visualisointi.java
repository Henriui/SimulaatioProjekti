package com.project.view;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Visualisointi {
    
    private double i = 0;
	private double j = 10;
	
	private GraphicsContext gc;
	private Canvas cnv;

	

    public static final double D = 20;  // diameter.

	int asiakasLkm = 0;

	public Visualisointi(Canvas cnv) {
		this.cnv = cnv;
		this.gc = cnv.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	

	public void tyhjennaNaytto() {
		
        
	}
	
	public void uusiAsiakas() {
		gc.setFill(Color.RED);
		gc.fillOval(i,j,10,10);
		
		i = i + 10;
		if (i >= cnv.getWidth()) {i = 0; j+=10;}
        
	}

    public void asiakasLiikkuu(Circle asd, String tyyppi){
        Circle circle = new Circle(100);
        circle.setRadius(200);
       
        circle.setFill(Color.BLUE);

        switch(tyyppi){
            case "myynti":
                Path path = new Path();
                path.getElements().add(new MoveTo(485,50));
                path.getElements().add(new LineTo(63,50));
                path.getElements().add(new LineTo(63,260));
            
                PathTransition transition = new PathTransition();
                transition.setNode(asd);
                transition.setDuration(Duration.seconds(0.5));
                transition.setPath(path);
                transition.setCycleCount(PathTransition.INDEFINITE);
                transition.play();
                break;
            case "netti":
                Path path2 = new Path();
                path2.getElements().add(new MoveTo(485,50));
                path2.getElements().add(new LineTo(183,50));
                path2.getElements().add(new LineTo(183,260));
            
                PathTransition transition2 = new PathTransition();
                transition2.setNode(asd);
                transition2.setDuration(Duration.seconds(0.5));
                transition2.setPath(path2);
                transition2.setCycleCount(PathTransition.INDEFINITE);
                transition2.play();
                break;
            case "liittymä":
                Path path3 = new Path();
                path3.getElements().add(new MoveTo(485,50));
                path3.getElements().add(new LineTo(300,50));
                path3.getElements().add(new LineTo(300,260));
            
                PathTransition transition3 = new PathTransition();
                transition3.setNode(asd);
                transition3.setDuration(Duration.seconds(0.5));
                transition3.setPath(path3);
                transition3.setCycleCount(PathTransition.INDEFINITE);
                transition3.play();
                break;
            case "laskutus":
                Path path4 = new Path();
                path4.getElements().add(new MoveTo(485,50));
                path4.getElements().add(new LineTo(418,50));
                path4.getElements().add(new LineTo(418,260));
            
                PathTransition transition4 = new PathTransition();
                transition4.setNode(asd);
                transition4.setDuration(Duration.seconds(0.5));
                transition4.setPath(path4);
                transition4.setCycleCount(PathTransition.INDEFINITE);
                transition4.play();
                break;
        }
       
    }

    public void asd(){
        gc.setLineWidth(1.0);

       

        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                    new KeyValue(x, 0),
                    new KeyValue(y, 0)
            ),
            new KeyFrame(Duration.seconds(3),
                    new KeyValue(x, cnv.getWidth() - D),
                    new KeyValue(y, cnv.getHeight() - D)
            )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
           
                gc.setFill(Color.web("#2F0743"));
                gc.fillRect(0, 0, cnv.getWidth(), cnv.getHeight());
                gc.setFill(Color.FORESTGREEN);
                gc.fillOval(
                    x.doubleValue(),
                    y.doubleValue(),
                    D,
                    D
                );
            }
           
        };
        timer.start();
        timeline.play();
       
    }
}
