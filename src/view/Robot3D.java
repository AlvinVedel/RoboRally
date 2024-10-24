package view;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import model.Direction;

public class Robot3D extends Group {

	private Box tronc;
	private Box head;
	private Box armRight;
	private Box armLeft;
	private Box epaules;
	private Sphere eyeR; private Sphere eyeL;
	
	public Robot3D(Box tronc, Box head, Box arm1, Box arm2) {
		this.tronc=tronc;
		this.head=head;
		this.armRight=arm1;
		this.armLeft=arm2;
		this.head.setTranslateY(tronc.getHeight());
	}
	
	
	
	public Robot3D(double xTronc, double yTronc, double zTronc, double xHead, double yHead, double zHead, double xArm1, double yArm1,
			double zArm1, double xArm2, double yArm2, double zArm2) {
		this.tronc=new Box(xTronc, yTronc, zTronc);
		this.head=new Box(xHead, yHead, zHead);
		this.armRight=new Box(xArm1, yArm1, zArm1);
		this.armLeft=new Box(xArm2, yArm2, zArm2);
		
		
	}
	public Robot3D(double xTronc, double yTronc, double zTronc, double xHead, double yHead, double zHead, double xArm1, double yArm1,
			double zArm1, double xArm2, double yArm2, double zArm2, Color color) {
		this.tronc=new Box(xTronc, yTronc, zTronc); this.tronc.setMaterial(new PhongMaterial(color));
		this.head=new Box(xHead, yHead, zHead); this.head.setMaterial(new PhongMaterial(color));
		this.armRight=new Box(xArm1, yArm1, zArm1); this.armRight.setMaterial(new PhongMaterial(color));
		this.armLeft=new Box(xArm2, yArm2, zArm2); this.armLeft.setMaterial(new PhongMaterial(color));
		
		
	}
	
	public Robot3D() {
		this.tronc=new Box(100, 300, 100);
		this.head=new Box(300, 150, 200);
		this.armRight=new Box(50, 190, 50);
		this.armLeft=new Box(50, 190, 50);
	}
	public Robot3D(Color color, Direction orientation) {
		this.tronc=new Box(100, 200, 100); this.tronc.setMaterial(new PhongMaterial(color));
		this.head=new Box(90, 100, 90); this.head.setMaterial(new PhongMaterial(color));
		this.armRight=new Box(50, 100, 50); this.armRight.setMaterial(new PhongMaterial(color));
		this.armLeft=new Box(50, 100, 50); this.armLeft.setMaterial(new PhongMaterial(color));
		this.epaules=new Box(240, 50, 100); this.epaules.setMaterial(new PhongMaterial(color));
		this.eyeR=new Sphere(20); this.eyeL=new Sphere(20); this.eyeR.setMaterial(new PhongMaterial(color)); this.eyeL.setMaterial(new PhongMaterial(color));
		this.head.setTranslateY(-150);this.epaules.setTranslateY(-100);
		this.armRight.setTranslateX(95); this.armRight.setTranslateY(-40);
		this.armLeft.setTranslateX(-95);this.armLeft.setTranslateY(-40);
		this.eyeR.setTranslateY(-200); this.eyeL.setTranslateY(-200);
		this.eyeR.setTranslateX(50); this.eyeL.setTranslateX(50); 
		this.eyeR.setTranslateZ(20); this.eyeL.setTranslateZ(-20);
		this.getChildren().addAll(tronc, head, armRight, armLeft, epaules, eyeR, eyeL);
		if(orientation.equals(Direction.Droite) || orientation.equals(Direction.Gauche)) {
			Rotate r = new Rotate(90, Rotate.Y_AXIS);
			this.getTransforms().add(r);
		}
	}
	public Robot3D(Color color) {
		// regarde par défaut au Nord
		this.tronc=new Box(100, 200, 100); this.tronc.setMaterial(new PhongMaterial(color));
		this.head=new Box(90, 100, 90); this.head.setMaterial(new PhongMaterial(color));
		this.armRight=new Box(50, 100, 50); this.armRight.setMaterial(new PhongMaterial(color));
		this.armLeft=new Box(50, 100, 50); this.armLeft.setMaterial(new PhongMaterial(color));
		this.epaules=new Box(240, 50, 100); this.epaules.setMaterial(new PhongMaterial(color));
		this.eyeR=new Sphere(10); this.eyeL=new Sphere(10); this.eyeR.setMaterial(new PhongMaterial(color)); this.eyeL.setMaterial(new PhongMaterial(color));
		this.head.setTranslateY(-150);this.epaules.setTranslateY(-100);
		this.armRight.setTranslateX(95); this.armRight.setTranslateY(-40);
		this.armLeft.setTranslateX(-95);this.armLeft.setTranslateY(-40);
		this.eyeR.setTranslateY(-185); this.eyeL.setTranslateY(-185);
		this.eyeR.setTranslateX(20); this.eyeL.setTranslateX(-20); 
		this.eyeR.setTranslateZ(50); this.eyeL.setTranslateZ(50);
		this.getChildren().addAll(tronc, head, armRight, armLeft, epaules, eyeR, eyeL);
	}
	

	
	
	
	public void deplacerRobot3D(double x, double y, double z) {
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setTranslateZ(z);
	}
	
	public void tournerRobot(double value, Point3D rot) {
		Rotate r = new Rotate(value, rot);
		this.getTransforms().add(r);
	}
	
	
	
	public Box getTronc() {
		return tronc;
	}

	public Box getHead() {
		return head;
	}

	public Box getArmRight() {
		return armRight;
	}

	public Box getArmLeft() {
		return armLeft;
	}

	public Box getEpaules() {
		return epaules;
	}



	public Sphere getEyeR() {
		return eyeR;
	}



	public void setEyeR(Sphere eyeR) {
		this.eyeR = eyeR;
	}



	public Sphere getEyeL() {
		return eyeL;
	}



	public void setEyeL(Sphere eyeL) {
		this.eyeL = eyeL;
	}
	
	
	
	
	
	
	
}
