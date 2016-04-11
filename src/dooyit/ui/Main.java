// @@author A0124278A
package dooyit.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * The <tt>Main</tt> class is the entry point for the application. 
 * @author 	Wu Wenqi
 * @version	0.5
 * @since 	2016-04-10
 */

public class Main extends Application {
	private static final String APP_ICON = "/dooyit/ui/resrc/images/icon.png";
	private static final String APP_TITLE = "Dooyit";
	private static final int MINWIDTH_STAGE = 720;
	private static final int MINHEIGHT_STAGE = 620;
	private static final String LOG_MSG_START_SUCCESS = "Stage is successfully initialized and shown.";
	
	private Logger logger;
	private UIController ui;
	
	// Need to keep this for Java Virtual Machine to know where is Main class
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method is used to start the application.
	 * @param primaryStage This is the primary stage of the application.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		initLogger();
		try {
			initStage(primaryStage);
			this.ui = UIController.getInstance(primaryStage);
			primaryStage.setScene(this.ui.getScene());
			primaryStage.show();
			this.logger.log(Level.INFO, LOG_MSG_START_SUCCESS);
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	/**
	 * This method is used to initialize the logger.
	 * It is used by the <tt>initialize</tt> method.
	 */
	private void initLogger() {
		this.logger = Logger.getLogger(getClass().getName());
	}
	
	/**
	 * This method is used to initialize the primary stage.
	 * @param primaryStage This is the primary stage of the application.
	 */
	private void initStage(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(APP_ICON));
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setMinWidth(MINWIDTH_STAGE);
		primaryStage.setWidth(MINWIDTH_STAGE);
		primaryStage.setMinHeight(MINHEIGHT_STAGE);
		primaryStage.setHeight(MINHEIGHT_STAGE);
	}
}
