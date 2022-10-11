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

    /**
     * Alustaa parametrit sekä toistaa animaation kun näkymä avataan.
     * @author Jonne Borgman
     */
    @FXML
    public void initialize() {
        uP = new Parametrit();
        new animatefx.animation.ZoomIn();
        ZoomIn trans1 = new ZoomIn(backGround);
        new animatefx.util.ParallelAnimationFX(trans1).play();
        System.out.println("Start");
    }

    /**
     * Vie näkymän takaisin pää-näkymälle.
     * @throws IOException
     * @author Jonne Borgman
     */
    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }

    /**
     * Tyhjentää näkymän asiakkaista, ja käynnistää simulaation.
     * @throws IOException
     * @author Jonne Borgman
     */
    @FXML
    public void aloitaSimulaatio() throws InterruptedException {
        alustaAsiakkaat();
        if (!simulationRunning && !pallotNäytöllä ) {
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
     * @throws IOException
     * @author Jonne Borgman
     */
    @FXML
    public void setSuureet() throws IOException {
        if (!open) {
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
     * TODO:     * @param sS
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
     * TODO:     * @param sS
     * @throws IOException
     * @author Lassi
     */
    public void runTulokset(SimulaatioData sS) throws IOException {
        if (!open) {
            FXMLLoader loader = loadFXML("tuloksetDetailedPopUp");
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

            TuloksedDetailedController controller = loader.getController();
            controller.setSimulationController(this);
            controller.setSimulaationSuureet(sS);
            stage.show();
            controller.updateValues();
            open = true;
        }
    }

    /**
     * Tarkistaa onko pop-up ikkuna auki.
     * @param isOpen
     * @author Jonne Borgman
     */
    public void popupOpen(boolean isOpen) {
        open = isOpen;
    }

    /**
     * TODO:     * @return
     */
    public Parametrit getParametri() {
        return uP;
    }

    /**
     * TODO:     * @param parametri
     */
    public void setParametri(Parametrit parametri) {
        uP = parametri;
    }

    /**
     * Hakee kutsuessa oikean FXML tiedoston oikeasta paikasta.
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
     * TODO:
     * @param suureStatusMap
     * 
     * @author Rasmus Hyyppä
     */
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
                // suureStatusMap.get("Varattu")[i] Palvelupisteistä kuinka monta on varattuna!
                // suureStatusMap.get("Totalit")[i]
                // suureStatusMap.get("Palveluprosentti")[i]

                // Esim.
                int yksityisTv = 0;
                int yritysTv = 0;
                for (int i = 0; i < suureStatusMap.get("Varattu").length; i++) {
                    if (i < 4) {
                        yksityisTv += suureStatusMap.get("Tyovuorossa")[i];
                        int myynti = suureStatusMap.get("Tyovuorossa")[0];
                        int netti = suureStatusMap.get("Tyovuorossa")[1];
                        int liittymä = suureStatusMap.get("Tyovuorossa")[2];
                        int laskutus = suureStatusMap.get("Tyovuorossa")[3];
                        switch (i) {
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
                        int Ymyynti = suureStatusMap.get("Tyovuorossa")[0];
                        int Ynetti = suureStatusMap.get("Tyovuorossa")[1];
                        int Yliittymä = suureStatusMap.get("Tyovuorossa")[2];
                        int Ylaskutus = suureStatusMap.get("Tyovuorossa")[3];
                        switch (i) {
                            case 4:
                                System.out.println("Myynnin työvuorossa = " + Ymyynti);
                                CmyyntiTv.setText(String.valueOf(Ymyynti));
                                break;
                            case 5:
                                System.out.println("Netin työvuorossa = " + Ynetti);
                                CnettiTv.setText(String.valueOf(Ynetti));
                                break;
                            case 6:
                                System.out.println("Liittymä työvuorossa = " + Yliittymä);
                                CliittymäTv.setText(String.valueOf(Yliittymä));
                                break;
                            case 7:
                                System.out.println("Laskutus työvuorossa = " + Ylaskutus);
                                ClaskutusTv.setText(String.valueOf(Ylaskutus));
                                break;
                        }
                    }

                }
                // yksityisPalvelupisteita.setText("Palvelupisteitä: " +
                // String.valueOf(yksityisTv));
                // yritysPalvelupisteita.setText("Palvelupisteitä: " +
                // String.valueOf(yritysTv));
                int yksityisPalvelu = 0;
                int yritysPalvelu = 0;
                for (int i = 0; i < suureStatusMap.get("Palveltu").length; i++) {
                    if (i < 4) {
                        yksityisPalvelu += suureStatusMap.get("Palveltu")[i];
                    } else if (i > 3 && i < 8) {
                        yritysPalvelu += suureStatusMap.get("Palveltu")[i];
                    }
                }
                // palvelupisteellaYksityis.setText("Palveltuja as " +
                // String.valueOf(yksityisPalvelu));
                // palvelupisteellaYritys.setText("Palveltuja as " +
                // String.valueOf(yritysPalvelu));

                int jonoYksityis = 0;
                int jonoYritys = 0;
                for (int i = 0; i < suureStatusMap.get("Jonossa").length; i++) {
                    if (i < 4) {
                        jonoYksityis += suureStatusMap.get("Jonossa")[i];
                    } else if (i > 3 && i < 8) {
                        jonoYritys += suureStatusMap.get("Jonossa")[i];
                    }
                }
                // yksityisJonossa.setText(String.valueOf(jonoYksityis));
                // YritysJonossa.setText(String.valueOf(jonoYritys));

                // "Totalit" [0] = asiakkaitten kokonaismäärä simulaatiossa
                // "Totalit" [1] = asiakkaita palveltu simulaatiossa
                // "Totalit" [2] = asiakkaitta quitannut jonosta simulaatiossa
                // "Totalit" [3] = asiakkaita reroutattu simulaatiossa
                // "Totalit" [4] = simulaationaika
                // "Totalit" [5] = palveluprosentti

                // Esim.
                int kokonaisMaara = 0;
                int ulkona = 0;
                for (int i = 0; i < suureStatusMap.get("Totalit").length; i++) {
                    if (i == 0) {
                        // asiakkaitten kokonaismäärä simulaatiossa
                        kokonaisMaara = suureStatusMap.get("Totalit")[i];
                    } else if (i > 0 && i < 3) {
                        // ulkona asiakkaita simulaatiosta (palveltu[1]+quitterit[2])
                        ulkona += suureStatusMap.get("Totalit")[i];
                    } else if (i == 4) {
                        // asiakkaita rerouttattu simulaatiossa
                    } else if (i == 5) {
                        // simulointiaika currently
                    }
                }
                kokonaismäärä.setText("Total: " + String.valueOf(ulkona));
            }
        });
    }

    /**
     * Hidastaa simulaatiolla sekä animaatioita 5ms.
     * @author Jonne Borgman
     */
    @Override
    public void hidastaSimulaatiota() {
        m.setViive(m.getViive() + 5);
        visualisointi.visuaalinenNopeus(m.getViive());
    }

    /**
     * Nopeuttaa simulaatiolla sekä animaatioita 5ms.
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
     * @author Jonne Borgman
     */
     public void alustaAsiakkaat(){
        // Alustaa jonojen Y valuet.
        visualisointi.alustaJonot();

        // Yksityisasiakkaat.
        while (myyntiAsiakkaat.iterator().hasNext()) { 
            Circle m = myyntiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(m);
            myyntiAsiakkaat.removeFirst();
        } 
        while (nettiAsiakkaat.iterator().hasNext()) { 
            Circle mn = nettiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(mn);
            nettiAsiakkaat.removeFirst();
        } 
        while (liittymäAsiakkaat.iterator().hasNext()) { 
            Circle mli = liittymäAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(mli);
            liittymäAsiakkaat.removeFirst();
        } 
        while (laskutusAsiakkaat.iterator().hasNext()) { 
            Circle mla = laskutusAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(mla);
            laskutusAsiakkaat.removeFirst();
        } 
        while (CmyyntiAsiakkaat.iterator().hasNext()) { 
            Circle mcm = CmyyntiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(mcm);
            CmyyntiAsiakkaat.removeFirst();
        } 
        while (CnettiAsiakkaat.iterator().hasNext()) { 
            Circle mcn = CnettiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(mcn);
            CnettiAsiakkaat.removeFirst();
        } 
        while (CliittymäAsiakkaat.iterator().hasNext()) { 
            Circle mcli = CliittymäAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(mcli);
            CliittymäAsiakkaat.removeFirst();
        } 
        while (ClaskutusAsiakkaat.iterator().hasNext()) { 
            Circle mcla = ClaskutusAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(mcla);
            ClaskutusAsiakkaat.removeFirst();
        }
        // Yritys asiakkaat.
        while (PoistumyyntiAsiakkaat.iterator().hasNext()) { 
            Circle Pm = PoistumyyntiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pm);
            PoistumyyntiAsiakkaat.removeFirst();
        } 
        while (PoistunettiAsiakkaat.iterator().hasNext()) { 
            Circle Pmn = PoistunettiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pmn);
            PoistunettiAsiakkaat.removeFirst();
        } 
        while (PoistuliittymäAsiakkaat.iterator().hasNext()) { 
            Circle Pmli = PoistuliittymäAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pmli);
            PoistuliittymäAsiakkaat.removeFirst();
        } 
        while (PoistulaskutusAsiakkaat.iterator().hasNext()) { 
            Circle Pmla = PoistulaskutusAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pmla);
            PoistulaskutusAsiakkaat.removeFirst();
        } 
        while (PoistuCmyyntiAsiakkaat.iterator().hasNext()) { 
            Circle Pmcm = PoistuCmyyntiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pmcm);
            PoistuCmyyntiAsiakkaat.removeFirst();
        } 
        while (PoistuCnettiAsiakkaat.iterator().hasNext()) { 
            Circle Pmcn = PoistuCnettiAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pmcn);
            PoistuCnettiAsiakkaat.removeFirst();
        } 
        while (PoistuCliittymäAsiakkaat.iterator().hasNext()) { 
            Circle Pmcli = PoistuCliittymäAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pmcli);
            PoistuCliittymäAsiakkaat.removeFirst();
        } 
        while (PoistuClaskutusAsiakkaat.iterator().hasNext()) { 
            Circle Pmcla = PoistuClaskutusAsiakkaat.getFirst();
            visuaalinenTausta.getChildren().remove(Pmcla);
            PoistuClaskutusAsiakkaat.removeFirst();
        }
        pallotNäytöllä = false;
    }
   
    /**
     * Asiakkaan lähtiessä myynnin jonosta, poistaa asiakkaan listasta,
     * ja liikuttaa koko jonoa 20pikseliä alemmas.
     * @author Jonne Borgman
     */
    public void poistaMyyntiJono() 
    {
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
     * @author Jonne Borgman
     */
    public void CpoistaMyyntiJono() 
    {
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
     * Luo asiakas pallon, lisää sen listaan ja kutsuu visualisoinnista asiakkaalle animaation.
     * @param asType
     * @author Jonne Borgman
     */
    @Override
    public void visualisoiAsiakas(int asType) {
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
     * Jos asiakas on Palveltu, saa hän normaalin poistumis animaation visualisointi luokalta.
     * Jos asiakas on Quitter, luodaan asiakkaalle image, ja visualisointi luokalta antaa quit animaation.
     * @param asType
     * @param poistumusType
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
}