// @@author A0124278A
package dooyit.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * The <tt>UIHeader</tt> class contains methods to initialize the header view of the UI.
 * @author 	Wu Wenqi
 * @version 0.5
 * @since 	2016-04-10
 */

public class UIHeader {
	private static final String STYLECLASS_HEADER = UIStyle.HEADER_VIEW;
	private static final String LABEL_TITLE = "DOOYIT";
	private static final String STYLECLASS_TITLE = UIStyle.HEADER_TITLE;
	private static final String PATH_FONT_AVENIR_MEDIUM = "fonts/Avenir-Medium.ttf";
	private static final Font HELVETICA_L = Font.font("Helvetica", 19);
	private static final int FONTSIZE_TITLE = 19;

	private Font customFont;
	private HBox header;
	private Label title;
	
	/**
	 * This is the constructor method.
	 */
	public UIHeader() {
		initialize();
	}
	
	/**
	 * This method is used to initialize <tt>UIHeader</tt> class.
	 * It is used by the constructor.
	 */
	private void initialize() {
		initTitle();
		initHeader();
	}
	
	/**
	 * This method is used to initialize the header title.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTitle() {
		this.title = new Label(LABEL_TITLE);
		try {
			initTitleFont();
		} catch (Exception e) {
			this.title.setFont(HELVETICA_L);
		}
		this.title.getStyleClass().add(STYLECLASS_TITLE);
	}
	
	/**
	 * This method is used to initialize the custom header font.
	 * It is used by the <tt>initTitle</tt> method.
	 * @throws Exception if custom font could not be initialized.
	 */
	private void initTitleFont() throws Exception {
		this.customFont = Font.loadFont(getClass().getResourceAsStream(PATH_FONT_AVENIR_MEDIUM), FONTSIZE_TITLE);
		this.title.setFont(this.customFont);
	}
	
	/**
	 * This method is used to initialize the header view box. 
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initHeader() {
		this.header = new HBox();
		this.header.getStyleClass().add(STYLECLASS_HEADER);
		this.header.getChildren().addAll(this.title);
		this.header.setAlignment(Pos.CENTER);
	}

	/**
	 * This method is used to retrieve the header view box.
	 * @return The header view box.
	 */
	protected HBox getView() {
		return this.header;
	}
}