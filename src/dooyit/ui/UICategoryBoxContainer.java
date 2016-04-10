// @@author A0124278A
package dooyit.ui;

import java.util.ArrayList;
import dooyit.common.datatype.Category;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * The <tt>UICategoryBoxContainer</tt> class contains the methods to create and retrieve a list of <tt>UICategoryBox</tt> objects.
 * It also has a container view which contains the category menu buttons for the list.
 * @author 	Wu Wenqi
 * @version 0.5
 * @since 	2016-04-10
 */

public class UICategoryBoxContainer {
	private static final int SPACING_CAT_BOX_CONTAINER_VIEW = 8;

	private UISideMenu parent;
	private VBox categoryBoxContainerView;
	private ArrayList<UICategoryBox> categoryBoxList;
	private ArrayList<Category> categoryList;
	
	/**
	 * This is the constructor method for <tt>UICategoryBoxContainer</tt> class.
	 * @param parent This is the parent <tt>UISideMenu</tt> class.
	 * @param categoryList This is the list of <tt>Category</tt> objects to be displayed on the menu.
	 */
	protected UICategoryBoxContainer(UISideMenu parent, ArrayList<Category> categoryList) {
		this.parent = parent;
		this.categoryList = categoryList;
		initialize();
	}
	
	/**
	 * This method is used to initialize the <tt>UICategoryBoxContainer</tt> class.
	 */
	private void initialize() {
		initCategoryBoxContainerView();
		addAllCategories();
	}
	
	/**
	 * This method is used to initialize the view container which contains the category menu buttons.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCategoryBoxContainerView() {
		this.categoryBoxContainerView = new VBox();
		this.categoryBoxContainerView.setSpacing(SPACING_CAT_BOX_CONTAINER_VIEW);
	}
	
	/**
	 * This method is used to create a <tt>UICategoryBox</tt> for each <tt>Category</tt> object.
	 * The category menu button for each <tt>UICategoryBox</tt> is added to the view container.
	 * This method is used by the <tt>initialize</tt> method.
	 */
	private void addAllCategories() {
		this.categoryBoxList = new ArrayList<UICategoryBox>();
		for (Category category : this.categoryList) {
			addCategory(category);
		}
	}
	
	/**
	 * This method is used to create a <tt>UICategoryBox</tt> for a <tt>Category</tt> object.
	 * The category menu button for <tt>UICategoryBox</tt> is subsequently added to the view container.
	 * This method is used by the <tt>addAllCategories</tt> method. 
	 * @param category This is the <tt>Category</tt> to be displayed by the category menu button.
	 */
	private void addCategory(Category category) {
		UICategoryBox categoryBox = new UICategoryBox(this, category);
		this.categoryBoxList.add(categoryBox);
		this.categoryBoxContainerView.getChildren().add(categoryBox.getView());
	}
	
	/**
	 * This method is used to get the <tt>ToggleGroup</tt> for the menu buttons.
	 * @return The <tt>ToggleGroup</tt> for the menu buttons.
	 */
	protected ToggleGroup getMainViewToggleGroup() {
		return this.parent.getMainViewToggleGroup();
	}
	
	/**
	 * This method is used to pass a command string to the parent <tt>UISideMenu</tt> class to be processed.
	 * @param cmd This is the command string to be processed.
	 */
	protected void processCommand(String cmd) {
		this.parent.processCommand(cmd);
	}
	
	/**
	 * This method is used to select the category menu button for a <tt>Category</tt>.
	 * @param category The <tt>Category</tt> whose menu button is to be selected.
	 */
	protected void setActiveCategoryButton(Category category) {
		for (UICategoryBox categoryBox : this.categoryBoxList) {
			if (categoryBox.getCategory().equals(category)) {
				categoryBox.getView().setSelected(true);
				break;
			}
		}
	}
	
	/**
	 * This method is used to retrieve the view container which contains the category menu buttons.
	 * @return The view container.
	 */
	protected VBox getView() {
		return this.categoryBoxContainerView;
	}

	/**
	 * This method is used to update the category menu buttons.
	 * @param categoryList The updated list of <tt>Category</tt> objects to create menu buttons for.
	 */
	protected void refresh(ArrayList<Category> categoryList) {
		this.categoryBoxContainerView.getChildren().clear();
		this.categoryBoxList.clear();
		for (Category category : categoryList) {
			addCategory(category);
		}
	}
	
	/**
	 * This method is used to get the name of the <tt>Category</tt> whose menu button is selected.
	 * @return The <tt>Category</tt> name.
	 */
	protected String getSelectedCategoryName() {
		for (UICategoryBox categoryBox : this.categoryBoxList) {
			if (categoryBox.getView().isSelected()) {
				return categoryBox.getCategory().getName();
			}
		}
		return UIData.EMP_STR;
	}
}
