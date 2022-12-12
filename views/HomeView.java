package PL19215001.views;

import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import PL19215001.Main.ResourceLoader;
import PL19215001.util.*;
import PL19215001.util.SortAlgo.*;
import PL19215001.views.layouts.Center;
import PL19215001.views.widgets.Graph;
import PL19215001.views.widgets.Spinner;

/**
 * @author Ezekiel B. Ouedraogo
 */
public class HomeView extends Center {
	public static final double GAP = 15;
	
	public final static String[] DATA_TYPE_LABEL = new String[] {"Random", "Nearly Sorted", "Reversed" };
	
	// Graph data
	public static final double GRAPH_SIZE = 145;
	public static final double GRAPH_GAP = 5;
	public static interface Data {
		public final static int[] RANDOM = new int[] {15, 30, 35, 25, 100, 45, 60, 40, 75, 70, 55, 85, 95, 10, 105, 80, 65, 20, 50, 90};
		public final static int[] NEARLY_SORTED = new int[] {10, 15, 20, 5, 25, 30, 35, 40, 60, 65, 50, 55, 70, 45, 90, 80, 85, 95, 100, 75};
		public final static int[] REVERSED = new int[] {100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30, 25, 20, 15, 10, 5};
	}

	public final static double GRAPH_BAR_GAP = 7;
	public static final double GRAPH_BAR_HEIGHT = 3;
	public final static javafx.scene.paint.Color GRAPH_BAR_COLOR = Color.GREY;
	

	public static final double GRAPH_CURSOR_WIDTH = 9;
	public static final double GRAPH_CURSOR_HEIGHT = 5;
	
	public static final Graph[][] graphMat = new Graph[][] {
			new Graph[] { getGraph(Data.RANDOM, new Insertion(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.NEARLY_SORTED, new Insertion(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.REVERSED, new Insertion(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)) },
			new Graph[] { getGraph(Data.RANDOM, new Selection(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.NEARLY_SORTED, new Selection(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.REVERSED, new Selection(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)) },
			new Graph[] { getGraph(Data.RANDOM, new Bubble(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.NEARLY_SORTED, new Bubble(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.REVERSED, new Bubble(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)) },
			new Graph[] { getGraph(Data.RANDOM, new Merge(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.NEARLY_SORTED, new Merge(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.REVERSED, new Merge(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)) },
			new Graph[] { getGraph(Data.RANDOM, new Quick(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.NEARLY_SORTED, new Quick(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.REVERSED, new Quick(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)) },
			new Graph[] { getGraph(Data.RANDOM, new Heap(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.NEARLY_SORTED, new Heap(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)), getGraph(Data.REVERSED, new Heap(GRAPH_BAR_GAP, GRAPH_BAR_COLOR)) }, 
		};
	
	public HomeView(Navigator navigator) {
		super(init(navigator), Constant.WIDTH, Constant.HEIGHT);
	}
	
	private static VBox init(Navigator navigator) {
		Body body = new Body(graphMat, navigator);
		Header header = new Header(() -> {
	    	for(int i = 0; i < graphMat.length; i++)
	    		for(int j = 0; j < graphMat[i].length; j++)
	    			graphMat[i][j].reset();
		});
		
		VBox vbox = new VBox(header, body);
		
		vbox.setSpacing(GAP);
		return vbox;
	}
	
	private static Graph getGraph(int[] widthList, SortAlgo sortAlgo) {
		return new Graph(
			widthList, 
			sortAlgo,
			GRAPH_SIZE,
			GRAPH_CURSOR_WIDTH,
			GRAPH_CURSOR_HEIGHT,
			GRAPH_BAR_HEIGHT,
			GRAPH_BAR_GAP,
			GRAPH_BAR_COLOR
		);
	}
	
	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Header extends HBox {
		public static final double WIDTH = 1045;
		public static final double HEIGHT = 50;
		public static final String RESET_TEXT = "Reset";
	
		public Header(Runnable reset) {
			setPrefWidth(WIDTH);
			setPrefHeight(HEIGHT);
			
			Label label = new Label(Constant.TITLE);
			label.setFont(Font.font("", FontWeight.BOLD, Constant.FONT_SIZE));
			HBox hbox1 = new HBox(label);
			hbox1.setPrefWidth(WIDTH/2);
			hbox1.setAlignment(Pos.CENTER_LEFT);
			
			HBox hbox2 = new HBox(new Center.CenterVBox(new ResetButton(reset), HEIGHT));
			hbox2.setPrefWidth(WIDTH/4);
			hbox2.setAlignment(Pos.CENTER_RIGHT);
			
			HBox hbox3 = new HBox(new Center.CenterVBox(new Spinner(), HEIGHT));
			hbox3.setPrefWidth(WIDTH/4);
			hbox3.setAlignment(Pos.CENTER_RIGHT);
			
			this.getChildren().addAll(hbox1, new HBox(hbox2, hbox3));
		}
		
		/**
		 * @author Ezekiel B. Ouedraogo
		 */
		public static class ResetButton extends HBox {
			public static final double GAP = 7;
			public ResetButton(Runnable reset) {
				Label label = new Label("Reset");
				
				InputStream stream = ResourceLoader.getStream(Constant.ImageUrl.RESET_ICON_BLACK);
				ImageView imageView = stream == null ? new ImageView() : new ImageView(new Image(stream));
				
				this.setOnMouseClicked((e) -> {
					reset.run();
				});
				
				setOnMouseEntered((e) -> {
					setCursor(Cursor.HAND);		
				});
				
				setOnMouseExited((e) -> {
					setCursor(Cursor.DEFAULT);		
				});
				
				setSpacing(GAP);
				getChildren().addAll(label, imageView);
			}
		}
	}

	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Body extends GridPane {
		public static final double GAP = 5;
		
		public Body(Graph[][] graphMat, Navigator navigator) {
			this.setVgap(GAP);
			this.setHgap(GAP);
			
	    	Graph[] all = new Graph[graphMat[0].length*graphMat.length]; 
	    	int k = 0;
	    	Graph[][] reverse = new Graph[graphMat[0].length][graphMat.length];
	    	for(int i = 0; i < graphMat.length; i++) {
	    		Graph[] graphList = graphMat[i];
	    		PlayPane playpane = new PlayPane(
	    				GRAPH_SIZE,
	        			Constant.ImageUrl.PLAY_ICON_INDIGO, 
	        			graphList[0].getSortAlgo().toString(), 
	        			Constant.Color.INDIGO
	        		);
	    		
	    		InputStream stream = ResourceLoader.getStream(Constant.ImageUrl.INFO_ICON);
	    		Button details = stream == null ? new Button("details") : new Button("", new ImageView(new Image(stream)));
	    		details.setOnMouseClicked((e) -> {
					try {
						navigator.push(new Scene(new AlgoDetailsView((SortAlgo)graphList[0].getSortAlgo().clone(), navigator)));
					} catch (Exception err) {
						err.printStackTrace();
					}
	    		});
	    		
	    		HBox hbox = new HBox(details);
	    		hbox.setAlignment(Pos.TOP_RIGHT);
	    		hbox.setOnMouseClicked((e) -> {
	    			for(int j = 0; j < graphList.length; j++)
	    				graphList[j].play();
	    		});
	    		hbox.setOnMouseEntered((e) -> {
	    			hbox.setCursor(Cursor.HAND);		
	    		});
	    		
	    		hbox.setOnMouseExited((e) -> {
	    			hbox.setCursor(Cursor.DEFAULT);		
	    		});
	    		
	    		this.add(new StackPane(playpane, hbox), i+1, 0);
	    		
	        	for(int j = 0; j < graphList.length; j++) {
	        		Graph graph = graphList[j];
	        		PlayPane innerPane = new PlayPane(
	        				GRAPH_SIZE, 
	        				Constant.ImageUrl.PLAY_ICON_WHITE, 
	        				"Play", 
	        				Color.WHITE
	        			);
	        		

	        		innerPane.setStyle("-fx-background-color: #8B76F3;");
	        		innerPane.setVisible(false);
	        		
	        		Pane outerPane = new Pane(innerPane);

	        		outerPane.setOnMouseClicked((e) -> {
	        			innerPane.setVisible(false);
	        			graph.play();
	        		});
	        		
	        		outerPane.setOnMouseEntered((e) -> {
	        			Anim anim = graph.getAnim();
	        			if(anim == null || anim.isFinished())
	        				innerPane.setVisible(true);
	        		});

	        		outerPane.setOnMouseExited((e) -> {
	        			innerPane.setVisible(false);
	        		});
	        		
	            	this.add(new StackPane(graphMat[i][j], outerPane), i+1, j+1);
	            	reverse[j][i] = graphMat[i][j];
	            	all[k++] = graphMat[i][j];
	        	}
	    	}
	    	
	    	for(k = 0; k < reverse.length; k++) {
	    		PlayPane playpane = new PlayPane(GRAPH_SIZE, Constant.ImageUrl.PLAY_ICON_INDIGO, DATA_TYPE_LABEL[k], Constant.Color.INDIGO);

    			Graph[] graphList = reverse[k];
	    		playpane.setOnMouseClicked((e) -> {
	    			for(int i = 0; i < graphList.length; i++)
	    				graphList[i].play();
	    		});
	    		
	        	this.add(playpane,0, k+1);
	    	}
	    	
	    	PlayPane playpane = new PlayPane(GRAPH_SIZE, Constant.ImageUrl.PLAY_ICON_BLACK, "PlayAll", Constant.Color.BLACK);
	    	playpane.setOnMouseClicked((e) -> {
    			for(int i = 0; i < all.length; i++)
    				all[i].play();
	    	});
	    	
	    	this.add(playpane, 0, 0);
	    }
		
		/**
		 * @author Ezekiel B. Ouedraogo
		 */
		public static class PlayPane extends Pane {
			
			public PlayPane(double size, String imageUrl, String text, Color textColor) {
				InputStream stream = ResourceLoader.getStream(imageUrl);
				ImageView imageview = stream == null ? new ImageView() : new ImageView(new Image(stream));

				Label label = new Label(text);
				label.setFont(new Font(Constant.FONT_SIZE));
				label.setTextFill(textColor);
				
				setStyle("-fx-border-style: solid; -fx-border-color: #707070;");
				setPrefWidth(size);
				setPrefHeight(size);
				
				setOnMouseEntered((e) -> {
					setCursor(Cursor.HAND);		
				});
				
				setOnMouseExited((e) -> {
					setCursor(Cursor.DEFAULT);		
				});
				getChildren().add(new Center(new VBox(new Center(imageview, size,  0.0), new Center(label, size,  0.0)),size, size));
			}
		}

	}
}
