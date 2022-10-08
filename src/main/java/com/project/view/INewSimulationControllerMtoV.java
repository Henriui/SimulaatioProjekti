package com.project.view;

import java.util.HashMap;

import com.project.simu.model.SimulaatioData;

public interface INewSimulationControllerMtoV {

    public void paivitaPalveluPisteet(HashMap<String, int[]> suureStatusMap);

    public void showTulokset(SimulaatioData sS);

}
