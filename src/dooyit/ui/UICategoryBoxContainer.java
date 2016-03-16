package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.logic.core.*;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UICategoryBoxContainer{
	private static final int SPACING_CAT_BOX_CONTAINER_VIEW = 6;
	
	private UISideMenu parent;
	private VBox categoryBoxContainerView;
	private ArrayList<UICategoryBox> categoryBoxList;
	private Logic logic;
	
	public UICategoryBoxContainer(UISideMenu parent, ArrayList<Category> categoryList){
		this.parent = parent;
		initCategoryBoxContainerView();
		this.categoryBoxList = new ArrayList<UICategoryBox>();
		
		for (int i = 0; i < categoryList.size(); i++){
			addCategory(categoryList.get(i));
		}
	}
	
	private void initCategoryBoxContainerView(){
		this.categoryBoxContainerView = new VBox();
		this.categoryBoxContainerView.setSpacing(SPACING_CAT_BOX_CONTAINER_VIEW);
	}
	
	private void addCategory(Category category){
		UICategoryBox categoryBox = new UICategoryBox(this, category);
		this.categoryBoxList.add(categoryBox);
		this.categoryBoxContainerView.getChildren().add(categoryBox.getView());
	}
	
	public Logic getLogic(){
		return this.logic;
	}
	
	public VBox getView(){
		return this.categoryBoxContainerView;
	}
	
	public ToggleGroup getMainViewToggleGroup(){
		return this.parent.getMainViewToggleGroup();
	}
	
	public void refresh(ArrayList<Category> categoryList){
		this.categoryBoxContainerView.getChildren().clear();
		this.categoryBoxList.clear();
		for (int i = 0; i < categoryList.size(); i++){
			addCategory(categoryList.get(i));
		}
	}
}
