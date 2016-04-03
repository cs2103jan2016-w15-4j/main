package dooyit.ui;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class UITaskBox {

	private static final String STYLECLASS_TASK_CHECKBOX = UIStyle.TASK_CHECKBOX;
	private static final Font FONT_TASK_ID = UIFont.TAHOMA_S;
	private static final String STYLECLASS_TASK_ID = UIStyle.TASK_ID;
	private static final int PREFWIDTH_TASK_ID = 20;
	private static final Font FONT_TASK_NAME = UIFont.SEGOE_M;
	private static final String STYLECLASS_TASK_NAME = UIStyle.TASK_NAME;
	private static final int WIDTH_TO_SUBTRACT = 55;
	private static final String STYLECLASS_TASK_PERIOD = UIStyle.TASK_PERIOD;
	private static final Font FONT_TASK_CATEGORY_LABEL = UIFont.SEGOE_S;
	private static final String STYLECLASS_TASK_CATEGORY_LABEL = UIStyle.TASK_CATEGORY_LABEL;
	private static final int PREFWIDTH_TASK_CATEGORY_LABEL = 120;
	private static final String STYLECLASS_TASK_BOX = UIStyle.DAY_TASK_BOX;
	private static final int RADIUS_CAT_CIRCLE = 4;
	private static final int WIDTH_MENU = 180;
	private static final int PAD_X = 20;
	private static final String DOUBLE_SPACE = "  ";

	private UIDayBox parent;
	private Task task;
	private CheckBox taskCheckBox;
	private Label taskId;
	private Label taskName;
	private Label taskPeriod;
	private HBox taskDetailBox;
	private Label taskCategoryLabel;
	private Circle taskCategoryCircle;
	private HBox taskCategoryBox;
	private AnchorPane taskBox;

	public UITaskBox(UIDayBox parent, Task task) {
		this.task = task;
		this.parent = parent;
		initialize();
	}
	
	private void initialize(){
		initTaskCheckBox();
		initTaskId();
		initTaskCategoryLabel();
		initTaskPeriod();
		initTaskName();
		initTaskDetailBox();
		initTaskCategoryBox();
		initTaskBox();
		initListeners();
		updateTaskBoxWidth();
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
		this.taskPeriod = new Label(getTaskPeriodString());
	    this.taskPeriod.getStyleClass().add(STYLECLASS_TASK_PERIOD);
	}
	
	private String getTaskPeriodString(){
		String s = this.task.getDateString();
		if (!s.isEmpty()){
			s += DOUBLE_SPACE;
		} 
		return s;
	}
	
	private void initTaskCategoryLabel(){
		setCategoryCircle();
		this.taskCategoryLabel = new Label();
	    this.taskCategoryLabel.setFont(FONT_TASK_CATEGORY_LABEL);
	    this.taskCategoryLabel.setAlignment(Pos.CENTER_RIGHT);
	    this.taskCategoryLabel.getStyleClass().add(STYLECLASS_TASK_CATEGORY_LABEL);
	    this.taskCategoryLabel.setPrefWidth(PREFWIDTH_TASK_CATEGORY_LABEL);
	}
	
	private void setCategoryCircle(){
		if (this.task.hasCategory()){
			Category category = this.task.getCategory();
			this.taskCategoryLabel.setText(category.getName());
			this.taskCategoryCircle = new Circle(RADIUS_CAT_CIRCLE, category.getColour());
		} else {
			this.taskCategoryCircle = new Circle(RADIUS_CAT_CIRCLE, Color.TRANSPARENT);
		}
	}
	
	private void initTaskName(){
		this.taskName = new Label(this.task.getName());
	    this.taskName.setFont(FONT_TASK_NAME);
	    this.taskName.getStyleClass().add(STYLECLASS_TASK_NAME);
	}
	
	private void initTaskDetailBox(){
		this.taskDetailBox = new HBox();
		this.taskDetailBox.getChildren().addAll(this.taskCheckBox, this.taskId, this.taskPeriod, this.taskName);
		this.taskDetailBox.setAlignment(Pos.CENTER_LEFT);
	}
	
	private void initTaskCategoryBox(){
		this.taskCategoryBox = new HBox();
		this.taskCategoryBox.getChildren().addAll(this.taskCategoryLabel, this.taskCategoryCircle);
		this.taskCategoryBox.setAlignment(Pos.CENTER_RIGHT);
	}
	
	private void initTaskBox(){
		 this.taskBox = new AnchorPane();
		 AnchorPane.setTopAnchor(this.taskDetailBox, (double) 5);
		 AnchorPane.setTopAnchor(this.taskCategoryBox, (double) 5);
		 AnchorPane.setLeftAnchor(this.taskDetailBox, (double) 0);
		 AnchorPane.setRightAnchor(this.taskCategoryBox, (double) 0);
		 this.taskBox.getStyleClass().add(STYLECLASS_TASK_BOX);
		 this.taskBox.getChildren().addAll(this.taskDetailBox, this.taskCategoryBox);
	}
	
	private void initListeners(){
	    this.taskCheckBox.setOnAction((event) -> {
	    	if (!this.task.isCompleted()){
	    		this.parent.markTask(this.task.getId());
	    	}
	    });
	}

	private void updateTaskBoxWidth(double stageWidth) {
		double widthToSubtract = 0;
		widthToSubtract += WIDTH_MENU;
		widthToSubtract += 2 * PAD_X;
		widthToSubtract += WIDTH_TO_SUBTRACT;
		double width = stageWidth - widthToSubtract;
		this.taskBox.setMinWidth(width);
		this.taskBox.setPrefWidth(width);
		this.taskBox.setMaxWidth(width);
	}
	
	private void updateTaskBoxWidth(){
		double width = this.parent.getStageWidth();
	    updateTaskBoxWidth(width);
	}
	
	protected AnchorPane getView() {
		return this.taskBox;
	}

	protected void updatePosition(double stageWidth) {
		updateTaskBoxWidth(stageWidth);
	}
}
