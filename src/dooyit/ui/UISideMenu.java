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
	
	private HBox todayBtnContent;
	private HBox extendedBtnContent;
	private HBox floatBtnContent;
	private HBox allBtnContent;
	private HBox completedBtnContent;
	
	private Label categoryTitle;
	
	private UICategoryBoxContainer categoryBoxContainer;
	private Logic logic;
	
	public UISideMenu(Logic logic){
		this.logic = logic;
		
		this.menu = new VBox();
		this.menu.setSpacing(5);
		this.menu.getStyleClass().add("menu-view");
		
		this.mainViewToggleGroup = new ToggleGroup();
		
		FxIconicsLabel todayIcon =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_calendar)
                .size(18)
                .color(MaterialColor.GREY_400).build();
		
		Label todayLabel = new Label("Today");
		todayLabel.setFont(Font.font("Euphemia", 14));
		todayLabel.getStyleClass().add("btn-select-label");
		
		this.todayBtnContent = new HBox();
		this.todayBtnContent.setSpacing(8);
		this.todayBtnContent.getChildren().addAll(todayIcon, todayLabel);
		
		this.todayBtn = new ToggleButton();
		this.todayBtn.setGraphic(this.todayBtnContent);
		this.todayBtn.setPrefWidth(180);
		this.todayBtn.getStyleClass().add("btn-select-view");
		this.todayBtn.setToggleGroup(this.mainViewToggleGroup);
		this.todayBtn.setSelected(true);
		this.todayBtn.setUserData("day");
		
		FxIconicsLabel extendedIcon =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_numeric_7_box_multiple_outline)
                .size(18)
                .color(MaterialColor.GREY_400).build();
		
		Label extendedLabel = new Label("Next 7 days");
		extendedLabel.setFont(Font.font("Euphemia", 14));
		extendedLabel.getStyleClass().add("btn-select-label");
		
		this.extendedBtnContent = new HBox();
		this.extendedBtnContent.setSpacing(8);
		this.extendedBtnContent.getChildren().addAll(extendedIcon, extendedLabel);
		
		this.extendedBtn = new ToggleButton();
		this.extendedBtn.setGraphic(this.extendedBtnContent);
		this.extendedBtn.setPrefWidth(180);
		this.extendedBtn.getStyleClass().add("btn-select-view");
		this.extendedBtn.setToggleGroup(this.mainViewToggleGroup);
		this.extendedBtn.setUserData("extended");
		
		FxIconicsLabel floatIcon =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_image_filter_drama)
                .size(18)
                .color(MaterialColor.GREY_400).build();
		
		Label floatLabel = new Label("Float");
		floatLabel.setFont(Font.font("Euphemia", 14));
		floatLabel.getStyleClass().add("btn-select-label");
		
		this.floatBtnContent = new HBox();
		this.floatBtnContent.setSpacing(8);
		this.floatBtnContent.getChildren().addAll(floatIcon, floatLabel);
		
		this.floatBtn = new ToggleButton();
		this.floatBtn.setGraphic(this.floatBtnContent);
		this.floatBtn.setPrefWidth(180);
		this.floatBtn.getStyleClass().add("btn-select-view");
		this.floatBtn.setToggleGroup(this.mainViewToggleGroup);
		this.floatBtn.setUserData("float");
		
		FxIconicsLabel allIcon =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_calendar_multiple)
                .size(18)
                .color(MaterialColor.GREY_400).build();
		
		Label allLabel = new Label("All");
		allLabel.setFont(Font.font("Euphemia", 14));
		allLabel.getStyleClass().add("btn-select-label");
		
		this.allBtnContent = new HBox();
		this.allBtnContent.setSpacing(8);
		this.allBtnContent.getChildren().addAll(allIcon, allLabel);
		
		this.allBtn = new ToggleButton();
		this.allBtn.setGraphic(this.allBtnContent);
		this.allBtn.setPrefWidth(180);
		this.allBtn.getStyleClass().add("btn-select-view");
		this.allBtn.setToggleGroup(this.mainViewToggleGroup);
		this.allBtn.setUserData("all");
		
		FxIconicsLabel completedIcon =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(FxFontCommunity.Icons.cmd_comment_check)
                .size(18)
                .color(MaterialColor.GREY_400).build();
		
		Label completedLabel = new Label("Completed");
		completedLabel.setFont(Font.font("Euphemia", 14));
		completedLabel.getStyleClass().add("btn-select-label");
		
		this.completedBtnContent = new HBox();
		this.completedBtnContent.setSpacing(8);
		this.completedBtnContent.getChildren().addAll(completedIcon, completedLabel);
		
		this.completedBtn = new ToggleButton();
		this.completedBtn.setGraphic(this.completedBtnContent);
		this.completedBtn.setPrefWidth(180);
		this.completedBtn.getStyleClass().add("btn-select-view");
		this.completedBtn.setToggleGroup(this.mainViewToggleGroup);
		this.completedBtn.setUserData("completed");
		
        this.categoryTitle = new Label("CATEGORIES");
        this.categoryTitle.setFont(Font.font("Tahoma", 12));
        this.categoryTitle.getStyleClass().add("category-title");
		
        this.categoryBoxContainer = new UICategoryBoxContainer(new ArrayList<Category>(), this.logic, this.mainViewToggleGroup);
        
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.floatBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());

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

