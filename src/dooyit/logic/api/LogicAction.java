package dooyit.logic.api;

public class LogicAction {

	Action action;
	String message = "";
	
	public LogicAction(Action action){
		this.action = action;
	}
	
	public LogicAction(Action action, String message){
		this.action = action;
		this.message = message;
	}
	
	public Action getAction(){
		return this.action;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public boolean hasMessage(){
		return !message.equals("");
	}
}

