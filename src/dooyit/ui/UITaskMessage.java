package dooyit.ui;

import javafx.scene.control.Label;

public class UITaskMessage {

	private static final String STYLESHEET_TASK_MESSAGE = "task-message";
	private Label taskMessage;
	
	public UITaskMessage(String message){
		this.taskMessage = new Label(message);
		this.taskMessage.setFont(UIFont.SEGOE_M);
		this.taskMessage.getStyleClass().add(STYLESHEET_TASK_MESSAGE);
	}
	
	public Label getView(){
		return this.taskMessage;
	}
	
}
