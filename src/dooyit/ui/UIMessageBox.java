package dooyit.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.stage.Stage;

public class UIMessageBox {
	private Stage primaryStage;
	private Popup messageBox;
	private Label messageLabel;
	private boolean isOn;
	
	public UIMessageBox(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.messageLabel = new Label("Test label");
		this.messageLabel.setFont(Font.font("Euphemia", 15));
		this.messageLabel.getStyleClass().add("message-box-label");
		
		this.messageBox = new Popup();
		this.messageBox.getContent().addAll(this.messageLabel);
	}
	
	public boolean isShowing(){
		return this.messageBox.isShowing();
	}
	
	public void show(String msg){
		this.isOn = true;
		this.messageLabel.setText(msg);
		display();
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
	
	public void tempHide(){
		this.messageBox.hide();
	}
	
	public void hide(){
		this.isOn = false;
		this.messageBox.hide();
	}
	
	public boolean isOn(){
		return this.isOn;
	}
}