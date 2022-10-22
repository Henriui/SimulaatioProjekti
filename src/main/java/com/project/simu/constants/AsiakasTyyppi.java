package com.project.simu.constants;

public enum AsiakasTyyppi {

    // Yksityisasiakaat
    PRI_SALES(1),
    PRI_NETWORK(2),
    PRI_SUBSRCIBER(3),
    PRI_INVOICE(4),
    // Yritysasiakkaat
    CO_SALES(5),
    CO_NETWORK(6),
    CO_SUBSCRIBER(7),
    CO_INVOICE(8),
    // Jakaumaa varten henkilö / yritysasiakkaat
    PRI(9),
    CO(10);

    private int asiakasTypeNumero;

    AsiakasTyyppi(int asiakasTypeNumero) {
        this.asiakasTypeNumero = asiakasTypeNumero;
    }

    public int getAsiakasTypeNumero() {
        return this.asiakasTypeNumero;
    }
}
