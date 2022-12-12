package PL19215001.views.layouts;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Ezekiel B. Ouedraogo
 */
public class Center extends Pane {
	public Center(Node node, double width, double height) {
		super(new CenterVBox(new CenterHBox(node, width), height));
	}
	
	public static class CenterVBox extends VBox {
		public CenterVBox(Node node, double size) {
			super(node);
			setPrefHeight(size);
			setAlignment(Pos.CENTER);
		}
	}
	
	public static class CenterHBox extends HBox {
		public CenterHBox(Node node, double size) {
			super(node);
			setPrefWidth(size);
			setAlignment(Pos.CENTER);
		}
	}
}
