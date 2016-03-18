package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.TaskGroup;
import dooyit.logic.core.*;
import javafx.scene.layout.VBox;

public class UIDayBoxContainer {
	private UIController parent;
	private ArrayList<UIDayBox> dayBoxList;
	private VBox dayBoxContainer;
	
	public UIDayBoxContainer(UIController parent){
		this.parent = parent;
		this.dayBoxContainer = new VBox();
		this.dayBoxList = new ArrayList<UIDayBox>();
	}

	public void refresh(ArrayList<TaskGroup> taskGroupList) {
		this.dayBoxContainer.getChildren().clear();
		taskGroupList.forEach((taskGroup)->{
			addDayBox(taskGroup);
		});
	}
	
	private void addDayBox(TaskGroup taskGroup){
		UIDayBox dayBox = new UIDayBox(this, taskGroup);
		this.dayBoxList.add(dayBox);
		this.dayBoxContainer.getChildren().add(dayBox.getView());
	}
	
	public VBox getView(){
		return this.dayBoxContainer;
	}

	public void updatePosition(double stageWidth) {
		this.dayBoxList.forEach((dayBox) -> {
			dayBox.updatePosition(stageWidth);
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
