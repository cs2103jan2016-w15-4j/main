package dooyit.ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import dooyit.logic.*;

public class UITaskBox {
	private Task task;
	
	private CheckBox taskCheckBox;
	private Label taskId;
	private Label taskName;
	private Label taskPeriod;
	private Label taskCategoryLabel;
	private HBox taskBox;
	
	private Logic logic;
	
	public UITaskBox(Task task, Logic logic){
		this.task = task;
		this.logic = logic;
		
		this.taskCheckBox = new CheckBox();
		this.taskCheckBox.getStyleClass().add("task-checkbox");
		
		
		this.taskId = new Label(Integer.toString(this.task.getId()));
		this.taskId.setFont(Font.font("Tahoma", 12));
	    this.taskId.getStyleClass().add("task-id");
	    this.taskId.setPrefWidth(30);
		
		this.taskName = new Label(this.task.getName());
		this.taskName.setFont(Font.font("Euphemia", 14));
	    this.taskName.getStyleClass().add("task-name");
	    this.taskName.setPrefWidth(250);
	    
	    if (this.task.hasDeadlineTime()){
	    	this.taskPeriod = new Label(this.task.getDeadlineTime().getTime12hStr());
	    } else {
	    	this.taskPeriod = new Label("");
	    }
	    this.taskPeriod.setFont(Font.font("Verdana", 12));
	    this.taskPeriod.getStyleClass().add("task-period");
	    this.taskPeriod.setPrefWidth(90);
	    
	    this.taskCategoryLabel = new Label("School");
	    this.taskCategoryLabel.setFont(Font.font("Verdana", 10));
	    this.taskCategoryLabel.getStyleClass().add("task-category-label");
	    this.taskCategoryLabel.setStyle("-fx-text-fill: #007AFF;");
	    this.taskCategoryLabel.setMaxWidth(60);
	    
	    this.taskBox = new HBox();
	    this.taskBox.setAlignment(Pos.CENTER_LEFT);
	    this.taskBox.setSpacing(10);
	    this.taskBox.getStyleClass().add("day-task-box");
	    this.taskBox.getChildren().addAll(this.taskCheckBox, this.taskId, this.taskName, this.taskPeriod, this.taskCategoryLabel);
	
	    // Check box action
	    this.taskCheckBox.setOnAction((event) -> {
	    	boolean isChecked = this.taskCheckBox.isSelected();
	    	this.logic.processCommand("mark " + Integer.toString(this.task.getId()));
	    });
	}
	
	public HBox getView(){
		return this.taskBox;
	}
}
