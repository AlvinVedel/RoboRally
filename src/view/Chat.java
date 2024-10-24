package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

public class Chat extends FlowPane {

	
	private TextField entreeChat;
	
	
	private Button boutonEnvoyer;
	
	
	private TextArea affichageChat;
	
	
	
	
	public Chat() {
		this.getChildren().addAll(configureAffichageChat(), configureEntreeChat(), configureBoutonEnvoyer());
	}
	
	public TextArea configureAffichageChat() {
		affichageChat = new TextArea();		
	//	BorderPane.setAlignment(entreeChat, Pos.TOP_CENTER);
		return affichageChat;
	}
	
	
	public TextField configureEntreeChat() {
		entreeChat = new TextField();
		entreeChat.setPromptText("Ecrivez un message...");
	//	BorderPane.setAlignment(entreeChat, Pos.BOTTOM_LEFT);
		
		return entreeChat;
	}
	
	public Button configureBoutonEnvoyer() {
		boutonEnvoyer = new Button("Envoyer");
	//	BorderPane.setAlignment(boutonEnvoyer, Pos.BOTTOM_RIGHT);
		
		return boutonEnvoyer;
	}

	public Button getBoutonEnvoyer() {
		return boutonEnvoyer;
	}
	public TextField getEntreeChat() {
		return entreeChat;
	}
	public TextArea getAffichageChat() {
		return affichageChat;
	}
	
}
