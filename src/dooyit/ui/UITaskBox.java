// @@author A0124278A
package dooyit.ui;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The <tt>UITaskBox</tt> class contains the methods to initialize a task view and 
 * change its layout and dimensions.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class UITaskBox {
	private static final String STYLECLASS_TASK_CHECKBOX = UIStyle.TASK_CHECKBOX;
	private static final String STYLECLASS_TASK_ID = UIStyle.TASK_ID;
	private static final int PREFWIDTH_TASK_ID = 24;
	private static final String STYLECLASS_TASK_NAME = UIStyle.TASK_NAME;
	private static final String STYLECLASS_TASK_PERIOD = UIStyle.TASK_PERIOD;
	private static final String STYLECLASS_TASK_PERIOD_OVERDUE = UIStyle.TASK_PERIOD_OVERDUE;
	private static final String STYLECLASS_TASK_CATEGORY_LABEL = UIStyle.TASK_CATEGORY_LABEL;
	private static final int PREFWIDTH_TASK_CATEGORY_LABEL = 120;
	private static final String STYLECLASS_TASK_BOX = UIStyle.DAY_TASK_BOX;
	private static final int RADIUS_CAT_CIRCLE = 4;
	private static final int WIDTH_MENU = 180;
	private static final int PAD_X = 20;
	private static final int WIDTH_TO_SUBTRACT = 55 + WIDTH_MENU + 2 * PAD_X;
	private static final String DOUBLE_SPACE = "  ";
	private static final double ANCHOR_TOP = 5.0;
	private static final double ANCHOR_LEFT = 0.0;
	private static final double ANCHOR_RIGHT = 0.0;
	private static final int STR_LEN_TASK_PERIOD = 7;
	private static final double LEN_EVENT_PERIOD = 112;
	private static final double LEN_TASK_PERIOD = 56;

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

	/**
	 * This is the constructor method.
	 * @param parent The parent <tt>UIDayBox</tt> class.
	 * @param task The <tt>Task</tt> to populate the task view with.
	 */
	public UITaskBox(UIDayBox parent, Task task) {
		this.task = task;
		this.parent = parent;
		initialize();
	}
	
	/**
	 * This method is used to initialize the <tt>UITaskBox</tt> class.
	 * It is used by the constructor.
	 */
	private void initialize() {
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
	
	/**
	 * This method is used to initialize the check box for the task view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskCheckBox() {
		this.taskCheckBox = new CheckBox();
		this.taskCheckBox.getStyleClass().add(STYLECLASS_TASK_CHECKBOX);
		if (this.task.isCompleted()) {
			this.taskCheckBox.setSelected(true);
		}
	}
	
	/**
	 * This method is used to initialize the task id for the task view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskId() {
		this.taskId = new Label(Integer.toString(this.task.getDisplayId()));
		this.taskId.getStyleClass().add(STYLECLASS_TASK_ID);
		this.taskId.setPrefWidth(PREFWIDTH_TASK_ID);
	}
	
	/**
	 * This method is used to initialize the task period for the task view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskPeriod() {
		this.taskPeriod = new Label(getTaskPeriodString());
		this.taskPeriod.getStyleClass().add(STYLECLASS_TASK_PERIOD);
		if (isOverdueTask()) {
			this.taskPeriod.getStyleClass().add(STYLECLASS_TASK_PERIOD_OVERDUE);
		}
		if (!isEmptyTaskPeriod()) {
			setTaskPeriodWidth();
		}
	}
	
	/**
	 * This method is used to set the width of the task period. It should only be called after 
	 * the task period has been initialized. It is used by the <tt>initTaskPeriod</tt> method.
	 */
	private void setTaskPeriodWidth() {
		if (isLongTaskPeriod()) {
			this.taskPeriod.setPrefWidth(LEN_EVENT_PERIOD);
		} else {
			this.taskPeriod.setPrefWidth(LEN_TASK_PERIOD);
		}
	}
	
	/**
	 * This method is used to check if the <tt>Task</tt> is overdue.
	 * @return <tt>True</tt> if the <tt>Task</tt> is overdue, <tt>False</tt> otherwise.
	 */
	private boolean isOverdueTask() {
		return this.task.isOverDue(new DateTime());
	}
	
	/**
	 * This method is used to check if the task period is empty.
	 * @return <tt>True</tt> if the task period is empty, <tt>False</tt> otherwise.
	 */
	private boolean isEmptyTaskPeriod() {
		return this.taskPeriod.getText().isEmpty();
	}
	
	/**
	 * This method is used to check if the task period string is long.
	 * @return <tt>True</tt> if task period string is long, <tt>False</tt> otherwise.
	 */
	private boolean isLongTaskPeriod() {
		return this.taskPeriod.getText().length() > STR_LEN_TASK_PERIOD;
	}
	
	/**
	 * This method is used to get the task period string.
	 * @return The task period string.
	 */
	private String getTaskPeriodString() {
		String s = this.task.getDateString();
		if (!s.isEmpty()) {
			s += DOUBLE_SPACE;
		} 
		return s;
	}
	
	/**
	 * This method is used to initialize the category name displayed in the task view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskCategoryLabel() {
		this.taskCategoryLabel = new Label();
		setCategoryCircle();
		this.taskCategoryLabel.setAlignment(Pos.CENTER_RIGHT);
		this.taskCategoryLabel.getStyleClass().add(STYLECLASS_TASK_CATEGORY_LABEL);
		this.taskCategoryLabel.setPrefWidth(PREFWIDTH_TASK_CATEGORY_LABEL);
	}
	
	/**
	 * This method is used to initialize the colored circle of the category in the task view.
	 * It is used by the <tt>setCategoryCircle</tt> method.
	 */
	private void setCategoryCircle() {
		if (this.task.hasCategory()){
			Category category = this.task.getCategory();
			this.taskCategoryLabel.setText(category.getName());
			this.taskCategoryCircle = new Circle(RADIUS_CAT_CIRCLE, category.getColour());
		} else {
			this.taskCategoryCircle = new Circle(RADIUS_CAT_CIRCLE, Color.TRANSPARENT);
		}
	}
	
	/**
	 * This method is used to initialize the task name.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskName() {
		this.taskName = new Label(this.task.getName());
		this.taskName.getStyleClass().add(STYLECLASS_TASK_NAME);
	}
	
	/**
	 * This method is used to initialize the view box for the task details.
	 * This method can only be called after the check box, task id, task period and task name have been 
	 * initialized.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskDetailBox() {
		this.taskDetailBox = new HBox();
		this.taskDetailBox.getChildren().addAll(this.taskCheckBox, this.taskId, this.taskPeriod, this.taskName);
		this.taskDetailBox.setAlignment(Pos.CENTER_LEFT);
	}
	
	/**
	 * This method is used to initialized the view box for the task's category details.
	 * This method can only been called after the category label and category colored circle have 
	 * been initialized.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskCategoryBox() {
		this.taskCategoryBox = new HBox();
		this.taskCategoryBox.getChildren().addAll(this.taskCategoryLabel, this.taskCategoryCircle);
		this.taskCategoryBox.setAlignment(Pos.CENTER_RIGHT);
	}
	
	/**
	 * This method is used to initialize the task view.
	 * It can only be called after the view boxes for both task details and category details have been 
	 * initialized.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTaskBox() {
		 this.taskBox = new AnchorPane();
		 AnchorPane.setTopAnchor(this.taskDetailBox, ANCHOR_TOP);
		 AnchorPane.setTopAnchor(this.taskCategoryBox, ANCHOR_TOP);
		 AnchorPane.setLeftAnchor(this.taskDetailBox, ANCHOR_LEFT);
		 AnchorPane.setRightAnchor(this.taskCategoryBox, ANCHOR_RIGHT);
		 this.taskBox.getStyleClass().add(STYLECLASS_TASK_BOX);
		 this.taskBox.getChildren().addAll(this.taskDetailBox, this.taskCategoryBox);
	}
	
	/**
	 * This method is used to initialize event listeners for the task view.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initListeners() {
	    this.taskCheckBox.setOnAction((event) -> {
	    	if (!this.task.isCompleted()){
	    		this.parent.markTask(this.task.getDisplayId());
	    	} else {
	    		this.parent.unmarkTask(this.task.getDisplayId());
	    	}
	    });
	}

	/**
	 * This method is used to adjust the task view width depending on the stage width of the 
	 * application scene.
	 * @param stageWidth The stage width of the application scene.
	 */
	private void updateTaskBoxWidth(double stageWidth) {
		double width = stageWidth - WIDTH_TO_SUBTRACT;
		this.taskBox.setMinWidth(width);
		this.taskBox.setPrefWidth(width);
		this.taskBox.setMaxWidth(width);
		double taskNameMaxWidth = getTaskNameMaxWidth(width);
		this.taskName.setMaxWidth(taskNameMaxWidth);
	}
	
	/**
	 * This method is used to retrieve the max width of the task name label given the width of the 
	 * task view.
	 * It is used by the <tt>updateTaskBoxWidth</tt> method.
	 * @param width The width of the task view.
	 * @return The maximum width of the task name label.
	 */
	private double getTaskNameMaxWidth(double width) {
		return width - PREFWIDTH_TASK_CATEGORY_LABEL - RADIUS_CAT_CIRCLE - 2 * PAD_X - this.taskPeriod.getPrefWidth();
	}
	
	/**
	 * This is an overloading method for the <tt>updateTaskBoxWidth</tt> method.
	 */
	private void updateTaskBoxWidth() {
		double width = this.parent.getStageWidth();
		updateTaskBoxWidth(width);
	}
	
	/**
	 * This method is used to retrieve the task view.
	 * @return A task view.
	 */
	protected AnchorPane getView() {
		return this.taskBox;
	}

	/**
	 * This method is used to update the dimensions of the task view.
	 * @param stageWidth The stage width of the application scene.
	 */
	protected void updatePosition(double stageWidth) {
		updateTaskBoxWidth(stageWidth);
	}
}
