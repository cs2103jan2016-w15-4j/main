package dooyit.ui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UIMessageBox {
	private static final Font FONT_MESSAGE_BOX_LABEL = UIFont.EUPHEMIA_M;
	private static final String STYLECLASS_MESSAGE_BOX_LABEL = UIStyle.MESSAGE_BOX_LABEL;
	private static final int FADE_TIME = 6000;
	private static final int PREFHEIGHT = 50;
	private static final int PAD_X = 15;
	private static final int PAD_Y = 130;

	private Stage primaryStage;
	private Popup messageBox;
	private Label messageLabel;
	private boolean isOn;
	private FadeTransition ft;

	public UIMessageBox(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
	}
	
	private void initialize(){
		initMessageLabel();
		initMessageBox();
		initTransitions();
	}
	
	private void initMessageLabel(){
		this.messageLabel = new Label();
		this.messageLabel.setFont(FONT_MESSAGE_BOX_LABEL);
		this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL);
	}
	
	private void initMessageBox(){
		this.messageBox = new Popup();
		this.messageBox.getContent().addAll(this.messageLabel);
	}
	
	private void initTransitions(){
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
	
	private void update(double x, double y, double width, double height){
		this.messageBox.setX(x);
		this.messageBox.setY(y);
		this.messageLabel.setPrefSize(width, height);
	}
	
	public boolean isShowing(){
		return this.messageBox.isShowing();
	}
	
	public void updatePosition(){
		double x = this.primaryStage.getX() + PAD_X;
		double y = this.primaryStage.getY() + this.primaryStage.getHeight() - PAD_Y;
		double width = this.primaryStage.getWidth() - 2*PAD_X;
		update(x, y, width, PREFHEIGHT);
	}
	
	public void display(){
		double x = this.primaryStage.getX() + PAD_X;
		double y = this.primaryStage.getY() + this.primaryStage.getHeight() - PAD_Y;
		double width = this.primaryStage.getWidth() - 2*PAD_X;
		update(x, y, width, PREFHEIGHT);
		this.messageBox.show(this.primaryStage);
	}
	
	
	public void show(String msg){
		this.isOn = true;
		this.messageLabel.setText(msg);
		display();
		this.ft.playFromStart();
	}

	public void tempHide() {
		this.messageBox.hide();
	}

	public void hide() {
		this.ft.play();
	}

	public boolean isOn() {
		return this.isOn;
	}
}