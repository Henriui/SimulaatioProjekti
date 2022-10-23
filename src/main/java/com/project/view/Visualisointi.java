package com.project.view;

import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Luokka animoi näytöllä asiakkaitten tulemisia sekä poistumisia.
 * 
 * @author Jonne Borgman
 */
public class Visualisointi {

    private static double nopeus;
    // Yksityisjonon Y value.
    private static int myyntijono = 260;
    private static int nettijono = 260;
    private static int liittymäjono = 260;
    private static int laskutusjono = 260;
    // Yritysjonon Y value.
    private static int cmyyntijono = 260;
    private static int cnettijono = 260;
    private static int cliittymäjono = 260;
    private static int claskutusjono = 260;

    /**
     * Simulaatoiden välissä alustaa jonon Y valuen 260.
     * 
     * @author Jonne Borgman
     */
    public void alustaJonot() {
        myyntijono = 260;
        nettijono = 260;
        liittymäjono = 260;
        laskutusjono = 260;

        cmyyntijono = 260;
        cnettijono = 260;
        cliittymäjono = 260;
        claskutusjono = 260;
    }

    /**
     * Asettaa animaatioille nopeuden simulaation nopeuden mukaan.
     * 
     * @param viive
     * @author Jonne Borgman
     */
    public void visuaalinenNopeus(long viive) {
        nopeus = viive;
    }

    /**
     * Antaa asiakkaille polun, jota pitkin tietyn tyyppiset asiakkaat liikkuvat.
     * 
     * @param asiakas
     * @param tyyppi
     * @author Jonne Borgman
     */
    public void asiakasLiikkuu(Circle asiakas, String tyyppi) {
        switch (tyyppi) {
            case "Pmyynti":
                // Luodaan polku asiakkaalle.
                Path path = new Path();
                path.getElements().add(new MoveTo(485, 50)); // Aloituspiste.
                path.getElements().add(new LineTo(63, 50)); // Vasemmalle.
                path.getElements().add(new LineTo(63, myyntijono)); // Alas.

                // Asetetaan animaatio käyntiin simulaation nopeudella.
                PathTransition transition = new PathTransition();
                transition.setNode(asiakas);
                transition.setDuration(Duration.millis(nopeus + 100));
                transition.setPath(path);
                transition.setCycleCount(1);
                transition.play();
                myyntijono -= 20;
                break;
            case "Pnetti":
                Path path2 = new Path();
                path2.getElements().add(new MoveTo(485, 50)); // Aloituspiste.
                path2.getElements().add(new LineTo(183, 50)); // Vasemmalle.
                path2.getElements().add(new LineTo(183, nettijono)); // Alas.

                PathTransition transition2 = new PathTransition();
                transition2.setNode(asiakas);
                transition2.setDuration(Duration.millis(nopeus + 100));
                transition2.setPath(path2);
                transition2.setCycleCount(1);
                transition2.play();
                nettijono -= 20;
                break;
            case "Pliittymä":
                Path path3 = new Path();
                path3.getElements().add(new MoveTo(485, 50)); // Aloituspiste.
                path3.getElements().add(new LineTo(300, 50)); // Vasemmalle.
                path3.getElements().add(new LineTo(300, liittymäjono)); // Alas.

                PathTransition transition3 = new PathTransition();
                transition3.setNode(asiakas);
                transition3.setDuration(Duration.millis(nopeus + 100));
                transition3.setPath(path3);
                transition3.setCycleCount(1);
                transition3.play();
                liittymäjono -= 20;
                break;
            case "Plaskutus":
                Path path4 = new Path();
                path4.getElements().add(new MoveTo(485, 50)); // Aloituspiste.
                path4.getElements().add(new LineTo(418, 50)); // Vasemmalle.
                path4.getElements().add(new LineTo(418, laskutusjono)); // Alas.

                PathTransition transition4 = new PathTransition();
                transition4.setNode(asiakas);
                transition4.setDuration(Duration.millis(nopeus + 100));
                transition4.setPath(path4);
                transition4.setCycleCount(1);
                transition4.play();
                laskutusjono -= 20;
                break;

            case "Ymyynti":
                Path path5 = new Path();
                path5.getElements().add(new MoveTo(565, 50)); // Aloituspiste.
                path5.getElements().add(new LineTo(977, 50)); // Oikealle.
                path5.getElements().add(new LineTo(977, cmyyntijono)); // Alas.

                PathTransition transition5 = new PathTransition();
                transition5.setNode(asiakas);
                transition5.setDuration(Duration.millis(nopeus + 100));
                transition5.setPath(path5);
                transition5.setCycleCount(1);
                transition5.play();
                cmyyntijono -= 20;
                break;
            case "Ynetti":
                Path path6 = new Path();
                path6.getElements().add(new MoveTo(565, 50)); // Aloituspiste.
                path6.getElements().add(new LineTo(857, 50)); // Oikealle.
                path6.getElements().add(new LineTo(857, cnettijono)); // Alas.

                PathTransition transition6 = new PathTransition();
                transition6.setNode(asiakas);
                transition6.setDuration(Duration.millis(nopeus + 100));
                transition6.setPath(path6);
                transition6.setCycleCount(1);
                transition6.play();
                cnettijono -= 20;
                break;
            case "Yliittymä":
                Path path7 = new Path();
                path7.getElements().add(new MoveTo(565, 50)); // Aloituspiste.
                path7.getElements().add(new LineTo(740, 50)); // Oikealle.
                path7.getElements().add(new LineTo(740, cliittymäjono)); // Alas.

                PathTransition transition7 = new PathTransition();
                transition7.setNode(asiakas);
                transition7.setDuration(Duration.millis(nopeus + 100));
                transition7.setPath(path7);
                transition7.setCycleCount(1);
                transition7.play();
                cliittymäjono -= 20;
                break;
            case "Ylaskutus":
                Path path8 = new Path();
                path8.getElements().add(new MoveTo(565, 50)); // Aloituspiste.
                path8.getElements().add(new LineTo(622, 50)); // Oikealle.
                path8.getElements().add(new LineTo(622, claskutusjono)); // Alas.

                PathTransition transition8 = new PathTransition();
                transition8.setNode(asiakas);
                transition8.setDuration(Duration.millis(nopeus + 100));
                transition8.setPath(path8);
                transition8.setCycleCount(1);
                transition8.play();
                claskutusjono -= 20;
                break;
        }
    }

    /**
     * Jos asiakas suuttuu, luodaan poistuminen kuvalla animoimalla.
     * 
     * @param image
     * @param tyyppi
     * @param poistumusType
     * @author Jonne Borgman
     */
    public void asiakasSuuttuu(ImageView image, String tyyppi, String poistumusType) {

        switch (tyyppi) {
            case "Pmyynti":
                // Quitteri
                myyntijono += 20;
                Path path = new Path();
                path.getElements().add(new MoveTo(63, 401)); // Aloituspiste
                path.getElements().add(new LineTo(63, 800)); // Alaspäin

                PathTransition transition = new PathTransition();
                transition.setNode(image);
                transition.setDuration(Duration.millis(nopeus + 1000));
                transition.setPath(path);
                transition.setCycleCount(1);
                transition.play();
                break;

            case "Pnetti":
                nettijono += 20;
                Path path2 = new Path();
                path2.getElements().add(new MoveTo(183, 401)); // Aloituspiste
                path2.getElements().add(new LineTo(183, 800)); // Alaspäin

                PathTransition transition2 = new PathTransition();
                transition2.setNode(image);
                transition2.setDuration(Duration.millis(nopeus + 1000));
                transition2.setPath(path2);
                transition2.setCycleCount(1);
                transition2.play();
                break;
            case "Pliittymä":
                liittymäjono += 20;
                Path path3 = new Path();
                path3.getElements().add(new MoveTo(300, 401)); // Aloituspiste
                path3.getElements().add(new LineTo(300, 800)); // Alaspäin

                PathTransition transition3 = new PathTransition();
                transition3.setNode(image);
                transition3.setDuration(Duration.millis(nopeus + 1000));
                transition3.setPath(path3);
                transition3.setCycleCount(1);
                transition3.play();
                break;
            case "Plaskutus":
                laskutusjono += 20;
                Path path4 = new Path();
                path4.getElements().add(new MoveTo(418, 401)); // Aloituspiste
                path4.getElements().add(new LineTo(418, 800)); // Alaspäin

                PathTransition transition4 = new PathTransition();
                transition4.setNode(image);
                transition4.setDuration(Duration.millis(nopeus + 1000));
                transition4.setPath(path4);
                transition4.setCycleCount(1);
                transition4.play();
                break;
            case "Ymyynti":
                cmyyntijono += 20;
                Path path5 = new Path();
                path5.getElements().add(new MoveTo(977, 401)); // Aloituspiste
                path5.getElements().add(new LineTo(977, 800)); // Alaspäin

                PathTransition transition5 = new PathTransition();
                transition5.setNode(image);
                transition5.setDuration(Duration.millis(nopeus + 1000));
                transition5.setPath(path5);
                transition5.setCycleCount(1);
                transition5.play();
                break;

            case "Ynetti":
                cnettijono += 20;
                Path path6 = new Path();
                path6.getElements().add(new MoveTo(857, 401)); // Aloituspiste
                path6.getElements().add(new LineTo(857, 800)); // Alaspäin

                PathTransition transition6 = new PathTransition();
                transition6.setNode(image);
                transition6.setDuration(Duration.millis(nopeus + 1000));
                transition6.setPath(path6);
                transition6.setCycleCount(1);
                transition6.play();
                break;

            case "Yliittymä":
                cliittymäjono += 20;
                Path path7 = new Path();
                path7.getElements().add(new MoveTo(740, 401)); // Aloituspiste
                path7.getElements().add(new LineTo(740, 800)); // Alaspäin

                PathTransition transition7 = new PathTransition();
                transition7.setNode(image);
                transition7.setDuration(Duration.millis(nopeus + 1000));
                transition7.setPath(path7);
                transition7.setCycleCount(1);
                transition7.play();
                break;

            case "Ylaskutus":
                claskutusjono += 20;
                Path path8 = new Path();
                path8.getElements().add(new MoveTo(622, 401)); // Aloituspiste
                path8.getElements().add(new LineTo(622, 800)); // Alaspäin

                PathTransition transition8 = new PathTransition();
                transition8.setNode(image);
                transition8.setDuration(Duration.millis(nopeus + 1000));
                transition8.setPath(path8);
                transition8.setCycleCount(1);
                transition8.play();
                break;
        }
    }

    /**
     * Jos asiakas on tehnyt väärän valinnan, luodaan reroute kuvalla animoimalla.
     * 
     * @param image
     * @param tyyppi
     * 
     * @author Jonne Borgman
     */
    public void asiakasReroute(ImageView image, int tyyppi) {

        switch (tyyppi) {
            case 1:
                myyntijono += 20;
                Path path = new Path();
                path.getElements().add(new MoveTo(63, 401)); // Aloituspiste
                path.getElements().add(new LineTo(63, 504)); // Alaspäin
                path.getElements().add(new LineTo(524, 504)); // Vasemmalle
                path.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition = new PathTransition();
                transition.setNode(image);
                transition.setDuration(Duration.millis(nopeus + 1000));
                transition.setPath(path);
                transition.setCycleCount(1);
                transition.play();
                break;

            case 2:
            nettijono += 20;
                Path path2 = new Path();
                path2.getElements().add(new MoveTo(183, 401)); // Aloituspiste
                path2.getElements().add(new LineTo(183, 504)); // Alaspäin
                path2.getElements().add(new LineTo(524, 504)); // Vasemmalle
                path2.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition2 = new PathTransition();
                transition2.setNode(image);
                transition2.setDuration(Duration.millis(nopeus + 1000));
                transition2.setPath(path2);
                transition2.setCycleCount(1);
                transition2.play();
                break;
            case 3:
            liittymäjono += 20;
                Path path3 = new Path();
                path3.getElements().add(new MoveTo(300, 401)); // Aloituspiste
                path3.getElements().add(new LineTo(300, 504)); // Alaspäin
                path3.getElements().add(new LineTo(524, 504)); // Vasemmalle
                path3.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition3 = new PathTransition();
                transition3.setNode(image);
                transition3.setDuration(Duration.millis(nopeus + 1000));
                transition3.setPath(path3);
                transition3.setCycleCount(1);
                transition3.play();
                break;
            case 4:
            laskutusjono += 20;
                Path path4 = new Path();
                path4.getElements().add(new MoveTo(418, 401)); // Aloituspiste
                path4.getElements().add(new LineTo(418, 504)); // Alaspäin
                path4.getElements().add(new LineTo(524, 504)); // Vasemmalle
                path4.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition4 = new PathTransition();
                transition4.setNode(image);
                transition4.setDuration(Duration.millis(nopeus + 1000));
                transition4.setPath(path4);
                transition4.setCycleCount(1);
                transition4.play();
                break;
            case 5:
            cmyyntijono += 20;
                Path path5 = new Path();
                path5.getElements().add(new MoveTo(977, 401)); // Aloituspiste
                path5.getElements().add(new LineTo(977, 504)); // Alaspäin
                path5.getElements().add(new LineTo(524, 504)); // Oikealle
                path5.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition5 = new PathTransition();
                transition5.setNode(image);
                transition5.setDuration(Duration.millis(nopeus + 1000));
                transition5.setPath(path5);
                transition5.setCycleCount(1);
                transition5.play();
                break;

            case 6:
            cnettijono += 20;
                Path path6 = new Path();
                path6.getElements().add(new MoveTo(857, 401)); // Aloituspiste
                path6.getElements().add(new LineTo(857, 504)); // Alaspäin
                path6.getElements().add(new LineTo(524, 504)); // Oikealle
                path6.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition6 = new PathTransition();
                transition6.setNode(image);
                transition6.setDuration(Duration.millis(nopeus + 1000));
                transition6.setPath(path6);
                transition6.setCycleCount(1);
                transition6.play();
                break;

            case 7:
            cliittymäjono += 20;
                Path path7 = new Path();
                path7.getElements().add(new MoveTo(740, 401)); // Aloituspiste
                path7.getElements().add(new LineTo(740, 504)); // Alaspäin
                path7.getElements().add(new LineTo(524, 504)); // Oikealle
                path7.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition7 = new PathTransition();
                transition7.setNode(image);
                transition7.setDuration(Duration.millis(nopeus + 1000));
                transition7.setPath(path7);
                transition7.setCycleCount(1);
                transition7.play();
                break;

            case 8:
            claskutusjono += 20;
                Path path8 = new Path();
                path8.getElements().add(new MoveTo(622, 401)); // Aloituspiste
                path8.getElements().add(new LineTo(622, 504)); // Alaspäin
                path8.getElements().add(new LineTo(524, 504)); // Oikealle
                path8.getElements().add(new LineTo(524, 40)); // Ylös

                PathTransition transition8 = new PathTransition();
                transition8.setNode(image);
                transition8.setDuration(Duration.millis(nopeus + 1000));
                transition8.setPath(path8);
                transition8.setCycleCount(1);
                transition8.play();
                break;
        }
    }

    /**
     * Asiakkaan poistumis polun määrittäminen.
     * 
     * @param asiakas
     * @param tyyppi
     * @param poistumusType
     * @author Jonne Borgman
     */
    public void asiakasPoistuu(Circle asiakas, String tyyppi, String poistumusType) {
        Circle circle = new Circle(100);
        circle.setRadius(200);

        circle.setFill(Color.BLUE);
        switch (tyyppi) {
            case "Pmyynti":
                if (poistumusType.equals("Palveltu")) {
                    myyntijono += 20;
                    Path path = new Path();
                    path.getElements().add(new MoveTo(63, 401)); // Aloituspiste
                    path.getElements().add(new LineTo(63, 504)); // Alaspäin
                    path.getElements().add(new LineTo(524, 504)); // Vasemmalle
                    path.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition = new PathTransition();
                    transition.setNode(asiakas);
                    transition.setDuration(Duration.millis(nopeus + 100));
                    transition.setPath(path);
                    transition.setCycleCount(1);
                    transition.play();
                    break;
                }

            case "Pnetti":
                if (poistumusType.equals("Palveltu")) {
                    nettijono += 20;
                    Path path2 = new Path();
                    path2.getElements().add(new MoveTo(183, 401)); // Aloituspiste
                    path2.getElements().add(new LineTo(183, 504)); // Alaspäin
                    path2.getElements().add(new LineTo(524, 504)); // Vasemmalle
                    path2.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition2 = new PathTransition();
                    transition2.setNode(asiakas);
                    transition2.setDuration(Duration.millis(nopeus + 100));
                    transition2.setPath(path2);
                    transition2.setCycleCount(1);
                    transition2.play();
                    break;
                }

            case "Pliittymä":
                if (poistumusType.equals("Palveltu")) {
                    liittymäjono += 20;
                    Path path3 = new Path();
                    path3.getElements().add(new MoveTo(300, 401)); // Aloituspiste
                    path3.getElements().add(new LineTo(300, 504)); // Alaspäin
                    path3.getElements().add(new LineTo(524, 504)); // Vasemmalle
                    path3.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition3 = new PathTransition();
                    transition3.setNode(asiakas);
                    transition3.setDuration(Duration.millis(nopeus + 100));
                    transition3.setPath(path3);
                    transition3.setCycleCount(1);
                    transition3.play();
                    break;
                }

            case "Plaskutus":
                if (poistumusType.equals("Palveltu")) {
                    laskutusjono += 20;
                    Path path4 = new Path();
                    path4.getElements().add(new MoveTo(418, 401)); // Aloituspiste
                    path4.getElements().add(new LineTo(418, 504)); // Alaspäin
                    path4.getElements().add(new LineTo(524, 504)); // Vasemmalle
                    path4.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition4 = new PathTransition();
                    transition4.setNode(asiakas);
                    transition4.setDuration(Duration.millis(nopeus + 100));
                    transition4.setPath(path4);
                    transition4.setCycleCount(1);
                    transition4.play();
                    break;
                }

            case "Ymyynti":
                if (poistumusType.equals("Palveltu")) {
                    cmyyntijono += 20;
                    Path path5 = new Path();
                    path5.getElements().add(new MoveTo(977, 401)); // Aloituspiste
                    path5.getElements().add(new LineTo(977, 504)); // Alaspäin
                    path5.getElements().add(new LineTo(524, 504)); // Oikealle
                    path5.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition5 = new PathTransition();
                    transition5.setNode(asiakas);
                    transition5.setDuration(Duration.millis(nopeus + 100));
                    transition5.setPath(path5);
                    transition5.setCycleCount(1);
                    transition5.play();
                    break;
                }

            case "Ynetti":
                if (poistumusType.equals("Palveltu")) {
                    cnettijono += 20;
                    Path path6 = new Path();
                    path6.getElements().add(new MoveTo(857, 401)); // Aloituspiste
                    path6.getElements().add(new LineTo(857, 504)); // Alaspäin
                    path6.getElements().add(new LineTo(524, 504)); // Oikealle
                    path6.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition6 = new PathTransition();
                    transition6.setNode(asiakas);
                    transition6.setDuration(Duration.millis(nopeus + 100));
                    transition6.setPath(path6);
                    transition6.setCycleCount(1);
                    transition6.play();
                    break;
                }

            case "Yliittymä":
                if (poistumusType.equals("Palveltu")) {
                    cliittymäjono += 20;
                    Path path7 = new Path();
                    path7.getElements().add(new MoveTo(740, 401)); // Aloituspiste
                    path7.getElements().add(new LineTo(740, 504)); // Alaspäin
                    path7.getElements().add(new LineTo(524, 504)); // Oikealle
                    path7.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition7 = new PathTransition();
                    transition7.setNode(asiakas);
                    transition7.setDuration(Duration.millis(nopeus + 100));
                    transition7.setPath(path7);
                    transition7.setCycleCount(1);
                    transition7.play();
                    break;
                }

            case "Ylaskutus":
                if (poistumusType.equals("Palveltu")) {
                    claskutusjono += 20;
                    Path path8 = new Path();
                    path8.getElements().add(new MoveTo(622, 401)); // Aloituspiste
                    path8.getElements().add(new LineTo(622, 504)); // Alaspäin
                    path8.getElements().add(new LineTo(524, 504)); // Oikealle
                    path8.getElements().add(new LineTo(524, 574)); // Alas ja poistuu

                    PathTransition transition8 = new PathTransition();
                    transition8.setNode(asiakas);
                    transition8.setDuration(Duration.millis(nopeus + 100));
                    transition8.setPath(path8);
                    transition8.setCycleCount(1);
                    transition8.play();
                    break;
                }
        }
    }
}
