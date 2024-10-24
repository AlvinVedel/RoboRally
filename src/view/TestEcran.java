package view;

import javafx.application.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.*;
import model.Carte;
import model.Partie;

public class TestEcran extends Application {

	Stage window;
//	Button pretButton;
	EcranDemarrage ecr;
	Camera cam;
	
	private double anchorY, anchorX;
	private double anchorAngleX=0;
	private double anchorAngleY=0;
	private final DoubleProperty angleX= new SimpleDoubleProperty(0);
	private final DoubleProperty angleY= new SimpleDoubleProperty(0);
	
	
	public PerspectiveCamera defineCamera() {
		PerspectiveCamera cam = new PerspectiveCamera();
		cam.setTranslateX(-window.getWidth()/2);
		cam.setTranslateY(-window.getHeight()/2 - 100);
		
		cam.setTranslateZ(-700);
		cam.setNearClip(0.001);
		cam.setFarClip(1000);
		return cam;
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
	
	public void start(Stage arg0) throws Exception {
		window = arg0;
		window.setHeight(720);
		window.setWidth(1080);
		ecr = new EcranDemarrage(window.getWidth(), window.getHeight());
		
	/*	pretButton = new Button("Prêt");
		pretButton.setPrefSize(200, 120);
		AnchorPane.setTopAnchor(pretButton, (double) (window.getHeight()/2) - 60 );
		
		AnchorPane.setRightAnchor(pretButton, (window.getWidth()/2) - 100);
		ecr.getChildren().addAll(pretButton);  */
		
		
		ecr.setStyle("-fx-background-color: deepskyblue");
		SmartGroup group = new SmartGroup();
		Partie partie = new Partie(10);
		System.out.println(partie.getMap());
		Plateau plateau = new Plateau(group, partie);
	//	group.getChildren().add(new CarteAJouer(new Carte()));
		
	//	group.getChildren().add(plateau);
		
		Scene scene = new Scene(group, window.getWidth(), window.getHeight(), true);
		scene.setFill(Color.RED);
		initMouseControl(group, scene);
		cam = defineCamera();
		scene.setCamera(cam);
		window.setScene(scene);
		new Thread(new Runnable() {
			public void run() {
				double height = window.getHeight();
				double width = window.getWidth();
				
				while(true) {
					while(window.getHeight()==height && window.getWidth()==width) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					height=window.getHeight();
					width=window.getWidth();
					ecr.setPosition(window);
				
			}
			}
		}).start();
		
		
		
		
		window.setTitle("Ecran demarrage");
		window.show();
		
		
	}
	
	
	
	
	
	
	public static void main(String[]args) {
		launch(args);
	}
	
	

}
