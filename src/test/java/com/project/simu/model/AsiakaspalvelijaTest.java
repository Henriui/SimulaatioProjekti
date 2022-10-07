package com.project.simu.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import com.project.simu.constants.*;
import com.project.simu.framework.*;
import com.project.simu.framework.Trace.Level;
import com.project.eduni.distributions.Normal;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AsiakaspalvelijaTest {

     Asiakaspalvelija asiakaspalvelija;
     double[] asJakaumaArray;

     @BeforeEach
     public void setUp() {
          Trace.setTraceLevel(Level.INFO);
          Kello.getInstance().setAika(0);
          double aika = 10 * 60;
          Tapahtumalista tapahtumalista = new Tapahtumalista();
          Palvelupiste.resetPPUID();
          Tyyppi aPTyyppi = Tyyppi.PRI_INVOICE_DEPART;
          Tyovuoro tV = Tyovuoro.EIGHT;
          asJakaumaArray = new double[] { 25, 50, 75, 100, 25, 50, 75, 100 };

          for (int i = 0; i < 5; i++) {
               new Asiakaspalvelija(null, tapahtumalista, aPTyyppi, aika, tV);
          }

          asiakaspalvelija = new Asiakaspalvelija(new Normal(aika - (aika / 2), aika + (aika / 2)),
                    tapahtumalista, aPTyyppi, aika, tV);
     }

     @Test
     @DisplayName("getPPNum(): Onnistuuko palvelupisteen id numeron nouto")
     @Order(1)
     public void testGetPPId() {
          assertEquals(5, asiakaspalvelija.getPPId(), "Palvelupisteen id väärin");
     }

     @Test
     @DisplayName("getPPTyyppi(): Onnistuuko asiakaspalvelija tyypin nouto")
     @Order(2)
     public void testGetPPTyyppi() {
          assertEquals(Tyyppi.PRI_INVOICE_DEPART, asiakaspalvelija.getPPTyyppi(),
                    "Asiakaspalvelija tyyppi on väärin");
     }

     @Test
     @DisplayName("getTv(): Onnistuuko työvuoron nouto")
     @Order(3)
     public void testGetTv() {
          assertEquals(Tyovuoro.EIGHT, asiakaspalvelija.getTv(), "Asiakaspalvelijan työvuoro on väärin");
     }

     @Test
     @DisplayName("getOnPaikalla(): Onnistuuko työvuoron vaihtaminen ja sen tarkistus")
     @Order(4)
     public void testGetOnPaikalla() {
          Kello.getInstance().setAika(0);
          asiakaspalvelija.setTv(1);
          assertEquals(false, asiakaspalvelija.getOnPaikalla(), "Asiakaspalvelijan paikallaolo on väärin.");
     }

     @Test
     @DisplayName("addJonoon(): Asiakaspalvelijan jonon pitää palauttaa sinne lisätyt asiakkaat")
     @Order(5)
     public void testAddJonoon() {
          Kello.getInstance().setAika(3600);
          for (int i = 0; i < 5; i++) {
               asiakaspalvelija.addJonoon(new Asiakas(0, 0, asJakaumaArray));
          }
          assertEquals(5, asiakaspalvelija.getAsLisattyJonoon(), "Asiakaspalvelijan jonoon muuttuja ei lisäännyt");
     }

     @Test
     @DisplayName("aloitaPalvelu(): Asiakaspalvelijan pitää pystyä palvelemaan asiakkaita jonosta")
     @Order(6)
     public void testAloitaPalvelu() {
          asiakaspalvelija.addJonoon(new Asiakas(0, 0, asJakaumaArray));
          asiakaspalvelija.aloitaPalvelu();
          assertEquals(1, asiakaspalvelija.getAsPalveltuJonosta(), "Asiakasta ei pystytty palvelemaan.");
     }

     @Test
     @DisplayName("otaJonosta(): Asiakaspalvelijan pitää pystyä ottamaan palveltu asiakas pois jonostaan")
     @Order(7)
     public void testOtaJonosta() {
          for (int i = 0; i < 5; i++) {
               asiakaspalvelija.addJonoon(new Asiakas(0, 0, asJakaumaArray));
          }
          asiakaspalvelija.aloitaPalvelu();
          Asiakas as = asiakaspalvelija.otaJonosta();
          assertEquals(4, asiakaspalvelija.getJonoKoko(),
                    "Asiakaspalvelijan jonossa on ei ole oikea määrä asiakkaita");
     }

     @Test
     @DisplayName("getAsPoistunutJonosta(): Asiakkaan poistaminen jää muuttujaan")
     @Order(8)
     public void testKyllastyiJonoon() {
          Kello.getInstance().setAika(3600);
          asiakaspalvelija.addJonoon(new Asiakas(0, 0, asJakaumaArray));
          Kello.getInstance().setAika(6000);
          asiakaspalvelija.aloitaPalvelu();
          assertEquals(1, asiakaspalvelija.getAsPoistunutJonosta(), "Asiakaspalvelijan poistuja muuttuja ei toimi.");
     }

     @Test
     @DisplayName("otaJonosta(): Asiakkaan poistuminen jonosta maxJonoPituuden puitteissa")
     @Order(9)
     public void testOtaJonosta2() {
          Kello.getInstance().setAika(3600);
          Asiakas as = new Asiakas(0, 0, asJakaumaArray);
          asiakaspalvelija.addJonoon(as);
          asiakaspalvelija.aloitaPalvelu();
          Kello.getInstance().setAika(4200);
          as = asiakaspalvelija.otaJonosta();
          double maxJonoPituus = as.getAsSaapumisaika() + asiakaspalvelija.getMaxJononPituus();
          assertEquals(true, maxJonoPituus == as.getAsPoistumisaikaPP(),
                    "Asiakaspalvelijan jonosta poistui asiakas väärään aikaan.");
     }

     @Test
     @DisplayName("getReRoutedJonosta(): Testaa Asiakaspalvelija luokan reroute ominaisuutta")
     @Order(10)
     public void testSetTunnus3() {
          Asiakas as = new Asiakas(100, 0, asJakaumaArray);
          asiakaspalvelija.addJonoon(as);
          asiakaspalvelija.aloitaPalvelu();
          as = asiakaspalvelija.otaJonosta();
          Kello.getInstance().setAika(1000);
          as.setReRouted();
          asiakaspalvelija.addJonoon(as);
          asiakaspalvelija.aloitaPalvelu();
          assertEquals(1, asiakaspalvelija.getAsReRoutedJonosta(), "Asiakaspalvelijoiden rerouttaaminen ei toimi.");
     }
}
