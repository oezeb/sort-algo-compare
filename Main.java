package PL19215001;
 
import java.io.InputStream;

import PL19215001.util.*;
import PL19215001.views.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Ezekiel B. Ouedraogo
 */
public class Main extends Application {
	public static void main(String[] args) { launch();}
	
	@Override
	public void start(Stage stage) {
		// set Application title
        stage.setTitle(Constant.TITLE);
        // set not resizable
        stage.setResizable(false);
        // set Application title bar Icon
        InputStream stream = ResourceLoader.getStream(Constant.ImageUrl.ICON);
		if(stream != null)
        	stage.getIcons().add(new Image(stream));
		
        // init Navigator
        Navigator navigator = new Navigator(stage);
        navigator.push(new Scene(new HomeView(navigator)));

		// show built UI
		stage.show();
	}
	

	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class ResourceLoader {
		private final static ResourceLoader loader = new ResourceLoader();
		
//		public static URL getURL(String url) {
//			return loader.getClass().getResource(url);
//		}
		
		public static InputStream getStream(String url) {
			return loader.getClass().getResourceAsStream(url);
		}
	}
}
