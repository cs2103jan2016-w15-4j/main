package dooyit.ui;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Colour;
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
	private static final Font FONT_CAT_NAME = Font.font("Euphemia", 14);
	private static final String STYLECLASS_CAT_NAME = "category-name";
	
	private static final String STYLECLASS_CAT_CIRCLE = "category-circle";
	
	private static final String STYLECLASS_CAT_BOX_WRAPPER = "category-box-wrapper";
	private static final int SPACING_CAT_BOX_WRAPPER = 14;
	
	private static final String STYLECLASS_CAT_BOX = "btn-select-view";
	private static final String USERDATA_CAT_BOX = "category";
	
	private UICategoryBoxContainer categoryBoxContainer;
	private HBox categoryBoxWrapper;
	private Label categoryName;
	private Circle categoryCircle;
	private ToggleButton categoryBox;
	private ToggleGroup mainViewToggleGroup;
	private Font customCategoryLabelFont;
	
	public UICategoryBox(UICategoryBoxContainer categoryBoxContainer, Category category, ToggleGroup mainViewToggleGroup){		
		this.mainViewToggleGroup = mainViewToggleGroup;
		this.categoryBoxContainer = categoryBoxContainer;
		this.categoryBoxWrapper = new HBox();
		
		this.categoryName = new Label(category.getName());
		try {
			this.customCategoryLabelFont = Font.loadFont(getClass().getResourceAsStream("fonts/SF-Regular.ttf"), 15);
			this.categoryName.setFont(this.customCategoryLabelFont);
		} catch(Exception e){
			this.categoryName.setFont(FONT_CAT_NAME);
		}
		
		this.categoryName.getStyleClass().add(STYLECLASS_CAT_NAME);
		
		Colour colour = category.getColour();
		this.categoryCircle = new Circle(4, Color.color(colour.r, colour.g, colour.b));
		this.categoryCircle.getStyleClass().add(STYLECLASS_CAT_CIRCLE);
		
		this.categoryBoxWrapper.getChildren().addAll(this.categoryCircle, this.categoryName);
		this.categoryBoxWrapper.getStyleClass().add(STYLECLASS_CAT_BOX_WRAPPER);
		this.categoryBoxWrapper.setSpacing(SPACING_CAT_BOX_WRAPPER);
		
		this.categoryBox = new ToggleButton();
		this.categoryBox.setGraphic(this.categoryBoxWrapper);
		this.categoryBox.getStyleClass().add(STYLECLASS_CAT_BOX);
		this.categoryBox.setPrefWidth(180);
		this.categoryBox.setToggleGroup(this.mainViewToggleGroup);
		this.categoryBox.setSelected(false);
		this.categoryBox.setUserData(USERDATA_CAT_BOX);
		
		this.categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 // categoryBoxContainer.getLogic().processCommand("show " + categoryName.getText());
		         event.consume();
		     }
		});

	}
	
	public ToggleButton getView(){
		return this.categoryBox;
	}
}