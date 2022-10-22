package com.project.simu.framework;

/**
 * Kayttoliittyma viewille simulaatiota varten
 * @author Rasmus Hyyppä
 */
public interface IMoottori {
     public void setSimulointiAika(double aika);

     public void setViive(long aika);

     public long getViive();
}
