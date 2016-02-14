package dooyit.ui;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UICommandBox {
	private HBox commandBox;
	private TextField commandTextField;
	
	public UICommandBox(){
		this.commandTextField = new TextField();
		this.commandTextField.setPrefWidth(2000);
		this.commandTextField.setFont(Font.font("Consolas", 14));
		this.commandTextField.setPromptText("Enter command here. Type 'help' for manual.");
		this.commandTextField.getStyleClass().add("command-textfield");
		
		this.commandBox = new HBox();
		this.commandBox.getStyleClass().add("command-box");
		this.commandBox.getChildren().addAll(commandTextField);
	}
	
	public HBox getView(){
		return this.commandBox;
	}
	
	public TextField getCommandTextField(){
		return this.commandTextField;
	}
}
