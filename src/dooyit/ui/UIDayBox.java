package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Wu Wenqi A0124278A
 *
 */

public class UIDayBox {
	private static final String STYLECLASS_DAY_BOX = UIStyle.DAY_BOX;
	private static final String STYLECLASS_DAY_TITLE = UIStyle.DAY_TITLE;
	private static final String STYLECLASS_DAY_TITLE_FADED = UIStyle.DAY_TITLE_FADED;
	private static final String MSG_TODAY_NO_TASKS = "No tasks for today. Enjoy your day!";

	private UIDayBoxContainer parent;
	private VBox dayBox;
	private Label dayTitle;
	private ArrayList<Task> taskList;
	private ArrayList<UITaskBox> taskBoxList;
	private TaskGroup taskGroup;
	
	protected UIDayBox(UIDayBoxContainer parent, TaskGroup taskGroup){
		this.parent = parent;
		this.taskGroup = taskGroup;
		initialize();
	}
	
	private void initialize(){
		this.taskList = this.taskGroup.getTasks();
		this.taskBoxList = new ArrayList<UITaskBox>();
		initDayBox();
        initAllTasks();
	}
	
	private void initDayBox(){
		initDayTitle();
        this.dayBox = new VBox();
		this.dayBox.getStyleClass().add(STYLECLASS_DAY_BOX);
        this.dayBox.getChildren().add(this.dayTitle);
	}
	
	private void initDayTitle(){
        this.dayTitle = new Label(this.taskGroup.getTitle());
        this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE);
        if (taskList.size() == 0 && !this.taskGroup.getTitle().equals(UIData.TODAY)){
        	this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE_FADED);
        }
	}
	
	private void initAllTasks(){
		if (taskList.size() > 0){
			addAllTasks();
		} else {
			displayNoTasks();
		}
	}
	
	private void addAllTasks(){
		for (int i = 0; i < this.taskList.size(); i++){
			addTask(this.taskList.get(i));
		}
	}
	
	private void displayNoTasks(){
		if (this.taskGroup.getTitle().contains(UIData.TODAY)){
			UIMainViewType activeView = getActiveMainView();
			if (activeView == UIMainViewType.EXTENDED || activeView == UIMainViewType.TODAY){
				setNoTaskMessage();
			}
		}
	}
	
	private void addTask(Task task){
		UITaskBox taskBox = new UITaskBox(this, task);
		this.taskBoxList.add(taskBox);
		AnchorPane taskBoxView = taskBox.getView();
        this.dayBox.getChildren().add(taskBoxView);
	}
	
	private void setNoTaskMessage(){
		Label taskMessageView = new UITaskMessage(MSG_TODAY_NO_TASKS).getView();
		this.dayBox.getChildren().add(taskMessageView);
	}
	
	private UIMainViewType getActiveMainView(){
		return this.parent.getActiveMainView();
	}

	protected double getStageWidth() {
		return this.parent.getStageWidth();
	}

	protected void markTask(int taskId) {
		this.parent.markTask(taskId);
	}
	
	protected VBox getView() {
		return this.dayBox;
	}

	protected void updatePosition(double stageWidth) {
		this.taskBoxList.forEach((taskBox) -> {
			taskBox.updatePosition(stageWidth);
		});
	}
}