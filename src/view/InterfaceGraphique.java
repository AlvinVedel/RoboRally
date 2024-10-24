package view;

import java.io.InputStream;
import java.util.ArrayList;

import controler.Etat;
import controler.EtatPartie;
import controler.ListePartie;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Carte;
import model.Direction;
import model.Partie;
import model.Robot;


public class InterfaceGraphique extends Application{

	public static  InterfaceGraphique inter;
	private Stage window;
	private Partie partie = new Partie(10);
		
/*	public final void setSceneActuelle(EtatPartie value) {
		sceneActuelle.set(value);
	}  */
	private boolean ecranJeuOk;
	
	
	private Scene ecranDem; 
	private Scene ecranJeu;
	
	private DropShadow shadow = new DropShadow();
	
	private Button bt = new Button("Bouton test");
	
	private double anchorY, anchorX;
	private double anchorAngleX=0;
	private double anchorAngleY=0;
	private final DoubleProperty angleX= new SimpleDoubleProperty(0);
	private final DoubleProperty angleY= new SimpleDoubleProperty(0);
	
	private String newLine = System.getProperty("line.separator");
	
	// demande Nom
	
	private Button valider = new Button("Valider");
	public void configureBoutonValider() {
		valider.setPrefSize(100,80);
		valider.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				valider.setEffect(shadow);
			}
		});
		valider.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				valider.setEffect(null);
			}
		});
	}
	public Button getBoutonValider() {
		return valider;
	}
	private Label consigne = new Label("                                Veuillez entrez votre nom  -->");
	public void configureConsigne() {
		consigne.setPrefSize(700, 80);
		consigne.setFont(new Font("TimeNewsRoman", 20));
	}
	
	private TextField demandeDeNom = new TextField();
	public void configureDemandeDeNom() {
		demandeDeNom.setPromptText("Entrez votre nom");
		demandeDeNom.setFont(new Font("Arial", 20));
		demandeDeNom.setPrefSize(300, 80);
	}
	public TextField getDemandeDeNom() {
		return demandeDeNom;
	}
	public void viderDemandeDeNom() {
		Platform.runLater(new Runnable() {
			public void run() {
				getDemandeDeNom().setText("");
			}
		});
	}
	
	public void afficherDemandeNom() {
		Platform.runLater(new Runnable() {
			public void run() {
				configureDemandeDeNom();
				configureBoutonValider();
				configureConsigne();
				
				GridPane root = new GridPane();
				FlowPane flow = new FlowPane();
				flow.getChildren().addAll(demandeDeNom, valider);
				GridPane.setConstraints(flow, 1, 1);
			   GridPane.setConstraints(consigne, 0, 1);
				Rectangle rec = new Rectangle(750, 430, Color.DARKCYAN);
				
				rec.setStyle("-fx-border-color: black");
				GridPane.setConstraints(rec, 0, 0);
				
				root.getChildren().addAll(rec, flow, consigne);
				root.setStyle("-fx-background-color: darkcyan");
	//			root.setBackground(Color.);
				
				window.setScene(new Scene(root, window.getWidth(), window.getHeight()));
			}
		});
		
	}
	
	
	
	
	// attribut de l'écran de démarrage 
	private Button boutonPret=new Button("Prêt");
	public void configureBoutonPret(double width, double height) {
		boutonPret.setPrefSize(150, 100);
		boutonPret.setMaxSize(150, 100);
		boutonPret.setMinSize(150, 100);
		boutonPret.setStyle("-fx-border-color: black; -fx-background-color: darkred");
		boutonPret.setFont(new Font("arial", 20));
		
		
		boutonPret.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				boutonPret.setEffect(shadow);
			}
		});
		boutonPret.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				boutonPret.setEffect(null);
			}
		});
		boutonPret.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
			}
		});
		AnchorPane.setRightAnchor(boutonPret, width/2+150);
		AnchorPane.setLeftAnchor(boutonPret, width/2+150);
		AnchorPane.setBottomAnchor(boutonPret, height/2 - boutonPret.getHeight() +200);
	}
	
	private TextArea regles=new TextArea();
	public void configureRegles(double width, double height) {
		regles = new TextArea();
		regles.setPrefSize(400, 350);
		AnchorPane.setLeftAnchor(regles, 0.0);
		AnchorPane.setBottomAnchor(regles, 0.0);
		regles.setStyle("-fx-border-color: black");
		String rule1 = "- Les joueurs possèdent un robot chacun"+newLine+" et doivent capturer le maximum de drapeau";
		String rule2 = "- Faites attention aux tapis roulants qui vous"+newLine+" déplaceront et à ne pas tomber dans un trou !";
		String rule3 = "- A la fin de chaque tour les robots tirent droit devant "+newLine+"eux et infligent 1 point de dégats, vous possédez"+newLine+" 5 vies";
		String rule4 = "- ";
		regles.setText(rule1+newLine+newLine+rule2+newLine+newLine+rule3+newLine);
		regles.setFont(new Font("blue", 14));;
	}
	
	private Label titre = new Label();
	public void configureTitre(double width, double height) {
		titre=new Label("Bienvenue dans Robot Rally !");
		titre.setPrefSize(width/2, height/10);
		titre.setFont(new Font(36));
		AnchorPane.setTopAnchor(titre, width/10);
		AnchorPane.setRightAnchor(titre, width/2);
		AnchorPane.setLeftAnchor(titre, width/2);
	}
	
	
	//attribut multi-écran chat
	private Button boutonEnvoyer = new Button("Envoyer");
	public void configureBoutonEnvoyer(double width, double height) {
		boutonEnvoyer.setPrefSize(width/12, height/10);
		boutonEnvoyer.setStyle("-fx-border-color: black");
		AnchorPane.setRightAnchor(boutonEnvoyer, 0.0);
		AnchorPane.setBottomAnchor(boutonEnvoyer, 0.0);
	}
	
	private TextField entreeChat = new TextField();
	public void configureEntreChat(double width, double height) {
		entreeChat = new TextField();
		entreeChat.setPromptText("Ecrivez un message...");
		entreeChat.setPrefSize(3*width/12, height/10);	
		entreeChat.setStyle("-fx-border-color: black");
		AnchorPane.setRightAnchor(entreeChat, width/12);
		AnchorPane.setBottomAnchor(entreeChat, 0.0);
	}
	public void viderEntreeChat() {
		Platform.runLater(new Runnable() {
			public void run() {
				entreeChat.setText("");
			}
		});
	}
	
	private TextArea affichageChat = new TextArea();
	public void configureAffichageChat(double width, double height) {
		affichageChat = new TextArea();
		affichageChat.setPrefSize(300, 550);
		affichageChat.setStyle("-fx-border-color: black");
		AnchorPane.setRightAnchor(affichageChat, 0.0);
		AnchorPane.setLeftAnchor(affichageChat, width-20);
		AnchorPane.setBottomAnchor(affichageChat, entreeChat.getHeight()+80);
	}
	
	private TextArea listeJoueur = new TextArea();
	public void configureListeJoueur(double width, double height) {
		listeJoueur=new TextArea();
		listeJoueur.setText("Liste joueurs : ");
		listeJoueur.setPrefSize(180, 660);
		AnchorPane.setLeftAnchor(listeJoueur, 0.0);
		AnchorPane.setTopAnchor(listeJoueur, 0.0);
		listeJoueur.setStyle("-fx-border-color: black");
		
	}
	public void recevoirMessage(String msg) {
		Platform.runLater(new Runnable() {
			public void run() {
				if(affichageChat.getText()!=null) {
					affichageChat.setText(affichageChat.getText()+msg+newLine);
				}
				else {
					affichageChat.setText(msg+newLine);
				}
			}
		});
	}
	public void assombrirBoutonCarteChoisie(Button button) {
		Platform.runLater(new Runnable() {
			public void run() {
				button.setStyle("-fx-background-color: darkgray");
			}
		});
	}
	public void eclaircirBouton(Button button) {
		Platform.runLater(new Runnable() {
			public void run() {
				button.setStyle(STYLESHEET_CASPIAN);
			}
		});
	}
	
	public void rafraichirBouton() {
		Platform.runLater(new Runnable() {
			public void run() {
				eclaircirBouton(boutonCarte1);
				eclaircirBouton(boutonCarte2);
				eclaircirBouton(boutonCarte3);
				eclaircirBouton(boutonCarte4);
				eclaircirBouton(boutonCarte5);
				eclaircirBouton(boutonCarte6);
				eclaircirBouton(boutonCarte7);
				eclaircirBouton(boutonCarte8);
				eclaircirBouton(boutonCarte9);
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	public void init() {
		System.out.println("lalalalalallala");
		inter = this;
	}
	
	
	public void setScene1(Scene scene) {
		this.ecranDem=scene;
	}
	
	
	public void setPartie(Partie partie2) {
		partie=partie2;
	}
	
	public void updatePartie(ArrayList<Partie> liste) {
		while(!liste.isEmpty()) {
			Platform.runLater(new Runnable() {
				public void run() {
					redessinePlateau(liste.get(0));
					liste.remove(0);
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public void setCouleurBoutonPret(boolean rd) {
		Platform.runLater(new Runnable() {
			public void run() {
				if(rd) {
					boutonPret.setStyle("-fx-background-color: darkgreen");
				}
				else {
					boutonPret.setStyle("-fx-background-color: darkred");
				}
			}
		});
		
	}
	
	 
	public void afficherEcranDemarrage() {
		Platform.runLater(new Runnable() {
			public void run() {
				AnchorPane pane = new AnchorPane();
				configureBoutonPret(window.getWidth(), window.getHeight());
				configureBoutonEnvoyer(window.getWidth(), window.getHeight());
				configureEntreChat(window.getWidth(), window.getHeight());
				configureAffichageChat(window.getWidth(), window.getHeight());
				configureTitre(window.getWidth(), window.getHeight());
				configureRegles(window.getWidth(), window.getHeight());
				configureListeJoueur(window.getWidth(), window.getHeight());
				 pane.getChildren().addAll(boutonPret, boutonEnvoyer, affichageChat, entreeChat, titre, regles, listeJoueur);
				 pane.setStyle("-fx-background-color: royalblue");
				window.setScene(new Scene(pane, window.getWidth(), window.getHeight()));
			}
		});
		
	}
	
	// attribut nécessaire à l'écran de jeu
	private Plateau plateau;
	private SmartGroup group = new SmartGroup();
	private SubScene scenePlateau;
	private final double WIDTH=700;
	private final double HEIGHT=500;
	private Camera cam=defineCamera();
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
		
	// affichage des cartes 
	
	/*Class<?> clazz = this.getClass();
	
	InputStream stream = clazz.getResourceAsStream("/imageJeu/Est vitesse0.png");
	
	Image image = new Image(stream, 100, 200, false, false);
	ImageView imageV = new ImageView(image);  */
	
	
	
	
	private Button boutonCarte1 = new Button("Carte 1");
	public Button getBoutonCarte1() { return boutonCarte1; }
	private Button boutonCarte2 = new Button("Carte 2");
	public Button getBoutonCarte2() { return boutonCarte2; }
	private Button boutonCarte3 = new Button("Carte 3");
	public Button getBoutonCarte3() { return boutonCarte3; }
	private Button boutonCarte4 = new Button("Carte 4");
	public Button getBoutonCarte4() { return boutonCarte4; }
	private Button boutonCarte5 = new Button("Carte 5");
	public Button getBoutonCarte5() { return boutonCarte5; }
	private Button boutonCarte6 = new Button("Carte 6");
	public Button getBoutonCarte6() { return boutonCarte6; }
	private Button boutonCarte7 = new Button("Carte 7");
	public Button getBoutonCarte7() { return boutonCarte7; }
	private Button boutonCarte8 = new Button("Carte 8");
	public Button getBoutonCarte8() { return boutonCarte8; }
	private Button boutonCarte9 = new Button("Carte 9");
	public Button getBoutonCarte9() { return boutonCarte9; }
	
	
	
	public FlowPane affichageCartes(ArrayList<Carte> liste) {
		FlowPane deck = new FlowPane();
		int width=150; int height=60;
		System.out.println("Je suis dans affichage cartes : "+liste);
		for(int i=0; i<liste.size(); i++) {
			FlowPane carte = new FlowPane();
			carte.setOrientation(Orientation.VERTICAL);
			carte.getChildren().add(imageCorrespondante(liste.get(i)));
			if(i==0) {	
				
				boutonCarte1.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte1);
			}
			else if(i==1) {
				boutonCarte2.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte2);
			}
			else if(i==2) {
				boutonCarte3.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte3);
			}
			else if(i==3) {
				boutonCarte4.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte4);
			}
			else if(i==4) {
				boutonCarte5.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte5);
			}
			else if(i==5) {
				boutonCarte6.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte6);
			}
			else if(i==6) {
				boutonCarte7.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte7);
			}
			else if(i==7) {
				boutonCarte8.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte8);
			}
			else if(i==8) {
				boutonCarte9.setPrefSize(width, height);
				carte.getChildren().add(boutonCarte9);
			}
			deck.getChildren().add(carte);
		}
		return deck;
	}
	
	
	
	
	private FlowPane deck = new FlowPane();
	private FlowPane chat = new FlowPane();
	private FlowPane basChat = new FlowPane();
	
	public void setDeck(ArrayList<Carte> liste) {
		Platform.runLater(new Runnable() {
			public void run() {
				gridJeu.getChildren().remove(deck);
				deck.getChildren().removeAll(deck.getChildren());
				deck = affichageCartes(liste);
				GridPane.setConstraints(deck, 0, 1);
				gridJeu.getChildren().add(deck);
			}
			
		});
	}
	
	private TextArea affichageScore = new TextArea();
	public void setScores(Partie partie) {
		Platform.runLater(new Runnable() {
			public void run() {
				String score = "Scores : "+newLine;
				for(int i=0; i<partie.getListeRobot().size(); i++) {
					score+=partie.getListeRobot().get(i).getUsernameDetenteur()+" : "+partie.getListeRobot().get(i).getScore()+newLine;
				}
				affichageScore.setText(score);
			}
						
		});
	}
	
	
	
	private GridPane gridJeu = new GridPane();
	
	public void afficherEcranJeu(Partie partie, ArrayList<Carte> liste) {
		Platform.runLater(new Runnable() {
			public void run() {
				
				
				gridJeu.setStyle("-fx-border-color: black");
				
	/*			Partie parto = new Partie(10);
				parto.getListeRobot().add(new Robot("John", Color.AZURE));
				parto.getListeRobot().add(new Robot("Barto", Color.VIOLET));parto.getListeRobot().add(new Robot("Luffy", Color.RED));
				parto.getRobotParDetenteur("Barto").setCoordX(4); parto.getRobotParDetenteur("Luffy").setCoordY(-2);  */
								
				plateau = new Plateau(group, partie);
				scenePlateau = new SubScene(group, 1350, 700, true, null);
				GridPane.setConstraints(scenePlateau, 0, 0);
				deck = affichageCartes(liste);
				deck.setPrefSize(1350,  300);
				
				GridPane.setConstraints(deck, 0, 1);
				
				chat = new FlowPane();
				chat.setOrientation(Orientation.VERTICAL);
				basChat = new FlowPane();
				boutonEnvoyer.setPrefSize(100, 100);
				entreeChat.setPrefSize(300, 100);
				affichageChat.setPrefSize(300, 600);
				basChat.setOrientation(Orientation.HORIZONTAL);
				basChat.getChildren().addAll(entreeChat, boutonEnvoyer);
				chat.getChildren().addAll(affichageChat, basChat);
				GridPane.setConstraints(chat, 1, 0);
				
				affichageScore = new TextArea();
				affichageScore.setPrefSize(300, 250);
				affichageScore.setText("Scores : "+newLine);
				GridPane.setConstraints(affichageScore, 1, 1);
				
				
				scenePlateau.setCamera(cam);
				scenePlateau.setFill(Color.RED);
				
			//	ArrayList<Carte> liste = new ArrayList<>();
			//	liste.add(new Carte("Bob")); liste.add(new Carte("Kevin")); liste.add(new Carte("Jack")); liste.add(new Carte("Alv"));
			//	liste.add(new Carte("Jo")); liste.add(new Carte("Dams")); liste.add(new Carte("Alfred")); liste.add(new Carte("JOOON"));
			//	liste.add(new Carte("ta onne"));
			//	deck=new AffichageCartes(liste);
		//		deck.setPrefSize(1350, 400);
				
		//		GridPane.setConstraints(deck, 0, 1);
				
		//		affichageScore.setStyle("-fx-border-color: black");
		//		affichageScore.setMaxSize(100, 700);
		//		GridPane.setConstraints(affichageScore, 1, 0);
				
				
				
				
				gridJeu.getChildren().addAll(scenePlateau, deck, chat, affichageScore);
			//	ecr2 = new EcranPartie(partie);
				ecranJeu = new Scene(gridJeu, window.getWidth(), window.getHeight());
				initMouseControl(group, scenePlateau);  
				window.setScene(ecranJeu);
			}
		});
		
	}
	
	public void redessinePlateau(Partie partie) {
		Platform.runLater(new Runnable() {
			public void run() {
				group.getChildren().removeAll(group.getChildren());
				plateau = new Plateau(group, partie);
			}
		});	
	}
	
	public void resetEffetBoutons() {
		
	}
	
	
	
	
/*	public void ajouterBouton() {
		Platform.runLater(new Runnable() {
			public void run() {
				Group g = new Group();
				g.getChildren().add(bt);
				window.setScene(new Scene(g, window.getWidth(), window.getHeight()));
			}
			
		});
		
	}
	public Button getBoutonbt() {
		return bt;
	}  */
	
	public ArrayList<Carte> generateListe(){
		ArrayList<Carte> list = new ArrayList<>();
		for(int i=0; i<9; i++) {
			list.add(new Carte());
		}
		return list;
	}
	
	
	// ecran de mort
	
	private Label msgDeMort= new Label();
	private Button spectate= new Button("Mode spectateur");
	private Button quitter = new Button("Quitter");
	private AnchorPane ecranMort = new AnchorPane();
	
	public Button getBoutonSpectate() {
		return spectate;
	}
	public Button getBoutonQuitter() {
		return quitter;
	}
	
	
	private FlowPane boutons = new FlowPane();
	
	
	public void afficherEcranMort() {
		Platform.runLater(new Runnable() {
			public void run() {
				msgDeMort.setPrefSize(800, 200);
				
				msgDeMort.setText("Vous êtes mort");
				msgDeMort.setFont(new Font("Old English Text MT", 60));
			//	BorderPane.setAlignment(msgDeMort, Pos.CENTER);
			//	ecranMort.setCenter(msgDeMort);
				spectate.setPrefSize(100, 60);
				
				boutons.setOrientation(Orientation.HORIZONTAL);
		//		boutons.setOpaqueInsets(new Insets(20, 20, 20, 20));
			
			//	BorderPane.setAlignment(spectate, Pos.BOTTOM_LEFT);
				quitter.setPrefSize(100, 60);
			//	BorderPane.setAlignment(quitter, Pos.BOTTOM_RIGHT);
				boutons.getChildren().addAll(quitter, spectate);
				boutons.setPrefSize(200, 60);
				
		//		BorderPane.setAlignment(boutons, Pos.BOTTOM_CENTER);
				AnchorPane.setRightAnchor(msgDeMort, window.getWidth()/2);
				AnchorPane.setLeftAnchor(msgDeMort, window.getWidth()/2);
				AnchorPane.setTopAnchor(msgDeMort, 250.0);
				ecranMort.getChildren().add(msgDeMort);
				
				AnchorPane.setRightAnchor(boutons, window.getWidth()/2);
				AnchorPane.setLeftAnchor(boutons, window.getWidth()/2+100);
				AnchorPane.setBottomAnchor(boutons, 250.0);
				ecranMort.getChildren().add(boutons);
				
		//		complet.setOrientation(Orientation.VERTICAL);
		//		complet.getChildren().addAll(msgDeMort, boutons);
		//		complet.setStyle("-fx-background-color: darkgrey");
		//		ecranMort.setRight(boutons);
				ecranMort.setStyle("-fx-background-color: dimgrey");
				Scene sceneMort = new Scene(ecranMort, window.getWidth(), window.getHeight());
				sceneMort.setFill(Color.DARKGREY);
				System.out.println("l'écran de mort est prêt : je change la scène");
				window.setScene(sceneMort);
				
			}
		});
	}
	
	// ecranFin
	
	// on va réutiliser affichageScore   chat     quitter
	
	private Label msgFin = new Label("Merci d'avoir joué à Robot Rally ! ");
	private BorderPane ecranFin = new BorderPane();
	
	public void afficherEcranFin() {
		Platform.runLater(new Runnable() {
			public void run() {
					quitter.setPrefSize(100, 60);
					msgFin.setPrefSize(800, 100);
					msgFin.setFont(new Font(40));
					
					boutonEnvoyer.setPrefSize(100, 100);
					entreeChat.setPrefSize(300, 100);
					affichageChat.setPrefSize(300, 600);
					if(basChat.getChildren().size()==0) {
						basChat.setOrientation(Orientation.HORIZONTAL);
					basChat.getChildren().addAll(entreeChat, boutonEnvoyer);
					chat.setOrientation(Orientation.VERTICAL);
					chat.getChildren().addAll(affichageChat, basChat);
					}
					
					
					chat.setPrefSize(300, 600);
					affichageScore.setPrefSize(100, 200);
					affichageScore.setMaxSize(150, 200);
					
					BorderPane.setAlignment(quitter, Pos.CENTER);
					ecranFin.setCenter(quitter);
					
					BorderPane.setAlignment(msgFin, Pos.CENTER);
					ecranFin.setTop(msgFin);
					
					BorderPane.setAlignment(affichageScore, Pos.BOTTOM_CENTER);
					ecranFin.setLeft(affichageScore);
					
					BorderPane.setAlignment(chat, Pos.BOTTOM_CENTER);
					ecranFin.setRight(chat);
					
			//		ecranFin.setStyle("-fx-background-color: ");
					
				//	ecranFin.getChildren().addAll(quitter, msgFin, affichageScore, chat);
					Scene sceneFin = new Scene(ecranFin, window.getWidth(), window.getHeight());
					Thread color = new Thread(new Runnable() {
						public void run() {
							while(true) {
								int h =(int)(Math.random()*tab.length);
								Platform.runLater(new Runnable() {
									public void run() {
										ecranFin.setStyle("-fx-background-color: "+tab[h]);
									}
								});
								try {
									Thread.sleep(4000);
								} catch(InterruptedException e) { e.printStackTrace();}
							}
						}
					}); color.setDaemon(true); color.start();
					window.setScene(sceneFin);
			}
		});
	}
	
	private String tab[] = { "green" , "red" , "yellow", "purple", "blue", "pink" , "orange" };
	
	// ecran Credits
	
	private Label prenom1 = new Label("Berrefas Yasmine");
	private Label prenom2 = new Label("Chenouf Ayoub");
	private Label prenom3 = new Label("Tcherre Karim");
	private Label prenom4 = new Label("Truchot Solène");
	private Label prenom5 = new Label("Vedel Alvin");
	private FlowPane panelCredit = new FlowPane();
	
	public Label modifLabel(Label lab) {
		lab.setPrefSize(200, 50);
		lab.setTextFill(Color.WHITE);
		lab.setFont(new Font("Baskerville Old Face", 20));
		return lab;
	}
	
	public void afficherCredits() {
		Platform.runLater(new Runnable() {
			public void run() {
				AnchorPane border = new AnchorPane();
				
				panelCredit.setOrientation(Orientation.VERTICAL);
				
				
				panelCredit.getChildren().addAll(modifLabel(prenom1), modifLabel(prenom2), modifLabel(prenom3), modifLabel(prenom4), modifLabel(prenom5));
				AnchorPane.setRightAnchor(panelCredit, window.getWidth()/2);
				AnchorPane.setLeftAnchor(panelCredit, window.getWidth()/2);
				AnchorPane.setBottomAnchor(panelCredit, 200.0);
				border.getChildren().add(panelCredit);
				border.setStyle("-fx-background-color: black");
				Scene credits = new Scene(border, window.getWidth(), window.getHeight());
				
				window.setScene(credits);
			}
		});
	}
	
	
	
	
	
	
	public void start(Stage arg0) throws Exception{
		window = arg0;
		window.setFullScreen(true);
		window.setTitle("Robot Rally");
	//	afficherEcranMort();
	//	afficherEcranDemarrage();
	/*	msgDeMort.setPrefSize(800, 200);
		msgDeMort.setText("Vous êtes mort");
		msgDeMort.setFont(new Font("Old English Text MT", 40));
		BorderPane.setAlignment(msgDeMort, Pos.CENTER);
		spectate.setPrefSize(100, 60);
		BorderPane.setAlignment(spectate, Pos.BOTTOM_LEFT);
		quitter.setPrefSize(100, 60);
		BorderPane.setAlignment(quitter, Pos.BOTTOM_RIGHT);
		ecranMort.getChildren().addAll(msgDeMort, spectate, quitter);
		Scene sceneMort = new Scene(ecranMort, window.getWidth(), window.getHeight());
	//	sceneMort.setFill(Color.BLUE);
		System.out.println("l'écran de mort est prêt : je change la scène");
		window.setScene(sceneMort);
		
	
		
		BorderPane group = new BorderPane();
		BorderPane.setAlignment(msgDeMort, Pos.CENTER);
		BorderPane.setAlignment(spectate, Pos.BOTTOM_LEFT);
		BorderPane.setAlignment(quitter, Pos.BOTTOM_RIGHT);
		group.setCenter(msgDeMort); //group.setCenter(spectate); group.setCenter(quitter);
		
		
		Scene scene = new Scene(group, window.getWidth(), window.getHeight());
		scene.setFill(Color.RED);
		window.setScene(scene);   */
		
		
		
		window.show();
		
		
		
		
		
		
		
		
		
/*		ArrayList<Carte> listeCarte = generateListe();
		System.out.println(listeCarte);
		Scene scene = new Scene(affichageCartes(listeCarte), window.getWidth(), window.getHeight());
		window.setScene(scene);  */
	/*	Group root = new Group();
		root.getChildren().add(imageV);
		Scene scene = new Scene(root, window.getWidth(), window.getHeight());
		window.setScene(scene); *:
		
		
		
		
		// 1ère partie écran d'accueil
		
/*		ecr= new EcranDemarrage(window.getWidth(), window.getHeight());
		ecranDem = new Scene(ecr, window.getWidth(), window.getHeight());
		window.setScene(ecranDem); */
		
	/*	ecr.getBoutonPret().setOnAction(new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent arg0) {
				System.out.println("im in handle bitch");
				if(arg0.getSource()==ecr.getBoutonEnvoyer()) {
					System.out.println("bouton fonctionne");
				}
				
			}
		});  */
		
		
		
		
		
		/*sceneActuelle.addListener(new ChangeListener<EtatPartie>() {

			@Override
			public void changed(ObservableValue<? extends EtatPartie> arg0, EtatPartie arg1, EtatPartie arg2) {
				if(sceneActuelle.get().getEtat().equals(Etat.enJeu)){
					System.out.println("ecran jeu la");
					afficherEcranJeu();
				}
				else if(sceneActuelle.get().getEtat().equals(Etat.ecranDemarrage)) {
					afficherEcranDemarrage();
					System.out.println("ecran demarrage la");
				}
				
			}
			
		});   */
			
	
		

		
		//2ème partie affichage du jeu
	/*	partie = new Partie(10);
		ecr2 = new EcranPartie(partie);
		
		
		
		
		Robot roby = new Robot("Kevin", Color.BLUEVIOLET);
		Robot3D roby3D = new Robot3D(roby.getColor(), Direction.Droite);
		GroupRobot rgb = new GroupRobot();
		roby3D.deplacerRobot3D(0, -100, 0);
		roby3D.deplacerRobot3D(4*550, -100, 550);
		rgb.ajouterRobot(roby3D);
		rgb.setTranslateY(-100);
		ecr2.getSmartGroup().getChildren().addAll(rgb);
		
		//seule partie nécessaire
		ecranJeu = new Scene(ecr2, window.getWidth(), window.getHeight(), true);
		
		initMouseControl(ecr2.getSmartGroup(), ecr2.getScenePlateau()); 
		window.setScene(ecranJeu);  
		
		rgb.setTranslateX(4*550);
		rgb.setTranslateZ(-550);   */
		  
		
		//3ème partie affichage de fin de partie 
		
		
	
		
		
		
		
		
		
	}
	
	public static void main(String[]args) {
		launch(args);
	}
	
	
	

	
	
	public void setBoolean2(boolean b) {
		this.ecranJeuOk=b;
	}
	
	
	
	
	private void initMouseControl(SmartGroup group, Scene scene) {
		Rotate xRotate; Rotate yRotate;
		group.getTransforms().addAll(xRotate=new Rotate(0, Rotate.X_AXIS), yRotate=new Rotate(0, Rotate.Y_AXIS));
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);
		
		scene.setOnMousePressed(event -> {
			anchorX=event.getSceneX();
			anchorY=event.getSceneY();
			anchorAngleX=angleX.get();
			anchorAngleY=angleY.get();
		});	
		scene.setOnMouseDragged(event -> {
			angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
			angleY.set(anchorAngleY + anchorX - event.getSceneX());
		});
		scene.addEventHandler(ScrollEvent.SCROLL, event -> {
			double movement = event.getDeltaY();
			group.translateZProperty().set(group.getTranslateZ()+movement);
		});
			
		
	}

	private void initMouseControl(SmartGroup group, SubScene scene) {
		Rotate xRotate; Rotate yRotate;
		group.getTransforms().addAll(xRotate=new Rotate(0, Rotate.X_AXIS), yRotate=new Rotate(0, Rotate.Y_AXIS));
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);
		
		scene.setOnMousePressed(event -> {
			anchorX=event.getSceneX();
			anchorY=event.getSceneY();
			anchorAngleX=angleX.get();
			anchorAngleY=angleY.get();
		});	
		scene.setOnMouseDragged(event -> {
			angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
			angleY.set(anchorAngleY + anchorX - event.getSceneX());
		});
		scene.addEventHandler(ScrollEvent.SCROLL, event -> {
			double movement = event.getDeltaY();
			group.translateZProperty().set(group.getTranslateZ()+movement);
		});
			
		
	}

	public Button getBoutonPret() {
		return boutonPret;
	}

	public TextArea getRegles() {
		return regles;
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
	
	private Pane pane = new Pane();
	private StringBuilder sb = new StringBuilder(); 
	
	
	public ImageView imageCorrespondante(Carte carte) {
		Class<?> clazz = this.getClass();
		int width=150; int height=250;
		
		if(carte.getAvancement()==0) {
			if(carte.getDirection()==Direction.Haut) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Nord vitesse0.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Bas) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Sud vitesse0.png");
				Image image = new Image(stream,  width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Gauche) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Ouest vitesse0.png");
				Image image = new Image(stream,  width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Est vitesse0.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
		}
		else if(carte.getAvancement()==1) {
			if(carte.getDirection()==Direction.Haut) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Nord vitesse1.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Bas) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Sud vitesse1.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Gauche) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Ouest vitesse1.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Est vitesse1.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
		}
		else if(carte.getAvancement()==2) {
			if(carte.getDirection()==Direction.Haut) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Nord vitesse2.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Bas) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Sud vitesse2.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Gauche) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Ouest vitesse2.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Est vitesse2.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
		}
		else {
			if(carte.getDirection()==Direction.Haut) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Nord vitesse3.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Bas) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Sud vitesse3.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else if(carte.getDirection()==Direction.Gauche) {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Ouest vitesse3.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
			else {
				InputStream stream = clazz.getResourceAsStream("/imageJeu/Est vitesse3.png");
				Image image = new Image(stream, width, height, false, false);
				ImageView imageV = new ImageView(image);
				return imageV;
			}
		}
		
	}
	
	
	

}
