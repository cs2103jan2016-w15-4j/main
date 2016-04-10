// @@author A0124278A
package dooyit.ui;

import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * The <tt>UICommandBox</tt> class contains the methods to modify the textfield 
 * in which the user types his commands, as well as to retrieve the user's command input history.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class UICommandBox {
	private static final String CMD_TEXT_FIELD_PROMPT = "Enter command here. Type 'help' for manual.";
	private static final String STYLECLASS_CMD_TEXT_FIELD = UIStyle.COMMAND_TEXTFIELD;
	private static final int PREFWIDTH_CMD_TEXT_FIELD = 2000;
	private static final String STYLECLASS_CMD_BOX = UIStyle.COMMAND_BOX;
	private static final int INITIAL_HISTORY_INDEX = -1;
	
	private HBox commandBox;
	private TextField commandTextField;
	private int historyIndex;
	private ArrayList<String> commandHistory;
	
	/**
	 * This is the constructor method for <tt>UICommandBox</tt>.
	 */
	protected UICommandBox() {
		initialize();
	}
	
	/**
	 * This method is used to initialize the <tt>UICommandBox</tt>
	 * It is used by the constructor.
	 */
	private void initialize() {
		initCommandTextField();
		initCommandBox();
		initCommandHistory();
	}
	
	/**
	 * This method is used to initialize the textfield in which the user types his commands. 
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCommandTextField() {
		this.commandTextField = new TextField();
		this.commandTextField.setPrefWidth(PREFWIDTH_CMD_TEXT_FIELD);
		this.commandTextField.setPromptText(CMD_TEXT_FIELD_PROMPT);
		this.commandTextField.getStyleClass().add(STYLECLASS_CMD_TEXT_FIELD);
	}
	
	/**
	 * This method is used to initialize the view box that contains the user input textfield.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCommandBox() {
		this.commandBox = new HBox();
		this.commandBox.getStyleClass().add(STYLECLASS_CMD_BOX);
		this.commandBox.getChildren().addAll(commandTextField);
	}
	
	/**
	 * This method is used to initialize the <tt>ArrayList</tt> that will store the user's input history.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCommandHistory() {
		this.historyIndex = INITIAL_HISTORY_INDEX;
		this.commandHistory = new ArrayList<String>();
	}
	
	/**
	 * This method is used to check if the index points to an earlier user input in history. 
	 * It is called by the <tt>showPrevHistory</tt> method.
	 * @return <tt>True</tt> if the index is valid, <tt>False</tt> otherwise.
	 */
	private boolean isValidPrevHistoryIndex() {
		return this.historyIndex >= 0 && this.historyIndex < this.commandHistory.size();
	}
	
	/**
	 * This method is used to check if the index points to a later user input in history. 
	 * It is called by the <tt>showNextHistory</tt> method.
	 * @return <tt>True</tt> if the index is valid, <tt>False</tt> otherwise.
	 */
	private boolean isValidNextHistoryIndex() {
		return this.historyIndex >= -1 && this.historyIndex < this.commandHistory.size() - 1;
	}
	
	/**
	 * This method is used to add an user input string to the history and update the index.
	 * @param s The user input to be added to history.
	 */
	protected void updateHistory(String s) {
		this.commandHistory.add(s);
		this.historyIndex = this.commandHistory.size() - 1;
	}
	
	/**
	 * This method is used to populate the textfield with an earlier user input from history.
	 */
	protected void showPrevHistory() {
		if (isValidPrevHistoryIndex()) {
			this.commandTextField.setText(this.commandHistory.get(this.historyIndex));
			this.historyIndex--;
		}
	}

	/**
	 * This method is used to populate the textfield with a later user input from history.
	 */
	protected void showNextHistory() {
		if (isValidNextHistoryIndex()) {
			this.historyIndex++;
			this.commandTextField.setText(this.commandHistory.get(this.historyIndex));
		} else {
			this.commandTextField.setText(UIData.EMP_STR);
		}
	}

	/**
	 * This method is used to retrieve the view box which contains the user input textfield.
	 * @return The view box which contains the user input textfield.
	 */
	protected HBox getView() {
		return this.commandBox;
	}

	/**
	 * This method is used to retrieve the user input textfield.
	 * @return The user input textfield.
	 */
	protected TextField getCommandTextField() {
		return this.commandTextField;
	}
	
	/**
	 * This method is used to check if the user input textfield is focused.
	 * @return <tt>True</tt> if the user textfield is focused, <tt>False</tt> otherwise.
	 */
	protected boolean isSelected() {
		return this.commandTextField.isFocused();
	}
	
	/**
	 * This method is used to focus the user input textfield.
	 */
	protected void select() {
		this.commandTextField.requestFocus();
	}
	
	/**
	 * This method is used to clear any text that is displayed in the user input textfield.
	 */
	protected void empty() {
		this.commandTextField.clear();
	}
}
