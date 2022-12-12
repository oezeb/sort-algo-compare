package PL19215001.views.widgets;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 
 * @author Ezekiel B. Ouedraogo
 *
 */
public class NumericTextField extends  TextField {
	private int min;
	private int max;

	public NumericTextField(int value, int min, int max) {
		this(value, min, max, 55);
	}

	public NumericTextField(int value, int min, int max, double width) {
		super(Integer.toString(value));
		this.min = min;
		this.max = max;
		
		setPrefWidth(width);
		textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	update(newValue);
		    }
		});
		
		setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                	increment();
                }
                else if (event.getCode() == KeyCode.DOWN) {
                	decrement();
                }
            }
        });
	}
	
	public void update(String string) {
		if(string.isEmpty()) {
			string = Integer.toString(min);
    	}
    	else {
	        if (!string.matches("\\d*"))
	        	string = string.replaceAll("[^\\d]", "");
	        int value = Integer.parseInt(string);
	        if(value < min)
	        	string = Integer.toString(min);
	        else if(value > max)
	        	string = Integer.toString(max);
    	}
        setText(string);
	}
	
	public void increment() {
		update(Integer.toString(Integer.parseInt(getText().toString())+1));
	}
	
	public void decrement() {
		update(Integer.toString(Integer.parseInt(getText().toString())-1));
	}
}
