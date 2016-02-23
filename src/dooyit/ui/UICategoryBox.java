package dooyit.ui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import dooyit.logic.*;

public class UICategoryBox {
	private UICategoryBoxContainer categoryBoxContainer;
	private HBox categoryBox;
	private Label categoryName;
	private Circle categoryCircle;
	
	public UICategoryBox(UICategoryBoxContainer categoryBoxContainer, Category category){		
		this.categoryBoxContainer = categoryBoxContainer;
		
		this.categoryBox = new HBox();
		
		this.categoryName = new Label("Chores");
		this.categoryName.setFont(Font.font("Euphemia", 14));
		this.categoryName.getStyleClass().add("category-name");
		
		this.categoryCircle = new Circle(4, Color.web("#007AFF"));
		this.categoryCircle.getStyleClass().add("category-circle");
		
		this.categoryBox.getChildren().addAll(categoryCircle, categoryName);
		this.categoryBox.getStyleClass().add("category-box");
		this.categoryBox.setSpacing(14);
		
		this.categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		         categoryBoxContainer.setAllCategoryBoxesInactive();
		    	 setActive();
		    	 // Pass command to parser here
		         event.consume();
		     }
		});

	}
	
	public void setActive(){
		this.categoryName.setFont(Font.font("Euphemia", FontWeight.BOLD, 14));
	}
	
	public void setInactive(){
		this.categoryName.setFont(Font.font("Euphemia", 14));
	}
	
	public HBox getView(){
		return this.categoryBox;
	}
}