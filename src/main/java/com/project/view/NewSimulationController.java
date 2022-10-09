package com.project.view;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Label YmyyntiTv;
    @FXML
    private Label YnettiTv;
    @FXML
    private Label YliittymäTv;
    @FXML
    private Label YlaskutusTv;
   
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
 
    private Boolean simulationRunning = false;

    @FXML
	private Canvas visu;
    @FXML
	private Canvas ani;
    private Visualisointi visualisointi = new Visualisointi();

    public static final double W = 200; // canvas dimensions.
    public static final double H = 200;

    public static final double D = 20;  // diameter.
    
   
    
    @FXML
    public void initialize() {
        new animatefx.animation.ZoomIn();
        ZoomIn trans1 = new ZoomIn(backGround);
        new animatefx.util.ParallelAnimationFX(trans1).play();

        System.out.println("Start");
		
		//visualisointi = new Visualisointi(visu);
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }

    @FXML
    public void aloitaSimulaatio() throws InterruptedException {
        if (!simulationRunning) {
            Parametrit uP = Parametrit.getInstance();
            Trace.setTraceLevel(Level.INFO);
            m = new OmaMoottori(this);
            m.setViive(25);
            m.setSimulointiAika(uP.getSimulaationAika() * 3600);
            visualisointi.visuaalinenNopeus(m.getViive());
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
                        int myynti = suureStatusMap.get("Tyovuorossa")[0];
                        int netti = suureStatusMap.get("Tyovuorossa")[1];
                        int liittymä = suureStatusMap.get("Tyovuorossa")[2];
                        int laskutus = suureStatusMap.get("Tyovuorossa")[3];
                        switch (i){
                            case 0:
                                System.out.println("Myynnin työvuorossa = " + myynti);
                                YmyyntiTv.setText(String.valueOf(myynti));
                                break;
                            case 1:
                                System.out.println("Netin työvuorossa = " + netti);
                                YnettiTv.setText(String.valueOf(netti));
                                break;
                            case 2:
                                System.out.println("Liittymä työvuorossa = " + liittymä);
                                YliittymäTv.setText(String.valueOf(liittymä));
                                break;
                            case 3:
                                System.out.println("Laskutus työvuorossa = " + laskutus);
                                YlaskutusTv.setText(String.valueOf(laskutus));
                                break;
                        } 
                    } else if (i > 3 && i < 8) {
                        yritysTv += suureStatusMap.get("Tyovuorossa")[i];
                    }

                }
                //yksityisPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yksityisTv));
                //yritysPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yritysTv));
                int yksityisPalvelu = 0;
                int yritysPalvelu = 0;
                for (int i = 0; i < suureStatusMap.get("Palveltu").length; i++) {
                    if (i < 4) {
                        yksityisPalvelu += suureStatusMap.get("Palveltu")[i];
                    } else if (i > 3 && i < 8) {
                        yritysPalvelu += suureStatusMap.get("Palveltu")[i];
                    }
                }
                //palvelupisteellaYksityis.setText("Palveltuja as " + String.valueOf(yksityisPalvelu));
                //palvelupisteellaYritys.setText("Palveltuja as " + String.valueOf(yritysPalvelu));

                int jonoYksityis = 0;
                int jonoYritys = 0;
                for (int i = 0; i < suureStatusMap.get("Jonossa").length; i++) {
                    if (i < 4) {
                        jonoYksityis += suureStatusMap.get("Jonossa")[i];
                    } else if (i > 3 && i < 8) {
                        jonoYritys += suureStatusMap.get("Jonossa")[i];
                    }
                }
                //yksityisJonossa.setText(String.valueOf(jonoYksityis));
                //YritysJonossa.setText(String.valueOf(jonoYritys));

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
                //suorittaneetMaara.setText("Total: " + String.valueOf(kokonaisMaara));
            }
        });
    }

    @Override
    public void hidastaSimulaatiota() {
        m.setViive(m.getViive() + 5);
        visualisointi.visuaalinenNopeus(m.getViive());
    }

    @Override
    public void nopeutaSimulaatiota() {
        if (m.getViive() > 0) {
            m.setViive(m.getViive() - 5);
        }
        visualisointi.visuaalinenNopeus(m.getViive());
    }

    
       // }
      //Displaying the contents of the stage 

    
    // Private: myynti = 1, netti = 2, liittymä = 3, laskutus= 4
    // Corporate: myynti = 5, netti = 6, liittymä = 7, laskutus = 8
    @Override
    public void visualisoiAsiakas(int asType) {
        Platform.runLater(new Runnable() {
            public void run() {
                switch (asType){
                case 1:
                    Circle circle1 = new Circle(10);           // initialize circles with radius of 10
                    circle1.setFill(Color.RED);
                    visuaalinenTausta.getChildren().addAll(circle1);
                    visualisointi.asiakasLiikkuu(circle1, "Pmyynti");
                    break;
                case 2:
                    Circle circle2 = new Circle(10);           // initialize circles with radius of 10
                    circle2.setFill(Color.BLUE);
                    visuaalinenTausta.getChildren().add(circle2);
                    visualisointi.asiakasLiikkuu(circle2, "Pnetti");
                    break;
                case 3:
                    Circle circle3 = new Circle(10);           // initialize circles with radius of 10
                    circle3.setFill(Color.GREEN);
                    visuaalinenTausta.getChildren().add(circle3);
                    visualisointi.asiakasLiikkuu(circle3, "Pliittymä");
                    break;
                case 4:
                    Circle circle4 = new Circle(10);           // initialize circles with radius of 10
                    circle4.setFill(Color.PINK);
                    visuaalinenTausta.getChildren().add(circle4);
                    visualisointi.asiakasLiikkuu(circle4, "Plaskutus");
                    break;
                case 5:
                   Circle circle5= new Circle(10);           // initialize circles with radius of 10
                   circle5.setFill(Color.RED);
                   visuaalinenTausta.getChildren().add(circle5);
                   visualisointi.asiakasLiikkuu(circle5, "Ymyynti");
                   break;
                case 6:
                    Circle circle6= new Circle(10);           // initialize circles with radius of 10
                    circle6.setFill(Color.BLUE);
                    visuaalinenTausta.getChildren().add(circle6);
                    visualisointi.asiakasLiikkuu(circle6, "Ynetti");
                    break;
                case 7:
                    Circle circle7= new Circle(10);           // initialize circles with radius of 10
                    circle7.setFill(Color.GREEN);
                    visuaalinenTausta.getChildren().add(circle7);
                    visualisointi.asiakasLiikkuu(circle7, "Yliittymä");
                    break;
                case 8:
                    Circle circle8= new Circle(10);           // initialize circles with radius of 10
                    circle8.setFill(Color.PINK);
                    visuaalinenTausta.getChildren().add(circle8);
                    visualisointi.asiakasLiikkuu(circle8, "Ylaskutus");
                    break;
            }
        }
    });
}

    // Private: myynti = 1, netti = 2, liittymä = 3, laskutus= 4
    // Corporate: myynti = 5, netti = 6, liittymä = 7, laskutus = 8
    // Poistumistype: "Quitter" / "Palveltu"
    @Override
    public void visualisoiPoistuminen(int asType, String poistumisType) { 
        Platform.runLater(new Runnable() {
            public void run() {
                
                switch (asType){
                    case 1:
                        Circle circle1 = new Circle(10);           // initialize circles with radius of 10
                        circle1.setFill(Color.RED);

                        if(poistumisType.equals("Quitter")){
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Pmyynti", poistumisType);
                            break;
                        }else{
                            visuaalinenTausta.getChildren().addAll(circle1);
                        }
                        visualisointi.asiakasPoistuu(circle1, "Pmyynti", poistumisType);
                        break;
                    case 2:
                        Circle circle2 = new Circle(10);           // initialize circles with radius of 10
                        circle2.setFill(Color.BLUE);

                        if(poistumisType.equals("Quitter")){
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Pnetti", poistumisType);
                            break;
                        }else{
                            visuaalinenTausta.getChildren().addAll(circle2);
                        }

                        visualisointi.asiakasPoistuu(circle2, "Pnetti", poistumisType);
                        break;
                    case 3:
                        Circle circle3 = new Circle(10);           // initialize circles with radius of 10
                        circle3.setFill(Color.GREEN);
                        if(poistumisType.equals("Quitter")){
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Pliittymä", poistumisType);
                            break;
                        }else{
                            visuaalinenTausta.getChildren().addAll(circle3);
                        }
                        visualisointi.asiakasPoistuu(circle3, "Pliittymä", poistumisType);
                        break;
                    case 4:
                        Circle circle4 = new Circle(10);           // initialize circles with radius of 10
                        circle4.setFill(Color.PINK);
                        if(poistumisType.equals("Quitter")){
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Plaskutus", poistumisType);
                            break;
                        }else{
                            visuaalinenTausta.getChildren().addAll(circle4);
                        }
                        visualisointi.asiakasPoistuu(circle4, "Plaskutus", poistumisType);
                        break;
                    case 5:
                       Circle circle5= new Circle(10);           // initialize circles with radius of 10
                       circle5.setFill(Color.RED);
                       if(poistumisType.equals("Quitter")){
                        File diverFile;
                        ImageView ivDiver = new ImageView();
                        diverFile = new File("src/main/resources/com/project/icons/angry.png");
                        Image diverImage = new Image(diverFile.toURI().toString());
                        ivDiver.setImage(diverImage);
                        visuaalinenTausta.getChildren().addAll(ivDiver);
                        visualisointi.asiakasSuuttuu(ivDiver, "Ymyynti", poistumisType);
                        break;
                    }else{
                        visuaalinenTausta.getChildren().addAll(circle5);
                    }
                       visualisointi.asiakasPoistuu(circle5, "Ymyynti", poistumisType);
                       break;
                    case 6:
                        Circle circle6= new Circle(10);           // initialize circles with radius of 10
                        circle6.setFill(Color.BLUE);
                        if(poistumisType.equals("Quitter")){
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Ynetti", poistumisType);
                            break;
                        }else{
                            visuaalinenTausta.getChildren().addAll(circle6);
                        }
                        visualisointi.asiakasPoistuu(circle6, "Ynetti", poistumisType);
                        break;
                    case 7:
                        Circle circle7= new Circle(10);           // initialize circles with radius of 10
                        circle7.setFill(Color.GREEN);
                        if(poistumisType.equals("Quitter")){
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Yliittymä", poistumisType);
                            break;
                        }else{
                            visuaalinenTausta.getChildren().addAll(circle7);
                        }
                        visualisointi.asiakasPoistuu(circle7, "Yliittymä", poistumisType);
                        break;
                    case 8:
                        Circle circle8= new Circle(10);           // initialize circles with radius of 10
                        circle8.setFill(Color.PINK);
                        if(poistumisType.equals("Quitter")){
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Ylaskutus", poistumisType);
                            break;
                        }else{
                            visuaalinenTausta.getChildren().addAll(circle8);
                        }
                        visualisointi.asiakasPoistuu(circle8, "Ylaskutus", poistumisType);
                        break;
                }
            }
        });
    }
}