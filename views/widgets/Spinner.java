package PL19215001.views.widgets;

import java.io.InputStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import PL19215001.Main.ResourceLoader;
import PL19215001.util.*;

/**
 * 
 * @author Ezekiel B. Ouedraogo
 *
 */
public class Spinner extends HBox {
	public static int currValue;
	private NumericTextField textfield;

	public Spinner() {
		this(Anim.INIT_DURATION);
	}
	
	public Spinner(int value) {
		Spinner.currValue = value;
		
		setSpacing(10);
		
		Label label = new Label("speed (ms)");
		
		textfield = new NumericTextField(Spinner.currValue, Anim.MIN_DURATION, Anim.MAX_DURATION);
		
		textfield.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		     	Spinner.currValue = Integer.parseInt(newValue);
		    }
		});
		
		
		Runnable increment = () -> {
			textfield.increment();
		};
		Runnable decrement = () -> {
			textfield.increment();
		};
		Button up = new Button(Button.Type.UP, increment, decrement);
		Button down = new Button(Button.Type.DOWN, increment, decrement);
		
		getChildren().addAll(label, textfield, up, down);
	}
	
	/**
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Button extends Pane {
		public static enum Type {UP, DOWN}
		public Button(Type type, Runnable increment, Runnable decrement) {
			String url = type == Type.UP ? Constant.ImageUrl.ARROW_UP : Constant.ImageUrl.ARROW_DOWN;
			InputStream stream = ResourceLoader.getStream(url);
			final Node btn = stream != null ? new ImageView(new Image(stream)) : new javafx.scene.control.Button(type == Type.UP ? "+" : "-");
			
			btn.setOnMouseClicked((e) -> {
				if(type == Type.UP) increment.run();
				else decrement.run();
			});
			
			btn.setOnMouseEntered((e) -> {
				btn.setCursor(Cursor.HAND);		
			});
			
			btn.setOnMouseExited((e) -> {
				btn.setCursor(Cursor.DEFAULT);		
			});
			this.getChildren().add(btn);
		}
	}
	
	public void setValue(int value) {
		textfield.update(Integer.toString(value));
		currValue = value;
	}
}