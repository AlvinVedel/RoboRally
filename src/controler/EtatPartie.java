package controler;

import java.io.Serializable;

public class EtatPartie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6180306528330712075L;
	private Etat etat;
	

	public EtatPartie(Etat etat) {
		setEtat(etat);
	}
	
	
	
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	public String toString() {
		return "état : "+etat;
	}
	
}
