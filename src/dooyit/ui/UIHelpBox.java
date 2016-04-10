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
 * 
 * @author Wu Wenqi
 *
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

	protected UIHelpBox() {
		initialize();
	}
	
	private void initialize() {
		this.isOn = false;
		initTitle();
		initLeftContent();
		initRightContent();
		initContentWrapper();
		initCloseLabel();
		initHelpBox();
	}
	
	private void initTitle() {
		this.title = new Label(LABEL_TITLE);
		this.title.setAlignment(Pos.CENTER);
		this.title.getStyleClass().add(STYLECLASS_TITLE);
	}
	
	private void initLeftContent() {
		this.leftContent = new VBox();
		this.leftContent.setSpacing(SPACING_CONTENT);
		cmdListLeft.forEach((cmdName) -> {
			Label cmdNameLabel = makeLabel(cmdName);
			this.leftContent.getChildren().add(cmdNameLabel);
		});
	}
	
	private void initRightContent() {
		this.rightContent = new VBox();
		this.rightContent.setSpacing(SPACING_CONTENT);
		cmdListRight.forEach((cmdDesc) -> {
			Label cmdDescLabel = makeLabel(cmdDesc);
			this.rightContent.getChildren().add(cmdDescLabel);
		});
	}
	
	private void initContentWrapper() {
		this.contentWrapper = new HBox();
		this.contentWrapper.setSpacing(SPACING_CONTENT_WRAPPER);
		this.contentWrapper.getChildren().addAll(this.leftContent, this.rightContent);
	}
	
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
	
	private void initHelpBox() {
		this.helpBoxWrapper = new VBox();
		this.helpBoxWrapper.setSpacing(SPACING_HELP_BOX_WRAPPER);
		this.helpBoxWrapper.setAlignment(Pos.CENTER);
		this.helpBoxWrapper.getChildren().addAll(this.title, this.contentWrapper, this.closeLabel);
		this.helpBoxWrapper.getStyleClass().add(STYLECLASS_HELP_BOX_WRAPPER);
		this.helpBox = new Popup();
		this.helpBox.getContent().addAll(this.helpBoxWrapper);
	}
	
	private Label makeLabel(String s) {
		Label label = new Label(s);
		label.getStyleClass().add(STYLECLASS_CONTENT_LABEL);
		label.setMaxHeight(LABEL_HEIGHT);
		label.setPrefHeight(LABEL_HEIGHT);
		label.setMinHeight(LABEL_HEIGHT);
		return label;
	}

	protected boolean isShowing() {
		return this.helpBox.isShowing();
	}

	protected void updatePosition(double stageX, double stageY, double stageWidth, double stageHeight) {
		this.helpBox.setX(stageX + stageWidth / 2 - WIDTH / 2);
		this.helpBox.setY(stageY + stageHeight / 2 - HEIGHT / 2);
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
	}

	protected void show(Stage primaryStage) {
		this.isOn = true;
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
		this.helpBox.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - WIDTH / 2);
		this.helpBox.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - HEIGHT / 2);
		this.helpBox.show(primaryStage);
	}
	
	protected void tempHide() {
		this.helpBox.hide();
	}

	protected void hide() {
		this.isOn = false;
		this.helpBox.hide();
	}
	
	protected boolean isOn() {
		return this.isOn;
	}
}
