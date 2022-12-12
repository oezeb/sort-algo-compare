package PL19215001.views;

import java.io.InputStream;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import PL19215001.Main.ResourceLoader;
import PL19215001.util.*;
import PL19215001.views.layouts.Center;
import PL19215001.views.widgets.*;

/**
 * @author Ezekiel B. Ouedraogo
 */
public class AlgoDetailsView extends Center {
	// Graph data
	public static final double GRAPH_SIZE = 435;
	public static interface Data {
		public final static int[] RANDOM = new int[] {45, 90, 105, 75, 300, 135, 180, 120, 225, 210, 165, 255, 285, 30, 315, 240, 195, 60, 150, 270};
	}

	public final static double GRAPH_BAR_GAP = 20;
	public static final double GRAPH_BAR_HEIGHT = 10;
	public final static javafx.scene.paint.Color GRAPH_BAR_COLOR = Color.GREY;
	

	public static final double GRAPH_CURSOR_WIDTH = 27;
	public static final double GRAPH_CURSOR_HEIGHT = 15;
	
	public AlgoDetailsView(SortAlgo algo, Navigator navigator) {
		super(init(algo, navigator), Constant.WIDTH, Constant.HEIGHT);
	}
	
	public static GridPane init(SortAlgo algo, Navigator navigator) {
		GridPane gridpane = new GridPane();
		 
		Label label = new Label(algo.toString()+" Sort");
		label.setFont(Font.font("", FontWeight.BOLD, Constant.FONT_SIZE));

		InputStream stream = ResourceLoader.getStream(Constant.ImageUrl.ARROW_LEFT);
		final Node back = stream == null ? new Label("back") : new ImageView(new Image(stream));
		
		final int value = Spinner.currValue;
		back.setOnMouseClicked((e) -> {
			Spinner.currValue = value;
			navigator.pop();
		});
		back.setOnMouseEntered((e) -> {
			back.setCursor(Cursor.HAND);		
		});
		
		back.setOnMouseExited((e) -> {
			back.setCursor(Cursor.DEFAULT);		
		});
		
		HBox hbox = new HBox(back, label);
		hbox.setSpacing(10);
		
		gridpane.add(hbox, 0, 0, 2, 1);
		gridpane.add(new Info(Constant.RESOURCES_BASE_URL + "sortingalgorithm/" + algo.toString().toLowerCase()+".html"), 0, 1);
		gridpane.add(new Demo(algo), 1, 1);
		
		gridpane.setVgap(15);
		gridpane.setHgap(15);
		
		return gridpane;
	}
	
	private static int[] getWidthList() {
		return Data.RANDOM;
	}
	
	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Info extends VBox {
		public Info(String url) {
			setPrefWidth(500);
	        
			final WebView browser = new WebView();
	        final WebEngine webEngine = browser.getEngine();
	        ScrollPane scrollPane = new ScrollPane();
	        
	        scrollPane.setContent(browser);
	        
			webEngine.loadContent(loadFileData(url));
	        getChildren().addAll(scrollPane);   
		}
		
		String loadFileData(String url) {
			InputStream stream = ResourceLoader.getStream(url);
			if(stream == null)
				return "File Not Found";
			Scanner scanner = new Scanner(stream);
			String str = "";
			while(scanner.hasNext())
		        str += scanner.nextLine() + "\n";
			scanner.close();
			return str;
		}
	}
	
	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Demo extends VBox {
		public static final double GAP = 15;
		
		public Demo(SortAlgo algo) {	
			algo.setBarGap(GRAPH_BAR_GAP);
			Graph graph = new Graph(
				getWidthList(),
				algo,
				GRAPH_SIZE,
				GRAPH_CURSOR_WIDTH,
				GRAPH_CURSOR_HEIGHT,
				GRAPH_BAR_HEIGHT,
				GRAPH_BAR_GAP,
				GRAPH_BAR_COLOR
			);
		
			HBox spinnerbox = new HBox(new Spinner(50));
			spinnerbox.setAlignment(Pos.CENTER_RIGHT);
		
			DataGrid dataGrid = new DataGrid(graph);
			
			GraphGrid graphGrid = new GraphGrid(graph, () -> {
				dataGrid.enable();
			}, () -> {
				dataGrid.disable();
			});
			this.getChildren().addAll(spinnerbox, dataGrid, graphGrid);
			this.setSpacing(GAP);
		}

		/**
		 * @author Ezekiel B. Ouedraogo
		 */
		public static class DataGrid extends GridPane {
			public static final double GAP = 10;
			
			private NumericTextField[] textfieldList;
			
			public DataGrid(Graph graph) {	
				int[] list = graph.getWidthList();
				textfieldList = new NumericTextField[list.length];
				for(int i = 0; i < list.length; i++) {
					textfieldList[i] = new NumericTextField(list[i], 1, 300);//, graph);
					final int index = i;
					textfieldList[i].textProperty().addListener(new ChangeListener<String>() {
					    @Override
					    public void changed(ObservableValue<? extends String> observable, String oldValue, 
					        String newValue) {
					    	graph.setWidth(Integer.parseInt(newValue), index);
					    }
					});
					if(i < list.length/2)
						this.add(textfieldList[i], i, 0);
					else
						this.add(textfieldList[i], i%(list.length/2), 1);
				}
		
				this.setVgap(GAP);
				this.setHgap(GAP);
			}
			
			public void enable() {
				for(int i = 0; i < textfieldList.length; i++)
					textfieldList[i].setDisable(false);
			}
			
			public void disable() {
				for(int i = 0; i < textfieldList.length; i++)
					textfieldList[i].setDisable(true);
			}
		}

		/**
		 * @author Ezekiel B. Ouedraogo
		 */
		public static class GraphGrid extends GridPane {
			public GraphGrid(Graph graph, Runnable enableTextfield, Runnable disableTextfield) {
				InputStream stream = ResourceLoader.getStream(Constant.ImageUrl.PLAY_ICON_INDIGO);
				Button play = new Button(
						Button.Type.PLAY, 
						stream == null ? null : new Image(stream),
						() -> {// OnMouseClicked
							disableTextfield.run();
							graph.play();
							new Thread(() -> {
								while(!graph.getAnim().isFinished()) {
									try {
										Thread.sleep(10);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
								enableTextfield.run();
							}).start();
						}
						);
				
				stream = ResourceLoader.getStream(Constant.ImageUrl.RESET_ICON_INDIGO);
				Button reset = new Button(
						Button.Type.RESET, 
						stream == null ? null : new Image(stream),
						() -> {// OnMouseClicked
							graph.reset();
						}
						);
				
				VBox playbox = new VBox(play);
				VBox resetbox = new VBox(reset);
				playbox.setPrefHeight(215);
				resetbox.setPrefHeight(215);
				playbox.setAlignment(Pos.CENTER);
				resetbox.setAlignment(Pos.CENTER);
				
				this.add(graph, 1, 0, 1, 2);
				this.add(playbox, 0, 0);
				this.add(resetbox, 0, 1);
				
				this.setAlignment(Pos.CENTER);
				this.setHgap(10);
			}
			
			/**
			 * @author Ezekiel B. Ouedraogo
			 */
			public static class Button extends javafx.scene.control.Button {
				public final static int WIDTH = 145;
				public enum Type {PLAY, RESET }
				public Button(Type type, Image icon, Runnable onMouseClicked) {
					super(type == Type.PLAY ? "Play" : "Reset", icon == null ? new ImageView() : new ImageView(icon));
					setOnMouseClicked((e) -> {
						onMouseClicked.run();
					});
					setOnMouseEntered((e) -> {
						setCursor(Cursor.HAND);		
					});
					
					setOnMouseExited((e) -> {
						setCursor(Cursor.DEFAULT);		
					});

					setPrefWidth(WIDTH);
				}
			}
		}
	}
}
