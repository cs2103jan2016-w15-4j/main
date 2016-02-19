package dooyit.main;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import dooyit.logic.Logic;
import dooyit.ui.*;

/**
 * 
 * @author Lim Ta Eu
 */

public class Main extends Application{
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

	// scanner for receiving user input
	private Scanner sc;
	private UIController ui;
	Logic logic;

	public Main() {
	}

	private static void launchDooyit(String[] args) {
		Main textBuddy = new Main();
		// textBuddy.init(new Scanner(System.in));
	}


	public void init(Scanner sc) throws IOException {
		this.sc = sc;
		logic = new Logic();
		UI(logic);
	}


	@Override
	public void start(Stage primaryStage) throws IOException {
		
		logic = new Logic();
		
		try {
			this.ui = new UIController(primaryStage, logic);
			Scene scene = this.ui.getScene();
			
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("icon.png"));
	        primaryStage.setTitle("Dooyit");
	        primaryStage.setMaxWidth(720);
	        primaryStage.show();
			logic.setUIController(this.ui);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void UI(Logic logic) throws IOException{
		while (true) {
			showToUser(MESAGE_COMMAND);
			String userInput = sc.nextLine();
			logic.processCommand(userInput);
		}
	}
	
	public static void main(String[] args) {
		launchDooyit(args);
		launch(args);
	}


	/**
	 * Display message to user
	 * 
	 * @param message
	 */
	public static void showToUser(String message) {
		System.out.println(message);
	}
}
