package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Carte implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4642792156927372063L;
	private Direction direction;
	private int avancement;
	private int vitesse;
	private int ordre;
	private String detenteur;
	
	public Carte(Direction direction, int avancement, int vitesse, String username) {
		setDirection(direction);
		setAvancement(avancement);
		setVitesse(vitesse);
		setDetenteur(username);
	}
	public Carte(String username) {
		setDirection(randomDirection());
		setAvancement(randomAvancement());
		setVitesse(randomVitesse());
		setDetenteur(username);
	}
	public Carte(String username, int ordre) {
		setDirection(randomDirection());
		setAvancement(randomAvancement());
		setVitesse(randomVitesse());
		setDetenteur(username);
		setOrdre(ordre);
	}
	
	public Carte() {
		setDirection(randomDirection());
		setAvancement(randomAvancement());
		setVitesse(randomVitesse());
	}
	
	public Direction randomDirection() {
		double hz = Math.random();
		if(hz<=0.25) {
			return Direction.Bas;
		}
		else if(hz<=0.5) {
			return Direction.Haut;
		}
		else if(hz<=0.75) {
			return Direction.Droite;
		}
		else {
			return Direction.Gauche;
		}
	}
	
	public int randomAvancement() {
		int av = (int)(Math.random()*3);
		return av;
	}
	public int randomVitesse() {
		int vit = (int)(Math.random()*10);
		vit *= 10;
		return vit;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getAvancement() {
		return avancement;
	}

	public void setAvancement(int avancement) {
		this.avancement = avancement;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
	public String toString() {
		return direction+" : "+avancement+" ("+vitesse+")"+" detenteur : "+detenteur;
	}
	public int getOrdre() {
		return ordre;
	}
	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
	public String getDetenteur() {
		return detenteur;
	}
	public void setDetenteur(String detenteur) {
		this.detenteur = detenteur;
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
		this.direction = (Direction) ois.readObject();
		this.avancement = (int) ois.readObject();
		this.vitesse = (int) ois.readObject();
		this.ordre = (int) ois.readObject();
		this.detenteur = (String) ois.readObject();
		
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(direction);
		oos.writeObject(avancement);
		oos.writeObject(vitesse);
		oos.writeObject(ordre);
		oos.writeObject(detenteur);
		
	}

}
