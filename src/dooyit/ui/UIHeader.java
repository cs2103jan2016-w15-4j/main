// @@author A0124278A
package dooyit.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * The <tt>UIHeader</tt> class contains methods to initialize the header view of the UI.
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class UIHeader {
	private static final String LOG_MSG_CUSTOM_FONT_SUCCESS = "Loading of custom font for UIHeader successful";
	private static final String LOG_MSG_CUSTOM_FONT_FAIL = "Loading of custom font for UIHeader failed. Fallback to Helvetica.";
	private static final String STYLECLASS_HEADER = UIStyle.HEADER_VIEW;
	private static final String LABEL_TITLE = "DOOYIT";
	private static final String STYLECLASS_TITLE = UIStyle.HEADER_TITLE;
	private static final String PATH_FONT_AVENIR_MEDIUM = "fonts/Avenir-Medium.ttf";
	private static final Font HELVETICA_L = Font.font("Helvetica", 19);
	private static final int FONTSIZE_TITLE = 19;

	private Font customFont;
	private HBox header;
	private Label title;
	private Logger logger;
	
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
		initLogger();
		initTitle();
		initHeader();
	}
	
	/**
	 * This method is used to initialize the logger.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initLogger() {
		this.logger = Logger.getLogger(getClass().getName());
	}
	
	/**
	 * This method is used to initialize the header title.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initTitle() {
		this.title = new Label(LABEL_TITLE);
		try {
			initTitleFont();
			this.logger.log(Level.INFO, LOG_MSG_CUSTOM_FONT_SUCCESS);
		} catch (Exception e) {
			this.title.setFont(HELVETICA_L);
			this.logger.log(Level.INFO, LOG_MSG_CUSTOM_FONT_FAIL);
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