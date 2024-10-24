package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class ElementMap implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6037070011975446509L;
	private NatureElement nature;
	private int coordX;
	private int coordY;
	
	public ElementMap(int coordX, int coordY) {
		setCoordX(coordX);
		setCoordY(coordY);
	}
	public ElementMap(int coordX, int coordY, NatureElement nat) {
		setCoordX(coordX);
		setCoordY(coordY);
		setNature(nat);
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
	
	public String toString() {
		return nature+" : "+coordX+" ; "+coordY+" ";
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
		this.coordX = (int) ois.readObject();
		this.coordY = (int) ois.readObject();
		this.nature=(NatureElement) ois.readObject();
		
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(coordX);
		oos.writeObject(coordY);
		oos.writeObject(nature);
		
	}
	public NatureElement getNature() {
		return nature;
	}
	public void setNature(NatureElement nature) {
		this.nature = nature;
	}
	
}
