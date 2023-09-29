
public class LinkedList {

	Node head;
	Node tail;
	static long size = 0;

	public LinkedList() {

	}

	public LinkedList(Node head) {
		this.head = head;
		tail = head;
		size++;
	}

	public void addFirst(Object d) {
		Node tmp = new Node(d);

		if (size == 0) {
			head = tmp;
			tail = head;
			size++;
		} else {
			tmp.next = head;
			head.prev = tmp;
			head = tmp;
			size++;
		}
	}

	public void addLast(Object d) {
		/////////////////////////////////////////////////////////
		Node tmp = new Node(d);
		if (size == 0) {
			head = tmp;
			tail = head;
			size++;
		} else {

			tail.next = tmp;
			tmp.prev = tail;
			tail = tmp;
			size++;
		}
	}

	public void deleteFirst() {
		////////////////////////////////////////////////////////
		if (size == 0)
			System.out.println("there is no member");

		else if (size == 1) {

			head = null;
			tail = null;
			size--;
		}

		else {
			Node tmp = new Node(head.next.data);
			tmp.next = head.next.next;
			head.next = null;
			head = tmp;
			size--;
		}
	}

	public void deleteLast() {
		//////////////////////////////////////////////////////
		if (size == 0)
			System.out.println("there is no member");

		else if (size == 1) {

			head = null;
			tail = null;
			size--;
		} else {
			Node tmp = new Node();
			tmp = tail;
			tail = tail.prev;
			tmp.prev = null;
			tail.next = null;
			size--;
		}
	}

	public Node getFirst() {
		return head;
	}

	public Node getLast() {
		return tail;
	}

	public static long getSize() {
		return size;
	}

	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	public boolean isEmpty() {
		if (size == 0)
			return true;
		return false;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public void setTail(Node tail) {
		this.tail = tail;
	}

	/*
	 * public int getCount(Node l) { if(l==null) return 0; return
	 * 1+getCount(l.next); }
	 */

}
