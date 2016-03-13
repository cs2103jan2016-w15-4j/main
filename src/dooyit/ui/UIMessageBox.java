package dooyit.ui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UIMessageBox {
	private static final Font FONT_MESSAGE_BOX_LABEL = Font.font("Euphemia", 15);
	private static final String STYLECLASS_MESSAGE_BOX_LABEL = "message-box-label";
	
	private static final int FADE_TIME = 6000;
	
	private static final int PREFHEIGHT = 50;
	private static final int PAD_X = 15;
	private static final int PAD_Y = 130;
	
	private Stage primaryStage;
	private Popup messageBox;
	private Label messageLabel;
	private boolean isOn;
	private FadeTransition ft;
	
	public UIMessageBox(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.messageLabel = new Label("Test label");
		this.messageLabel.setFont(FONT_MESSAGE_BOX_LABEL);
		this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL);
		
		this.messageBox = new Popup();
		this.messageBox.getContent().addAll(this.messageLabel);
		
		this.ft = new FadeTransition(Duration.millis(FADE_TIME), this.messageLabel);
		this.ft.setFromValue(1.0);
        this.ft.setToValue(0.0);
        this.ft.setCycleCount(1);
        this.ft.setAutoReverse(false);
        this.ft.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                messageBox.hide();
                isOn = false;
            }
        });
	}
	
	public boolean isShowing(){
		return this.messageBox.isShowing();
	}
	
	public void updatePosition(){
		this.messageBox.setX(this.primaryStage.getX() + PAD_X);
		this.messageBox.setY(this.primaryStage.getY() + this.primaryStage.getHeight() - PAD_Y);
	}
	
	public void display(){
		this.messageLabel.setPrefSize(this.primaryStage.getWidth() - 2*PAD_X, PREFHEIGHT);
		this.messageBox.setX(this.primaryStage.getX() + PAD_X);
		this.messageBox.setY(this.primaryStage.getY() + this.primaryStage.getHeight() - PAD_Y);
		this.messageBox.show(this.primaryStage);
	}
	
	public void show(String msg){
		this.isOn = true;
		this.messageLabel.setText(msg);
		display();
		this.ft.playFromStart();
	}
	
	public void tempHide(){
		this.messageBox.hide();
	}
	
	public void hide(){
		this.ft.play();
	}
	
	public boolean isOn(){
		return this.isOn;
	}
}