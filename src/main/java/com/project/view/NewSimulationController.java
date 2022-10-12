package com.project.view;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

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
    private Label YmyyntiTv;
    @FXML
    private Label YnettiTv;
    @FXML
    private Label YliittymäTv;
    @FXML
    private Label YlaskutusTv;
    @FXML
    private Label palvelupisteellaYksityis;

    // Yrityspuolen labelit.

    @FXML
    private Label YritysJonossa;
    @FXML
    private Label CmyyntiTv;
    @FXML
    private Label CnettiTv;
    @FXML
    private Label CliittymäTv;
    @FXML
    private Label ClaskutusTv;
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
    private boolean pallotNäytöllä = false;

    private Visualisointi visualisointi = new Visualisointi();

    // Reroutatut asiakkaat
    private int reroutMyynti = 1;
    private int reroutNetti = 1;
    private int reroutLiittymä = 1;
    private int reroutLaskutus = 1;
    private int reroutYritysMyynti = 1;
    private int reroutYritysNetti = 1;
    private int reroutYritysLiittymä = 1;
    private int reroutYritysLaskutus = 1;

    // Yksityisasiakkaat lista.
    LinkedList<Circle> myyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> nettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> liittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> laskutusAsiakkaat = new LinkedList<Circle>();
    // Yksityisasiakkaat poistuminen lista.
    LinkedList<Circle> PoistumyyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> PoistunettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> PoistuliittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> PoistulaskutusAsiakkaat = new LinkedList<Circle>();
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
    LinkedList<Circle> CmyyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> CnettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> CliittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> ClaskutusAsiakkaat = new LinkedList<Circle>();
    // Yritysasiakkaat poistuminen lista.
    LinkedList<Circle> PoistuCmyyntiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> PoistuCnettiAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> PoistuCliittymäAsiakkaat = new LinkedList<Circle>();
    LinkedList<Circle> PoistuClaskutusAsiakkaat = new LinkedList<Circle>();
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

    HashMap<Integer, LinkedList<Circle>> circleKartta = new HashMap<>();
    HashMap<Integer, LinkedList<ImageView>> iVKartta = new HashMap<>();

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
        circleKartta.put(5, CmyyntiAsiakkaat);
        circleKartta.put(6, CnettiAsiakkaat);
        circleKartta.put(7, CliittymäAsiakkaat);
        circleKartta.put(8, ClaskutusAsiakkaat);
        circleKartta.put(9, PoistumyyntiAsiakkaat);
        circleKartta.put(10, PoistunettiAsiakkaat);
        circleKartta.put(11, PoistuliittymäAsiakkaat);
        circleKartta.put(12, PoistulaskutusAsiakkaat);
        circleKartta.put(13, PoistuCmyyntiAsiakkaat);
        circleKartta.put(14, PoistuCnettiAsiakkaat);
        circleKartta.put(15, PoistuCliittymäAsiakkaat);
        circleKartta.put(16, PoistuClaskutusAsiakkaat);
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
            m.setViive(0);
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
        Label[] tyoVuoroLabelit = new Label[] { YmyyntiTv, YnettiTv, YliittymäTv, YlaskutusTv, CmyyntiTv,
                CnettiTv, CliittymäTv, ClaskutusTv };
        Platform.runLater(new Runnable() {
            public void run() {
                for (int i = 0; i < suureStatusMap.get("Varattu").length; i++) {
                    String strValue = String.valueOf(suureStatusMap.get("Tyovuorossa")[i]);
                    if (i < 8) {
                        // System.out.println(tyoVuoroLabelit[i].toString() + " työvuorossa = " +
                        // strValue);
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
              
                for (int i = 0; i < suureStatusMap.get("ReRouted").length; i++) {
                    if(i >= 0 && i < 8){
                        switch (i){
                            case 0:
                                if(reroutMyynti == suureStatusMap.get("ReRouted")[0]){
                                    visualisoiReroute(0);
                                    reroutMyynti++;
                               }
                                break;
                            case 1:
                                if(reroutNetti == suureStatusMap.get("ReRouted")[1]){
                                    visualisoiReroute(1);
                                    reroutNetti++;
                                }
                                break;
                            case 2:
                                if(reroutLiittymä == suureStatusMap.get("ReRouted")[2]){
                                    visualisoiReroute(2);
                                    reroutLiittymä++;
                                }
                                break;
                            case 3:
                                if(reroutLaskutus == suureStatusMap.get("ReRouted")[3]){
                                    visualisoiReroute(3);
                                    reroutLaskutus++;
                                }
                                break;
                            case 4:
                                if(reroutYritysMyynti == suureStatusMap.get("ReRouted")[4]){
                                    visualisoiReroute(4);
                                    reroutYritysMyynti++;
                                }
                                break;
                            case 5:
                                if(reroutYritysNetti == suureStatusMap.get("ReRouted")[5]){
                                    visualisoiReroute(5);
                                    reroutYritysNetti++;
                                }
                                break;
                            case 6:
                                if(reroutYritysLiittymä == suureStatusMap.get("ReRouted")[6]){
                                    visualisoiReroute(6);
                                    reroutYritysLiittymä++;
                                }
                                break;
                            case 7:
                                if(reroutYritysLaskutus == suureStatusMap.get("ReRouted")[7]){
                                    visualisoiReroute(7);
                                    reroutYritysLaskutus++;
                                }
                                break;
                        } 
                    }
                }
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
        }
        visualisointi.visuaalinenNopeus(m.getViive());
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
        pallotNäytöllä = false;
    }

    /**
     * Asiakkaan lähtiessä myynnin jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void poistaMyyntiJono() {
        // Poista yksityisasiakkaan pallo jonosta.
        Circle m = myyntiAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(m);
        myyntiAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < myyntiAsiakkaat.size(); i++) {
            int y = 20;
            Circle cm = myyntiAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cm.getTransforms().add(translate);
        }
    }

    /**
     * Asiakkaan lähtiessä netin jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void poistaNettiJono() {
        Circle n = nettiAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(n);
        nettiAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < nettiAsiakkaat.size(); i++) {
            int y = 20;
            Circle cn = nettiAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cn.getTransforms().add(translate);
        }

    }

    /**
     * Asiakkaan lähtiessä liittymän jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void poistaLiittymäJono() {
        Circle li = liittymäAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(li);
        liittymäAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < liittymäAsiakkaat.size(); i++) {
            int y = 20;
            Circle cli = liittymäAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cli.getTransforms().add(translate);
        }
    }

    /**
     * Asiakkaan lähtiessä laskutuksen jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void poistaLaskutusJono() {
        Circle la = laskutusAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(la);
        laskutusAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < laskutusAsiakkaat.size(); i++) {
            int y = 20;
            Circle cla = laskutusAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cla.getTransforms().add(translate);
        }
    }

    /**
     * Asiakkaan lähtiessä yritys myynnin jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void CpoistaMyyntiJono() {
        // Poista yritysasiakkaan pallo jonosta.
        Circle m = CmyyntiAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(m);
        CmyyntiAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < CmyyntiAsiakkaat.size(); i++) {
            int y = 20;
            Circle cm = CmyyntiAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cm.getTransforms().add(translate);
        }
    }

    /**
     * Asiakkaan lähtiessä yritys netin jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void CpoistaNettiJono() {
        Circle n = CnettiAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(n);
        CnettiAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < CnettiAsiakkaat.size(); i++) {
            int y = 20;
            Circle cn = CnettiAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cn.getTransforms().add(translate);
        }
    }

    /**
     * Asiakkaan lähtiessä yritys liittymän jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void CpoistaLiittymäJono() {
        Circle li = CliittymäAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(li);
        CliittymäAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < CliittymäAsiakkaat.size(); i++) {
            int y = 20;
            Circle cli = CliittymäAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cli.getTransforms().add(translate);
        }
    }

    /**
     * Asiakkaan lähtiessä yritys laskutus jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * 
     * @author Jonne Borgman
     */
    public void CpoistaLaskutusJono() {
        Circle la = ClaskutusAsiakkaat.getFirst();
        visuaalinenTausta.getChildren().remove(la);
        ClaskutusAsiakkaat.removeFirst();

        // Siirtää kaikki pallot listasta 20 pikseliä alemmas.
        for (int i = 0; i < ClaskutusAsiakkaat.size(); i++) {
            int y = 20;
            Circle cla = ClaskutusAsiakkaat.get(i);

            Translate translate = new Translate();
            translate.setY(y);
            cla.getTransforms().add(translate);
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
        pallotNäytöllä = true;
        Platform.runLater(new Runnable() {
            public void run() {
                switch (asType) {
                    case 1:
                        Circle circle1 = new Circle(10); // initialize circles with radius of 10
                        circle1.setFill(Color.RED);
                        myyntiAsiakkaat.addLast(circle1);
                        visuaalinenTausta.getChildren().addAll(circle1);
                        visualisointi.asiakasLiikkuu(circle1, "Pmyynti");
                        break;
                    case 2:
                        Circle circle2 = new Circle(10); // initialize circles with radius of 10
                        circle2.setFill(Color.BLUE);
                        nettiAsiakkaat.addLast(circle2);
                        visuaalinenTausta.getChildren().add(circle2);
                        visualisointi.asiakasLiikkuu(circle2, "Pnetti");
                        break;
                    case 3:
                        Circle circle3 = new Circle(10); // initialize circles with radius of 10
                        circle3.setFill(Color.GREEN);
                        liittymäAsiakkaat.addLast(circle3);
                        visuaalinenTausta.getChildren().add(circle3);
                        visualisointi.asiakasLiikkuu(circle3, "Pliittymä");
                        break;
                    case 4:
                        Circle circle4 = new Circle(10); // initialize circles with radius of 10
                        circle4.setFill(Color.PINK);
                        laskutusAsiakkaat.addLast(circle4);
                        visuaalinenTausta.getChildren().add(circle4);
                        visualisointi.asiakasLiikkuu(circle4, "Plaskutus");
                        break;
                    case 5:
                        Circle circle5 = new Circle(10); // initialize circles with radius of 10
                        circle5.setFill(Color.RED);
                        CmyyntiAsiakkaat.addLast(circle5);
                        visuaalinenTausta.getChildren().add(circle5);
                        visualisointi.asiakasLiikkuu(circle5, "Ymyynti");
                        break;
                    case 6:
                        Circle circle6 = new Circle(10); // initialize circles with radius of 10
                        circle6.setFill(Color.BLUE);
                        CnettiAsiakkaat.addLast(circle6);
                        visuaalinenTausta.getChildren().add(circle6);
                        visualisointi.asiakasLiikkuu(circle6, "Ynetti");
                        break;
                    case 7:
                        Circle circle7 = new Circle(10); // initialize circles with radius of 10
                        circle7.setFill(Color.GREEN);
                        CliittymäAsiakkaat.addLast(circle7);
                        visuaalinenTausta.getChildren().add(circle7);
                        visualisointi.asiakasLiikkuu(circle7, "Yliittymä");
                        break;
                    case 8:
                        Circle circle8 = new Circle(10); // initialize circles with radius of 10
                        circle8.setFill(Color.PINK);
                        ClaskutusAsiakkaat.addLast(circle8);
                        visuaalinenTausta.getChildren().add(circle8);
                        visualisointi.asiakasLiikkuu(circle8, "Ylaskutus");
                        break;
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
        // Poistumistype: "Quitter" / "Palveltu"
        Platform.runLater(new Runnable() {
            public void run() {

                switch (asType) {
                    case 1:
                        Circle circle1 = new Circle(10); // initialize circles with radius of 10
                        circle1.setFill(Color.RED);
                        PoistumyyntiAsiakkaat.addLast(circle1);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterMyyntiAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Pmyynti", poistumisType);
                            poistaMyyntiJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle1);
                            poistaMyyntiJono();
                        }
                        visualisointi.asiakasPoistuu(circle1, "Pmyynti", poistumisType);
                        break;
                    case 2:
                        Circle circle2 = new Circle(10); // initialize circles with radius of 10
                        circle2.setFill(Color.BLUE);
                        PoistunettiAsiakkaat.addLast(circle2);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterNettiAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Pnetti", poistumisType);
                            poistaNettiJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle2);
                            poistaNettiJono();
                        }
                        visualisointi.asiakasPoistuu(circle2, "Pnetti", poistumisType);
                        break;
                    case 3:
                        Circle circle3 = new Circle(10); // initialize circles with radius of 10
                        circle3.setFill(Color.GREEN);
                        PoistuliittymäAsiakkaat.addLast(circle3);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterLiittymäAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Pliittymä", poistumisType);
                            poistaLiittymäJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle3);
                            poistaLiittymäJono();
                        }
                        visualisointi.asiakasPoistuu(circle3, "Pliittymä", poistumisType);
                        break;
                    case 4:
                        Circle circle4 = new Circle(10); // initialize circles with radius of 10
                        circle4.setFill(Color.PINK);
                        PoistulaskutusAsiakkaat.addLast(circle4);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterLaskutusAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Plaskutus", poistumisType);
                            poistaLaskutusJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle4);
                            poistaLaskutusJono();
                        }
                        visualisointi.asiakasPoistuu(circle4, "Plaskutus", poistumisType);
                        break;
                    case 5:
                        Circle circle5 = new Circle(10); // initialize circles with radius of 10
                        circle5.setFill(Color.RED);
                        PoistuCmyyntiAsiakkaat.addLast(circle5);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterCmyyntiAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Ymyynti", poistumisType);
                            CpoistaMyyntiJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle5);
                            CpoistaMyyntiJono();
                        }
                        visualisointi.asiakasPoistuu(circle5, "Ymyynti", poistumisType);
                        break;
                    case 6:
                        Circle circle6 = new Circle(10); // initialize circles with radius of 10
                        circle6.setFill(Color.BLUE);
                        PoistuCnettiAsiakkaat.addLast(circle6);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterCnettiAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Ynetti", poistumisType);
                            CpoistaNettiJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle6);
                            CpoistaNettiJono();
                        }
                        visualisointi.asiakasPoistuu(circle6, "Ynetti", poistumisType);
                        break;
                    case 7:
                        Circle circle7 = new Circle(10); // initialize circles with radius of 10
                        circle7.setFill(Color.GREEN);
                        PoistuCliittymäAsiakkaat.addLast(circle7);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterCliittymäAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Yliittymä", poistumisType);
                            CpoistaLiittymäJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle7);
                            CpoistaLiittymäJono();
                        }
                        visualisointi.asiakasPoistuu(circle7, "Yliittymä", poistumisType);
                        break;
                    case 8:
                        Circle circle8 = new Circle(10); // initialize circles with radius of 10
                        circle8.setFill(Color.PINK);
                        PoistuClaskutusAsiakkaat.addLast(circle8);

                        if (poistumisType.equals("Quitter")) {
                            File diverFile;
                            ImageView ivDiver = new ImageView();
                            diverFile = new File("src/main/resources/com/project/icons/angry.png");
                            Image diverImage = new Image(diverFile.toURI().toString());
                            ivDiver.setImage(diverImage);
                            visuaalinenTausta.getChildren().addAll(ivDiver);
                            quitterClaskutusAsiakkaat.addLast(ivDiver);
                            visualisointi.asiakasSuuttuu(ivDiver, "Ylaskutus", poistumisType);
                            CpoistaLaskutusJono();
                            break;
                        } else {
                            visuaalinenTausta.getChildren().addAll(circle8);
                            CpoistaLaskutusJono();
                        }
                        visualisointi.asiakasPoistuu(circle8, "Ylaskutus", poistumisType);
                        break;
                }
            }
        });
    }

    public void visualisoiReroute(int asType) {
        // Private: myynti = 0, netti = 1, liittymä = 2, laskutus= 3
        // Corporate: myynti = 4, netti = 5, liittymä = 6, laskutus = 7
        Platform.runLater(new Runnable() {
            public void run() {

                switch (asType) {
                    case 0:
                        File diverFile0;
                        ImageView ivDiver0 = new ImageView();
                        diverFile0 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage0 = new Image(diverFile0.toURI().toString());
                        ivDiver0.setImage(diverImage0);
                        visuaalinenTausta.getChildren().addAll(ivDiver0);
                        rerouteMyyntiAsiakkaat.addLast(ivDiver0);
                        visualisointi.asiakasReroute(ivDiver0, 0);
                        break;
                    case 1:
                        File diverFile1;
                        ImageView ivDiver1 = new ImageView();
                        diverFile1 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage1 = new Image(diverFile1.toURI().toString());
                        ivDiver1.setImage(diverImage1);
                        visuaalinenTausta.getChildren().addAll(ivDiver1);
                        rerouteNettiAsiakkaat.addLast(ivDiver1);
                        visualisointi.asiakasReroute(ivDiver1, 1);
                        break;
                    case 2:
                        File diverFile2;
                        ImageView ivDiver2 = new ImageView();
                        diverFile2 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage2 = new Image(diverFile2.toURI().toString());
                        ivDiver2.setImage(diverImage2);
                        visuaalinenTausta.getChildren().addAll(ivDiver2);
                        rerouteLiittymäAsiakkaat.addLast(ivDiver2);
                        visualisointi.asiakasReroute(ivDiver2, 2);
                        break;
                    case 3:
                        File diverFile3;
                        ImageView ivDiver3 = new ImageView();
                        diverFile3 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage3 = new Image(diverFile3.toURI().toString());
                        ivDiver3.setImage(diverImage3);
                        visuaalinenTausta.getChildren().addAll(ivDiver3);
                        rerouteLaskutusAsiakkaat.addLast(ivDiver3);
                        visualisointi.asiakasReroute(ivDiver3, 3);
                        break;
                    case 4:
                        File diverFile4;
                        ImageView ivDiver4 = new ImageView();
                        diverFile4 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage4 = new Image(diverFile4.toURI().toString());
                        ivDiver4.setImage(diverImage4);
                        visuaalinenTausta.getChildren().addAll(ivDiver4);
                        rerouteCmyyntiAsiakkaat.addLast(ivDiver4);
                        visualisointi.asiakasReroute(ivDiver4, 4);
                        break;
                    case 5:
                        File diverFile5;
                        ImageView ivDiver5 = new ImageView();
                        diverFile5 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage5 = new Image(diverFile5.toURI().toString());
                        ivDiver5.setImage(diverImage5);
                        visuaalinenTausta.getChildren().addAll(ivDiver5);
                        rerouteCnettiAsiakkaat.addLast(ivDiver5);
                        visualisointi.asiakasReroute(ivDiver5, 5);
                        break;
                    case 6:
                        File diverFile6;
                        ImageView ivDiver6 = new ImageView();
                        diverFile6 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage6 = new Image(diverFile6.toURI().toString());
                        ivDiver6.setImage(diverImage6);
                        visuaalinenTausta.getChildren().addAll(ivDiver6);
                        rerouteCliittymäAsiakkaat.addLast(ivDiver6);
                        visualisointi.asiakasReroute(ivDiver6, 6);
                        break;
                    case 7:
                        File diverFile7;
                        ImageView ivDiver7 = new ImageView();
                        diverFile7 = new File("src/main/resources/com/project/icons/mistake.png");
                        Image diverImage7 = new Image(diverFile7.toURI().toString());
                        ivDiver7.setImage(diverImage7);
                        visuaalinenTausta.getChildren().addAll(ivDiver7);
                        rerouteClaskutusAsiakkaat.addLast(ivDiver7);
                        visualisointi.asiakasReroute(ivDiver7, 7);
                        break;
                }
            }
        });
    }
}