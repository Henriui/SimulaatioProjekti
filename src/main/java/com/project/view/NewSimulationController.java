package com.project.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author Jonne Borgman
 * @author Rasmus Hyyppä
 */
public class NewSimulationController implements INewSimulationControllerVtoM, INewSimulationControllerMtoV {
    @FXML
    private AnchorPane backGround;
    @FXML
    private AnchorPane visuaalinenTausta;

    // Yksityispuolen labelit.

    @FXML
    private Label yksityisPalvelupisteita;
    @FXML
    private Label yksityisJonossa;
    @FXML
    private Label ymyyntiTv;
    @FXML
    private Label ynettiTv;
    @FXML
    private Label yliittymäTv;
    @FXML
    private Label ylaskutusTv;
    @FXML
    private Label palvelupisteellaYksityis;

    // Yrityspuolen labelit.

    @FXML
    private Label YritysJonossa;
    @FXML
    private Label cmyyntiTv;
    @FXML
    private Label cnettiTv;
    @FXML
    private Label cliittymäTv;
    @FXML
    private Label claskutusTv;
    @FXML
    private Label yritysPalvelupisteita;
    @FXML
    private Label palvelupisteellaYritys;

    @FXML
    private Label kokonaismäärä;
    @FXML
    private Circle liikkuu;

    private IMoottori m;
    private Parametrit uP;
    private double xOffset = 0;
    private double yOffset = 0;
    private static boolean open = false;
    private boolean simulationRunning = false;

    private Visualisointi visualisointi = new Visualisointi();

    // Array's and hashmaps for circles
    Color[] colorArr = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.PINK, Color.RED, Color.BLUE,
            Color.GREEN, Color.PINK };
    String[] aL = new String[] { "Pmyynti", "Pnetti", "Pliittymä", "Plaskutus", "Ymyynti", "Ynetti", "Yliittymä",
            "Ylaskutus" };
    HashMap<Integer, LinkedList<Circle>> circleKartta = new HashMap<>();
    HashMap<Integer, LinkedList<ImageView>> iVKartta = new HashMap<>();

    // Yksityisasiakkaat lista.
    LinkedList<Circle> myyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> nettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> liittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> laskutusAsiakkaat = new LinkedList<Circle>();
    // Yksityisasiakkaat poistuminen lista.
    LinkedList<Circle> poistuMyyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> poistuNettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> poistuLiittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> poistuLaskutusAsiakkaat = new LinkedList<Circle>();
    // Yksityisasiakkaat Reroute lista.
    LinkedList<ImageView> rerouteMyyntiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> rerouteNettiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> rerouteLiittymäAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> rerouteLaskutusAsiakkaat = new LinkedList<ImageView>();
    // Yksityisasiakkaat suuttuneet lista.
    LinkedList<ImageView> quitterMyyntiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> quitterNettiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> quitterLiittymäAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> quitterLaskutusAsiakkaat = new LinkedList<ImageView>();
    // Yritysasiakkaat lista.
    LinkedList<Circle> cMyyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> cNettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> cLiittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> cLaskutusAsiakkaat = new LinkedList<Circle>();
    // Yritysasiakkaat poistuminen lista.
    LinkedList<Circle> poistuCmyyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> poistuCnettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> poistuCliittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> poistuClaskutusAsiakkaat = new LinkedList<Circle>();
    // Yritysasiakkaat suuttuneet lista.
    LinkedList<ImageView> quitterCmyyntiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> quitterCnettiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> quitterCliittymäAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> quitterClaskutusAsiakkaat = new LinkedList<ImageView>();
    // Yritysasiakkaat Reroute lista.
    LinkedList<ImageView> rerouteCmyyntiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> rerouteCnettiAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> rerouteCliittymäAsiakkaat = new LinkedList<ImageView>();
    LinkedList<ImageView> rerouteClaskutusAsiakkaat = new LinkedList<ImageView>();

    /**
     * Alustaa parametrit sekä toistaa animaation kun näkymä avataan.
     * 
     * @author Jonne Borgman
     */
    @FXML
    public void initialize() {
        uP = new Parametrit();
        open = false;
        new animatefx.animation.ZoomIn();
        ZoomIn trans1 = new ZoomIn(backGround);
        new animatefx.util.ParallelAnimationFX(trans1).play();
        System.out.println("Start");
        circleKartta.put(1, myyntiAsiakkaat);
        circleKartta.put(2, nettiAsiakkaat);
        circleKartta.put(3, liittymäAsiakkaat);
        circleKartta.put(4, laskutusAsiakkaat);
        circleKartta.put(5, cMyyntiAsiakkaat);
        circleKartta.put(6, cNettiAsiakkaat);
        circleKartta.put(7, cLiittymäAsiakkaat);
        circleKartta.put(8, cLaskutusAsiakkaat);
        circleKartta.put(9, poistuMyyntiAsiakkaat);
        circleKartta.put(10, poistuNettiAsiakkaat);
        circleKartta.put(11, poistuLiittymäAsiakkaat);
        circleKartta.put(12, poistuLaskutusAsiakkaat);
        circleKartta.put(13, poistuCmyyntiAsiakkaat);
        circleKartta.put(14, poistuCnettiAsiakkaat);
        circleKartta.put(15, poistuCliittymäAsiakkaat);
        circleKartta.put(16, poistuClaskutusAsiakkaat);
        iVKartta.put(1, quitterMyyntiAsiakkaat);
        iVKartta.put(2, quitterNettiAsiakkaat);
        iVKartta.put(3, quitterLiittymäAsiakkaat);
        iVKartta.put(4, quitterLaskutusAsiakkaat);
        iVKartta.put(5, quitterCmyyntiAsiakkaat);
        iVKartta.put(6, quitterCnettiAsiakkaat);
        iVKartta.put(7, quitterCliittymäAsiakkaat);
        iVKartta.put(8, quitterClaskutusAsiakkaat);
        iVKartta.put(9, rerouteMyyntiAsiakkaat);
        iVKartta.put(10, rerouteNettiAsiakkaat);
        iVKartta.put(11, rerouteLiittymäAsiakkaat);
        iVKartta.put(12, rerouteLaskutusAsiakkaat);
        iVKartta.put(13, rerouteCmyyntiAsiakkaat);
        iVKartta.put(14, rerouteCnettiAsiakkaat);
        iVKartta.put(15, rerouteCliittymäAsiakkaat);
        iVKartta.put(16, rerouteClaskutusAsiakkaat);
    }

    /**
     * Vie näkymän takaisin pää-näkymälle.
     * 
     * @throws IOException
     * @author Jonne Borgman
     */
    @FXML
    private void takaisinMainView() throws IOException {
        if (simulationRunning) {
            open = true;
            m.setSimulointiAika(0);
        }
        MainApp.setRoot("mainView");
    }

    /**
     * Tarkistaa onko pop-up ikkuna auki.
     * 
     * @param isOpen
     * @author Jonne Borgman
     */
    public void popupOpen(boolean isOpen) {
        open = isOpen;
    }

    /**
     * @return Parametrit from newsimulationcontroller for popup windows
     * @author Rasmus Hyyppä
     */
    public Parametrit getParametri() {
        return uP;
    }

    /**
     * @param parametri Sets Parametrit from ParametriController to
     *                  NewSimulationController
     * @author Rasmus Hyyppä
     */
    public void setParametri(Parametrit parametri) {
        uP = parametri;
    }

    /**
     * Hakee kutsuessa oikean FXML tiedoston oikeasta paikasta.
     * 
     * @param fxml
     * @return
     * @throws IOException
     * 
     * @author Jonne Borgman
     */
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        // Finds fxml file from the resources folder.
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader;
    }

    /**
     * Tyhjentää näkymän asiakkaista, ja käynnistää simulaation.
     * 
     * @throws IOException
     * @author Jonne Borgman
     */
    @FXML
    public void aloitaSimulaatio() throws InterruptedException {

        if (simulationRunning) {
            m.setSimulointiAika(0);
            return;
        }

        alustaAsiakkaat();

        if (!open) {
            Trace.setTraceLevel(Level.INFO);
            m = new OmaMoottori(this, uP);
            m.setViive(250);
            m.setSimulointiAika(uP.getSimulaationAika() * 3600);
            visualisointi.visuaalinenNopeus(m.getViive());
            ((Thread) m).start();
            simulationRunning = true;
        }
    }

    /**
     * Avaa asetukset pop-up näkymän.
     * 
     * @throws IOException
     * @author Jonne Borgman
     */
    @FXML
    public void setParametrit() throws IOException {
        if (!open && !simulationRunning) {
            FXMLLoader loader = loadFXML("Parametrit");
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Suureiden asetukset");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);

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

    /**
     * TODO:
     * 
     * @param sS
     * @author Lassi
     */
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

    /**
     * TODO:
     * 
     * @param sS
     * @throws IOException
     * @author Lassi
     */
    public void runTulokset(SimulaatioData sS) throws IOException {
        if (!open) {
            FXMLLoader loader = loadFXML("tuloksetDetailedPopUp");
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Simulaatiokerran tulos");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            // Can move window when mouse down and drag.
            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            TuloksedDetailedController controller = loader.getController();
            controller.setSimulationController(this);
            controller.setSimulaationData(sS);
            controller.setParametrit(uP);
            stage.show();
            controller.updateValues();
            open = true;
        }
    }

    /**
     * Grants view a hashmap from kontrolleri in model, that contains current status
     * of each palvelupiste group
     * when simulation is running.
     * [i] = 0-7 = aspa, 8-10 = puhelinvalikko
     * suureStatusMap.get("Palveltu")[i], kuinka monta palveltu
     * suureStatusMap.get("Jonossa")[i], kuinka monta jonossa
     * suureStatusMap.get("Quitter")[i], kuinka monta quitannut
     * suureStatusMap.get("ReRouted")[i], kuinka monta reroutattu
     * suureStatusMap.get("Tyovuorossa")[i], onko tullut töihin vielä
     * suureStatusMap.get("Varattu")[i], Kuinka monta varattua palvelupistettä
     * suureStatusMap.get("Palveluprosentti")[i], palvelupisteen vastausprosentti
     * suureStatusMap.get("Totalit")[i], =
     * "Totalit" [0] = asiakkaitten kokonaismäärä simulaatiossa
     * "Totalit" [1] = asiakkaita palveltu simulaatiossa
     * "Totalit" [2] = asiakkaitta quitannut jonosta simulaatiossa
     * "Totalit" [3] = asiakkaita reroutattu simulaatiossa
     * "Totalit" [4] = simulaationaika
     * "Totalit" [5] = palveluprosentti
     * 
     * @param suureStatusMap
     * @author Rasmus Hyyppä
     */
    @Override
    public void paivitaPalveluPisteet(HashMap<String, int[]> suureStatusMap) {
        Label[] tyoVuoroLabelit = new Label[] { ymyyntiTv, ynettiTv, yliittymäTv, ylaskutusTv, cmyyntiTv,
                cnettiTv, cliittymäTv, claskutusTv };
        Platform.runLater(new Runnable() {
            public void run() {
                for (int i = 0; i < suureStatusMap.get("Tyovuorossa").length; i++) {
                    if (i < 8) {
                        String strValue = String.valueOf(suureStatusMap.get("Tyovuorossa")[i]);
                        tyoVuoroLabelit[i].setText(strValue);
                    }
                }
                int ulkona = 0;
                for (int i = 0; i < suureStatusMap.get("Totalit").length; i++) {
                    if (i > 0 && i < 3) {
                        // ulkona asiakkaita simulaatiosta (palveltu[1]+quitterit[2])
                        ulkona += suureStatusMap.get("Totalit")[i];
                    }
                }
                kokonaismäärä.setText("Total: " + String.valueOf(ulkona));
            }
        });
    }

    /**
     * Hidastaa simulaatiolla sekä animaatioita 5ms.
     * 
     * @author Jonne Borgman
     */
    @Override
    public void hidastaSimulaatiota() {
        m.setViive(m.getViive() + 5);
        visualisointi.visuaalinenNopeus(m.getViive());
    }

    /**
     * Nopeuttaa simulaatiolla sekä animaatioita 5ms.
     * 
     * @author Jonne Borgman
     */
    @Override
    public void nopeutaSimulaatiota() {
        if (m.getViive() > 0) {
            m.setViive(m.getViive() - 5);
            visualisointi.visuaalinenNopeus(m.getViive());
        }
    }

    /**
     * Alustaa asiakkaat listalta, sekä poistaa asiakkaat näytöltä.
     * Alustaa visualisoinnin Y valuet.
     * 
     * @author Jonne Borgman
     */
    public void alustaAsiakkaat() {
        // Alustaa jonojen Y valuet.
        visualisointi.alustaJonot();

        for (int i = 1; i < circleKartta.size(); i++) {
            while (circleKartta.get(i).iterator().hasNext()) {
                Circle m = circleKartta.get(i).getFirst();
                visuaalinenTausta.getChildren().remove(m);
                circleKartta.get(i).removeFirst();
            }
        }

        for (int i = 1; i < iVKartta.size(); i++) {
            while (iVKartta.get(i).iterator().hasNext()) {
                ImageView iV = iVKartta.get(i).getFirst();
                visuaalinenTausta.getChildren().remove(iV);
                iVKartta.get(i).removeFirst();
            }
        }
    }

    /**
     * Asiakkaan lähtiessä myynnin jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void poistaJono(int asType) {
        // Poista pallo jonosta.
        if (circleKartta.get(asType).size() > 0) {
            // Siirtää kaikki pallot listasta 20 pikseliä alemmas
            Circle m = circleKartta.get(asType).getFirst();
            visuaalinenTausta.getChildren().remove(m);
            circleKartta.get(asType).removeFirst();
        }

        for (int i = 0; i < circleKartta.get(asType).size(); i++) {
            int y = 20;
            Circle cm = circleKartta.get(asType).get(i);
            Translate translate = new Translate();
            translate.setY(y);
            cm.getTransforms().add(translate);
        }

    }

    /**
     * Luo asiakas pallon, lisää sen listaan ja kutsuu visualisoinnista asiakkaalle
     * animaation.
     * 
     * @param asType
     * @author Jonne Borgman
     */
    @Override
    public void visualisoiAsiakas(int asType, int oldAsType) {
        // Private: myynti = 1, netti = 2, liittymä = 3, laskutus= 4
        // Corporate: myynti = 5, netti = 6, liittymä = 7, laskutus = 8
        Platform.runLater(new Runnable() {
            public void run() {
                if (oldAsType < 9) {
                    File diverFile0;
                    ImageView ivDiver0 = new ImageView();
                    diverFile0 = new File("src/main/resources/com/project/icons/mistake.png");
                    Image diverImage0 = new Image(diverFile0.toURI().toString());
                    ivDiver0.setImage(diverImage0);
                    visuaalinenTausta.getChildren().addAll(ivDiver0);
                    iVKartta.get(oldAsType + 8).addLast(ivDiver0);
                    visualisointi.asiakasReroute(ivDiver0, oldAsType);
                    Circle circle1 = new Circle(10); // initialize circles with radius of 10
                    circle1.setFill(colorArr[asType - 1]);
                    circleKartta.get(asType).addLast(circle1);
                    visuaalinenTausta.getChildren().addAll(circle1);
                    visualisointi.asiakasLiikkuu(circle1, aL[asType - 1]);
                } else {
                    Circle circle1 = new Circle(10); // initialize circles with radius of 10
                    circle1.setFill(colorArr[asType - 1]);
                    circleKartta.get(asType).addLast(circle1);
                    visuaalinenTausta.getChildren().addAll(circle1);
                    visualisointi.asiakasLiikkuu(circle1, aL[asType - 1]);
                }
            }
        });
    }

    /**
     * Antaa asiakkaalle poistumis animaation poistumisTypen mukaan.
     * Jos asiakas on Palveltu, saa hän normaalin poistumis animaation visualisointi
     * luokalta.
     * Jos asiakas on Quitter, luodaan asiakkaalle image, ja visualisointi luokalta
     * antaa quit animaation.
     * 
     * @param asType
     * @param poistumusType
     * @author Jonne Borgman
     */
    @Override
    public void visualisoiPoistuminen(int asType, String poistumisType) {
        // Private: myynti = 1, netti = 2, liittymä = 3, laskutus= 4
        // Corporate: myynti = 5, netti = 6, liittymä = 7, laskutus = 8
        // Poistumistype: "Quitter" / "Palveltu" / "Rerouted"
        Platform.runLater(new Runnable() {
            public void run() {

                if (poistumisType.equals("Quitter")) {
                    File diverFile;
                    ImageView ivDiver = new ImageView();
                    diverFile = new File("src/main/resources/com/project/icons/angry.png");
                    Image diverImage = new Image(diverFile.toURI().toString());
                    ivDiver.setImage(diverImage);
                    visuaalinenTausta.getChildren().addAll(ivDiver);
                    iVKartta.get(asType).addLast(ivDiver);
                    visualisointi.asiakasSuuttuu(ivDiver, aL[asType - 1], poistumisType);
                    poistaJono(asType);
                } else if (poistumisType.equals("Rerouted")) {
                    poistaJono(asType);
                } else {
                    Circle circle1 = new Circle(10); // initialize circles with radius of 10
                    circle1.setFill(colorArr[asType - 1]);
                    circleKartta.get(asType + 8).addLast(circle1);
                    visuaalinenTausta.getChildren().addAll(circle1);
                    poistaJono(asType);
                    visualisointi.asiakasPoistuu(circle1, aL[asType - 1], poistumisType);
                }
            }
        });
    }
}