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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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

    private IMoottori m;
    private Parametrit uP;

    private double xOffset = 0;
    private double yOffset = 0;
    private static boolean open = false;
    private boolean simulationRunning = false;

    @FXML
    public void initialize() {
        uP = new Parametrit();
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
        if (!simulationRunning) {
            Trace.setTraceLevel(Level.INFO);
            m = new OmaMoottori(this, uP);
            m.setViive(0);
            m.setSimulointiAika(uP.getSimulaationAika() * 3600);
            ((Thread) m).start();
            simulationRunning = true;
        }
    }

    @FXML
    public void setSuureet() throws IOException {
        if (!open) {
            FXMLLoader loader = loadFXML("Parametrit");
            Scene scene = new Scene(loader.load());
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

            ParametriController controller = loader.getController();
            controller.setSimulationController(this);
            stage.show();
            open = true;
        }
    }

    public void showTulokset(SimulaatioData sS) {
        simulationRunning = false;
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    runTulokset(sS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void runTulokset(SimulaatioData sS) throws IOException {
        if (!open) {
            FXMLLoader loader = loadFXML("tuloksetDetailedPopUp");
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Suureiden asetukset");
            stage.initStyle(StageStyle.TRANSPARENT);

            TuloksedDetailedController controller = loader.getController();
            controller.setSimulaationSuureet(sS);
            controller.setSimulationController(this);
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
            controller.updateValues();
            open = true;
        }
    }

    public void popupOpen(boolean isOpen) {
        open = isOpen;
    }

    public Parametrit getParametri() {
        return uP;
    }

    public void setParametri(Parametrit parametri) {
        uP = parametri;
    }

    // Finds fxml file from the resources folder.
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader;
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
                for (int i = 0; i < suureStatusMap.get("Varattu").length; i++) {
                    if (i < 4) {
                        yksityisTv += suureStatusMap.get("Varattu")[i];
                    } else if (i > 3 && i < 8) {
                        yritysTv += suureStatusMap.get("Varattu")[i];
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
                // "Totalit" [4] = simulaationaika
                // "Totalit" [5] = palveluprosentti

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
}