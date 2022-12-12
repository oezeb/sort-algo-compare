package PL19215001.util;

import java.util.Stack;

import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @author Ezekiel B. Ouedraogo
 */
public class Navigator {
	Stage stage;
	private Stack<Scene> stk;
	
	public Navigator(Stage stage) {
		this(stage, null);
	}
	
	public Navigator(Stage stage, Scene scene) {
		this.stage = stage;
		this.stk = new Stack<>();
		push(scene);
	}
	
	public Scene currentScene() {
		return stk.peek();
	}
	
	public void push(Scene scene) {
		if(scene == null)
			return;
		stk.push(scene);
		stage.setScene(scene);
	}
	
	public void pop() {
		if(stk.size() <= 1)
			return;
		stk.pop();
		stage.setScene(stk.peek());
	}
}
