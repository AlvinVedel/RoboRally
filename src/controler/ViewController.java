package controler;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Carte;
import model.Partie;
import view.InterfaceGraphique;

public class ViewController {
	
	
	
	
	// binding attributes et leurs méthode
	// cc --> vc
	private final ObjectProperty<Partie> partie = new SimpleObjectProperty<Partie>(new Partie(10));
	public final Partie getPartie() {return partie.get();}
	public final void setPartie(Partie value) {partie.set(value);}
	public final ObjectProperty<Partie> partieProperty(){return partie;}
	
	// cc--> vc
	private final ObjectProperty<ListeCarte> listeCarte = new SimpleObjectProperty<ListeCarte>(new ListeCarte(new ArrayList<Carte>()));
	public final void setListeCarte(ListeCarte value) { listeCarte.set(value);}
	public final ListeCarte getListeCarte() { return listeCarte.get();}
	public final ObjectProperty<ListeCarte> listeCarteProperty(){return listeCarte; }
	
	// cc --> vc
	private final ObjectProperty<EtatPartie> etat = new SimpleObjectProperty<EtatPartie>(new EtatPartie(Etat.demandeNom));
	public final void setEtat(Etat value) {	etat.get().setEtat(value); }
	public final EtatPartie getEtat() { return etat.get(); }
	public final ObjectProperty<EtatPartie> etatProperty(){ return etat; }
	
	// vc--> cc
	private final ObjectProperty<ArrayList<Carte>> listeCartesChoisies = new SimpleObjectProperty<ArrayList<Carte>>(new ArrayList<Carte>()); 
	public final ArrayList<Carte> getListeCarteChoisies() { return listeCartesChoisies.get(); }
	public final void setListeCarteChoisies(ArrayList<Carte> value) { listeCartesChoisies.set(value); }
	public final ObjectProperty<ArrayList<Carte>> listeCarteChoisiesProperty(){ return listeCartesChoisies; }
	
	// vc --> cc
	private final BooleanProperty pret = new SimpleBooleanProperty(false);
	public final void setPret(boolean value) { pret.set(value); }
	public final boolean getPret() { return pret.get(); }
	public final BooleanProperty pretProperty() { return pret; }
	
	// vc --> cc
	private final StringProperty msgEnvoye = new SimpleStringProperty("");
	public final void setMsgEnvoye(String value) { msgEnvoye.set(value); }
	public final String getMsgEnvoye() { return msgEnvoye.get(); }
	public final StringProperty msgEnvoyeProperty() { return msgEnvoye; }
	
	// vc --> cc
	private final StringProperty username = new SimpleStringProperty("");
	public final void setUsername(String value) { username.set(value); }
	public final String getUsername() { return username.get(); }
	public final StringProperty usernameProperty() { return username; }
	
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
	
	
	
	private int ordre = 1;
	
	
	
	
	public static void lancementIG() {
		InterfaceGraphique.launch(InterfaceGraphique.class);
	}
	
	
	public ViewController() {
		
		new Thread(new Runnable() {
			public void run() {
				
				//1ère étape : lancement IG pour init les boutons
				lancementIG();
			}}).start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// on ajoute les change Listener
		partie.addListener(new ChangeListener<Partie>() {
			public void changed(ObservableValue<? extends Partie> arg0, Partie arg1, Partie arg2) {
				System.out.println("Partie a changé (vc) : "+partie.get());
				InterfaceGraphique.inter.redessinePlateau(partie.get());
				InterfaceGraphique.inter.setScores(partie.get());
			}
		});
		listeCarte.addListener(new ChangeListener<ListeCarte>() {
			public void changed(ObservableValue<? extends ListeCarte> arg0, ListeCarte arg1, ListeCarte arg2) {
				System.out.println("La liste de carte a été mise à jour : "+getListeCarte().getListeCarte());
				InterfaceGraphique.inter.setDeck(listeCarte.get().getListeCarte());
				InterfaceGraphique.inter.rafraichirBouton();
			}
		});
		etat.addListener(new ChangeListener<EtatPartie>() {
			@Override
			public void changed(ObservableValue<? extends EtatPartie> arg0, EtatPartie arg1, EtatPartie arg2) {
				if(etat.get().getEtat().equals(Etat.ecranDemarrage)) {
					System.out.println("etat = ecranDemarrage");
					InterfaceGraphique.inter.afficherEcranDemarrage();;
				}
				else if(etat.get().getEtat().equals(Etat.enJeu)) {
					System.err.println("Etat = enJeu");
					InterfaceGraphique.inter.afficherEcranJeu(partie.get(), listeCarte.get().getListeCarte());
				}
				else if(etat.get().getEtat().equals(Etat.ecranFin)) {
					System.err.println("Etat = fin");
					InterfaceGraphique.inter.afficherEcranFin();
				}
				else if(etat.get().getEtat().equals(Etat.perduMaisEnCours)) {
					System.err.println("Etat = mort");
					InterfaceGraphique.inter.afficherEcranMort();
				}
				else if(etat.get().getEtat().equals(Etat.spectateur)) {
					
				}
				else if(etat.get().getEtat().equals(Etat.demandeNom)) {
					System.out.println("Etat = demande nom");
					InterfaceGraphique.inter.afficherDemandeNom();
				}
				else if(etat.get().getEtat().equals(Etat.credits)) {
					System.err.println("Etat = credits");
					InterfaceGraphique.inter.afficherCredits();
				}
			}
		});
		msgRecu.addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(msgRecu.get()!=null || msgRecu.get()!="") {
					System.out.println("msg recu par vc");
					InterfaceGraphique.inter.recevoirMessage(msgRecu.get());
				}
			}
		});
		retourListeComplete.addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					setListeComplete(false);
					ordre=1; System.out.println("j'ai bien remis ordre à "+ordre);
					setListeCarteChoisies(new ArrayList<Carte>());
					//setRetourListeComplete(false);
			}
			
		});
		
		
	//	new Thread(new Runnable() {
		//	public void run() {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				InterfaceGraphique.inter.getBoutonValider().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(InterfaceGraphique.inter.getDemandeDeNom().getText()!=null) {
					username.setValue(InterfaceGraphique.inter.getDemandeDeNom().getText());
					System.out.println("user name coté vc : "+username.get());
					
				}
				InterfaceGraphique.inter.viderDemandeDeNom();
			}
		});
				// on ajoute les EventHandler des boutons initialisés en étape 1 avec petit délai de 1sec pour coordination (étape 2)
				InterfaceGraphique.inter.getBoutonPret().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonPret()) {
							boolean rd = !pret.get();
							setPret(rd);
							InterfaceGraphique.inter.setCouleurBoutonPret(rd);
							System.out.println("joueur : "+pret.get());
						}}});
				InterfaceGraphique.inter.getBoutonEnvoyer().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonEnvoyer()) {
							setMsgEnvoye(username.get()+" : "+InterfaceGraphique.inter.getEntreeChat().getText());
							InterfaceGraphique.inter.viderEntreeChat();
							System.out.println("Message a envoyer (vc) : "+msgEnvoye.get());
						}}});
				InterfaceGraphique.inter.getBoutonCarte1().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte1()&& listeCarte.get().getListeCarte().size()>=1 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(0); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte1());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(0));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte2().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte2()&& listeCarte.get().getListeCarte().size()>=2 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(1); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte2());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(1));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte3().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte3()&& listeCarte.get().getListeCarte().size()>=3 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(2); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte3());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(2));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte4().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte4()&& listeCarte.get().getListeCarte().size()>=4 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(3); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte4());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(3));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte5().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte5()&& listeCarte.get().getListeCarte().size()>=5 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(4); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte5());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(4));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte6().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte6()&& listeCarte.get().getListeCarte().size()>=6 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(5); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte6());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(5));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte7().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte7()&& listeCarte.get().getListeCarte().size()>=7 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(6); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte7());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(6));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte8().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte8()&& listeCarte.get().getListeCarte().size()>=8 && listeCartesChoisies.get().size()<5) {
							Carte card = listeCarte.get().getListeCarte().get(7); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte8());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(7));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonCarte9().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonCarte9() && listeCarte.get().getListeCarte().size()>=9 && listeCartesChoisies.get().size()<5) {
						//	listeCartesChoisies.get().add(listeCarte.get().getListeCarte().get(8));
							Carte card = listeCarte.get().getListeCarte().get(8); card.setOrdre(ordre); ordre++;
							listeCartesChoisies.get().add(card);
							InterfaceGraphique.inter.assombrirBoutonCarteChoisie(InterfaceGraphique.inter.getBoutonCarte9());
							System.out.println("J'ajoute une carte"+listeCarte.get().getListeCarte().get(8));
							if(getListeCarteChoisies().size()==5) {
								setListeComplete(true);
							}
						}
						else {
							System.out.println("bouton cliqué mais une des conditions non satisfaite");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonSpectate().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonSpectate()) {
							setDemandeClient(new DemandeClient("spectate"));
							System.out.println("le client demande à spectate");
						}
					}
				});
				InterfaceGraphique.inter.getBoutonQuitter().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						if(arg0.getSource()==InterfaceGraphique.inter.getBoutonQuitter()) {
							setDemandeClient(new DemandeClient("quitter"));
							System.out.println("le client demande à quitter");
						}
					}
				});
				// les 2 boutons nécessaires à l'écran de démarrage sont initialisés ainsi que leurs eventHandler, on peut les afficher
				
				// 3ème étape : affichage de l'écran de démarrage
		//		InterfaceGraphique.inter.afficherEcranDemarrage();
			//	System.out.println("j'ai affiché l'écran de démarrage");
				
				
				
			/*	InterfaceGraphique.inter.getEcranDemarrage().retirerBoutons();
				InterfaceGraphique.inter.getEcranDemarrage().getBoutonEnvoyer().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						System.out.println("handle");
						if(arg0.getSource()==InterfaceGraphique.inter.getEcranDemarrage().getBoutonEnvoyer()) {
							System.out.println("BoutonEnvoyer fonctionne");
						}
					}
				});
				InterfaceGraphique.inter.getEcranDemarrage().getBoutonPret().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						System.out.println("handle");
						if(arg0.getSource()==InterfaceGraphique.inter.getEcranDemarrage().getBoutonPret()) {
							System.out.println("bouton pret fonctionne");
						}
					}
				});
				InterfaceGraphique.inter.getEcranDemarrage().ajouterBoutons(); */
			}  
	//	}).start();
		
		
		
		
	/*	InterfaceGraphique.inter.getBoutonbt().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				System.out.println("Dans le handle");
				if(arg0.getSource()==InterfaceGraphique.inter.getBoutonbt()) {
					System.out.println("dans la cond");
				}
				
			}
		});
		InterfaceGraphique.inter.ajouterBouton();   */
		
		
	/*	EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
				
			public void handle(MouseEvent arg0) {
				if(arg0.getSource()==InterfaceGraphique.inter.getEcranDemarrage().getBoutonPret()) {
					boolean ready = !pret.get();
					System.out.println("prêt : "+ready);
					setPret(ready);
				}
				if(arg0.getSource()==InterfaceGraphique.inter.getEcranDemarrage().getBoutonEnvoyer()) {
					String txt = InterfaceGraphique.inter.getEcranDemarrage().getEntreeChat().getText();
					System.out.println("texte à envoyer : "+txt);
					setMsgEnvoye(txt);
					InterfaceGraphique.inter.viderEntreeChatD();
				}
				
			};
		};
		
		EventHandler<ActionEvent> handler2 = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				if(arg0.getSource()==InterfaceGraphique.inter.getEcranDemarrage().getBoutonPret()) {
					boolean ready = !pret.get();
					System.out.println("prêt : "+ready);
					setPret(ready);
				}
				if(arg0.getSource()==InterfaceGraphique.inter.getEcranDemarrage().getBoutonEnvoyer()) {
					String txt = InterfaceGraphique.inter.getEcranDemarrage().getEntreeChat().getText();
					System.out.println("texte à envoyer : "+txt);
					setMsgEnvoye(txt);
					InterfaceGraphique.inter.viderEntreeChatD();
				}
				else {
					System.out.println("en dehors des if --> else");
				}
			}
			
		};
		InterfaceGraphique.inter.getEcranDemarrage().getBoutonPret().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				System.out.println("Dans le handle");
				if(arg0.getSource()==InterfaceGraphique.inter.getEcranDemarrage().getBoutonPret()) {
					System.out.println("cond vérifiée");
				}	
			}
		});
		
		
		
		
		
	/*	InterfaceGraphique.inter.getEcranDemarrage().getBoutonPret().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				boolean ready = !pret.get();
				System.out.println("prêt : "+ready);
				setPret(ready);
			}
		});
		InterfaceGraphique.inter.getEcranDemarrage().getBoutonEnvoyer().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				String txt = InterfaceGraphique.inter.getEcranDemarrage().getEntreeChat().getText();
				System.out.println("texte à envoyer : "+txt);
				setMsgEnvoye(txt);
				InterfaceGraphique.inter.viderEntreeChatD();
			}
		});   */
		
		
		
		
		
//	}
	
	
	
	
	
	
	
	
	public static void main(String[]args) {
		ViewController vc = new ViewController();
	}
	
	
}
