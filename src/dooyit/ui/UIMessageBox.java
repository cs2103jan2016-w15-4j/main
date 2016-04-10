// @@author A0124278A
package dooyit.ui;

import dooyit.common.utils.OsUtils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The <tt>UIMessageBox</tt> class contains the methods to initialize the message box, 
 * control its visibility and set its displayed message.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class UIMessageBox {
	private static final String STYLECLASS_MESSAGE_BOX_LABEL = UIStyle.MESSAGE_BOX_LABEL;
	private static final String STYLECLASS_MESSAGE_BOX_LABEL_ERROR = UIStyle.MESSAGE_BOX_LABEL_ERROR;
	private static final int FADE_TIME = 6000;
	private static final int FADE_TIME_LONG = 11000;
	private static final int CHAR_LEN_LONG = 40;
	private static final int PREFHEIGHT = 41;
	private static final int PAD_X = 8;
	private static final int PAD_Y = 105;
	private static final double FT_INITIAL_VAL = 1.0;
	private static final double FT_FINAL_VAL = 0.0;
	private static final int FT_CYCLE_COUNT = 1;

	private Stage primaryStage;
	private Popup messageBox;
	private Label messageLabel;
	private boolean isOn;
	private FadeTransition ft;

	/**
	 * This is the constructor method.
	 * @param primaryStage The stage of the application scene.
	 */
	protected UIMessageBox(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
	}
	
	/**
	 * This method is used to initialize <tt>UIMessageBox</tt> class.
	 * It is used by the constructor.
	 */
	private void initialize() {
		initMessageLabel();
		initMessageBox();
		initTransitions();
	}
	
	/**
	 * This method is used to initialize the message label.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initMessageLabel() {
		this.messageLabel = new Label();
		this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL);
	}
	
	/**
	 * This method is used to initialize the message box view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initMessageBox() {
		this.messageBox = new Popup();
		this.messageBox.getContent().addAll(this.messageLabel);
	}
	
	/**
	 * This method is used to initialize visual transitions for the message box.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTransitions() {
		this.ft = new FadeTransition(Duration.millis(FADE_TIME), this.messageLabel);
		this.ft.setFromValue(FT_INITIAL_VAL);
		this.ft.setToValue(FT_FINAL_VAL);
		this.ft.setCycleCount(FT_CYCLE_COUNT);
		this.ft.setAutoReverse(false);
		this.ft.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				messageBox.hide();
				isOn = false;
			}
		});
	}
	
	/**
	 * This method is used to change the position and dimension of the message box.
	 * @param x The value to set the x-coordinate of the message box to.
	 * @param y The value to set the y-coordinate of the message box to.
	 * @param width The value to set the width of the message box to.
	 * @param height The value to set the height of the message box to.
	 */
	private void update(double x, double y, double width, double height) {
		this.messageBox.setX(x);
		this.messageBox.setY(y);
		this.messageLabel.setPrefSize(width, height);
	}
	
	/**
	 * This method is used to update the style of the message label.
	 * @param msgType The <tt>UIMessageType</tt> to set the style of the message label to.
	 */
	private void updateMessageLabelStyleClass(UIMessageType msgType) {
		if (msgType == UIMessageType.DEFAULT) {
			this.messageLabel.getStyleClass().clear();
			this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL);
		} else if (msgType == UIMessageType.ERROR) {
			this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL_ERROR);
		}
	}
	
	/**
	 * This method is used to adjust the duration of the message box's transition, 
	 * depending on length of the message it is displaying.
	 * @param msg The message displayed by the message box.
	 */
	private void updateMessageLabelSettings(String msg) {
		if (msg.length() > CHAR_LEN_LONG) {
			this.ft.setDuration(Duration.millis(FADE_TIME_LONG));
		} else {
			this.ft.setDuration(Duration.millis(FADE_TIME));
		}
		this.isOn = true;
		this.messageLabel.setText(msg);
	}
	
	/**
	 * This method is used to check if the message box is visible.
	 * @return <tt>True</tt> if message box is visible, <tt>False</tt> otherwise.
	 */
	protected boolean isShowing() {
		return this.messageBox.isShowing();
	}
	
	/**
	 * This method is used to update the position of the message box.
	 */
	protected void updatePosition() {
		double x = this.primaryStage.getX();
		double y = this.primaryStage.getY() + this.primaryStage.getHeight() - PAD_Y;
		double width = this.primaryStage.getWidth();
		if(OsUtils.isWindows()) {
			x += PAD_X;
			width -= 2 * PAD_X;
		}
		update(x, y, width, PREFHEIGHT);
	}
	
	/**
	 * This method is used to show the message box in the stage of the application scene.
	 * It is used by the <tt>show</tt> method.
	 */
	protected void display() {
		updatePosition();
		this.messageBox.show(this.primaryStage);
	}
	
	/**
	 * This method is used to show the message box to the user.
	 * @param msg The message to display to the user.
	 * @param msgType The <tt>UIMessageType</tt> of the message.
	 */
	protected void show(String msg, UIMessageType msgType) {
		updateMessageLabelStyleClass(msgType);
		updateMessageLabelSettings(msg);
		display();
		this.ft.playFromStart();
	}

	/**
	 * This method is used to temporarily hide the message box.
	 */
	protected void tempHide() {
		this.messageBox.hide();
	}

	/**
	 * This method is used to hide the message box.
	 */
	protected void hide() {
		this.ft.play();
	}

	/**
	 * This method is used to retrieve the visibility attribute of the message box.
	 * @return <tt>True</tt> if message box should be visible to user, <tt>False</tt> otherwise.
	 */
	protected boolean isOn() {
		return this.isOn;
	}
}