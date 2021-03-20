package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import calculations.Fraction;
import calculations.Operations;

/**
 * Graphical interface for calculator.
 * 
 * @author Tinh and Joe McCuen and Will and Jeremy
 *
 */
@SuppressWarnings("serial")
public class CalculatorWindow extends JFrame
		implements ActionListener, KeyListener, ComponentListener
{
	private static Fraction leftOperand, rightOperand, result, reducedFrac, irreducedFrac;
	private static JLabel opLabel, equalsLabel;
	private static JLabel leftSlash, leftWholeLabel, leftNumerLabel, leftDenomLabel,
			rightNumerLabel, rightDenomLabel, rightWholeLabel, rightSlash;
	private static JLabel resultWholeLabel, resultNumerLabel, resultDenomLabel, resultSlash;
	private static JLabel selected;
	private static int style;
	private static Integer leftNumer, leftDenom, rightNumer, rightDenom, resultNumer, resultDenom,
			fareysOrder;
	private static JPanel displayCards;
	private static JPanel left, right, res;
	private static JLabel leftBar, rightBar, resBar;
	private static JLabel leftWholeLabel2, leftNumerLabel2, leftDenomLabel2;
	private static JLabel opLabel2, equalsLabel2;
	private static JLabel rightWholeLabel2, rightNumerLabel2, rightDenomLabel2;
	private static JLabel resultWholeLabel2, resultNumerLabel2, resultDenomLabel2;
	private static JFrame self;
	private JPanel display, barDisplay; // Top part; Immutable
	private JMenuBar menu;
	private JMenu displayStyle, format, historyMenu, mode, intermediateSteps, pieChart;
	private JScrollPane historyScroll, stepsScroll;
	private Container contentPane;
	private JPanel buttons, leftButtons, rightButtons, opPanel, rightOpPanel;
	private boolean isReduced = false;
	private JButton focusButton, historyPopoutButton, stepsPopoutButton;
	private JWindow histWindow, stepsWindow;
	private StringBuilder histStr;
	private int versionStyle = 2; // 0 = Fragile, 1 = JMU, 2 = Pepsi
	private ArrayList<String> historyExpressions;
	private boolean undefined = false;
	private int playbackIndex;

	/**
	 * Constructor.
	 */
	public CalculatorWindow()
	{
		super("Mixed Fraction Calculator");
		this.setLocation(400, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		style = 3; // Default to 3 for slash style
		self = this;
		playbackIndex = 0;

		leftOperand = new Fraction(0, 0, 0);
		rightOperand = new Fraction(0, 0, 0);
		result = null;

		leftBar = new JLabel("-", JLabel.LEFT);
		rightBar = new JLabel("-", JLabel.LEFT);
		resBar = new JLabel("-", JLabel.LEFT);

		display = new JPanel(new FlowLayout(FlowLayout.LEFT));
		barDisplay = new JPanel(new FlowLayout(FlowLayout.LEFT));
		displayCards = new JPanel(new CardLayout());
		displayCards.addKeyListener(this);
		displayCards.setFocusable(true);
		displayCards.add(display, "display");
		displayCards.add(barDisplay, "barDisplay");

		leftWholeLabel = new JLabel("  ", JLabel.LEFT);
		leftWholeLabel.setBorder(new LineBorder(Color.BLACK));

		leftNumerLabel = new JLabel("  ", JLabel.LEFT);
		leftNumerLabel.setBorder(new LineBorder(Color.BLACK));
		leftSlash = new JLabel("/", JLabel.LEFT);
		leftDenomLabel = new JLabel("  ", JLabel.LEFT);
		leftDenomLabel.setBorder(new LineBorder(Color.BLACK));

		opLabel = new JLabel(" ", JLabel.LEFT);
		opLabel2 = new JLabel(" ", JLabel.LEFT);

		rightWholeLabel = new JLabel("  ", JLabel.LEFT);
		rightWholeLabel.setBorder(new LineBorder(Color.BLACK));
		rightNumerLabel = new JLabel("  ", JLabel.LEFT);
		rightNumerLabel.setBorder(new LineBorder(Color.BLACK));
		rightSlash = new JLabel("/", JLabel.LEFT);
		rightDenomLabel = new JLabel("  ", JLabel.LEFT);
		rightDenomLabel.setBorder(new LineBorder(Color.BLACK));

		equalsLabel = new JLabel("=", JLabel.LEFT);
		equalsLabel2 = new JLabel("=", JLabel.LEFT);

		resultWholeLabel = new JLabel("", JLabel.LEFT);
		resultNumerLabel = new JLabel("", JLabel.LEFT);
		resultSlash = new JLabel("/", JLabel.LEFT);
		resultDenomLabel = new JLabel("", JLabel.LEFT);

		resultWholeLabel2 = new JLabel("", JLabel.LEFT);
		resultNumerLabel2 = new JLabel("", JLabel.CENTER);
		resultDenomLabel2 = new JLabel("", JLabel.CENTER);
		res = new JPanel(new GridLayout(0, 1));
		res.setBackground(Color.CYAN);
		res.add(resultNumerLabel2);
		resBar.setVisible(false);
		res.add(resBar);
		res.add(resultDenomLabel2);

		selected = leftWholeLabel;
		selected.setBorder(new LineBorder(Color.RED));

		leftNumer = null;
		leftDenom = null;
		rightNumer = null;
		rightDenom = null;
		resultNumer = null;
		resultDenom = null;
		fareysOrder = null;

		left = new JPanel(new GridLayout(0, 1));
		left.setBackground(Color.CYAN);
		leftWholeLabel2 = new JLabel("  ", JLabel.CENTER);
		leftWholeLabel2.setBorder(new LineBorder(Color.BLACK));
		leftNumerLabel2 = new JLabel("  ", JLabel.CENTER);
		leftNumerLabel2.setBorder(new LineBorder(Color.BLACK));
		leftDenomLabel2 = new JLabel("  ", JLabel.CENTER);
		leftDenomLabel2.setBorder(new LineBorder(Color.BLACK));
		left.add(leftNumerLabel2);
		left.add(leftBar);
		left.add(leftDenomLabel2);

		right = new JPanel(new GridLayout(0, 1));
		right.setBackground(Color.CYAN);
		rightWholeLabel2 = new JLabel("  ", JLabel.CENTER);
		rightWholeLabel2.setBorder(new LineBorder(Color.BLACK));
		rightNumerLabel2 = new JLabel("  ", JLabel.CENTER);
		rightNumerLabel2.setBorder(new LineBorder(Color.BLACK));
		rightDenomLabel2 = new JLabel("  ", JLabel.CENTER);
		rightDenomLabel2.setBorder(new LineBorder(Color.BLACK));
		right.add(rightNumerLabel2);
		right.add(rightBar);
		right.add(rightDenomLabel2);

		barDisplay.add(leftWholeLabel2);
		barDisplay.add(left);
		barDisplay.add(opLabel2);
		barDisplay.add(rightWholeLabel2);
		barDisplay.add(right);
		barDisplay.add(equalsLabel2);
		barDisplay.add(resultWholeLabel2);
		barDisplay.add(res);

		display.add(leftWholeLabel);
		display.add(leftNumerLabel);
		display.add(leftSlash);
		display.add(leftDenomLabel);
		display.add(opLabel);
		display.add(rightWholeLabel);
		display.add(rightNumerLabel);
		display.add(rightSlash);
		display.add(rightDenomLabel);
		display.add(equalsLabel);
		display.add(resultWholeLabel);
		display.add(resultNumerLabel);
		display.add(resultSlash);
		display.add(resultDenomLabel);

		setInvis(display);
		setInvis(barDisplay);
		leftWholeLabel.setVisible(true);
		leftWholeLabel2.setVisible(true);

		Font f = leftWholeLabel.getFont();
		for (Component c : display.getComponents())
		{
			c.setFont(f.deriveFont((float) f.getSize() + 10));
		}

		Dimension dd = display.getSize();
		dd.setSize(dd.getWidth(), dd.getHeight() + 70);
		display.setPreferredSize(dd);
		barDisplay.setPreferredSize(dd);

		display.setAlignmentX(SwingConstants.LEFT);
		barDisplay.setAlignmentX(SwingConstants.LEFT);
		display.setBackground(Color.CYAN);
		barDisplay.setBackground(Color.CYAN);

		// Adding the Logo to the gui
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		contentPane.add(northPanel, BorderLayout.NORTH);
		northPanel.add(displayCards, BorderLayout.CENTER);

		buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		leftButtons = new JPanel(new GridLayout(0, 3, 5, 5));
		rightButtons = new JPanel(new GridLayout(1, 2, 5, 5));
		opPanel = new JPanel(new GridLayout(5, 1, 5, 5));
		rightOpPanel = new JPanel(new GridLayout(5, 1, 5, 5));

		ImageIcon logo;
		if (versionStyle == 1) // JMU
		{
			logo = new ImageIcon(getClass().getResource("/resources/JMU_Logo_Purple.png"));
			northPanel.setBackground(new Color(70, 10, 132));
			setColorScheme(Color.YELLOW, displayCards);
		} else if (versionStyle == 2) // Pepsi
		{
			logo = new ImageIcon(getClass().getResource("/resources/Pepsi.png"));
			northPanel.setBackground(new Color(2, 104, 170));
			setColorScheme(new Color(0, 150, 250), displayCards);
		} else // Fragile
		{
			logo = new ImageIcon(getClass().getResource("/resources/Fragile_Logo_300x50.png"));
		}
		JLabel label = new JLabel(logo);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		northPanel.add(label, BorderLayout.NORTH);

		addButton(leftButtons, "\u00B1", Color.MAGENTA); // SIGN BUTTON
		addButton(leftButtons, "C", Color.MAGENTA); // CLEAR BUTTON
		addButton(leftButtons, "\u2190", Color.MAGENTA); // BACKSPACE BUTTON

		for (int i = 1; i <= 9; i++)
		{
			addButton(leftButtons, i + "", null);
		}
		// Add button 0 seperately since it is larger
		JButton but0 = new JButton("0");
		but0.setFocusable(false);
		but0.addActionListener(this);
		leftButtons.add(but0);

		ImageIcon image = new ImageIcon(getClass().getResource("/resources/positionButton.png"));
		focusButton = new JButton(image);
		focusButton.addActionListener(this);
		focusButton.setFocusable(false);
		leftButtons.add(focusButton);
		addButton(leftButtons, "Next", null);

		// To align better with right buttons, since they are nested in 1 more panel
		// layer.
		leftButtons.setBorder(new EmptyBorder(10, 0, 10, 10));

		// Add Operations Buttons
		addButton(opPanel, "+", Color.BLUE);
		addButton(opPanel, "-", Color.BLUE);
		addButton(opPanel, "x", Color.BLUE);
		addButton(opPanel, "\u00F7", Color.BLUE);
		addButton(opPanel, "=", Color.BLUE);

		addButton(rightOpPanel, "R", Color.BLUE);
		addButton(rightOpPanel, "Inv", Color.BLUE);
		addButton(rightOpPanel, "^", Color.BLUE);
		addButton(rightOpPanel, "&", Color.BLUE);
		addButton(rightOpPanel, "F", Color.BLUE);

		rightButtons.add(opPanel);
		rightButtons.add(rightOpPanel);

		buttons.add(leftButtons);
		buttons.add(rightButtons);
		contentPane.add(buttons, BorderLayout.CENTER);

		menu = new JMenuBar();
		// Initialize all JMenus
		displayStyle = new JMenu("Display Style");
		displayStyle.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		format = new JMenu("Format");
		format.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		historyMenu = new JMenu("History");
		historyMenu.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		intermediateSteps = new JMenu("Steps");
		intermediateSteps.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
//		help = new JMenu("Help");
//		help.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		pieChart = new JMenu("Pie Chart");
		pieChart.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		mode = new JMenu("Mode");
		mode.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));

		// Add the display styles menu items to displayStyle
		ButtonGroup styleGroup = new ButtonGroup();
		addRadioButtons(displayStyle, "Bar", styleGroup, false);
		addRadioButtons(displayStyle, "Solidus", styleGroup, false);
		addRadioButtons(displayStyle, "Slash", styleGroup, true);

		// Add the JMenuItems to Format
		ButtonGroup formatGroup = new ButtonGroup();
		addRadioButtons(format, "Reduced", formatGroup, false);
		addRadioButtons(format, "Irreduced", formatGroup, true);

		// Add the history menuItems
		addMenuItem(historyMenu, "Toggle History");
		addMenuItem(historyMenu, "Print History");
		addMenuItem(historyMenu, "Record History");
		addMenuItem(historyMenu, "Playback History");

		// Add the steps menuItem
		addMenuItem(intermediateSteps, "Toggle Intermediate Steps");

		// Add the Help menu
//		addMenuItem(help, "Help");
//		addMenuItem(help, "About");

		// Add the PieChart menu
		addMenuItem(pieChart, "Pie Chart");

		// Add the mode selector menu to the menu bar
		ButtonGroup modeGroup = new ButtonGroup();
		addRadioButtons(mode, "Proper Form", modeGroup, false);
		addRadioButtons(mode, "Improper Form", modeGroup, false);
		addRadioButtons(mode, "Mixed Form", modeGroup, true);

		// Add each tab to the MenuBar
		menu.add(displayStyle);
		menu.add(format);
		menu.add(historyMenu);
		menu.add(intermediateSteps);
//		menu.add(help);
		menu.add(mode);
		menu.add(pieChart);
		this.setJMenuBar(menu);

		JPanel historyPopout = new JPanel();
		historyPopoutButton = new JButton(">");
		historyPopoutButton.setActionCommand(">");
		historyPopoutButton.setFocusable(false);
		historyPopoutButton.addActionListener(this);
		historyPopout.add(historyPopoutButton);
		contentPane.add(historyPopout, BorderLayout.EAST);

		JPanel stepsPopout = new JPanel();
		stepsPopoutButton = new JButton("<");
		stepsPopoutButton.setActionCommand("<");
		stepsPopoutButton.setFocusable(false);
		stepsPopoutButton.addActionListener(this);
		stepsPopout.add(stepsPopoutButton);
		contentPane.add(stepsPopout, BorderLayout.WEST);

		this.setResizable(false);
		this.addComponentListener(this);
		this.pack();

		JPanel iPanel = new JPanel(new GridLayout(10, 1));
		stepsScroll = new JScrollPane(iPanel);
		stepsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		stepsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		stepsScroll.setPreferredSize(new Dimension(400, 300));
		Steps.init(iPanel);
		stepsWindow = new JWindow();
		stepsWindow.setSize(300, 300);
		stepsWindow.add(stepsScroll);

		JPanel historyPanel = new JPanel(new GridLayout(10, 1));
		historyScroll = new JScrollPane(historyPanel);
		historyScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		historyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		historyScroll.setPreferredSize(new Dimension(400, 300));
		historyPanel.setFocusable(false);
		History.init(historyPanel);
		histWindow = new JWindow();
		histWindow.setSize(300, 300);
		histWindow.add(historyScroll);

		histStr = new StringBuilder();
		historyExpressions = new ArrayList<String>();
		displayCards.addKeyListener(History.getHListener());
	}

	/**
	 * Helper method to add a button to specified panel.
	 * 
	 * @param panel The panel to be added to.
	 * @param button The name of button to be added.
	 * @param color The color of the button, can be null(default to black).
	 */
	private void addButton(JPanel panel, String button, Color color)
	{
		JButton b = new JButton(button);
		b.setFocusable(false);
		if (color != null)
		{
			b.setForeground(color);
		}
		b.addActionListener(this);
		panel.add(b);
	}

	/**
	 * Helper method to add JMenuItems to a menu.
	 * 
	 * @param menu1 is The menu to add items in.
	 * @param name is The name of item to be added.
	 */
	private void addMenuItem(JMenu menu1, String name)
	{
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(this);
		item.setActionCommand(name);
		menu1.add(item);
	}

	/**
	 * Helper method to add Radio buttons to a menu.
	 * 
	 * @param menu1 The menu to add items in.
	 * @param name The name of the button to be added.
	 * @param group The group the button belonged to.
	 * @param selected1 Whether the button should be selected by default.
	 */
	private void addRadioButtons(JMenu menu1, String name, ButtonGroup group, boolean selected1)
	{
		JRadioButton b = new JRadioButton(name);
		b.addActionListener(this);
		b.setActionCommand(name);
		group.add(b);
		b.setSelected(selected1);
		menu1.add(b);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		String command = event.getActionCommand();
		if (undefined)
		{
			resultWholeLabel.setVisible(false);
			resultNumerLabel.setVisible(false);
			resultDenomLabel.setVisible(false);
			resultSlash.setVisible(false);
		}
		if (result != null && result.getWhole() == 0)
		{
			resultWholeLabel.setVisible(false);
		} else
		{
			resultWholeLabel.setVisible(true);
		}
		if (result != null && result.getWhole() == 0 && result.getNumer() == 0
				&& result.getDenom() == 1 && !undefined)
		{
			resultWholeLabel.setVisible(true);
			resultNumerLabel.setVisible(false);
			resultDenomLabel.setVisible(false);
			resultSlash.setVisible(false);
		}
		if (command.equals("R"))
		{
			display.repaint();
			leftOperand = null;
			rightOperand = null;
			undefined = false;
			result = null;
			leftNumerLabel.setText("  ");
			leftDenomLabel.setText("  ");
			leftWholeLabel.setText("  ");
			opLabel.setText(" ");
			rightNumerLabel.setText("  ");
			rightDenomLabel.setText("  ");
			rightWholeLabel.setText("  ");
			resultWholeLabel.setText("");
			resultNumerLabel.setText("");
			resultDenomLabel.setText("");

			selected.setBorder(new LineBorder(Color.BLACK));
			selected = leftWholeLabel;
			selected.setBorder(new LineBorder(Color.RED));
			setInvis(display);
			setInvis(barDisplay);
			leftWholeLabel.setVisible(true);
			leftWholeLabel2.setVisible(true);
			leftNumer = null;
			leftDenom = null;
			rightNumer = null;
			rightDenom = null;
			resultNumer = null;
			resultDenom = null;
			fareysOrder = null;

			setBarBorder();
			Steps.reset();
			History.reset();
			historyExpressions.clear();
			playbackIndex = 0;
		} else if (command.equals("C"))
		{
			if (selected == leftWholeLabel || selected == leftNumerLabel
					|| selected == leftDenomLabel)
			{
				leftWholeLabel.setText("  ");
				leftNumerLabel.setText("  ");
				leftDenomLabel.setText("  ");
				leftNumer = null;
				leftDenom = null;
				selected.setBorder(new LineBorder(Color.BLACK));
				selected = leftWholeLabel;
				selected.setBorder(new LineBorder(Color.RED));
			} else
			{
				rightWholeLabel.setText("  ");
				rightNumerLabel.setText("  ");
				rightDenomLabel.setText("  ");
				rightNumer = null;
				rightDenom = null;
				selected.setBorder(new LineBorder(Color.BLACK));
				selected = rightWholeLabel;
				selected.setBorder(new LineBorder(Color.RED));
			}
			setBarBorder();
		} else if (command.equals("\u00B1")) // SIGN BUTTON
		{
			if (!leftWholeLabel.getText().equals("  ") && rightWholeLabel.getText().equals("  "))
			{
				int temp = Integer.parseInt(leftWholeLabel.getText());
				temp = temp * -1;
				leftWholeLabel.setText(Integer.toString(temp));
			} else if (!rightWholeLabel.getText().equals("  "))
			{
				int temp = Integer.parseInt(rightWholeLabel.getText());
				temp = temp * -1;
				rightWholeLabel.setText(Integer.toString(temp));
			}
			setBarBorder();
		} else if (command.equals("Inv")) // INVERSE BUTTON
		{
			if (selected == leftWholeLabel || selected == leftNumerLabel
					|| selected == leftDenomLabel)
			{
				parseInput(leftOperand);
				leftOperand = Operations.inverse(leftOperand);
				leftNumer = leftOperand.getNumer();
				leftDenom = leftOperand.getDenom();
				displayFrac(leftOperand, style);
			} else
			{
				parseInput(rightOperand);
				rightOperand = Operations.inverse(rightOperand);
				rightNumer = rightOperand.getNumer();
				rightDenom = rightOperand.getDenom();
				displayFrac(rightOperand, style);
			}
			setBarBorder();
		} else if (command.equals("+") || command.equals("-") || command.equals("x")
				|| command.equals("\u00F7") || command.equals("&") || command.equals("^")
				|| command.equals("F"))
		{
			if (result != null)
			{
				leftOperand = result;
				displayFrac(leftOperand, style);
			}
			opLabel.setText(command);
			if (command.equals("^"))
				opLabel.setText("^");
			if (command.equals("F"))
			{
				String input = JOptionPane.showInputDialog(this,
						"Please enter the order of the farey sequence", "Farey's sequence order",
						JOptionPane.QUESTION_MESSAGE);
				if (input != null)
				{
					input = input.replaceAll("\\s", "");
					try
					{
						fareysOrder = Integer.parseInt(input);
					} catch (NumberFormatException e)
					{
						JOptionPane.showMessageDialog(this, "Please only enter numbers", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			afterOpClear();
			opLabel.setVisible(true);
			opLabel2.setVisible(true);
			rightWholeLabel.setVisible(true);
			rightWholeLabel2.setVisible(true);

			selected.setBorder(new LineBorder(Color.BLACK));
			selected = rightWholeLabel;
			selected.setBorder(new LineBorder(Color.RED));
			setBarBorder();
		} else if (command.equals("="))
		{
			String operation = opLabel.getText();
			if (!operation.equals(" ") && result != null)
			{
				leftOperand = result;
				displayFrac(leftOperand, style);
			} else
			{
				parseInput(leftOperand);
			}
			parseInput(rightOperand);
			switch (operation)
			{
				case "+":
					result = Operations.add(leftOperand, rightOperand);
					break;
				case "-":
					result = Operations.subtract(leftOperand, rightOperand);
					break;
				case "x":
					result = Operations.multiply(leftOperand, rightOperand);
					break;
				case "\u00F7":
					if (rightOperand.getWhole() == 0 && rightOperand.getNumer() == 0
							&& rightOperand.getDenom() == 1)
					{
						undefined = true;
					}
					result = Operations.divide(leftOperand, rightOperand);
					break;
				case "&":
					result = Operations.mediant(leftOperand, rightOperand);
					break;
				case "^":
					result = Operations.pow(leftOperand, rightOperand.getWhole());
					break;
				case "F":
					result = Operations.farey(leftOperand, rightOperand, fareysOrder);
					result.setWhole(leftOperand.getWhole());
					break;
				default: // should not get here
					break;
			}
			irreducedFrac = result;
			reducedFrac = Operations.simplify(result);
			if (this.isReduced)
				result = reducedFrac;
			equalsLabel.setVisible(true);
			equalsLabel2.setVisible(true);
			resultSlash.setVisible(true);
			resultWholeLabel.setVisible(true);
			resultWholeLabel2.setVisible(true);
			if (result.getNumer() == 0)
			{
				resultNumerLabel.setVisible(false);
				resultDenomLabel.setVisible(false);
				resultSlash.setVisible(false);
			} else
			{
				resultNumerLabel.setVisible(true);
				resultDenomLabel.setVisible(true);
			}
			if (result.getNumer() == 0)
				res.setVisible(false);
			else
				res.setVisible(true);
			displayFrac(leftOperand, style);
			displayFrac(rightOperand, style);
			resultNumer = result.getNumer();
			resultDenom = result.getDenom();
			displayFrac(result, style);
			resBar.setVisible(true);
			setBarBorder();
			Steps.eval(leftOperand, operation, rightOperand, result, style, fareysOrder);
			History.print(leftOperand, style, operation, rightOperand, result);
			// Add the current values to the histStr stringbuilder
			histStr.append(
					leftOperand.toString(3) + " " + operation + " " + rightOperand.toString(3)
							+ " = " + result.toString(3) + System.lineSeparator());
			if (result != null && result.getWhole() == 0 && result.getNumer() == 0
					&& result.getDenom() == 1 && !undefined)
			{
				resultWholeLabel.setVisible(true);
				resultNumerLabel.setVisible(false);
				resultDenomLabel.setVisible(false);
				resultSlash.setVisible(false);
			}
			if (undefined)
			{
				resultWholeLabel.setVisible(false);
				resultNumerLabel.setVisible(false);
				resultDenomLabel.setVisible(false);
				resultSlash.setVisible(false);
			}
		} else if (command.equals("\u2190")) // backspace
		{
			int len = selected.getText().length(); // get the length of the current field
			if (len < 2)
			{
				selected.setText("  ");
				if (selected == leftNumerLabel)
					leftNumer = null;
				else if (selected == leftDenomLabel)
					leftDenom = null;
				else if (selected == rightNumerLabel)
					rightNumer = null;
				else if (selected == rightDenomLabel)
					rightDenom = null;
			} else if (selected.getText().equals("  "))
			{
				boolean set = false;
				selected.setBorder(new LineBorder(Color.BLACK));
				if (!set && selected == leftWholeLabel)
				{
					selected = leftDenomLabel;
					set = true;
				} else if (!set && selected == leftNumerLabel)
				{
					selected = leftWholeLabel;
					set = true;
				} else if (!set && selected == leftDenomLabel)
				{
					selected = leftNumerLabel;
					set = true;
				} else if (!set && selected == rightWholeLabel && !opLabel.getText().equals("^"))
				{
					selected = rightDenomLabel;
					set = true;
				} else if (!set && selected == rightNumerLabel)
				{
					selected = rightWholeLabel;
					set = true;
				} else if (!set && selected == rightDenomLabel)
				{
					selected = rightNumerLabel;
					set = true;
				}
				selected.setBorder(new LineBorder(Color.RED));
			} else
			{
				// remove the right most character
				selected.setText(selected.getText().substring(0, len - 1));
				if (selected == leftNumerLabel)
					leftNumer = Integer.parseInt(selected.getText());
				if (selected == leftDenomLabel)
					leftDenom = Integer.parseInt(selected.getText());
				if (selected == rightNumerLabel)
					rightNumer = Integer.parseInt(selected.getText());
				if (selected == rightDenomLabel)
					rightDenom = Integer.parseInt(selected.getText());
			}
			setBarBorder();
		} else if (command.equals("Bar"))
		{
			if (style != 1)
			{
				style = 1;
				styleChange(style);
			}
		} else if (command.equals("Solidus"))
		{
			if (style != 2)
			{
				style = 2;
				styleChange(style);
			}
		} else if (command.equals("Slash"))
		{
			if (style != 3)
			{
				style = 3;
				styleChange(style);
			}
		} else if (event.getSource().equals(focusButton))
		{
			boolean set = false;
			selected.setBorder(new LineBorder(Color.BLACK));
			if (!set && selected == leftWholeLabel)
			{
				selected = leftNumerLabel;
				left.setVisible(true);
				set = true;
			} else if (!set && selected == leftNumerLabel)
			{
				selected = leftDenomLabel;
				leftSlash.setVisible(true);
				set = true;
			} else if (!set && selected == leftDenomLabel)
			{
				selected = leftWholeLabel;
			} else if (!set && selected == rightWholeLabel && !opLabel.getText().equals("^"))
			{
				selected = rightNumerLabel;
				right.setVisible(true);
				set = true;
			} else if (!set && selected == rightNumerLabel)
			{
				selected = rightDenomLabel;
				rightSlash.setVisible(true);
				set = true;
			} else if (!set && selected == rightDenomLabel)
			{
				selected = rightWholeLabel;
				set = true;
			}
			selected.setVisible(true);
			selected.setBorder(new LineBorder(Color.RED));
			setBarBorder();
		} else if (command.equals("0"))
			setInput("0");
		else if (command.equals("1"))
			setInput("1");
		else if (command.equals("2"))
			setInput("2");
		else if (command.equals("3"))
			setInput("3");
		else if (command.equals("4"))
			setInput("4");
		else if (command.equals("5"))
			setInput("5");
		else if (command.equals("6"))
			setInput("6");
		else if (command.equals("7"))
			setInput("7");
		else if (command.equals("8"))
			setInput("8");
		else if (command.equals("9"))
			setInput("9");
		else if (command.equals("Next"))
		{
			if (historyExpressions.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Please select file to read from first");
			} else
			{ // parse the next line from the file
				String curLine;
				String[] parsedLine;
				Fraction leftFraction, rightFraction, answerFraction;
				String leftStr = "", rightStr = "", ansStr = "", opStr = "";

				if (historyExpressions.isEmpty()) // If empty then ask for a file
				{
					JOptionPane.showMessageDialog(this, "Please select file to read from first");
				} else
				{ // else take the next equation from the file
					if (playbackIndex >= historyExpressions.size())
						JOptionPane.showMessageDialog(this,
								"There are no more equations to playback in the file.");
					else
					{
						curLine = historyExpressions.get(playbackIndex);
						playbackIndex++;
						// parse the current line into the array based on spaces
						parsedLine = curLine.split(" ");
						// parsedLine can be a few different sizes
						if (parsedLine.length == 5) // no additional fractions
						{
							leftStr = parsedLine[0];
							opStr = parsedLine[1];
							rightStr = parsedLine[2];
							ansStr = parsedLine[4];
						} else if (parsedLine.length == 6) // one number has an additional fraction
						{
							// check which number has an additional fraction
							if (parsedLine[1].length() > 1) // the first number has a fraction
							{
								leftStr = parsedLine[0] + " " + parsedLine[1];
								opStr = parsedLine[2];
								rightStr = parsedLine[3];
								ansStr = parsedLine[5];
							} else if (parsedLine[3].length() > 1)
							// the second number has a fraction
							{
								leftStr = parsedLine[0];
								opStr = parsedLine[1];
								rightStr = parsedLine[2] + " " + parsedLine[3];
								ansStr = parsedLine[5];
							} else // the answer has a fraction
							{
								leftStr = parsedLine[0];
								opStr = parsedLine[1];
								rightStr = parsedLine[2];
								ansStr = parsedLine[4] + " " + parsedLine[5];
							}
						} else if (parsedLine.length == 7) // one number doesn't have a fraction
						{
							// check which number DOES NOT have an additional fraction
							// the first number doesn't have a fraction
							if (parsedLine[1].length() == 1)
							{
								leftStr = parsedLine[0];
								opStr = parsedLine[1];
								rightStr = parsedLine[2] + " " + parsedLine[3];
								ansStr = parsedLine[5] + " " + parsedLine[6];
							}
							// the second number doesn't has a fraction
							else if (parsedLine[4].length() == 1)
							{
								leftStr = parsedLine[0] + " " + parsedLine[1];
								opStr = parsedLine[2];
								rightStr = parsedLine[3];
								ansStr = parsedLine[5] + " " + parsedLine[6];
							} else // the answer has no extra fraction
							{
								leftStr = parsedLine[0] + " " + parsedLine[1];
								opStr = parsedLine[2];
								rightStr = parsedLine[3] + " " + parsedLine[4];
								ansStr = parsedLine[6];
							}
						} else if (parsedLine.length == 8) // all of the numbers have a fraction
						{
							leftStr = parsedLine[0] + " " + parsedLine[1];
							opStr = parsedLine[2];
							rightStr = parsedLine[3] + " " + parsedLine[4];
							ansStr = parsedLine[6] + " " + parsedLine[7];
						}
						leftFraction = Fraction.parseFraction(leftStr);
						rightFraction = Fraction.parseFraction(rightStr);
						answerFraction = Fraction.parseFraction(ansStr);

						leftOperand = leftFraction;
						displayFrac(leftOperand, style);
						rightOperand = rightFraction;
						displayFrac(rightOperand, style);
						result = answerFraction;
						displayFrac(result, style);
						opLabel.setText(opStr);
						opLabel.setVisible(true);
						opLabel2.setText(opStr);
						opLabel2.setVisible(true);
						equalsLabel.setText("=");
						equalsLabel.setVisible(true);

						Steps.eval(leftOperand, opStr, rightOperand, result, style, fareysOrder);
						History.print(leftOperand, style, opStr, rightOperand, result);
					}
				}
			}
		}
		// Reduced handling
		else if (command.equals("Reduced"))
		{
			this.isReduced = true;
			if (result != null)
			{
				result = reducedFrac;
				resultWholeLabel.setText("" + result.getWhole());
				resultNumerLabel.setText("" + result.getNumer());
				resultDenomLabel.setText("" + result.getDenom());
			}
		} else if (command.equals("Irreduced"))
		{
			this.isReduced = false;
			if (result != null)
			{
				result = irreducedFrac;
				resultWholeLabel.setText("" + result.getWhole());
				resultNumerLabel.setText("" + result.getNumer());
				resultDenomLabel.setText("" + result.getDenom());
			}
		} else if (command.equals("Print History"))
		{
			History.printHistory();
		} else if (command.equals("Toggle History") || command.equals(">"))
		{
			if (!histWindow.isVisible())
			{
				History.toggle(histWindow);
				historyPopoutButton.setText("<");
				int correctX = histWindow.getX();
				histWindow.setLocation(correctX - 300, histWindow.getY());
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						histWindow.setLocation(histWindow.getX() + 20, histWindow.getY());
						if (histWindow.getX() >= correctX)
						{
							timer.cancel();
						}
					}
				}, 0, 10);
			} else
			{
				historyPopoutButton.setText(">");
				int correctX = histWindow.getX();
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						histWindow.setLocation(histWindow.getX() - 20, histWindow.getY());
						if (histWindow.getX() <= correctX - 300)
						{
							History.toggle(histWindow);
							histWindow.setLocation(correctX, histWindow.getY());
							timer.cancel();
						}
					}
				}, 0, 10);
			}
		} else if (command.equals("Toggle Intermediate Steps") || command.equals("<"))
		{
			if (!stepsWindow.isVisible())
			{
				Steps.toggle(stepsWindow);
				stepsPopoutButton.setText(">");
				int correctX = stepsWindow.getX();
				stepsWindow.setLocation(correctX + 300, stepsWindow.getY());
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						stepsWindow.setLocation(stepsWindow.getX() - 20, stepsWindow.getY());
						if (stepsWindow.getX() <= correctX)
						{
							timer.cancel();
						}
					}
				}, 0, 10);
			} else
			{
				stepsPopoutButton.setText("<");
				int correctX = stepsWindow.getX();
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						stepsWindow.setLocation(stepsWindow.getX() + 20, stepsWindow.getY());
						if (stepsWindow.getX() >= correctX + 300)
						{
							Steps.toggle(stepsWindow);
							stepsWindow.setLocation(correctX, stepsWindow.getY());
							timer.cancel();
						}
					}
				}, 0, 10);
			}
		}
		// Mode Handling
		else if (command.equals("Improper Form"))
		{
			if (result != null && result.getNumer() != 0)
			{
				result = Operations.toImproper(result);
				resultWholeLabel.setVisible(false);
				resultNumerLabel.setText("" + result.getNumer());
				resultDenomLabel.setText("" + result.getDenom());
			}
		} else if (command.equals("Mixed Form") || command.equals("Proper Form"))
		{
			if (result != null)
			{
				result = reducedFrac;
				resultWholeLabel.setVisible(true);
				resultWholeLabel.setText("" + result.getWhole());
				resultNumerLabel.setText("" + result.getNumer());
				resultDenomLabel.setText("" + result.getDenom());
			}
		} else if (command.equals("Record History"))
		{
			try
			{
				History.recording(histStr.toString());
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(this, "Failed to record the history", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (command.equals("Playback History"))
		{
			String fileName = JOptionPane.showInputDialog(this, "Please enter the file to read",
					"Open", JOptionPane.QUESTION_MESSAGE);
			if (fileName != null)
			{
				File file = new File(fileName);
				try
				{
					Scanner sc = new Scanner(file);
					historyExpressions.clear();
					while (sc.hasNextLine())
					{
						historyExpressions.add(sc.nextLine());
					}
					playbackIndex = 0;
					sc.close();
				} catch (IOException e)
				{
					if (e instanceof FileNotFoundException)
						JOptionPane.showMessageDialog(this, "File not found", "Error",
								JOptionPane.ERROR_MESSAGE);
					else
						e.printStackTrace();
				}
			}
		}
//		else if (command.equals("Help"))
//		{
//			this.displayWebPage("src/resources/help.html");
//
//		} else if (command.equals("About"))
//		{
//			this.displayWebPage("src/resources/about.html");
//		} 
		else if (command.contentEquals("Pie Chart"))
		{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame frame = new JFrame("PieChart Example ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setBackground(Color.white);
			frame.setLocation(this.getX(), this.getY() - 250);
			frame.setSize(900, 200);
			frame.setFocusable(false);
			PieChart panel = new PieChart(leftOperand.getNumer(), leftOperand.getDenom(),
					rightOperand.getNumer(), rightOperand.getDenom(), result.getNumer(),
					result.getDenom(), opLabel.getText());
			frame.add(panel);
			frame.setVisible(true);
		}
		this.toFront();
	}

	/**
	 * Set the input to selected field.
	 * 
	 * @param input The input to be set.
	 */
	private static void setInput(String input)
	{
		try
		{
			if (!input.isEmpty())
			{

				if (selected == leftNumerLabel)
				{
					if (leftNumer != null)
						leftNumer = Integer.valueOf("" + leftNumer + input);
					else
						leftNumer = Integer.valueOf(input);
				} else if (selected == leftDenomLabel)
				{
					if (leftDenom != null)
						leftDenom = Integer.valueOf("" + leftDenom + input);
					else
						leftDenom = Integer.valueOf(input);
				} else if (selected == rightNumerLabel)
				{
					if (rightNumer != null)
						rightNumer = Integer.valueOf("" + rightNumer + input);
					else
						rightNumer = Integer.valueOf(input);
				} else if (selected == rightDenomLabel)
				{
					if (rightDenom != null)
						rightDenom = Integer.valueOf("" + rightDenom + input);
					else
						rightDenom = Integer.valueOf(input);
				}

			}
			String str = "";
			if (!selected.getText().equals("  "))
				str = selected.getText();
			if (!input.isEmpty())
				selected.setText(str + input);
		} catch (NumberFormatException e)
		{
			String message = String.format("Inputs only support numbers less than %d",
					Integer.MAX_VALUE);
			JOptionPane.showMessageDialog(self, message, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		leftWholeLabel2.setText(leftWholeLabel.getText());
		leftNumerLabel2.setText(leftNumerLabel.getText());
		leftDenomLabel2.setText(leftDenomLabel.getText());
		opLabel2.setText(opLabel.getText());
		rightWholeLabel2.setText(rightWholeLabel.getText());
		rightNumerLabel2.setText(rightNumerLabel.getText());
		rightDenomLabel2.setText(rightDenomLabel.getText());
		resultWholeLabel2.setText(resultWholeLabel.getText());
		resultNumerLabel2.setText(resultNumerLabel.getText());
		resultDenomLabel2.setText(resultDenomLabel.getText());

		setBarBorder();
		if (style == 2)
			styleChange(style);
	}

	/**
	 * Sets border in display for bar form to match regular form.
	 */
	private static void setBarBorder()
	{
		if (!Thread.currentThread().getStackTrace()[2].getMethodName().equals("setInput"))
		{
			JLabel temp = selected;
			selected = leftWholeLabel;
			setInput("");
			selected = temp;
		}
		if (selected == leftWholeLabel)
			leftWholeLabel2.setBorder(new LineBorder(Color.RED));
		else
			leftWholeLabel2.setBorder(new LineBorder(Color.BLACK));

		if (selected == leftNumerLabel)
			leftNumerLabel2.setBorder(new LineBorder(Color.RED));
		else
			leftNumerLabel2.setBorder(new LineBorder(Color.BLACK));

		if (selected == leftDenomLabel)
			leftDenomLabel2.setBorder(new LineBorder(Color.RED));
		else
			leftDenomLabel2.setBorder(new LineBorder(Color.BLACK));

		if (selected == rightWholeLabel)
			rightWholeLabel2.setBorder(new LineBorder(Color.RED));
		else
			rightWholeLabel2.setBorder(new LineBorder(Color.BLACK));

		if (selected == rightNumerLabel)
			rightNumerLabel2.setBorder(new LineBorder(Color.RED));
		else
			rightNumerLabel2.setBorder(new LineBorder(Color.BLACK));

		if (selected == rightDenomLabel)
			rightDenomLabel2.setBorder(new LineBorder(Color.RED));
		else
			rightDenomLabel2.setBorder(new LineBorder(Color.BLACK));
		setBarDashLength();
	}

	/**
	 * Helper method to clear all display to the right of the operator after user
	 * press an op.
	 */
	private void afterOpClear()
	{
		rightWholeLabel.setText("  ");
		rightNumerLabel.setText("  ");
		rightDenomLabel.setText("  ");
		equalsLabel.setVisible(false);
		equalsLabel2.setVisible(false);
		resultWholeLabel.setText("");
		resultNumerLabel.setText("");
		resultDenomLabel.setText("");
		resultSlash.setVisible(false);
		rightNumer = null;
		rightDenom = null;
	}

	/**
	 * Helper method for displaying a new filled out fraction.
	 * 
	 * @param pos The fraction position.
	 * @param fstyle The style to be displayed as.
	 */
	private static void displayFrac(Fraction pos, int fstyle)
	{
		if (pos == leftOperand)
		{
			leftWholeLabel.setText("" + leftOperand.getWhole());
			if (leftOperand.getWhole() != 0)
			{
				leftWholeLabel.setVisible(true);
				leftWholeLabel2.setVisible(true);
			}
			leftNumer = leftOperand.getNumer();
			leftNumerLabel.setText("" + leftNumer);
			leftDenom = leftOperand.getDenom();
			leftDenomLabel.setText("" + leftDenom);
			if (leftNumer != 0 && leftDenom != 0)
			{
				leftNumerLabel.setVisible(true);
				leftDenomLabel.setVisible(true);
				leftSlash.setVisible(true);
				left.setVisible(true);
				leftBar.setVisible(true);
			}
		} else if (pos == rightOperand)
		{
			rightWholeLabel.setText("" + rightOperand.getWhole());
			if (rightOperand.getWhole() != 0)
			{
				rightWholeLabel.setVisible(true);
				rightWholeLabel2.setVisible(true);
			}
			rightNumer = rightOperand.getNumer();
			rightNumerLabel.setText("" + rightNumer);
			rightDenom = rightOperand.getDenom();
			rightDenomLabel.setText("" + rightDenom);
			if (rightNumer != 0 && rightDenom != 0)
			{
				rightNumerLabel.setVisible(true);
				rightDenomLabel.setVisible(true);
				rightSlash.setVisible(true);
				right.setVisible(true);
				rightBar.setVisible(true);
			}
		} else if (pos == result)
		{
			resultWholeLabel.setText("" + result.getWhole());
			if (result.getWhole() != 0)
			{
				resultWholeLabel.setVisible(true);
				resultWholeLabel2.setVisible(true);
			}
			resultNumer = result.getNumer();
			resultNumerLabel.setText("" + resultNumer);
			resultDenom = result.getDenom();
			resultDenomLabel.setText("" + resultDenom);
			if (resultNumer != 0 && resultDenom != 0)
			{
				resultNumerLabel.setVisible(true);
				resultDenomLabel.setVisible(true);
				resultSlash.setVisible(true);
				res.setVisible(true);
				resBar.setVisible(true);
			}
			if (result.getWhole() == 0)
			{
				resultWholeLabel.setVisible(false);
				resultWholeLabel.setVisible(false);
			}
		}
		styleChange(fstyle);
	}

	/**
	 * Helper method to instantly change the display's style.
	 * 
	 * @param fstyle The style to change to. 1 = bar; 2 = solidus; 3 = slash.
	 */
	private static void styleChange(int fstyle)
	{
		if (fstyle == 2)
		{
			if (leftNumer != null)
				leftNumerLabel.setText(Fraction.superscript(leftNumer));
			if (leftDenom != null)
				leftDenomLabel.setText(Fraction.subscript(leftDenom));
			if (rightNumer != null)
				rightNumerLabel.setText(Fraction.superscript(rightNumer));
			if (rightDenom != null)
				rightDenomLabel.setText(Fraction.subscript(rightDenom));
			if (resultNumer != null)
				resultNumerLabel.setText(Fraction.superscript(resultNumer));
			if (resultDenom != null)
				resultDenomLabel.setText(Fraction.subscript(resultDenom));
		} else
		{
			if (leftNumer != null)
				leftNumerLabel.setText("" + leftNumer);
			if (leftDenom != null)
				leftDenomLabel.setText("" + leftDenom);
			if (rightNumer != null)
				rightNumerLabel.setText("" + rightNumer);
			if (rightDenom != null)
				rightDenomLabel.setText("" + rightDenom);
			if (resultNumer != null)
				resultNumerLabel.setText("" + resultNumer);
			if (resultDenom != null)
				resultDenomLabel.setText("" + resultDenom);
		}
		if (fstyle == 1)
		{
			CardLayout c1 = (CardLayout) displayCards.getLayout();
			c1.show(displayCards, "barDisplay");
			setBarBorder();
		} else
		{
			CardLayout c1 = (CardLayout) displayCards.getLayout();
			c1.show(displayCards, "display");
		}
	}

	/**
	 * Sets value of text field to numeric Key.
	 */
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
			((JButton) opPanel.getComponent(4)).doClick();
			return;
		}

		switch (arg0.getKeyChar())
		{
			case '0':
				setInput("0");
				break;
			case '1':
				setInput("1");
				break;
			case '2':
				setInput("2");
				break;
			case '3':
				setInput("3");
				break;
			case '4':
				setInput("4");
				break;
			case '5':
				setInput("5");
				break;
			case '6':
				setInput("6");
				break;
			case '7':
				setInput("7");
				break;
			case '8':
				setInput("8");
				break;
			case '9':
				setInput("9");
				break;
			case '+':
				((JButton) opPanel.getComponent(0)).doClick();
				break;
			case '-':
				((JButton) opPanel.getComponent(1)).doClick();
				break;
			case '*':
				((JButton) opPanel.getComponent(2)).doClick();
				break;
			case '/':
				((JButton) opPanel.getComponent(3)).doClick();
				break;
			case '=':
				((JButton) opPanel.getComponent(4)).doClick();
				break;
			case 'r':
				((JButton) rightOpPanel.getComponent(0)).doClick();
				break;
			case 'i':
				((JButton) rightOpPanel.getComponent(1)).doClick();
				break;
			case '^':
				((JButton) rightOpPanel.getComponent(2)).doClick();
				break;
			case '&':
				((JButton) rightOpPanel.getComponent(3)).doClick();
				break;
			case 'f':
				((JButton) rightOpPanel.getComponent(4)).doClick();
				break;
			case 8: // backspace
				((JButton) leftButtons.getComponent(2)).doClick();
				break;
			case '.':
				((JButton) leftButtons.getComponent(13)).doClick();
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// NOT USED.
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// NOT USED.
	}

	/**
	 * Helper method to set everything inside container to be not visible.
	 * 
	 * @param container The container to iterate through
	 */
	private void setInvis(Container container)
	{
		for (Component c : container.getComponents())
		{
			c.setVisible(false);
		}
	}

	/**
	 * Parse what the user entered. If only whole numbers, set fraction to 0/1.
	 * 
	 * @param frac The left or right Operand.
	 */
	private void parseInput(Fraction frac)
	{
		if (frac == leftOperand)
		{
			if (!leftWholeLabel.getText().equals("  "))
			{
				if (leftNumer == null)
					leftNumer = 0;
				if (leftDenom == null)
					leftDenom = 1;
			}
			leftOperand = Fraction
					.parseFraction(leftWholeLabel.getText() + " " + leftNumer + "/" + leftDenom);
		} else if (frac == rightOperand)
		{
			if (!rightWholeLabel.getText().equals("  "))
			{
				if (rightNumer == null)
					rightNumer = 0;
				if (rightDenom == null)
					rightDenom = 1;
			}
			rightOperand = Fraction
					.parseFraction(rightWholeLabel.getText() + " " + rightNumer + "/" + rightDenom);
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0)
	{
		// Unused
	}

	@Override
	public void componentMoved(ComponentEvent arg)
	{
		double x = this.getLocationOnScreen().getX();
		double y = this.getLocationOnScreen().getY();
		histWindow.setLocation((int) x + this.getWidth(), (int) y + 60);
		// histWindow.setAlwaysOnTop(true);
		stepsWindow.setLocation((int) x - 300, (int) y + 60);
		// stepsWindow.setAlwaysOnTop(true);
		if (histWindow.isVisible())
			histWindow.toFront();
		if (stepsWindow.isVisible())
			stepsWindow.toFront();
	}

	@Override
	public void componentResized(ComponentEvent arg0)
	{
		// Unused
	}

	@Override
	public void componentShown(ComponentEvent arg0)
	{
		// Unused
	}

	/**
	 * Helper methods to set color scheme of display panel.
	 * 
	 * @param color The new color to set.
	 * @param container is the container used.
	 */
	private void setColorScheme(Color color, Container container)
	{
		for (Component c : container.getComponents())
		{
			c.setBackground(color);
			if (c instanceof JPanel)
				setColorScheme(color, (JPanel) c);
		}
	}

//	/**
//	 * Method to display an html page within the gui.
//	 * 
//	 * @param html path to html file
//	 */
//	private void displayWebPage(String html)
//	{
//		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
//		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
//		{
//			File helpPage = new File(html);
//			URI uri = helpPage.toURI();
//			try
//			{
//				desktop.browse(uri);
//			} catch (IOException e1)
//			{
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			JEditorPane jep = new JEditorPane();
//			jep.setEditable(false);
//
//			try
//			{
//				jep.setPage(uri.toURL());
//			} catch (IOException e)
//			{
//				jep.setContentType("text/html");
//				jep.setText("<html>Could not load</html>");
//			}

//			JScrollPane scrollPane = new JScrollPane(jep);
//			JFrame f = new JFrame("");
//			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//			f.getContentPane().add(scrollPane);
//			f.setPreferredSize(new Dimension(800, 600));
//			f.setSize(800, 600);
//			f.setVisible(true);
//			f.setAlwaysOnTop(true);
//		}
//	}

	/**
	 * Helper method for copying pasting from history.
	 * 
	 * @param frac The left operand.
	 */
	public static void setLeftOperand(Fraction frac)
	{
		leftOperand = new Fraction(frac.getWhole(), frac.getNumer(), frac.getDenom());
		displayFrac(leftOperand, style);
	}

	/**
	 * Helper method for copying pasting from history.
	 * 
	 * @param frac The right operand.
	 */
	public static void setRightOperand(Fraction frac)
	{
		rightOperand = new Fraction(frac.getWhole(), frac.getNumer(), frac.getDenom());
		displayFrac(rightOperand, style);
	}

	/**
	 * Helper method for copying pasting from history.
	 * 
	 * @param frac The result.
	 */
	public static void setResult(Fraction frac)
	{
		result = new Fraction(frac.getWhole(), frac.getNumer(), frac.getDenom());
		displayFrac(result, style);
	}

	/**
	 * Sets the result to null for pasting functionality.
	 */
	public static void setResultNull()
	{
		result = null;
		resultWholeLabel.setText("");
		resultNumerLabel.setText("");
		resultDenomLabel.setText("");
		resultSlash.setVisible(false);
		resBar.setVisible(false);
		setBarBorder();
	}

	/**
	 * Helper method for setting the dashes for bar style dynamically.
	 */
	private static void setBarDashLength()
	{
		leftBar.setText("-");
		rightBar.setText("-");
		resBar.setText("-");
		while (leftBar.getText().length() < leftNumerLabel.getText().length() * 2
				|| leftBar.getText().length() < 4)
		{
			leftBar.setText(leftBar.getText() + "-");
		}
		while (leftBar.getText().length() < leftDenomLabel.getText().length() * 2
				|| leftBar.getText().length() < 4)
		{
			leftBar.setText(leftBar.getText() + "-");
		}

		while (rightBar.getText().length() < rightNumerLabel.getText().length() * 2
				|| rightBar.getText().length() < 4)
		{
			rightBar.setText(rightBar.getText() + "-");
		}
		while (rightBar.getText().length() < rightDenomLabel.getText().length() * 2
				|| rightBar.getText().length() < 4)
		{
			rightBar.setText(rightBar.getText() + "-");
		}

		while (resBar.getText().length() < resultNumerLabel.getText().length() * 2
				|| resBar.getText().length() < 4)
		{
			resBar.setText(resBar.getText() + "-");
		}
		while (resBar.getText().length() < resultDenomLabel.getText().length() * 2
				|| resBar.getText().length() < 4)
		{
			resBar.setText(resBar.getText() + "-");
		}
	}
}
