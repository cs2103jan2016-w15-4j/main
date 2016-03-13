package dooyit.ui;

import java.io.FileInputStream;

import dooyit.common.datatype.Task;
import dooyit.logic.core.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class UITaskBox {
	
	private static final String STYLECLASS_TASK_CHECKBOX = "task-checkbox";
	
	private static final String FONT_TASK_ID = "Tahoma";
	private static final int FONTSIZE_TASK_ID = 12;
	private static final String STYLECLASS_TASK_ID = "task-id";
	private static final int PREFWIDTH_TASK_ID = 30;
	
	private static final String FONT_TASK_NAME = "Euphemia";
	private static final int FONTSIZE_TASK_NAME = 14;
	private static final String STYLECLASS_TASK_NAME = "task-name";
	private static final int TASK_NAME_WIDTH_TO_SUBTRACT = 240;
	
	private static final String FONT_TASK_PERIOD = "Verdana";
	private static final int FONTSIZE_TASK_PERIOD = 12;
	private static final String STYLECLASS_TASK_PERIOD = "task-period";
	private static final int PREFWIDTH_TASK_PERIOD = 100;
	
	private static final String FONT_TASK_CATEGORY_LABEL = "Verdana";
	private static final int FONTSIZE_TASK_CATEGORY_LABEL = 10;
	private static final String STYLECLASS_TASK_CATEGORY_LABEL = "task-category-label";
	private static final int PREFWIDTH_TASK_CATEGORY_LABEL = 80;
	
	private static final String STYLECLASS_TASK_BOX = "day-task-box";
	private static final int SPACING_TASK_BOX = 10;
	
	private static final String TASK_PERIOD_TO = " to ";
	private static final String TASK_PERIOD_BEGINS = "Begins ";
	private static final String TASK_PERIOD_ENDS = "Ends ";
	
	private static final String EMPTY_STR = "";
	private static final String CMD_MARK = "mark ";
	
	private static final int WIDTH_MENU = 180;
	private static final int PAD_X = 20;
	
	private Font Avenir_16;
	private UIDayBox parent;
	private Task task;
	private CheckBox taskCheckBox;
	private Label taskId;
	private Label taskName;
	private Label taskPeriod;
	private Label taskCategoryLabel;
	private HBox taskBox;
	private Logic logic;
	
	public UITaskBox(UIDayBox parent, Task task, Logic logic){
		this.task = task;
		this.logic = logic;
		this.parent = parent;
		
		this.taskCheckBox = new CheckBox();
		this.taskCheckBox.getStyleClass().add(STYLECLASS_TASK_CHECKBOX);
		
		
		this.taskId = new Label(Integer.toString(this.task.getId()));
		this.taskId.setFont(Font.font(FONT_TASK_ID, FONTSIZE_TASK_ID));
	    this.taskId.getStyleClass().add(STYLECLASS_TASK_ID);
	    this.taskId.setPrefWidth(PREFWIDTH_TASK_ID);
	    
	    if (this.task.hasDeadlineTime()){
	    	this.taskPeriod = new Label(this.task.getDeadlineTime().getTime24hStr());
	    } else if (this.task.hasStartTime() && this.task.hasEndTime()){
	    	this.taskPeriod = new Label(this.task.getDateTimeStart().getTime24hStr() + TASK_PERIOD_TO + this.task.getDateTimeEnd().getTime24hStr());
	    } else if (this.task.hasStartTime()){
	    	this.taskPeriod = new Label(TASK_PERIOD_BEGINS + this.task.getDateTimeStart().getTime24hStr());
	    } else if (this.task.hasEndTime()){
	    	this.taskPeriod = new Label(TASK_PERIOD_ENDS + this.task.getDateTimeEnd().getTime24hStr());
	    } else {
	    	this.taskPeriod = new Label(EMPTY_STR);
	    }
	    
	    this.taskPeriod.setFont(Font.font(FONT_TASK_PERIOD, FONTSIZE_TASK_PERIOD));
	    this.taskPeriod.getStyleClass().add(STYLECLASS_TASK_PERIOD);
	    this.taskPeriod.setPrefWidth(PREFWIDTH_TASK_PERIOD);
	    
	    this.taskCategoryLabel = new Label("School");
	    this.taskCategoryLabel.setFont(Font.font(FONT_TASK_CATEGORY_LABEL, FONTSIZE_TASK_CATEGORY_LABEL));
	    this.taskCategoryLabel.getStyleClass().add(STYLECLASS_TASK_CATEGORY_LABEL);
	    this.taskCategoryLabel.setStyle("-fx-text-fill: #007AFF;");
	    this.taskCategoryLabel.setPrefWidth(PREFWIDTH_TASK_CATEGORY_LABEL);
	    
	    this.taskName = new Label(this.task.getName());
	    try {
	    	this.Avenir_16 = Font.loadFont(getClass().getResourceAsStream("fonts/avenir-light.ttf"), 16);
	    	this.taskName.setFont(this.Avenir_16);
	    } catch(Exception e) {
			this.taskName.setFont(Font.font(FONT_TASK_NAME, FONTSIZE_TASK_NAME));
			System.out.println(e.getMessage());
	    }
	    this.taskName.getStyleClass().add(STYLECLASS_TASK_NAME);
	    double width = this.parent.getStageWidth();
	    updateTaskNameWidth(width - TASK_NAME_WIDTH_TO_SUBTRACT);
	    
	    this.taskBox = new HBox();
	    this.taskBox.setAlignment(Pos.CENTER_LEFT);
	    this.taskBox.setSpacing(SPACING_TASK_BOX);
	    this.taskBox.getStyleClass().add(STYLECLASS_TASK_BOX);
	    this.taskBox.getChildren().addAll(this.taskCheckBox, this.taskId, this.taskName, this.taskPeriod, this.taskCategoryLabel);
	    
	    // Check box action
	    this.taskCheckBox.setOnAction((event) -> {
	    	boolean isChecked = this.taskCheckBox.isSelected();
	    	this.logic.processCommand(CMD_MARK + Integer.toString(this.task.getId()));
	    });
	}
	
	public HBox getView(){
		return this.taskBox;
	}
	
	public void updatePosition(double stageWidth){
		updateTaskNameWidth(stageWidth);
	}
	
	private void updateTaskNameWidth(double stageWidth){
		double widthToSubtract = 0;
		widthToSubtract += WIDTH_MENU;
		widthToSubtract += 2 * PAD_X;
		widthToSubtract += this.taskCheckBox.getWidth() + 2 * SPACING_TASK_BOX;
		widthToSubtract += this.taskId.getWidth() + SPACING_TASK_BOX;
		widthToSubtract += this.taskPeriod.getWidth() + SPACING_TASK_BOX;
		widthToSubtract += this.taskCategoryLabel.getWidth() + 2 * SPACING_TASK_BOX;
		double width = stageWidth - widthToSubtract;
		this.taskName.setMinWidth(width);
		this.taskName.setPrefWidth(width);
		this.taskName.setMaxWidth(width);
	}
}
