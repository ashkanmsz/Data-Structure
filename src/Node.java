
public class Node {

	Object data;
	Object key;
	Node next;
	Node prev;


	public Node() {

	}
	
	public Node(Object key,Object data) {
		this.data=data;
		this.key=key;
		next=null;
	}

	public Node(Object d) {
		data = d;
		next = prev = null;
	}

}
