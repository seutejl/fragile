package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.EtchedBorder;

import calculations.Fraction;

/**
 * History page for the calculator.
 * 
 * @author Tinh Tran and Jeremy Seute
 *
 */
public class History
{
	private static JPanel historyPanel;
	private static int histCount;
	private static HistoryListener listener;
	private static JMenuBar menu;

	/**
	 * Initialized the history panel.
	 * 
	 * @param panel The panel passed in from main window app.
	 */
	public static void init(JPanel panel)
	{
		historyPanel = panel;
		listener = new HistoryListener();
		addMenu();
	}

	/**
	 * Adds the copy/paste menu to the history panel.
	 */
	public static void addMenu()
	{
		menu = new JMenuBar();
		JButton citem = new JButton("Copy");
		citem.addActionListener(listener);
		citem.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		JButton pLitem = new JButton("Paste Left");
		pLitem.addActionListener(listener);
		pLitem.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		JButton pRitem = new JButton("Paste Right");
		pRitem.addActionListener(listener);
		pRitem.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
		menu.add(citem);
		menu.add(pLitem);
		menu.add(pRitem);
		historyPanel.add(menu);
		histCount = 0;
	}

	/**
	 * Clears everything in history panel after user pressed reset button.
	 */
	public static void reset()
	{
		Component[] components = historyPanel.getComponents();
		for (Component c : components)
		{
			if (!c.equals(menu))
			{
				historyPanel.remove(c);
			}
		}
		histCount = 0;
		((GridLayout) historyPanel.getLayout()).setRows(10);
		historyPanel.repaint();
	}

	/**
	 * Toggled the visibility of passed in window.
	 * 
	 * @param window The window to toggle.
	 */
	public static void toggle(JWindow window)
	{
		if (window.isVisible())
			window.setVisible(false);
		else
			window.setVisible(true);
	}

	/**
	 * Print out the entered expression after user hit evaluate.
	 * 
	 * @param leftOperand Left operand of expression.
	 * @param style Current style of the display.
	 * @param operation The operation used.
	 * @param rightOperand Right operand of expression.
	 * @param result Result of expression.
	 */
	public static void print(Fraction leftOperand, int style, String operation,
			Fraction rightOperand, Fraction result)
	{
		JPanel temp = new JPanel();
		if (style == 1)
		{
			temp.add(new JPanel());
			temp.add(new JLabel(""));
			temp.add(new JPanel());
			temp.add(new JLabel(" = "));
			temp.add(new JPanel());
		} else
		{
			for (int ii = 0; ii < 8; ii++)
			{
				JLabel temp2 = new JLabel("" + ii);
				temp2.setText("");
				temp.add(temp2);
			}
		}
		historyPanel.add(temp);

		JLabel temp2 = (JLabel) temp.getComponent(3);
		if (style == 1)
		{
			JLabel temp3 = new JLabel("" + leftOperand.getWhole());
			JLabel temp4 = new JLabel("" + leftOperand.toString(style));
			((JPanel) temp.getComponent(0)).add(temp3);
			((JPanel) temp.getComponent(0)).add(temp4);
			((JPanel) temp.getComponent(0)).addMouseListener(listener);
			((JPanel) temp.getComponent(0)).setName(leftOperand.toString(3));
			
		} else
		{
			temp2.setText(temp2.getText() + leftOperand.toString(style));
			temp2.addMouseListener(listener);
		}

		if (style == 1)
		{
			((JLabel) temp.getComponent(1)).setText("" + operation);
			JLabel temp3 = new JLabel("" + rightOperand.getWhole());
			JLabel temp4 = new JLabel("" + rightOperand.toString(style));
			((JPanel) temp.getComponent(2)).add(temp3);
			((JPanel) temp.getComponent(2)).add(temp4);
			((JPanel) temp.getComponent(2)).addMouseListener(listener);
			((JPanel) temp.getComponent(2)).setName(rightOperand.toString(3));
			
			((JLabel) temp.getComponent(3)).setText(" = ");
			JLabel temp5 = new JLabel("" + result.getWhole());
			JLabel temp6 = new JLabel("" + result.toString(style));
			((JPanel) temp.getComponent(4)).add(temp5);
			((JPanel) temp.getComponent(4)).add(temp6);
			((JPanel) temp.getComponent(4)).addMouseListener(listener);
			((JPanel) temp.getComponent(4)).setName(result.toString(3));
			
		} else
		{

			temp2 = (JLabel) temp.getComponent(4);
			temp2.setText(temp2.getText() + " " + operation + " ");
			temp2 = (JLabel) temp.getComponent(5);
			temp2.addMouseListener(listener);
			temp2.setText(temp2.getText() + rightOperand.toString(style));
			temp2 = (JLabel) temp.getComponent(6);
			temp2.setText(temp2.getText() + " = ");
			temp2 = (JLabel) temp.getComponent(7);
			temp2.addMouseListener(listener);
			temp2.setText(temp2.getText() + result.toString(style));
		}
		histCount++;
		if (histCount + 1 > 10)
		{
			((GridLayout) historyPanel.getLayout()).setRows(histCount + 1);
		}
	}

	/**
	 * Sends out whatever is currently on the history panel to a printer.
	 */
	public static void printHistory()
	{
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setJobName(" Print History ");
		pj.setPrintable(new Printable()
		{
			public int print(Graphics pg, PageFormat pf, int pageNum)
			{
				if (pageNum > 0)
					return Printable.NO_SUCH_PAGE;
				Graphics2D g2 = (Graphics2D) pg;
				g2.translate(pf.getImageableX(), pf.getImageableY());
				historyPanel.paint(g2);
				return Printable.PAGE_EXISTS;
			}
		});
		if (!pj.printDialog())
			return;
		Container topLevel = historyPanel.getTopLevelAncestor();
		boolean visibility = topLevel.isVisible();
		topLevel.setVisible(true);
		try
		{
			pj.print();
		} catch (PrinterException ex)
		{
			if (!(ex instanceof PrinterAbortException))
			{
				ex.printStackTrace();
				System.exit(-1);
			}
		} finally
		{
			topLevel.setVisible(visibility);
		}
	}

	/**
	 * A method for recording the current history to a file named fragileHistory.txt.
	 * 
	 * @param str the string to write to the file
	 * @throws IOException if the writer fails
	 */
	public static void recording(String str) throws IOException
	{
		String fileName = JOptionPane.showInputDialog(historyPanel,
				"Please enter file name to save under", "Save as", JOptionPane.QUESTION_MESSAGE);
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		// str is built every time the equals button is pressed, a
		writer.write(str);
		writer.close();
	}

	/**
	 * Gets current history listener.
	 * 
	 * @return listener variable
	 */
	public static HistoryListener getHListener()
	{
		return listener;
	}
}
