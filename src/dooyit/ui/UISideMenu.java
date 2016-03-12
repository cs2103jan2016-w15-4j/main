package dooyit.ui;

import java.util.ArrayList;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.cmd.FxFontCommunity;

import dooyit.logic.core.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UISideMenu {
	
	private VBox menu;
	private ToggleGroup mainViewToggleGroup;
	private ToggleButton todayBtn;
	private ToggleButton extendedBtn;
	private ToggleButton floatBtn;
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
		
		this.todayBtn = getMenuButton("Today", "day", FxFontCommunity.Icons.cmd_calendar);
		this.extendedBtn = getMenuButton("Next 7 days", "extended", FxFontCommunity.Icons.cmd_numeric_7_box_multiple_outline);
		this.floatBtn = getMenuButton("Float", "float", FxFontCommunity.Icons.cmd_image_filter_drama);
		this.allBtn = getMenuButton("All", "all", FxFontCommunity.Icons.cmd_calendar_multiple);
		this.completedBtn = getMenuButton("Completed", "completed", FxFontCommunity.Icons.cmd_comment_check);
		
        this.categoryTitle = new Label("CATEGORIES");
        this.categoryTitle.setFont(Font.font("Tahoma", 12));
        this.categoryTitle.getStyleClass().add("category-title");
		
        this.categoryBoxContainer = new UICategoryBoxContainer(new ArrayList<Category>(), this.logic, this.mainViewToggleGroup);
        
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.floatBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());

	}
	
	private ToggleButton getMenuButton(String title, String userData, FxFontCommunity.Icons icon){
		FxIconicsLabel btnIcon =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(icon)
                .size(18)
                .color(MaterialColor.GREY_400).build();
		
		Label btnLabel = new Label(title);
		btnLabel.setFont(Font.font("Euphemia", 14));
		btnLabel.getStyleClass().add("btn-select-label");
		
		HBox btnContent = new HBox();
		btnContent.setSpacing(8);
		btnContent.getChildren().addAll(btnIcon, btnLabel);
		
		ToggleButton menuBtn = new ToggleButton();
		menuBtn.setGraphic(btnContent);
		menuBtn.setPrefWidth(180);
		menuBtn.getStyleClass().add("btn-select-view");
		menuBtn.setToggleGroup(this.mainViewToggleGroup);
		menuBtn.setUserData(userData);
		
		return menuBtn;
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
	
	public ToggleButton getAllBtn(){
		return this.allBtn;
	}
	
	public ToggleButton getCompletedBtn(){
		return this.completedBtn;
	}
	
	public ToggleGroup getMainViewToggleGroup(){
		return this.mainViewToggleGroup;
	}
	
	public void refreshCategoryMenuView(ArrayList<Category> categoryList){
		this.categoryBoxContainer.refresh(categoryList);
	}
	
}

