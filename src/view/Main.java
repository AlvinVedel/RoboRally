package view;

import javafx.application.Application;
import model.Partie;

public class Main {
	
	private static Partie partie = new Partie(10);

	public static void main(String[]args) throws Exception {
		/*	try {
			gr.setPartie(new Partie(10));
		} catch (Exception e) {
			e.printStackTrace();
		}  */
	//	Partie partie = new Partie(10);
		System.out.println(partie.getMap());
		
		InterfaceGraphique.setPartie(partie);;
		
		Application.launch(InterfaceGraphique.class, args);
	}
	
}
