package dooyit.ui;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.logic.core.*;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UICategoryBox {
	private static final String STYLECLASS_CAT_NAME = UIStyle.CATEGORY_NAME;
	private static final String STYLECLASS_CAT_CIRCLE = UIStyle.CATEGORY_CIRCLE;
	private static final String STYLECLASS_CAT_BOX = UIStyle.BTN_SELECT_VIEW;
	private static final String STYLECLASS_CAT_BOX_WRAPPER = UIStyle.CATEGORY_BOX_WRAPPER;
	private static final int SPACING_CAT_BOX_WRAPPER = 14;

	private Category category;
	private UICategoryBoxContainer parent;
	private HBox categoryBoxWrapper;
	private Label categoryName;
	private Circle categoryCircle;
	private ToggleButton categoryBox;

	public UICategoryBox(UICategoryBoxContainer parent, Category category) {
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
		this.categoryName.setFont(UIFont.SEGOE_M);
		this.categoryName.getStyleClass().add(STYLECLASS_CAT_NAME);
	}
	
	private void initCategoryCircle(){
		this.categoryCircle = new Circle(4, Color.color(colour.r, colour.g, colour.b));
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
		this.categoryBox.setPrefWidth(180);
		this.categoryBox.setToggleGroup(this.parent.getMainViewToggleGroup());
		this.categoryBox.setSelected(false);
		this.categoryBox.setUserData(UIData.USERDATA_CATEGORY);
	}
	
	private void initListeners(){
		this.categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// categoryBoxContainer.getLogic().processCommand("show " +
				// categoryName.getText());
				event.consume();
			}
		});
	}

	public ToggleButton getView() {
		return this.categoryBox;
	}
}