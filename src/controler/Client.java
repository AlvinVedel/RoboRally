package controler;

import java.io.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import interfaceTestServeur.*;
import javafx.application.Application;
import model.Carte;
import model.Partie;
import view.InterfaceGraphique;


public class Client implements ActionListener {
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String username;
	private Socket socket;
	
	private ArrayList<Carte> listeCarte = new ArrayList<>();
	private ArrayList<Carte> listeCartesChoisies = new ArrayList<>(); 
	private boolean aJour = true;
	
	private static ArrayList<Partie> listePartie = new ArrayList<>();
	private Partie partie;
	JButton print = new JButton("Liste Carte");		
	
	public Client(Socket socket) {
		try {
			this.socket=socket;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Client(Socket socket, String username) {
		try {
			this.socket=socket;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			setUsername(username);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void sendObj(Object o) {
		try {
		if(o!=null) {
			oos.writeObject(o);
			oos.flush();
			oos.reset();
			System.out.println("J'ai envoyé l'objet");
		}
		} catch(Exception e) {
			deconnection();
		}
	}
	
	
	public void envoyerEtRecevoir() {
		new Thread(new Runnable() {
			public void run() {
				Object o;
				while(socket.isConnected()) {
					try {
						o = ois.readObject();
					//	System.out.println(o);
						
						if(o instanceof ListeCarte) {
							listeCarte =  ((ListeCarte) o).getListeCarte();
							System.out.println("J'ai bien reçu la liste de carte --> "+listeCarte);
							aJour=false;
							while(fen==null) {
								
							}
							fen.getPanelBoutons().setListeCarte(listeCarte);
						}
						else if(o instanceof Partie) {
							listePartie.add((Partie) o);
							if(listePartie.size()==listePartie.get(0).getNbJoueurs()*5) {
								System.out.println("Liste partie complète : "+listePartie);
								updatePartie(listePartie);
								System.out.println("Partie updaté");
							}
							else {
								System.out.println("Partie recu : "+o);
							}
							
						//	System.out.println("J'ai modifié la partie --> "+partie);
						}  
						else if(o instanceof ListePartie) {
							updatePartie(((ListePartie) o).getListePartie());
							System.out.println("Fin de l'update");
						}
						else if(o instanceof String) {
							System.out.println(o);
						}
						else {
							System.out.println("Je ne reconnais pas o comme instance de ListeCarte ni partie");
							System.out.println("o est instance de "+o.getClass());
						}	
				} catch(IOException e) {
					deconnection();
					System.err.println("Le problème est ici : "); e.printStackTrace();
					break;
				}
				catch(Exception e) {
					e.printStackTrace();
					break;
				}
				}
		}
	}).start();
		new Thread(new Runnable() {
			public void run() {
				while(socket.isConnected()) {
					try {
						if(listeCartesChoisies.size()==5) {
							oos.writeObject(new ListeCarte(listeCartesChoisies));
							oos.flush();
							System.out.println("J'ai envoyé la liste : "+listeCartesChoisies);
							listeCartesChoisies.removeAll(listeCartesChoisies);
							System.out.println("La normalement j'ai vidé la liste : "+listeCartesChoisies);
						}
						else {
							
							Thread.sleep(1000);
						}
			 		}
				 catch(IOException e) {
					deconnection();
					System.err.println("Le problème est ici : "); e.printStackTrace();
					break;
				}
				catch(Exception e) {
					e.printStackTrace();
					break;
				}
				
				
			}}
		}).start();	
		
	}
	
	public boolean cartesDifferentes() {
		return true;
	}
	
	public void updatePartie(ArrayList<Partie> liste) {
		for(int i=0; i<liste.size(); i++) {
			try {
				partie = liste.get(i);
			//	Thread.sleep(2000);
				System.out.println("J'ai mis à jour la partie + délai de 2sec");
				System.out.println(partie);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void deconnection() {	
		System.err.println("Vous avez été déconnecté");
		
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ArrayList<Carte> getListeCarte() {
		return listeCarte;
	}
	public void setListeCarte(ArrayList<Carte> listeCarte) {
		this.listeCarte = listeCarte;
	}
	public ArrayList<Carte> getListeCartesChoisies() {
		return listeCartesChoisies;
	}
	public void setListeCartesChoisies(ArrayList<Carte> listeCartesChoisies) {
		this.listeCartesChoisies = listeCartesChoisies;
	}
	
	
	
	
	public static void main(String[]args) throws UnknownHostException, IOException, InterruptedException {
		Socket socket = new Socket(InetAddress.getLocalHost(), 1234);
		Scanner sc = new Scanner(System.in);
		System.out.println("Nom : ");
		String username = sc.next();
		Client client = new Client(socket, username);
		client.sendObj(username);
		client.envoyerEtRecevoir();
	//	client.recevoirObject();
	//	client.envoyerListeCarte();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Application.launch(InterfaceGraphique.class, args);
		
		
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==print) {
			System.out.println(listeCarte);
			try {
				oos.writeBoolean(true);
				oos.flush();
				oos.reset();
				System.out.println("boolean envoyé");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public ArrayList<Partie> getListePartie() {
		return listePartie;
	}
	
	


	
	
}
