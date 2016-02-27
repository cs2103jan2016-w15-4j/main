package dooyit.ui;

import java.util.ArrayList;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.cmd.FxFontCommunity;

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
		
		FxIconicsLabel todayLabel =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_calendar)
                .size(18)
                .text(" Today", 14)
                .color(MaterialColor.DEEP_ORANGE_400).build();
		
		this.todayBtn = new ToggleButton();
//		this.todayBtn.setFont(Font.font("Euphemia", 14));
		this.todayBtn.setGraphic(todayLabel);
		this.todayBtn.setPrefWidth(180);
		this.todayBtn.getStyleClass().add("btn-select-view");
		this.todayBtn.setToggleGroup(this.mainViewToggleGroup);
		this.todayBtn.setSelected(true);
		this.todayBtn.setUserData("day");
		
		FxIconicsLabel extendedLabel =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_numeric_7_box_multiple_outline)
                .size(18)
                .text(" Next 7 days", 14)
                .color(MaterialColor.DEEP_ORANGE_400).build();
		
		this.extendedBtn = new ToggleButton();
		this.extendedBtn.setGraphic(extendedLabel);
//		this.extendedBtn.setFont(Font.font("Euphemia", 14));
		this.extendedBtn.setPrefWidth(180);
		this.extendedBtn.getStyleClass().add("btn-select-view");
		this.extendedBtn.setToggleGroup(this.mainViewToggleGroup);
		this.extendedBtn.setUserData("extended");
		
		FxIconicsLabel allLabel =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_calendar_multiple)
                .size(18)
                .text(" All", 14)
                .color(MaterialColor.DEEP_ORANGE_400).build();
		
		this.allBtn = new ToggleButton();
		this.allBtn.setGraphic(allLabel);
//		this.allBtn.setFont(Font.font("Euphemia", 14));
		this.allBtn.setPrefWidth(180);
		this.allBtn.getStyleClass().add("btn-select-view");
		this.allBtn.setToggleGroup(this.mainViewToggleGroup);
		this.allBtn.setUserData("all");
		
		FxIconicsLabel completedLabel =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_comment_check)
                .size(18)
                .text(" Completed", 14)
                .color(MaterialColor.DEEP_ORANGE_400).build();
		
		this.completedBtn = new ToggleButton();
		this.completedBtn.setGraphic(completedLabel);
//		this.completedBtn.setFont(Font.font("Euphemia", 14));
		this.completedBtn.setPrefWidth(180);
		this.completedBtn.getStyleClass().add("btn-select-view");
		this.completedBtn.setToggleGroup(this.mainViewToggleGroup);
		this.completedBtn.setUserData("completed");
		
        this.categoryTitle = new Label("CATEGORIES");
        this.categoryTitle.setFont(Font.font("Tahoma", 12));
        this.categoryTitle.getStyleClass().add("category-title");
		
        this.categoryBoxContainer = new UICategoryBoxContainer(new ArrayList<Category>(), this.logic, this.mainViewToggleGroup);
        
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

