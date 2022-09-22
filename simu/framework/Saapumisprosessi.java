package simu.framework;
import eduni.distributions.*;
import simu.model.Tyyppi;
public class Saapumisprosessi {
	
	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private Tyyppi tyyppi;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, Tyyppi tyyppi){
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		tapahtumalista.lisaa(t);
	}

}
