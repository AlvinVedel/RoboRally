package view;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import model.Carte;
import model.Partie;
import model.Robot;

public class EcranPartie extends GridPane {
	
	private Plateau plateau;
	private SmartGroup group = new SmartGroup();
	private SubScene scenePlateau;
	private final double WIDTH=700;
	private final double HEIGHT=500;
	private Camera cam=defineCamera();
	
	private AffichageCartes deck;
	private TextArea affichageScore;
	private String newLine = System.getProperty("line.separator");
	
	private Chat chat = new Chat();
	
	
	
	
	
	public PerspectiveCamera defineCamera() {
		PerspectiveCamera cam = new PerspectiveCamera();
		cam.setTranslateX(-WIDTH/2 - 50);
		cam.setTranslateY(-HEIGHT/2 - 300);
		cam.setTranslateZ(-700);
		cam.setNearClip(0.001);
		cam.setFarClip(1000);
		return cam;
	}
	 
	
	public SmartGroup getSmartGroup() {
		return group;
	}
	
	public SubScene getScenePlateau() {
		return scenePlateau;
	}

	public void defAffichageScore(Partie partie) {
		affichageScore = new TextArea();
		for(int i=0; i<partie.getListeRobot().size(); i++) {
			String newText = partie.getListeRobot().get(i).getUsernameDetenteur()+" : "+partie.getListeRobot().get(i).getScore()+"("+partie.getListeRobot().get(i).getColor()+")";
			
			affichageScore.setText(affichageScore.getText()+newText+newLine);			
		}
	}
	
	public void setPlateau(Partie partie) {
		this.getChildren().remove(plateau);
		plateau=new Plateau(group, partie);
		this.getChildren().add(plateau);
	}
	
	public void setDeck(ArrayList<Carte> liste) {
		this.getChildren().remove(deck);
		deck=new AffichageCartes(liste);
		this.getChildren().add(deck);
	}
	
	public EcranPartie(Partie partie) {
		plateau = new Plateau(group, partie);
		scenePlateau = new SubScene(group, 1350, 700, true, null);
		GridPane.setConstraints(scenePlateau, 0, 0);
		scenePlateau.setCamera(cam);
		scenePlateau.setFill(Color.RED);
		
		ArrayList<Carte> liste = new ArrayList<>();
		liste.add(new Carte("Bob")); liste.add(new Carte("Kevin")); liste.add(new Carte("Jack")); liste.add(new Carte("Alv"));
		liste.add(new Carte("Jo")); liste.add(new Carte("Dams")); liste.add(new Carte("Alfred")); liste.add(new Carte("JOOON"));
		liste.add(new Carte("ta onne"));
		deck=new AffichageCartes(liste);
		deck.setPrefSize(1350, 400);
		
		GridPane.setConstraints(deck, 0, 1);
		
		Partie copie = partie;
		copie.getListeRobot().add(new Robot("Hugh")); copie.getListeRobot().add(new Robot("Bob"));
		 defAffichageScore(copie);
		affichageScore.setStyle("-fx-border-color: black");
		affichageScore.setMaxSize(100, 700);
		GridPane.setConstraints(affichageScore, 1, 0);
		
		chat.setPrefSize(480, 700);
		chat.getAffichageChat().setPrefSize(480, 650); chat.getBoutonEnvoyer().setPrefSize(60, 50);
		chat.getEntreeChat().setPrefSize(420, 50);
		GridPane.setConstraints(chat, 2, 0);
		
		
		
		this.getChildren().addAll(scenePlateau, deck, affichageScore, chat);
		
		
		
	}

	


}