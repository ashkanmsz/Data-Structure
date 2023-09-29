import java.util.Scanner;

/**
 * This is a map program for calculating functions and operations
 * 
 * @author ashkan
 * @since 21/11/2018
 *
 */

public class MainPage {

	static Map map = new Map();
	static Tree t = new Tree();
	static Define def = new Define();
	static Stack st = new Stack();
	static String status = "";// in moteghayer yek noe flag ast baraye error e wrong exp

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		String vorudi = sc.nextLine();// string e vorudi

		while (vorudi.equals("#new")) {

			while (!vorudi.equals("#end")) {

				st.clear();

				vorudi = sc.nextLine();

				if (vorudi.equals("#new"))
					break;

				String[] vs = vorudi.split(" ");// tabdile vorudi be araye

				if (vs[0].equals("#define")) {// dastoore define

					String s1 = "", s2 = "", s3 = "";
					boolean flag = false, flag2 = false, flag3 = false;
					int k = 0;

					if (vs[1].length() > 1 && vs[1].charAt(1) == '(') {// agar vorudi tabe bood

						for (int i = 2; vs[1].charAt(i) != ')'; i++) {// rikhtane kole funcinput dar stringe s1
							s1 += vs[1].charAt(i);
						}

						String tmp[] = s1.split(",");

						// in araye baraye baresie motghayere na ashna boodan dar tabe ast
						String tmp2[] = vs[3].split("");

						// in halghe baraye ja gozarie meghdare moteghayere na ashana ast
						for (int i = 0; i < tmp2.length; i++) {
							if (map.isData(tmp2[i])) {
								tmp2[i] = (String) map.getData(tmp2[i]);
								flag2 = true;
							}
						}

						if (flag2 == true) {

							for (int i = 0; i < tmp2.length; i++)
								s2 += tmp2[i];

							vs[3] = s2;// avaz kardane meghdare jadid e tarife tabe

						}

						/**
						 * in halghe baraye baresi kardane brabar boodan vorudi haye tabe va motaghayer
						 * haye tarife tabe ast
						 */
						// for (int i = 0; i < vs[3].length(); i++) {

						// if (!t.isOperator(vs[3].charAt(i)) && !def.isNumber(vs[3].charAt(i))
						// && vs[3].charAt(i) != '(' && vs[3].charAt(i) != ')')

						// for (int j = 0; j < tmp.length; j++) {

						// if (vs[3].charAt(i) != tmp[j].charAt(0))
						// flag = true;
						// else
						// flag = false;
						// }

						// }

						if (flag == false) { // agar funcInput ha va moteghayer ha mojood boodan

							try {

								s3 = def.specify(vs[3]);

								String tmp3[] = s3.split(" ");

								for (int i = 0; i < tmp3.length; i++) {// in halghe baraye error hayee manand " f(x) =x(
																		// " or f(x) = x()"

									if (tmp3[i].equals("("))
										if (i - 1 >= 0 && !t.isOperator(tmp3[i - 1].charAt(0))) {
											System.out.println(">>err1 : wrong exp ");
											flag3 = true;
											break;
										}
								}

								if (flag3 == false) {// agar error e bala nabood

									status = "wrong exp";// meghdar dehie flag

									String postfix = t.inToPost(tmp3);// mohasebe ebarate tabe baraye peida kardane
																		// error

									String[] tmp4 = postfix.split(" ");

									Node root = t.calcPost(tmp4);

									String a = root.data.toString();// in moteghyer alaki hast baraye peida kardane null
																	// exp

									if (map.isFuncData(String.valueOf(vs[1].charAt(0)))) // agar in tabe az ghabl mojood
																							// bood
										map.change(vs[1], vs[3]);

									else
										map.insert(vs[1], vs[3]);

									status = "";

								}

							} catch (Exception e) {
								System.out.println(">>err1 : wrong exp");
								status = "";
							}

						} else
							
							System.out.println(">>err1 : wrong exp ");

					}

					else {// agar vordudi tabe nabood va moteghayer bood

						boolean f = false;
						String s = "";

						try {

							s = def.specify(vs[3]);// joda sazi voroodi ha va rikhtan dar araye
							String tmp[] = s.split(" ");

							// agar dar tarife moteghayer yek moteghayere digar bood meghadare an ra
							// begozarad
							for (int i = 0; i < tmp.length; i++) {
								if (map.isData(tmp[i]))
									tmp[i] = (String) map.getData(tmp[i]);
							}

							// in halghe baraye tashkhis error e syntax ast manand "2(" or "2(3+4)"
							for (int i = 0; i < tmp.length; i++) {

								if (tmp[i].equals("("))
									if (i - 1 >= 0 && !t.isOperator(tmp[i - 1].charAt(0))) {
										System.out.println(">>err3 : wrong syntax");
										f = true;
										break;
									}
							}

							if (f == false) {

								String postfix = t.inToPost(tmp);// tabdile infix be postfix

								String[] tmp1 = postfix.split(" ");

								Node root = t.calcPost(tmp1);// mohasebe postfix

								double exp = Double.parseDouble((String) root.data);

								if (map.isData(vs[1]))// agar moteghayer az ghabl mojood bood
									map.change(vs[1], root.data);

								else
									map.insert(vs[1], root.data);

							}

						}

						catch (NumberFormatException e) {// baraye mavarede vorudie "x=y"

							System.out.println(">>err1 : wrong exp");
						}

						catch (Exception e) {// baraye mavarede " #define x "

							if (s == "")
								System.out.println(">>err1 : wrong exp");

						}
					}
				}

				if (vs[0].equals("#print")) {// agar dastoore print dade shod

					boolean flag = false, f = false;
					String infix = vs[1];

					String s, s1 = "", funcInput = "";
					int c1 = 0, c2 = 0;
					s = def.specify(infix);// jode sazi ebarate dade shode az operand ha va rikhtan dar araye

					String tmp2[] = s.split(" ");

					/**
					 * in loop baraye peimayesh bar string e vordoodi va tashkhis o mohasebe tabe ha
					 * ast
					 */

					for (int i = 0; i < tmp2.length; i++) {

						if (i - 1 >= 0 && tmp2[i].equals("(") && !t.isOperator(tmp2[i - 1].charAt(0))) {// agar
																										// sharayete
																										// tabe bood

							c1++;

							if (f == false)// baraye avalin bar name e tabe ra be string e funcinput midahim
								funcInput += tmp2[i - 1] + tmp2[i];

							else
								funcInput += tmp2[i];

							f = true;

							continue;
						}

						if (f == true && tmp2[i].equals(")")) {
							c2++;
						}

						if (f == true) {
							funcInput += tmp2[i];
						}

						if (f == true && c1 == c2) {

							if (def.function(funcInput) == null) {// agar tabe ra be soorate eshtebah voroodi dade
																	// bashim
								flag = true;
								f = false;
								funcInput = "";
								break;
							}

							s1 += def.function(funcInput) + " ";// mohasebe tabe

							f = false;
							c1 = 0;
							c2 = 0;

							// name tabe ra ke varede string karde boodim ra hazf mikonim

							for (int j = 0; j < s1.length(); j++)
								if (s1.charAt(j) == funcInput.charAt(0))
									s1 = s1.substring(0, j) + s1.substring(j + 2);

							funcInput = "";
							continue;
						}

						if (f == false) {

							// in if baraye mohasebe ebarate define shode ast
							try {

								if (map.isData(tmp2[i]) && tmp2[i + 1].charAt(0) != '(')

									tmp2[i] = (String) map.getData(tmp2[i]);

							} catch (Exception e) {

								tmp2[i] = (String) map.getData(tmp2[i]);

							}

							s1 += tmp2[i] + " ";

						}
					}

					// sharti ke neshan midahad syntax e vorudi error darad
					if (!funcInput.equals("")) {

						System.out.println(">>err3 : wrong syntax ");
						flag = true;

					}

					if (flag == false) {// agar error nadasht

						try {

							String tmp3[] = s1.split(" ");

							String postfix = t.inToPost(tmp3);

							String[] tmp4 = postfix.split(" ");

							Node root = t.calcPost(tmp4);

							System.out.println(">> " + Double.parseDouble((String) root.data));

						} catch (NumberFormatException e) {

							System.out.println(">>err2 : udefined value");
						}

						catch (Exception e) {

						}
					}

				}

			}

			if (!vorudi.equals("#new")) {

				System.out.println("if you want to continue enter (#new) else enter (#thanks) :");
				vorudi = sc.nextLine();

			}
			map.clear();
		}

		System.out.println("bye :)");
	}
}
