package com.project.simu.constants;

/**
 * 
 */
public enum Tyovuoro {

    EIGHT(0),
    NINE(3600),
    TEN(7200),
    ELVEN(10800),
    TWELVE(14440);

    private double tyoVuoronAlkamisAika;

    Tyovuoro(double tyoVuoronAlkamisAika) {
        this.tyoVuoronAlkamisAika = tyoVuoronAlkamisAika;
    }

    public double getTyoAlkaa() {
        return tyoVuoronAlkamisAika;
    }

    /**
     * 
     * @param tV
     * @return
     */
    public int getTyoVuoro(Tyovuoro tV) {
        int tyoVuoroIndex = 0;
        switch ((int) tV.tyoVuoronAlkamisAika) {
            case 0:
                tyoVuoroIndex = 0;
                break;
            case 3600:
                tyoVuoroIndex = 1;
                break;
            case 7200:
                tyoVuoroIndex = 2;
                break;
            case 10800:
                tyoVuoroIndex = 3;
                break;
            case 14440:
                tyoVuoroIndex = 4;
                break;
        }
        return tyoVuoroIndex;
    }

    public static final int size;
    static {
        size = values().length;
    }
}
