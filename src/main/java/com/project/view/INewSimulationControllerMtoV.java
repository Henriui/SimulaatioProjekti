package com.project.view;

import java.util.HashMap;

import com.project.simu.model.SimulaatioData;

public interface INewSimulationControllerMtoV {

    public void paivitaPalveluPisteet(HashMap<String, int[]> suureStatusMap);

    //public void visualisoiAsiakas(int asType);
    public void visualisoiAsiakas(int asType, int oldAsType);

    public void visualisoiPoistuminen(int asType, String poistumisType);

    public void showTulokset(SimulaatioData sS);

}
