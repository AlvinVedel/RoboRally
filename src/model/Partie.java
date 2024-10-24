package model;

import java.util.*;
import java.io.*;
import java.net.*;

public class Partie implements Serializable, ObjectInputValidation, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6595526483865742684L;
	private ArrayList<Robot> listeRobot = new ArrayList<>();
	
	
//	private ArrayList<Robot> listeRobotMort = new ArrayList<>();
	private int nbJoueurs;
	private Map map;
	private int limiteXpos; private int limiteXneg; private int limiteYpos; private int limiteYneg;
	
	
	
	public Partie(int taille) {
	//	for(int i=0; i<nbJoueurs; i++) {
		//	listeRobot.add(new Robot(i, i));
			map = new Map(taille);
			limiteXpos=getMaxX(); limiteXneg=getMinX();
			limiteYpos=getMaxY(); limiteYneg=getMinY();
	//	}
	}
	
	
	public Partie(Partie partie) {
		this.map=partie.map;
		System.out.println(map);
		this.nbJoueurs=partie.nbJoueurs;
		this.listeRobot=partie.listeRobot;
		limiteXpos=getMaxX(); limiteXneg=getMinX();
		limiteYpos=getMaxY(); limiteYneg=getMinY();
	}
	
	
	public int getMinX() {
		return map.getListeElement().get(0).getCoordX();
	}
	public int getMaxX(){
		return map.getListeElement().get(map.getListeElement().size()-1).getCoordX();
	}
	public int getMaxY() {
		return map.getListeElement().get(map.getListeElement().size()-1).getCoordY();
	}
	public int getMinY() {
		return map.getListeElement().get(0).getCoordY();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone(); 
	}
	
	public static void main(String[]args) {
		Partie part = new Partie(10);
		String username = "Kevin";
		part.listeRobot.add(new Robot(username));
		System.out.println(part.getMap());
		System.out.println(part.getListeRobot());
		Carte carte = new Carte(username);
		System.out.println("Carte : "+carte);
		part.deplaceRobot(username, carte);
		System.out.println(part.listeRobot);
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listeRobot = (ArrayList<Robot>) ois.readObject();
		this.nbJoueurs = (int) ois.readObject();
		this.map=(Map) ois.readObject();	
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(listeRobot);
		oos.writeObject(nbJoueurs);
		oos.writeObject(map);
	}
	
	
	public boolean isEncoreDrapeauCapturable() {
		boolean capturable = false; int i=0;
		while(capturable && i<map.getListeElement().size()) {
			if(map.getListeElement().get(i).getNature()==NatureElement.Drapeau) {
				System.out.println("ce drapeau est encore disponible : "+map.getListeElement().get(i));
				capturable=true;	
			}
			i++;
		}
		return capturable;
	}
	
	
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public int getNbJoueurs() {
		return nbJoueurs;
	}
	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}
	public ArrayList<Robot> getListeRobot() {
		return listeRobot;
	}
	public void setListeRobot(ArrayList<Robot> listeRobot) {
		this.listeRobot = listeRobot;
	}
	public Robot getRobotParDetenteur(String username) {
		Robot rob = null;
		for(int i=0; i<listeRobot.size(); i++) {
		//if(listeRobot.get(i).getDetenteur()!=null && listeRobot.get(i).getDetenteur().getUsername().equals(username)) {
			if(listeRobot.get(i).getUsernameDetenteur() != null && listeRobot.get(i).getUsernameDetenteur().equals(username)) {
				rob = listeRobot.get(i);
			}
		}
		return rob;
	}
	
	public Robot getRobotParCoordonnee(int coX, int coY) {
		Robot rob =null;
		for(int i=0; rob==null && i<listeRobot.size(); i++) {
			if(listeRobot.get(i).getCoordX()==coX && listeRobot.get(i).getCoordY()==coY) {
				rob=listeRobot.get(i);
			}
		}
		return rob;
	}
	
	public String toString() {
		return listeRobot+"";
	}
	
/*	public void deplacerRobot(String detenteur, Carte carte) {
		if(getRobotParDetenteur(detenteur)!=null && getRobotParDetenteur(detenteur).isAlive()) {
			System.out.println(getMap());
		if(carte.getDirection()==Direction.Bas) {
			System.out.println((getRobotParDetenteur(detenteur).getCoordY()-1)+" est la future coordonnée, limite : "+limiteYneg);
			if(carte.getAvancement()==1) {
				if(getRobotParDetenteur(detenteur).getCoordY()-1>=limiteYneg) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()-1);
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 1)");
				}
				
			}
			
			else if(carte.getAvancement()==2) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordY()-2)+" est la future coordonnée, limite : "+limiteYneg);
				if(getRobotParDetenteur(detenteur).getCoordY()-2>=limiteYneg) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()-2);
				}
				else if(getRobotParDetenteur(detenteur).getCoordY()-1>=limiteYneg) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()-1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 2)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
			else if(carte.getAvancement()==3) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordY()-3)+" est la future coordonnée, limite : "+limiteYneg);
				if(getRobotParDetenteur(detenteur).getCoordY()-3>=limiteYneg) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()-3);
				}
				else if(getRobotParDetenteur(detenteur).getCoordY()-2>=limiteYneg) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()-2);
					System.out.println("Le robot heurt un mur et n'avance que de deux cases (au lieu de 2)");
				}
				else if(getRobotParDetenteur(detenteur).getCoordY()-1>=limiteYneg) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()-1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 3)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
		}
		else if(carte.getDirection()==Direction.Haut) {
			if(carte.getAvancement()==1) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordY()+1)+" est la future coordonnée, limite : "+limiteYpos);
				if(getRobotParDetenteur(detenteur).getCoordY()+1<=limiteYpos) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()+1);
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 1)");
				}
				
			}
			
			else if(carte.getAvancement()==2) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordY()+2)+" est la future coordonnée, limite : "+limiteYpos);
				if(getRobotParDetenteur(detenteur).getCoordY()+2<=limiteYpos) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()+2);
				}
				else if(getRobotParDetenteur(detenteur).getCoordY()+1<=limiteYpos) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()+1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 2)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
			else if(carte.getAvancement()==3) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordY()+3)+" est la future coordonnée, limite : "+limiteYpos);
				if(getRobotParDetenteur(detenteur).getCoordY()+3<=limiteYpos) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()+3);
				}
				else if(getRobotParDetenteur(detenteur).getCoordY()+2<=limiteYpos) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()+2);
					System.out.println("Le robot heurt un mur et n'avance que de deux cases (au lieu de 2)");
				}
				else if(getRobotParDetenteur(detenteur).getCoordY()+1<=limiteYpos) {
					getRobotParDetenteur(detenteur).setCoordY(getRobotParDetenteur(detenteur).getCoordY()+1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 3)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
		}
		else if(carte.getDirection()==Direction.Droite) {
			if(carte.getAvancement()==1) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordX()+1)+" est la future coordonnée, limite : "+limiteXpos);
				if(getRobotParDetenteur(detenteur).getCoordX()+1<=limiteXpos) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()+1);
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 1)");
				}
				
			}
			
			else if(carte.getAvancement()==2) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordX()+2)+" est la future coordonnée, limite : "+limiteXpos);
				if(getRobotParDetenteur(detenteur).getCoordX()+2<=limiteXpos) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()+2);
				}
				else if(getRobotParDetenteur(detenteur).getCoordX()+1<=limiteXpos) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()+1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 2)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
			else if(carte.getAvancement()==3) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordX()+3)+" est la future coordonnée, limite : "+limiteXpos);
				if(getRobotParDetenteur(detenteur).getCoordX()+3<=limiteXpos) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()+3);
				}
				else if(getRobotParDetenteur(detenteur).getCoordX()+2<=limiteXpos) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()+2);
					System.out.println("Le robot heurt un mur et n'avance que de deux cases (au lieu de 2)");
				}
				else if(getRobotParDetenteur(detenteur).getCoordX()+1<=limiteXpos) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()+1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 3)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
		}
		else {
			if(carte.getAvancement()==1) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordX()-1)+" est la future coordonnée, limite : "+limiteXneg);
				if(getRobotParDetenteur(detenteur).getCoordX()-1>=limiteXneg) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()-1);
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 1)");
				}
				
			}
			
			else if(carte.getAvancement()==2) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordX()-2)+" est la future coordonnée, limite : "+limiteXneg);
				if(getRobotParDetenteur(detenteur).getCoordX()-2>=limiteXneg) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()-2);
				}
				else if(getRobotParDetenteur(detenteur).getCoordX()-1>=limiteXneg) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()-1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 2)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
			else if(carte.getAvancement()==3) {
				System.out.println((getRobotParDetenteur(detenteur).getCoordX()-3)+" est la future coordonnée, limite : "+limiteXneg);
				if(getRobotParDetenteur(detenteur).getCoordX()-3>=limiteXneg) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()-3);
				}
				else if(getRobotParDetenteur(detenteur).getCoordX()-2>=limiteXneg) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()-2);
					System.out.println("Le robot heurt un mur et n'avance que de deux cases (au lieu de 2)");
				}
				else if(getRobotParDetenteur(detenteur).getCoordX()-1>=limiteXneg) {
					getRobotParDetenteur(detenteur).setCoordX(getRobotParDetenteur(detenteur).getCoordX()-1);
					System.out.println("Le robot heurt un mur et n'avance que d'une case (au lieu de 3)");
				}
				else {
					System.out.println("Le robot heurt un mur et n'avance pas (au lieu de 2)");
				}
			}
		}
		} else {
			System.out.println("Le robot était null/mort : "+getRobotParDetenteur(detenteur));
		}
		
		Robot rob = getRobotParDetenteur(detenteur);
		int coX = rob.getCoordX(); int coY = rob.getCoordY();
		if(map.getElementMapParCoord(coX, coY)!=null && map.getElementMapParCoord(coX, coY).getNature()==NatureElement.Drapeau) {
			getRobotParDetenteur(detenteur).setScore(getRobotParDetenteur(detenteur).getScore()+1);
			map.getElementMapParCoord(coX, coY).setNature(NatureElement.DrapeauCapture);
			((Drapeau) map.getElementMapParCoord(coX, coY)).setCapture(true);
			System.out.println("Le robot a capturé un drapeau");
			System.out.println("Case en question : "+map.getElementMapParCoord(coX, coY));
		}
		if(map.getElementMapParCoord(coX, coY)!=null && map.getElementMapParCoord(coX, coY).getNature().equals(NatureElement.Trou)) {
			getRobotParDetenteur(detenteur).setPV(0);
	//		listeRobotMort.add(rob);
		//	listeRobot.remove(rob);
			System.out.println("Le robot est tombé dans un puit");
			System.out.println("Case en question : "+map.getElementMapParCoord(coX, coY));
		}
		if(map.getElementMapParCoord(coX, coY)!=null && map.getElementMapParCoord(coX, coY).getNature().equals(NatureElement.Tapis)) {
			System.out.println("Le robot est censé avancer dans la direction du tapis mais pas encore codé");
			System.out.println("Case en question : "+map.getElementMapParCoord(coX, coY));
		}
	}    */
	
	
	public void deplaceRobot(String username, Carte carte) {
		try {
		if(getRobotParDetenteur(username)!=null && getRobotParDetenteur(username).isAlive()) {
			if(carte.getAvancement()==0) {
				getRobotParDetenteur(username).setOrientation(carte.getDirection());
				System.out.println("rotation robot");
			}
			else {
				for(int i=0; i<carte.getAvancement(); i++) {
					avancer(getRobotParDetenteur(username), carte.getDirection());
					verificationCase(username, getRobotParDetenteur(username).getCoordX(), getRobotParDetenteur(username).getCoordY());
					Robot autreRobot = robotDejaSurCase(username, map.getElementMapParCoord(getRobotParDetenteur(username).getCoordX(), getRobotParDetenteur(username).getCoordY()));
					if(autreRobot!=null) {
						avancer(autreRobot, carte.getDirection());
					}
					else {
						System.out.println("La voie est libre");
					}
					System.out.println("un avancement/ tentative faite");
				}
			}
		}}
		catch(ChuteException e) {
			System.out.println("le robot est tombé, fin du tour");
			getRobotParDetenteur(username).setPV(0); System.out.println("le robot a perdu ses pvs : "+getRobotParDetenteur(username).getPV());
		}
	}
	
	public void verificationCase(String username, int coordX, int coordY) throws ChuteException {
		if(map.getElementMapParCoord(coordX, coordY)!=null) {
			if(map.getElementMapParCoord(coordX, coordY).getNature()==NatureElement.Drapeau) {
				getRobotParDetenteur(username).setScore(getRobotParDetenteur(username).getScore()+1);
				map.getElementMapParCoord(coordX, coordY).setNature(NatureElement.DrapeauCapture);
				((Drapeau)map.getElementMapParCoord(coordX, coordY)).setNomProprietaire(username);
				((Drapeau) map.getElementMapParCoord(coordX, coordY)).setCapture(true);
				System.out.println("Le robot a capturé un drapeau");
				System.out.println("Case en question : "+map.getElementMapParCoord(coordX, coordY));
			}
			else if(map.getElementMapParCoord(coordX, coordY).getNature()==NatureElement.Trou) {
				getRobotParDetenteur(username).setPV(0);
				getRobotParDetenteur(username).setOrientation(Direction.Chute);
				System.out.println("Le robot est tombé dans un puit");
				System.out.println("Case en question : "+map.getElementMapParCoord(coordX, coordY));
				throw new ChuteException("Le robot est tombé");
			}
			else if((map.getElementMapParCoord(coordX, coordY).getNature()==NatureElement.Tapis)){
				System.out.println("Le robot est censé avancer dans la direction du tapis mais pas encore codé");
				System.out.println("Case en question : "+map.getElementMapParCoord(coordX, coordY));
				avancer(getRobotParDetenteur(username), ((TapisRoulant)map.getElementMapParCoord(coordX, coordY)).getOrientation());
			}
			else {
				System.out.println("Nouvelle case : RAS");
			}
		}
		else {
			System.out.println("incapacité de trouver la case correspondant aux coordonnées "+coordX+", "+ coordY);
		}
	}
	
	public void avancer(Robot robot, Direction direction) {
		System.out.println("Direction : "+direction);
		if(direction==Direction.Haut && robot.getCoordY()<limiteYpos) {
			robot.setCoordY(robot.getCoordY()+1);
			System.out.println("je monte de 1");
		}
		else if(direction==Direction.Bas && robot.getCoordY()>limiteYneg) {
			robot.setCoordY(robot.getCoordY()-1);
			System.out.println("je descends de 1");
		}
		else if(direction==Direction.Droite && robot.getCoordX()<limiteXpos) {
			robot.setCoordX(robot.getCoordX()+1);
			System.out.println("je vais à droite de 1");
		}
		else if(direction==Direction.Gauche && robot.getCoordX()>limiteXneg) {
			robot.setCoordX(robot.getCoordX()-1);
			System.out.println("je vais à gauche de 1");
		}
		else {
			if(direction==Direction.Bas) {
				System.out.println("si je suis la c'est que je peux pas descendre, je comprends pas pourquoi : limite Y negatif : "+limiteYneg+" coordonnée Y actuelle : "+robot.getCoordY());
			}
			System.out.println("le robot ne peut pas avancer");
		}
		
	}
	
	public Robot robotDejaSurCase(String username, ElementMap el) {
		for(int i=0; i<listeRobot.size(); i++) {
			if(listeRobot.get(i).getCoordX()==el.getCoordX() && listeRobot.get(i).getCoordY()==el.getCoordY() && !(listeRobot.get(i).getUsernameDetenteur().equals(username))) {
				System.out.println("un robot était déja sur la case !");
				return listeRobot.get(i);
			}
		}
		return null;
	}
	

public boolean isDejaRobot(ElementMap el) {
	boolean verif = false; int i=0;
	while(!verif && i<getListeRobot().size()) {
		if(listeRobot.get(i).getCoordX()==el.getCoordX() && listeRobot.get(i).getCoordY()==el.getCoordY()) {
			verif=true;
		}
	}
	return verif;
	
}

public void setPartie(Partie partie) {
	setMap(partie.getMap());
	setListeRobot(partie.getListeRobot());
	setNbJoueurs(partie.getNbJoueurs());
}


public void tirerLaser(Robot rob) {
	int coX = rob.getCoordX(); int coY = rob.getCoordY();
	boolean touche = false;
	if(rob.getOrientation()==Direction.Haut) {
		for(int i=coY; !touche && i<map.getTaille()/2; i++) {
			if(getRobotParCoordonnee(coX, i)!=null) {
				System.out.println("Le robot "+getRobotParCoordonnee(coX, i)+" a été touché par le laser de "+rob);
				getRobotParCoordonnee(coX, i).setPV(getRobotParCoordonnee(coX, i).getPV()-1);
				touche=true;
			}
		}
	}
	else if(rob.getOrientation()==Direction.Bas) {
		for(int i=coY; !touche && i<(-map.getTaille()/2); i--) {
			if(getRobotParCoordonnee(coX, i)!=null) {
				System.out.println("Le robot "+getRobotParCoordonnee(coX, i)+" a été touché par le laser de "+rob);
				getRobotParCoordonnee(coX, i).setPV(getRobotParCoordonnee(coX, i).getPV()-1);
				if(getRobotParCoordonnee(coX, i).getPV()==0) {
					getRobotParCoordonnee(coX, i).setOrientation(Direction.Sol);
				}
				touche=true;
			}
		}
	}
	else if(rob.getOrientation()==Direction.Droite) {
		for(int i=coX; !touche && i<map.getTaille()/2; i++) {
			if(getRobotParCoordonnee(i, coY)!=null) {
				System.out.println("Le robot "+getRobotParCoordonnee(i, coY)+" a été touché par le laser de "+rob);
				getRobotParCoordonnee(i, coY).setPV(getRobotParCoordonnee(i, coY).getPV()-1);
				touche=true;
			}
		}
	}
	else if(rob.getOrientation()==Direction.Droite) {
		for(int i=coX; !touche && i<(-map.getTaille()/2); i--) {
			if(getRobotParCoordonnee(i, coY)!=null) {
				System.out.println("Le robot "+getRobotParCoordonnee(i, coY)+" a été touché par le laser de "+rob);
				getRobotParCoordonnee(i, coY).setPV(getRobotParCoordonnee(i, coY).getPV()-1);
				touche=true;
			}
		}
	}
	
}


@Override
public void validateObject() throws InvalidObjectException {
	// TODO Auto-generated method stub
	
}

/*public ArrayList<Robot> getListeRobotMort() {
	return listeRobotMort;
}  */
	
	
	
	
}
