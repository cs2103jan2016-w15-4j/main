package dooyit.ui;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import dooyit.logic.*;

public class UIDayBox {
	
	private VBox dayBox;
	private Label dayTitle;
	
	public UIDayBox(ArrayList<Task> taskList){
		this.dayBox = new VBox();
		this.dayBox.getStyleClass().add("day-box");
		
        this.dayTitle = new Label("Today, 1 February 2016");
        this.dayTitle.setFont(Font.font("Tahoma", 18));
        this.dayTitle.getStyleClass().add("day-title");
		
        this.dayBox.getChildren().add(dayTitle);
        
		for (int i = 0; i < taskList.size(); i++){
			UITaskBox taskBoxView = new UITaskBox(taskList.get(i));
	        HBox dayTaskBox = taskBoxView.getView();
	        this.dayBox.getChildren().add(dayTaskBox);
		}
	}
	
	public VBox getView(){
		return this.dayBox;
	}
}