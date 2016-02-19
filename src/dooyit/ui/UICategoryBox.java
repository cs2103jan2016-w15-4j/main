package dooyit.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class UICategoryBox {
	private HBox categoryBox;
	private Label categoryName;
	private Circle categoryCircle;
	
	public UICategoryBox(){		
		this.categoryBox = new HBox();
		
		this.categoryName = new Label("Chores");
		this.categoryName.setFont(Font.font("Euphemia", 14));
		this.categoryName.getStyleClass().add("category-name");
		
		this.categoryCircle = new Circle(4, Color.web("#007AFF"));
		this.categoryCircle.getStyleClass().add("category-circle");
		
		this.categoryBox.getChildren().addAll(categoryCircle, categoryName);
		this.categoryBox.getStyleClass().add("category-box");
		this.categoryBox.setSpacing(14);
	}
	
	public HBox getView(){
		return this.categoryBox;
	}
}