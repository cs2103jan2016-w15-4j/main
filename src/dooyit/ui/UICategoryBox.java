package dooyit.ui;

import dooyit.common.datatype.Category;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class UICategoryBox {
	private static final String STYLECLASS_CAT_NAME = UIStyle.CATEGORY_NAME;
	private static final String STYLECLASS_CAT_CIRCLE = UIStyle.CATEGORY_CIRCLE;
	private static final String STYLECLASS_CAT_BOX = UIStyle.BTN_SELECT_VIEW;
	private static final String STYLECLASS_CAT_BOX_WRAPPER = UIStyle.CATEGORY_BOX_WRAPPER;
	private static final int SPACING_CAT_BOX_WRAPPER = 14;
	private static final int PREFWIDTH_CAT_BOX = 200;

	private Category category;
	private UICategoryBoxContainer parent;
	private HBox categoryBoxWrapper;
	private Label categoryName;
	private Circle categoryCircle;
	private ToggleButton categoryBox;

	protected UICategoryBox(UICategoryBoxContainer parent, Category category) {
		this.parent = parent;
		this.category = category;
		initialize();
	}
	
	private void initialize(){
		initCategoryName();
		initCategoryCircle();
		initCategoryBoxWrapper();
		initCategoryBox();
		initListeners();
	}
	
	private void initCategoryName(){
		this.categoryName = new Label(this.category.getName());
		this.categoryName.getStyleClass().add(STYLECLASS_CAT_NAME);
	}
	
	private void initCategoryCircle(){
		this.categoryCircle = new Circle(4, this.category.getColour());
		this.categoryCircle.getStyleClass().add(STYLECLASS_CAT_CIRCLE);
	}
	
	private void initCategoryBoxWrapper(){
		this.categoryBoxWrapper = new HBox();
		this.categoryBoxWrapper.getChildren().addAll(this.categoryCircle, this.categoryName);
		this.categoryBoxWrapper.getStyleClass().add(STYLECLASS_CAT_BOX_WRAPPER);
		this.categoryBoxWrapper.setSpacing(SPACING_CAT_BOX_WRAPPER);
	}
	
	private void initCategoryBox(){
		this.categoryBox = new ToggleButton();
		this.categoryBox.setGraphic(this.categoryBoxWrapper);
		this.categoryBox.getStyleClass().add(STYLECLASS_CAT_BOX);
		this.categoryBox.setPrefWidth(PREFWIDTH_CAT_BOX);
		this.categoryBox.setToggleGroup(this.parent.getMainViewToggleGroup());
		this.categoryBox.setUserData(UIData.USERDATA_CATEGORY);
	}
	
	private void initListeners(){
		this.categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				categoryBox.setSelected(true);
				parent.processCommand(UIData.CMD_SHOW_CAT + categoryName.getText());
				event.consume();
			}
		});
	}
	
	protected Category getCategory(){
		return this.category;
	}
	
	protected ToggleButton getView() {
		return this.categoryBox;
	}
}