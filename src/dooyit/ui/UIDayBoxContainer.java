package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.TaskGroup;
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

	protected double getStageWidth() {
		double width = this.parent.getStageWidth();
		return width;
	}

	protected void markTask(int taskId) {
		this.parent.markTask(taskId);
	}
	
	protected UIMainViewType getActiveMainView(){
		return this.parent.getActiveViewType();
	}
	
	public VBox getView(){
		return this.dayBoxContainer;
	}

	public void updatePosition(double stageWidth) {
		this.dayBoxList.forEach((dayBox) -> {
			dayBox.updatePosition(stageWidth);
		});
	}
}
