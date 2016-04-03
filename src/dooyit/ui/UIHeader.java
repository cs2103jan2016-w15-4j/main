package dooyit.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UIHeader {
	private static final String STYLECLASS_HEADER = UIStyle.HEADER_VIEW;
	private static final String LABEL_TITLE = "DOOYIT";
	private static final String STYLECLASS_TITLE = UIStyle.HEADER_TITLE;
	private static final String PATH_FONT_AVENIR_MEDIUM = "fonts/Avenir-Medium.ttf";
	private static final int FONTSIZE_TITLE = 19;

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
			initTitleFont();
		} catch (Exception e) {
			this.title.setFont(UIFont.HELVETICA_L);
		}
		this.title.getStyleClass().add(STYLECLASS_TITLE);
	}
	
	private void initTitleFont() throws Exception {
		this.customFont = Font.loadFont(getClass().getResourceAsStream(PATH_FONT_AVENIR_MEDIUM), FONTSIZE_TITLE);
		this.title.setFont(this.customFont);
	}
	
	private void initHeader(){
		this.header = new HBox();
		this.header.getStyleClass().add(STYLECLASS_HEADER);
		this.header.getChildren().addAll(this.title);
		this.header.setAlignment(Pos.CENTER);
	}

	protected HBox getView() {
		return this.header;
	}
}