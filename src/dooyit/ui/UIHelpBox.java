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

public class UIHelpBox {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 450;
	private static final String STYLECLASS_TITLE = UIStyle.HELP_BOX_TITLE;
	private static final String LABEL_TITLE = "Hola! Here are some tips to get you started.";
	private static final int LABEL_HEIGHT = 20;
	private static final String STYLECLASS_CONTENT_LABEL = UIStyle.HELP_BOX_CONTENT_LABEL;
	private static final int SPACING_CONTENT_WRAPPER = 80;
	private static final int SPACING_CONTENT = 10;
	private static final String STYLECLASS_HELP_BOX_WRAPPER = UIStyle.HELP_BOX_WRAPPER;
	private static final int SPACING_HELP_BOX_WRAPPER = 60;
	private static final String STYLECLASS_CLOSE_LABEL = "help-box-close-label";
	private static final String CLOSE_LABEL_TITLE = "CLOSE";
	private static final String DESC_ADD_TASK = "Add task";
	private static final String DESC_MARK_TASK = "Mark task as complete";
	private static final String DESC_DEL_TASK = "Delete task";
	private static final String DESC_SHOW_VIEW = "Show different views";
	private static final String DESC_QUIT_APP = "Quit Dooyit";
	private static final String CMD_ADD_TASK = "add <task name>";
	private static final String CMD_MARK_TASK = "mark <task index>";
	private static final String CMD_DEL_TASK = "delete <task index>";
	private static final String CMD_SHOW_VIEW = "show <view>";
	private static final String CMD_QUIT_APP = "exit";

	private Popup helpBox;
	private VBox helpBoxWrapper;
	private Label title;
	private boolean isOn;
	private HBox contentWrapper;
	private VBox leftContent;
	private VBox rightContent;
	private Label closeLabel;
	
	private static ArrayList<String> cmdNameList = new ArrayList<String>(){{
		add(DESC_ADD_TASK);
		add(DESC_MARK_TASK);
		add(DESC_DEL_TASK);
		add(DESC_SHOW_VIEW);
		add(DESC_QUIT_APP);
	}};
	
	private static ArrayList<String> cmdDescList = new ArrayList<String>(){{
		add(CMD_ADD_TASK);
		add(CMD_MARK_TASK);
		add(CMD_DEL_TASK);
		add(CMD_SHOW_VIEW);
		add(CMD_QUIT_APP);
	}};

	protected UIHelpBox() {
		initialize();
	}
	
	private void initialize(){
		this.isOn = false;
		initTitle();
		initLeftContent();
		initRightContent();
		initContentWrapper();
		initCloseLabel();
		initHelpBox();
	}
	
	private void initTitle(){
		this.title = new Label(LABEL_TITLE);
		this.title.setAlignment(Pos.CENTER);
		this.title.getStyleClass().add(STYLECLASS_TITLE);
	}
	
	private void initLeftContent(){
		this.leftContent = new VBox();
		this.leftContent.setSpacing(SPACING_CONTENT);

		cmdNameList.forEach((cmdName) -> {
			Label cmdNameLabel = makeLabel(cmdName);
			this.leftContent.getChildren().add(cmdNameLabel);
		});
	}
	
	private void initRightContent(){
		this.rightContent = new VBox();
		this.rightContent.setSpacing(SPACING_CONTENT);

		cmdDescList.forEach((cmdDesc) -> {
			Label cmdDescLabel = makeLabel(cmdDesc);
			this.rightContent.getChildren().add(cmdDescLabel);
		});
	}
	
	private void initContentWrapper(){
		this.contentWrapper = new HBox();
		this.contentWrapper.setSpacing(SPACING_CONTENT_WRAPPER);
		this.contentWrapper.getChildren().addAll(this.leftContent, this.rightContent);
	}
	
	private void initCloseLabel(){
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
	
	private void initHelpBox(){
		this.helpBoxWrapper = new VBox();
		this.helpBoxWrapper.setSpacing(SPACING_HELP_BOX_WRAPPER);
		this.helpBoxWrapper.setAlignment(Pos.CENTER);
		this.helpBoxWrapper.getChildren().addAll(this.title, this.contentWrapper, this.closeLabel);
		this.helpBoxWrapper.getStyleClass().add(STYLECLASS_HELP_BOX_WRAPPER);
		this.helpBox = new Popup();
		this.helpBox.getContent().addAll(this.helpBoxWrapper);
	}
	
	private Label makeLabel(String s){
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
	
	protected void tempHide(){
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
