package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Robot implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 430791425593558982L;
	private int coordX;
	private int coordY;
	private String usernameDetenteur;
	private int pv=5;
	private int score=0;
	private boolean alive=true;
	private Direction orientation;
	private double r; private double g; private  double b;
	
	public Robot(String detenteur, int coordX, int coordY) {
		setUsernameDetenteur(detenteur);
		setCoordX(coordX);
		setCoordY(coordY);
		
	}
	public Robot(int coordX, int coordY) {
		setCoordX(coordX);
		setCoordY(coordY);
		
	}
	
	
	public Robot(String usernameDetenteur) {
		this.usernameDetenteur=usernameDetenteur;
	}
	
	public Robot(String usernameDetenteur, double r, double g, double b) {
		setUsernameDetenteur(usernameDetenteur);
		setR(r);setG(g);setB(b);
	}
	public Robot(String usernameDetenteur, double r, double g, double b, int coordX, int coordY) {
		setUsernameDetenteur(usernameDetenteur);
		setR(r);setG(g);setB(b);
		setCoordX(coordX); setCoordY(coordY);
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public String getUsernameDetenteur() {
		return usernameDetenteur;
	}
	public void setUsernameDetenteur(String usernameDetenteur) {
		this.usernameDetenteur=usernameDetenteur;
	}
	public int getPV() {
		return pv;
	}
	public void setPV(int pv) {
		if(pv>0) {
			this.pv=pv;
		}
		else {
			System.err.println("Le robot est mort");
			this.pv=0;
			setAlive(false);
		}
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
		this.coordX = (int) ois.readObject();
		this.coordY = (int) ois.readObject();
		this.usernameDetenteur = (String) ois.readObject();
		this.pv = (int) ois.readObject();	
		this.r = (double) ois.readObject();
		this.g = (double) ois.readObject();
		this.b = (double) ois.readObject();
		this.orientation = (Direction) ois.readObject();
		this.alive = (boolean) ois.readObject();
		this.score=(int)ois.readObject();
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(coordX);
		oos.writeObject(coordY);
		oos.writeObject(usernameDetenteur);
		oos.writeObject(pv);
		oos.writeObject(r);
		oos.writeObject(g);
		oos.writeObject(b);
		oos.writeObject(orientation);
		oos.writeObject(alive);
		oos.writeObject(score);
		
	}
	
	public String toString() {
		if(pv>0) {
			return "Robot de "+usernameDetenteur+"("+coordX+" ; "+coordY+")";
		}
		else {
			return "Robot de "+usernameDetenteur+"("+coordX+" ; "+coordY+") ==>  mort";
		}
		 }
	/*	if(detenteur==null) {
			return "Robot inconnu ("+coordX+" ; "+coordY+")";
		}
		else {
			return "Robot de "+detenteur.getUsername()+" ("+coordX+" ; "+coordY+")";
		}	
	}  */

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public Direction getOrientation() {
		return orientation;
	}
	public void setOrientation(Direction orientation) {
		this.orientation = orientation;
	}
	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	public double getG() {
		return g;
	}
	public void setG(double g) {
		this.g = g;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}

}
