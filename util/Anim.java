package PL19215001.util;

import java.util.Queue;

import PL19215001.views.widgets.Spinner;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * @author Ezekiel B. Ouedraogo
 */
public class Anim {
	public final static int INIT_DURATION = 10; // ms
	public final static int MIN_DURATION = 1; // ms
	public final static int MAX_DURATION = 1000; // ms
	
	private Queue<Data>  queue;
	private volatile boolean finished;
	
	public Anim(Queue<Data>  queue) {
		this.queue = queue;
		this.finished = true;
	}

	public void setAnimQueue(Queue<Data>  queue) {
		this.queue = queue;
	}
	
	public static Anim start(Queue<Data>  queue) {
		Anim anim = new Anim(queue);
		anim.start();
		return anim;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void start() {
		if(queue.isEmpty()) {
			finished = true;
		}
		else {
			finished = false;
			Data data = queue.poll();
			if(VisibleData.class.isInstance(data)) {
				boolean bool = ((VisibleData)data).e;
				if(data.node.isVisible() != bool)
					data.node.setVisible(((VisibleData)data).e);
				start();
			}
			else if(ColorData.class.isInstance(data)){
				((Rectangle)data.node).setFill(((ColorData)data).e);
				start();
			}
			else if(TranslateData.class.isInstance(data)) {
				TranslateData td = (TranslateData)data;
				
				TranslateTransition tr = new TranslateTransition();
				tr.setDuration(td.duration == null ? new Duration(Spinner.currValue) : td.duration); 
				tr.setNode(data.node);
				tr.setFromY(td.from);
				tr.setToY(td.to);
				tr.setAutoReverse(false);
				tr.setOnFinished((e) -> {
					start();
				});
				tr.play();
			}
		}
	}
	
	public static abstract class Data {
		public Node node;
		public Data(Node node) {
			this.node = node;
		}
	}
	
	public static class SingleData<E> extends Data {
		public E e;
		public SingleData(Node node, E e) {
			super(node);
			this.e = e;
		}
	}
	

	public static class ColorData extends SingleData<Color> {
		public ColorData(Node node, Color e) {
			super(node, e);
		}
	}
	
	public static class VisibleData extends SingleData<Boolean> {
		public VisibleData(Node node, Boolean e) {
			super(node, e);
		}
	}
	
	public static class TranslateData extends Data {
		public double from, to;
		public Duration duration;
		
		public TranslateData(Node node, double from, double to) {
			this(node, from, to, null);
		}
		
		public TranslateData(Node node, double from, double to, Duration duration) {
			super(node);
			this.from = from;
			this.to = to;
			this.duration = duration;
		}
	}
}
