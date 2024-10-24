package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

public class Drapeau3D extends Group {

	private Cylinder poteau;
	private Box flag;
	private Color couleur;
	
	public Drapeau3D() {
		poteau= new Cylinder(20, 250);
		flag = new Box(100, 50, 10);
		flag.setTranslateX(50); flag.setTranslateY(-100); 
		this.getChildren().addAll(poteau, flag);
	}
	public Drapeau3D(Color color) {
		poteau= new Cylinder(20, 250);
		flag = new Box(100, 50, 10);
		flag.setTranslateX(50); flag.setTranslateY(-100); 
		flag.setMaterial(new PhongMaterial(color));
		poteau.setMaterial(new PhongMaterial(Color.LIGHTSLATEGREY));
		this.getChildren().addAll(poteau, flag);
	}
	

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	
	
}
