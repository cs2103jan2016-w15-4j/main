package dooyit.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UIHeader {
	private static final String STYLECLASS_HEADER = UIStyle.HEADER_VIEW;
	private static final String LABEL_TITLE = "DOOYIT";
	private static final String STYLECLASS_TITLE = UIStyle.HEADER_TITLE;

	private Font customFont;
	private HBox header;
	private Label title;
	
	public UIHeader(){
		initialize();
	}
	
	private void initialize(){
		initTitle();
		initHeader();
	}
	
	private void initTitle(){
		this.title = new Label(LABEL_TITLE);
		try {
			this.customFont = Font.loadFont(getClass().getResourceAsStream("fonts/Avenir-Medium.ttf"), 19);
			this.title.setFont(this.customFont);
		} catch (Exception e) {
			this.title.setFont(UIFont.HELVETICA_L);
		}
		this.title.getStyleClass().add(STYLECLASS_TITLE);
	}
	
	private void initHeader(){
		this.header = new HBox();
		this.header.getStyleClass().add(STYLECLASS_HEADER);
		this.header.setSpacing(590);
		this.header.getChildren().addAll(this.title);
		this.header.setAlignment(Pos.CENTER);
	}

	public HBox getView() {
		return this.header;
	}
}