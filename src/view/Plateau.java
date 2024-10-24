package view;

import java.io.InputStream;

import javafx.application.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.stage.*;
import model.*;


public class Plateau extends Group {

	private Stage window;
	private final double WIDTH  = 1200;
	private final double HEIGHT = 700;
	private Camera camera = defineCamera();
	private SmartGroup group;
	
	Class <?> clazz = this.getClass();
	
	
	
	private static double red = 10;
	private static double green = 25;
	private static double blue = 47;
	
	private double anchorY, anchorX;
	private double anchorAngleX=0;
	private double anchorAngleY=0;
	private final DoubleProperty angleX= new SimpleDoubleProperty(0);
	private final DoubleProperty angleY= new SimpleDoubleProperty(0);
	

	public Plateau(Group group, Partie partie) {
	//	this.getChildren().add(generatePlateau(partie));
		generatePlateau(group, partie);
		
	}
	
	public void ajouterRobot(Robot rob) {
		this.getChildren().add(new Robot3D(new Color(red, green, blue, 0)));
		red+=20; green+=20; blue+=20;
		
		
	}
	
	
	
	
	public PerspectiveCamera defineCamera() {
		PerspectiveCamera cam = new PerspectiveCamera();
		cam.setTranslateX(-WIDTH/2);
		cam.setTranslateY(-HEIGHT/2 - 100);
		
		cam.setTranslateZ(-700);
		cam.setNearClip(0.001);
		cam.setFarClip(1000);
		return cam;
	}
	
	public void generatePlateau(Group group, Partie partie) {
		
		
		final int debutX=-partie.getMap().getTaille()/2 - 275; int localisationX=debutX;
		int incremX=550;
		final int debutZ=-partie.getMap().getTaille()/2; int localisationZ=debutZ; 
		int incremZ=550;
		
		for(int a=0; a<partie.getMap().getListeElement().size(); a++) {
			Plaque pl = new Plaque(500, 100, 500, Color.WHITE);
			if(partie.getMap().getListeElement().get(a).getNature()==NatureElement.Drapeau) {
				pl.setMaterial(new PhongMaterial(Color.GREEN));
				pl.setTranslateX(partie.getMap().getListeElement().get(a).getCoordX()*550);
				pl.setTranslateZ(partie.getMap().getListeElement().get(a).getCoordY()*550);
				Drapeau3D flag = new Drapeau3D(); 
				flag.setTranslateX(pl.getTranslateX()+100); flag.setTranslateZ(pl.getTranslateZ()+100); flag.setTranslateY(pl.getTranslateY()-100);
				group.getChildren().addAll(pl, flag);
			}
			else if(partie.getMap().getListeElement().get(a).getNature()==NatureElement.Trou) {
				pl.setMaterial(new PhongMaterial(Color.BLACK));
			//	pl.setTranslateX(partie.getMap().getListeElement().get(a).getCoordX()*550);
			//	pl.setTranslateZ(partie.getMap().getListeElement().get(a).getCoordY()*550);
			//	group.getChildren().addAll(pl);
			}
			else if(partie.getMap().getListeElement().get(a).getNature()==NatureElement.Tapis) {
				PhongMaterial mat = new PhongMaterial();
				InputStream input; Image img; 
				if(((TapisRoulant)partie.getMap().getListeElement().get(a)).getOrientation()==Direction.Haut) {
					 input = clazz.getResourceAsStream("/imageJeu/Tapis Haut.png");
					 img = new Image(input);
					 mat.setDiffuseMap(img);
				}
				else if(((TapisRoulant)partie.getMap().getListeElement().get(a)).getOrientation()==Direction.Bas) {
					input = clazz.getResourceAsStream("/imageJeu/Tapis Bas.png");
					 img = new Image(input);
					 mat.setDiffuseMap(img);
				}
				else if(((TapisRoulant)partie.getMap().getListeElement().get(a)).getOrientation()==Direction.Droite) {
					input = clazz.getResourceAsStream("/imageJeu/Tapis Droite.png");
				}
				else {
					input = clazz.getResourceAsStream("/imageJeu/Tapis Gauche.png");
					 img = new Image(input);
					 mat.setDiffuseMap(img);
				}
				pl.setMaterial(mat);
				pl.setTranslateX(partie.getMap().getListeElement().get(a).getCoordX()*550);
				pl.setTranslateZ(partie.getMap().getListeElement().get(a).getCoordY()*550);
				group.getChildren().addAll(pl);
		}
			else if(partie.getMap().getListeElement().get(a).getNature()==NatureElement.DrapeauCapture) {
				pl.setMaterial(new PhongMaterial(Color.RED));
				pl.setTranslateX(partie.getMap().getListeElement().get(a).getCoordX()*550);
				pl.setTranslateZ(partie.getMap().getListeElement().get(a).getCoordY()*550);
				double r = partie.getRobotParDetenteur((((Drapeau) partie.getMap().getListeElement().get(a)).getNomProprietaire())).getR();
				double g = partie.getRobotParDetenteur((((Drapeau) partie.getMap().getListeElement().get(a)).getNomProprietaire())).getG();
				double b = partie.getRobotParDetenteur((((Drapeau) partie.getMap().getListeElement().get(a)).getNomProprietaire())).getB();
				Drapeau3D flag = new Drapeau3D(new Color(r, g, b, 1)); 
				flag.setTranslateX(pl.getTranslateX()+100); flag.setTranslateZ(pl.getTranslateZ()+100); flag.setTranslateY(pl.getTranslateY()-100);
				group.getChildren().addAll(pl, flag);
			}
			else {
			pl.setTranslateX(partie.getMap().getListeElement().get(a).getCoordX()*550);
			pl.setTranslateZ(partie.getMap().getListeElement().get(a).getCoordY()*550);
			group.getChildren().addAll(pl); 
			}
		}
		
		for(int a = 0; a<partie.getListeRobot().size(); a++) {
			Robot3D rob = new Robot3D(new Color(partie.getListeRobot().get(a).getR(), partie.getListeRobot().get(a).getG(), partie.getListeRobot().get(a).getB(), 1));
			rob.setTranslateY(rob.getTranslateY()-100);
			rob.setTranslateX(partie.getListeRobot().get(a).getCoordX()*550);
			rob.setTranslateZ(partie.getListeRobot().get(a).getCoordY()*550);
			if(partie.getListeRobot().get(a).getOrientation()==Direction.Chute) { rob.tournerRobot(180, Rotate.X_AXIS); }
			else if(partie.getListeRobot().get(a).getOrientation()==Direction.Sol) { rob.tournerRobot(90, Rotate.X_AXIS); }
			else if(partie.getListeRobot().get(a).getOrientation()==Direction.Droite) { rob.tournerRobot(270, Rotate.Y_AXIS); }
			else if(partie.getListeRobot().get(a).getOrientation()==Direction.Gauche) { rob.tournerRobot(90, Rotate.Y_AXIS); }
			else if(partie.getListeRobot().get(a).getOrientation()==Direction.Bas) { rob.tournerRobot(180, Rotate.Y_AXIS); }
			group.getChildren().add(rob);
		}
		
		
				
		
	}}
	
/*	public void generatePlateau(Group group) {
		int ecartX =0;
		int deltaX = 0;
		int incremX = 550;
		
		int ecartZ = 0;
		int deltaZ=0;
		int incremZ=550;
		
		for(int car =0; car<4; car++) {
		
			if(car==0) {
				incremX=-550;
				incremZ=550;
				ecartX=-275;
				ecartZ=275;
			}
			if(car==1) {
				incremX=-550;
				incremZ=-550;
				ecartX=-275;
				ecartZ=-275;
			}
			if(car==2) {
				incremX=550;
				incremZ=-550;
				ecartX=275;
				ecartZ=-275;
			}
			if(car==3) {
				incremX=550;
				incremZ=550;
				ecartX=275;
				ecartZ=275;
			}
			
			
			for(int a=0; a<5; a++) {
				
		
		
				for(int i =0; i<5; i++) {
					Plaque pl = new Plaque(500, 100, 500, Color.WHITE);
					pl.setTranslateX(deltaX+ecartX);
					pl.setTranslateZ(deltaZ+ecartZ);
					deltaX+=incremX;
					/*	Plaque bordure = new Plaque(100, 200, 500, Color.BLACK);
					bordure.setTranslateX(ecart);
					ecart+=300;   
					group.getChildren().addAll(pl); 
				}
			deltaX=0;
			deltaZ+=incremZ;
			}
			
			deltaZ=0;
		}
		 
	}   */
	
	
	
/*	public void start(Stage arg0) throws Exception {
		window = arg0;
		group=new SmartGroup();
		
	//	group.getChildren().add(camera);
		Plaque plaque = new Plaque(500, 100, 500);
		Plaque plaque2 = new Plaque(500, 100, 500);
		plaque2.translateXProperty().set(600);
		Plaque plaque3 = new Plaque(100, 100, 500);
		plaque3.translateXProperty().set(300);
		plaque3.setMaterial(new PhongMaterial(Color.BLACK));
	//	Plaque[] liste = {plaque, new Plaque(), new Plaque()};
		Partie parto = new Partie(10);
		parto.getListeRobot().add(new Robot("John", Color.AZURE));
		parto.getListeRobot().add(new Robot("Barto", Color.VIOLET));parto.getListeRobot().add(new Robot("Luffy", Color.RED));
		
		group.getChildren().addAll(plaque, plaque2, plaque3);  
		generatePlateau(group, parto);
	//	addRobot(550, -200, 550);
		
		GroupRobot rgb = new GroupRobot();
	/*	Robot3D roby = new Robot3D(Color.DARKGOLDENROD);
		roby.deplacerRobot3D(2000, -200, 0);
		rgb.ajouterRobot(roby);
		rgb.setTranslateY(-100);
	//	rgb.getListeRobot().add(roby);
	//	Group gr = new Group();
	//	gr.getChildren().addAll(roby);

		group.getChildren().addAll(rgb);   
		
		
	//	Box box = new Box(500, 200, 500);
	//	box.setMaterial(new PhongMaterial(Color.BLUE));
	//	group.getChildren().add(box);
		
		Scene scene = new Scene(group, WIDTH, HEIGHT, true);
		scene.setCamera(camera);
		initMouseControl(group, scene);
		
		
		window.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			switch(event.getCode()) {
				case D :
					group.RotateByX(10);
					System.out.println("key pressed");
					break;
				case Q :
					group.RotateByX(-10);
					break;
				case Z :
					group.translateZProperty().set((group.getTranslateZ())-100);
					break;
				case S :
					group.translateZProperty().set(group.getTranslateZ()+100);
					break;
				case A :
					group.RotateByY(10);
					break;
				case E :
					group.RotateByY(-10);
					break;
			}
		}
				);
		
		
		scene.setFill(Color.CORAL);
		
		window.setScene(scene);
		window.setTitle("Plateau de jeu");
		window.show();
		
		
		
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
	
	public void addRobot(double x, double y, double z) {
		Group robotGroup = new Group();
		Cylinder tronc = new Cylinder(100, 300);
		Box head = new Box(300, 150, 200);
		Box armRight = new Box(50, 190, 50);
		Box armLeft = new Box(50, 190, 50);
		tronc.setTranslateY(y); tronc.setTranslateX(x); tronc.setTranslateZ(z);
		head.setTranslateY(y-150); head.setTranslateX(x); head.setTranslateZ(z);
		armRight.setTranslateX(x+150); armLeft.setTranslateX(x-150);
		armRight.setTranslateY(y); armLeft.setTranslateY(y); armRight.setTranslateZ(z); armLeft.setTranslateZ(z);
		armRight.setMaterial(new PhongMaterial(Color.BLUE)); armLeft.setMaterial(new PhongMaterial(Color.BLUE));
		tronc.setMaterial(new PhongMaterial(Color.BLACK));
		head.setMaterial(new PhongMaterial(Color.DARKRED));
		robotGroup.getChildren().addAll(tronc, head, armLeft, armRight);
		group.getChildren().add(robotGroup);
		robotGroup.setTranslateX(robotGroup.getTranslateX()+2000);
		
	}
	
	public static void main(String[]args) {
		Application.launch(args);
	}  

	
	
	
}  */
