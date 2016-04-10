// @@author A0124278A
package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.TaskGroup;
import javafx.scene.layout.VBox;

/**
 * The <tt>UIDayBoxContainer</tt> class contains the methods to initialize <tt>UIDayBox</tt> 
 * and update their views.
 * @author 	Wu Wenqi
 * @version 0.5
 * @since 	2016-04-10
 */

public class UIDayBoxContainer {
	private UIController parent;
	private ArrayList<UIDayBox> dayBoxList;
	private VBox dayBoxContainer;
	
	/**
	 * This is the constructor method for <tt>UIDayBoxContainer</tt> class.
	 * @param parent The parent <tt>UIController</tt> class.
	 */
	protected UIDayBoxContainer(UIController parent) {
		this.parent = parent;
		this.dayBoxContainer = new VBox();
		this.dayBoxList = new ArrayList<UIDayBox>();
	}
	
	/**
	 * This method is used to create a <tt>UIDayBox</tt> and add its view to the view box.
	 * @param taskGroup The <tt>TaskGroup</tt> to be associated with <tt>UIDayBox</tt>.
	 */
	private void addDayBox(TaskGroup taskGroup) {
		UIDayBox dayBox = new UIDayBox(this, taskGroup);
		this.dayBoxList.add(dayBox);
		this.dayBoxContainer.getChildren().add(dayBox.getView());
	}
	
	/**
	 * This method is used to update the view box content.
	 * @param taskGroupList The list of <tt>TaskGroup</tt> objects to create <tt>UIDayBox</tt> for.
	 */
	protected void refresh(ArrayList<TaskGroup> taskGroupList) {
		this.dayBoxContainer.getChildren().clear();
		taskGroupList.forEach((taskGroup) -> {
			addDayBox(taskGroup);
		});
	}

	/**
	 * This method is used to get the stage width of the application scene.
	 * @return The stage width of the application scene.
	 */
	protected double getStageWidth() {
		return this.parent.getStageWidth();
	}

	/**
	 * This method is used to mark a <tt>Task</tt> as completed.
	 * @param taskId The displayed id of the <tt>Task</tt> to be marked as completed.
	 */
	protected void markTask(int taskId) {
		this.parent.markTask(taskId);
	}
	
	/**
	 * This method is used to unmarked a previously marked <tt>Task</tt>
	 * @param taskId The displayed id of the <tt>Task</tt> to be unmarked.
	 */
	protected void unmarkTask(int taskId) {
		this.parent.unmarkTask(taskId);
	}
	
	/**
	 * This method is used to retrieve the currently active <tt>UIMainViewType</tt> of the UI.
	 * @return The currently active <tt>UIMainViewType</tt> of the UI.
	 */
	protected UIMainViewType getActiveMainView() {
		return this.parent.getActiveViewType();
	}
	
	/**
	 * This method is used to retrieve the view box.
	 * @return The view box.
	 */
	protected VBox getView() {
		return this.dayBoxContainer;
	}

	/** 
	 * This method is used to update the views of all <tt>UIDayBox</tt> objects.
	 * @param stageWidth The stage width of the application scene.
	 */
	protected void updatePosition(double stageWidth) {
		this.dayBoxList.forEach((dayBox) -> {
			dayBox.updatePosition(stageWidth);
		});
	}
}
