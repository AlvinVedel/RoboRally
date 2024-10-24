package controler;

import java.io.*;
import java.util.*;

import model.Partie;
import testServeur.Map;

public class ListePartie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7557415483134810634L;

	private ArrayList<Partie> listePartie = new ArrayList<>();
	
	public ListePartie(ArrayList<Partie> liste) {
		this.listePartie=liste;
	}
	
	public ArrayList<Partie> getListePartie(){
		return listePartie;
	}
	
	public String toString() {
		return ""+listePartie;
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listePartie = (ArrayList<Partie>) ois.readObject();
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(listePartie);
	}
	
	
}
