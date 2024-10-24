package model;

public class TapisRoulant extends ElementMap {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4211813814005319870L;
	private Direction orientation;
	private int avancement;

	public TapisRoulant(int coordX, int coordY) {
		super(coordX, coordY, NatureElement.Tapis);
		setOrientation(randomDirection());
	}
	public TapisRoulant(int coX, int coY, NatureElement nat) {
		super(coX, coY, nat);
		setOrientation(randomDirection());

	}

	public Direction getOrientation() {
		return orientation;
	}

	public void setOrientation(Direction orientation) {
		this.orientation = orientation;
	}

	public int getAvancement() {
		return avancement;
	}

	public void setAvancement(int avancement) {
		this.avancement = avancement;
	}
	
	public Direction randomDirection() {
		double hz = Math.random();
		if(hz<=0.25) {
			return Direction.Haut;
		}
		else if(hz<=0.50) {
			return Direction.Bas;
		}
		else if(hz<=0.75) {
			return Direction.Droite;
		}
		else {
			return Direction.Gauche;
		}
		
		
	}

	
	
}
