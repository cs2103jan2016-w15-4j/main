// @@author A0124278A
package dooyit.ui;

import javafx.scene.control.Label;

/**
 * The <tt>UITaskMessage</tt> class contains the methods to initialize the label that is added to 
 * the message box.
 * @author 	Wu Wenqi
 * @version 0.5
 * @since 	2016-04-10
 */

public class UITaskMessage {
	private static final String STYLESHEET_TASK_MESSAGE = UIStyle.TASK_MESSAGE;
	
	private Label taskMessage;
	
	/**
	 * This is the constructor method.
	 * @param message The message to display in the label.
	 */
	protected UITaskMessage(String message) {
		this.taskMessage = new Label(message);
		this.taskMessage.getStyleClass().add(STYLESHEET_TASK_MESSAGE);
	}
	
	/**
	 * This method is used to retrieve the message box label.
	 * @return The message box label.
	 */
	protected Label getView() {
		return this.taskMessage;
	}	
}
