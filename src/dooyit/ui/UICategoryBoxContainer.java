// @@author A0124278A
package dooyit.ui;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Wu Wenqi
 *
 */

public class UICategoryBoxContainer {
	private static final int SPACING_CAT_BOX_CONTAINER_VIEW = 8;

	private UISideMenu parent;
	private VBox categoryBoxContainerView;
	private ArrayList<UICategoryBox> categoryBoxList;
	private ArrayList<Category> categoryList;
	
	protected UICategoryBoxContainer(UISideMenu parent, ArrayList<Category> categoryList){
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
		for (Category category : categoryList){
			addCategory(category);
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
	
	protected void processCommand(String cmd){
		this.parent.processCommand(cmd);
	}
	
	protected void setActiveCategoryButton(Category category){
		for (UICategoryBox categoryBox : this.categoryBoxList){
			if (categoryBox.getCategory().equals(category)){
				categoryBox.getView().setSelected(true);
				break;
			}
		}
	}
	
	protected VBox getView() {
		return this.categoryBoxContainerView;
	}

	protected void refresh(ArrayList<Category> categoryList) {
		this.categoryBoxContainerView.getChildren().clear();
		this.categoryBoxList.clear();
		for (Category category : categoryList){
			addCategory(category);
		}
	}
	
	protected String getSelectedCategoryName(){
		for (UICategoryBox categoryBox : this.categoryBoxList){
			if (categoryBox.getView().isSelected()){
				return categoryBox.getCategory().getName();
			}
		}
		return UIData.EMP_STR;
	}
}
