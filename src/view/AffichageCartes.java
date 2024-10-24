package view;

import java.util.ArrayList;

import javafx.scene.layout.FlowPane;
import model.Carte;

public class AffichageCartes extends FlowPane {

	private ArrayList<Carte> listeCarte = new ArrayList<>();
	private CarteAJouer carte1 = genereCarteAJouer(0);
	private CarteAJouer carte2 = genereCarteAJouer(1);
	private CarteAJouer carte3 = genereCarteAJouer(2);
	private CarteAJouer carte4 = genereCarteAJouer(3);
	private CarteAJouer carte5 = genereCarteAJouer(4);
	private CarteAJouer carte6 = genereCarteAJouer(5);
	private CarteAJouer carte7 = genereCarteAJouer(6);
	private CarteAJouer carte8 = genereCarteAJouer(7);
	private CarteAJouer carte9 = genereCarteAJouer(8);
	
	
	public AffichageCartes(ArrayList<Carte> liste) {
		listeCarte = liste;
		
		carte1 = genereCarteAJouer(0); if(carte1!=null) { this.getChildren().add(carte1); }
		carte2 = genereCarteAJouer(1); if(carte2!=null) { this.getChildren().add(carte2); }
		carte3 = genereCarteAJouer(2); if(carte3!=null) { this.getChildren().add(carte3); }
		carte4 = genereCarteAJouer(3); if(carte4!=null) { this.getChildren().add(carte4); }
		carte5 = genereCarteAJouer(4); if(carte5!=null) { this.getChildren().add(carte5); }
		carte6 = genereCarteAJouer(5); if(carte6!=null) { this.getChildren().add(carte6); }
		carte7 = genereCarteAJouer(6); if(carte7!=null) { this.getChildren().add(carte7); }
		carte8 = genereCarteAJouer(7); if(carte8!=null) { this.getChildren().add(carte8); }
		carte9 = genereCarteAJouer(8); if(carte9!=null) { this.getChildren().add(carte9); }
	//	this.getChildren().addAll(carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9);
	}
	
	public AffichageCartes() {
		this.getChildren().addAll(carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9);
	}
	
	public CarteAJouer genereCarteAJouer(int ordre) {
		CarteAJouer carteAJ;
		if(ordre<listeCarte.size() && listeCarte.get(ordre)!= null) {
			carteAJ = new CarteAJouer(listeCarte.get(ordre));
		}
		else {
			carteAJ = null;
		}
		return carteAJ; 
	}
	
	
	
	public void setListeCarte(ArrayList<Carte> listeCarte) {
		this.listeCarte = listeCarte;
	}
	public ArrayList<Carte> getListeCarte(){
		return listeCarte;
	}
	
	
	
}
