import java.util.Scanner;

/**
 * 
 * @author Lim Ta Eu
 */
public class Main {
	static final String COMMAND_EXIT = "exit";
	static final String COMMAND_CLEAR = "clear";
	static final String COMMAND_SHOW = "show";
	static final String COMMAND_DELETE = "delete";
	static final String COMMAND_EDIT = "edit";
	static final String COMMAND_ADD = "add";
	static final String MESSAGE_WELCOME = "Welcome to TextBuddy %1$s is ready for use.";
	static final String MESSAGE_TEXT_NO = "%1$d. %2$s";
	static final String MESSAGE_FILE_IS_EMPTY = "%1$s is empty";
	static final String MESSAGE_INPUT_ADDED = "added to %1$s: \"%2$s\"";
	static final String MESSAGE_INCORRECT_ADD_FORMAT = "Incorrect format -add <text>";
	static final String MESSAGE_DELETED = "Deleted from %1$s: \"%2$s\"";
	static final String MESSAGE_UNABLE_TO_DELETE = "Unable to delete text in line No. %1$d";
	static final String MESSAGE_INCORRECT_DELETE_FORMAT = "Incorrect format -delete <number>, number starts from 1";
	static final String MESSAGE_ALL_DELETED = "All content deleted from %1$s";
	static final String MESSAGE_INVALID_COMMAND = "Invalid command: %1$s";
	static final String MESAGE_EMPTY_COMMAND = "Empty command";
	static final String MESAGE_COMMAND = "command: ";
	static final String MESSAGE_INVALID_ARGUMENT = "Invalid Argument";
	static final String ERROR_IO_EXCEPTION = "ERROR: IOException";
	static final String ERROR_NUMBER_FORMAT_EXCEPTION = "ERROR: Number Format Exception";
	static final int NO_OF_ARG = 1;

	// scanner for receiving user input
	private Scanner sc;

	Logic logic;

	public Main() {
	}

	public static void main(String[] args) {
		launchDooyit(args);
	}



	private static void launchDooyit(String[] args) {
		Main textBuddy = new Main();
		textBuddy.init(new Scanner(System.in));
	}


	public void init(Scanner sc) {
		this.sc = sc;
		logic = new Logic();
		UI(logic);
	}


	
	private void UI(Logic logic){
		while (true) {
			showToUser(MESAGE_COMMAND);
			String userInput = sc.nextLine();
			logic.processCommand(userInput);
		}
	}


	/**
	 * Display message to user
	 * 
	 * @param message
	 */
	static void showToUser(String message) {
		System.out.println(message);
	}
}
