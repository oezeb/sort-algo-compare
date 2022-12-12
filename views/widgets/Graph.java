package PL19215001.views.widgets;

import java.util.Queue;

import PL19215001.util.*;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * @author Ezekiel B. Ouedraogo
 */
public class Graph extends Pane {
	private Pane barPane;
	private Cursor cursor;
	private SortAlgo sortAlgo;
	private Anim anim;
	
	public Graph(
		int[] widthList, 
		SortAlgo sortAlgo,
		double graphSize,
		double cursorWidth, 
		double cursorHeight,
		double barHeight,
		double barGap,
		Color barDefaultColor
	) {
		setPrefWidth(graphSize);
		setPrefHeight(graphSize);
		setStyle("-fx-border-style: solid; -fx-border-color: #707070;");
		
		this.cursor = new Cursor(cursorWidth, cursorHeight);
		this.sortAlgo = sortAlgo;
		this.barPane = new Pane();
		
		barPane.setPrefHeight(widthList.length*barGap);
		for(int i = 0; i < widthList.length; i++)
			barPane.getChildren().add(new Rectangle(widthList[i], barHeight));
		
		ObservableList<Node> list = barPane.getChildren();
		
		this.anim = new Anim(sortAlgo.reset(list.toArray(new Node[list.size()]), cursor));
		anim.start();
		
		VBox outerBarPane = new VBox(barPane);
		outerBarPane.setPrefHeight(graphSize);
		outerBarPane.setAlignment(Pos.CENTER);
		

		HBox cursorPane = new HBox(cursor);
		cursorPane.setPrefWidth(cursorWidth*2);
		cursorPane.setAlignment(Pos.BASELINE_CENTER);
		
		getChildren().add(new HBox(cursorPane, outerBarPane));
	}
	
	public SortAlgo getSortAlgo() {
		return sortAlgo;
	}
	

	public int[] getWidthList() {
		ObservableList<Node> list = barPane.getChildren();
		int[] ans = new int[list.size()];
		for(int i = 0; i < list.size(); i++)
			ans[i] = (int) ((Rectangle)list.get(i)).getWidth();
		return ans;
	}
	
	public void setWidth(int value, int index) {
		((Rectangle)barPane.getChildren().get(index)).setWidth(value);
	}
	
	public void play() {
		if(anim.isFinished()) {
			ObservableList<Node> barList = barPane.getChildren();
			Node[] arr = barList.toArray(new Node[barList.size()]);
			Queue<Anim.Data> queue = sortAlgo.sort(arr, cursor);
			anim.setAnimQueue(queue);
			anim.start();
		}
	}
	
	public void reset() {
		ObservableList<Node> barList = barPane.getChildren();
		Node[] arr = barList.toArray(new Node[barList.size()]);
		Queue<Anim.Data> queue = sortAlgo.reset(arr, cursor);
		anim.setAnimQueue(queue);
		anim.start();
	}

	public Anim getAnim() {
		return anim;
	}
	
	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Cursor extends Polygon {
		public Cursor(double width, double height) {
			this.setFill(Constant.Color.RED);
//			this.setVisible(false);
			
			this.getPoints().add(0.0);
			this.getPoints().add(0.0);
			this.getPoints().add(height);
			this.getPoints().add(width/2);
			this.getPoints().add(0.0);
			this.getPoints().add(width);
		}
	}
}
