package com.project.view;

import java.util.HashMap;

import com.project.simu.model.SimulaationSuureet;

public interface INewSimulationControllerMtoV {

    public void paivitaPalveluPisteet(HashMap<String, int[]> suureStatusMap);

    public void showTulokset(SimulaationSuureet sS);

}
