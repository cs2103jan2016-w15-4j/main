package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.TaskGroup;
import javafx.scene.layout.VBox;

/**
 * 
 * @@author Wu Wenqi <A0124278A>
 *
 */

public class UIDayBoxContainer {
	private UIController parent;
	private ArrayList<UIDayBox> dayBoxList;
	private VBox dayBoxContainer;
	
	protected UIDayBoxContainer(UIController parent){
		this.parent = parent;
		this.dayBoxContainer = new VBox();
		this.dayBoxList = new ArrayList<UIDayBox>();
	}
	
	private void addDayBox(TaskGroup taskGroup){
		UIDayBox dayBox = new UIDayBox(this, taskGroup);
		this.dayBoxList.add(dayBox);
		this.dayBoxContainer.getChildren().add(dayBox.getView());
	}
	
	protected void refresh(ArrayList<TaskGroup> taskGroupList) {
		this.dayBoxContainer.getChildren().clear();
		taskGroupList.forEach((taskGroup)->{
			addDayBox(taskGroup);
		});
	}

	protected double getStageWidth() {
		return this.parent.getStageWidth();
	}

	protected void markTask(int taskId) {
		this.parent.markTask(taskId);
	}
	
	protected void unmarkTask(int taskId) {
		this.parent.unmarkTask(taskId);
	}
	
	protected UIMainViewType getActiveMainView() {
		return this.parent.getActiveViewType();
	}
	
	protected VBox getView() {
		return this.dayBoxContainer;
	}

	protected void updatePosition(double stageWidth) {
		this.dayBoxList.forEach((dayBox) -> {
			dayBox.updatePosition(stageWidth);
		});
	}
}
