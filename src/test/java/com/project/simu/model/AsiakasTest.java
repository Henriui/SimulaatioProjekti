package com.project.simu.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import com.project.simu.framework.*;
import com.project.simu.framework.Trace.Level;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AsiakasTest {

     Asiakas as;

     @BeforeEach
     public void setUp() {
          Trace.setTraceLevel(Level.INFO);
          Kello.getInstance().setAika(0);
          Asiakas.resetAsiakasSum();
          Asiakas.resetAsiakasUID();

          for (int i = 0; i < 10; i++) {
               new Asiakas(i, i, null);
          }

          as = new Asiakas(0, 0, new double[] { 25, 50, 75, 100, 25, 50, 75, 100 });
     }

     @Test
     @DisplayName("getId(): Onnistuuko asiakkaan id numeron nouto")
     @Order(1)
     public void testGetId() {
          assertEquals(11, as.getId(), "Asiakkaan id väärin");
     }

     @Test
     @DisplayName("getAsType(): Onnistuuko asiakaspalvelija tyypin integer valuen nouto")
     @Order(2)
     public void testGetAsType() {
          assertEquals(9, as.getAsType(), "Asiakkaan tyyppi on väärin");
     }

     @Test
     @DisplayName("getAsiakasUID(): Asiakkaan unique id haku")
     @Order(3)
     public void testGetAsiakasUID() {
          assertEquals(12, Asiakas.getAsiakasUID(), "Asiakkaan unique id on väärin.");
     }

     // @Test
     // @DisplayName("getAsiakasSum(): Onnistuuko asikkaitten kokonaismäärän haku")
     // @Order(10)
     // public void testGetAsiakasSum(){
     // as.raportti();
     // assertEquals(1, Asiakas.getAsiakasSum(), "Asiakkaiden kokonaismäärä on
     // väärin.");
     // }
}
