package dooyit.ui;

import java.util.ArrayList;

import javafx.scene.layout.VBox;

import dooyit.logic.*;

public class UIDayBoxContainer {
	private VBox dayBoxContainer;
	private Logic logic;
	
	public UIDayBoxContainer(Logic logic){
		this.dayBoxContainer = new VBox();
		this.logic = logic;
	}
	
	public void refresh(ArrayList<TaskGroup> taskGroupList){
		this.dayBoxContainer.getChildren().clear();
		taskGroupList.forEach((taskGroup)->{
			UIDayBox dayBox = new UIDayBox(taskGroup, logic);
			this.dayBoxContainer.getChildren().add(dayBox.getView());
		});
	}
	
	public VBox getView(){
		return this.dayBoxContainer;
	}
	
}
