package dooyit.ui;

import java.util.ArrayList;

import dooyit.logic.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UICategoryBoxContainer {
	
	private VBox categoryBoxContainerView;
	
	public UICategoryBoxContainer(ArrayList<Category> categoryList){
		this.categoryBoxContainerView = new VBox();
		this.categoryBoxContainerView.setSpacing(4);
		
		for (int i = 0; i < categoryList.size(); i++){
			UICategoryBox categoryBoxView = new UICategoryBox(categoryList.get(i));
			HBox categoryBox = categoryBoxView.getView();
			this.categoryBoxContainerView.getChildren().add(categoryBox);
		}
	}
	
	public VBox getView(){
		return this.categoryBoxContainerView;
	}
	
	public void refresh(ArrayList<Category> categoryList){
		this.categoryBoxContainerView.getChildren().clear();
		for (int i = 0; i < categoryList.size(); i++){
			UICategoryBox categoryBoxView = new UICategoryBox(categoryList.get(i));
			HBox categoryBox = categoryBoxView.getView();
			this.categoryBoxContainerView.getChildren().add(categoryBox);
		}
	}
}
