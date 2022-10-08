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
	
	private GraphicsContext gc[] = new GraphicsContext[300];
	private Canvas cnv;
    int ii = 0;
    private static double nopeus;
	

    public static final double D = 20;  // diameter.

	int asiakasLkm = 0;

	public Visualisointi(Canvas cnv) {
		this.cnv = cnv;
		this.gc[1] = cnv.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	

	public void tyhjennaNaytto() {
		
        
	}
	
	public void uusiAsiakas(int ii) {
		gc[ii].setFill(Color.RED);
		gc[ii].fillOval(i,j,10,10);
		
		i = i + 10;
		if (i >= cnv.getWidth()) {i = 0; j+=10;}
        
	}

    public void visuaalinenNopeus(long viive){
        nopeus = viive;
    }

    public void asiakasLiikkuu(Circle asd, String tyyppi){
        Circle circle = new Circle(100);
        circle.setRadius(200);
       
        circle.setFill(Color.BLUE);

        switch(tyyppi){
            case "Pmyynti":
                Path path = new Path();
                path.getElements().add(new MoveTo(485,50));
                path.getElements().add(new LineTo(63,50));
                path.getElements().add(new LineTo(63,260));
            
                PathTransition transition = new PathTransition();
                transition.setNode(asd);
                transition.setDuration(Duration.millis(nopeus + 500));
                transition.setPath(path);
                transition.setCycleCount(1);
                transition.play();
                break;
            case "Pnetti":
                Path path2 = new Path();
                path2.getElements().add(new MoveTo(485,50));
                path2.getElements().add(new LineTo(183,50));
                path2.getElements().add(new LineTo(183,260));
            
                PathTransition transition2 = new PathTransition();
                transition2.setNode(asd);
                transition2.setDuration(Duration.millis(nopeus + 500));
                transition2.setPath(path2);
                transition2.setCycleCount(1);
                transition2.play();
                break;
            case "Pliittymä":
                Path path3 = new Path();
                path3.getElements().add(new MoveTo(485,50));
                path3.getElements().add(new LineTo(300,50));
                path3.getElements().add(new LineTo(300,260));
            
                PathTransition transition3 = new PathTransition();
                transition3.setNode(asd);
                transition3.setDuration(Duration.millis(nopeus + 500));
                transition3.setPath(path3);
                transition3.setCycleCount(1);
                transition3.play();
                break;
            case "Plaskutus":
                Path path4 = new Path();
                path4.getElements().add(new MoveTo(485,50));
                path4.getElements().add(new LineTo(418,50));
                path4.getElements().add(new LineTo(418,260));
            
                PathTransition transition4 = new PathTransition();
                transition4.setNode(asd);
                transition4.setDuration(Duration.millis(nopeus + 500));
                transition4.setPath(path4);
                transition4.setCycleCount(1);
                transition4.play();
                break;

            case "Ymyynti":
                Path path5 = new Path();
                path5.getElements().add(new MoveTo(565,50));
                path5.getElements().add(new LineTo(977,50));
                path5.getElements().add(new LineTo(977,260));
            
                PathTransition transition5 = new PathTransition();
                transition5.setNode(asd);
                transition5.setDuration(Duration.millis(nopeus + 500));
                transition5.setPath(path5);
                transition5.setCycleCount(1);
                transition5.play();
                break;
            case "Ynetti":
                Path path6 = new Path();
                path6.getElements().add(new MoveTo(565,50));
                path6.getElements().add(new LineTo(857,50));
                path6.getElements().add(new LineTo(857,260));
            
                PathTransition transition6 = new PathTransition();
                transition6.setNode(asd);
                transition6.setDuration(Duration.millis(nopeus + 500));
                transition6.setPath(path6);
                transition6.setCycleCount(1);
                transition6.play();
                break;
            case "Yliittymä":
                Path path7 = new Path();
                path7.getElements().add(new MoveTo(565,50));
                path7.getElements().add(new LineTo(740,50));
                path7.getElements().add(new LineTo(740,260));
            
                PathTransition transition7 = new PathTransition();
                transition7.setNode(asd);
                transition7.setDuration(Duration.millis(nopeus + 500));
                transition7.setPath(path7);
                transition7.setCycleCount(1);
                transition7.play();
                break;
            case "Ylaskutus":
                Path path8 = new Path();
                path8.getElements().add(new MoveTo(565,50));
                path8.getElements().add(new LineTo(622,50));
                path8.getElements().add(new LineTo(622,260));
            
                PathTransition transition8 = new PathTransition();
                transition8.setNode(asd);
                transition8.setDuration(Duration.millis(nopeus + 500));
                transition8.setPath(path8);
                transition8.setCycleCount(1);
                transition8.play();
                break;
        }
       
    }

    public void visuaalinenJono(int tyyppi){

    }
    public void asd(){
        gc[1].setLineWidth(1.0);

       

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
           
                gc[1].setFill(Color.web("#2F0743"));
                gc[1].fillRect(0, 0, cnv.getWidth(), cnv.getHeight());
                gc[1].setFill(Color.FORESTGREEN);
                gc[1].fillOval(
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
