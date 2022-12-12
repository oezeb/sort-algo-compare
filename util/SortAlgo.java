package PL19215001.util;

import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * @author Ezekiel B. Ouedraogo
 */
public abstract class SortAlgo implements Cloneable {
	protected double barGap;
	protected Color barColor;
	protected Queue<Anim.Data> queue;
	
	public SortAlgo(double barGap, Color defaultColor) {
		this.queue = new LinkedList<Anim.Data>();
		this.barGap = barGap;
		this.barColor = defaultColor;
	}
	
	public abstract Queue<Anim.Data> sort(Node[] list, Node cursor);
	
	public Queue<Anim.Data> reset(Node[] list, Node cursor) {
		queue.clear();
		translate(cursor, cursor.getLayoutY(), 0.0);
		queue.add(new Anim.VisibleData(cursor, false));
		
		for(int i = 0; i < list.length; i++) {
			Node node = list[i];
			
			node.setLayoutY(0);
			recolor(node, barColor);
			queue.add(new Anim.TranslateData(node, 0, i*barGap, Duration.millis(1)));
		}
		return queue;
	}
	
	@Override
	public abstract String toString();
	
	@Override
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}
	
	public void setBarGap(double gap) {
		this.barGap = gap;
	}
	
	public void translate(Node node, double from, double to) {
		queue.add(new Anim.VisibleData(node, true));
		queue.add(new Anim.TranslateData(node, from, to));
	}
	
	public void recolor(Node node, Color color) {
		queue.add(new Anim.ColorData(node, color));	
	}
	
	public static < E > void swap(E[] arr, int i, int j) {
		E e = arr[i];
		arr[i] = arr[j];
		arr[j] = e;
	}
	

	/**
	 * Insertion sort implementation
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Insertion extends SortAlgo {
		
		public Insertion(double barGap, javafx.scene.paint.Color defaultColor) {
			super(barGap, defaultColor);
		}
	
		@Override
		public Queue<Anim.Data> sort(Node[] list, Node cursor) {
			queue = reset(list, cursor);
			
			double cursorY = cursor.getLayoutY();
			translate(cursor, cursorY, 0.0);
			cursorY = 0.0;
			 
			recolor(list[0], Color.BLACK);
			for(int i = 1; i < list.length; i++) {
				Node key = list[i];
				
				translate(cursor, cursorY, i*barGap);
				cursorY = i*barGap;
				
				int j = i;
				while(--j >= 0 && ((Rectangle)list[j]).getWidth() > ((Rectangle)list[j+1]).getWidth()) {
					translate(cursor, cursorY, j*barGap);
					translate(list[j+1], (j+1)*barGap, j*barGap);
					translate(list[j], j*barGap, (j+1)*barGap);
					cursorY = j*barGap;
					
					swap(list, j, j+1);
				}
				
				recolor(key, Color.BLACK);
			}
	
			queue.add(new Anim.VisibleData(cursor, false));
			
			return queue;
		}
	
		@Override
		public String toString() {
			return "Insertion";
		}
	}
	
	
	/**
	 * Selection sort implementation
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Selection extends SortAlgo {
	
		public Selection(double barGap, javafx.scene.paint.Color defaultColor) {
			super(barGap, defaultColor);
		}
	
		@Override
		public Queue<Anim.Data> sort(Node[] list, Node cursor) {
			queue = reset(list, cursor);
			
			double cursorY = cursor.getLayoutY();
			translate(cursor, cursorY, 0.0);
			cursorY = 0.0;
			
			for(int i = 0; i < list.length; i++) {
				int k = i;
				
				translate(cursor, cursorY, i*barGap);
				cursorY = i*barGap;
				
				recolor(list[k], Color.RED);
				
				for(int j = i+1; j < list.length; j++) {
					translate(cursor, cursorY, j*barGap);
					cursorY = j*barGap;
					
					if(((Rectangle)list[j]).getWidth() < ((Rectangle)list[k]).getWidth()) {
						recolor(list[k], barColor);
						recolor(list[j], Color.RED);
						k = j;
					}
				}
				
				if(i != k) {
					translate(list[i], i*barGap, k*barGap);
					translate(list[k], k*barGap, i*barGap);
				}
	
				recolor(list[i], barColor);
				recolor(list[k], Color.BLACK);
				
				swap(list, i, k);
			}
			queue.add(new Anim.VisibleData(cursor, false));
			
			return queue;
		}
	
		@Override
		public String toString() {
			return "Selection";
		}
	}
	
	
	/**
	 * Bubble sort implementation
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Bubble extends SortAlgo {
	
		public Bubble(double barGap, javafx.scene.paint.Color defaultColor) {
			super(barGap, defaultColor);
		}
	
		@Override
		public Queue<Anim.Data>  sort(Node[] list, Node cursor) {
			queue = reset(list, cursor);
			
			double cursorY = cursor.getLayoutY();
			translate(cursor, cursorY, 0.0);
			cursorY = 0.0;
			 
			for(int i = 0; i < list.length; i++) {
				translate(cursor, cursorY, i*barGap);
				cursorY = i*barGap;
				boolean noswap = true;
				
				for(int j = list.length-1; j > i; j--) {
					translate(cursor, cursorY, j*barGap);
					cursorY = j*barGap;
					
					if(((Rectangle)list[j-1]).getWidth() > ((Rectangle)list[j]).getWidth()) {
						translate(list[j-1], (j-1)*barGap, j*barGap);
						translate(list[j], j*barGap, (j-1)*barGap);
						
						noswap = false;
						swap(list, j-1, j);
					}
				}
				
				if(noswap) {
					for(i = 0; i < list.length; i++)
						recolor(list[i], Color.BLACK);
					break;
				}
	
				recolor(list[i], Color.BLACK);
			}
			
			queue.add(new Anim.VisibleData(cursor, false));
			
			return queue;
		}
	
		@Override
		public String toString() {
			return "Bubble";
		}
	}

	/**
	 * Merge sort implementation
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Merge extends SortAlgo {
		
		public Merge(double barGap, javafx.scene.paint.Color defaultColor) {
			super(barGap, defaultColor);
		}

		@Override
		public Queue<Anim.Data> sort(Node[] list, Node cursor) {
			queue = reset(list, cursor);
			sort(list, cursor, 0, list.length-1);
			queue.add(new Anim.VisibleData(cursor, false));
			
			return queue;
		}
		
		public void sort(Node[] list, Node cursor, int start, int end) {
			if(start >= end) return;
			
			int mid = start + (end-start+1)/2;
			
			sort(list, cursor, start, mid-1);
			sort(list, cursor, mid, end);
			
			for(int i = start; i <= end; i++)
				recolor(list[i], Color.RED);
			
			int i = start;
			int j = mid;
			int k = 0;
			Node[] res = new Node[end-start+1];
			while(i < mid && j <= end) {
				if(((Rectangle)list[i]).getWidth() <= ((Rectangle)list[j]).getWidth()) {
					res[k] = list[i];
					translate(list[i], i*barGap, (start+k)*barGap);
					translate(cursor, i*barGap, (start+k)*barGap);
					i++;
				}
				else {
					res[k] = list[j];
					translate(list[j], j*barGap, (start+k)*barGap);
					translate(cursor, j*barGap, (start+k)*barGap);
					j++;
				}

				recolor(res[k++], Color.BLACK);
			}
			
			while(i < mid) {	
				res[k] = list[i];
				translate(list[i], i*barGap, (start+k)*barGap);
				translate(cursor, i*barGap, (start+k)*barGap);

				recolor(res[k++], Color.BLACK);
				i++;
			}
			
			while(j <= end) {	
				res[k] = list[j];
				translate(list[j], j*barGap, (start+k)*barGap);
				translate(cursor, j*barGap, (start+k)*barGap);

				recolor(res[k++], Color.BLACK);
				j++;
			}
			
			k = 0;
			for(i = start; i <= end; i++)
				list[i] = res[k++];
		}

		@Override
		public String toString() {
			return "Merge";
		}
	}
	
	/**
	 * Quick sort implementation
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Quick extends SortAlgo {

		public Quick(double barGap, javafx.scene.paint.Color defaultColor) {
			super(barGap, defaultColor);
		}

		@Override
		public Queue<Anim.Data> sort(Node[] list, Node cursor) {
			queue = reset(list, cursor);

		    qsort(list, cursor, 0, list.length-1);
			queue.add(new Anim.VisibleData(cursor, false));
			
			return queue;
		}

		public int partition(Node array[], Node cursor, int start, int end) {
		    // select pivot and place it at the end of array
		    Node pivot = array[end];
		    recolor(pivot, Color.BLUE);
		
		    // partionning around pivot
		    // j is the index to the last key which is smaller than pivot
		    // all key in array[0:j-1] < pivot
		    int j = start;
			double cursorY = cursor.getLayoutY();
			translate(cursor, cursorY, start*barGap);
			cursorY = start*barGap;
		    for (int i = start; i < end; i++) {
	    		translate(cursor, cursorY, i*barGap);
	    		cursorY = i*barGap;
	    		
		        if (((Rectangle)array[i]).getWidth() < ((Rectangle)pivot).getWidth()) {

		    		translate(array[i], i*barGap, j*barGap);
		    		translate(array[j], j*barGap, i*barGap);

				    recolor(array[i], Color.GREEN);
					
		            swap(array, i, j++);
		        }

		    }

			translate(cursor, cursorY, end*barGap);
			cursorY = end*barGap;
			translate(array[j], j*barGap, end*barGap);
			translate(array[end], end*barGap, j*barGap);

		    for(int i = start; i < end; i++)
			    recolor(array[i], barColor);

		    // move pivot element to j index
		    swap(array, j, end);

		    recolor(pivot, Color.BLACK);
		    return j;
		}
		
		// sort array[start:end]
		public void qsort(Node array[], Node cursor, int start, int end) {
			if(start == end) 
			    recolor(array[start], Color.BLACK);
		    if (start >= end) return;
		    // partition array and get pivot index
		    int k = partition(array, cursor, start, end);
		    // recursively sort the left and right subarray of the pivot index
		    qsort(array, cursor, start, k - 1);
		    qsort(array, cursor, k + 1, end);
		}

		@Override
		public String toString() {
			return "Quick";
		}
	}
	
	/**
	 * Heap sort implementations
	 * @author Ezekiel B. Ouedraogo
	 */
	public static class Heap extends SortAlgo {
		
		public Heap(double barGap, javafx.scene.paint.Color defaultColor) {
			super(barGap, defaultColor);
		}

		@Override
		public Queue<Anim.Data> sort(Node[] list, Node cursor) {
			queue = reset(list, cursor);
			
			cursor.setVisible(true);
			double cursorY = cursor.getLayoutY();
			buildheap(list, cursor);
		    // the heap root (array[0]) contains the largest key
		    for(int i = list.length-1; i > 0; i--) {
	    		translate(list[0], 0, i*barGap);
	    		translate(list[i], i*barGap, 0);
	    		
	    	    recolor(list[0], Constant.Color.BLACK);
	    		translate(cursor, cursorY, 0);
	    		cursorY = 0;
	    		
		        // the largest key is saved at the end of array
		        // and we consider now the array of size = i as the heap
		        swap(list, 0, i);
		        maxheapify(list, i, cursor, 0);
		    }
		    
		    recolor(list[0], Constant.Color.BLACK);
			queue.add(new Anim.VisibleData(cursor, false));
			
			return queue;
		}

		// left child is at positon 2*(index+1)-1 for index >= 0
		// left child is at positon 2*index for index >= 1
		public static int left(int index) { 
		    return 2*(index+1)-1; 
		}

		// right child is at positon 2*(index+1) for index >= 0
		// right child is at positon 2*index+1 for index >= 1
		public static int right(int index) { 
		    return 2*(index+1); 
		}

		public void maxheapify(Node array[], int size, Node cursor, int index) {
		    int l = left(index); // left child
		    int r = right(index); // right child

		    recolor(array[index], Color.RED);
			if(l < size) 
				recolor(array[l], Color.RED);
			if(r < size) 
				recolor(array[r], Color.RED);
			
		    // find the largest between current node and its two children
		    int largest = index;
		    if(l < size && ((Rectangle)array[largest]).getWidth() < ((Rectangle)array[l]).getWidth())
		        largest = l;
		    if(r < size && ((Rectangle)array[largest]).getWidth() < ((Rectangle)array[r]).getWidth())
		        largest = r;
		    
		    if(largest != index) {
				double cursorY = cursor.getLayoutY();
	    		translate(cursor, cursorY, largest*barGap);
	    		cursorY = largest*barGap;
	    		
	    		translate(array[index], index*barGap, largest*barGap);
	    		translate(array[largest], largest*barGap, index*barGap);
		    }

		    recolor(array[index], barColor);
			if(l < size) 
				recolor(array[l], barColor);
			if(r < size) 
				recolor(array[r], barColor);
			
		    if(largest != index) {
			    // set the largest as parent
		        swap(array, index, largest);
		        // the current node is now at array[largest]
		        // recursively heapify the current node again until it is in the right place
		        maxheapify(array, size, cursor, largest);
		    }
		}

		public void buildheap(Node array[], Node cursor) {
		    // fact: in an heap, the nodes indexed from ⌊size/2⌋ to size-1 are leaves
		    // therefore no need to heapify them
		    for(int i = array.length/2-1; i >= 0; i--)
		        maxheapify(array, array.length, cursor, i);
		}

		@Override
		public String toString() {
			return "Heap";
		}
	}
}