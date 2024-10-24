package application;

import controler.ClientControler;
import controler.ViewControler;

public class LancementClient {

	public static void main(String[]args) {
		ClientControler cc = new ClientControler();
		ViewControler vc = new ViewControler(args);
		cc.setViewControler(vc);
		
	}
	
}
