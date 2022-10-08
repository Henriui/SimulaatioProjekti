package com.project.view;

import java.io.IOException;
import com.project.MainApp;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.SimulaationSuureet;
import com.project.simu.model.UserParametrit;
import animatefx.animation.ZoomIn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewSimulationController implements INewSimulationControllerVtoM, INewSimulationControllerMtoV {
    @FXML
    private AnchorPane backGround;
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

    private double xOffset = 0;
    private double yOffset = 0;
    private static boolean open = false;
    private SimulaationSuureet sS;
    private Moottori m;
    private Boolean simulationRunning = false;

    @FXML
    public void initialize() {
        new animatefx.animation.ZoomIn();
        ZoomIn trans1 = new ZoomIn(backGround);
        new animatefx.util.ParallelAnimationFX(trans1).play();
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }

    @FXML
    public void aloitaSimulaatio() throws InterruptedException {
        if(!simulationRunning){
            UserParametrit uP = UserParametrit.getInstance();
            Trace.setTraceLevel(Level.INFO);
            m = new OmaMoottori(this);
            m.setViive(25);
            m.setSimulointiaika(uP.getSimulaationAika() * 3600);
            ((Thread) m).start();
            simulationRunning = true;
        }
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

    public void showTulokset(SimulaationSuureet sS) {
        simulationRunning = false;
        this.sS = sS;
        System.out.println(sS.getAsPalveltu());
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
            System.out.println("Nakyyko?");
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

    // LIUTA TESTI METHODEITA -> nää vois siivota järkevämmäks ehkä?
    // Jokanen tekee erillisen runin? jne jne
    // Check: SimulaationSuureet.java updateSuureet()
    @Override
    public void ilmoitaJononKoko(int yksityis, int yritys) {
        Platform.runLater(new Runnable() {
            public void run() {
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
}