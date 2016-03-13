package dooyit.ui;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.gmd.FxFontGoogleMaterial;
import com.pepperonas.fxiconics.cmd.FxFontCommunity;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UIHeader {
	private static final String STYLECLASS_HEADER = "header-view";
	
	private static final String LABEL_TITLE = "DOOYIT";
	private static final Font FONT_TITLE = Font.font("Helvetica", 19);
	private static final String STYLECLASS_TITLE = "header-title";
	
	private Font customFont;
	private HBox header;
	private Label title;
	
	public UIHeader(){
		this.header = new HBox();
		this.header.getStyleClass().add(STYLECLASS_HEADER);
		this.header.setSpacing(590);
		
		this.title = new Label(LABEL_TITLE);
		try {
	    	this.customFont = Font.loadFont(getClass().getResourceAsStream("fonts/Gentona-Medium.ttf"), 19);
	    	this.title.setFont(this.customFont);
	    } catch(Exception e) {
	    	this.title.setFont(FONT_TITLE);
	    }
		this.title.getStyleClass().add(STYLECLASS_TITLE);
		
		this.header.getChildren().addAll(this.title);
		this.header.setAlignment(Pos.CENTER);
	}
	
	public HBox getView(){
		return this.header;
	}
}