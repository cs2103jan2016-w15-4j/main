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
	private Stage primaryStage;
	private Popup messageBox;
	private Label messageLabel;
	private boolean isOn;
	private FadeTransition ft;
	
	public UIMessageBox(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.messageLabel = new Label("Test label");
		this.messageLabel.setFont(Font.font("Euphemia", 15));
		this.messageLabel.getStyleClass().add("message-box-label");
		
		this.messageBox = new Popup();
		this.messageBox.getContent().addAll(this.messageLabel);
		
		this.ft = new FadeTransition(Duration.millis(6000), this.messageLabel);
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
		this.messageBox.setX(this.primaryStage.getX() + 15);
		this.messageBox.setY(this.primaryStage.getY() + this.primaryStage.getHeight() - 130);
	}
	
	public void display(){
		this.messageLabel.setPrefSize(this.primaryStage.getWidth() - 30, 50);
		this.messageBox.setX(this.primaryStage.getX() + 15);
		this.messageBox.setY(this.primaryStage.getY() + this.primaryStage.getHeight() - 130);
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