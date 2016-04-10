// @@author A0124278A
package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * The <tt>UIDayBox</tt> class contains all the <tt>UITaskBox</tt> objects whose <tt>Task</tt> 
 * belongs to a single day or <tt>Task</tt> type.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class UIDayBox {
	private static final String STYLECLASS_DAY_BOX = UIStyle.DAY_BOX;
	private static final String STYLECLASS_DAY_TITLE = UIStyle.DAY_TITLE;
	private static final String STYLECLASS_DAY_TITLE_FADED = UIStyle.DAY_TITLE_FADED;
	private static final String MSG_TODAY_NO_TASKS = "No tasks for today. Enjoy your day!";

	private UIDayBoxContainer parent;
	private VBox dayBox;
	private Label dayTitle;
	private ArrayList<Task> taskList;
	private ArrayList<UITaskBox> taskBoxList;
	private TaskGroup taskGroup;
	
	/**
	 * This is the constructor method for <tt>UIDayBox</tt> class.
	 * @param parent The parent <tt>UIDayBoxContainer</tt> class.
	 * @param taskGroup The <tt>TaskGroup</tt> to be associated with.
	 */
	protected UIDayBox(UIDayBoxContainer parent, TaskGroup taskGroup) {
		this.parent = parent;
		this.taskGroup = taskGroup;
		initialize();
	}
	
	/**
	 * This method is used to initialize <tt>UIDayBox</tt>.
	 * It is used by the constructor.
	 */
	private void initialize() {
		this.taskList = this.taskGroup.getTasks();
		this.taskBoxList = new ArrayList<UITaskBox>();
		initDayBox();
		initAllTasks();
	}
	
	/**
	 * This method is used to initialize the view box.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initDayBox() {
		initDayTitle();
		this.dayBox = new VBox();
		this.dayBox.getStyleClass().add(STYLECLASS_DAY_BOX);
		this.dayBox.getChildren().add(this.dayTitle);
	}
	
	/**
	 * This method is used to initialize the title of the view box.
	 * It is used by the <tt>initDayTitle</tt> method.
	 */
	private void initDayTitle() {
		this.dayTitle = new Label(this.taskGroup.getTitle());
		this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE);
		if (isEmptyAndNotToday()) {
			this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE_FADED);
		}
	}
	
	/**
	 * This method is used to check if the <tt>TaskGroup</tt> is not today's and is empty.
	 * It is used by the <tt>initDayTitle</tt> method.
	 * @return <tt>True</tt> if the <tt>TaskGroup</tt> is not today's and is empty, <tt>False</tt> otherwise.
	 */
	private boolean isEmptyAndNotToday() {
		return taskList.size() == 0 && !isTodayTaskGroup();
	}
	
	/**
	 * This method is used to check that the <tt>TaskGroup</tt> is today's.
	 * It is used by the <tt>isEmptyAndNotToday</tt> method.
	 * @return <tt>True</tt> if the <tt>TaskGroup</tt> is today's.
	 */
	private boolean isTodayTaskGroup() {
		return this.taskGroup.getTitle().contains(UIData.TODAY);
	}
	
	/**
	 * This method is used to initialize all <tt>Task</tt> objects in the <tt>TaskGroup</tt>.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initAllTasks() {
		if (taskList.size() > 0) {
			addAllTasks();
		} else {
			displayNoTasks();
		}
	}
	
	/**
	 * This method is used to add all <tt>Task</tt> objects from the <tt>TaskGroup</tt>.
	 * It is used by the <tt>initAllTasks</tt> method.
	 */
	private void addAllTasks() {
		for (Task task : this.taskList) {
			addTask(task);
		}
	}
	
	/**
	 * This method is used to display in the view box a message 
	 * which says that the user does not have any tasks for today.
	 * It is used by the <tt>initAllTasks</tt> method.
	 */
	private void displayNoTasks() {
		if (isTodayTaskGroup()) {
			setNoTaskMessage();
		}
	}
	
	/**
	 * This method is used to create a <tt>UITaskBox</tt> for a <tt>Task</tt> and add its 
	 * view to the view box.
	 * @param task The <tt>Task</tt> to create a <tt>UITaskBox</tt> for.
	 */
	private void addTask(Task task) {
		UITaskBox taskBox = new UITaskBox(this, task);
		this.taskBoxList.add(taskBox);
		AnchorPane taskBoxView = taskBox.getView();
		this.dayBox.getChildren().add(taskBoxView);
	}
	
	/**
	 * This method is used to create a label informing the user that he has not tasks for today, 
	 * and add it to the view box.
	 * It is used by the <tt>initAllTasks</tt> method.
	 */
	private void setNoTaskMessage() {
		Label taskMessageView = new UITaskMessage(MSG_TODAY_NO_TASKS).getView();
		this.dayBox.getChildren().add(taskMessageView);
	}
	
	/**
	 * This method is used to get the currently active <tt>UIMainViewType</tt> of the UI.
	 * @return The currently active <tt>UIMainViewType</tt> of the UI.
	 */
	protected UIMainViewType getActiveMainView() {
		return this.parent.getActiveMainView();
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
	 * This method is used to unmark a previously marked <tt>Task</tt>.
	 * @param taskId The displayed id of the <tt>Task</tt> to be unmarked.
	 */
	protected void unmarkTask(int taskId) {
		this.parent.unmarkTask(taskId);
	}
	
	/**
	 * This method is used to retrieve the view box.
	 * @return The view box.
	 */
	protected VBox getView() {
		return this.dayBox;
	}

	/**
	 * This method is used to adjust the layouts of views contained within the view box.
	 * @param stageWidth The stage width of the application scene.
	 */
	protected void updatePosition(double stageWidth) {
		this.taskBoxList.forEach((taskBox) -> {
			taskBox.updatePosition(stageWidth);
		});
	}
}