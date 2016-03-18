package dooyit.ui;

// import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.TaskGroup;
import dooyit.logic.core.*;

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
	private UICommandHelper commandHelper;
	private UIMessageBox messageBox;
	private UIHelpBox helpBox;
	private ChangeListener<Number> resizeListener;
	private ChangeListener<Boolean> maximizeListener;

	private WebView webView;
	private WebEngine webEngine;
	private Stage secWindow;

	private Logic logic;
	private Stage primaryStage;
	private UIMainViewType activeMainView;
	
	public UIController(Stage primaryStage, Logic logic){
	    this.logic = logic;
	    this.primaryStage = primaryStage;
	    initialize();
	}
	
	public Scene getScene(){
		return this.scene;
	}
	
	public void displayMessage(String msg){
		this.messageBox.show(msg);
		this.messageBox.hide();
	}
	
	public void changeTheme(UITheme theme){
		this.scene.getStylesheets().clear();
		this.scene.getStylesheets().add(urlCssCommon);
		switch(theme){
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
	
	private void setActiveMenuButton(UIMainViewType mainViewType){
		switch(mainViewType){
			case TODAY:
				this.sideMenu.getMainViewToggleGroup().selectToggle(this.sideMenu.getTodayBtn());
				break;
			case EXTENDED:
				this.sideMenu.getMainViewToggleGroup().selectToggle(this.sideMenu.getExtendedBtn());
				break;
			case ALL:
				this.sideMenu.getMainViewToggleGroup().selectToggle(this.sideMenu.getAllBtn());
				break;
			case COMPLETED:
				this.sideMenu.getMainViewToggleGroup().selectToggle(this.sideMenu.getCompletedBtn());
				break;
			default:
				break;
		}
	}
	
//	private void setActiveMenuButton(Category category){
//		
//	}
	
	/**
	 * Use this to refresh main view with updated tasks
	 */
	public void refreshMainView(ArrayList<TaskGroup> taskGroupList){
		this.dayBoxContainer.refresh(taskGroupList);
		this.mainView.setContent(this.dayBoxContainer.getView());
	}
	
	public void refreshMainView(ArrayList<TaskGroup> taskGroupList, UIMainViewType mainViewType){
		this.activeMainView = mainViewType;
		setActiveMenuButton(mainViewType);
		refreshMainView(taskGroupList);
	}
	
	public void refreshMainView(ArrayList<TaskGroup> taskGroupList, Category category){
		this.activeMainView = UIMainViewType.CATEGORY;
		// set category button to active
		refreshMainView(taskGroupList);
	}
	
	public void setActiveViewType(UIMainViewType activeMainView){
		this.activeMainView = activeMainView;
	}
	
	public UIMainViewType getActiveViewType(){
		return this.activeMainView;
	}
	
	/**
	 * Refreshes categories
	 */
	public void refreshCategoryMenuView(ArrayList<Category> categoryList){
		this.sideMenu.refreshCategoryMenuView(categoryList);
	}
	
	public void showUserGuide(){
		this.webView = new WebView();
		this.webEngine = webView.getEngine();
		this.webEngine.load(getClass().getResource("htdocs/user_guide.html").toExternalForm());
		this.secWindow = new Stage();
        this.secWindow.setTitle("User Guide");
        this.secWindow.setScene(new Scene(this.webView, 600, 600));
        this.secWindow.show();
	}
	
	protected Stage getStage(){
		return this.primaryStage;
	}
	
	protected double getStageWidth(){
		return this.primaryStage.getWidth();
	}
	
	protected void markTask(int taskId){
		this.logic.processCommand(UIData.CMD_MARK + Integer.toString(taskId));
	}
	
	private void initialize(){
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
	}
	
	private void initCss(){
		this.urlCssCommon = getClass().getResource(UIStyle.URL_CSS_COMMON).toExternalForm();
		this.urlCssThemeLight = getClass().getResource(UIStyle.URL_CSS_THEME_LIGHT).toExternalForm();
	    this.urlCssThemeDark = getClass().getResource(UIStyle.URL_CSS_THEME_DARK).toExternalForm();
	    this.urlCssThemeAqua = getClass().getResource(UIStyle.URL_CSS_THEME_AQUA).toExternalForm();
	}
	
	private void initHeader(){
		this.header = new UIHeader();
	}
	
	private void initSideMenu(){
		this.sideMenu = new UISideMenu(this);
		this.sideMenu.getMainViewToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
				if (new_toggle == null){
	            	// do nothing
				} else {
				    String selected = sideMenu.getMainViewToggleGroup().getSelectedToggle().getUserData().toString();
		            switch(selected){
				    	case UIData.USERDATA_TODAY:
	                    	activeMainView = UIMainViewType.TODAY;
	                    	logic.processCommand(UIData.CMD_SHOW_TODAY);
	                        break;
	                    case UIData.USERDATA_EXTENDED:
	                    	activeMainView = UIMainViewType.EXTENDED;
	                    	logic.processCommand(UIData.CMD_SHOW_EXTENDED);
	                        break;
	                    case UIData.USERDATA_FLOAT:
	                    	activeMainView = UIMainViewType.FLOAT;
	                    	logic.processCommand(UIData.CMD_SHOW_FLOAT);
	                    	break;
	                    case UIData.USERDATA_ALL:
	                    	activeMainView = UIMainViewType.ALL;
	                    	logic.processCommand(UIData.CMD_SHOW_ALL);
	                    	break;
	                    case UIData.USERDATA_COMPLETED:
	                    	activeMainView = UIMainViewType.COMPLETED;
	                    	 logic.processCommand(UIData.CMD_SHOW_COMPLETED);
	                    	break;
	                    case UIData.USERDATA_CATEGORY:
	                    	activeMainView = UIMainViewType.CATEGORY;
	                    	// call logic processCommand from category box listener
	                }
                    mainView.setContent(dayBoxContainer.getView());
	            }
	        }
	    });
	}
	
	private void initMainView(){
		this.dayBoxContainer = new UIDayBoxContainer(this);
		this.mainView = new ScrollPane();
		this.mainView.getStyleClass().add(STYLECLASS_MAIN_VIEW);
		this.mainView.setContent(this.dayBoxContainer.getView());
		this.activeMainView = UIMainViewType.TODAY;
	}
	
	private void initCommandBox(){
		this.commandBox = new UICommandBox();
		this.commandHelper = new UICommandHelper(this.primaryStage);
	}
	
	private void initMessageBox(){
		this.messageBox = new UIMessageBox(this.primaryStage);
	}
	
	private void initHelpBox(){
		this.helpBox = new UIHelpBox();
	}
	
	private void initRoot(){
		this.root = new BorderPane();
		this.root.setTop(this.header.getView());
		this.root.setLeft(this.sideMenu.getView());
		this.root.setCenter(this.mainView);
		this.root.setBottom(this.commandBox.getView());
	}
	
	private void initScene(){
		this.scene = new Scene(root, WIDTH_SCENE, HEIGHT_SCENE);
		this.scene.getStylesheets().addAll(this.urlCssCommon, this.urlCssThemeLight);
	}
	
	private void initListeners(){
		initCommandBoxListeners();
		initSceneListeners();
		initStageListeners();
	}
	
	private void initCommandBoxListeners(){
		// Command text field listener
		// When value changes, command helper will be displayed
		this.commandBox.getCommandTextField().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(UIData.EMP_STR) && commandHelper.isShowing()) {
					// commandHelper.hide();
				} else if (!newValue.equals(oldValue) && !commandHelper.isShowing()) {
					// commandHelper.show();
				}
			}
		});

		// Command text field listener
		this.commandBox.getCommandTextField().setOnAction((event) -> {
			String commandString = commandBox.getCommandTextField().getText();
			System.out.println(commandString);
			commandBox.getCommandTextField().setText(UIData.EMP_STR);

			this.logic.processCommand(commandString);

			switch (commandString) {
			// CHANGE: Pass commandString to parser.
			case "change theme dark":
				changeTheme(UITheme.DARK);
				break;
			case "change theme light":
				changeTheme(UITheme.LIGHT);
				break;
			case "change theme aqua":
				changeTheme(UITheme.AQUA);
				break;
			case "help":
				if (helpBox.isShowing()) {
					helpBox.hide();
				} else {
					helpBox.show(primaryStage);
				}
				break;
			case "manual":
				showUserGuide();
				break;
			case "mb":
				displayMessage("hello world");
				break;
			case "hmb":
				this.messageBox.hide();
				break;
			}
		});
	}
	
	private void initSceneListeners(){
		this.resizeListener = new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				messageBox.updatePosition();
				dayBoxContainer.updatePosition(primaryStage.getWidth());
				if (helpBox.isShowing()) {
					helpBox.updatePosition(primaryStage.getX(), primaryStage.getY(), primaryStage.getWidth(),
							primaryStage.getHeight());
				}
			}
		};

		this.maximizeListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue,
					Boolean newValue) {
				System.out.println("MAX");
				messageBox.updatePosition();
				dayBoxContainer.updatePosition(primaryStage.getWidth());
				if (helpBox.isShowing()) {
					helpBox.updatePosition(primaryStage.getX(), primaryStage.getY(), primaryStage.getWidth(),
							primaryStage.getHeight());
				}
			}
		};
		
		this.scene.heightProperty().addListener(this.resizeListener);
		this.scene.widthProperty().addListener(this.resizeListener);
		this.primaryStage.maximizedProperty().addListener(this.maximizeListener);
		
		
		this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.isControlDown()) {
					KeyCode key = keyEvent.getCode();
					if (key == KeyCode.T){
						logic.processCommand(UIData.CMD_SHOW_TODAY);
					} else if (key == KeyCode.E){
						logic.processCommand(UIData.CMD_SHOW_EXTENDED);
					} else if (key == KeyCode.F){
						logic.processCommand(UIData.CMD_SHOW_FLOAT);
					} else if (key == KeyCode.A){
						logic.processCommand(UIData.CMD_SHOW_ALL);
					} else if (key == KeyCode.D){
						logic.processCommand(UIData.CMD_SHOW_COMPLETED);
					}
				} else {
					if (!commandBox.isSelected()){
						commandBox.select();
					}
				}
				keyEvent.consume();
			}
		});
	}
	
	private void initStageListeners(){
		this.primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override 
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue){
				if (!newValue && messageBox.isOn()){
					messageBox.tempHide();
				} else if (newValue && messageBox.isOn()) {
					messageBox.display();
				}
			}
		});
	}
}
