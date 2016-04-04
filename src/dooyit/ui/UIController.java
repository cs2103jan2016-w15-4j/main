package dooyit.ui;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.TaskGroup;
import dooyit.logic.api.*;

/**
 * 
 * @@author Wu Wenqi <A0124278A>
 *
 */

public class UIController {

	static final int WIDTH_SCENE = 720;
	static final int HEIGHT_SCENE = 580;
	static final String STYLECLASS_MAIN_VIEW = UIStyle.MAIN_VIEW;

	private String urlCssCommon;
	private String urlCssThemeLight;
	private String urlCssThemeDark;
	private String urlCssThemeAqua;
	private Scene scene;
	private BorderPane root;
	private UIHeader header;
	private UISideMenu sideMenu;
	private UIDayBoxContainer dayBoxContainer;
	private ScrollPane mainView;
	private UICommandBox commandBox;
	private UIMessageBox messageBox;
	private UIHelpBox helpBox;
	private ChangeListener<Number> resizeListener;
	private ChangeListener<Boolean> maximizeListener;
	private LogicController logic;
	private Stage primaryStage;
	private UIMainViewType activeMainView;
	private static UIController instance = null;

	private UIController(Stage primaryStage, LogicController logic) {
		this.logic = logic;
		this.primaryStage = primaryStage;
		this.activeMainView = UIMainViewType.TODAY;
		initialize();
	}

	public static synchronized UIController getInstance(Stage primaryStage, LogicController logic) {
		if (instance == null) {
			instance = new UIController(primaryStage, logic);
		}
		return instance;
	}

	private void initialize() {
		initCss();
		initHeader();
		initSideMenu();
		initMainView();
		initCommandBox();
		initMessageBox();
		initHelpBox();
		initRoot();
		initScene();
		initListeners();
		updatePositions();
		initPopulate();
	}

	private void initCss() {
		this.urlCssCommon = loadCss(UIStyle.URL_CSS_COMMON);
		this.urlCssThemeLight = loadCss(UIStyle.URL_CSS_THEME_LIGHT);
		this.urlCssThemeDark = loadCss(UIStyle.URL_CSS_THEME_DARK);
		this.urlCssThemeAqua = loadCss(UIStyle.URL_CSS_THEME_AQUA);
	}

	private String loadCss(String cssUrl) {
		return getClass().getResource(cssUrl).toExternalForm();
	}

	private void initHeader() {
		this.header = new UIHeader();
	}

	private void initSideMenu() {
		this.sideMenu = new UISideMenu(this);
		setActiveMenuButton(this.activeMainView);
		ChangeListener<Toggle> toggleListener = new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				Toggle toggle = new_toggle;
				if (toggle == null) {
					toggle = old_toggle;
				}
				toggle.setSelected(true);
				String userData = getSideMenuUserData();
				if (!userData.equals(UIData.USERDATA_CATEGORY)) {
					showSelectedSideMenu(userData);
				}
			}
		};
		this.sideMenu.getMainViewToggleGroup().selectedToggleProperty().addListener(toggleListener);
	}

	private String getSideMenuUserData() {
		return this.sideMenu.getMainViewToggleGroup().getSelectedToggle().getUserData().toString();
	}

	private void showSelectedSideMenu(String userData) {
		switch (userData) {
			case UIData.USERDATA_TODAY:
				activeMainView = UIMainViewType.TODAY;
				processInput(UIData.CMD_SHOW_TODAY);
				break;
			case UIData.USERDATA_EXTENDED:
				activeMainView = UIMainViewType.EXTENDED;
				processInput(UIData.CMD_SHOW_EXTENDED);
				break;
			case UIData.USERDATA_FLOAT:
				activeMainView = UIMainViewType.FLOAT;
				processInput(UIData.CMD_SHOW_FLOAT);
				break;
			case UIData.USERDATA_ALL:
				activeMainView = UIMainViewType.ALL;
				processInput(UIData.CMD_SHOW_ALL);
				break;
			case UIData.USERDATA_COMPLETED:
				activeMainView = UIMainViewType.COMPLETED;
				processInput(UIData.CMD_SHOW_COMPLETED);
				break;
			default:
				break;
		}
		mainView.setContent(dayBoxContainer.getView());
	}

	private void initMainView() {
		this.dayBoxContainer = new UIDayBoxContainer(this);
		this.mainView = new ScrollPane();
		this.mainView.getStyleClass().add(STYLECLASS_MAIN_VIEW);
		this.mainView.setContent(this.dayBoxContainer.getView());
		this.activeMainView = UIMainViewType.TODAY;
	}

	private void initCommandBox() {
		this.commandBox = new UICommandBox();
	}

	private void initMessageBox() {
		this.messageBox = new UIMessageBox(this.primaryStage);
	}

	private void initHelpBox() {
		this.helpBox = new UIHelpBox();
	}

	private void initRoot() {
		this.root = new BorderPane();
		this.root.setTop(this.header.getView());
		this.root.setLeft(this.sideMenu.getView());
		this.root.setCenter(this.mainView);
		this.root.setBottom(this.commandBox.getView());
	}

	private void initScene() {
		this.scene = new Scene(root, WIDTH_SCENE, HEIGHT_SCENE);
		this.scene.getStylesheets().addAll(this.urlCssCommon, this.urlCssThemeDark);
	}

	private void initListeners() {
		initCommandBoxListeners();
		initSceneListeners();
		initStageListeners();
	}

	private void initCommandBoxListeners() {
		this.commandBox.getCommandTextField().setOnAction((event) -> {
			String commandString = commandBox.getCommandTextField().getText();
			this.commandBox.getCommandTextField().setText(UIData.EMP_STR);
			processInput(commandString);
		});
	}

	private void initSceneListeners() {
		this.resizeListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				updateOnResize();
			}
		};

		this.maximizeListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
				updateOnResize();
			}
		};

		this.scene.heightProperty().addListener(this.resizeListener);
		this.scene.widthProperty().addListener(this.resizeListener);
		this.primaryStage.maximizedProperty().addListener(this.maximizeListener);

		this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				processKeyEvent(keyEvent);
			}
		});
	}

	private void updateOnResize() {
		this.messageBox.updatePosition();
		this.dayBoxContainer.updatePosition(primaryStage.getWidth());
		if (this.helpBox.isShowing()) {
			this.helpBox.updatePosition(primaryStage.getX(), primaryStage.getY(), primaryStage.getWidth(), primaryStage.getHeight());
		}
	}

	private void processKeyEvent(final KeyEvent keyEvent) {
		if (keyEvent.isControlDown()) {
			KeyCode key = keyEvent.getCode();
			switch(key){
				case DIGIT1:
					processInput(UIData.CMD_SHOW_TODAY);
					break;
				case DIGIT2:
					processInput(UIData.CMD_SHOW_EXTENDED);
					break;
				case DIGIT3:
					processInput(UIData.CMD_SHOW_FLOAT);
					break;
				case DIGIT4:
					processInput(UIData.CMD_SHOW_ALL);
					break;
				case DIGIT5:
					processInput(UIData.CMD_SHOW_COMPLETED);
					break;
				default:
					break;
			}
		} else {
			if (!commandBox.isSelected()) {
				commandBox.select();
			}
		}
		keyEvent.consume();
	}

	private void initStageListeners() {
		this.primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
				updateMessageBoxOnFocusChange(newValue);
				updateHelpBoxOnFocusChange(newValue);
			}
		});
	}
	
	private void updateMessageBoxOnFocusChange(Boolean obs){
		if (!obs && this.messageBox.isOn()) {
			this.messageBox.tempHide();
		} else if (obs && this.messageBox.isOn()) {
			this.messageBox.display();
		}
	}
	
	private void updateHelpBoxOnFocusChange(Boolean obs){
		if (!obs && this.helpBox.isOn()) {
			this.helpBox.tempHide();
		} else if (obs && helpBox.isOn()) {
			this.helpBox.show(primaryStage);
		}
	}

	private void initPopulate() {
		refreshCategoryMenuView();
		processInput(UIData.CMD_SHOW_TODAY);
	}

	private void processInput(String s) {
		processLogicAction(logic.processInput(s));
	}

	private void processLogicAction(LogicAction logicAction) {
		Action action = logicAction.getAction();
		switch (action) {
			case ADD_TODAY_TASK:
			case EDIT_TO_TODAY_TASK:
			case SHOW_TODAY_TASK:
				refreshMainView(this.logic.getTaskGroupsToday(), UIMainViewType.TODAY);
				break;
			case ADD_NEXT7DAY_TASK:
			case EDIT_TO_NEXT7DAY_TASK:
			case SHOW_NEXT7DAY_TASK:
				refreshMainView(this.logic.getTaskGroupsNext7Days(), UIMainViewType.EXTENDED);
				break;
			case ADD_FLOATING_TASK:
			case EDIT_TO_FLOATING_TASK:
			case SHOW_FLOATING_TASK:
				refreshMainView(this.logic.getTaskGroupsFloating(), UIMainViewType.FLOAT);
				break;
			case ADD_ALL_TASK:
			case EDIT_TO_ALL_TASK:
			case SHOW_ALL_TASK:
				refreshMainView(this.logic.getTaskGroupsAll(), UIMainViewType.ALL);
				break;
			case SHOW_COMPLETED:
				refreshMainView(this.logic.getTaskGroupsCompleted(), UIMainViewType.COMPLETED);
				break;
			case SHOW_CATEGORY:
				refreshMainView(this.logic.getTaskGroupCategory(), this.logic.getSelectedCategory());
				setActiveCategoryButton(this.logic.getSelectedCategory());
				break;
			case DELETE_CATEGORY:
			case CLEAR_CATEGORY:
				refreshCategoryMenuView();
			case DELETE_TASK:
			case SET_CATEGORY:
			case CLEAR_TASK:
			case MARK_TASK:
			case UNMARK_TASK:
			case UNDO:
			case REDO:
			case REMOVE_CAT_FROM_TASK:
				if (this.activeMainView == UIMainViewType.TODAY) {
					refreshMainView(this.logic.getTaskGroupsToday(), UIMainViewType.TODAY);
				} else if (this.activeMainView == UIMainViewType.EXTENDED) {
					refreshMainView(this.logic.getTaskGroupsNext7Days(), UIMainViewType.EXTENDED);
				} else if (this.activeMainView == UIMainViewType.FLOAT) {
					refreshMainView(this.logic.getTaskGroupsFloating(), UIMainViewType.FLOAT);
				} else if (this.activeMainView == UIMainViewType.ALL) {
					refreshMainView(this.logic.getTaskGroupsAll(), UIMainViewType.ALL);
				} else if (this.activeMainView == UIMainViewType.COMPLETED) {
					refreshMainView(this.logic.getTaskGroupsCompleted(), UIMainViewType.COMPLETED);
				} else if (this.activeMainView == UIMainViewType.CATEGORY) {
					refreshMainView(this.logic.getTaskGroupCategory(), this.logic.getSelectedCategory());
				}
				break;
			case ADD_CATEGORY:
				refreshCategoryMenuView();
				break;
			case SEARCH:
				refreshMainView(this.logic.getSearchTaskGroup(), UIMainViewType.SEARCH);
				break;
			case HELP:
				showHelp();
				break;
			case CHANGE_THEME_DEFAULT:
				changeTheme(UITheme.LIGHT);
				break;
			case CHANGE_THEME_DARK:
				changeTheme(UITheme.DARK);
				break;
			case CHANGE_THEME_AQUA:
				changeTheme(UITheme.AQUA);
				break;
			case CHANGE_THEME_CUSTOM:
				changeTheme(UITheme.CUSTOM);
				break;
			case SET_STORAGE_PATH:
				break;
			case ERROR:
				//
				break;
			default:
				break;
		}

		if (logicAction.hasMessage()) {
			displayMessage(logicAction.getMessage());
		}
	}

	protected void updatePositions() {
		this.dayBoxContainer.updatePosition(this.primaryStage.getWidth());
	}

	protected Scene getScene() {
		return this.scene;
	}

	private void displayMessage(String msg) {
		this.messageBox.show(msg);
		this.messageBox.hide();
	}

	private void changeTheme(UITheme theme) {
		this.scene.getStylesheets().clear();
		this.scene.getStylesheets().add(urlCssCommon);
		switch (theme) {
			case DARK:
				this.scene.getStylesheets().addAll(urlCssThemeDark);
				break;
			case AQUA:
				this.scene.getStylesheets().addAll(urlCssThemeAqua);
				break;
			default:
				this.scene.getStylesheets().addAll(urlCssThemeLight);
				break;
		}
	}

	private void setActiveMenuButton(UIMainViewType mainViewType) {
		switch (mainViewType) {
			case TODAY:
				this.sideMenu.getTodayBtn().setSelected(true);
				break;
			case EXTENDED:
				this.sideMenu.getExtendedBtn().setSelected(true);
				break;
			case FLOAT:
				this.sideMenu.getFloatBtn().setSelected(true);
				break;
			case ALL:
				this.sideMenu.getAllBtn().setSelected(true);
				break;
			case COMPLETED:
				this.sideMenu.getCompletedBtn().setSelected(true);
				break;
			case SEARCH:
				//unsetAllMenuButtons();
				break;
			default:
				break;
		}
	}

	private void setActiveCategoryButton(Category category){
		this.sideMenu.setActiveCategoryButton(category);
	}
	
//	private void unsetAllMenuButtons() {
//		this.sideMenu.getMainViewToggleGroup().getToggles().forEach(toggle -> toggle.setSelected(false));
//	}

	private void refreshMainView(ArrayList<TaskGroup> taskGroupList) {
		this.dayBoxContainer.refresh(taskGroupList);
		this.mainView.setContent(this.dayBoxContainer.getView());
	}

	private void refreshMainView(ArrayList<TaskGroup> taskGroupList, UIMainViewType mainViewType) {
		this.activeMainView = mainViewType;
		setActiveMenuButton(mainViewType);
		refreshMainView(taskGroupList);
	}

	private void refreshMainView(ArrayList<TaskGroup> taskGroupList, Category category) {
		this.activeMainView = UIMainViewType.CATEGORY;
		refreshMainView(taskGroupList);
	}

	private void refreshCategoryMenuView() {
		this.sideMenu.refreshCategoryMenuView(this.logic.getAllCategories());
	}

	private void showHelp() {
		this.helpBox.show(this.primaryStage);
	}

	protected Stage getStage() {
		return this.primaryStage;
	}

	protected double getStageWidth() {
		return this.primaryStage.getWidth();
	}

	protected void markTask(int taskId) {
		processInput(UIData.CMD_MARK + Integer.toString(taskId));
	}

	protected void processCommand(String cmd) {
		processInput(cmd);
	}
	
	public UIMainViewType getActiveViewType() {
		return this.activeMainView;
	}

}
