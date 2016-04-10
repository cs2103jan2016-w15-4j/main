// @@author A0124278A
package dooyit.ui;

import dooyit.common.datatype.Category;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * The <tt>UICategoryBox</tt> class contains the methods to create and retrieve a category menu button. 
 * @author 	Wu Wenqi
 * @version 0.5
 * @since 	2016-04-10
 */

public class UICategoryBox {
	private static final String STYLECLASS_CAT_NAME = UIStyle.CATEGORY_NAME;
	private static final String STYLECLASS_CAT_CIRCLE = UIStyle.CATEGORY_CIRCLE;
	private static final String STYLECLASS_CAT_BOX = UIStyle.BTN_SELECT_VIEW;
	private static final String STYLECLASS_CAT_BOX_WRAPPER = UIStyle.CATEGORY_BOX_WRAPPER;
	private static final double CAT_CIRCLE_RADIUS = 4.0;
	private static final int SPACING_CAT_BOX_WRAPPER = 14;
	private static final int PREFWIDTH_CAT_BOX = 200;

	private UICategoryBoxContainer parent;
	private Category category;
	private HBox categoryBoxWrapper;
	private Label categoryName;
	private Circle categoryCircle;
	private ToggleButton categoryBox;

	/**
	 * This is the constructor method for <tt>UICategoryBox</tt> class.
	 * @param parent 	This is the parent <tt>UICategoryBoxContainer</tt> class.
	 * @param category 	This is the <tt>Category</tt> object to be displayed by the category menu button.
	 */
	protected UICategoryBox(UICategoryBoxContainer parent, Category category) {
		this.parent = parent;
		this.category = category;
		initialize();
	}
	
	/**
	 * This method is used to initialize the <tt>UICategoryBox</tt> class.
	 */
	private void initialize() {
		initCategoryName();
		initCategoryCircle();
		initCategoryBoxWrapper();
		initCategoryBox();
		initListeners();
	}
	
	/**
	 * This method is used to initialize the category name label.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCategoryName() {
		this.categoryName = new Label(this.category.getName());
		this.categoryName.getStyleClass().add(STYLECLASS_CAT_NAME);
	}
	
	/**
	 * This method is used to initialize the colored circle for the category.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCategoryCircle() {
		this.categoryCircle = new Circle(CAT_CIRCLE_RADIUS, this.category.getColour());
		this.categoryCircle.getStyleClass().add(STYLECLASS_CAT_CIRCLE);
	}
	
	/**
	 * This method is used to initialize the category box wrapper which contains the category circle and category name label.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCategoryBoxWrapper() {
		this.categoryBoxWrapper = new HBox();
		this.categoryBoxWrapper.getChildren().addAll(this.categoryCircle, this.categoryName);
		this.categoryBoxWrapper.getStyleClass().add(STYLECLASS_CAT_BOX_WRAPPER);
		this.categoryBoxWrapper.setSpacing(SPACING_CAT_BOX_WRAPPER);
	}
	
	/**
	 * This method is used to initialize the category menu button which contains the category wrapper.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initCategoryBox() {
		this.categoryBox = new ToggleButton();
		this.categoryBox.setGraphic(this.categoryBoxWrapper);
		this.categoryBox.getStyleClass().add(STYLECLASS_CAT_BOX);
		this.categoryBox.setPrefWidth(PREFWIDTH_CAT_BOX);
		this.categoryBox.setToggleGroup(this.parent.getMainViewToggleGroup());
		this.categoryBox.setUserData(UIData.USERDATA_CATEGORY);
	}
	
	/**
	 * This method is used to initialize listeners for the category menu button.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initListeners() {
		this.categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				parent.processCommand(getShowCategoryCommand());
				event.consume();
			}
		});
	}
	
	/**
	 * This method is used to generate a command string for displaying the tasks belonging to this class's <tt>Category</tt> object.
	 * @return A command string.
	 */
	private String getShowCategoryCommand() {
		return UIData.CMD_SHOW_CAT + categoryName.getText();
	}
	
	/**
	 * This method is used to retrieve the <tt>Category</tt> object which is displayed by the category menu button.
	 * @return The <tt>Category</tt> object.
	 */
	protected Category getCategory() {
		return this.category;
	}
	
	/**
	 * This method is used to retrieve the category menu button.
	 * @return The category menu button.
	 */
	protected ToggleButton getView() {
		return this.categoryBox;
	}
}