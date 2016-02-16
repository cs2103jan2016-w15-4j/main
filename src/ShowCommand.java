
public class ShowCommand extends Command {
	
	CommandShowType commandShowType;
	String categoryName;
	
	public ShowCommand(CommandShowType commandShowType){
		this.commandShowType = commandShowType;
	}
	
	public ShowCommand(CommandShowType commandShowType, String categoryName){
		this.commandShowType = commandShowType;
		this.categoryName = categoryName;
	}
	
	@Override
	public void execute(TaskManager taskManager) {
		

	}

}
