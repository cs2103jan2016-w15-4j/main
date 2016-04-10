package dooyit.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import dooyit.logic.api.LogicController;

/**
 * 
 * @author Lim Ta Eu
 */

public class Main extends Application {
	private static final String APP_ICON = "/dooyit/ui/resrc/images/icon.png";
	private static final String APP_TITLE = "Dooyit";
	private static final int MINWIDTH_STAGE = 720;
	private static final int MINHEIGHT_STAGE = 620;

	private UIController ui;
	private LogicController logic;

	public Main() {
	}

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
	
	private void initStage(Stage primaryStage){
		primaryStage.getIcons().add(new Image(APP_ICON));
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setMinWidth(MINWIDTH_STAGE);
		primaryStage.setWidth(MINWIDTH_STAGE);
		primaryStage.setMinHeight(MINHEIGHT_STAGE);
		primaryStage.setHeight(MINHEIGHT_STAGE);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void showToUser(String message) {
		System.out.println(message);
	}
}
