package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class UICategoryBoxContainer {
	private static final int SPACING_CAT_BOX_CONTAINER_VIEW = 8;

	private UISideMenu parent;
	private VBox categoryBoxContainerView;
	private ArrayList<UICategoryBox> categoryBoxList;
	private ArrayList<Category> categoryList;
	
	public UICategoryBoxContainer(UISideMenu parent, ArrayList<Category> categoryList){
		this.parent = parent;
		this.categoryList = categoryList;
		initialize();
	}
	
	private void initialize(){
		initCategoryBoxContainerView();
		addAllCategories();
	}
	
	private void initCategoryBoxContainerView(){
		this.categoryBoxContainerView = new VBox();
		this.categoryBoxContainerView.setSpacing(SPACING_CAT_BOX_CONTAINER_VIEW);
	}
	
	private void addAllCategories(){
		this.categoryBoxList = new ArrayList<UICategoryBox>();
		for (int i = 0; i < categoryList.size(); i++){
			addCategory(categoryList.get(i));
		}
	}
	
	private void addCategory(Category category){
		UICategoryBox categoryBox = new UICategoryBox(this, category);
		this.categoryBoxList.add(categoryBox);
		this.categoryBoxContainerView.getChildren().add(categoryBox.getView());
	}
	
	protected ToggleGroup getMainViewToggleGroup(){
		return this.parent.getMainViewToggleGroup();
	}
	
	public VBox getView() {
		return this.categoryBoxContainerView;
	}

	public void refresh(ArrayList<Category> categoryList) {
		this.categoryBoxContainerView.getChildren().clear();
		this.categoryBoxList.clear();
		for (int i = 0; i < categoryList.size(); i++){
			addCategory(categoryList.get(i));
		}
	}
}
