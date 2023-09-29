
/**
 * this class is for specifing the functions or process inputs
 */
public class Define {

	Tree t = new Tree();

	public String specify(String infix) {

		String s = "";
		String tmp1[] = new String[infix.length()];// araye ee baraye rikhtane string dakhele an
		int k = 0;
		boolean f = false;

		for (int i = 0; i < tmp1.length; i++)
			tmp1[i] = "";

		// in halghe baraye taeen e in ast ke add be ham be chasband ya manfi be an ha
		// bechasbad ya .........

		for (int i = 0; i < infix.length(); i++) {

			if ((!t.isOperator(infix.charAt(i)) || (i == 0 && infix.charAt(i) == '-') || f == true)// agar adad bood
					&& infix.charAt(i) != '(' && infix.charAt(i) != ')'
					&& !MainPage.map.isFuncData(String.valueOf(infix.charAt(i)))) {

				tmp1[k] += infix.charAt(i);
				f = false;
			}

			else {// agar amalgar bood

				if (i != 0)
					k++;
				tmp1[k] += infix.charAt(i);

				if (i + 1 < infix.length() && infix.charAt(i) == ')' && infix.charAt(i + 1) == '-')
					continue;

				// negahi be charactere e baedi
				if (i + 1 < infix.length() && (infix.charAt(i + 1) == '-' || !t.isOperator(infix.charAt(i + 1)))
						&& infix.charAt(i + 1) != '(' && infix.charAt(i + 1) != ')'
						&& !MainPage.map.isFuncData(String.valueOf(infix.charAt(i + 1)))) {
					k++;
					f = true;
				}
			}
		}

		for (int i = 0; i < tmp1.length; i++) {
			s += tmp1[i] + " ";
		}

		return s;

	}

	public boolean isNumber(char c) {

		if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8'
				|| c == '9')
			return true;

		return false;

	}

	// in tabe baraye ja gozarie meghadre majhool dar tabe ast
	public String[] funcCalc(String[] s, String x, String data) {

		String s2 = "";

		for (int i = 0; i < s.length; i++) {

			s2 = "";
			if (s[i].contains(x)) {

				for (int j = 0; j < s[i].length(); j++)
					if (s[i].charAt(j) == x.charAt(0)) {// peida kardan ebarate majhool
						s2 += s[i].substring(0, j) + data + s[i].substring(j + 1);// hazf an va ja gozari meghdare jadid
						s[i] = s2;
					}
			}
		}

		return s;

	}

	// in tabe baraye mohase tabe ast
	public String function(String s) {

		Stack st = new Stack();
		Node node = null, t1, t2, t3;
		String data = "", key = "", s3 = "", postfix;
		String result[] = null;
		boolean f = false;

		try {

			for (int i = 0; i < s.length(); i++) {

				String s1 = "", s2 = "", s5 = "", mount = "", calcMount = "";
				s3 = "";

				if (s.charAt(i) == ')') {

					while (!st.peek().data.toString().equals("(")) {// ta zamani ke be "(" berese bayad pop kond
						t1 = st.pop();
						s1 = t1.data + s1;
					}

					t2 = st.pop();// "("
					t3 = st.pop();// funcName

					s2 += t2.data;
					s3 += t3.data;

					s3 += s2 + s1 + s.charAt(i);// darooni tarin tabe "f(1)"

					for (int j = 2; s3.charAt(j) != ')'; j++) {// in loop baraye joda sazi input e jadide tabe ast

						mount += s3.charAt(j);
					}

					String mountArr[] = mount.split(",");// tabdile input be araye

					for (int j = 0; j < mountArr.length; j++) {// agar vorudi ha mohasebati bood in loop mohasebe
																// mikonad
						calcMount = specify(mountArr[j]);

						String tmp[] = calcMount.split(" ");

						postfix = t.inToPost(tmp);
						
						String[] tmp1 = postfix.split(" ");

						for (int j1 = 0; j1 < tmp1.length; j1++) {

							if (MainPage.map.isData(tmp1[j1]))
								tmp1[j1] = (String) MainPage.map.getData(tmp1[j1]);

						}

						Node root = t.calcPost(tmp1);

						mountArr[j] = root.data.toString();// ja gozari meghdare mohasebe shode dar haman khane
					}

					s3 = s3.substring(0, 2);// joda sazi e ebtedaye function "f("

					data = (String) MainPage.map.getFuncData(s3);// peida kardane data e tabe

					key = (String) MainPage.map.getKey(data);// peida kardane key e tabe

					data = specify(data);// mohasebe data

					String dataArr[] = data.split(" ");

					for (int j = 2; key.charAt(j) != ')'; j++) {// in halghe baraye joda sazie vordoodie majhoole tabe
																// ast
						s5 += key.charAt(j);
					}

					String variables[] = s5.split(",");// joda sazie majhoolat

					for (int j = 0; j < mountArr.length; j++) {// ja sazie maloomat ba majhoolat

						result = funcCalc(dataArr, variables[j], mountArr[j]);

					}

					postfix = t.inToPost(result);// mohasebe string e result

					String[] tmp2 = postfix.split(" ");

					Node root = t.calcPost(tmp2);

					st.push(root.data);

					continue;
				}

				st.push(s.charAt(i));// tamame char haye stringe vorudi ra push bayad kard
			}

			node = st.pop();

		} catch (ArrayIndexOutOfBoundsException e) {

			if (!MainPage.status.equals("wrong exp"))
				System.out.println(">>err1 : wrong exp ");
			return null;
		}

		catch (NullPointerException e) {

			if (data == null && !isNumber(s3.charAt(0)) && !MainPage.status.equals("wrong exp"))
				System.out.println(">>err2 : undefined exp ");

			if (isNumber(s3.charAt(0)) && !MainPage.status.equals("wrong exp"))
				System.out.println(">>err3 : wrong syntax");

			return null;

		}

		return node.data.toString();

	}

}
