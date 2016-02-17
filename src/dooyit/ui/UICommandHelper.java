package dooyit.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class UICommandHelper {
	private Stage primaryStage;
	private Popup commandHelperView;
	private ListView<String> listView;
	
	public UICommandHelper(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.listView = new ListView<String>();
		this.commandHelperView = new Popup();
		this.commandHelperView.getContent().addAll(this.listView);
	}
	
	public void show(){
		ObservableList<String> commands = FXCollections.observableArrayList (
		    "add", "edit", "sleep");
		this.listView.setItems(commands);
		this.listView.setPrefHeight(commands.size() * 50 + 2);
		this.commandHelperView.setX(this.primaryStage.getX() + 10);
		this.commandHelperView.setY(this.primaryStage.getY() + this.primaryStage.getHeight() - 200);
		this.commandHelperView.show(this.primaryStage);
	}
	
	public void hide(){
		this.commandHelperView.hide();
	}
}