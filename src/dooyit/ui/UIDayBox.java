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
	
	private Logic logic;
	private VBox dayBox;
	private Label dayTitle;
	private ArrayList<Task> taskList;
	private String dayBoxTitle;
	
	public UIDayBox(TaskGroup taskGroup, Logic logic){
		this.logic = logic;
		this.taskList = taskGroup.getTasks();
		
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
			UITaskBox taskBoxView = new UITaskBox(this.taskList.get(i), this.logic);
	        HBox dayTaskBox = taskBoxView.getView();
	        this.dayBox.getChildren().add(dayTaskBox);
		}
	}
	
	public VBox getView(){
		return this.dayBox;
	}
}