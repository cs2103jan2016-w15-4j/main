package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UIDayBox {
	private static final String STYLECLASS_DAY_BOX = UIStyle.DAY_BOX;
	private static final String STYLECLASS_DAY_TITLE = UIStyle.DAY_TITLE;
	private static final String STYLECLASS_DAY_TITLE_FADED = UIStyle.DAY_TITLE_FADED;
	private static final String TASK_GROUP_TODAY = "Today";

	private UIDayBoxContainer parent;
	private VBox dayBox;
	private Label dayTitle;
	private ArrayList<Task> taskList;
	private ArrayList<UITaskBox> taskBoxList;
	private String title;
	
	public UIDayBox(UIDayBoxContainer parent, TaskGroup taskGroup){
		this.parent = parent;
		initialize(taskGroup);
	}
	
	private void initialize(TaskGroup taskGroup){
		this.taskList = taskGroup.getTasks();
		this.taskBoxList = new ArrayList<UITaskBox>();
		initDayBox(taskGroup);
        
		if (taskList.size() > 0){
			for (int i = 0; i < this.taskList.size(); i++){
				addTask(this.taskList.get(i));
			}
		} else {
			if (taskGroup.getTitle().equals(TASK_GROUP_TODAY)){
				UIMainViewType activeView = getActiveMainView();
				if (activeView == UIMainViewType.EXTENDED){
					setNoTaskMessage();
				} else if (activeView == UIMainViewType.TODAY){
					//setNoTaskPane();
					setNoTaskMessage();
				}
			}
		}
	}
	
	private void initDayBox(TaskGroup taskGroup){
		initDayTitle(taskGroup);
        this.dayBox = new VBox();
		this.dayBox.getStyleClass().add(STYLECLASS_DAY_BOX);
        this.dayBox.getChildren().add(this.dayTitle);
	}
	
	private void initDayTitle(TaskGroup taskGroup){
		this.title = taskGroup.getTitle();
		if (taskGroup.hasDateTime()){
			String date = taskGroup.getDateTime().getDate();
			date = date.substring(0, date.length() - 5);
			this.title += UIData.COMMA_SPLIT + date;
		}
		
        this.dayTitle = new Label(this.title);
        this.dayTitle.setFont(UIFont.SEGOE_L);
        this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE);
        
        if (taskList.size() == 0 && !taskGroup.getTitle().equals(TASK_GROUP_TODAY)){
        	this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE_FADED);
        }
	}
	
	private void addTask(Task task){
		UITaskBox taskBox = new UITaskBox(this, task);
		this.taskBoxList.add(taskBox);
        HBox taskBoxView = taskBox.getView();
        this.dayBox.getChildren().add(taskBoxView);
	}
	
	private void setNoTaskPane(){
		VBox taskPaneView = new UITaskPane().getView();
		this.dayBox.getChildren().add(taskPaneView);
	}
	
	private void setNoTaskMessage(){
		Label taskMessageView = new UITaskMessage("No tasks for today. Enjoy your day!").getView();
		this.dayBox.getChildren().add(taskMessageView);
	}
	
	private UIMainViewType getActiveMainView(){
		return this.parent.getActiveMainView();
	}

	public VBox getView() {
		return this.dayBox;
	}

	public void updatePosition(double stageWidth) {
		this.taskBoxList.forEach((taskBox) -> {
			taskBox.updatePosition(stageWidth);
		});
	}

	protected double getStageWidth() {
		double width = this.parent.getStageWidth();
		return width;
	}

	protected void markTask(int taskId) {
		this.parent.markTask(taskId);
	}
}