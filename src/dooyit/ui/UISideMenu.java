package dooyit.ui;

import java.util.ArrayList;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.cmd.FxFontCommunity;

import dooyit.common.datatype.Category;
import dooyit.logic.core.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UISideMenu{
	
	private static final String LABEL_TODAY = "Today";
	private static final String LABEL_EXTENDED = "Next 7 days";
	private static final String LABEL_FLOAT = "Float";
	private static final String LABEL_ALL = "All";
	private static final String LABEL_COMPLETED = "Completed";
	
	private static final String STYLECLASS_MENU = UIStyle.MENU_VIEW;
	private static final String LABEL_CATEGORY_TITLE = "CATEGORIES";
	private static final Font FONT_CATEGORY_TITLE = UIFont.TAHOMA_S;
	private static final String STYLECLASS_CATEGORY_TITLE = UIStyle.CATEGORY_TITLE;
	private static final Font FONT_BTN_LABEL = UIFont.SEGOE_M;
	private static final String STYLECLASS_BTN_LABEL = UIStyle.BTN_SELECT_LABEL;
	private static final String COLOR_BTN_ICON = MaterialColor.GREY_400;
	private static final int SPACING_BTN_CONTENT = 8;
	private static final String STYLECLASS_MENU_BTN = UIStyle.BTN_SELECT_VIEW;
	private static final int PREFWIDTH_MENU_BTN = 180;
	
	private VBox menu;
	private ToggleGroup mainViewToggleGroup;
	private ToggleButton todayBtn;
	private ToggleButton extendedBtn;
	private ToggleButton floatBtn;
	private ToggleButton allBtn;
	private ToggleButton completedBtn;
	private Label categoryTitle;
	private UICategoryBoxContainer categoryBoxContainer;
	private UIController parent;
	
	public UISideMenu(UIController parent){
		this.parent = parent;
		initialize();
	}
	
	private void initialize(){
		this.mainViewToggleGroup = new ToggleGroup();
		initMenuButtons();
		initCategoryButtons();
		initMenu();
	}
	
	private void initMenuButtons(){
		this.todayBtn = getMenuButton(LABEL_TODAY, UIData.USERDATA_TODAY, FxFontCommunity.Icons.cmd_calendar);
		this.extendedBtn = getMenuButton(LABEL_EXTENDED, UIData.USERDATA_EXTENDED, FxFontCommunity.Icons.cmd_numeric_7_box_multiple_outline);
		this.floatBtn = getMenuButton(LABEL_FLOAT, UIData.USERDATA_FLOAT, FxFontCommunity.Icons.cmd_image_filter_drama);
		this.allBtn = getMenuButton(LABEL_ALL, UIData.USERDATA_ALL, FxFontCommunity.Icons.cmd_calendar_multiple);
		this.completedBtn = getMenuButton(LABEL_COMPLETED, UIData.USERDATA_COMPLETED, FxFontCommunity.Icons.cmd_comment_check);
	}
	
	private void initCategoryButtons(){
		 this.categoryTitle = new Label(LABEL_CATEGORY_TITLE);
	     this.categoryTitle.setFont(FONT_CATEGORY_TITLE);
	     this.categoryTitle.getStyleClass().add(STYLECLASS_CATEGORY_TITLE);	
	     this.categoryBoxContainer = new UICategoryBoxContainer(this, new ArrayList<Category>());
	}
	
	private void initMenu(){
		this.menu = new VBox();
		this.menu.setSpacing(5);
		this.menu.getStyleClass().add(STYLECLASS_MENU);
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.floatBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());
	}
	
	private ToggleButton getMenuButton(String title, String userData, FxFontCommunity.Icons icon){
		FxIconicsLabel btnIcon = getMenuBtnIcon(icon);
		Label btnLabel = getMenuBtnLabel(title); 
		return makeMenuButton(btnIcon, btnLabel, userData);
	}
	
	private FxIconicsLabel getMenuBtnIcon(FxFontCommunity.Icons icon){
		return (FxIconicsLabel) new FxIconicsLabel
        .Builder(icon)
        .size(18)
        .color(COLOR_BTN_ICON).build();
	}
	
	private Label getMenuBtnLabel(String title){
		Label btnLabel = new Label(title);
		btnLabel.setFont(FONT_BTN_LABEL);
		btnLabel.getStyleClass().add(STYLECLASS_BTN_LABEL);
		return btnLabel;
	}
	
	private ToggleButton makeMenuButton(FxIconicsLabel icon, Label btnLabel, String userData){
		HBox btnContent = new HBox();
		btnContent.setSpacing(SPACING_BTN_CONTENT);
		btnContent.getChildren().addAll(icon, btnLabel);
		ToggleButton menuBtn = new ToggleButton();
		menuBtn.setGraphic(btnContent);
		menuBtn.setPrefWidth(PREFWIDTH_MENU_BTN);
		menuBtn.getStyleClass().add(STYLECLASS_MENU_BTN);
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

