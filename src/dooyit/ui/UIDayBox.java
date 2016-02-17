package dooyit.ui;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UIDayBox {
	
	private VBox dayBox;
	private Label dayTitle;
	
	public UIDayBox(ArrayList<Task> task){
		this.dayBox = new VBox();
		this.dayBox.getStyleClass().add("day-box");
		
        this.dayTitle = new Label("Today, 1 February 2016");
        this.dayTitle.setFont(Font.font("Tahoma", 18));
        this.dayTitle.getStyleClass().add("day-title");
		
        this.dayBox.getChildren().add(dayTitle);
        
		for (int i = 0; i < 3; i++){
			UITaskBox taskBoxView = new UITaskBox(new Task());
	        HBox dayTaskBox = taskBoxView.getView();
	        this.dayBox.getChildren().add(dayTaskBox);
		}
	}
	
	public VBox getView(){
		return this.dayBox;
	}
}