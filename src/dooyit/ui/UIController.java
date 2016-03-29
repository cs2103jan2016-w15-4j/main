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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.TaskGroup;
import dooyit.logic.api.*;

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
	private WebView webView;
	private WebEngine webEngine;
	private Stage secWindow;
	private LogicController logic;
	private Stage primaryStage;
	private UIMainViewType activeMainView;
	
	public UIController(Stage primaryStage, LogicController logic){
	    this.logic = logic;
	    this.primaryStage = primaryStage;
	    this.activeMainView = UIMainViewType.TODAY;
	    initialize();
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
		this.urlCssCommon = loadCss(UIStyle.URL_CSS_COMMON);
		this.urlCssThemeLight = loadCss(UIStyle.URL_CSS_THEME_LIGHT);
	    this.urlCssThemeDark = loadCss(UIStyle.URL_CSS_THEME_DARK);
	    this.urlCssThemeAqua = loadCss(UIStyle.URL_CSS_THEME_AQUA);
	}
	
	private String loadCss(String cssUrl){
		return getClass().getResource(cssUrl).toExternalForm();
	}
	
	private void initHeader(){
		this.header = new UIHeader();
	}
	
	private void initSideMenu(){
		this.sideMenu = new UISideMenu(this);
		setActiveMenuButton(this.activeMainView);
		ChangeListener<Toggle> toggleListener = new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (new_toggle == null){
					sideMenu.getMainViewToggleGroup().selectToggle(old_toggle);
				}
			    String userData = getSideMenuUserData();
		        showSelectedSideMenu(userData);
	        }
		};
		this.sideMenu.getMainViewToggleGroup().selectedToggleProperty().addListener(toggleListener);
	}
	
	private String getSideMenuUserData(){
		return this.sideMenu.getMainViewToggleGroup().getSelectedToggle().getUserData().toString();
	}
	
	private void showSelectedSideMenu(String userData){
		switch(userData){
			case UIData.USERDATA_TODAY:
		    	activeMainView = UIMainViewType.TODAY;
		    	logic.processInput(UIData.CMD_SHOW_TODAY);
		        break;
		    case UIData.USERDATA_EXTENDED:
		    	activeMainView = UIMainViewType.EXTENDED;
		    	logic.processInput(UIData.CMD_SHOW_EXTENDED);
		        break;
		    case UIData.USERDATA_FLOAT:
		    	activeMainView = UIMainViewType.FLOAT;
		    	logic.processInput(UIData.CMD_SHOW_FLOAT);
		    	break;
		    case UIData.USERDATA_ALL:
		    	activeMainView = UIMainViewType.ALL;
		    	logic.processInput(UIData.CMD_SHOW_ALL);
		    	break;
		    case UIData.USERDATA_COMPLETED:
		    	activeMainView = UIMainViewType.COMPLETED;
		    	logic.processInput(UIData.CMD_SHOW_COMPLETED);
		    	break;
		    case UIData.USERDATA_CATEGORY:
		    	activeMainView = UIMainViewType.CATEGORY;
		    	// call logic processCommand from category box listener
		}
		mainView.setContent(dayBoxContainer.getView());
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
		this.commandBox.getCommandTextField().setOnAction((event) -> {
			String commandString = commandBox.getCommandTextField().getText();
			System.out.println(commandString);
			commandBox.getCommandTextField().setText(UIData.EMP_STR);

			this.logic.processInput(commandString);
			
			if (commandString.equals("help")){
				showHelp();
			}
		});
	}
	
	private void initSceneListeners(){
		this.resizeListener = new ChangeListener<Number>(){
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
	
	private void updateOnResize(){
		this.messageBox.updatePosition();
		this.dayBoxContainer.updatePosition(primaryStage.getWidth());
		if (this.helpBox.isShowing()) {
			this.helpBox.updatePosition(primaryStage.getX(), primaryStage.getY(), primaryStage.getWidth(),
					primaryStage.getHeight());
		}
	}
	
	private void processKeyEvent(final KeyEvent keyEvent){
		if (keyEvent.isControlDown()) {
			KeyCode key = keyEvent.getCode();
			if (key == KeyCode.T){
				logic.processInput(UIData.CMD_SHOW_TODAY);
			} else if (key == KeyCode.E){
				logic.processInput(UIData.CMD_SHOW_EXTENDED);
			} else if (key == KeyCode.F){
				logic.processInput(UIData.CMD_SHOW_FLOAT);
			} else if (key == KeyCode.A){
				logic.processInput(UIData.CMD_SHOW_ALL);
			} else if (key == KeyCode.D){
				logic.processInput(UIData.CMD_SHOW_COMPLETED);
			}
		} else {
			if (!commandBox.isSelected()){
				commandBox.select();
			}
		}
		keyEvent.consume();
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
			case FLOAT:
				this.sideMenu.getMainViewToggleGroup().selectToggle(this.sideMenu.getFloatBtn());
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
	
	protected Stage getStage(){
		return this.primaryStage;
	}
	
	protected double getStageWidth(){
		return this.primaryStage.getWidth();
	}
	
	protected void markTask(int taskId){
		this.logic.processInput(UIData.CMD_MARK + Integer.toString(taskId));
	}

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
		setActiveMenuButton(activeMainView);
	}
	
	public UIMainViewType getActiveViewType(){
		return this.activeMainView;
	}
	
	public void refreshCategoryMenuView(ArrayList<Category> categoryList){
		this.sideMenu.refreshCategoryMenuView(categoryList);
	}
	
	public void showHelp(){
		this.helpBox.show(this.primaryStage);
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

}
