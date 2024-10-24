package view;

import javax.swing.BorderFactory;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class EcranDemarrage extends AnchorPane{
	
	private Button boutonPret=new Button("Prêt");; 
	private final double WIDTH_BOUTON=200;
	private final double HEIGHT_BOUTON=90;
	
	private TextArea regles;
	private final double WIDTH_REGLES = 300;
	private final double HEIGHT_REGLES = 400;
	
	private Label titre;
	private final double WIDTH_TITRE=480;
	private final double HEIGHT_TITRE=70;
	
	private Label label;
	private TextArea chat;
	
	private Button boutonEnvoyer = new Button("Envoyer");
	
	private TextField entreeChat;
	
	private TextArea affichageChat;
	
	private final double WIDTH_ENTREE_CHAT = 200;
	private final double HEIGHT_ENTREE_CHAT = 50;
	
	private TextArea listeJoueur;
	
	private Chat chat2;
	
	String newLine = System.getProperty("line.separator");
	
	public EcranDemarrage(double width, double height ) {
		
		this.getChildren().addAll(configureBoutonPret(width, height), configureRegles(width, height), configureTitre(width, height));
	//	this.getChildren().addAll(configureEntreeChat(width, height));
		 this.getChildren().addAll(configureBoutonEnvoyer(width, height), configureEntreChat(width, height), configureAffichageChat(width, height));
	}
	
/*	public Chat configureChat(double width, double height) {
		chat2 = new Chat(width, height);
		chat2.setPrefSize(chat2.getTotalWidth(), chat2.getTotalHeight());
		AnchorPane.setBottomAnchor(chat2, 0.0 );
		AnchorPane.setRightAnchor(chat2, 0.0);
	
		return chat2;
	}   */
	
	public void retirerBoutons() {
		Platform.runLater(new Runnable() {
			public void run() {
				getChildren().removeAll(boutonPret, boutonEnvoyer);
				System.out.println("Jai retiré les boutons");
			}
		});
			
		
	}
	public void ajouterBoutons() {
		Platform.runLater(new Runnable() {
			public void run() {
				getChildren().addAll(boutonPret, boutonEnvoyer);
				System.out.println("je les ai rajoutés");
			}
		});
		
	}
	
	public TextArea configureAffichageChat(double width, double height) {
		affichageChat = new TextArea();
		affichageChat.setPrefSize(width/4, height/2);
		AnchorPane.setRightAnchor(affichageChat, 0.0);
		AnchorPane.setBottomAnchor(affichageChat, entreeChat.getHeight());
		return affichageChat;
	}
	
	public Button configureBoutonEnvoyer(double width, double height) {
		boutonEnvoyer.setPrefSize(width/12, height/10);
		AnchorPane.setRightAnchor(boutonEnvoyer, 0.0);
		AnchorPane.setBottomAnchor(boutonEnvoyer, 0.0);
		return boutonEnvoyer;
	}
	
	public TextField configureEntreChat(double width, double height) {
		entreeChat = new TextField();
		entreeChat.setPromptText("Ecrivez un message...");
		entreeChat.setPrefSize(3*width/12, height/10);	
		AnchorPane.setRightAnchor(entreeChat, boutonEnvoyer.getWidth());
		AnchorPane.setBottomAnchor(entreeChat, 0.0);
		return entreeChat;
	}
	
	
	public Button configureBoutonPret(double width, double height) {
		
		boutonPret.setPrefSize(WIDTH_BOUTON,HEIGHT_BOUTON);
		AnchorPane.setRightAnchor(boutonPret, width/2 - WIDTH_BOUTON);
		AnchorPane.setBottomAnchor(boutonPret, height/2 - HEIGHT_BOUTON -60);
		return boutonPret;
	}
	
	public TextArea configureRegles(double width, double height) {
		regles = new TextArea();
		regles.setPrefSize(WIDTH_REGLES, HEIGHT_REGLES);
		AnchorPane.setLeftAnchor(regles, 0.0);
		AnchorPane.setBottomAnchor(regles, 0.0);
		regles.setStyle("-fx-border-color: black");
		String rule1 = "- Les joueurs possèdent un robot chacun"+newLine+" et doivent capturer le maximum de drapeau";
		String rule2 = "- Faites attention aux tapis roulants qui vous"+newLine+" déplaceront et à ne pas tomber dans un trou !";
		String rule3 = "- A la fin de chaque tour les robots tirent droit devant "+newLine+"eux et infligent 1 point de dégats, vous possédez"+newLine+" 5 vies";
		String rule4 = "- ";
		regles.setText(rule1+newLine+newLine+rule2+newLine+newLine+rule3);
		regles.setFont(new Font("blue", 14));;
		return regles;
	}
	
	public Label configureTitre(double width, double height) {
		titre=new Label("Bienvenue dans Robot Rally !");
		titre.setPrefSize(WIDTH_TITRE, HEIGHT_TITRE);
		titre.setFont(new Font(36));
		AnchorPane.setTopAnchor(titre, width/10);
		AnchorPane.setRightAnchor(titre, height/2.6);
		
		return titre;
	}
	
	public TextField configureEntreeChat(double width, double height) {
		entreeChat = new TextField();
		entreeChat.setPromptText("Ecrivez un message...");
		entreeChat.setPrefSize(WIDTH_ENTREE_CHAT, HEIGHT_ENTREE_CHAT);
		AnchorPane.setRightAnchor(entreeChat, width/10);
		AnchorPane.setBottomAnchor(entreeChat, height/10);
		
		return entreeChat;
	}
	
	
	
	
	
	public void setPosition(Stage window) {
		
		AnchorPane.setBottomAnchor(boutonPret, (window.getHeight()/4));
		AnchorPane.setRightAnchor(boutonPret, (window.getWidth()/2.3));
		regles.setPrefSize(window.getWidth()/5, window.getHeight()/3);
		AnchorPane.setTopAnchor(titre, window.getHeight()/10);
		AnchorPane.setRightAnchor(titre, window.getWidth()/2.6);
		affichageChat.setPrefSize(window.getWidth()/4, 2*window.getHeight()/3);
		boutonEnvoyer.setPrefSize(window.getWidth()/12, window.getHeight()/10);
		AnchorPane.setRightAnchor(entreeChat, boutonEnvoyer.getWidth());
		
		boutonEnvoyer.setPrefSize(window.getWidth()/12, window.getHeight()/10);
		AnchorPane.setRightAnchor(boutonEnvoyer, 0.0);
		AnchorPane.setBottomAnchor(boutonEnvoyer, 0.0);
		
		entreeChat.setPrefSize(3*window.getWidth()/12, window.getHeight()/10);	
		AnchorPane.setRightAnchor(entreeChat, 3.5*boutonEnvoyer.getWidth()/2);
		AnchorPane.setBottomAnchor(entreeChat, 0.0);
		
		affichageChat.setPrefSize(window.getWidth()/4, window.getHeight()/2);
		AnchorPane.setRightAnchor(affichageChat, 0.0);
		AnchorPane.setBottomAnchor(affichageChat, 3*entreeChat.getHeight()/2);
		
	//	chat2.setPrefSize(window.getWidth()/5, 2*window.getHeight()/3);
	//	AnchorPane.setRightAnchor(entreeChat, window.getWidth()/10);
//		AnchorPane.setBottomAnchor(entreeChat, window.getHeight()/10);
		
	}
	
	public Button getBoutonPret() {
		return boutonPret;
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
	public TextArea getListeJoueur() {
		return listeJoueur;
	}

}
