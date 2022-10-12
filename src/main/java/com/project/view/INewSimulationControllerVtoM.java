package com.project.view;

import java.io.IOException;

public interface INewSimulationControllerVtoM {

    public void aloitaSimulaatio() throws InterruptedException;

    public void setParametrit() throws IOException;

    public void hidastaSimulaatiota();

    public void nopeutaSimulaatiota();
}
