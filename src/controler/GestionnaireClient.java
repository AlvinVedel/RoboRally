package controler;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import model.Carte;
import model.ElementMap;
import model.NatureElement;
import model.Partie;
import model.Robot;
import view.InterfaceGraphique;

public class GestionnaireClient implements Runnable {

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String username;
	private static ArrayList<GestionnaireClient> listeGestionnaire = new ArrayList<>();
	private static ArrayList<GestionnaireClient> listeGestionnaireEnJeu = new ArrayList<>();
	
	
	private static Partie partie = new Partie(10);
	
	
	private static boolean partieEnCours=false;
	private boolean joueurPret=false;
	private boolean carteRecues=false;
	
	private ArrayList<Carte> listeCartes;
	private static ArrayList<Carte> listeCartesGlobale=new ArrayList<>();
		
	
	public GestionnaireClient(Socket socket) {
		try {
			this.socket=socket;
			oos= new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			listeGestionnaire.add(this);
			System.out.println("Test print gestionnaire");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized void run() {
		try {
			System.out.println("debut run");
			if(!partieEnCours) {
				System.out.println("premier if");
				// réception username
				oos.writeObject(new EtatPartie(Etat.demandeNom));
				Object o = ois.readObject();
				username = (String) o;
				o=null;
				oos.writeObject(new EtatPartie(Etat.ecranDemarrage));
				while(!isLobbyReady()) {
			//		Thread.sleep(40);
					System.out.println("Le lobby n'est pas prêt");
					//lecture du booléen
					o = ois.readObject();
					if(o instanceof String) {
						envoyerAtous((String) o);
						System.out.println("j'envoie le message à tous le monde");
					}
					else if(o instanceof Boolean) {
						joueurPret=(Boolean)o;
						System.out.println("je change le boolean joueurPret : "+joueurPret+" pour "+username);
					}
					else {
						System.out.println("Objet reçu non reconnu");
					}
				}
				Thread.sleep(1000);
				System.err.println("j'ai attendu 1sec pour synchro post pret");
		/*		if(isLobbyReady()) {
					listeGestionnaireEnJeu.add(this);
					System.out.println("j'ajoute "+username+" au listeGestionnaireEnJeu");
				}
				Thread.sleep(1000); */
				//la partie est en cours, personne ne peut plus y entrer
			//	partieEnCours=true;
				System.out.println("Je suis après le while lobby pas pret pour --> "+username);
				
				Thread.sleep(1000);
				System.err.println("j'ai attendu 1sec pour synchro post pret");
			//	listeGestionnaireEnJeu.add(this);
				// ici tout le monde est censé être prêt
				
		//		afficherGestionnaireEnJeu();
			//	for(int i=0; i<listeGestionnaire.size(); i++) {
					double r = Math.random(); double g = Math.random(); double b=Math.random();
					ElementMap spawnRobot = getCaseVide();
					partie.getListeRobot().add(new Robot(username, r, g, b, spawnRobot.getCoordX(), spawnRobot.getCoordY()));
					partie.setNbJoueurs(partie.getNbJoueurs()+1);
					System.out.println("nb joueurs : "+partie.getNbJoueurs());
					System.out.println(partie);
					oos.writeObject(new EtatPartie(Etat.enJeu)); oos.flush(); oos.reset();
					envoyerAtous(partie);
		//		}
				
				//nouveau robot ajouté pour chaque joueur				
		//		envoyerAtousDansPartie(new EtatPartie(Etat.enJeu));
		//		envoyerAtousDansPartie(partie);
				listeCartes=generateListe();
				System.out.println("liste de "+username+" : "+listeCartes);
				oos.writeObject(new ListeCarte(listeCartes)); oos.flush(); oos.reset();
				Thread.sleep(500);
				System.err.println("j'ai attendu 0.5sec pour synchro post pret");
				
				while(!partie.isEncoreDrapeauCapturable() && partie.getRobotParDetenteur(username).getPV()>0 && socket.isConnected()) {
					// tant que le joueur est rattaché au gestionnaire
					System.out.println(partie);
					
					
					// réception des cartes choisies : 
					o = ois.readObject();
					
					if(o instanceof ListeCarte) {
						carteRecues=true;
						System.out.println("o est bien instance d listeCarte --> ");
						System.out.println(((ListeCarte)o).getListeCarte());
						// on ajoute les carte du joueur associé à une liste globale
						ArrayList<Carte> liste = ((ListeCarte)o).getListeCarte();
						listeCartesGlobale.addAll(liste);
						System.out.println(listeCartesGlobale);
						
						System.out.println("EveryoneHasSend : "+hasEveryoneSend());
						while(!hasEveryoneSend()) {
							//en attente de l'envoi de la part de tout le monde
							Thread.sleep(1000);
							System.out.println("pas tout le monde a envoyé");
						}
						// ici tout le monde a envoyé
						System.out.println("juste avant le tour");
						tour();
						partie.tirerLaser(partie.getRobotParDetenteur(username));
						if(partie.getRobotParDetenteur(username).getPV()==0) { partie.getListeRobot().remove(partie.getRobotParDetenteur(username)); }
						
						Thread.sleep(1000);
						envoyerAtous(partie);
						System.out.println("Tour terminé");
					//	listePartie.getListePartie().removeAll(listePartie.getListePartie());
					//	System.out.println("liste partie réinitialisée");
						// on réinitialise tout, les listes et o qui doit à nouveau être lu
						listeCartes.removeAll(listeCartes);
						listeCartesGlobale.removeAll(listeCartesGlobale);
				//		o=null;
						
						//affichage des scores
						affichageScore();
						

						listeCartes=generateListe();
						// envoi de la liste de carte
						oos.writeObject(new ListeCarte(listeCartes));
						oos.flush(); oos.reset(); 
						
							
					}
					else if(o instanceof String) {
						envoyerAtous((String)o);
						System.out.println("Message recu depuis un joueur et envoyé à tous");
						}
					else if(o instanceof DemandeClient) {
						if(((DemandeClient)o).getDemande().equals("spectate")) {
							oos.writeObject(new EtatPartie(Etat.spectateur));
							oos.flush(); oos.reset();
						}
						else if(((DemandeClient)o).getDemande().equals("quitter")) {
							oos.writeObject(new EtatPartie(Etat.credits));
							oos.flush(); oos.reset();
						}
					}
					else {
						System.out.println("Ce n'est pas une liste de carte");
					}
				}
				Thread thread = new Thread(new Runnable() {
					public void run() {
						try {
						Object recu = null;
						while(true) {
								recu = ois.readObject();
								if(recu instanceof DemandeClient) {
									if(((DemandeClient)recu).getDemande().equals("spectate")) {
										oos.writeObject(new EtatPartie(Etat.spectateur));
										oos.flush(); oos.reset();
									}
									else if(((DemandeClient)recu).getDemande().equals("quitter")) {
										oos.writeObject(new EtatPartie(Etat.credits));
										oos.flush(); oos.reset();
									}
								}
								else if(recu instanceof String) {
									envoyerAtous((String)recu);
								}
							}
						}catch(IOException | ClassNotFoundException e) {
								e.printStackTrace();
								System.err.println("la connexion avec le client a été perdue");
					}
					}}); thread.setDaemon(true); thread.start();
				if(partie.getRobotParDetenteur(username).getPV()==0) {
					partie.getListeRobot().remove(partie.getRobotParDetenteur(username));
					System.out.println("Votre robot est mort et a été retiré de la partie");
					envoyerAtous(partie);
					Thread.sleep(1000);
					if(partie.getListeRobot().size()>0) {
						envoyerAtous(new EtatPartie(Etat.perduMaisEnCours));
					}
					else {
						envoyerAtous(new EtatPartie(Etat.ecranFin));
					}
					
					
				}
				else if(!partie.isEncoreDrapeauCapturable()) {
					envoyerAtous(new EtatPartie(Etat.ecranFin));
				}
				
				
				
			 
			
			
			}
			else {
				// else de partieEnCours
				System.err.println("Partie déjà en cours");
				String msg = "Une partie est déja en cours";
				oos.writeObject(msg);
				oos.flush();
				oos.reset();
			}
		}
		catch(SocketException e) {
			System.out.println("Problème de connexion catch ici");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	// true si tous les joueurs sont prêts à débuter la partie, faux sinon 
	public boolean isLobbyReady() {
		boolean rd = true;
		for(int i=0; rd && i<listeGestionnaire.size();i++) {
				rd=rd && listeGestionnaire.get(i).joueurPret;
				System.out.println(rd+" dans le gestionnaire de "+listeGestionnaire.get(i).username);
		}
		return rd;
	}
	
	public ElementMap getCaseVide() {
		ElementMap el = partie.getMap().getElementMapParNature(NatureElement.None);
		if(!partie.isDejaRobot(el)) {
			return el;
		}
		else {
			return getCaseVide();
		}
	}
	
	public void afficherGestionnaireEnJeu() {
		for(int i=0; i<listeGestionnaireEnJeu.size(); i++) {
			System.out.println(username);
		}
	}
	
	
	// affichage des scores en fin de tour
	public void affichageScore() {
		for(int i=0; i<listeGestionnaire.size(); i++) {
			if(partie.getRobotParDetenteur(username)!=null) {
				System.out.println("Robot de "+listeGestionnaire.get(i).username+" : "+partie.getRobotParDetenteur(username).getScore());
			}
			else {
				System.out.println("Robot de "+listeGestionnaire.get(i).username+"est mort");
			}
		}
	}
	
	
	
	// true si tout le monde a envoyé des cartes, faux sinon
	public boolean hasEveryoneSend() {
		boolean everyone = true;
		for(int i=0; everyone && i<listeGestionnaire.size(); i++) {
			everyone = everyone && listeGestionnaire.get(i).carteRecues;
		}
		return everyone;
	}
	
	
	// envoi à tous les clients
	public void envoyerAtous(Object o) {
		if(o!=null) {
			System.out.println("objet à envoyer : "+o);
			try {
				for(int i=0; i<listeGestionnaire.size();i++) {
					listeGestionnaire.get(i).oos.writeObject(o);
					listeGestionnaire.get(i).oos.flush();
					listeGestionnaire.get(i).oos.reset();
					System.out.println("Copie envoyée à "+listeGestionnaire.get(i).username);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void envoyerAtousDansPartie(Object o) {
		if(o!=null) {
			System.out.println("objet à envoyer : "+o);
			try {
				for(int i=0; i<listeGestionnaireEnJeu.size();i++) {
					listeGestionnaireEnJeu.get(i).oos.writeObject(o);
					listeGestionnaireEnJeu.get(i).oos.flush();
					listeGestionnaireEnJeu.get(i).oos.reset();
					System.out.println("Copie envoyée à "+listeGestionnaireEnJeu.get(i).username);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// genere la liste de cartes au début du tour
	public ArrayList<Carte> generateListe(){
		int nbCarte = 4+partie.getRobotParDetenteur(username).getPV();
		ArrayList<Carte> list = new ArrayList<>();
		for(int i=0; i<nbCarte; i++) {
			list.add(new Carte(username));
		}
		return list;
	}
	
	// renvoie la liste des cartes d'ordre a 
	public ArrayList<Carte> getListeCarteParOrdre(int ordre) {
		ArrayList<Carte> or = new ArrayList<>();
		for(int i=0; i<listeCartesGlobale.size(); i++) {
			if(listeCartesGlobale.get(i).getOrdre()==ordre) {
				or.add(listeCartesGlobale.get(i));
			}
		}
		return or;
	}
	// retourne la carte la plus rapide d'une liste de carte
	public Carte getCartePlusRapide(ArrayList<Carte> liste) {
		if(!liste.isEmpty()) {
			Carte fast = liste.get(0);
			for(int i=0; i<liste.size();i++) {
				if(liste.get(i).getVitesse()>fast.getVitesse()) {
					fast = liste.get(i);
				}
			}
			return fast;
		}
		else {
			return null;
		}
	}
	
	
	// deroulement d'un tour lorsque tous les joueurs sont prêts et que toutes les cartes ont étés envoyées
	public synchronized void tour() {
		try {
			System.out.println("Début du tour");
			System.out.println("Il y a "+partie.getNbJoueurs()+" joueurs");
			
			// boucle de 5 itérations pour les 5 cartes par gestionnaire --> 5 ordres
			for(int i=1; i<=5; i++) {
				System.out.println("tour --> i = "+i);
				// on obtient la première carte à jouer ici
				Carte carteAJouer = getCartePlusRapide(getListeCarteParOrdre(i));
				if(carteAJouer == null) {
					// cas si carteAJouer == null, fréquente erreur lors du getListeCarteParOrdre
					System.out.println("CarteAJouer : "+carteAJouer);
					System.out.println("On passe ce tour");
				}
				else {
				
					// on déplace un robot selon le propriétaire de la carte et la carte en question
					partie.deplaceRobot(carteAJouer.getDetenteur(), carteAJouer);
					System.out.println(carteAJouer.getDetenteur()+" joue la carte : "+carteAJouer);
					// on retire cette carte de la liste Globale après pour qu'elle ne soit pas jouée 2 fois
					listeCartesGlobale.remove(carteAJouer);
					System.out.println("Ajout de la partie via constructeur par copie ici");
					System.out.println(partie);
					
				//	Partie copie = new Partie(partie);
					envoyerAtous(partie);
				//	listePartie.getListePartie().add(copie);
				//	System.out.println("Liste partie : "+listePartie);
					Thread.sleep(500);
					
				}
		}}
		
		catch(Exception e) {
			System.out.println("Il y a un problème ailleurs");
			e.printStackTrace();
		}
		
	}
	
	public void deconnection() {	
		try {
			if(ois!=null) {
				ois.close();
			}
			if(oos!=null) {
				oos.close();
			}
			if(socket!=null) {
				socket.close();
			}
			System.err.println("Le joueur "+username+" s'est déconnecté --> prise en charge par ia");
			
		} catch (IOException e) {
			System.err.println("c'est la déconnexion qui bug");
			e.printStackTrace();
		}
	}


	
	
	
	

	
	
	
}
