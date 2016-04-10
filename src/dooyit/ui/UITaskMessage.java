// @@author A0124278A
package dooyit.ui;

import javafx.scene.control.Label;

/**
 * 
 * @author Wu Wenqi
 *
 */

public class UITaskMessage {
	private static final String STYLESHEET_TASK_MESSAGE = UIStyle.TASK_MESSAGE;
	
	private Label taskMessage;
	
	protected UITaskMessage(String message) {
		this.taskMessage = new Label(message);
		this.taskMessage.getStyleClass().add(STYLESHEET_TASK_MESSAGE);
	}
	
	protected Label getView() {
		return this.taskMessage;
	}	
}
