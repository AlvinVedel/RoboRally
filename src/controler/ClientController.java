package controler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Carte;
import model.Partie;
import view.InterfaceGraphique;

public class ClientController {
	

	private ViewController viewController;
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
//	private String username;
	
	
	// attribut servant a envoyer la liste de carte choisie  vc --> cc
	private final ObjectProperty<ArrayList<Carte>> listeCartesChoisies = new SimpleObjectProperty<ArrayList<Carte>>(); 
	public final ArrayList<Carte> getListeCarteChoisies() { return listeCartesChoisies.get(); }
	public final void setListeCartesChoisies(ArrayList<Carte> value) { listeCartesChoisies.set(value); }
	public final ObjectProperty<ArrayList<Carte>> listeCarteChoisiesProperty(){ return listeCartesChoisies; }
	
	
	// cc--> vc
	private final ObjectProperty<Partie> partie = new SimpleObjectProperty<Partie>(new Partie(10));
	public final Partie getPartie() { return partie.get(); }
	public final void setPartie(Partie value) { partie.set(value); }
	public final ObjectProperty<Partie> partieProperty(){ return partie; }
	
	
	// cc--> vc
	private final ObjectProperty<EtatPartie> etat = new SimpleObjectProperty<EtatPartie>(new EtatPartie(Etat.demandeNom));
	public final void setEtat(EtatPartie value) { etat.set(value); }
	public final EtatPartie getEtat() { return etat.get(); }
	public final ObjectProperty<EtatPartie> etatProperty(){ return etat; }
	
	
	
	// cc--> vc
	private final ObjectProperty<ListeCarte> listeCarte = new SimpleObjectProperty<ListeCarte>(new ListeCarte(new ArrayList<Carte>()));
	public final void setListeCarte(ListeCarte value) { listeCarte.set(value); }
	public final ListeCarte getListeCarte() { return listeCarte.get(); }
	public final ObjectProperty<ListeCarte> listeCarteProperty(){ return listeCarte; }
	
	
	// liaison attribut demande de nom vc --> cc
	private final StringProperty username = new SimpleStringProperty("");
	public final void setUsername(String value) { username.set(value); }
	public final String getUsername() { return username.get(); }
	public final StringProperty usernameProperty() { return username; }
	
	// vc --> cc
	private final BooleanProperty pret = new SimpleBooleanProperty(false);
	public final void setPret(boolean value) { pret.set(value); }
	public final Boolean getPret() { return pret.get(); }
	public final BooleanProperty pretProperty() { return pret; }
	
	private final StringProperty msgEnvoye = new SimpleStringProperty("");
	public final void setMsgEnvoye(String value) { msgEnvoye.set(value); }
	public final String getMsgEnvoye() { return msgEnvoye.get(); }
	public final StringProperty msgEnvoyeProperty() { return msgEnvoye; }
	
	private final StringProperty msgRecu = new SimpleStringProperty("");
	public final void setMsgRecu(String value) { msgRecu.set(value); }
	public final String getMsgRecu() { return msgRecu.get(); }
	public final StringProperty msgRecuProperty() { return msgRecu; }
	
	private final BooleanProperty listeComplete = new SimpleBooleanProperty(false);
	public final void setListeComplete(boolean value) { listeComplete.set(value); }
	public final Boolean getListeComplete() { return listeComplete.get(); }
	public final BooleanProperty listeCompleteProperty() { return listeComplete; }
	
	private final BooleanProperty retourListeComplete = new SimpleBooleanProperty(false);
	public final void setRetourListeComplete(boolean value) { retourListeComplete.set(value); }
	public final Boolean getRetourListeComplete() { return retourListeComplete.get(); }
	public final BooleanProperty retourListeCompleteProperty() { return retourListeComplete; }
	
	private final ObjectProperty<DemandeClient> demandeClient = new SimpleObjectProperty<DemandeClient>();
	public final void setDemandeClient(DemandeClient value) { demandeClient.set(value); }
	public final DemandeClient getDemandeClient() { return demandeClient.get(); }
	public final ObjectProperty<DemandeClient> demandeClientProperty() { return demandeClient; }
	
	
	
	
	public ClientController() {
		viewController = new ViewController();
		ajoutBindingEtChangeListener();		
	}
	public ClientController(ViewController vc) {
		this.viewController=vc;
		ajoutBindingEtChangeListener();	
	}
	
	
	public ClientController(Socket socket) {
		viewController = new ViewController();
		ajoutBindingEtChangeListener();	
		this.socket = socket;
		try {
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
		demarrageThreadCommunicationServeur();

	}
	
	public void ajoutBindingEtChangeListener() {
		System.out.println("Je suis dans la méthode ajoutBinding...");
		viewController.partieProperty().bind(partie);
		viewController.listeCarteProperty().bind(listeCarte);
		viewController.etatProperty().bind(etat);	
		viewController.retourListeCompleteProperty().bind(retourListeComplete);
		viewController.msgRecuProperty().bind(msgRecu);
		this.username.bind(viewController.usernameProperty());
	//	viewController.usernameProperty().bindBidirectional(username);
		username.addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// envoyer au serveur? créer un robot à ce nom?
				System.out.println("username : "+username.get());
				try {
					if(oos!=null) {
				oos.writeObject(getUsername());
				oos.flush(); oos.reset();
				System.out.println("username envoye");
					}
				} catch(IOException e) { e.printStackTrace(); }
			}
		});
		this.listeCartesChoisies.bind(viewController.listeCarteChoisiesProperty());
		listeCartesChoisies.addListener(new ChangeListener<ArrayList<Carte>>() {
			public void changed(ObservableValue<? extends ArrayList<Carte>> arg0, ArrayList<Carte> arg1,
					ArrayList<Carte> arg2) {
				System.out.println("il y a eu un changement dans la liste de cartes choisies (cc)");
				// envoyer au serveur
				if(listeCartesChoisies.get().size()==5) {
					System.out.println("il y a 5 cartes : je les ennvoies");
					try {
						if(oos!=null) {
					oos.writeObject(getListeCarteChoisies());
					oos.flush();
					oos.reset();
					System.out.println("listeCarteChoisies envoye");
						}
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("il y a seulement : "+listeCartesChoisies.get().size()+" cartes");
				}
			}
		});
		this.pret.bind(viewController.pretProperty());
		pret.addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					// envoyer au serveur
				
				try {
					if(oos!=null) {
					oos.writeObject(getPret());
					oos.flush();
					oos.reset();
					System.out.println("pret envoye ");
					}
				} catch(IOException e) { e.printStackTrace(); }
			}
		});
		this.msgEnvoye.bind(viewController.msgEnvoyeProperty());
		msgEnvoye.addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				System.out.println("message à envoyer : "+msgEnvoye.get());
				try {
					if(oos!=null) {
					oos.writeObject(getMsgEnvoye());
					oos.flush();
					oos.reset();
					System.out.println("msgEnvoye envoye");
					}
				} catch(IOException e) { e.printStackTrace(); }
			/*	System.out.println("je démarre le thread d'affichage listeChoisies vc / cc");
				Thread deamon = new Thread(new Runnable() {
					public void run() {
						int i=0;
						while(i<10) {
							System.out.println("CC : "+getListeCarteChoisies());
							System.out.println("VC : "+viewController.getListeCarteChoisies());
							System.out.println("");
							i++;
							try { Thread.sleep(1000); } catch(Exception e) { e.printStackTrace(); }
						}
					}
				}); deamon.setDaemon(true); deamon.start();*/
			}  
			
		});
		this.listeComplete.bind(viewController.listeCompleteProperty());
		listeComplete.addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(getListeComplete() && getListeCarteChoisies().size()==5) {
					try {
						oos.writeObject(new ListeCarte(getListeCarteChoisies()));
						oos.flush(); oos.reset();
						System.out.println("j'ai envoyé la liste de carte choisies");
						setRetourListeComplete(!getRetourListeComplete());
					} catch(IOException e) { e.printStackTrace(); }
				}
				else {
					System.out.println("boolean de listeComplete : "+getListeComplete()+" size liste : "+getListeCarteChoisies().size());
				}	
			}
		});
		this.demandeClient.bind(viewController.demandeClientProperty());
		demandeClient.addListener(new ChangeListener<DemandeClient>() {
			public void changed(ObservableValue<? extends DemandeClient> arg0, DemandeClient arg1, DemandeClient arg2) {
				System.out.println("nouvelle demande du client");
				if(getDemandeClient().getDemande()!=null) {
					try {
						oos.writeObject(getDemandeClient());
						oos.flush();
						oos.reset();
					} catch(IOException e) { e.printStackTrace(); }
				}
					
			}
			
		});
		
		
		
		
	}
	
	
	
	public void demarrageThreadCommunicationServeur() {
		new Thread(new Runnable() {
			public void run() {
				while(socket.isConnected()) {
					try {
						Object o = ois.readObject();
						System.out.println("Objet o recu : "+o);
						if(o instanceof ListeCarte) {
							setListeCarte(((ListeCarte) o));
						}
						else if(o instanceof Partie) {
							setPartie((Partie)o);
						}
						else if(o instanceof ListePartie) {
							updatePartie((ListePartie)o);
						}
						else if(o instanceof String) {
							setMsgRecu((String)o);
							System.out.println("msg recu par cc");
						}
						else if(o instanceof EtatPartie) {
							setEtat((EtatPartie)o);
						}
						else {
							System.err.println("un objet inconnu a été envoyé");
						}
					} catch(IOException | ClassNotFoundException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}).start();
		
	/*	new Thread(new Runnable() {
			public void run() {
				while(socket.isConnected()) {
					if(listeCartesChoisies.get().size()==5) {
						try {
							oos.writeObject(listeCartesChoisies);
							oos.flush(); oos.reset();
						} catch(IOException e) { e.printStackTrace(); }
						listeCartesChoisies.get().removeAll(listeCartesChoisies.get());
					}
				}
			}
		}).start();  */
		
	}
	
	public void updatePartie(ListePartie liste) {
		ArrayList<Partie> list = liste.getListePartie();
		for(int i=0; i<list.size();i++) {
			setPartie(list.get(i));
			System.out.println("je set la partie puis j'attendrai 2sec");
			try {
				Thread.sleep(2000);
			} catch(Exception e) {
				System.err.println("Le sommeil de l'update s'est mal passé");
			}
		}
	}
	


	public ViewController getViewControler() {
		return viewController;
	}
	public void setViewControler(ViewController viewController) {
		this.viewController = viewController;
	}
	
	public static void main(String[]args) throws InterruptedException, UnknownHostException, IOException {
		
		Socket socket = new Socket("localhost", 1234);
		ClientController cc = new ClientController(socket);
	//	Thread.sleep(2000);
	//	cc.setEtat(new EtatPartie(Etat.credits));
	//	cc.setMsgRecu("Bienvenu dans robot Rally");
	//	cc.getViewControler().setListeCarteChoisies(new ArrayList<Carte>());
		//cc.setPartie(new Partie(10));
	/*	cc.setEtat(new EtatPartie(Etat.demandeNom));
		Thread.sleep(1500);
		cc.setEtat(new EtatPartie(Etat.ecranDemarrage));
		
		Thread.sleep(2000);
		cc.setPartie(new Partie(10));
		ArrayList<Carte> liste = generateListe();
		cc.setListeCarte(new ListeCarte(liste));
		
		Thread.sleep(1000);
		
		cc.setEtat(new EtatPartie(Etat.enJeu));
	//	cc.getViewControler().setMsgEnvoye("Bienvenue dans RobotRally");
		
	//	Thread.sleep(4000);
		
	//	cc.setEtat(new EtatPartie(Etat.demandeNom));
		
		
	//	cc.setEtat(new EtatPartie(Etat.ecranDemarrage));
	//	System.out.println("ecran demarrage affiché");
		
	
		
		Thread usernamecc = new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(i<10) {
					i++;
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cc.setPartie(new Partie(10));
				}
			}
		}); usernamecc.setDaemon(true); usernamecc.start();
		
	/*	System.out.println("username vc : "+cc.getViewControler().getUsername());
		System.out.println("username cc : "+cc.getUsername());
		//cc.setEtat(new EtatPartie(Etat.enJeu)); */
	//	System.out.println("ecran Jeu affiché");
		
	}
	
	
	
}
