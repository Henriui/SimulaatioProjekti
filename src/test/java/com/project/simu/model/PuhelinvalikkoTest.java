package com.project.simu.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import com.project.simu.framework.*;
import com.project.simu.framework.Trace.Level;
import com.project.eduni.distributions.Normal;
import com.project.simu.constants.Tyyppi;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PuhelinvalikkoTest {

     Puhelinvalikko puhelinvalikko;

     @BeforeEach
     public void setUp() {
          Trace.setTraceLevel(Level.INFO);
          Kello.getInstance().setAika(0);
          double aika = 10 * 60;
          Tapahtumalista tapahtumalista = new Tapahtumalista();
          Tyyppi pVTyyppi = Tyyppi.BLENDER_VALIKKO_DEPART;
          Palvelupiste.resetPPUID();
          for (int i = 0; i < 5; i++) {
               new Puhelinvalikko(null, tapahtumalista, pVTyyppi, i);
          }
          puhelinvalikko = new Puhelinvalikko(new Normal(aika - (aika / 2), aika + (aika / 2)), tapahtumalista,
                    pVTyyppi, aika);
     }

     @Test
     @DisplayName("getPPNum(): Onnistuuko palvelupisteen id numeron nouto")
     @Order(1)
     public void testGetPPId() {
          assertEquals(5, puhelinvalikko.getPPId(), "Palvelupisteen id väärin");
     }

     @Test
     @DisplayName("getPPTyyppi(): Onnistuuko palvelupisteen tyypin nouto")
     @Order(2)
     public void testGetPPTyyppi() {
          assertEquals(Tyyppi.BLENDER_VALIKKO_DEPART, puhelinvalikko.getPPTyyppi(),
                    "Palvelupisteen tyyppi on väärin");
     }

     @Test
     @DisplayName("getOnPaikalla(): Puhelinvalikot ovat koko simulaation ajan paikalla ja sen tarkistus")
     @Order(3)
     public void testGetOnPaikalla() {
          assertEquals(true, Kello.getInstance().getAika() == puhelinvalikko.getPpPoistumisAika(),
                    "Puhelinvalikon paikallaolo on väärin.");
          Kello.getInstance().setAika(6000000);
          assertEquals(true, puhelinvalikko.getOnPaikalla(), "Puhelinvalikon paikallaolo on väärin.");
     }
}
