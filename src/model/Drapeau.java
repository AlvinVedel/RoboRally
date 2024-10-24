package model;

public class Drapeau extends ElementMap{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5201243454657488500L;
	private String nomProprietaire;
	private boolean capture;

	public Drapeau(int coordX, int coordY) {
		super(coordX, coordY, NatureElement.Drapeau);
		
	}
	public Drapeau(int coX, int coY, NatureElement nat) {
		super(coX, coY, nat);
	}

	public String getNomProprietaire() {
		return nomProprietaire;
	}

	public void setNomProprietaire(String nomProprietaire) {
		this.nomProprietaire = nomProprietaire;
	}

	public boolean isCapture() {
		return capture;
	}

	public void setCapture(boolean capture) {
		this.capture = capture;
	}
	
	
	

}
