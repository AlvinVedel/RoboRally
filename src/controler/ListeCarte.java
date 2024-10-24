package controler;

import java.io.*;
import java.util.*;

import model.Carte;

public class ListeCarte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5609941645196423197L;
	private ArrayList<Carte> listeCarte = new ArrayList<>();
	
	public ListeCarte(ArrayList<Carte> list) {
		this.setListeCarte(list);
	}

	public ArrayList<Carte> getListeCarte() {
		return listeCarte;
	}

	public void setListeCarte(ArrayList<Carte> listeCarte) {
		this.listeCarte = listeCarte;
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
		this.listeCarte = (ArrayList<Carte>) ois.readObject();
		
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(listeCarte);
		
	}
	
	
}
