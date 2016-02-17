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
import javafx.stage.Popup;
import dooyit.logic.Logic;
import dooyit.main.*;;

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
	private UIDayBox dayBox;
	private UIDayBoxContainer dayBoxContainer;
	private ScrollPane mainView;
	private UICommandBox commandBox;
	private Logic logic;
	
	public UIController(Logic logic){
		this.urlCssCommon = getClass().getResource(URL_CSS_COMMON).toExternalForm();
		this.urlCssThemeLight = getClass().getResource(URL_CSS_THEME_LIGHT).toExternalForm();
	    this.urlCssThemeDark = getClass().getResource(URL_CSS_THEME_DARK).toExternalForm();
		this.logic = logic;
	    
	    // Root view
		this.root = new BorderPane();
		
		// Header
		this.header = new UIHeader();
		
		// Side menu
		this.sideMenu = new UISideMenu();
		
		// Day box
		this.dayBox = new UIDayBox(new ArrayList<Task>());
		
		// Extended view
		this.dayBoxContainer = new UIDayBoxContainer(new ArrayList<UIDayBox>());
	
		// Main view
		this.mainView = new ScrollPane();
		this.mainView.getStyleClass().add("main-view");
		this.mainView.setContent(this.dayBox.getView());
		
		// Command view
		this.commandBox = new UICommandBox();
		
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
		                        mainView.setContent(dayBox.getView());
		                        break;
		                    case "next7days":
		                        mainView.setContent(dayBoxContainer.getView());
		                        break;
		                }
		            }
		        }
		    });
		
		// Command text field listener
		commandBox.getCommandTextField().setOnAction((event)->{
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
			}
		}); 
	}
	
	public Scene getScene(){
		return this.scene;
	}
	
	/**
	 * Use this to refresh day view with updated tasks
	 */
	public void refreshDayView(ArrayList<Task> taskList, String dateString){
		this.dayBox = new UIDayBox(taskList);
	}
	
	/**
	 * Use this to refresh extended (next 7 days) view with updated tasks
	 * Parameter will be [DateString,[Task]]
	 */
	public void refreshExtendedView(){
		this.dayBoxContainer = new UIDayBoxContainer(new ArrayList<UIDayBox>());
	}
	
	/**
	 * Refreshes categories
	 */
	public void refreshCategoryMenuView(){
		
	}
	
}
