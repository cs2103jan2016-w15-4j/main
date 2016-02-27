package dooyit.ui;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import dooyit.logic.*;

public class UIDayBox {
	
	private Logic logic;
	private VBox dayBox;
	private Label dayTitle;
	private ArrayList<Task> taskList;
	private String dayBoxTitle;
	
	public UIDayBox(TaskGroup taskGroup, Logic logic){
		this.logic = logic;
		this.taskList = taskGroup.getTasks();
		
		this.dayBox = new VBox();
		this.dayBox.getStyleClass().add("day-box");
		
		this.dayBoxTitle = taskGroup.getTitle();
		if (taskGroup.hasDateTime()){
			String date = taskGroup.getDateTime().getDate();
			date = date.substring(0, date.length() - 5);
			this.dayBoxTitle += ", " + date;
		}
		
        this.dayTitle = new Label(this.dayBoxTitle);
        this.dayTitle.setFont(Font.font("Euphemia", 18));
        this.dayTitle.getStyleClass().add("day-title");
        
        if (taskList.size() == 0 
        		&& !taskGroup.getTitle().equals("Today") 
        		&& !taskGroup.getTitle().equals("All") 
        		&& !taskGroup.getTitle().equals("Completed")){
        	this.dayTitle.getStyleClass().add("day-title-faded");
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