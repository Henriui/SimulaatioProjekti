package com.project.simu.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

public class UserAsetuksetControllerTest {
    private UserAsetuksetController uAC;
    private UserAsetukset uA;

    @BeforeEach
    private void setUp(){
        uA = new UserAsetukset("kissa", "pasi", "kuikka");
    }

    @Test
    void testKirjoitaTiedostoonDbParametrit() {
        assertTrue(uAC.kirjoitaTiedostoonDbParametrit(uA), "Tiedostoon kirjoittamin ei onnistunut");
    }

    @Test
    void testLueTiedostostaDbParametrit() {
        UserAsetukset uAT = uAC.lueTiedostostaDbParametrit();
        assertEquals(uA, uAT, "Tiedot eivät täsmää");
    }
}
