package dooyit.ui;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import dooyit.logic.Logic;
import dooyit.main.*;
import dooyit.logic.*;

public class UIController {
	static final String URL_CSS_COMMON = "common.css";
	static final String URL_CSS_THEME_LIGHT = "theme_light.css";
	static final String URL_CSS_THEME_DARK = "theme_dark.css";

	private String urlCssCommon;
	private String urlCssThemeLight;
	private String urlCssThemeDark;

	private Scene scene;
	private BorderPane root;
	private UIHeader header;
	private UISideMenu sideMenu;
	private UIDayBoxContainer dayBoxContainer;
	private ScrollPane mainView;
	private UICommandBox commandBox;
	private UICommandHelper commandHelper;
	private UIMessageBox messageBox;
	
	private WebView webView;
	private WebEngine webEngine;
	private Stage secWindow;

	private Logic logic;
	private Stage primaryStage;
	
	public UIController(Stage primaryStage, Logic logic){
		this.urlCssCommon = getClass().getResource(URL_CSS_COMMON).toExternalForm();
		this.urlCssThemeLight = getClass().getResource(URL_CSS_THEME_LIGHT).toExternalForm();
	    this.urlCssThemeDark = getClass().getResource(URL_CSS_THEME_DARK).toExternalForm();
		
	    this.logic = logic;
	    this.primaryStage = primaryStage;
	    
	    // Root views
		this.root = new BorderPane();
		
		// Header
		this.header = new UIHeader();
		
		// Side menu
		this.sideMenu = new UISideMenu();
		
		// Day box container
		this.dayBoxContainer = new UIDayBoxContainer(this.logic);
	
		// Main view
		this.mainView = new ScrollPane();
		this.mainView.getStyleClass().add("main-view");
		this.mainView.setContent(this.dayBoxContainer.getView());
		
		// Command view
		this.commandBox = new UICommandBox();
		
		// Command helper
		this.commandHelper = new UICommandHelper(this.primaryStage);
		
		// Message box
		this.messageBox = new UIMessageBox(this.primaryStage);
		
		// Add views to root
		this.root.setTop(this.header.getView());
		this.root.setLeft(this.sideMenu.getView());
		this.root.setCenter(this.mainView);
		this.root.setBottom(this.commandBox.getView());
		
		// Add style to scene
		this.scene = new Scene(root,720,520);
		this.scene.getStylesheets().addAll(this.urlCssCommon, this.urlCssThemeLight);
		
		// Side menu listeners
		this.sideMenu.getMainViewToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle toggle, Toggle new_toggle) {
		            if (new_toggle == null){
		            	// do nothing
		            } else {
		                String selected = sideMenu.getMainViewToggleGroup().getSelectedToggle().getUserData().toString();
                        switch(selected){
		                    case "day":
		                        mainView.setContent(dayBoxContainer.getView());
		                        break;
		                    case "extended":
		                        mainView.setContent(dayBoxContainer.getView());
		                        break;
		                }
		            }
		        }
		    });
		
		// Command text field listener
		// When value changes, command helper will be displayed
		this.commandBox.getCommandTextField().textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	if (newValue.equals("") && commandHelper.isShowing()){
		    		//commandHelper.hide();
		    	} else if (!newValue.equals(oldValue) && !commandHelper.isShowing()){
		    		//commandHelper.show();
		    	}
		    }
		});
		
		// Command text field listener
		this.commandBox.getCommandTextField().setOnAction((event)->{
			String commandString = commandBox.getCommandTextField().getText();
			System.out.println(commandString);
			commandBox.getCommandTextField().setText("");
			
			this.logic.processCommand(commandString);
			
			switch(commandString){
				// CHANGE: Pass commandString to parser.
				case "change theme dark":
					// change to dark theme
					scene.getStylesheets().clear();
					scene.getStylesheets().addAll(urlCssCommon, urlCssThemeDark);
					System.out.println("changed to dark");
					break;
				case "change theme light":
					scene.getStylesheets().clear();
					scene.getStylesheets().addAll(urlCssCommon, urlCssThemeLight);
					System.out.println("changed to light");
					break;
				case "help":
					this.webView = new WebView();
					this.webEngine = webView.getEngine();
					this.webEngine.load(getClass().getResource("htdocs/user_guide.html").toExternalForm());
					this.secWindow = new Stage();
		            this.secWindow.setTitle("User Guide");
		            this.secWindow.setScene(new Scene(this.webView, 600, 600));
		            this.secWindow.show();
					break;
				case "mb":
					displayMessage("hello world");
					break;
				case "hmb":
					this.messageBox.hide();
					break;
			}
		}); 
		
		// Command helper listener
		this.commandHelper.getListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        commandBox.getCommandTextField().setText(newValue);
		        commandBox.getCommandTextField().positionCaret(100);
		    }
		});
	}
	
	public Scene getScene(){
		return this.scene;
	}
	
	public void displayMessage(String msg){
		this.messageBox.show(msg);
	}
	
	/**
	 * Use this to refresh main view with updated tasks
	 */
	public void refreshDayView(ArrayList<TaskGroup> taskGroupList){
		refreshMainView(taskGroupList);
	}
	
	public void refreshExtendedView(ArrayList<TaskGroup> taskGroupList){
		refreshMainView(taskGroupList);
	}
	
	public void refreshAllView(ArrayList<TaskGroup> taskGroupList){
		refreshMainView(taskGroupList);
	}
	
	public void refreshCompletedView(ArrayList<TaskGroup> taskGroupList){
		refreshMainView(taskGroupList);
	}
	
	private void refreshMainView(ArrayList<TaskGroup> taskGroupList){
		this.dayBoxContainer.refresh(taskGroupList);
		this.mainView.setContent(this.dayBoxContainer.getView());
	}
	
	/**
	 * Refreshes categories
	 */
	public void refreshCategoryMenuView(ArrayList<Category> categoryList){
		this.sideMenu.refreshCategoryMenuView(categoryList);
	}
	
}
