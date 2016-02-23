package dooyit.ui;

import java.util.ArrayList;

import dooyit.logic.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UICategoryBoxContainer {
	
	private VBox categoryBoxContainerView;
	private ArrayList<UICategoryBox> categoryBoxList;
	
	public UICategoryBoxContainer(ArrayList<Category> categoryList){
		this.categoryBoxContainerView = new VBox();
		this.categoryBoxContainerView.setSpacing(4);
		this.categoryBoxList = new ArrayList<UICategoryBox>();
		
		for (int i = 0; i < categoryList.size(); i++){
			UICategoryBox categoryBox = new UICategoryBox(this, categoryList.get(i));
			this.categoryBoxList.add(categoryBox);
			this.categoryBoxContainerView.getChildren().add(categoryBox.getView());
		}
	}
	
	public VBox getView(){
		return this.categoryBoxContainerView;
	}
	
	public void refresh(ArrayList<Category> categoryList){
		this.categoryBoxContainerView.getChildren().clear();
		this.categoryBoxList.clear();
		for (int i = 0; i < categoryList.size(); i++){
			UICategoryBox categoryBox = new UICategoryBox(this, categoryList.get(i));
			this.categoryBoxList.add(categoryBox);
			this.categoryBoxContainerView.getChildren().add(categoryBox.getView());
		}
	}
	
	public void setAllCategoryBoxesInactive(){
		for (UICategoryBox categoryBox: this.categoryBoxList){
			categoryBox.setInactive();
		}
	}
}
