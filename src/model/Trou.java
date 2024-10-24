package model;

public class Trou extends ElementMap{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2782862801419326098L;

	public Trou(int coordX, int coordY) {
		super(coordX, coordY, NatureElement.Trou);
		// TODO Auto-generated constructor stub
	}
	public Trou(int coX, int coY, NatureElement nat) {
		super(coX, coY, nat);
	}

}
