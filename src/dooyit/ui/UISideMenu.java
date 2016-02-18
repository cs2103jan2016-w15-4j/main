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
	private ToggleButton next7DaysBtn;
	private Label categoryTitle;
	
	private UICategoryBoxContainer categoryBoxContainer;
	
	public UISideMenu(){
		this.menu = new VBox();
		this.menu.setSpacing(5);
		this.menu.getStyleClass().add("menu-view");
		
		this.mainViewToggleGroup = new ToggleGroup();
		
		this.todayBtn = new ToggleButton("Today");
		this.todayBtn.setFont(Font.font("Tahoma", 14));
		this.todayBtn.setPrefWidth(180);
		this.todayBtn.getStyleClass().add("btn-select-view");
		this.todayBtn.setToggleGroup(mainViewToggleGroup);
		this.todayBtn.setSelected(true);
		this.todayBtn.setUserData("day");
		
		this.next7DaysBtn = new ToggleButton("Next 7 days");
		this.next7DaysBtn.setFont(Font.font("Tahoma", 14));
		this.next7DaysBtn.setPrefWidth(180);
		this.next7DaysBtn.getStyleClass().add("btn-select-view");
		this.next7DaysBtn.setToggleGroup(mainViewToggleGroup);
		this.next7DaysBtn.setUserData("next7days");
		
        this.categoryTitle = new Label("CATEGORIES");
        this.categoryTitle.setFont(Font.font("Tahoma", 12));
        this.categoryTitle.getStyleClass().add("category-title");
		
        this.categoryBoxContainer = new UICategoryBoxContainer(new ArrayList<Category>());
        
		this.menu.getChildren().addAll(this.todayBtn, this.next7DaysBtn, this.categoryTitle, this.categoryBoxContainer.getView());

	}
	
	public VBox getView(){
		return this.menu;
	}
	
	public ToggleButton getTodayBtn(){
		return this.todayBtn;
	}
	
	public ToggleButton getNext7DaysBtn(){
		return this.next7DaysBtn;
	}
	
	public ToggleGroup getMainViewToggleGroup(){
		return this.mainViewToggleGroup;
	}
	
	public void refreshCategoryMenuView(ArrayList<Category> categoryList){
		this.categoryBoxContainer.refresh(categoryList);
	}
	
}

