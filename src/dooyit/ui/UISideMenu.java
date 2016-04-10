// @@author A0124278A
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
 * The <tt>UISideMenu</tt> class contains the methods to initialize the side menu 
 * and update its menu buttons.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
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

	/**
	 * This is the constructor method.
	 * @param parent The parent <tt>UIController</tt> class.
	 */
	protected UISideMenu(UIController parent) {
		this.parent = parent;
		initialize();
	}
	
	/**
	 * This method is used to initialize the <tt>UISideMenu</tt> class.
	 * It is used by the constructor.
	 */
	private void initialize() {
		this.mainViewToggleGroup = new ToggleGroup();
		initMenuButtons();
		initCategoryButtons();
		initMenu();
		initMenuPane();
	}
	
	/**
	 * This method is used to initialize the main menu buttons.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initMenuButtons() {
		this.todayBtn = getMenuButton(LABEL_TODAY, UIData.USERDATA_TODAY, FxFontCommunity.Icons.cmd_calendar);
		this.extendedBtn = getMenuButton(LABEL_EXTENDED, UIData.USERDATA_EXTENDED, FxFontCommunity.Icons.cmd_numeric_7_box_multiple_outline);
		this.floatBtn = getMenuButton(LABEL_FLOAT, UIData.USERDATA_FLOAT, FxFontCommunity.Icons.cmd_image_filter_drama);
		this.allBtn = getMenuButton(LABEL_ALL, UIData.USERDATA_ALL, FxFontCommunity.Icons.cmd_calendar_multiple);
		this.completedBtn = getMenuButton(LABEL_COMPLETED, UIData.USERDATA_COMPLETED, FxFontCommunity.Icons.cmd_comment_check);
	}
	
	/**
	 * This method is used to initialize the category menu buttons.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCategoryButtons() {
		 this.categoryTitle = new Label(LABEL_CATEGORY_TITLE);
		 this.categoryTitle.getStyleClass().add(STYLECLASS_CATEGORY_TITLE);	
		 this.categoryBoxContainer = new UICategoryBoxContainer(this, new ArrayList<Category>());
	}
	
	/**
	 * This method is used to initialize the menu view. 
	 * It should only be called after all menu buttons and the category title have been initialized.
	 * This method is used by the <tt>initialize</tt> method.
	 */
	private void initMenu() {
		this.menu = new VBox();
		this.menu.setSpacing(SPACING_MENU);
		this.menu.getStyleClass().add(STYLECLASS_MENU);
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.floatBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());
	}
	
	/**
	 * This method is used to initialize the scroll pane that will contain the menu view.
	 * It should only be called after the menu view has been initialized.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initMenuPane() {
		this.menuPane = new ScrollPane();
		this.menuPane.getStyleClass().add(STYLECLASS_MENU_PANE);
		this.menuPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		this.menuPane.setFitToWidth(true);
		this.menuPane.setContent(this.menu);
	}
	
	/**
	 * This method is used to create and return a menu button.
	 * @param title The name of the menu button.
	 * @param userData The user data that is associated with the menu button.
	 * @param icon The icon of the menu button.
	 * @return A menu button.
	 */
	private ToggleButton getMenuButton(String title, String userData, FxFontCommunity.Icons icon) {
		FxIconicsLabel btnIcon = getMenuBtnIcon(icon);
		Label btnLabel = getMenuBtnLabel(title); 
		return makeMenuButton(btnIcon, btnLabel, userData);
	}
	
	/**
	 * This method is used to create and return an icon for a menu button.
	 * @param icon The icon to be used for a menu button.
	 * @return
	 */
	private FxIconicsLabel getMenuBtnIcon(FxFontCommunity.Icons icon) {
		return (FxIconicsLabel) new FxIconicsLabel.Builder(icon).size(SIZE_BTN_ICON).color(COLOR_BTN_ICON).build();
	}
	
	/**
	 * This method is used to create and return a label for a menu button.
	 * @param title The label to be used for a menu button.
	 * @return
	 */
	private Label getMenuBtnLabel(String title) {
		Label btnLabel = new Label(title);
		btnLabel.getStyleClass().add(STYLECLASS_BTN_LABEL);
		return btnLabel;
	}
	
	/**
	 * This method is used to create and return a menu button.
	 * It is used by the <tt>getMenuButton</tt> method.
	 * @param icon The icon of the menu button.
	 * @param btnLabel The label to add to the menu button.
	 * @param userData The user data to be associated with the menu button.
	 * @return A menu button.
	 */
	private ToggleButton makeMenuButton(FxIconicsLabel icon, Label btnLabel, String userData) {
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
	
	/**
	 * This method is used to select the category menu button of <tt>category</tt>.
	 * @param category The <tt>Category</tt> whose menu button is to be selected.
	 */
	protected void setActiveCategoryButton(Category category) {
		this.categoryBoxContainer.setActiveCategoryButton(category);
	}
	
	/**
	 * This method is used to get the scroll pane that contains the menu view.
	 * @return The scroll pane that contains the menu view.
	 */
	protected ScrollPane getView() {
		return this.menuPane;
	}

	/**
	 * This method is used to get the menu button for today's view.
	 * @return Menu button for today's view
	 */
	protected ToggleButton getTodayBtn() {
		return this.todayBtn;
	}

	/**
	 * This method is used to get the menu button for next 7 days' view.
	 * @return Menu button for next 7 days' view.
	 */
	protected ToggleButton getExtendedBtn() {
		return this.extendedBtn;
	}
	
	/**
	 * This method is used to get the menu button for float view.
	 * @return Menu button for float view.
	 */
	protected ToggleButton getFloatBtn() {
		return this.floatBtn;
	}

	/**
	 * This method is used to get the menu button for all view.
	 * @return Menu button for all view.
	 */
	protected ToggleButton getAllBtn() {
		return this.allBtn;
	}

	/**
	 * This method is used to get the menu button for completed view.
	 * @return Menu button for completed view.
	 */
	protected ToggleButton getCompletedBtn() {
		return this.completedBtn;
	}

	/**
	 * This method is used to get the <tt>ToggleGroup</tt> of the menu buttons.
	 * @return <tt>ToggleGroup</tt> of the menu buttons.
	 */
	protected ToggleGroup getMainViewToggleGroup() {
		return this.mainViewToggleGroup;
	}

	/**
	 * This method is used to update the category menu buttons.
	 * @param categoryList The list of <tt>Category</tt> objects to create menu buttons for.
	 */
	protected void refreshCategoryMenuView(ArrayList<Category> categoryList) {
		this.categoryBoxContainer.refresh(categoryList);
	}
	
	/**
	 * This method is used to process a command string.
	 * @param cmd The command string to be processed.
	 */
	protected void processCommand(String cmd) {
		this.parent.processCommand(cmd);
	}
	
	/**
	 * This method is used to get the name of the <tt>Category</tt> whose menu button is selected.
	 * @return The name of the <tt>Category</tt> whose menu button is selected.
	 */
	protected String getSelectedCategoryName() {
		return this.categoryBoxContainer.getSelectedCategoryName();
	}
}
