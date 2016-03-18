package dooyit.ui;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UITaskBox {

	private static final String STYLECLASS_TASK_CHECKBOX = UIStyle.TASK_CHECKBOX;
	private static final Font FONT_TASK_ID = UIFont.TAHOMA_S;
	private static final String STYLECLASS_TASK_ID = UIStyle.TASK_ID;
	private static final int PREFWIDTH_TASK_ID = 30;

	private static final Font FONT_TASK_NAME = UIFont.EUPHEMIA_M;
	private static final String STYLECLASS_TASK_NAME = UIStyle.TASK_NAME;
	private static final int TASK_NAME_WIDTH_TO_SUBTRACT = 280;

	private static final Font FONT_TASK_PERIOD = UIFont.SEGOE_M;
	private static final String STYLECLASS_TASK_PERIOD = UIStyle.TASK_PERIOD;
	private static final int PREFWIDTH_TASK_PERIOD = 100;
	private static final String TASK_PERIOD_TO = " to ";
	private static final String TASK_PERIOD_BEGINS = "Begins ";
	private static final String TASK_PERIOD_ENDS = "Ends ";

	private static final Font FONT_TASK_CATEGORY_LABEL = UIFont.SEGOE_M;
	private static final String STYLECLASS_TASK_CATEGORY_LABEL = UIStyle.TASK_CATEGORY_LABEL;
	private static final int PREFWIDTH_TASK_CATEGORY_LABEL = 120;

	private static final String STYLECLASS_TASK_BOX = UIStyle.DAY_TASK_BOX;
	private static final int SPACING_TASK_BOX = 10;

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

	public UITaskBox(UIDayBox parent, Task task) {
		this.task = task;
		this.parent = parent;
		initialize();
	}
	
	private void initialize(){
		initTaskCheckBox();
		initTaskId();
		initTaskPeriod();
		initTaskCategoryLabel();
		initTaskName();
		initTaskBox();
		initListeners();
	}
	
	private void initTaskCheckBox(){
		this.taskCheckBox = new CheckBox();
		this.taskCheckBox.getStyleClass().add(STYLECLASS_TASK_CHECKBOX);
		if (this.task.isCompleted()) {
			this.taskCheckBox.setSelected(true);
		}
	}
	
	private void initTaskId(){
		this.taskId = new Label(Integer.toString(this.task.getId()));
		this.taskId.setFont(FONT_TASK_ID);
	    this.taskId.getStyleClass().add(STYLECLASS_TASK_ID);
	    this.taskId.setPrefWidth(PREFWIDTH_TASK_ID);
	}
	
	private void initTaskPeriod(){
		if (this.task.hasDeadlineTime()){
	    	this.taskPeriod = new Label(this.task.getDeadlineTime().getTime24hStr());
	    } else if (this.task.hasStartTime() && this.task.hasEndTime()){
	    	this.taskPeriod = new Label(this.task.getDateTimeStart().getTime24hStr() + TASK_PERIOD_TO + this.task.getDateTimeEnd().getTime24hStr());
	    } else if (this.task.hasStartTime()){
	    	this.taskPeriod = new Label(TASK_PERIOD_BEGINS + this.task.getDateTimeStart().getTime24hStr());
	    } else if (this.task.hasEndTime()){
	    	this.taskPeriod = new Label(TASK_PERIOD_ENDS + this.task.getDateTimeEnd().getTime24hStr());
	    } else {
	    	this.taskPeriod = new Label(UIData.EMP_STR);
	    }
		
//	    try {
//			this.customTaskPeriodFont = Font.loadFont(getClass().getResourceAsStream("fonts/Avenir-Medium.ttf"), 14);
//			this.taskPeriod.setFont(this.customTaskPeriodFont);
//		} catch (Exception e) {
//			this.taskPeriod.setFont(FONT_TASK_PERIOD);
//		}
		this.taskPeriod.setFont(FONT_TASK_PERIOD);
	    this.taskPeriod.getStyleClass().add(STYLECLASS_TASK_PERIOD);
	    this.taskPeriod.setPrefWidth(PREFWIDTH_TASK_PERIOD);
	}
	
	private void initTaskCategoryLabel(){
		this.taskCategoryLabel = new Label();
		if (this.task.hasCategory()){
			Category category = this.task.getCategory();
			this.taskCategoryLabel.setText(category.getName());
			this.taskCategoryLabel.setTextFill(category.getColour());
		}
	    this.taskCategoryLabel.setFont(FONT_TASK_CATEGORY_LABEL);
	    this.taskCategoryLabel.getStyleClass().add(STYLECLASS_TASK_CATEGORY_LABEL);
	    this.taskCategoryLabel.setPrefWidth(PREFWIDTH_TASK_CATEGORY_LABEL);
	}
	
	private void initTaskName(){
		this.taskName = new Label(this.task.getName());
//	    try {
//	    	this.Avenir_16 = Font.loadFont(getClass().getResourceAsStream("fonts/Avenir-Light.ttf"), 16);
//	    	this.taskName.setFont(this.Avenir_16);
//	    } catch(Exception e) {
//			this.taskName.setFont(FONT_TASK_NAME);
//	    }
	    this.taskName.setFont(FONT_TASK_NAME);
	    this.taskName.getStyleClass().add(STYLECLASS_TASK_NAME);
	    double width = this.parent.getStageWidth();
	    updateTaskNameWidth(width - TASK_NAME_WIDTH_TO_SUBTRACT);
	}
	
	private void initTaskBox(){
		 this.taskBox = new HBox();
		 this.taskBox.setAlignment(Pos.CENTER_LEFT);
		 this.taskBox.setSpacing(SPACING_TASK_BOX);
		 this.taskBox.getStyleClass().add(STYLECLASS_TASK_BOX);
		 this.taskBox.getChildren().addAll(this.taskCheckBox, this.taskId, this.taskName, this.taskPeriod, this.taskCategoryLabel);   
	}
	
	private void initListeners(){
	    this.taskCheckBox.setOnAction((event) -> {
	    	if (!this.task.isCompleted()){
	    		this.parent.markTask(this.task.getId());
	    	}
	    });

	}

	public HBox getView() {
		return this.taskBox;
	}

	public void updatePosition(double stageWidth) {
		updateTaskNameWidth(stageWidth);
	}

	private void updateTaskNameWidth(double stageWidth) {
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
