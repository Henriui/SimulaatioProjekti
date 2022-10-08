package com.project.view;

import java.io.IOException;

import com.project.simu.model.SimulaationSuureet;

public interface INewSimulationControllerMtoV {

    public void ulkonaAs(int maara);

    public void asPPMaara(int type, int koko);

    public void ilmoitaPalveluPisteet(int yritys, int yksityis);

    public void ilmoitaJononKoko(int yksityis, int yritys);

    public void showTulokset(SimulaationSuureet sS);

}
