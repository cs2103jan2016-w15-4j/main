package dooyit.ui;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class UICommandBox {
	private static final String CMD_TEXT_FIELD_PROMPT = "Enter command here. Type 'help' for manual.";
	private static final String STYLECLASS_CMD_TEXT_FIELD = UIStyle.COMMAND_TEXTFIELD;
	private static final int PREFWIDTH_CMD_TEXT_FIELD = 2000;
	private static final String STYLECLASS_CMD_BOX = UIStyle.COMMAND_BOX;

	private HBox commandBox;
	private TextField commandTextField;
	
	public UICommandBox(){
		initialize();
	}
	
	private void initialize(){
		initCommandTextField();
		initCommandBox();
	}
	
	private void initCommandTextField(){
		this.commandTextField = new TextField();
		this.commandTextField.setPrefWidth(PREFWIDTH_CMD_TEXT_FIELD);
		this.commandTextField.setFont(UIFont.CONSOLAS_M);
		this.commandTextField.setPromptText(CMD_TEXT_FIELD_PROMPT);
		this.commandTextField.getStyleClass().add(STYLECLASS_CMD_TEXT_FIELD);
	}
	
	private void initCommandBox(){
		this.commandBox = new HBox();
		this.commandBox.getStyleClass().add(STYLECLASS_CMD_BOX);
		this.commandBox.getChildren().addAll(commandTextField);
	}

	public HBox getView() {
		return this.commandBox;
	}

	public TextField getCommandTextField() {
		return this.commandTextField;
	}
	
	public boolean isSelected(){
		return this.commandTextField.isFocused();
	}
	
	public void select(){
		this.commandTextField.requestFocus();
	}
	
	public void empty(){
		this.commandTextField.clear();
	}
}
