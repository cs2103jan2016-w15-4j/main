package dooyit.ui;

import java.util.ArrayList;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.cmd.FxFontCommunity;
import dooyit.common.datatype.Category;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @@author Wu Wenqi <A0124278A>
 *
 */

public class UISideMenu {

	private static final String LABEL_TODAY = "Today";
	private static final String LABEL_EXTENDED = "Next 7 days";
	private static final String LABEL_FLOAT = "Float";
	private static final String LABEL_ALL = "All";
	private static final String LABEL_COMPLETED = "Completed";
	private static final String STYLECLASS_MENU = UIStyle.MENU_VIEW;
	private static final int SPACING_MENU = 8;
	private static final String LABEL_CATEGORY_TITLE = "CATEGORIES";
	private static final String STYLECLASS_CATEGORY_TITLE = UIStyle.CATEGORY_TITLE;
	private static final String STYLECLASS_BTN_LABEL = UIStyle.BTN_SELECT_LABEL;
	private static final int SIZE_BTN_ICON = 18;
	private static final String COLOR_BTN_ICON = MaterialColor.GREY_400;
	private static final int SPACING_BTN_CONTENT = 8;
	private static final String STYLECLASS_MENU_BTN = UIStyle.BTN_SELECT_VIEW;
	private static final int PREFWIDTH_MENU_BTN = 200;
	private static final String STYLECLASS_MENU_PANE = UIStyle.MENU_PANE;

	private ScrollPane menuPane;
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

	protected UISideMenu(UIController parent) {
		this.parent = parent;
		initialize();
	}
	
	private void initialize(){
		this.mainViewToggleGroup = new ToggleGroup();
		initMenuButtons();
		initCategoryButtons();
		initMenu();
		initMenuPane();
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
	     this.categoryTitle.getStyleClass().add(STYLECLASS_CATEGORY_TITLE);	
	     this.categoryBoxContainer = new UICategoryBoxContainer(this, new ArrayList<Category>());
	}
	
	private void initMenu(){
		this.menu = new VBox();
		this.menu.setSpacing(SPACING_MENU);
		this.menu.getStyleClass().add(STYLECLASS_MENU);
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.floatBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());
	}
	
	private void initMenuPane(){
		this.menuPane = new ScrollPane();
		this.menuPane.getStyleClass().add(STYLECLASS_MENU_PANE);
		this.menuPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		this.menuPane.setFitToWidth(true);
		this.menuPane.setContent(this.menu);
	}
	
	private ToggleButton getMenuButton(String title, String userData, FxFontCommunity.Icons icon){
		FxIconicsLabel btnIcon = getMenuBtnIcon(icon);
		Label btnLabel = getMenuBtnLabel(title); 
		return makeMenuButton(btnIcon, btnLabel, userData);
	}
	
	private FxIconicsLabel getMenuBtnIcon(FxFontCommunity.Icons icon){
		return (FxIconicsLabel) new FxIconicsLabel.Builder(icon).size(SIZE_BTN_ICON).color(COLOR_BTN_ICON).build();
	}
	
	private Label getMenuBtnLabel(String title){
		Label btnLabel = new Label(title);
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
	
	protected void setActiveCategoryButton(Category category){
		this.categoryBoxContainer.setActiveCategoryButton(category);
	}
	

	protected ScrollPane getView() {
		return this.menuPane;
	}

	protected ToggleButton getTodayBtn() {
		return this.todayBtn;
	}

	protected ToggleButton getExtendedBtn() {
		return this.extendedBtn;
	}
	
	protected ToggleButton getFloatBtn() {
		return this.floatBtn;
	}

	protected ToggleButton getAllBtn() {
		return this.allBtn;
	}

	protected ToggleButton getCompletedBtn() {
		return this.completedBtn;
	}

	protected ToggleGroup getMainViewToggleGroup() {
		return this.mainViewToggleGroup;
	}

	protected void refreshCategoryMenuView(ArrayList<Category> categoryList) {
		this.categoryBoxContainer.refresh(categoryList);
	}
	
	protected void processCommand(String cmd){
		this.parent.processCommand(cmd);
	}

}
