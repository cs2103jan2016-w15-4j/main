package dooyit.ui;

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
	private UICategoryBoxContainer categoryBoxContainer;
	private HBox categoryBoxWrapper;
	private Label categoryName;
	private Circle categoryCircle;
	private ToggleButton categoryBox;
	private ToggleGroup mainViewToggleGroup;
	
	public UICategoryBox(UICategoryBoxContainer categoryBoxContainer, Category category, ToggleGroup mainViewToggleGroup){		
		this.mainViewToggleGroup = mainViewToggleGroup;
		this.categoryBoxContainer = categoryBoxContainer;
		this.categoryBoxWrapper = new HBox();
		
		this.categoryName = new Label(category.getName());
		this.categoryName.setFont(Font.font("Euphemia", 14));
		this.categoryName.getStyleClass().add("category-name");
		
		Colour colour = category.getColour();
		this.categoryCircle = new Circle(4, Color.color(colour.r, colour.g, colour.b));
		this.categoryCircle.getStyleClass().add("category-circle");
		
		this.categoryBoxWrapper.getChildren().addAll(categoryCircle, categoryName);
		this.categoryBoxWrapper.getStyleClass().add("category-box-wrapper");
		this.categoryBoxWrapper.setSpacing(14);
		
		this.categoryBox = new ToggleButton();
		this.categoryBox.setGraphic(this.categoryBoxWrapper);
		this.categoryBox.getStyleClass().add("btn-select-view");
		this.categoryBox.setPrefWidth(180);
		this.categoryBox.setToggleGroup(this.mainViewToggleGroup);
		this.categoryBox.setSelected(false);
		this.categoryBox.setUserData("category");
		
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