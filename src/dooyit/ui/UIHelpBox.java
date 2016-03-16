package dooyit.ui;

import java.util.ArrayList;

import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class UIHelpBox {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 450;

	private static final String STYLECLASS_TITLE = UIStyle.HELP_BOX_TITLE;
	private static final String LABEL_TITLE = "Hola! Here are some tips to get you started.";
	private static final Font FONT_TITLE = UIFont.EUPHEMIA_L;

	private static final String STYLECLASS_CONTENT_LABEL = UIStyle.HELP_BOX_CONTENT_LABEL;
	private static final Font FONT_CMD_NAME = UIFont.EUPHEMIA_M;
	private static final Font FONT_CMD_DESC = UIFont.CONSOLAS_M;
	private static final int SPACING_CONTENT_WRAPPER = 80;
	private static final int SPACING_CONTENT = 10;

	private static final String STYLECLASS_HELP_BOX_WRAPPER = UIStyle.HELP_BOX_WRAPPER;
	private static final int SPACING_HELP_BOX_WRAPPER = 100;

	private Popup helpBox;
	private VBox helpBoxWrapper;
	private Label title;

	private HBox contentWrapper;
	private VBox leftContent;
	private VBox rightContent;

	public UIHelpBox() {
		this.title = new Label(LABEL_TITLE);
		this.title.setFont(FONT_TITLE);
		this.title.setAlignment(Pos.CENTER);
		this.title.getStyleClass().add(STYLECLASS_TITLE);

		this.leftContent = new VBox();
		this.leftContent.setSpacing(SPACING_CONTENT);
		ArrayList<String> cmdNameList = new ArrayList<String>();
		cmdNameList.add("Add task");
		cmdNameList.add("Mark task as complete");
		cmdNameList.add("Delete task");
		cmdNameList.add("Show different views");
		cmdNameList.add("Quit the app");
		cmdNameList.add("Toggle help");

		cmdNameList.forEach((cmdName) -> {
			Label cmdNameLabel = new Label(cmdName);
			cmdNameLabel.getStyleClass().add(STYLECLASS_CONTENT_LABEL);
			cmdNameLabel.setFont(FONT_CMD_NAME);
			this.leftContent.getChildren().add(cmdNameLabel);
		});

		this.rightContent = new VBox();
		this.rightContent.setSpacing(SPACING_CONTENT);
		ArrayList<String> cmdDescList = new ArrayList<String>();
		cmdDescList.add("add <task name>");
		cmdDescList.add("mark <task index>");
		cmdDescList.add("delete <task index>");
		cmdDescList.add("show <view>");
		cmdDescList.add("exit");
		cmdDescList.add("help");

		cmdDescList.forEach((cmdDesc) -> {
			Label cmdDescLabel = new Label(cmdDesc);
			cmdDescLabel.getStyleClass().add(STYLECLASS_CONTENT_LABEL);
			cmdDescLabel.setFont(FONT_CMD_DESC);
			this.rightContent.getChildren().add(cmdDescLabel);
		});

		this.contentWrapper = new HBox();
		this.contentWrapper.setSpacing(SPACING_CONTENT_WRAPPER);
		this.contentWrapper.getChildren().addAll(this.leftContent, this.rightContent);

		this.helpBoxWrapper = new VBox();
		this.helpBoxWrapper.setSpacing(SPACING_HELP_BOX_WRAPPER);
		this.helpBoxWrapper.getChildren().addAll(this.title, this.contentWrapper);
		this.helpBoxWrapper.getStyleClass().add(STYLECLASS_HELP_BOX_WRAPPER);

		this.helpBox = new Popup();
		this.helpBox.getContent().addAll(this.helpBoxWrapper);

	}

	public boolean isShowing() {
		return this.helpBox.isShowing();
	}

	public void updatePosition(double stageX, double stageY, double stageWidth, double stageHeight) {
		this.helpBox.setX(stageX + stageWidth / 2 - WIDTH / 2);
		this.helpBox.setY(stageY + stageHeight / 2 - HEIGHT / 2);
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
	}

	public void show(Stage primaryStage) {
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
		this.helpBox.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - WIDTH / 2);
		this.helpBox.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - HEIGHT / 2);
		this.helpBox.show(primaryStage);
	}

	public void hide() {
		this.helpBox.hide();
	}
}
