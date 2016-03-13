package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import dooyit.logic.core.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UIDayBox {
	private static final String STYLECLASS_DAY_BOX = "day-box";
	private static final String DAY_BOX_TITLE_COMMA = ", ";
	
	private static final Font FONT_DAY_TITLE = Font.font("Euphemia", 18);
	private static final String STYLECLASS_DAY_TITLE = "day-title";
	private static final String STYLECLASS_DAY_TITLE_FADED = "day-title-faded";
	
	private static final String TASK_GROUP_TODAY = "Today";
	
	private UIDayBoxContainer parent;
	private Logic logic;
	private VBox dayBox;
	private Label dayTitle;
	private ArrayList<Task> taskList;
	private ArrayList<UITaskBox> taskBoxList;
	private String dayBoxTitle;
	
	public UIDayBox(UIDayBoxContainer parent, TaskGroup taskGroup, Logic logic){
		this.parent = parent;
		this.logic = logic;
		this.taskList = taskGroup.getTasks();
		this.taskBoxList = new ArrayList<UITaskBox>();
		this.dayBox = new VBox();
		this.dayBox.getStyleClass().add(STYLECLASS_DAY_BOX);
		
		this.dayBoxTitle = taskGroup.getTitle();
		if (taskGroup.hasDateTime()){
			String date = taskGroup.getDateTime().getDate();
			date = date.substring(0, date.length() - 5);
			this.dayBoxTitle += DAY_BOX_TITLE_COMMA + date;
		}
		
        this.dayTitle = new Label(this.dayBoxTitle);
        this.dayTitle.setFont(FONT_DAY_TITLE);
        this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE);
        
        if (taskList.size() == 0 && !taskGroup.getTitle().equals(TASK_GROUP_TODAY)){
        	this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE_FADED);
        }
		
        this.dayBox.getChildren().add(dayTitle);
        
		for (int i = 0; i < this.taskList.size(); i++){
			UITaskBox taskBox = new UITaskBox(this, this.taskList.get(i), this.logic);
			this.taskBoxList.add(taskBox);
	        HBox taskBoxView = taskBox.getView();
	        this.dayBox.getChildren().add(taskBoxView);
		}
	}
	
	public VBox getView(){
		return this.dayBox;
	}
	
	public void updatePosition(double stageWidth){
		this.taskBoxList.forEach((taskBox)->{
			taskBox.updatePosition(stageWidth);
		});
	}
	
	protected double getStageWidth(){
		double width = this.parent.getStageWidth();
		return width;
	}
}