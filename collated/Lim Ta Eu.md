# Lim Ta Eu
###### src\dooyit\ui\Main.java
``` java
 */

public class Main extends Application {
	public static final String MESSAGE_WELCOME = "Welcome to TextBuddy %1$s is ready for use.";
	public static final String MESSAGE_TEXT_NO = "%1$d. %2$s";
	public static final String MESSAGE_FILE_IS_EMPTY = "%1$s is empty";
	public static final String MESSAGE_INPUT_ADDED = "added to %1$s: \"%2$s\"";
	public static final String MESSAGE_INCORRECT_ADD_FORMAT = "Incorrect format -add <text>";
	public static final String MESSAGE_DELETED = "Deleted from %1$s: \"%2$s\"";
	public static final String MESSAGE_UNABLE_TO_DELETE = "Unable to delete text in line No. %1$d";
	public static final String MESSAGE_INCORRECT_DELETE_FORMAT = "Incorrect format -delete <number>, number starts from 1";
	public static final String MESSAGE_ALL_DELETED = "All content deleted from %1$s";
	public static final String MESSAGE_INVALID_COMMAND = "Invalid command: %1$s";
	public static final String MESAGE_EMPTY_COMMAND = "Empty command";
	public static final String MESAGE_COMMAND = "command: ";
	public static final String MESSAGE_INVALID_ARGUMENT = "Invalid Argument";
	public static final String ERROR_IO_EXCEPTION = "ERROR: IOException";
	public static final String ERROR_NUMBER_FORMAT_EXCEPTION = "ERROR: Number Format Exception";
	public static final int NO_OF_ARG = 1;
	private static final String APP_ICON = "icon.png";
	private static final String APP_TITLE = "Dooyit";
	private static final int MINWIDTH_STAGE = 720;
	private static final int MINHEIGHT_STAGE = 620;

	// scanner for receiving user input
	private UIController ui;
	LogicController logic;

	public Main() {
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			logic = new LogicController();
			initStage(primaryStage);
			this.ui = UIController.getInstance(primaryStage, logic);
			Scene scene = this.ui.getScene();
			primaryStage.setScene(scene);
			primaryStage.show();
			logic.setUIController(this.ui);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initStage(Stage primaryStage){
		primaryStage.getIcons().add(new Image(APP_ICON));
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setMinWidth(MINWIDTH_STAGE);
		primaryStage.setWidth(MINWIDTH_STAGE);
		primaryStage.setMinHeight(MINHEIGHT_STAGE);
		primaryStage.setHeight(MINHEIGHT_STAGE);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void showToUser(String message) {
		System.out.println(message);
	}
}
```
