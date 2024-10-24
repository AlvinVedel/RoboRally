package view;

import java.util.*;
import javafx.scene.Group;


public class GroupRobot extends Group{

//	private ArrayList<Robot3D> listeRobot;;
	
	public GroupRobot() {
		
	}
	
/*	public ArrayList<Robot3D> getListeRobot() {
		return listeRobot;
	}  */
	
	
	public void ajouterRobot(Robot3D rob) {
		this.getChildren().addAll(rob.getTronc(), rob.getHead(), rob.getArmRight(), rob.getArmLeft(), rob.getEpaules());
	}
	
	
	
	
	
	
	
	
	
	
}
