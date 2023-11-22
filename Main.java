import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

class Node {
	String content;
	Node next;

	Node(String content) {
		this.content = content;
	}
}

class Stack {
	Node root;
	int capacity;
	int count;
	String stackName;

	Stack(String stackName, int capacity) {
		this.capacity = capacity;
		root = null;
		count = 0;
		this.stackName = stackName;
	}

	void clearStack() {
		root = null;
		count = 0;
	}

	boolean isEmpty() {
		return count == 0;
	}

	void push(String content) {
		if (count < capacity) {
			count++;
			if (root == null) {
				root = new Node(content);
			} else {
				Node nn = new Node(content);
				nn.next = root;
				root = nn;
			}
		} else {
			System.out.println("No room for " + content + " in " + stackName);
		}
	}

	String pop() {
		String rEle;
		if (count == 0) {
			System.out.println(stackName + " is Empty");
			return "";
		} else {
			if (count == 1) {
				rEle = root.content;
				root = null;
				count--;
				return rEle;
			} else {
				rEle = root.content;
				root = root.next;
				count--;
				return rEle;
			}
		}

	}

	String getTop() {
		return root.content;
	}

	void displayStack() {
		System.out.print(stackName + ":\n");
		Node tRoot = root;
		while (tRoot != null) {
			System.out.print(tRoot.content + "\n");
			tRoot = tRoot.next;
		}
	}
}

public class Main extends JFrame implements ActionListener {
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	Container c;
	JTextField t1 = new JTextField(10);
	JTextField t2 = new JTextField(5);
	JButton[][] table;
	Stack s1 = new Stack("S1", 100);
	Stack s2 = new Stack("S2", 100);
	String operator = "";

	JButton addButton = new JButton("+");
	JButton multiplyButton = new JButton("*");
	JButton divideButton = new JButton("/");
	JButton equalsButton = new JButton("=");
	JButton clearButton = new JButton("Clear");
	JButton backspaceButton = new JButton("delete");

	/*
	 * JButton b1 = new JButton("+");
	 * b1.addActionListener(this);
	 * JButton b2 = new JButton("-");
	 * b2.addActionListener(this);
	 * multiplyButton = new JButton("*");
	 * multiplyButton.addActionListener(this);
	 * divideButton = new JButton("/");
	 * divideButton.addActionListener(this);
	 * equalsButton = new JButton("=");
	 * equalsButton.addActionListener(this);
	 * clearButton = new JButton("C");
	 * clearButton.addActionListener(this);
	 */

	void calculate() {
		double op1, op2, result = 0.0;
		String operand;
		op2 = Double.valueOf(s1.pop());
		op1 = Double.valueOf(s1.pop());
		operand = s2.pop();
		if (operand.equals("+")) {
			result = op1 + op2;
		} else if (operand.equals("-")) {
			result = op1 - op2;
		} else if (operand.equals("*")) {
			result = op1 * op2;
		} else if (operand.equals("/")) {
			result = op1 / op2;
		} else if (operand.equals("^")) {
			result = Math.pow(op1, op2);
		}
		System.out.printf(op1 + operand + op2 + "= %.2f", result);
		s1.push("" + result);
	}

	void initTable() {
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 4; j++) {
				table[i][j] = new JButton("");
				table[i][j].addActionListener(this);
				table[i][j].setFont(new Font("", Font.PLAIN, 20));
				p2.add(table[i][j]);
			}
		}
		table[0][0].setText("7");
		table[0][1].setText("8");
		table[0][2].setText("9");
		table[0][3].setText("*");
		table[0][4].setText("(");
		table[1][0].setText("4");
		table[1][1].setText("5");
		table[1][2].setText("6");
		table[1][3].setText("/");
		table[1][4].setText(")");
		table[2][0].setText("1");
		table[2][1].setText("2");
		table[2][2].setText("3");
		table[2][3].setText("+");
		table[2][4].setText("!");
		table[3][0].setText("0");
		table[3][1].setText(".");
		table[3][2].setText("=");
		table[3][3].setText("-");
		table[3][4].setText("^");
		table[3][2].setFont(new Font("", Font.PLAIN, 42));

	}

	Main() {
		table = new JButton[4][5];
		this.setTitle("Visual Calculator CSC 254");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		c = this.getContentPane();
		c.setLayout(new BorderLayout());
		t1.setEditable(false);
		t1.setText("");
		t1.setFont(new Font("", Font.BOLD, 24));
		t2.setFont(new Font("", Font.BOLD, 24));
		p1.add(t1);
		t2.setBackground(Color.cyan);
		p1.add(t2);
		clearButton.addActionListener(this);
		p1.add(clearButton);
		c.add(p1, BorderLayout.NORTH);
		p2.setLayout(new GridLayout(4, 5));
		initTable();
		c.add(p2, BorderLayout.CENTER);
		backspaceButton.addActionListener(this);
		p3.add(backspaceButton);
		c.add(p3, BorderLayout.SOUTH);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

	boolean precedence(String operand1, String operand2) {
		if ((operand1.equals("^") || operand1.equals("*") || operand1.equals("/"))
				&& (operand2.equals("+") || operand2.equals("-"))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Set<String> operators = new HashSet<>();
			operators.add("^");
			operators.add("*");
			operators.add("/");
			operators.add("+");
			operators.add("-");
			operators.add("(");
			operators.add(")");
			JButton b = (JButton) e.getSource();
			if (b == backspaceButton) {
				String text = t1.getText();
				if (text.length() > 0) {
					text = text.substring(0, text.length() - 1);
					t1.setText(text);
				}
			} else if (b == clearButton) {
				t1.setText("");
				t2.setText("");
				operator = "";
				s1.clearStack();
				s2.clearStack();
			} else if (operators.contains(b.getText())) {
				s1.push(operator);
				if (s2.isEmpty()) {
					s2.push(b.getText());
				} else {
					if (precedence(b.getText(), s2.getTop())) {
						s2.push(b.getText());
					} else {
						calculate();
						s2.push(b.getText());
					}

				}
				operator = "";
				t1.setText(t1.getText() + " " + b.getText() + " ");

			} else if (b.getText().equals("(")) {
				s1.push(operator);
			} else if (b.getText().equals(")")) {
				while (!s2.isEmpty() && !s2.getTop().equals("(")) {
					calculate();

				}
			} else if (b.getText().equals("=")) {
				s1.push(operator);
				t1.setText(t1.getText() + " " + b.getText() + " ");
				while (!s2.isEmpty()) {
					calculate();
				}
				t2.setText(s1.getTop());
			} else {
				operator += b.getText();
				t1.setText(t1.getText() + b.getText());
			}
		} catch (Exception ae) {
			t1.setText("NAN");
		}

	}
}
