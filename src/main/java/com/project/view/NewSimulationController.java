package com.project.view;

import java.io.IOException;
import java.util.HashMap;

import com.project.MainApp;
import com.project.simu.framework.IMoottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Parametrit;
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
    private IMoottori m;
    private SimulaatioData sS;
<<<<<<< HEAD
 
    private Boolean simulationRunning = false;

    @FXML
	private Canvas visu;
    @FXML
	private Canvas ani;
    private Visualisointi visualisointi;

    public static final double W = 200; // canvas dimensions.
    public static final double H = 200;
=======

    private Boolean simulationRunning = false;
>>>>>>> main

    public static final double D = 20;  // diameter.
    
    
    @FXML
    public void initialize() {
        new animatefx.animation.ZoomIn();
        ZoomIn trans1 = new ZoomIn(backGround);
        new animatefx.util.ParallelAnimationFX(trans1).play();
<<<<<<< HEAD

        System.out.println("Start");
		
		visualisointi = new Visualisointi(visu);
		
        //asd();
=======
>>>>>>> main
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }

    @FXML
    public void aloitaSimulaatio() throws InterruptedException {
<<<<<<< HEAD
        if(!simulationRunning){
=======
        if (!simulationRunning) {
>>>>>>> main
            Parametrit uP = Parametrit.getInstance();
            Trace.setTraceLevel(Level.INFO);
            m = new OmaMoottori(this);
            m.setViive(0);
            m.setSimulointiAika(uP.getSimulaationAika() * 3600);
            ((Thread) m).start();
            simulationRunning = true;
        }
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

    public void showTulokset(SimulaatioData sS) {
        simulationRunning = false;
        this.sS = sS;
<<<<<<< HEAD
        Platform.runLater(new Runnable() {
            public void run() {
                runTulokset();
            }
        });
    }

    public void runTulokset() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/tuloksetDetailedPopUp.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tulokset");
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TuloksedDetailedController controller = loader.getController();
            controller.setSimulaationSuureet(sS);

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            // Can move window when mouse down and drag.
            scene.setOnMouseDragged(event -> {
                dialogStage.setX(event.getScreenX() - xOffset);
                dialogStage.setY(event.getScreenY() - yOffset);
            });
            dialogStage.show();
            controller.updateValues();
            open = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    @Override
    public void paivitaPalveluPisteet(HashMap<String, int[]> suureStatusMap) {
        Platform.runLater(new Runnable() {
            public void run() {
                // [i] = 0-7 = aspa, 8-10 = puhelinvalikko
                // suureStatusMap.get("Palveltu")[i]
                // suureStatusMap.get("Jonossa")[i]
                // suureStatusMap.get("Quitter")[i]
                // suureStatusMap.get("ReRouted")[i]
                // suureStatusMap.get("Tyovuorossa")[i]
                // suureStatusMap.get("Totalit")[i]

                // Esim.
                int yksityisTv = 0;
                int yritysTv = 0;
                for (int i = 0; i < suureStatusMap.get("Tyovuorossa").length; i++) {
                    if (i < 4) {
                        yksityisTv += suureStatusMap.get("Tyovuorossa")[i];
                    } else if (i > 3 && i < 8) {
                        yritysTv += suureStatusMap.get("Tyovuorossa")[i];
                    }

                }
                yksityisPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yksityisTv));
                yritysPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yritysTv));

                int yksityisPalvelu = 0;
                int yritysPalvelu = 0;
                for (int i = 0; i < suureStatusMap.get("Palveltu").length; i++) {
                    if (i < 4) {
                        yksityisPalvelu += suureStatusMap.get("Palveltu")[i];
                    } else if (i > 3 && i < 8) {
                        yritysPalvelu += suureStatusMap.get("Palveltu")[i];
                    }
                }
                palvelupisteellaYksityis.setText("Palveltuja as " + String.valueOf(yksityisPalvelu));
                palvelupisteellaYritys.setText("Palveltuja as " + String.valueOf(yritysPalvelu));

=======
        Platform.runLater(new Runnable() {
            public void run() {
                runTulokset();
            }
        });
    }

    public void runTulokset() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/tuloksetDetailedPopUp.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tulokset");
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TuloksedDetailedController controller = loader.getController();
            controller.setSimulaationSuureet(sS);

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            // Can move window when mouse down and drag.
            scene.setOnMouseDragged(event -> {
                dialogStage.setX(event.getScreenX() - xOffset);
                dialogStage.setY(event.getScreenY() - yOffset);
            });
            dialogStage.show();
            controller.updateValues();
            open = true;
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void paivitaPalveluPisteet(HashMap<String, int[]> suureStatusMap) {
        Platform.runLater(new Runnable() {
            public void run() {
                // [i] = 0-7 = aspa, 8-10 = puhelinvalikko
                // suureStatusMap.get("Palveltu")[i]
                // suureStatusMap.get("Jonossa")[i]
                // suureStatusMap.get("Quitter")[i]
                // suureStatusMap.get("ReRouted")[i]
                // suureStatusMap.get("Tyovuorossa")[i]
                // suureStatusMap.get("Totalit")[i]

                // Esim.
                int yksityisTv = 0;
                int yritysTv = 0;
                for (int i = 0; i < suureStatusMap.get("Tyovuorossa").length; i++) {
                    if (i < 4) {
                        yksityisTv += suureStatusMap.get("Tyovuorossa")[i];
                    } else if (i > 3 && i < 8) {
                        yritysTv += suureStatusMap.get("Tyovuorossa")[i];
                    }

                }
                yksityisPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yksityisTv));
                yritysPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yritysTv));

                int yksityisPalvelu = 0;
                int yritysPalvelu = 0;
                for (int i = 0; i < suureStatusMap.get("Palveltu").length; i++) {
                    if (i < 4) {
                        yksityisPalvelu += suureStatusMap.get("Palveltu")[i];
                    } else if (i > 3 && i < 8) {
                        yritysPalvelu += suureStatusMap.get("Palveltu")[i];
                    }
                }
                palvelupisteellaYksityis.setText("Palveltuja as " + String.valueOf(yksityisPalvelu));
                palvelupisteellaYritys.setText("Palveltuja as " + String.valueOf(yritysPalvelu));

>>>>>>> main
                int jonoYksityis = 0;
                int jonoYritys = 0;
                for (int i = 0; i < suureStatusMap.get("Jonossa").length; i++) {
                    if (i < 4) {
                        jonoYksityis += suureStatusMap.get("Jonossa")[i];
                    } else if (i > 3 && i < 8) {
                        jonoYritys += suureStatusMap.get("Jonossa")[i];
                    }
                }
                yksityisJonossa.setText(String.valueOf(jonoYksityis));
                YritysJonossa.setText(String.valueOf(jonoYritys));

                // "Totalit" [0] = asiakkaitten kokonaismäärä simulaatiossa
                // "Totalit" [1] = asiakkaita palveltu simulaatiossa
                // "Totalit" [2] = asiakkaitta quitannut jonosta simulaatiossa
                // "Totalit" [3] = asiakkaita reroutattu simulaatiossa

                // Esim.
                int kokonaisMaara = 0;
                for (int i = 0; i < suureStatusMap.get("Totalit").length; i++) {
                    if (i == 0) {
                        // asiakkaitten kokonaismäärä simulaatiossa
                        kokonaisMaara = suureStatusMap.get("Totalit")[i];
                    } else if (i > 0 && i < 3) {
                        // ulkona asiakkaita simulaatiosta (palveltu+quit)
                    } else if (i == 4) {
                        // asiakkaita rerouttattu simulaatiossa
                    }
                }
                suorittaneetMaara.setText("Total: " + String.valueOf(kokonaisMaara));
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

<<<<<<< HEAD
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
    
=======
    // Private: myynti = 1, netti = 2, liittymä = 3, laskutus= 4
    // Corporate: myynti = 5, netti = 6, liittymä = 7, laskutus = 8
    @Override
    public void visualisoiAsiakas(int asType) {

    }

    // Private: myynti = 1, netti = 2, liittymä = 3, laskutus= 4
    // Corporate: myynti = 5, netti = 6, liittymä = 7, laskutus = 8
    // Poistumistype: "Quitter" / "Palveltu"
    @Override
    public void visualisoiPoistuminen(int asType, String poistumisType) {
    }
>>>>>>> main
}