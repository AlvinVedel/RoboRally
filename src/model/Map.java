package model;

import java.io.*;
import java.util.*;

public class Map implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8720058127781188532L;
	private ArrayList<ElementMap> listeElement = new ArrayList<>();
	private int taille;
	
	public Map(int taille) {
		for(int i=(int)((-taille)/2); i<(int)(taille/2); i++) {
			for(int a=(int)((-taille)/2); a<(int)(taille/2); a++) {
				listeElement.add(generateElement(a, i));
			}
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public ElementMap getElementMapParNature(NatureElement nat) {
		int rd = (int)(Math.random()*listeElement.size());
		if(listeElement.get(rd).getNature().equals(nat)) {
			return listeElement.get(rd);
		}
		else {
			return getElementMapParNature(nat);
		}
	}
	public ElementMap getElementMapParCoord(int coX, int coY) {
		try{
			int i=0;
		
		while(listeElement.get(i).getCoordX()!=coX || listeElement.get(i).getCoordY()!=coY) {
			i++;
		}
		return listeElement.get(i);
		} catch(IndexOutOfBoundsException e) {
			System.err.println("L'élément est indisponible");
			return null;
		}
	}
	

	public ArrayList<ElementMap> getListeElement() {
		return listeElement;
	}

	public void setListeElement(ArrayList<ElementMap> listeElement) {
		this.listeElement = listeElement;
	}
	
	public String toString() {
		return ""+listeElement;
	}
	
	public ElementMap generateElement(int coX, int coY) {
		double rd = Math.random();
		if(rd<0.1) {
			return new Drapeau(coX, coY, NatureElement.Drapeau);
		}
		else if(rd<0.3) {
			return new TapisRoulant(coX, coY, NatureElement.Tapis);
		}
		else if(rd<0.37) {
			return new Trou(coX, coY, NatureElement.Trou);
		}
		else {
			return new CaseBasique(coX, coY, NatureElement.None);
		}
	}
	
	public int getTaille() {
		return taille;
	}
	
	
	
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listeElement = (ArrayList<ElementMap>) ois.readObject();
		this.taille = (int) ois.readObject();
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(listeElement);
		oos.writeObject(taille);
	}
	
	public static void main(String[]args) {
		Map map = new Map(10);
		map.getListeElement().get(0).setNature(NatureElement.Drapeau);
		System.out.println(map);
		ElementMap em1 = map.getElementMapParNature(NatureElement.Tapis);
		System.out.println("Element recherché : tapis --> "+em1);
		ElementMap em2 = map.getElementMapParCoord(-6, 4);
		System.out.println("Element recherché : (-6, 4) --> "+em2);
		
	}
	
}
