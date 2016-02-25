package dooyit.ui;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.gmd.FxFontGoogleMaterial;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UIHeader {
	private HBox header;
	private Label title;
	private HBox icon;
	
	public UIHeader(){
		this.header = new HBox();
		this.header.getStyleClass().add("header-view");
		this.header.setSpacing(590);
		
		this.title = new Label("Dooyit");
		this.title.setFont(Font.font("Helvetica", 19));
		this.title.getStyleClass().add("header-title");
		
		FxIconicsLabel labelDefault =
                (FxIconicsLabel) new FxIconicsLabel.Builder(FxFontGoogleMaterial.Icons.gmd_rate_review)
                        .size(30)
                        .color(MaterialColor.DEEP_ORANGE_100)
                        .build();
		
		this.header.getChildren().addAll(labelDefault, this.title);
		this.header.setAlignment(Pos.CENTER);
	}
	
	public HBox getView(){
		return this.header;
	}
}