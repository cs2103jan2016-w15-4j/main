package dooyit.ui;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import dooyit.logic.*;

public class UISideMenu {
	
	private VBox menu;
	private ToggleGroup mainViewToggleGroup;
	private ToggleButton todayBtn;
	private ToggleButton extendedBtn;
	private ToggleButton allBtn;
	private ToggleButton completedBtn;
	
	private Label categoryTitle;
	
	private UICategoryBoxContainer categoryBoxContainer;
	private Logic logic;
	
	public UISideMenu(Logic logic){
		this.logic = logic;
		
		this.menu = new VBox();
		this.menu.setSpacing(5);
		this.menu.getStyleClass().add("menu-view");
		
		this.mainViewToggleGroup = new ToggleGroup();
		
		this.todayBtn = new ToggleButton("Today");
		this.todayBtn.setFont(Font.font("Euphemia", 14));
		this.todayBtn.setPrefWidth(180);
		this.todayBtn.getStyleClass().add("btn-select-view");
		this.todayBtn.setToggleGroup(mainViewToggleGroup);
		this.todayBtn.setSelected(true);
		this.todayBtn.setUserData("day");
		
		this.extendedBtn = new ToggleButton("Next 7 days");
		this.extendedBtn.setFont(Font.font("Euphemia", 14));
		this.extendedBtn.setPrefWidth(180);
		this.extendedBtn.getStyleClass().add("btn-select-view");
		this.extendedBtn.setToggleGroup(mainViewToggleGroup);
		this.extendedBtn.setUserData("extended");
		
		this.allBtn = new ToggleButton("All");
		this.allBtn.setFont(Font.font("Euphemia", 14));
		this.allBtn.setPrefWidth(180);
		this.allBtn.getStyleClass().add("btn-select-view");
		this.allBtn.setToggleGroup(mainViewToggleGroup);
		this.allBtn.setUserData("all");
		
		this.completedBtn = new ToggleButton("Completed");
		this.completedBtn.setFont(Font.font("Euphemia", 14));
		this.completedBtn.setPrefWidth(180);
		this.completedBtn.getStyleClass().add("btn-select-view");
		this.completedBtn.setToggleGroup(mainViewToggleGroup);
		this.completedBtn.setUserData("completed");
		
        this.categoryTitle = new Label("CATEGORIES");
        this.categoryTitle.setFont(Font.font("Tahoma", 12));
        this.categoryTitle.getStyleClass().add("category-title");
		
        this.categoryBoxContainer = new UICategoryBoxContainer(new ArrayList<Category>(), this.logic);
        
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());

	}
	
	public VBox getView(){
		return this.menu;
	}
	
	public ToggleButton getTodayBtn(){
		return this.todayBtn;
	}
	
	public ToggleButton getExtendedBtn(){
		return this.extendedBtn;
	}
	
	public ToggleGroup getMainViewToggleGroup(){
		return this.mainViewToggleGroup;
	}
	
	public void refreshCategoryMenuView(ArrayList<Category> categoryList){
		this.categoryBoxContainer.refresh(categoryList);
	}
	
}

