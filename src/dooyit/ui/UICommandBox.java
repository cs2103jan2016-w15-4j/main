package dooyit.ui;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UICommandBox {
	private static final Font FONT_CMD_TEXT_FIELD = Font.font("Consolas", 14);
	private static final String CMD_TEXT_FIELD_PROMPT = "Enter command here. Type 'help' for manual.";
	private static final String STYLECLASS_CMD_TEXT_FIELD = "command-textfield";
	private static final int PREFWIDTH_CMD_TEXT_FIELD = 2000;
	
	private static final String STYLECLASS_CMD_BOX = "command-box";
	
	private HBox commandBox;
	private TextField commandTextField;
	
	public UICommandBox(){
		this.commandTextField = new TextField();
		this.commandTextField.setPrefWidth(PREFWIDTH_CMD_TEXT_FIELD);
		this.commandTextField.setFont(FONT_CMD_TEXT_FIELD);
		this.commandTextField.setPromptText(CMD_TEXT_FIELD_PROMPT);
		this.commandTextField.getStyleClass().add(STYLECLASS_CMD_TEXT_FIELD);
		
		this.commandBox = new HBox();
		this.commandBox.getStyleClass().add(STYLECLASS_CMD_BOX);
		this.commandBox.getChildren().addAll(commandTextField);
	}
	
	public HBox getView(){
		return this.commandBox;
	}
	
	public TextField getCommandTextField(){
		return this.commandTextField;
	}
}
