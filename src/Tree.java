
/**
 * this class mosre is for calculting operations
 */
public class Tree {

	Node root;

	public Tree() {

	}

	public Tree(Node root) {
		this.root = root;
	}

	public boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^')
			return true;

		return false;
	}

	public int priority(char a) {
		switch (a) {

		case '^':
			return 3;
		case '*':
			return 2;
		case '/':
			return 2;
		case '+':
			return 1;
		case '-':
			return 1;
		case ')':
			return 0;
		case '(':
			return -1;

		}
		return 0;
	}

	// in tabe baraye tabdile infix be postfix ast
	public String inToPost(String[] s) {

		Stack st = new Stack();
		String tmp = "";
		Node t1, t2;

		for (int i = 0; i < s.length; i++) {

			try {
				// agar add bood va operator nabood dar string e tmp berizad
				if ((!isOperator(s[i].charAt(0)) || (s[i].length() >= 2 && !isOperator(s[i].charAt(1))))
						&& !s[i].equals("(") && !s[i].equals(")"))
					tmp += s[i];

				else {// agar operator bood

					if (!s[i].equals("(") && !s[i - 1].equals(")"))
						tmp += " ";

					// hargah dar stack ")" dashtim yani zirash "(" ast bayad khodash va "(" pop
					// shavnd
					if (!st.isEmpty() && st.peek().data.equals(")")) {
						t1 = st.pop();
						t2 = st.pop();

						if (t1 == null || t2 == null) {

							if (!MainPage.status.equals("wrong exp"))
								System.out.println(">>err3 : wrong syntax ");

							return null;
						}
					}

					// sharte khali kardane operator ha
					while (!st.isEmpty() && !s[i].equals("(") && !st.peek().data.equals("(")
							&& (priority(st.peek().data.toString().charAt(0)) >= priority(s[i].charAt(0)))) {
						tmp += st.pop().data;
						tmp += " ";
					}

					st.push(s[i]);

				}

			} catch (StringIndexOutOfBoundsException e) {

				if (!MainPage.status.equals("wrong exp"))
					System.out.println(">>err3 : wrong syntax ");

				return null;
			}

			catch (ArrayIndexOutOfBoundsException e) {

				if (!MainPage.status.equals("wrong exp"))
					System.out.println(">>err3 : wrong syntax ");

				return null;
			}

		}

		if (!st.isEmpty() && !st.peek().data.equals(")"))
			tmp += " ";

		if (!st.isEmpty() && st.peek().data.equals(")")) {// khali kardane parantez ha
			t1 = st.pop();
			t2 = st.pop();

			if (t1 == null || t2 == null) {

				if (!MainPage.status.equals("wrong exp"))
					System.out.println(">>err3 : wrong syntax ");

				return null;
			}

		}

		while (!st.isEmpty()) {
			tmp += st.pop().data;
			tmp += " ";
		}

		return tmp;
	}

	// in tabe baraye mohasebe postfix ast

	public Node calcPost(String[] s) {

		Node t = null, t1, t2;
		Stack st = new Stack();
		double a = 0, b = 0;

		for (int i = 0; i < s.length; i++) {

			String s1 = "", s2 = "", s3 = "";

			try {

				// agar adade + bood or adade - bood
				if (!isOperator(s[i].charAt(0)) || (s[i].length() >= 2 && !isOperator(s[i].charAt(1)))) {

					st.push(s[i]);

				} else {// agar amalgar bood

					try {

						t1 = st.pop();
						t2 = st.pop();

						s1 += t1.data;// adade balaye stack
						s2 += t2.data;// adade dobvome stack

					} catch (NullPointerException e) {

						return null;

					}

					try {

						a = Double.parseDouble(s1);
						b = Double.parseDouble(s2);

					} catch (NumberFormatException e) {

						if (s1.equals(")") || s1.equals("(") || s2.equals(")") || s2.equals("(")) {

							System.out.println(">>err3 : wrong syntax ");
							return null;
						}

						else if (!MainPage.status.equals("wrong exp")) {

							System.out.println(">>err2 : udefined exp ");
							return null;
						}

					}

					switch (s[i]) {

					case "+": {

						if (!MainPage.status.equals("wrong exp"))// agar dar ghesmate tarife tabe nabood
							s3 = String.valueOf(a + b);

						break;
					}

					case "-": {

						if (!MainPage.status.equals("wrong exp"))// agar dar ghesmate tarife tabe nabood
							s3 = String.valueOf(b - a);

						break;
					}

					case "*": {

						if (!MainPage.status.equals("wrong exp"))// agar dar ghesmate tarife tabe nabood
							s3 = String.valueOf(a * b);

						break;
					}

					case "/": {

						if (!MainPage.status.equals("wrong exp")) {// agar dar ghesmate tarife tabe nabood

							if (a == 0)
								s3 = "undefined value";
							else
								s3 = String.valueOf(b / a);
						}

						break;
					}

					case "^": {

						if (!MainPage.status.equals("wrong exp"))// agar dar ghesmate tarife tabe nabood
							s3 = String.valueOf(Math.pow(b, a));

						break;
					}

					}

					st.push(s3);

				}

			} catch (IndexOutOfBoundsException e) {

				if (!MainPage.status.equals("wrong exp"))
					System.out.println(">>err3 : wrong syntax ");

				return null;

			}
		}

		t = st.pop();

		if (t.data.toString().equals("(") || t.data.toString().equals(")")) {// baraye vorudie eshtebah

			if (!MainPage.status.equals("wrong exp"))
				System.out.println(">>err3 : wrong syntax");

			return null;
		}

		return t;

	}

}
