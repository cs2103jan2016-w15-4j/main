// @@author A0124278A
package dooyit.ui;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Wu Wenqi
 *
 */

public class UICommandBox {
	private static final String CMD_TEXT_FIELD_PROMPT = "Enter command here. Type 'help' for manual.";
	private static final String STYLECLASS_CMD_TEXT_FIELD = UIStyle.COMMAND_TEXTFIELD;
	private static final int PREFWIDTH_CMD_TEXT_FIELD = 2000;
	private static final String STYLECLASS_CMD_BOX = UIStyle.COMMAND_BOX;
	private HBox commandBox;
	private TextField commandTextField;
	
	private int historyIndex;
	private ArrayList<String> commandHistory;
	
	protected UICommandBox(){
		initialize();
	}
	
	private void initialize(){
		initCommandTextField();
		initCommandBox();
		initCommandHistory();
	}
	
	private void initCommandTextField(){
		this.commandTextField = new TextField();
		this.commandTextField.setPrefWidth(PREFWIDTH_CMD_TEXT_FIELD);
		this.commandTextField.setPromptText(CMD_TEXT_FIELD_PROMPT);
		this.commandTextField.getStyleClass().add(STYLECLASS_CMD_TEXT_FIELD);
	}
	
	private void initCommandBox(){
		this.commandBox = new HBox();
		this.commandBox.getStyleClass().add(STYLECLASS_CMD_BOX);
		this.commandBox.getChildren().addAll(commandTextField);
	}
	
	private void initCommandHistory(){
		this.historyIndex = -1;
		this.commandHistory = new ArrayList<String>();
	}
	
	protected void updateHistory(String s){
		this.commandHistory.add(s);
		this.historyIndex = this.commandHistory.size() - 1;
	}
	
	private boolean isValidPrevHistoryIndex(){
		return this.historyIndex >= 0 && this.historyIndex < this.commandHistory.size();
	}
	
	protected void showPrevHistory() {
		if (isValidPrevHistoryIndex()){
			this.commandTextField.setText(this.commandHistory.get(historyIndex));
			this.historyIndex--;
		}
	}
	
	private boolean isValidNextHistoryIndex(){
		return this.historyIndex >= -1 && this.historyIndex < this.commandHistory.size() - 1;
	}
	
	protected void showNextHistory() {
		if (isValidNextHistoryIndex()){
			this.historyIndex++;
			this.commandTextField.setText(this.commandHistory.get(historyIndex));
		} else {
			this.commandTextField.setText(UIData.EMP_STR);
		}
	}

	protected HBox getView() {
		return this.commandBox;
	}

	protected TextField getCommandTextField() {
		return this.commandTextField;
	}
	
	protected boolean isSelected(){
		return this.commandTextField.isFocused();
	}
	
	protected void select(){
		this.commandTextField.requestFocus();
	}
	
	protected void empty(){
		this.commandTextField.clear();
	}
}
