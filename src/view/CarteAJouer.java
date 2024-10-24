package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Carte;

public class CarteAJouer extends FlowPane {

	private Button selectionnerCarte;
	private Label imageAssocie;
	
	public CarteAJouer(Carte carte) {
		this.setPrefSize(150, 400);
		this.setRowValignment(VPos.TOP);
		this.setColumnHalignment(HPos.CENTER);
		this.setPadding(new Insets(5, 0, 0, 0));
		imageAssocie=new Label(carte.getVitesse()+" "+carte.getDetenteur());
		imageAssocie.setPrefSize(150, 300);
		imageAssocie.setFont(new Font(50));
		imageAssocie.setStyle("-fx-border-color: black");
		
		selectionnerCarte=new Button("Sélectionner");
		selectionnerCarte.setPrefSize(150, 100);
		this.getChildren().addAll(imageAssocie, selectionnerCarte);
		
		
		
	}
	
	

	public Button getSelectionnerCarte() {
		return selectionnerCarte;
	}
	
	
}
