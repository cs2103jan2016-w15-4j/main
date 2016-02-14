package dooyit.ui;

import java.util.ArrayList;

import javafx.scene.layout.VBox;

public class UIDayBoxContainer {
	private VBox dayBoxContainer;
	
	public UIDayBoxContainer(ArrayList<UIDayBox> dayBoxList){
		this.dayBoxContainer = new VBox();
		
		dayBoxList.forEach((dayBox)->{
			this.dayBoxContainer.getChildren().add(dayBox.getView());
		});
	}
	
	public VBox getView(){
		return this.dayBoxContainer;
	}
	
}
