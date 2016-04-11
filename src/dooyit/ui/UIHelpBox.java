// @@author A0124278A
package dooyit.ui;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * The <tt>UIHelpBox</tt> class contains the methods to initialize the help box, 
 * control its visibility and set the message to be displayed.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class UIHelpBox {
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final String STYLECLASS_TITLE = UIStyle.HELP_BOX_TITLE;
	private static final String LABEL_TITLE = "Hola! Here are some tips to get you started.";
	private static final int LABEL_HEIGHT = 20;
	private static final String STYLECLASS_CONTENT_LABEL = UIStyle.HELP_BOX_CONTENT_LABEL;
	private static final int SPACING_CONTENT_WRAPPER = 80;
	private static final int SPACING_CONTENT = 10;
	private static final String STYLECLASS_HELP_BOX_WRAPPER = UIStyle.HELP_BOX_WRAPPER;
	private static final int SPACING_HELP_BOX_WRAPPER = 40;
	private static final String STYLECLASS_CLOSE_LABEL = "help-box-close-label";
	private static final String CLOSE_LABEL_TITLE = "CLOSE";
	private static final String DESC_ADD = "add: create a task or event";
	private static final String DESC_ADDCAT = "addcat: create a category";
	private static final String DESC_CLEAR = "clear: delete ALL tasks";
	private static final String DESC_DELETE = "delete: delete a task";
	private static final String DESC_DELETECAT = "deletecat: delete a category";
	private static final String DESC_EDIT = "edit: edit details of a task";
	private static final String DESC_EDITCAT = "editcat: edit details of a category";
	private static final String DESC_EXIT = "exit: quit the application";
	private static final String DESC_FLOAT = "float: make a task floating";
	private static final String DESC_MARK = "mark: mark a task as done";
	private static final String DESC_MOVE = "move: move a task to a category";
	private static final String DESC_REDO = "redo: redo an undone command";
	private static final String DESC_REMOVE = "remove: take a task out of a category";
	private static final String DESC_SHOW = "show: switch between views";
	private static final String DESC_SHOWCAT = "showcat: switch to a category view";
	private static final String DESC_SEARCH = "search: search for tasks and events";
	private static final String DESC_SKIN = "skin: change application skin";
	private static final String DESC_STORAGE = "storage: specify storage directory";
	private static final String DESC_UNMARK = "unmark: unmark a completed task";
	private static final String DESC_UNDO = "undo: undo the previous command";

	private Popup helpBox;
	private VBox helpBoxWrapper;
	private Label title;
	private boolean isOn;
	private HBox contentWrapper;
	private VBox leftContent;
	private VBox rightContent;
	private Label closeLabel;
	
	private static final ArrayList<String> cmdListLeft = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add(DESC_ADD);
			add(DESC_ADDCAT);
			add(DESC_CLEAR);
			add(DESC_DELETE);
			add(DESC_DELETECAT);
			add(DESC_EDIT);
			add(DESC_EDITCAT);
			add(DESC_EXIT);
			add(DESC_FLOAT);
			add(DESC_MARK);
		}
	};
	
	private static final ArrayList<String> cmdListRight = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add(DESC_MOVE);
			add(DESC_REDO);
			add(DESC_REMOVE);
			add(DESC_SHOW);
			add(DESC_SHOWCAT);
			add(DESC_SEARCH);
			add(DESC_SKIN);
			add(DESC_STORAGE);
			add(DESC_UNMARK);
			add(DESC_UNDO);
		}
	};

	/**
	 * This is the constructor method.
	 */
	protected UIHelpBox() {
		initialize();
	}
	
	/**
	 * This method is used to initialize <tt>UIHelpBox</tt> class.
	 * It is used by the constructor.
	 */
	private void initialize() {
		this.isOn = false;
		initTitle();
		initLeftContent();
		initRightContent();
		initContentWrapper();
		initCloseLabel();
		initHelpBox();
	}
	
	/**
	 * This method is used to initialize the help box's title.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTitle() {
		this.title = new Label(LABEL_TITLE);
		this.title.setAlignment(Pos.CENTER);
		this.title.getStyleClass().add(STYLECLASS_TITLE);
	}
	
	/**
	 * This method is used to initialize the left-sided content of the help box.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initLeftContent() {
		this.leftContent = new VBox();
		this.leftContent.setSpacing(SPACING_CONTENT);
		cmdListLeft.forEach((cmdName) -> {
			Label cmdNameLabel = makeLabel(cmdName);
			this.leftContent.getChildren().add(cmdNameLabel);
		});
	}
	
	/**
	 * This method is used to initialize the right-sided content of the help box.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initRightContent() {
		this.rightContent = new VBox();
		this.rightContent.setSpacing(SPACING_CONTENT);
		cmdListRight.forEach((cmdDesc) -> {
			Label cmdDescLabel = makeLabel(cmdDesc);
			this.rightContent.getChildren().add(cmdDescLabel);
		});
	}
	
	/**
	 * This method is used to initialize the content wrapper. 
	 * It can only be called after the left- and right-sided content views have been initialized.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initContentWrapper() {
		this.contentWrapper = new HBox();
		this.contentWrapper.setSpacing(SPACING_CONTENT_WRAPPER);
		this.contentWrapper.getChildren().addAll(this.leftContent, this.rightContent);
	}
	
	/**
	 * This method is used to initialize the close button.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCloseLabel() {
		this.closeLabel = new Label(CLOSE_LABEL_TITLE);
		this.closeLabel.getStyleClass().add(STYLECLASS_CLOSE_LABEL);
		this.closeLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				hide();
				event.consume();
			}
		});
	}
	
	/**
	 * This method is used to initialize the help box view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initHelpBox() {
		this.helpBoxWrapper = new VBox();
		this.helpBoxWrapper.setSpacing(SPACING_HELP_BOX_WRAPPER);
		this.helpBoxWrapper.setAlignment(Pos.CENTER);
		this.helpBoxWrapper.getChildren().addAll(this.title, this.contentWrapper, this.closeLabel);
		this.helpBoxWrapper.getStyleClass().add(STYLECLASS_HELP_BOX_WRAPPER);
		this.helpBox = new Popup();
		this.helpBox.getContent().addAll(this.helpBoxWrapper);
	}
	
	/**
	 * This method is used to create and return a label that is to be displayed on 
	 * the left- and right-sided content views.
	 * @param s The text to set for the label.
	 * @return A label with <tt>s</tt> as its text.
	 */
	private Label makeLabel(String s) {
		Label label = new Label(s);
		label.getStyleClass().add(STYLECLASS_CONTENT_LABEL);
		label.setMaxHeight(LABEL_HEIGHT);
		label.setPrefHeight(LABEL_HEIGHT);
		label.setMinHeight(LABEL_HEIGHT);
		return label;
	}

	/**
	 * This method is used to check if the help box is visible.
	 * @return <tt>True</tt> if help box is visible, <tt>False</tt> otherwise.
	 */
	protected boolean isShowing() {
		return this.helpBox.isShowing();
	}

	/**
	 * This method is used to update the position of the help box.
	 * @param stageX The x-coordinate of the stage of the application scene.
	 * @param stageY The y-coordinate of the stage of the application scene.
	 * @param stageWidth The stage width of the application scene.
	 * @param stageHeight The stage height of the application scene.
	 */
	protected void updatePosition(double stageX, double stageY, double stageWidth, double stageHeight) {
		this.helpBox.setX(stageX + stageWidth / 2 - WIDTH / 2);
		this.helpBox.setY(stageY + stageHeight / 2 - HEIGHT / 2);
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
	}

	/**
	 * This method is used to show the help box.
	 * @param primaryStage The stage to show the help box in.
	 */
	protected void show(Stage primaryStage) {
		this.isOn = true;
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
		this.helpBox.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - WIDTH / 2);
		this.helpBox.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - HEIGHT / 2);
		this.helpBox.show(primaryStage);
	}
	
	/**
	 * This method is used to temporarily hide the help box.
	 */
	protected void tempHide() {
		this.helpBox.hide();
	}

	/**
	 * This method is used to hide the help box.
	 */
	protected void hide() {
		this.isOn = false;
		this.helpBox.hide();
	}
	
	/**
	 * This method is used to retrieve the visibility attribute of the help box.
	 * @return <tt>True</tt> if help box should be visible to user, <tt>False</tt> otherwise.
	 */
	protected boolean isOn() {
		return this.isOn;
	}
}
