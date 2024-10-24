package view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Plaque extends Box{

	
	public Plaque(double x, double y, double z) {
		this.setWidth(x);
		this.setHeight(y);
		this.setDepth(z);
		this.setOpacity(1000);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.WHITESMOKE);
		this.setMaterial(mat);
	}
	public Plaque(double x, double y, double z, Color color) {
		this.setWidth(x);
		this.setHeight(y);
		this.setDepth(z);
		this.setOpacity(1000);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(color);
		this.setMaterial(mat);
	}
	
	public Plaque(Color color) {
		this.setWidth(100);
		this.setHeight(50);
		this.setDepth(100);
		this.setOpacity(1000);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(color);
		this.setMaterial(mat);
	}
	
	public Plaque() {
		this.setWidth(100);
		this.setHeight(50);
		this.setDepth(100);
		this.setOpacity(1000);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.WHITESMOKE);
		this.setMaterial(mat);
	}
	
}
