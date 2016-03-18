package dooyit.ui;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.cmd.FxFontCommunity;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UITaskPane {
	
	private static final String STYLESHEET_TASK_PANE_HEADER = "task-pane-header";
	private static final Font FONT_TASK_PANE_HEADER = UIFont.SEGOE_M;
	private static final String STYLESHEET_TASK_PANE = "task-pane";
	
	private VBox taskPane;
	
	public UITaskPane(){
		initialize();
	}
	
	private void initialize(){
		this.taskPane = new VBox();
		this.taskPane.setAlignment(Pos.CENTER);
		this.taskPane.getStyleClass().add(STYLESHEET_TASK_PANE);
		FontAwesomeIconView icon = new FontAwesomeIconView();
		icon.setGlyphName("STAR_ALT");
		icon.setStyleClass("task-pane-icon");
		Label header = getHeader("No more tasks for today.");
		header.setFont(FONT_TASK_PANE_HEADER);
		header.getStyleClass().add(STYLESHEET_TASK_PANE_HEADER);
		this.taskPane.getChildren().addAll(icon, header);
	}
	
	private Label getHeader(String headerString){
		Label btnLabel = new Label(headerString);
		btnLabel.setFont(UIFont.SEGOE_L);
		btnLabel.getStyleClass().add(STYLESHEET_TASK_PANE_HEADER);
		return btnLabel;
	}
	
	public VBox getView(){
		return this.taskPane;
	}
}
