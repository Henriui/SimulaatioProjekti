package com.project.simu.model;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Trace;

/**
 * Class for gathering data from simulation that allows kontrolleri to send it
 * easily for view
 * 
 * @author Rasmus Hyyppä
 */
public class SimulaatioData {

    private double simulointiAika; // Simulaation kesto
    private double palveluprosentti; // Vastausprosentti / Palveluprosentti
    private long asTotalMaara; // Asiakas määrä simulaatiossa
    private int asPalveltu; // Asiakkaita palveltu simulaatiossa
    private int asPoistunut; // Asiakkaita kyllästynyt jonottamaan simulaatiossa
    private int asReRoutattu; // Asiakkaita reroutattu väärän valinnan vuoksi
    private double avgAvgAsAikaSim; // Asiakkaiden kokonais viipyminen keskimääräisesti simulaatiossa
    private double avgJonotusAika; // Avg jonotusaika kokonaisuudessan
    private Parametrit uP; // Parametrit mitkä annettu moottorille
    private HashMap<String, int[]> suureStatusMap; // Hashmap for view
    // Arrayt for hashmap
    private int[] palveluMaaraArr;
    private int[] palveluJonoArr;
    private int[] palveluQuitterArr;
    private int[] palveluReRoutedArr;
    private int[] palveluVuoroArr;
    private int[] palveluVarattuArr;
    private int[] palveluTotalArr;
    private int[] palveluProsenttiArr;
    private double[] palveluAikaArr;
    private double[] jonoAikaArr;

    public SimulaatioData(Parametrit parametrit) {
        uP = parametrit;
        alustaSuureet(); // Gives values to every variable
    }


    /**
     * Resetoi alkuarvot simulaattorin suureille seka Asiakas maarat ja palvelupisteiden maarat
     * @author Rasmus Hyyppä
     */
    public void alustaSuureet() {
        simulointiAika = Kello.getInstance().getAika();
        palveluprosentti = 0;
        asTotalMaara = 0;
        avgAvgAsAikaSim = 0;
        asPalveltu = 0;
        asPoistunut = 0;
        asReRoutattu = 0;
        avgJonotusAika = 0;
        suureStatusMap = new HashMap<String, int[]>();
        reloadHashMap();
        jonoAikaArr = new double[Tyyppi.maxSize];
        palveluAikaArr = new double[Tyyppi.maxSize];
        // Reset asiakas & palvelupiste luokat
        Asiakas.resetAsiakasUID();
        Asiakas.resetAsiakasSum();
        Palvelupiste.resetPPUID();

    }

    /**
     * Resetoi hashmapin arrayt jotta saadaan paivitetyt arvot siirettya viewiin kesken simulaation
     * @author Rasmus Hyyppä
     */
    private void reloadHashMap() {
        palveluMaaraArr = new int[Tyyppi.maxSize];
        palveluJonoArr = new int[Tyyppi.maxSize];
        palveluQuitterArr = new int[Tyyppi.maxSize];
        palveluReRoutedArr = new int[Tyyppi.maxSize];
        palveluVuoroArr = new int[Tyyppi.maxSize];
        palveluVarattuArr = new int[Tyyppi.maxSize];
        palveluProsenttiArr = new int[Tyyppi.maxSize];
        palveluTotalArr = new int[] { (int) asTotalMaara, asPalveltu,
                asPoistunut, asReRoutattu, (int) simulointiAika };
        suureStatusMap.put("Palveltu", palveluMaaraArr);
        suureStatusMap.put("Jonossa", palveluJonoArr);
        suureStatusMap.put("Quitter", palveluQuitterArr);
        suureStatusMap.put("ReRouted", palveluReRoutedArr);
        suureStatusMap.put("Tyovuorossa", palveluVuoroArr);
        suureStatusMap.put("Totalit", palveluTotalArr);
        suureStatusMap.put("Varattu", palveluVarattuArr);
        suureStatusMap.put("Palveluprosentti", palveluProsenttiArr);
    }

    /**
     * Getter for palveluprosentti from every palvelupiste
     * 
     * @return double returns palveluprosentti from total values
     * @author Rasmus Hyyppä
     */
    public double getPalveluprosentti() {
        palveluprosentti = (double) asPalveltu / (double) asTotalMaara;
        return (palveluprosentti * 100);
    }

    /**
     * Getter for certain Palvelupiste group busy time.
     * 
     * @param ppType palvelupiste Tyyppi number
     * @return double busy time, from param type of palvelupiste group
     * @author Rasmus Hyyppä
     */
    public double getPalveluAika(int ppType) {
        return palveluAikaArr[ppType - 1] / uP.getPPMaara(ppType);
    }

    /**
     * Getter for certain Palvelupiste group queue time.
     * 
     * @param ppType palvelupiste Tyyppi number
     * @return double queue time, from param type of palvelupiste group
     * @author Rasmus Hyyppä
     */
    public double getJonoAika(int ppType) {
        return this.jonoAikaArr[ppType - 1] / uP.getPPMaara(ppType);
    }

    /**
     * Getter for certain Palvelupiste group palveluprosentti.
     * 
     * @param ppType palvelupiste Tyyppi number
     * @return int from that type of palvelupiste group
     * @author Rasmus Hyyppä
     */
    public int getPalveluProsentti(int ppType) {
        return palveluProsenttiArr[ppType - 1] / uP.getPPMaara(ppType);
    }

    /**
     * Adder for palveluVuoroArr
     * 
     * @param ppType palvelupiste Tyyppi number
     * @param tulos  checks if palvelupiste is on duty
     * @author Rasmus Hyyppä
     */
    public void addVuoroMaara(int ppType, boolean tulos) {
        if (tulos) {
            palveluVuoroArr[ppType]++;
        }
    }

    /**
     * Adder for palveluVarattuArr
     * 
     * @param ppType palvelupiste type number
     * @param tulos  checks if palvelupiste is varattu
     * @author Rasmus Hyyppä
     */
    public void addVarattuMaara(int ppType, boolean tulos) {
        if (tulos) {
            palveluVarattuArr[ppType]++;
        }
    }

    /**
     * Method gathers latest data from palvelupiste class and
     * returns HashMap with int array's for view
     * 
     * @param palvelupisteet
     * @return HashMap<String, int[]>
     * @author Rasmus Hyyppä
     */
    public HashMap<String, int[]> getPPStatus(Palvelupiste[] palvelupisteet) {
        reloadHashMap();
        for (Palvelupiste pp : palvelupisteet) {
            int ppTyyppi = pp.getPPTyyppi().getTypeValue() - 1; // -1 for array slot
            palveluMaaraArr[ppTyyppi] += pp.getAsPalveltuJonosta();
            palveluJonoArr[ppTyyppi] += pp.getJonoKoko();
            palveluQuitterArr[ppTyyppi] += pp.getAsPoistunutJonosta();
            palveluProsenttiArr[ppTyyppi] += (int) pp.getPProsentti();
            if (ppTyyppi < 8) {
                palveluReRoutedArr[ppTyyppi] += ((Asiakaspalvelija) pp).getAsReRoutedJonosta();
                addVuoroMaara(ppTyyppi, pp.getOnPaikalla());
                addVarattuMaara(ppTyyppi, pp.onVarattu());
            }
        }
        return suureStatusMap;
    }

    /**
     * Gathers all the needed details for total values from
     * Palvelupiste param
     * 
     * @param pp Palvelupiste for data gathering
     * @author Rasmus Hyyppä
     */
    public void getSuureetPP(Palvelupiste pp) {
        int ppTyyppi = pp.getPPTyyppi().getTypeValue() - 1;
        palveluAikaArr[ppTyyppi] += pp.getAvgPalveluAika();
        jonoAikaArr[ppTyyppi] += pp.getAvgJonotusAika();
        if (ppTyyppi < 8) {
            avgJonotusAika += pp.getAvgJonotusAika();
        }
    }

    /**
     * Average queue time in full simulation doesnt take count queue's from
     * puhelinvalikko
     * 
     * @return double average queue time in full simulation
     * @author Rasmus Hyyppä
     */
    public double getJonotusAika() {
        return avgJonotusAika / ((double) uP.getAllPPMaara() - (double) Parametrit.getMinPPMaara());
    }

    // Printing and stuff..
    public void tulosteet() {
        DecimalFormat dF = new DecimalFormat("#0.00");
        Trace.out(Trace.Level.INFO, "\n\n\nSimulointiaika:" + dF.format(getSimulointiAika()));
        Trace.out(Trace.Level.INFO, "Palvelupisteiden palveluprosentti:" + dF.format(getPalveluprosentti()) + " %.");
        Trace.out(Trace.Level.INFO, "Asiakkaita lisatty jonoon:" + getAsTotalMaara());
        Trace.out(Trace.Level.INFO, "Asiakkaita palveltu jonosta:" + getAsPalveltu());
        Trace.out(Trace.Level.INFO, "Asiakkaita poistunut jonosta:" + getAsPoistunut());
        Trace.out(Trace.Level.INFO, "Asiakkaita reRoutattu jonosta:" + getAsReRouted());
        Trace.out(Trace.Level.INFO, "Asiakkaitten avg viipyminen simulaatiossa:" + dF.format(getAvgAsAikaSim()) + "\n");
    }

    // Getters & Setters & Adders for everything else

    public double getSimulointiAika() {
        return simulointiAika;
    }

    public void setSimulointiAika(double simulointiAika) {
        this.simulointiAika = simulointiAika;
    }

    public void setAvgAsAikaSim(double avgAvgAsAikaSim) {
        this.avgAvgAsAikaSim = avgAvgAsAikaSim;
    }

    public int getPalveluMaara(int ppType) {
        return palveluMaaraArr[ppType - 1];
    }

    public long getAsTotalMaara() {
        return asTotalMaara;
    }

    public void setAsTotalMaara(long sum) {
        asTotalMaara = sum;
    }

    public int getAsPalveltu() {
        return asPalveltu;
    }

    public void addAsPalveltu() {
        this.asPalveltu += 1;
    }

    public int getAsPoistunut() {
        return asPoistunut;
    }

    public void addAsPoistunut() {
        this.asPoistunut += 1;
    }

    public int getAsReRouted() {
        return asReRoutattu;
    }

    public void addAsReRouted() {
        this.asReRoutattu += 1;
    }

    public double getAvgAsAikaSim() {
        return avgAvgAsAikaSim;
    }
}