package com.project.view;

import java.io.IOException;
import com.project.MainApp;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.UserParametrit;
import animatefx.animation.ZoomIn;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NewSimulationController implements INewSimulationControllerVtoM, INewSimulationControllerMtoV {
    @FXML
    private AnchorPane backGround;
    @FXML
    private AnchorPane visuaalinenTausta;
    @FXML
    private Label yksityisJonossa;
    @FXML
    private Label YritysJonossa;
    @FXML
    private Label yksityisPalvelupisteita;
    @FXML
    private Label palvelupisteellaYksityis;
    @FXML
    private Label yritysPalvelupisteita;
    @FXML
    private Label palvelupisteellaYritys;
    @FXML
    private Label suorittaneetMaara;
    @FXML
    private Circle liikkuu;
    @FXML
    private Pane asd;

    private double xOffset = 0;
    private double yOffset = 0;
    private static boolean open = false;
    Moottori m;

    @FXML
	private Canvas visu;
    @FXML
	private Canvas ani;
    private Visualisointi visualisointi;

    public static final double W = 200; // canvas dimensions.
    public static final double H = 200;

    public static final double D = 20;  // diameter.
    
    
    @FXML
    public void initialize() {
        new animatefx.animation.ZoomIn();
        ZoomIn trans1 = new ZoomIn(backGround);
        new animatefx.util.ParallelAnimationFX(trans1).play();

        System.out.println("Start");
		
		visualisointi = new Visualisointi(visu);
		
        //asd();
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }

    @FXML
    public void aloitaSimulaatio() {
        UserParametrit uP = UserParametrit.getInstance();
        Trace.setTraceLevel(Level.INFO);
        m = new OmaMoottori(this);
        m.setViive(0);
        m.setSimulointiaika(uP.getSimulaationAika() * 3600);
        ((Thread) m).start();
    }

    public void ilmoitaJononKoko(int koko) {
        String tulos = String.valueOf(koko);
        yksityisJonossa.setText(tulos);
    }

    @FXML
    public void setSuureet() throws IOException {
        if (!open) {
            Scene scene = new Scene(loadFXML("Parametrit"));
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("Suureiden asetukset");
            stage.initStyle(StageStyle.TRANSPARENT);

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            // Can move window when mouse down and drag.

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            stage.show();
            open = true;
        }
    }

    public void popupOpen(boolean isOpen) {
        open = isOpen;
    }

    // Finds fxml file from the resources folder.

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // LIUTA TESTI METHODEITA -> nää vois siivota järkevämmäks ehkä?
    // Jokanen tekee erillisen runin? jne jne
    // Check: SimulaationSuureet.java updateSuureet()
    @Override
    public void ilmoitaJononKoko(int yksityis, int yritys) {
        Platform.runLater(new Runnable() {
            public void run() {
                Platform.runLater(()->visualisointi.uusiAsiakas());
                Platform.runLater(()->asd());
                YritysJonossa.setText(String.valueOf(yritys));
                yksityisJonossa.setText(String.valueOf(yksityis));
            }
        });

    }

    @Override
    public void ilmoitaPalveluPisteet(int yritys, int yksityis) {
        Platform.runLater(new Runnable() {
            public void run() {
                
                yksityisPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yksityis));
                yritysPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yritys));
            }
        });
    }

    @Override
    public void asPPMaara(int yksityis, int yritys) {
        Platform.runLater(new Runnable() {
            public void run() {
                asd();
                palvelupisteellaYritys.setText("As. Oleskellut: " + String.valueOf(yritys));
                palvelupisteellaYksityis.setText("As. Oleskellut: " + String.valueOf(yksityis));
            }
        });
    }

    @Override
    public void ulkonaAs(int maara) {
        Platform.runLater(new Runnable() {
            public void run() {
                suorittaneetMaara.setText("Ulkona: " + String.valueOf(maara));
            }
        });
    }

    @Override
    public void hidastaSimulaatiota() {
        m.setViive(m.getViive() + 5);
    }

    @Override
    public void nopeutaSimulaatiota() {
        if (m.getViive() > 0) {
            m.setViive(m.getViive() - 5);
        }
    }

    public void asd(){
        
        //Circle[] circle = new Circle[3]; 
        //for(int i = 0; i < circle.length; i++){
            Circle circle = new Circle(10);           // initialize circles with radius of 50
            circle.setFill(Color.PINK);
            visuaalinenTausta.getChildren().add(circle);
            visualisointi.asiakasLiikkuu(circle, "laskutus");

            Circle circle2 = new Circle(10);           // initialize circles with radius of 50
            circle2.setFill(Color.RED);
            visuaalinenTausta.getChildren().add(circle2);
            visualisointi.asiakasLiikkuu(circle2, "myynti");

            Circle circle3 = new Circle(10);           // initialize circles with radius of 50
            circle3.setFill(Color.BLUE);
            visuaalinenTausta.getChildren().add(circle3);
            visualisointi.asiakasLiikkuu(circle3, "netti");

            Circle circle4 = new Circle(10);           // initialize circles with radius of 50
            circle4.setFill(Color.GREEN);
            visuaalinenTausta.getChildren().add(circle4);
            visualisointi.asiakasLiikkuu(circle4, "liittymä");
       // }
      //Displaying the contents of the stage 
    }
    
}