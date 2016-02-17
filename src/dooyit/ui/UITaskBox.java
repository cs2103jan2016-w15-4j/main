package dooyit.ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import dooyit.logic.*;

public class UITaskBox {
	private CheckBox taskCheckBox;
	private Label taskName;
	private Label taskPeriod;
	private VBox taskDescBox;
	private Circle taskCategoryCircle;
	private HBox taskBox;
	
	public UITaskBox(Task task){
		this.taskCheckBox = new CheckBox();
		
		this.taskName = new Label(task.getName());
		this.taskName.setFont(Font.font("Tahoma", 15));
	    this.taskName.getStyleClass().add("task-name");
	    
	    this.taskPeriod = new Label("Due 9pm");
	    this.taskPeriod.setFont(Font.font("Tahoma", 13));
	    this.taskPeriod.getStyleClass().add("task-period");
	    
	    this.taskDescBox = new VBox();
	    this.taskDescBox.getChildren().addAll(this.taskName, this.taskPeriod);
	    
	    this.taskCategoryCircle = new Circle(3, Color.web("#007AFF"));
	    
	    this.taskBox = new HBox();
	    this.taskBox.setSpacing(60);
	    this.taskBox.getStyleClass().add("day-task-box");
	    this.taskBox.getChildren().addAll(this.taskCheckBox, this.taskDescBox, this.taskCategoryCircle);
	
	    // Checkbox action
	    this.taskCheckBox.setOnAction((event) -> {
	    	boolean isChecked = this.taskCheckBox.isSelected();
	    	System.out.println(isChecked);
	    });
	}
	
	public HBox getView(){
		return this.taskBox;
	}
}
