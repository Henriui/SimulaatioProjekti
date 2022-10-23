package com.project.simu.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class AsetuksetTallennusTest {
    UserAsetuksetController uAC;
    UserAsetukset uA;

    @BeforeEach
    private void setUp(){
        uA = new UserAsetukset("kissa", "pasi", "kuikka");
    }

    @Test
    @DisplayName("userAsetukset tallennus ja haku testi")
    private void userAsetuksetTest(){
        uAC.kirjoitaTiedostoonDbParametrit(uA);
        UserAsetukset uAT = uAC.lueTiedostostaDbParametrit();
        assertEquals(uA, uAT, "Asetukset eivät täsmää");
    }
}
