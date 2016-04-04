# Wu Wenqi A0124278A
###### src\dooyit\ui\UICategoryBox.java
``` java
 *
 */

public class UICategoryBox {
	private static final String STYLECLASS_CAT_NAME = UIStyle.CATEGORY_NAME;
	private static final String STYLECLASS_CAT_CIRCLE = UIStyle.CATEGORY_CIRCLE;
	private static final String STYLECLASS_CAT_BOX = UIStyle.BTN_SELECT_VIEW;
	private static final String STYLECLASS_CAT_BOX_WRAPPER = UIStyle.CATEGORY_BOX_WRAPPER;
	private static final int SPACING_CAT_BOX_WRAPPER = 14;
	private static final int PREFWIDTH_CAT_BOX = 200;

	private Category category;
	private UICategoryBoxContainer parent;
	private HBox categoryBoxWrapper;
	private Label categoryName;
	private Circle categoryCircle;
	private ToggleButton categoryBox;

	protected UICategoryBox(UICategoryBoxContainer parent, Category category) {
		this.parent = parent;
		this.category = category;
		initialize();
	}
	
	private void initialize(){
		initCategoryName();
		initCategoryCircle();
		initCategoryBoxWrapper();
		initCategoryBox();
		initListeners();
	}
	
	private void initCategoryName(){
		this.categoryName = new Label(this.category.getName());
		this.categoryName.getStyleClass().add(STYLECLASS_CAT_NAME);
	}
	
	private void initCategoryCircle(){
		this.categoryCircle = new Circle(4, this.category.getColour());
		this.categoryCircle.getStyleClass().add(STYLECLASS_CAT_CIRCLE);
	}
	
	private void initCategoryBoxWrapper(){
		this.categoryBoxWrapper = new HBox();
		this.categoryBoxWrapper.getChildren().addAll(this.categoryCircle, this.categoryName);
		this.categoryBoxWrapper.getStyleClass().add(STYLECLASS_CAT_BOX_WRAPPER);
		this.categoryBoxWrapper.setSpacing(SPACING_CAT_BOX_WRAPPER);
	}
	
	private void initCategoryBox(){
		this.categoryBox = new ToggleButton();
		this.categoryBox.setGraphic(this.categoryBoxWrapper);
		this.categoryBox.getStyleClass().add(STYLECLASS_CAT_BOX);
		this.categoryBox.setPrefWidth(PREFWIDTH_CAT_BOX);
		this.categoryBox.setToggleGroup(this.parent.getMainViewToggleGroup());
		this.categoryBox.setUserData(UIData.USERDATA_CATEGORY);
	}
	
	private void initListeners(){
		this.categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				parent.processCommand(UIData.CMD_SHOW_CAT + categoryName.getText());
				event.consume();
			}
		});
	}
	
	protected Category getCategory(){
		return this.category;
	}
	
	protected ToggleButton getView() {
		return this.categoryBox;
	}
}
```
###### src\dooyit\ui\UICategoryBoxContainer.java
``` java
 *
 */

public class UICategoryBoxContainer {
	private static final int SPACING_CAT_BOX_CONTAINER_VIEW = 8;

	private UISideMenu parent;
	private VBox categoryBoxContainerView;
	private ArrayList<UICategoryBox> categoryBoxList;
	private ArrayList<Category> categoryList;
	
	protected UICategoryBoxContainer(UISideMenu parent, ArrayList<Category> categoryList){
		this.parent = parent;
		this.categoryList = categoryList;
		initialize();
	}
	
	private void initialize(){
		initCategoryBoxContainerView();
		addAllCategories();
	}
	
	private void initCategoryBoxContainerView(){
		this.categoryBoxContainerView = new VBox();
		this.categoryBoxContainerView.setSpacing(SPACING_CAT_BOX_CONTAINER_VIEW);
	}
	
	private void addAllCategories(){
		this.categoryBoxList = new ArrayList<UICategoryBox>();
		for (int i = 0; i < categoryList.size(); i++){
			addCategory(categoryList.get(i));
		}
	}
	
	private void addCategory(Category category){
		UICategoryBox categoryBox = new UICategoryBox(this, category);
		this.categoryBoxList.add(categoryBox);
		this.categoryBoxContainerView.getChildren().add(categoryBox.getView());
	}
	
	protected ToggleGroup getMainViewToggleGroup(){
		return this.parent.getMainViewToggleGroup();
	}
	
	protected void processCommand(String cmd){
		this.parent.processCommand(cmd);
	}
	
	protected void setActiveCategoryButton(Category category){
		for (UICategoryBox categoryBox : this.categoryBoxList){
			if (categoryBox.getCategory().equals(category)){
				categoryBox.getView().setSelected(true);
				break;
			}
		}
	}
	
	protected VBox getView() {
		return this.categoryBoxContainerView;
	}

	protected void refresh(ArrayList<Category> categoryList) {
		this.categoryBoxContainerView.getChildren().clear();
		this.categoryBoxList.clear();
		for (int i = 0; i < categoryList.size(); i++){
			addCategory(categoryList.get(i));
		}
	}
}
```
###### src\dooyit\ui\UICommandBox.java
``` java
 *
 */

public class UICommandBox {
	private static final String CMD_TEXT_FIELD_PROMPT = "Enter command here. Type 'help' for manual.";
	private static final String STYLECLASS_CMD_TEXT_FIELD = UIStyle.COMMAND_TEXTFIELD;
	private static final int PREFWIDTH_CMD_TEXT_FIELD = 2000;
	private static final String STYLECLASS_CMD_BOX = UIStyle.COMMAND_BOX;

	private HBox commandBox;
	private TextField commandTextField;
	
	protected UICommandBox(){
		initialize();
	}
	
	private void initialize(){
		initCommandTextField();
		initCommandBox();
	}
	
	private void initCommandTextField(){
		this.commandTextField = new TextField();
		this.commandTextField.setPrefWidth(PREFWIDTH_CMD_TEXT_FIELD);
		this.commandTextField.setPromptText(CMD_TEXT_FIELD_PROMPT);
		this.commandTextField.getStyleClass().add(STYLECLASS_CMD_TEXT_FIELD);
	}
	
	private void initCommandBox(){
		this.commandBox = new HBox();
		this.commandBox.getStyleClass().add(STYLECLASS_CMD_BOX);
		this.commandBox.getChildren().addAll(commandTextField);
	}

	protected HBox getView() {
		return this.commandBox;
	}

	protected TextField getCommandTextField() {
		return this.commandTextField;
	}
	
	protected boolean isSelected(){
		return this.commandTextField.isFocused();
	}
	
	protected void select(){
		this.commandTextField.requestFocus();
	}
	
	protected void empty(){
		this.commandTextField.clear();
	}
}
```
###### src\dooyit\ui\UIController.java
``` java
 *
 */

public class UIController {

	static final int WIDTH_SCENE = 720;
	static final int HEIGHT_SCENE = 580;

	static final String STYLECLASS_MAIN_VIEW = UIStyle.MAIN_VIEW;

	private String urlCssCommon;
	private String urlCssThemeLight;
	private String urlCssThemeDark;
	private String urlCssThemeAqua;
	private Scene scene;
	private BorderPane root;
	private UIHeader header;
	private UISideMenu sideMenu;
	private UIDayBoxContainer dayBoxContainer;
	private ScrollPane mainView;
	private UICommandBox commandBox;
	private UIMessageBox messageBox;
	private UIHelpBox helpBox;
	private ChangeListener<Number> resizeListener;
	private ChangeListener<Boolean> maximizeListener;
	private LogicController logic;
	private Stage primaryStage;
	private UIMainViewType activeMainView;

	private static UIController instance = null;

	private UIController(Stage primaryStage, LogicController logic) {
		this.logic = logic;
		this.primaryStage = primaryStage;
		this.activeMainView = UIMainViewType.TODAY;
		initialize();
	}

	public static synchronized UIController getInstance(Stage primaryStage, LogicController logic) {
		if (instance == null) {
			instance = new UIController(primaryStage, logic);
		}
		return instance;
	}

	private void initialize() {
		initCss();
		initHeader();
		initSideMenu();
		initMainView();
		initCommandBox();
		initMessageBox();
		initHelpBox();
		initRoot();
		initScene();
		initListeners();
		updatePositions();
		initPopulate();
	}

	private void initCss() {
		this.urlCssCommon = loadCss(UIStyle.URL_CSS_COMMON);
		this.urlCssThemeLight = loadCss(UIStyle.URL_CSS_THEME_LIGHT);
		this.urlCssThemeDark = loadCss(UIStyle.URL_CSS_THEME_DARK);
		this.urlCssThemeAqua = loadCss(UIStyle.URL_CSS_THEME_AQUA);
	}

	private String loadCss(String cssUrl) {
		return getClass().getResource(cssUrl).toExternalForm();
	}

	private void initHeader() {
		this.header = new UIHeader();
	}

	private void initSideMenu() {
		this.sideMenu = new UISideMenu(this);
		setActiveMenuButton(this.activeMainView);
		ChangeListener<Toggle> toggleListener = new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				Toggle toggle = new_toggle;
				if (toggle == null) {
					toggle = old_toggle;
				}
				toggle.setSelected(true);
				String userData = getSideMenuUserData();
				if (!userData.equals(UIData.USERDATA_CATEGORY)) {
					showSelectedSideMenu(userData);
				}
			}
		};
		this.sideMenu.getMainViewToggleGroup().selectedToggleProperty().addListener(toggleListener);
	}

	private String getSideMenuUserData() {
		return this.sideMenu.getMainViewToggleGroup().getSelectedToggle().getUserData().toString();
	}

	private void showSelectedSideMenu(String userData) {
		switch (userData) {
		case UIData.USERDATA_TODAY:
			activeMainView = UIMainViewType.TODAY;
			processInput(UIData.CMD_SHOW_TODAY);
			break;
		case UIData.USERDATA_EXTENDED:
			activeMainView = UIMainViewType.EXTENDED;
			processInput(UIData.CMD_SHOW_EXTENDED);
			break;
		case UIData.USERDATA_FLOAT:
			activeMainView = UIMainViewType.FLOAT;
			processInput(UIData.CMD_SHOW_FLOAT);
			break;
		case UIData.USERDATA_ALL:
			activeMainView = UIMainViewType.ALL;
			processInput(UIData.CMD_SHOW_ALL);
			break;
		case UIData.USERDATA_COMPLETED:
			activeMainView = UIMainViewType.COMPLETED;
			processInput(UIData.CMD_SHOW_COMPLETED);
			break;
		}
		mainView.setContent(dayBoxContainer.getView());
	}

	private void initMainView() {
		this.dayBoxContainer = new UIDayBoxContainer(this);
		this.mainView = new ScrollPane();
		this.mainView.getStyleClass().add(STYLECLASS_MAIN_VIEW);
		this.mainView.setContent(this.dayBoxContainer.getView());
		this.activeMainView = UIMainViewType.TODAY;
	}

	private void initCommandBox() {
		this.commandBox = new UICommandBox();
	}

	private void initMessageBox() {
		this.messageBox = new UIMessageBox(this.primaryStage);
	}

	private void initHelpBox() {
		this.helpBox = new UIHelpBox();
	}

	private void initRoot() {
		this.root = new BorderPane();
		this.root.setTop(this.header.getView());
		this.root.setLeft(this.sideMenu.getView());
		this.root.setCenter(this.mainView);
		this.root.setBottom(this.commandBox.getView());
	}

	private void initScene() {
		this.scene = new Scene(root, WIDTH_SCENE, HEIGHT_SCENE);
		this.scene.getStylesheets().addAll(this.urlCssCommon, this.urlCssThemeDark);
	}

	private void initListeners() {
		initCommandBoxListeners();
		initSceneListeners();
		initStageListeners();
	}

	private void initCommandBoxListeners() {
		this.commandBox.getCommandTextField().setOnAction((event) -> {
			String commandString = commandBox.getCommandTextField().getText();
			this.commandBox.getCommandTextField().setText(UIData.EMP_STR);
			processInput(commandString);
		});
	}

	private void initSceneListeners() {
		this.resizeListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				updateOnResize();
			}
		};

		this.maximizeListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue,
					Boolean newValue) {
				updateOnResize();
			}
		};

		this.scene.heightProperty().addListener(this.resizeListener);
		this.scene.widthProperty().addListener(this.resizeListener);
		this.primaryStage.maximizedProperty().addListener(this.maximizeListener);

		this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				processKeyEvent(keyEvent);
			}
		});
	}

	private void updateOnResize() {
		this.messageBox.updatePosition();
		this.dayBoxContainer.updatePosition(primaryStage.getWidth());
		if (this.helpBox.isShowing()) {
			this.helpBox.updatePosition(primaryStage.getX(), primaryStage.getY(), primaryStage.getWidth(),
					primaryStage.getHeight());
		}
	}

	private void processKeyEvent(final KeyEvent keyEvent) {
		if (keyEvent.isControlDown()) {
			KeyCode key = keyEvent.getCode();
			if (key == KeyCode.DIGIT1) {
				processInput(UIData.CMD_SHOW_TODAY);
			} else if (key == KeyCode.DIGIT2) {
				processInput(UIData.CMD_SHOW_EXTENDED);
			} else if (key == KeyCode.DIGIT3) {
				processInput(UIData.CMD_SHOW_FLOAT);
			} else if (key == KeyCode.DIGIT4) {
				processInput(UIData.CMD_SHOW_ALL);
			} else if (key == KeyCode.DIGIT5) {
				processInput(UIData.CMD_SHOW_COMPLETED);
			}
		} else {
			if (!commandBox.isSelected()) {
				commandBox.select();
			}
		}
		keyEvent.consume();
	}

	private void initStageListeners() {
		this.primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue,
					Boolean newValue) {
				if (!newValue && messageBox.isOn()) {
					messageBox.tempHide();
				} else if (newValue && messageBox.isOn()) {
					messageBox.display();
				}

				if (!newValue && helpBox.isOn()) {
					helpBox.tempHide();
				} else if (newValue && helpBox.isOn()) {
					helpBox.show(primaryStage);
				}
			}
		});
	}

	private void initPopulate() {
		refreshCategoryMenuView();
		processInput(UIData.CMD_SHOW_TODAY);
	}

	private void processInput(String s) {
		processLogicAction(logic.processInput(s));
	}

	private void processLogicAction(LogicAction logicAction) {
		Action action = logicAction.getAction();
		switch (action) {
		case ADD_TODAY_TASK:
		case EDIT_TO_TODAY_TASK:
		case SHOW_TODAY_TASK:
			refreshMainView(this.logic.getTaskGroupsToday(), UIMainViewType.TODAY);
			break;
		case ADD_NEXT7DAY_TASK:
		case EDIT_TO_NEXT7DAY_TASK:
		case SHOW_NEXT7DAY_TASK:
			refreshMainView(this.logic.getTaskGroupsNext7Days(), UIMainViewType.EXTENDED);
			break;
		case ADD_FLOATING_TASK:
		case EDIT_TO_FLOATING_TASK:
		case SHOW_FLOATING_TASK:
			refreshMainView(this.logic.getTaskGroupsFloating(), UIMainViewType.FLOAT);
			break;
		case ADD_ALL_TASK:
		case EDIT_TO_ALL_TASK:
		case SHOW_ALL_TASK:
			refreshMainView(this.logic.getTaskGroupsAll(), UIMainViewType.ALL);
			break;
		case SHOW_COMPLETED:
			refreshMainView(this.logic.getTaskGroupsCompleted(), UIMainViewType.COMPLETED);
			break;
		case SHOW_CATEGORY:
			refreshMainView(this.logic.getTaskGroupCategory(), this.logic.getSelectedCategory());
			setActiveCategoryButton(this.logic.getSelectedCategory());
			break;
		case DELETE_CATEGORY:
		case CLEAR_CATEGORY:
			refreshCategoryMenuView();
		case DELETE_TASK:
		case SET_CATEGORY:
		case CLEAR_TASK:
		case MARK_TASK:
		case UNMARK_TASK:
		case UNDO:
		case REDO:
		case REMOVE_CAT_FROM_TASK:
			if (this.activeMainView == UIMainViewType.TODAY) {
				refreshMainView(this.logic.getTaskGroupsToday(), UIMainViewType.TODAY);
			} else if (this.activeMainView == UIMainViewType.EXTENDED) {
				refreshMainView(this.logic.getTaskGroupsNext7Days(), UIMainViewType.EXTENDED);
			} else if (this.activeMainView == UIMainViewType.FLOAT) {
				refreshMainView(this.logic.getTaskGroupsFloating(), UIMainViewType.FLOAT);
			} else if (this.activeMainView == UIMainViewType.ALL) {
				refreshMainView(this.logic.getTaskGroupsAll(), UIMainViewType.ALL);
			} else if (this.activeMainView == UIMainViewType.COMPLETED) {
				refreshMainView(this.logic.getTaskGroupsCompleted(), UIMainViewType.COMPLETED);
			} else if (this.activeMainView == UIMainViewType.CATEGORY) {
				refreshMainView(this.logic.getTaskGroupCategory(), this.logic.getSelectedCategory());
			}
			break;
		case ADD_CATEGORY:
			refreshCategoryMenuView();
			break;
		case SEARCH:
			refreshMainView(this.logic.getSearchTaskGroup(), UIMainViewType.SEARCH);
			break;
		case HELP:
			showHelp();
			break;
		case CHANGE_THEME_DEFAULT:
			changeTheme(UITheme.LIGHT);
			break;
		case CHANGE_THEME_DARK:
			changeTheme(UITheme.DARK);
			break;
		case CHANGE_THEME_AQUA:
			changeTheme(UITheme.AQUA);
			break;
		case CHANGE_THEME_CUSTOM:
			changeTheme(UITheme.CUSTOM);
			break;
		case SET_STORAGE_PATH:
			break;
		case ERROR:
			
			break;
		default:
			break;
		}

		if (logicAction.hasMessage()) {
			displayMessage(logicAction.getMessage());
		}
	}

	protected void updatePositions() {
		this.dayBoxContainer.updatePosition(this.primaryStage.getWidth());
	}

	protected Scene getScene() {
		return this.scene;
	}

	private void displayMessage(String msg) {
		this.messageBox.show(msg);
		this.messageBox.hide();
	}

	private void changeTheme(UITheme theme) {
		this.scene.getStylesheets().clear();
		this.scene.getStylesheets().add(urlCssCommon);
		switch (theme) {
		case DARK:
			this.scene.getStylesheets().addAll(urlCssThemeDark);
			break;
		case AQUA:
			this.scene.getStylesheets().addAll(urlCssThemeAqua);
			break;
		default:
			this.scene.getStylesheets().addAll(urlCssThemeLight);
			break;
		}
	}

	private void setActiveMenuButton(UIMainViewType mainViewType) {
		switch (mainViewType) {
		case TODAY:
			this.sideMenu.getTodayBtn().setSelected(true);
			break;
		case EXTENDED:
			this.sideMenu.getExtendedBtn().setSelected(true);
			break;
		case FLOAT:
			this.sideMenu.getFloatBtn().setSelected(true);
			break;
		case ALL:
			this.sideMenu.getAllBtn().setSelected(true);
			break;
		case COMPLETED:
			this.sideMenu.getCompletedBtn().setSelected(true);
			break;
		case SEARCH:
			//unsetAllMenuButtons();
			break;
		default:
			break;
		}
	}

	private void setActiveCategoryButton(Category category){
		this.sideMenu.setActiveCategoryButton(category);
	}
	
	private void unsetAllMenuButtons() {
		this.sideMenu.getMainViewToggleGroup().getToggles().forEach(toggle -> toggle.setSelected(false));
	}

	private void refreshMainView(ArrayList<TaskGroup> taskGroupList) {
		this.dayBoxContainer.refresh(taskGroupList);
		this.mainView.setContent(this.dayBoxContainer.getView());
	}

	private void refreshMainView(ArrayList<TaskGroup> taskGroupList, UIMainViewType mainViewType) {
		this.activeMainView = mainViewType;
		setActiveMenuButton(mainViewType);
		refreshMainView(taskGroupList);
	}

	private void refreshMainView(ArrayList<TaskGroup> taskGroupList, Category category) {
		this.activeMainView = UIMainViewType.CATEGORY;
		refreshMainView(taskGroupList);
	}

	private void refreshCategoryMenuView() {
		this.sideMenu.refreshCategoryMenuView(this.logic.getAllCategories());
	}

	private void showHelp() {
		this.helpBox.show(this.primaryStage);
	}

	protected Stage getStage() {
		return this.primaryStage;
	}

	protected double getStageWidth() {
		return this.primaryStage.getWidth();
	}

	protected void markTask(int taskId) {
		processInput(UIData.CMD_MARK + Integer.toString(taskId));
	}

	protected void processCommand(String cmd) {
		processInput(cmd);
	}
	
	public UIMainViewType getActiveViewType() {
		return this.activeMainView;
	}

}
```
###### src\dooyit\ui\UIData.java
``` java
 *
 */

public class UIData {
	protected static final String USERDATA_TODAY = "day";
	protected static final String USERDATA_EXTENDED = "extended";
	protected static final String USERDATA_FLOAT = "float";
	protected static final String USERDATA_ALL = "all";
	protected static final String USERDATA_COMPLETED = "completed";
	protected static final String USERDATA_CATEGORY = "category";

	protected static final String CMD_SHOW_TODAY = "show today";
	protected static final String CMD_SHOW_EXTENDED = "show next7";
	protected static final String CMD_SHOW_FLOAT = "show float";
	protected static final String CMD_SHOW_ALL = "show all";
	protected static final String CMD_SHOW_COMPLETED = "show completed";
	protected static final String CMD_SHOW_CAT = "show cat ";
	protected static final String CMD_SHOW = "show ";
	protected static final String CMD_MARK = "mark ";

	protected static final String EMP_STR = "";
	protected static final String COMMA_SPLIT = ", ";
	
	protected static final String TODAY = "Today";
}
```
###### src\dooyit\ui\UIDayBox.java
``` java
 *
 */

public class UIDayBox {
	private static final String STYLECLASS_DAY_BOX = UIStyle.DAY_BOX;
	private static final String STYLECLASS_DAY_TITLE = UIStyle.DAY_TITLE;
	private static final String STYLECLASS_DAY_TITLE_FADED = UIStyle.DAY_TITLE_FADED;
	private static final String MSG_TODAY_NO_TASKS = "No tasks for today. Enjoy your day!";

	private UIDayBoxContainer parent;
	private VBox dayBox;
	private Label dayTitle;
	private ArrayList<Task> taskList;
	private ArrayList<UITaskBox> taskBoxList;
	private TaskGroup taskGroup;
	
	protected UIDayBox(UIDayBoxContainer parent, TaskGroup taskGroup){
		this.parent = parent;
		this.taskGroup = taskGroup;
		initialize();
	}
	
	private void initialize(){
		this.taskList = this.taskGroup.getTasks();
		this.taskBoxList = new ArrayList<UITaskBox>();
		initDayBox();
        initAllTasks();
	}
	
	private void initDayBox(){
		initDayTitle();
        this.dayBox = new VBox();
		this.dayBox.getStyleClass().add(STYLECLASS_DAY_BOX);
        this.dayBox.getChildren().add(this.dayTitle);
	}
	
	private void initDayTitle(){
        this.dayTitle = new Label(this.taskGroup.getTitle());
        this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE);
        if (taskList.size() == 0 && !this.taskGroup.getTitle().equals(UIData.TODAY)){
        	this.dayTitle.getStyleClass().add(STYLECLASS_DAY_TITLE_FADED);
        }
	}
	
	private void initAllTasks(){
		if (taskList.size() > 0){
			addAllTasks();
		} else {
			displayNoTasks();
		}
	}
	
	private void addAllTasks(){
		for (int i = 0; i < this.taskList.size(); i++){
			addTask(this.taskList.get(i));
		}
	}
	
	private void displayNoTasks(){
		if (this.taskGroup.getTitle().contains(UIData.TODAY)){
			UIMainViewType activeView = getActiveMainView();
			if (activeView == UIMainViewType.EXTENDED || activeView == UIMainViewType.TODAY){
				setNoTaskMessage();
			}
		}
	}
	
	private void addTask(Task task){
		UITaskBox taskBox = new UITaskBox(this, task);
		this.taskBoxList.add(taskBox);
		AnchorPane taskBoxView = taskBox.getView();
        this.dayBox.getChildren().add(taskBoxView);
	}
	
	private void setNoTaskMessage(){
		Label taskMessageView = new UITaskMessage(MSG_TODAY_NO_TASKS).getView();
		this.dayBox.getChildren().add(taskMessageView);
	}
	
	private UIMainViewType getActiveMainView(){
		return this.parent.getActiveMainView();
	}

	protected double getStageWidth() {
		return this.parent.getStageWidth();
	}

	protected void markTask(int taskId) {
		this.parent.markTask(taskId);
	}
	
	protected VBox getView() {
		return this.dayBox;
	}

	protected void updatePosition(double stageWidth) {
		this.taskBoxList.forEach((taskBox) -> {
			taskBox.updatePosition(stageWidth);
		});
	}
}
```
###### src\dooyit\ui\UIDayBoxContainer.java
``` java
 *
 */

public class UIDayBoxContainer {
	private UIController parent;
	private ArrayList<UIDayBox> dayBoxList;
	private VBox dayBoxContainer;
	
	protected UIDayBoxContainer(UIController parent){
		this.parent = parent;
		this.dayBoxContainer = new VBox();
		this.dayBoxList = new ArrayList<UIDayBox>();
	}
	
	private void addDayBox(TaskGroup taskGroup){
		UIDayBox dayBox = new UIDayBox(this, taskGroup);
		this.dayBoxList.add(dayBox);
		this.dayBoxContainer.getChildren().add(dayBox.getView());
	}
	
	protected void refresh(ArrayList<TaskGroup> taskGroupList) {
		this.dayBoxContainer.getChildren().clear();
		taskGroupList.forEach((taskGroup)->{
			addDayBox(taskGroup);
		});
	}

	protected double getStageWidth() {
		return this.parent.getStageWidth();
	}

	protected void markTask(int taskId) {
		this.parent.markTask(taskId);
	}
	
	protected UIMainViewType getActiveMainView(){
		return this.parent.getActiveViewType();
	}
	
	protected VBox getView(){
		return this.dayBoxContainer;
	}

	protected void updatePosition(double stageWidth) {
		this.dayBoxList.forEach((dayBox) -> {
			dayBox.updatePosition(stageWidth);
		});
	}
}
```
###### src\dooyit\ui\UIHeader.java
``` java
 *
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
			this.title.setFont(HELVETICA_L);
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
```
###### src\dooyit\ui\UIHelpBox.java
``` java
 *
 */

public class UIHelpBox {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 450;
	private static final String STYLECLASS_TITLE = UIStyle.HELP_BOX_TITLE;
	private static final String LABEL_TITLE = "Hola! Here are some tips to get you started.";
	private static final int LABEL_HEIGHT = 20;
	private static final String STYLECLASS_CONTENT_LABEL = UIStyle.HELP_BOX_CONTENT_LABEL;
	private static final int SPACING_CONTENT_WRAPPER = 80;
	private static final int SPACING_CONTENT = 10;
	private static final String STYLECLASS_HELP_BOX_WRAPPER = UIStyle.HELP_BOX_WRAPPER;
	private static final int SPACING_HELP_BOX_WRAPPER = 60;
	private static final String STYLECLASS_CLOSE_LABEL = "help-box-close-label";
	private static final String CLOSE_LABEL_TITLE = "CLOSE";
	private static final String DESC_ADD_TASK = "Add task";
	private static final String DESC_MARK_TASK = "Mark task as complete";
	private static final String DESC_DEL_TASK = "Delete task";
	private static final String DESC_SHOW_VIEW = "Show different views";
	private static final String DESC_QUIT_APP = "Quit Dooyit";
	private static final String CMD_ADD_TASK = "add <task name>";
	private static final String CMD_MARK_TASK = "mark <task index>";
	private static final String CMD_DEL_TASK = "delete <task index>";
	private static final String CMD_SHOW_VIEW = "show <view>";
	private static final String CMD_QUIT_APP = "exit";

	private Popup helpBox;
	private VBox helpBoxWrapper;
	private Label title;
	private boolean isOn;
	private HBox contentWrapper;
	private VBox leftContent;
	private VBox rightContent;
	private Label closeLabel;
	
	private static ArrayList<String> cmdNameList = new ArrayList<String>(){{
		add(DESC_ADD_TASK);
		add(DESC_MARK_TASK);
		add(DESC_DEL_TASK);
		add(DESC_SHOW_VIEW);
		add(DESC_QUIT_APP);
	}};
	
	private static ArrayList<String> cmdDescList = new ArrayList<String>(){{
		add(CMD_ADD_TASK);
		add(CMD_MARK_TASK);
		add(CMD_DEL_TASK);
		add(CMD_SHOW_VIEW);
		add(CMD_QUIT_APP);
	}};

	protected UIHelpBox() {
		initialize();
	}
	
	private void initialize(){
		this.isOn = false;
		initTitle();
		initLeftContent();
		initRightContent();
		initContentWrapper();
		initCloseLabel();
		initHelpBox();
	}
	
	private void initTitle(){
		this.title = new Label(LABEL_TITLE);
		this.title.setAlignment(Pos.CENTER);
		this.title.getStyleClass().add(STYLECLASS_TITLE);
	}
	
	private void initLeftContent(){
		this.leftContent = new VBox();
		this.leftContent.setSpacing(SPACING_CONTENT);

		cmdNameList.forEach((cmdName) -> {
			Label cmdNameLabel = makeLabel(cmdName);
			this.leftContent.getChildren().add(cmdNameLabel);
		});
	}
	
	private void initRightContent(){
		this.rightContent = new VBox();
		this.rightContent.setSpacing(SPACING_CONTENT);

		cmdDescList.forEach((cmdDesc) -> {
			Label cmdDescLabel = makeLabel(cmdDesc);
			this.rightContent.getChildren().add(cmdDescLabel);
		});
	}
	
	private void initContentWrapper(){
		this.contentWrapper = new HBox();
		this.contentWrapper.setSpacing(SPACING_CONTENT_WRAPPER);
		this.contentWrapper.getChildren().addAll(this.leftContent, this.rightContent);
	}
	
	private void initCloseLabel(){
		this.closeLabel = new Label(CLOSE_LABEL_TITLE);
		this.closeLabel.getStyleClass().add(STYLECLASS_CLOSE_LABEL);
		this.closeLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				hide();
				event.consume();
			}
		});
	}
	
	private void initHelpBox(){
		this.helpBoxWrapper = new VBox();
		this.helpBoxWrapper.setSpacing(SPACING_HELP_BOX_WRAPPER);
		this.helpBoxWrapper.setAlignment(Pos.CENTER);
		this.helpBoxWrapper.getChildren().addAll(this.title, this.contentWrapper, this.closeLabel);
		this.helpBoxWrapper.getStyleClass().add(STYLECLASS_HELP_BOX_WRAPPER);
		this.helpBox = new Popup();
		this.helpBox.getContent().addAll(this.helpBoxWrapper);
	}
	
	private Label makeLabel(String s){
		Label label = new Label(s);
		label.getStyleClass().add(STYLECLASS_CONTENT_LABEL);
		label.setMaxHeight(LABEL_HEIGHT);
		label.setPrefHeight(LABEL_HEIGHT);
		label.setMinHeight(LABEL_HEIGHT);
		return label;
	}

	protected boolean isShowing() {
		return this.helpBox.isShowing();
	}

	protected void updatePosition(double stageX, double stageY, double stageWidth, double stageHeight) {
		this.helpBox.setX(stageX + stageWidth / 2 - WIDTH / 2);
		this.helpBox.setY(stageY + stageHeight / 2 - HEIGHT / 2);
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
	}

	protected void show(Stage primaryStage) {
		this.isOn = true;
		this.helpBoxWrapper.setPrefSize(WIDTH, HEIGHT);
		this.helpBox.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - WIDTH / 2);
		this.helpBox.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - HEIGHT / 2);
		this.helpBox.show(primaryStage);
	}
	
	protected void tempHide(){
		this.helpBox.hide();
	}

	protected void hide() {
		this.isOn = false;
		this.helpBox.hide();
	}
	
	protected boolean isOn() {
		return this.isOn;
	}

}
```
###### src\dooyit\ui\UIMainViewType.java
``` java
 *
 */

public enum UIMainViewType {
	TODAY, EXTENDED, FLOAT, ALL, COMPLETED, CATEGORY, SEARCH
}
```
###### src\dooyit\ui\UIMessageBox.java
``` java
 *
 */

public class UIMessageBox {
	private static final String STYLECLASS_MESSAGE_BOX_LABEL = UIStyle.MESSAGE_BOX_LABEL;
	private static final int FADE_TIME = 6000;
	private static final int PREFHEIGHT = 40;
	private static final int PAD_X = 0;
	private static final int PAD_Y = 105;
	private static final double FT_INITIAL_VAL = 1.0;
	private static final double FT_FINAL_VAL = 0.0;
	private static final int FT_CYCLE_COUNT = 1;

	private Stage primaryStage;
	private Popup messageBox;
	private Label messageLabel;
	private boolean isOn;
	private FadeTransition ft;

	protected UIMessageBox(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
	}
	
	private void initialize(){
		initMessageLabel();
		initMessageBox();
		initTransitions();
	}
	
	private void initMessageLabel(){
		this.messageLabel = new Label();
		this.messageLabel.getStyleClass().add(STYLECLASS_MESSAGE_BOX_LABEL);
	}
	
	private void initMessageBox(){
		this.messageBox = new Popup();
		this.messageBox.getContent().addAll(this.messageLabel);
	}
	
	private void initTransitions(){
		this.ft = new FadeTransition(Duration.millis(FADE_TIME), this.messageLabel);
		this.ft.setFromValue(FT_INITIAL_VAL);
		this.ft.setToValue(FT_FINAL_VAL);
		this.ft.setCycleCount(FT_CYCLE_COUNT);
		this.ft.setAutoReverse(false);
		this.ft.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				messageBox.hide();
				isOn = false;
			}
		});
	}
	
	private void update(double x, double y, double width, double height){
		this.messageBox.setX(x);
		this.messageBox.setY(y);
		this.messageLabel.setPrefSize(width, height);
	}
	
	protected boolean isShowing(){
		return this.messageBox.isShowing();
	}
	
	protected void updatePosition(){
		double x = this.primaryStage.getX() + PAD_X;
		double y = this.primaryStage.getY() + this.primaryStage.getHeight() - PAD_Y;
		double width = this.primaryStage.getWidth() - 2*PAD_X;
		update(x, y, width, PREFHEIGHT);
	}
	
	protected void display(){
		updatePosition();
		this.messageBox.show(this.primaryStage);
	}
	
	
	protected void show(String msg){
		this.isOn = true;
		this.messageLabel.setText(msg);
		display();
		this.ft.playFromStart();
	}

	protected void tempHide() {
		this.messageBox.hide();
	}

	protected void hide() {
		this.ft.play();
	}

	protected boolean isOn() {
		return this.isOn;
	}
}
```
###### src\dooyit\ui\UISideMenu.java
``` java
 *
 */

public class UISideMenu {

	private static final String LABEL_TODAY = "Today";
	private static final String LABEL_EXTENDED = "Next 7 days";
	private static final String LABEL_FLOAT = "Float";
	private static final String LABEL_ALL = "All";
	private static final String LABEL_COMPLETED = "Completed";
	private static final String STYLECLASS_MENU = UIStyle.MENU_VIEW;
	private static final int SPACING_MENU = 8;
	private static final String LABEL_CATEGORY_TITLE = "CATEGORIES";
	private static final String STYLECLASS_CATEGORY_TITLE = UIStyle.CATEGORY_TITLE;
	private static final String STYLECLASS_BTN_LABEL = UIStyle.BTN_SELECT_LABEL;
	private static final String COLOR_BTN_ICON = MaterialColor.GREY_400;
	private static final int SPACING_BTN_CONTENT = 8;
	private static final String STYLECLASS_MENU_BTN = UIStyle.BTN_SELECT_VIEW;
	private static final int PREFWIDTH_MENU_BTN = 200;

	private ScrollPane menuPane;
	private VBox menu;
	private ToggleGroup mainViewToggleGroup;
	private ToggleButton todayBtn;
	private ToggleButton extendedBtn;
	private ToggleButton floatBtn;
	private ToggleButton allBtn;
	private ToggleButton completedBtn;
	private Label categoryTitle;
	private UICategoryBoxContainer categoryBoxContainer;
	private UIController parent;

	protected UISideMenu(UIController parent) {
		this.parent = parent;
		initialize();
	}
	
	private void initialize(){
		this.mainViewToggleGroup = new ToggleGroup();
		initMenuButtons();
		initCategoryButtons();
		initMenu();
		initMenuPane();
	}
	
	private void initMenuButtons(){
		this.todayBtn = getMenuButton(LABEL_TODAY, UIData.USERDATA_TODAY, FxFontCommunity.Icons.cmd_calendar);
		this.extendedBtn = getMenuButton(LABEL_EXTENDED, UIData.USERDATA_EXTENDED,
				FxFontCommunity.Icons.cmd_numeric_7_box_multiple_outline);
		this.floatBtn = getMenuButton(LABEL_FLOAT, UIData.USERDATA_FLOAT, FxFontCommunity.Icons.cmd_image_filter_drama);
		this.allBtn = getMenuButton(LABEL_ALL, UIData.USERDATA_ALL, FxFontCommunity.Icons.cmd_calendar_multiple);
		this.completedBtn = getMenuButton(LABEL_COMPLETED, UIData.USERDATA_COMPLETED, FxFontCommunity.Icons.cmd_comment_check);
	}
	
	private void initCategoryButtons(){
		 this.categoryTitle = new Label(LABEL_CATEGORY_TITLE);
	     this.categoryTitle.getStyleClass().add(STYLECLASS_CATEGORY_TITLE);	
	     this.categoryBoxContainer = new UICategoryBoxContainer(this, new ArrayList<Category>());
	}
	
	private void initMenu(){
		this.menu = new VBox();
		this.menu.setSpacing(SPACING_MENU);
		this.menu.getStyleClass().add(STYLECLASS_MENU);
		this.menu.getChildren().addAll(this.todayBtn, this.extendedBtn, this.floatBtn, this.allBtn, this.completedBtn, this.categoryTitle, this.categoryBoxContainer.getView());
	}
	
	private void initMenuPane(){
		this.menuPane = new ScrollPane();
		this.menuPane.getStyleClass().add("menu-pane");
		this.menuPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		this.menuPane.setFitToWidth(true);
		this.menuPane.setContent(this.menu);
	}
	
	private ToggleButton getMenuButton(String title, String userData, FxFontCommunity.Icons icon){
		FxIconicsLabel btnIcon = getMenuBtnIcon(icon);
		Label btnLabel = getMenuBtnLabel(title); 
		return makeMenuButton(btnIcon, btnLabel, userData);
	}
	
	private FxIconicsLabel getMenuBtnIcon(FxFontCommunity.Icons icon){
		return (FxIconicsLabel) new FxIconicsLabel
        .Builder(icon)
        .size(18)
        .color(COLOR_BTN_ICON).build();
	}
	
	private Label getMenuBtnLabel(String title){
		Label btnLabel = new Label(title);
		btnLabel.getStyleClass().add(STYLECLASS_BTN_LABEL);
		return btnLabel;
	}
	
	private ToggleButton makeMenuButton(FxIconicsLabel icon, Label btnLabel, String userData){
		HBox btnContent = new HBox();
		btnContent.setSpacing(SPACING_BTN_CONTENT);
		btnContent.getChildren().addAll(icon, btnLabel);
		ToggleButton menuBtn = new ToggleButton();
		menuBtn.setGraphic(btnContent);
		menuBtn.setPrefWidth(PREFWIDTH_MENU_BTN);
		menuBtn.getStyleClass().add(STYLECLASS_MENU_BTN);
		menuBtn.setToggleGroup(this.mainViewToggleGroup);
		menuBtn.setUserData(userData);
		return menuBtn;
	}
	
	protected void setActiveCategoryButton(Category category){
		this.categoryBoxContainer.setActiveCategoryButton(category);
	}
	

	protected ScrollPane getView() {
		return this.menuPane;
	}

	protected ToggleButton getTodayBtn() {
		return this.todayBtn;
	}

	protected ToggleButton getExtendedBtn() {
		return this.extendedBtn;
	}
	
	protected ToggleButton getFloatBtn() {
		return this.floatBtn;
	}

	protected ToggleButton getAllBtn() {
		return this.allBtn;
	}

	protected ToggleButton getCompletedBtn() {
		return this.completedBtn;
	}

	protected ToggleGroup getMainViewToggleGroup() {
		return this.mainViewToggleGroup;
	}

	protected void refreshCategoryMenuView(ArrayList<Category> categoryList) {
		this.categoryBoxContainer.refresh(categoryList);
	}
	
	protected void processCommand(String cmd){
		this.parent.processCommand(cmd);
	}

}
```
###### src\dooyit\ui\UIStyle.java
``` java
 *
 */

public class UIStyle {
	protected static final String URL_CSS_COMMON = "resrc/css/common.css";
	protected static final String URL_CSS_THEME_LIGHT = "resrc/css/theme_light.css";
	protected static final String URL_CSS_THEME_DARK = "resrc/css/theme_dark.css";
	protected static final String URL_CSS_THEME_AQUA = "resrc/css/theme_aqua.css";
	
	protected static final String MAIN_VIEW = "main-view";
	protected static final String HEADER_VIEW = "header-view";
	protected static final String HEADER_TITLE = "header-title";

	protected static final String CATEGORY_TITLE = "category-title";
	protected static final String CATEGORY_NAME = "category-name";
	protected static final String CATEGORY_CIRCLE = "category-circle";
	protected static final String CATEGORY_BOX_WRAPPER = "category-box-wrapper";

	protected static final String BTN_SELECT_VIEW = "btn-select-view";
	protected static final String BTN_SELECT_LABEL = "btn-select-label";
	protected static final String MENU_VIEW = "menu-view";

	protected static final String DAY_BOX = "day-box";
	protected static final String DAY_TITLE = "day-title";
	protected static final String DAY_TITLE_FADED = "day-title-faded";

	protected static final String TASK_ID = "task-id";
	protected static final String TASK_CHECKBOX = "task-checkbox";
	protected static final String TASK_NAME = "task-name";
	protected static final String TASK_PERIOD = "task-period";
	protected static final String TASK_CATEGORY_LABEL = "task-category-label";
	protected static final String DAY_TASK_BOX = "day-task-box";
	protected static final String TASK_MESSAGE = "task-message";

	protected static final String COMMAND_TEXTFIELD = "command-textfield";
	protected static final String COMMAND_BOX = "command-box";

	protected static final String MESSAGE_BOX_LABEL = "message-box-label";
	protected static final String HELP_BOX_TITLE = "help-box-title";
	protected static final String HELP_BOX_CONTENT_LABEL = "help-box-content-label";
	protected static final String HELP_BOX_WRAPPER = "help-box-wrapper";
}
```
###### src\dooyit\ui\UITaskBox.java
``` java
 *
 */

public class UITaskBox {

	private static final String STYLECLASS_TASK_CHECKBOX = UIStyle.TASK_CHECKBOX;
	private static final String STYLECLASS_TASK_ID = UIStyle.TASK_ID;
	private static final int PREFWIDTH_TASK_ID = 20;
	private static final String STYLECLASS_TASK_NAME = UIStyle.TASK_NAME;
	private static final int WIDTH_TO_SUBTRACT = 55;
	private static final String STYLECLASS_TASK_PERIOD = UIStyle.TASK_PERIOD;
	private static final String STYLECLASS_TASK_CATEGORY_LABEL = UIStyle.TASK_CATEGORY_LABEL;
	private static final int PREFWIDTH_TASK_CATEGORY_LABEL = 120;
	private static final String STYLECLASS_TASK_BOX = UIStyle.DAY_TASK_BOX;
	private static final int RADIUS_CAT_CIRCLE = 4;
	private static final int WIDTH_MENU = 180;
	private static final int PAD_X = 20;
	private static final String DOUBLE_SPACE = "  ";
	private static final double ANCHOR_TOP = 5.0;
	private static final double ANCHOR_LEFT = 0.0;
	private static final double ANCHOR_RIGHT = 0.0;

	private UIDayBox parent;
	private Task task;
	private CheckBox taskCheckBox;
	private Label taskId;
	private Label taskName;
	private Label taskPeriod;
	private HBox taskDetailBox;
	private Label taskCategoryLabel;
	private Circle taskCategoryCircle;
	private HBox taskCategoryBox;
	private AnchorPane taskBox;

	public UITaskBox(UIDayBox parent, Task task) {
		this.task = task;
		this.parent = parent;
		initialize();
	}
	
	private void initialize(){
		initTaskCheckBox();
		initTaskId();
		initTaskCategoryLabel();
		initTaskPeriod();
		initTaskName();
		initTaskDetailBox();
		initTaskCategoryBox();
		initTaskBox();
		initListeners();
		updateTaskBoxWidth();
	}
	
	private void initTaskCheckBox(){
		this.taskCheckBox = new CheckBox();
		this.taskCheckBox.getStyleClass().add(STYLECLASS_TASK_CHECKBOX);
		if (this.task.isCompleted()) {
			this.taskCheckBox.setSelected(true);
		}
	}
	
	private void initTaskId(){
		this.taskId = new Label(Integer.toString(this.task.getId()));
	    this.taskId.getStyleClass().add(STYLECLASS_TASK_ID);
	    this.taskId.setPrefWidth(PREFWIDTH_TASK_ID);
	}
	
	private void initTaskPeriod(){
		this.taskPeriod = new Label(getTaskPeriodString());
	    this.taskPeriod.getStyleClass().add(STYLECLASS_TASK_PERIOD);
	}
	
	private String getTaskPeriodString(){
		String s = this.task.getDateString();
		if (!s.isEmpty()){
			s += DOUBLE_SPACE;
		} 
		return s;
	}
	
	private void initTaskCategoryLabel(){
		this.taskCategoryLabel = new Label();
		setCategoryCircle();
	    this.taskCategoryLabel.setAlignment(Pos.CENTER_RIGHT);
	    this.taskCategoryLabel.getStyleClass().add(STYLECLASS_TASK_CATEGORY_LABEL);
	    this.taskCategoryLabel.setPrefWidth(PREFWIDTH_TASK_CATEGORY_LABEL);
	}
	
	private void setCategoryCircle(){
		if (this.task.hasCategory()){
			Category category = this.task.getCategory();
			this.taskCategoryLabel.setText(category.getName());
			this.taskCategoryCircle = new Circle(RADIUS_CAT_CIRCLE, category.getColour());
		} else {
			this.taskCategoryCircle = new Circle(RADIUS_CAT_CIRCLE, Color.TRANSPARENT);
		}
	}
	
	private void initTaskName(){
		this.taskName = new Label(this.task.getName());
	    this.taskName.getStyleClass().add(STYLECLASS_TASK_NAME);
	}
	
	private void initTaskDetailBox(){
		this.taskDetailBox = new HBox();
		this.taskDetailBox.getChildren().addAll(this.taskCheckBox, this.taskId, this.taskPeriod, this.taskName);
		this.taskDetailBox.setAlignment(Pos.CENTER_LEFT);
	}
	
	private void initTaskCategoryBox(){
		this.taskCategoryBox = new HBox();
		this.taskCategoryBox.getChildren().addAll(this.taskCategoryLabel, this.taskCategoryCircle);
		this.taskCategoryBox.setAlignment(Pos.CENTER_RIGHT);
	}
	
	private void initTaskBox(){
		 this.taskBox = new AnchorPane();
		 AnchorPane.setTopAnchor(this.taskDetailBox, ANCHOR_TOP);
		 AnchorPane.setTopAnchor(this.taskCategoryBox, ANCHOR_TOP);
		 AnchorPane.setLeftAnchor(this.taskDetailBox, ANCHOR_LEFT);
		 AnchorPane.setRightAnchor(this.taskCategoryBox, ANCHOR_RIGHT);
		 this.taskBox.getStyleClass().add(STYLECLASS_TASK_BOX);
		 this.taskBox.getChildren().addAll(this.taskDetailBox, this.taskCategoryBox);
	}
	
	private void initListeners(){
	    this.taskCheckBox.setOnAction((event) -> {
	    	if (!this.task.isCompleted()){
	    		this.parent.markTask(this.task.getId());
	    	}
	    });
	}

	private void updateTaskBoxWidth(double stageWidth) {
		double widthToSubtract = 0;
		widthToSubtract += WIDTH_MENU;
		widthToSubtract += 2 * PAD_X;
		widthToSubtract += WIDTH_TO_SUBTRACT;
		double width = stageWidth - widthToSubtract;
		this.taskBox.setMinWidth(width);
		this.taskBox.setPrefWidth(width);
		this.taskBox.setMaxWidth(width);
	}
	
	private void updateTaskBoxWidth(){
		double width = this.parent.getStageWidth();
		System.out.println("TASKBOX WIDTH " + width);
	    updateTaskBoxWidth(width);
	}
	
	protected AnchorPane getView() {
		return this.taskBox;
	}

	protected void updatePosition(double stageWidth) {
		updateTaskBoxWidth(stageWidth);
	}
}
```
###### src\dooyit\ui\UITaskMessage.java
``` java
 *
 */

public class UITaskMessage {

	private static final String STYLESHEET_TASK_MESSAGE = UIStyle.TASK_MESSAGE;
	private Label taskMessage;
	
	protected UITaskMessage(String message){
		this.taskMessage = new Label(message);
		this.taskMessage.getStyleClass().add(STYLESHEET_TASK_MESSAGE);
	}
	
	protected Label getView(){
		return this.taskMessage;
	}
	
}
```
###### src\dooyit\ui\UITheme.java
``` java
 *
 */

public enum UITheme {
	LIGHT, DARK, AQUA, CUSTOM
}
```
###### test\dooyit\common\datatype\CategoryTest.java
``` java
 *
 */

public class CategoryTest {
	Category cat1;
	Category cat2;
	Category cat3;

	@Before
	public void setUp() {
		cat1 = new Category("School", CustomColor.BLUE);
		cat2 = new Category("Chores");
		cat3 = new Category("CHORES");
	}

	@Test
	public void testGetName() {
		assertEquals(cat1.getName(), "School");
		assertEquals(cat2.getName(), "Chores");
	}

	@Test
	public void testGetColor() {
		assertTrue(cat1.getColour().equals(CustomColor.BLUE.getColor()));
	}

	@Test
	public void testGetCustomColor() {
		assertTrue(cat1.getCustomColour().equals(CustomColor.BLUE));
	}

	@Test
	public void testGetCustomColorName() {
		assertEquals(cat1.getCustomColourName(), "blue");
	}

	@Test
	public void testEquals() {
		assertFalse(cat1.equals(cat2));
		assertTrue(cat2.equals(cat3));
		assertTrue(cat2.equals("chores"));
	}

	@Test
	public void testToString() {
		String s = "School " + CustomColor.BLUE.toString();
		assertEquals(cat1.toString(), s);
	}

}
```
###### test\dooyit\common\datatype\DateTimeTest.java
``` java
 *
 */

public class DateTimeTest {
	DateTime dt1, dt2, dt3, dt4, currDT;

	@Before
	public void setUp() {
		dt1 = new DateTime();
		int[] date = { 28, 3, 2016 };
		dt2 = new DateTime(date);
		dt3 = new DateTime(date, 1200);
		dt4 = new DateTime(date, 0);
		currDT = new DateTime();
	}

	@Test
	public void getMultiDayString() {
		int[] startDate = new int[] { 3, 4, 2016 };
		int startTime = 900;
		DateTime start = new DateTime(startDate, startTime);

		int[] endDate = new int[] { 4, 4, 2016 };
		int endTime = 1600;
		DateTime end = new DateTime(endDate, endTime);

		ArrayList<String> listOfString = DateTime.getMultiDayString(start, end);
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("09:00 - 23:59", "00:00 - 16:00"));
		assertEquals(expected, listOfString);
	}

	@Test
	public void compareTo() {
		assertEquals(dt1.compareTo(dt2), 1);
		assertEquals(dt2.compareTo(dt3), 1);
		assertEquals(dt2.compareTo(dt4), 1);
	}

	@Test
	public void getDayStr() {
		assertEquals(dt1.getDayStr(), currDT.getDayStr());
		assertEquals(dt2.getDayStr(), "Monday");
		assertEquals(dt3.getDayStr(), "Monday");
	}

	@Test
	public void getDayInt() {
		assertEquals(dt1.getDayInt(), currDT.getDayInt());
		assertEquals(dt2.getDayInt(), 1);
	}

	@Test
	public void getDate() {
		assertEquals(dt1.getDate(), currDT.getDate());
	}

	@Test
	public void getTime24hStr() {
		assertEquals(dt2.getTime24hStr(), "-1");
		assertEquals(dt3.getTime24hStr(), "12:00");
		assertEquals(dt4.getTime24hStr(), "00:00");
	}

	@Test
	public void getTime12hStr() {
		assertEquals(dt2.getTime12hStr(), "-1");
		assertEquals(dt3.getTime12hStr(), "12.00 pm");
		assertEquals(dt4.getTime12hStr(), "12.00 am");
	}

	@Test
	public void getTimeInt() {
		assertEquals(dt2.getTimeInt(), -1);
		assertEquals(dt3.getTimeInt(), 1200);
		assertEquals(dt4.getTimeInt(), 0);
	}

	@Test
	public void getDD() {
		assertEquals(dt2.getDD(), 28);
		assertEquals(dt3.getDD(), 28);
	}

	@Test
	public void getMM() {
		assertEquals(dt2.getMM(), 3);
		assertEquals(dt3.getMM(), 3);
	}

	@Test
	public void getYY() {
		assertEquals(dt2.getYY(), 2016);
		assertEquals(dt3.getYY(), 2016);
	}

	@Test
	public void testToString() {
		assertEquals(dt2.toString(), "28 Mar 2016 Monday -1 -1");
		assertEquals(dt3.toString(), "28 Mar 2016 Monday 12:00 12.00 pm");
		assertEquals(dt4.toString(), "28 Mar 2016 Monday 00:00 12.00 am");
	}

	@Test
	public void hasTime() {
		assertFalse(dt2.hasTime());
		assertTrue(dt3.hasTime());
		assertTrue(dt4.hasTime());
	}

	@Test
	public void isTheSameDateAs() {
		assertTrue(dt2.isTheSameDateAs(dt3));
		assertTrue(dt3.isTheSameDateAs(dt4));
	}

	@Test
	public void increaseByOneDay() {
		dt1.increaseByOneDay();
		currDT.increaseByOneDay();
		assertEquals(dt1.getDD(), currDT.getDD());
	}

	/*
	 * @Test public void convertToSavableString() {
	 * assertEquals(dt3.convertToSavableString(), "28 3 2016"); }
	 */
}
```
###### test\dooyit\common\datatype\DeadlineTaskTest.java
``` java
 *
 */

public class DeadlineTaskTest {

	DeadlineTask task1, task2, task3;
	DateTime dt1, dt2;

	@Before
	public void setUp() {
		int[] date = { 22, 2, 2016 };
		dt1 = new DateTime(date, 800);
		task1 = new DeadlineTask("Go shopping", dt1);
		dt2 = new DateTime();
		task2 = new DeadlineTask("Find a job", dt2);
		task3 = new DeadlineTask("Find a job", dt2);
	}

	@Test
	public void getDateTimeDeadline() {
		assertTrue(task1.getDateTimeDeadline().equals(dt1));
	}

	@Test
	public void isToday() {
		assertTrue(task2.isSameDate(dt2));
	}

	@Test
	public void isOverDue() {
		assertTrue(task1.isOverDue(dt2));
		assertFalse(task2.isOverDue(dt2));
	}

	@Test
	public void getDateString() {
		assertEquals(task1.getDateString(), "08:00");
	}

	@Test
	public void testToString() {
		assertEquals("Go shopping, Deadline: 22 Feb 2016 Monday 08:00 8.00 am", task1.toString());
	}
}
```
###### test\dooyit\common\datatype\EventTaskTest.java
``` java
 *
 */

public class EventTaskTest {

	EventTask task1;
	EventTask task2;
	EventTask task3;
	DateTime dt1_start, dt1_end;
	DateTime dt2_start, dt2_end;
	DateTime today;

	@Before
	public void setUp() {
		int[] date1 = { 22, 2, 2016 };
		today = new DateTime(date1, 0);
		dt1_start = new DateTime(date1, 800);
		dt1_end = new DateTime(date1, 1800);
		task1 = new EventTask("Beach day", dt1_start, dt1_end);
		int[] date2 = { 21, 2, 2016 };
		dt2_start = new DateTime(date2, 800);
		dt2_end = new DateTime(date2, 1800);
		task2 = new EventTask("Hiking", dt2_start, dt2_end);
		task3 = new EventTask("Hiking", dt2_start, dt2_end);
	}

	@Test
	public void getDateTimeStart() {
		assertTrue(task1.getDateTimeStart().equals(dt1_start));
	}

	@Test
	public void getDateTimeEnd() {
		assertTrue(task1.getDateTimeEnd().equals(dt1_end));
	}

	@Test
	public void isToday() {
		assertTrue(task1.isSameDate(today));
	}

	@Test
	public void isOverDue() {
		assertTrue(task2.isOverDue(today));
		assertFalse(task1.isOverDue(today));
	}

	@Test
	public void getDateString() {
		assertEquals(task1.getDateString(), "08:00 - 18:00");
	}

	@Test
	public void testToString() {
		assertEquals(task2.toString(),
				"Hiking, Event: 21 Feb 2016 Sunday 08:00 8.00 am to 21 Feb 2016 Sunday 18:00 6.00 pm");
	}
}
```
###### test\dooyit\common\datatype\FloatingTaskTest.java
``` java
 *
 */

public class FloatingTaskTest {

	FloatingTask task1;
	FloatingTask task2;
	FloatingTask task3;
	DateTime today;

	@Before
	public void setUp() {
		today = new DateTime();
		task1 = new FloatingTask("Get a scooter");
		task2 = new FloatingTask("Get a scooter");
		task3 = new FloatingTask("Go skydiving");
	}

	@Test
	public void isToday() {
		assertFalse(task1.isSameDate(today));
	}

	@Test
	public void isOverDue() {
		assertFalse(task1.isOverDue(today));
	}

	@Test
	public void getDateString() {
		assertEquals(task1.getDateString(), "");
	}

	/*@Test
	public void testToString() {
		assertEquals(task1.toString(), "Get a scooter");
	}*/

}
```
###### test\dooyit\common\datatype\TaskGroupTest.java
``` java
 *
 */

public class TaskGroupTest {

	TaskGroup taskGroup;
	TaskGroup taskGroup2;
	ArrayList<Task> taskGroupTasks;
	ArrayList<Task> tasks;
	DeadlineTask task1, task2, task3;
	DateTime dt;

	@Before
	public void setUp() {
		int[] date = { 22, 2, 2016 };
		dt = new DateTime(date, 1800);
		task1 = new DeadlineTask("Dinner at Tiffany's", dt);
		task2 = new DeadlineTask("Shopping at Ion", dt);
		task3 = new DeadlineTask("Movie date", dt);
		tasks = new ArrayList<Task>();
		tasks.add(task2);
		tasks.add(task3);
		taskGroup = new TaskGroup("Today", dt);
		taskGroup2 = new TaskGroup("Floating");
	}

	@Test
	public void addTask() {
		taskGroup.addTask(task1);
		taskGroupTasks = taskGroup.getTasks();
		assertEquals(taskGroupTasks.size(), 1);
		assertTrue(taskGroupTasks.get(0).equals(task1));
	}

	@Test
	public void addTasks() {
		taskGroup.addTasks(tasks);
		taskGroupTasks = taskGroup.getTasks();
		assertEquals(taskGroupTasks.size(), 2);
		assertTrue(taskGroupTasks.get(0).equals(task2));
		assertTrue(taskGroupTasks.get(1).equals(task3));
	}

	@Test
	public void getDateTime() {
		assertTrue(taskGroup.getDateTime().equals(dt));
	}

	@Test
	public void getTitle() {
		assertEquals(taskGroup.getTitle(), "Today, 22 Feb");
	}

	@Test
	public void setTitle() {
		taskGroup.setTitle("This day");
		assertEquals(taskGroup.getTitle(), "This day, 22 Feb");
	}

	@Test
	public void getTasks() {
		taskGroup.addTasks(tasks);
		taskGroupTasks = taskGroup.getTasks();
		assertEquals(taskGroupTasks.size(), 2);
		assertTrue(taskGroupTasks.get(0).equals(task2));
		assertTrue(taskGroupTasks.get(1).equals(task3));
	}

	@Test
	public void hasDateTime() {
		assertTrue(taskGroup.hasDateTime());
		assertFalse(taskGroup2.hasDateTime());
	}
}
```
###### test\dooyit\common\datatype\TaskTest.java
``` java
 *
 */

public class TaskTest {

	Task taskF1;
	Task taskF2;
	Task taskF3;

	Task taskD1;
	Task taskD2;
	Task taskD3;

	@Before
	public void setUp() {
		taskF1 = new FloatingTask("hello");
		taskF2 = new FloatingTask("hello");
		taskF3 = new FloatingTask("home");

		DateTime date = new DateTime();

		taskD1 = new DeadlineTask("hello", date);
		taskD2 = new DeadlineTask("hello", date);
		taskD3 = new DeadlineTask("home", new DateTime());
	}

	@Test
	public void getName() {
		assertEquals(taskF1.getName(), "hello");
		assertEquals(taskD3.getName(), "home");
	}

	@Test
	public void changeName() {
		taskF1.changeName("goodbye");
		taskD1.changeName("goodbye");
		assertEquals(taskF1.getName(), "goodbye");
		assertEquals(taskD1.getName(), "goodbye");
	}

	@Test
	public void isCompleted() {
		assertFalse(taskF1.isCompleted());
		assertFalse(taskD1.isCompleted());
	}

	@Test
	public void mark() {
		taskF1.mark();
		taskD1.mark();
		assertTrue(taskF1.isCompleted());
		assertTrue(taskD1.isCompleted());
	}

	@Test
	public void unMark() {
		taskF1.mark();
		taskF1.unMark();
		assertFalse(taskF1.isCompleted());
	}

	@Test
	public void hasCategory() {
		assertFalse(taskF1.hasCategory());
		assertFalse(taskD1.hasCategory());
	}

	@Test
	public void setCategory() {
		Category cat = new Category("School");
		taskF1.setCategory(cat);
		taskD1.setCategory(cat);
		assertTrue(taskF1.hasCategory());
		assertTrue(taskD1.hasCategory());
	}

	@Test
	public void getCategory() {
		Category cat = new Category("School");
		taskF1.setCategory(cat);
		taskD1.setCategory(cat);
		assertTrue(taskF1.getCategory().equals(cat));
		assertTrue(taskF1.getCategory().equals(cat));
	}

	@Test
	public void resetId() {
		taskF1.resetId();
		taskD1.resetId();
		assertEquals(taskF1.getId(), -1);
		assertEquals(taskD1.getId(), -1);
	}

	@Test
	public void getId() {

	}

	@Test
	public void setId() {
		taskF1.setId(3);
		taskD1.setId(5);
		assertEquals(taskF1.getId(), 3);
		assertEquals(taskD1.getId(), 5);
	}

	@Test
	public void getUniqueId() {

	}

	@Test
	public void setUniqueId() {
		taskF1.setUniqueId(3);
		taskD1.setUniqueId(5);
		assertEquals(taskF1.getUniqueId(), 3);
		assertEquals(taskD1.getUniqueId(), 5);
	}

	@Test
	public void getTaskType() {
		assertTrue(taskF1.getTaskType() == Task.TaskType.FLOATING);
		assertTrue(taskD1.getTaskType() == Task.TaskType.DEADLINE);
	}
}
```
