package simu.model;

public enum AsiakasTyyppi {
    // Yksityisasiakaat
    PRI_SALES(1),
    PRI_CONNECTION_SERVICE(2),
    PRI_SUBSRCIBER_SERVICE(3),
    PRI_INVOICE(4),

    // Yritysasiakkaat
    CO_SALES(5),
    CO_CONNECTION_SERVICE(6),
    CO_SUBSCRIBER_SERVICE(7),
    CO_INVOICE(8);

    private int asiakasTypeNumero;

    AsiakasTyyppi(int asiakasTypeNumero) {
        this.asiakasTypeNumero = asiakasTypeNumero;
    }

    public int getAsiakasTypeNumero() {
        return this.asiakasTypeNumero;
    }
}
