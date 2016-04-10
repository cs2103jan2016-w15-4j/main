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
 * 
 * @author Wu Wenqi
 *
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

	protected UIMessageBox(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
	}
	
	private void initialize() {
		initMessageLabel();
		initMessageBox();
		initTransitions();
	}
	
	private void initMessageLabel() {
		this.messageLabel = new Label();
		this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL);
	}
	
	private void initMessageBox() {
		this.messageBox = new Popup();
		this.messageBox.getContent().addAll(this.messageLabel);
	}
	
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
	
	private void update(double x, double y, double width, double height) {
		this.messageBox.setX(x);
		this.messageBox.setY(y);
		this.messageLabel.setPrefSize(width, height);
	}
	
	private void updateMessageLabelStyleClass(UIMessageType msgType) {
		if (msgType == UIMessageType.DEFAULT) {
			this.messageLabel.getStyleClass().clear();
			this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL);
		} else if (msgType == UIMessageType.ERROR) {
			this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL_ERROR);
		}
	}
	
	private void updateMessageLabelSettings(String msg) {
		if (msg.length() > CHAR_LEN_LONG) {
			this.ft.setDuration(Duration.millis(FADE_TIME_LONG));
		} else {
			this.ft.setDuration(Duration.millis(FADE_TIME));
		}
		this.isOn = true;
		this.messageLabel.setText(msg);
	}
	
	protected boolean isShowing() {
		return this.messageBox.isShowing();
	}
	
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
	
	protected void display() {
		updatePosition();
		this.messageBox.show(this.primaryStage);
	}
	
	
	protected void show(String msg, UIMessageType msgType) {
		updateMessageLabelStyleClass(msgType);
		updateMessageLabelSettings(msg);
		display();
		this.ft.playFromStart();
	}

	protected void tempHide() {
		this.messageBox.hide();
	}

	protected void hide() {
		this.ft.play();
	}

	protected boolean isOn() {
		return this.isOn;
	}
}