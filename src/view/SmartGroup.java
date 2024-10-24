package view;

import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;

public class SmartGroup extends Group {

	Rotate r;
	Transform t = new Rotate();
//	Box b;
	
/*	public ObjetMovible() {
		b=new Box(500, 50, 500);
	}
	public ObjetMovible(double x, double y, double z) {
		b=new Box(x, y, z);
		
	}   */
	
	public void RotateByX(int ang) {
		r = new Rotate(ang, Rotate.X_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}
	public void RotateByY(int ang) {
		r = new Rotate(ang, Rotate.Y_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}
/*	public void RotateByZ(int ang) {
		r = new Rotate(ang, Rotate.Z_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}   */
	
	
	
	
}
