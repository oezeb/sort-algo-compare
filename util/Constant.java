package PL19215001.util;

/**
 * @author Ezekiel B. Ouedraogo
 */
public class Constant {
	public static final String TITLE = "Sorting Algorithm Animation";
	public static final String RESOURCES_BASE_URL = "resources/";
	public static final double WIDTH = 1200;
	public static final double HEIGHT = 700;
	public static final double FONT_SIZE = 20;
	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static interface Color {
		public static final javafx.scene.paint.Color INDIGO = web("#8B76F3");
		public static final javafx.scene.paint.Color WHITE = web("#dadada");
		public static final javafx.scene.paint.Color BLACK = web("#000000");
		public static final javafx.scene.paint.Color GREY = web("#707070");
		public static final javafx.scene.paint.Color RED = web("#ff002a");
		public static final javafx.scene.paint.Color GREEN = web("#239e46");
		public static final javafx.scene.paint.Color BLUE = web("#056BB3");
		public static javafx.scene.paint.Color web(String string) {
			return javafx.scene.paint.Color.web(string);
		}
	}
	
	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static interface ImageUrl {
		public static final String BASE_URL = RESOURCES_BASE_URL + "images/";
		public static final String ICON = BASE_URL + "icon.png";
		public static final String INFO_ICON = BASE_URL + "info.png";
		public static final String PLAY_ICON_BLACK = BASE_URL + "play_black.png";
		public static final String PLAY_ICON_WHITE = BASE_URL + "play_white.png";
		public static final String PLAY_ICON_INDIGO = BASE_URL + "play_indigo.png";
		public static final String RESET_ICON_BLACK = BASE_URL + "reset_black.png";
		public static final String RESET_ICON_INDIGO = BASE_URL + "reset_indigo.png";
		public static final String ARROW_UP = BASE_URL + "arrow_up.png";
		public static final String ARROW_DOWN = BASE_URL + "arrow_down.png";
		public static final String ARROW_LEFT = BASE_URL + "arrow_left.png";
	}
}
