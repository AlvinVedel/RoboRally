package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CaseBasique extends ElementMap {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7526509315393105377L;

	public CaseBasique(int coX, int coY) {
		super(coX, coY);
	}
	public CaseBasique(int coX, int coY, NatureElement nat) {
		super(coX, coY, nat);
	}
	

}
