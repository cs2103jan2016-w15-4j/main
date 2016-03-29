package dooyit.logic.api;

public class LogicAction {

	public enum Action{
		ADD_TODAY_TASK,
		ADD_NEXT7DAY_TASK,
		ADD_FLOATING_TASK,
		ADD_ALL_TASK,
		ADD_CATEGORY,
		SET_CATEGORY,
		DELETE,
		CLEAR,
		MARK_TASK,
		UNMARK_TASK,
		EDIT_TODAY_TASK,
		EDIT_NEXT7DAY_TASK,
		EDIT_FLOATING_TASK,
		EDIT_ALL_TASK,
		SHOW_TODAY_TASK,
		SHOW_NEXT7DAY_TASK,
		SHOW_FLOATING_TASK,
		SHOW_ALL_TASK,
		SHOW_CATEGORY,
		SEARCH,
		HELP
	}

	Action action;
	String message = "";
	
	public LogicAction(Action action){
		this.action = action;
	}
	
	public LogicAction(Action action, String message){
		this.action = action;
		this.message = message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public boolean hasMessage(){
		return !message.equals("");
	}
}

