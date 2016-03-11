package dooyit.logic.core;

import dooyit.ui.*;

public class UserPreference {
	
	private UITheme activeTheme;
	private String customThemePath;
	private String taskSaveFilePath;
	
	public UserPreference(String taskSaveFilePath, UITheme theme){
		this.activeTheme = UITheme.LIGHT;
		this.taskSaveFilePath = taskSaveFilePath;
		this.customThemePath = "";
	}
	
	public UITheme getActiveTheme(){
		return this.activeTheme;
	}
	
	public boolean hasCustomTheme(){
		if (this.customThemePath.equals("")){
			return false;
		}
		return true;
	}
	
	public String getCustomThemePath(){
		return this.customThemePath;
	}
	
	public String getTaskSaveFilePath(){
		return this.taskSaveFilePath;
	}
	
	public void setActiveTheme(UITheme theme){
		this.activeTheme = theme;
	}
	
	public void setTaskSaveFilePath(String path){
		this.taskSaveFilePath = path;
	}
	
	public void setCustomThemePath(String path){
		this.customThemePath = path;
	}
	
}
