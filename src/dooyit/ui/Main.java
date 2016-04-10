// @@author A0124278A
package dooyit.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * The <tt>Main</tt> class is the entry point for the application. 
 * @author 	Wu Wenqi
 * @version 0.5
 * @since 	2016-04-10
 */

public class Main extends Application {
	private static final String APP_ICON = "/dooyit/ui/resrc/images/icon.png";
	private static final String APP_TITLE = "Dooyit";
	private static final int MINWIDTH_STAGE = 720;
	private static final int MINHEIGHT_STAGE = 620;

	private UIController ui;

	/**
	 * This method is used to start the application.
	 * @param primaryStage This is the primary stage of the application.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			initStage(primaryStage);
			this.ui = UIController.getInstance(primaryStage);
			primaryStage.setScene(this.ui.getScene());
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
