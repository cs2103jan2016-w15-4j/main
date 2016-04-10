// @@author A0124278A
package dooyit.ui;

import java.net.URL;
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
 * The <tt>UIController</tt> class contains methods to initialize all other classes belonging to the UI.
 * This is the only class from <tt>dooyit.ui</tt> that communicates directly with the Logic component. 
 * All communication between UI and Logic happens through it. 
 * The <tt>UIController</tt> class follows the Singleton design pattern, hence its constructor method is private.
 * It can be constructed using the <tt>getInstance</tt> method.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class UIController {
	static final int WIDTH_SCENE = 720;
	static final int HEIGHT_SCENE = 580;
	static final String STYLECLASS_MAIN_VIEW = UIStyle.MAIN_VIEW;
	static final String SPACE = " ";
	static final String ESCAPED_SPACE = "%20";
	static final String PATH_PREPEND = "file://";

	private String urlCssCommon;
	private String urlCssThemeLight;
	private String urlCssThemeDark;
	private String urlCssThemeAqua;
	private URL urlCssThemeCustom;
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
	
	/**
	 * This is the private constructor method for <tt>UIController</tt>.
	 * It is used by the <tt>getInstance</tt> method.
	 * @param primaryStage This is the primary stage of the application.
	 */
	private UIController(Stage primaryStage) {
		this.logic = new LogicController();
		this.primaryStage = primaryStage;
		this.activeMainView = UIMainViewType.TODAY;
		initialize();
	}

	/**
	 * This method is used to create an instance of <tt>UIController</tt> and return it, 
	 * if it has not already been created. 
	 * If an instance of <tt>UIController</tt> already exists, 
	 * no new instance will be created and the existing <tt>UIController</tt> will be returned instead. 
	 * This method is used by the <tt>Main</tt> class.
	 * @param primaryStage This is the primary stage of the application.
	 * @return The <tt>UIController</tt> instance.
	 */
	public static synchronized UIController getInstance(Stage primaryStage) {
		if (instance == null) {
			instance = new UIController(primaryStage);
		}
		return instance;
	}

	/**
	 * This method is used to initialize the <tt>UIController</tt> class.
	 * It is used by the constructor.
	 */
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

	/**
	 * This method is used to initialize the file paths of the CSS for the application skins.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCss() {
		this.urlCssCommon = loadCss(UIStyle.URL_CSS_COMMON);
		this.urlCssThemeLight = loadCss(UIStyle.URL_CSS_THEME_LIGHT);
		this.urlCssThemeDark = loadCss(UIStyle.URL_CSS_THEME_DARK);
		this.urlCssThemeAqua = loadCss(UIStyle.URL_CSS_THEME_AQUA);
		this.urlCssThemeCustom = getClass().getResource(UIStyle.URL_CSS_THEME_CUSTOM);
		this.logic.setDefaultCustomCss(urlCssThemeCustom);
	}

	/**
	 * This method is used to retrieve the absolute pathname of a CSS file given its relative pathname. 
	 * It is used by the <tt>initialize</tt> method.
	 * @param cssUrl The relative pathname to be resolved.
	 * @return The absolute pathname of <tt>cssUrl</tt>.
	 */
	private String loadCss(String cssUrl) {
		return getClass().getResource(cssUrl).toExternalForm();
	}

	/**
	 * This method is used to initialize the UI header. 
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initHeader() {
		this.header = new UIHeader();
	}

	/**
	 * This method is used to initialize the side menu of the UI. 
	 * It is used by the <tt>initialize</tt> method.
	 */
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
				showSelectedSideMenu(userData);
			}
		};
		this.sideMenu.getMainViewToggleGroup().selectedToggleProperty().addListener(toggleListener);
	}

	/**
	 * This method is used to retrieve the user data of the selected menu button. 
	 * It is called by the <tt>initSideMenu</tt> method.
	 * @return The user data of selected menu button.
	 */
	private String getSideMenuUserData() {
		return this.sideMenu.getMainViewToggleGroup().getSelectedToggle().getUserData().toString();
	}

	/**
	 * This method is used to select the menu button which corresponds to <tt>userData</tt> 
	 * and display the relevant view for it.
	 * It is used by the <tt>initSideMenu</tt> method.
	 * @param userData The user data of the selected menu button.
	 */
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
			case UIData.USERDATA_CATEGORY:
				activeMainView = UIMainViewType.CATEGORY;
				processInput(UIData.CMD_SHOW_CAT + getSelectedCategoryName());
				break;
			default:
				break;
		}
		mainView.setContent(dayBoxContainer.getView());
	}

	/**
	 * This method is used to initialize the main view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initMainView() {
		this.dayBoxContainer = new UIDayBoxContainer(this);
		this.mainView = new ScrollPane();
		this.mainView.getStyleClass().add(STYLECLASS_MAIN_VIEW);
		this.mainView.setContent(this.dayBoxContainer.getView());
		this.activeMainView = UIMainViewType.TODAY;
	}

	/**
	 * This method is used to initialize the command box view. 
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCommandBox() {
		this.commandBox = new UICommandBox();
	}

	/**
	 * This method is used to initialize the message box, 
	 * which is used to pass messages from the application to the user.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initMessageBox() {
		this.messageBox = new UIMessageBox(this.primaryStage);
	}

	/**
	 * This method is used to initialize the help box, 
	 * which is displayed when the user passes a help command.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initHelpBox() {
		this.helpBox = new UIHelpBox();
	}

	/**
	 * This method is used to initialize the root view. 
	 * The root view is the underlying view that contains all the other views. 
	 * Make sure this method is only called after all other views have been initialized.
	 * This method is used by the <tt>initialize</tt> method.
	 */
	private void initRoot() {
		this.root = new BorderPane();
		this.root.setTop(this.header.getView());
		this.root.setLeft(this.sideMenu.getView());
		this.root.setCenter(this.mainView);
		this.root.setBottom(this.commandBox.getView());
	}

	/**
	 * This method is used to initialize the scene of the application.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initScene() {
		this.scene = new Scene(root, WIDTH_SCENE, HEIGHT_SCENE);
		this.scene.getStylesheets().addAll(this.urlCssCommon, this.urlCssThemeLight);
	}

	/**
	 * This method is used to initialize event listeners for the UI.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initListeners() {
		initCommandBoxListeners();
		initSceneListeners();
		initStageListeners();
	}

	/**
	 * This method is used to initialize event listeners for the command box view. 
	 * It is used by the <tt>initListeners</tt> method.
	 */
	private void initCommandBoxListeners() {
		this.commandBox.getCommandTextField().setOnAction((event) -> {
			String commandString = commandBox.getCommandTextField().getText();
			this.commandBox.getCommandTextField().setText(UIData.EMP_STR);
			this.commandBox.updateHistory(commandString);
			processInput(commandString);
		});
		
		this.commandBox.getCommandTextField().setOnKeyPressed(new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent keyEvent) {
	        	processCommandBoxKeyEvent(keyEvent);
	        }
	    });
	}
	
	/**
	 * This method is used to process a <tt>KeyEvent</tt> for the command box view.
	 * It is used by the <tt>initCommandBoxListeners</tt> method.
	 * @param keyEvent The <tt>KeyEvent</tt> that has occurred.
	 */
	private void processCommandBoxKeyEvent(KeyEvent keyEvent) {
		KeyCode key = keyEvent.getCode();
    	switch(key){
        	case UP:
        		commandBox.showPrevHistory();
        		break;
        	case DOWN:
        		commandBox.showNextHistory();
        		break;
        	default:
        		break;
		}
	}

	/**
	 * This method is used to initialize the event listeners for the scene of the application.
	 * It is used by the <tt>initListeners</tt> method.
	 */
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

	/**
	 * This method is used to dynamically adjust the message box and main view layouts 
	 * when the scene has been resized.
	 * It is used by the <tt>initSceneListeners</tt> method.
	 */
	private void updateOnResize() {
		this.messageBox.updatePosition();
		this.dayBoxContainer.updatePosition(primaryStage.getWidth());
		if (this.helpBox.isShowing()) {
			this.helpBox.updatePosition(primaryStage.getX(), primaryStage.getY(), primaryStage.getWidth(), primaryStage.getHeight());
		}
	}

	/**
	 * This method is used to process a <tt>KeyEvent</tt> for an event listener of the scene.
	 * @param keyEvent The <tt>KeyEvent</tt> which occurred.
	 */
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
				case U:
				case PAGE_UP:
					if (mainView.getVvalue() > mainView.getVmin() + 0.1){
						mainView.setVvalue(mainView.getVvalue() - 0.1);
					}
					break;
				case J:
				case PAGE_DOWN:
					if (mainView.getVvalue() < mainView.getVmax() + 0.1){
						mainView.setVvalue(mainView.getVvalue() + 0.1);
					}
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

	/**
	 * This method is used to initialize the event listeners for the stage of the application.
	 * It is used by the <tt>initListeners</tt> method.
	 */
	private void initStageListeners() {
		this.primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
				updateMessageBoxOnFocusChange(newValue);
				updateHelpBoxOnFocusChange(newValue);
			}
		});
	}
	
	/**
	 * This method is used to update the visibility of the message box when 
	 * the stage of the application changes focus.
	 * @param obs The <tt>boolean</tt> for the stage's focus attribute.
	 * It is used by the <tt>initStageListeners</tt> method.
	 */
	private void updateMessageBoxOnFocusChange(Boolean obs) {
		if (!obs && this.messageBox.isOn()) {
			this.messageBox.tempHide();
		} else if (obs && this.messageBox.isOn()) {
			this.messageBox.display();
		}
	}
	
	/**
	 * This method is used to update the visibility of the help box when 
	 * the stage of the application changes focus.
	 * @param obs The <tt>boolean</tt> for the stage's focus attribute.
	 * It is used by the <tt>initStageListeners</tt> method.
	 */
	private void updateHelpBoxOnFocusChange(Boolean obs) {
		if (!obs && this.helpBox.isOn()) {
			this.helpBox.tempHide();
		} else if (obs && helpBox.isOn()) {
			this.helpBox.show(primaryStage);
		}
	}

	/**
	 * This method is used to populate the UI at application startup.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initPopulate() {
		refreshCategoryMenuView();
		processInput(UIData.CMD_SHOW_TODAY);
	}

	/**
	 * This method is used to process a user's input string by passing it to the Logic component. 
	 * @param s The user's input string.
	 */
	private void processInput(String s) {
		processLogicAction(logic.processInput(s));
	}
	
	/**
	 * This method is used to get the name of the <tt>Category</tt> whose menu button is selected.
	 * @return The name of the <tt>Category</tt> whose menu button is selected.
	 */
	private String getSelectedCategoryName() {
		return this.sideMenu.getSelectedCategoryName();
	}

	/**
	 * This method is used to process the <tt>LogicAction</tt> that is 
	 * returned by the Logic component's <tt>processInput</tt> method. 
	 * It is used by the <tt>processInput</tt> method.
	 * @param logicAction The <tt>LogicAction</tt> that is returned by Logic component's <tt>processInput</tt> method.
	 */
	private void processLogicAction(LogicAction logicAction) {
		Action action = logicAction.getAction();
		switch (action) {
			case ADD_TODAY_TASK:
			case ADD_ALL_TASK:
				if (this.activeMainView == UIMainViewType.TODAY) {
					refreshMainView(this.logic.getTaskGroupsToday(), UIMainViewType.TODAY);
				} else if (this.activeMainView == UIMainViewType.EXTENDED) {
					refreshMainView(this.logic.getTaskGroupsNext7Days(), UIMainViewType.EXTENDED);
				}
				break;
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
			case EDIT_CATEGORY:
			case ADD_N_SET_CATEGORY:
			case UNDO:
			case REDO:
				refreshCategoryMenuView();
			case DELETE_TASK:
			case SET_CATEGORY:
			case CLEAR_TASK:
			case MARK_TASK:
			case UNMARK_TASK:
			case REMOVE_CAT_FROM_TASK:
			case EDIT_NAME:
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
				displayMessage(logicAction.getMessage(), UIMessageType.ERROR);
				break;
			default:
				break;
		}

		if (hasNonErrorMessage(logicAction)) {
			displayMessage(logicAction.getMessage(), UIMessageType.DEFAULT);
		}
	}
	
	/**
	 * This method is used to check that an <tt>LogicAction</tt> has an non-error message.
	 * It is used by the <tt>processLogicAction</tt> method.
	 * @param logicAction The <tt>LogicAction</tt> to be checked.
	 * @return <tt>True</tt> if <tt>logicAction</tt> has an non-error message, <tt>False</tt> otherwise.
	 */
	private boolean hasNonErrorMessage(LogicAction logicAction) {
		return logicAction.hasMessage() && logicAction.getAction() != Action.ERROR;
	}

	/**
	 * This method is used to update the layout of the main view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void updatePositions() {
		this.dayBoxContainer.updatePosition(this.primaryStage.getWidth());
	}

	/**
	 * This method is used to populate the message box with a message and display it to the user.
	 * It is used by the <tt>processLogicAction</tt> method.
	 * @param msg The message to be displayed.
	 * @param msgType The <tt>UIMessageType</tt> of the message to be displayed.
	 */
	private void displayMessage(String msg, UIMessageType msgType) {
		this.messageBox.show(msg, msgType);
		this.messageBox.hide();
	}

	/**
	 * This method is used to change the application skin.
	 * It is used by the <tt>processLogicAction</tt> method.
	 * @param theme The <tt>UITheme</tt> to change the application skin into.
	 */
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
			case CUSTOM:
				this.scene.getStylesheets().addAll(getCustomCssPath());
				break;
			default:
				this.scene.getStylesheets().addAll(urlCssThemeLight);
				break;
		}
	}
	
	/**
	 * This method is used to get the absolute pathname for the custom CSS file.
	 * It is used by the <tt>changeTheme</tt> method.
	 * @return The absolute pathname for the custom CSS file.
	 */
	private String getCustomCssPath() {
		return PATH_PREPEND + this.logic.getCssPath().replace(SPACE, ESCAPED_SPACE);
	}

	/**
	 * This method is used to select a menu button.
	 * It is used by the <tt>refreshMainView</tt> methods.
	 * @param mainViewType The <tt>UIMainViewType</tt> of the menu button to be selected.
	 */
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
				break;
			default:
				break;
		}
	}
	
	/**
	 * This method is used to select a category menu button.
	 * It is used by the <tt>processLogicAction</tt> method.
	 * @param category The <tt>Category</tt> whose menu button is to be selected.
	 */
	private void setActiveCategoryButton(Category category) {
		this.sideMenu.setActiveCategoryButton(category);
	}

	/**
	 * This method is used to update the main view of the UI.
	 * It is used by the <tt>processLogicAction</tt> method.
	 * @param taskGroupList The <tt>ArrayList</tt> of <tt>TaskGroup</tt> objects 
	 * to populate the main view with.
	 */
	private void refreshMainView(ArrayList<TaskGroup> taskGroupList) {
		this.dayBoxContainer.refresh(taskGroupList);
		this.mainView.setContent(this.dayBoxContainer.getView());
	}

	/**
	 * This method is used to update the main view and selected menu button of the UI.
	 * It is used by the <tt>processLogicAction</tt> method. 
	 * @param taskGroupList The <tt>ArrayList</tt> of <tt>TaskGroup</tt> objects 
	 * to populate the main view with.
	 * @param mainViewType The <tt>UIMainViewType</tt> of the menu button to be selected.
	 */
	private void refreshMainView(ArrayList<TaskGroup> taskGroupList, UIMainViewType mainViewType) {
		this.activeMainView = mainViewType;
		setActiveMenuButton(mainViewType);
		refreshMainView(taskGroupList);
	}

	/**
	 * This method is used to update the main view and selected category menu button of the UI.
	 * It is used by the <tt>processLogicAction</tt> method. 
	 * @param taskGroupList The <tt>ArrayList</tt> of <tt>TaskGroup</tt> objects 
	 * to populate the main view with.
	 * @param category The <tt>Category</tt> whose menu button is to be selected.
	 */
	private void refreshMainView(ArrayList<TaskGroup> taskGroupList, Category category) {
		this.activeMainView = UIMainViewType.CATEGORY;
		refreshMainView(taskGroupList);
	}

	/**
	 * This method is used to update the category menu buttons.
	 * It is used by <tt>initPopulate</tt> and <tt>processLogicAction</tt> methods.
	 */
	private void refreshCategoryMenuView() {
		this.sideMenu.refreshCategoryMenuView(this.logic.getAllCategories());
	}

	/**
	 * This method is used to display the help box.
	 * It is used by the <tt>processLogicAction</tt> method.
	 */
	private void showHelp() {
		this.helpBox.show(this.primaryStage);
	}

	/**
	 * This method is used to retrieve the primary stage of the application scene.
	 * @return The primary stage of the application scene.
	 */
	protected Stage getStage() {
		return this.primaryStage;
	}

	/**
	 * This method is used to retrieve the width of the primary stage of the application scene.
	 * @return The width of the primary stage of the application scene.
	 */
	protected double getStageWidth() {
		return this.primaryStage.getWidth();
	}

	/**
	 * This method is used to mark a <tt>Task</tt> as completed.
	 * @param taskId The displayed id of the <tt>Task</tt> to be marked.
	 */
	protected void markTask(int taskId) {
		processInput(UIData.CMD_MARK + Integer.toString(taskId));
	}
	
	/**
	 * This method is used to unmark a previously marked <tt>Task</tt>.
	 * @param taskId The displayed id of the <tt>Task</tt> to be unmarked.
	 */
	protected void unmarkTask(int taskId) {
		processInput(UIData.CMD_UNMARK + Integer.toString(taskId));
	}

	/**
	 * This method is used to process a command string.
	 * @param cmd The command string to be processed.
	 */
	protected void processCommand(String cmd) {
		processInput(cmd);
	}
	
	/**
	 * This method is used to retrieve the currently active <tt>UIMainViewType</tt> of the UI.
	 * @return The currently active <tt>UIMainViewType</tt> of the UI.
	 */
	public UIMainViewType getActiveViewType() {
		return this.activeMainView;
	}
	
	/**
	 * This method is used to retrieve the scene of the application.
	 * @return The scene of the application.
	 */
	protected Scene getScene() {
		return this.scene;
	}
}
