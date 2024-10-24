package controler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class DemandeClient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2666989430322433150L;
	private String demande;
	
	public DemandeClient(String demande) {
		this.demande=demande;
	}
	
	public void setDemande(String dem) {
		this.demande=dem;
	}
	public String getDemande() {
		return demande;
	}
	
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.demande=(String) ois.readObject();	
	}
	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException{
		oos.writeObject(demande);
	}
	
	
}
