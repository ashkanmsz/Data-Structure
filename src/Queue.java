
public class Queue {

	LinkedList l = new LinkedList();
	
	public Queue() {
	
	}
	
	public Queue(Node head) {
		
		l.setHead(head);
		l.setTail(head);
		LinkedList.size++;
	}

	public void enqueue(Object d) {
		l.addFirst(d);
	}

	public Node dequeue() {
		
		Node front = new Node();
		front = l.getLast();
		l.deleteLast();
		return front;

	}

	public Node getFirst() {
		return l.getLast();
	}

	public Node getLast() {
		return l.getFirst();
	}
	public long getSize() {
		return LinkedList.getSize();
	}

	public void clear() {
		l.clear();
	}

	public boolean isEmpty() {
		return l.isEmpty();
	}

}
