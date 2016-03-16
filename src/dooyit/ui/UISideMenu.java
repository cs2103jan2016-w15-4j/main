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

public class UISideMenu {
	
	private static final String LABEL_TODAY = "Today";
	private static final String LABEL_EXTENDED = "Next 7 days";
	private static final String LABEL_FLOAT = "Float";
	private static final String LABEL_ALL = "All";
	private static final String LABEL_COMPLETED = "Completed";
	
	private static final String USERDATA_TODAY = "day";
	private static final String USERDATA_EXTENDED = "extended";
	private static final String USERDATA_FLOAT = "float";
	private static final String USERDATA_ALL = "all";
	private static final String USERDATA_COMPLETED = "completed";
	
	private static final String STYLECLASS_MENU = "menu-view";
	
	private static final String LABEL_CATEGORY_TITLE = "CATEGORIES";
	private static final String FONT_CATEGORY_TITLE = "Tahoma";
	private static final int FONTSIZE_CATEGORY_TITLE = 12;
	private static final String STYLECLASS_CATEGORY_TITLE = "category-title";
	
	private static final String FONT_BTN_LABEL = "Segoe UI";
	private static final int FONTSIZE_BTN_LABEL = 14;
	private static final String STYLECLASS_BTN_LABEL = "btn-select-label";
	private static final String COLOR_BTN_ICON = MaterialColor.GREY_400;
	private static final int SPACING_BTN_CONTENT = 8;
	private static final String STYLECLASS_MENU_BTN = "btn-select-view";
	private static final int PREFWIDTH_MENU_BTN = 180;
	
	private Font customBtnLabelFont;
	private boolean isLoadedCustomBtnLabelFont;
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
		this.menu.getStyleClass().add(STYLECLASS_MENU);
		
		try {
			this.customBtnLabelFont = Font.loadFont(getClass().getResourceAsStream("fonts/SF-Regular.ttf"), 15);
			this.isLoadedCustomBtnLabelFont = true;
		} catch(Exception e){
			this.isLoadedCustomBtnLabelFont = false;
		}
		
		this.mainViewToggleGroup = new ToggleGroup();
		
		this.todayBtn = getMenuButton(LABEL_TODAY, USERDATA_TODAY, FxFontCommunity.Icons.cmd_calendar);
		this.extendedBtn = getMenuButton(LABEL_EXTENDED, USERDATA_EXTENDED, FxFontCommunity.Icons.cmd_numeric_7_box_multiple_outline);
		this.floatBtn = getMenuButton(LABEL_FLOAT, USERDATA_FLOAT, FxFontCommunity.Icons.cmd_image_filter_drama);
		this.allBtn = getMenuButton(LABEL_ALL, USERDATA_ALL, FxFontCommunity.Icons.cmd_calendar_multiple);
		this.completedBtn = getMenuButton(LABEL_COMPLETED, USERDATA_COMPLETED, FxFontCommunity.Icons.cmd_comment_check);
		
        this.categoryTitle = new Label(LABEL_CATEGORY_TITLE);
        this.categoryTitle.setFont(Font.font(FONT_CATEGORY_TITLE, FONTSIZE_CATEGORY_TITLE));
        this.categoryTitle.getStyleClass().add(STYLECLASS_CATEGORY_TITLE);
		
        this.categoryBoxContainer = new UICategoryBoxContainer(new ArrayList<Category>(), this.logic, this.mainViewToggleGroup);
        
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.floatBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());

	}
	
	private ToggleButton getMenuButton(String title, String userData, FxFontCommunity.Icons icon){
		FxIconicsLabel btnIcon =
                (FxIconicsLabel) new FxIconicsLabel
                .Builder(icon)
                .size(18)
                .color(COLOR_BTN_ICON).build();
		
		Label btnLabel = new Label(title);
		if (this.isLoadedCustomBtnLabelFont){	
	    	//btnLabel.setFont(this.customBtnLabelFont);
			btnLabel.setFont(Font.font(FONT_BTN_LABEL, FONTSIZE_BTN_LABEL));
		} else {
			btnLabel.setFont(Font.font(FONT_BTN_LABEL, FONTSIZE_BTN_LABEL));
		}
		btnLabel.getStyleClass().add(STYLECLASS_BTN_LABEL);
		
		HBox btnContent = new HBox();
		btnContent.setSpacing(SPACING_BTN_CONTENT);
		btnContent.getChildren().addAll(btnIcon, btnLabel);
		
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

