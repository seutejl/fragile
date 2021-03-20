package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import calculations.*;

/**
 * Listener for the Copy/Paste function of History.
 * 
 * @author Jeremy Seute, Tinh Tran
 *
 */
public class HistoryListener implements MouseListener, KeyListener, ActionListener
{
  private JLabel selected;
  private Fraction saved;
  private JPanel selectedPanel;

  /**
   * HistoryListener constructor.
   */
  public HistoryListener()
  {
  }

  /**
   * Handles selection for history.
   * 
   * @param e
   *          mouseevent
   */
  @Override
  public void mouseClicked(MouseEvent e)
  {
    if (selected != null)
    {
      selected.setBorder(null);
      if (selectedPanel != null)
        selectedPanel.setBorder(null);
    }
    try
    {
      selected = (JLabel) e.getSource();
      selected.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    catch (ClassCastException ex)
    {
      selectedPanel = ((JPanel) e.getSource());
      selected = new JLabel(selectedPanel.getName());
      selectedPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }
  }

  /**
   * Stub.
   * 
   * @param e
   *          mouseevent
   */
  @Override
  public void mouseEntered(MouseEvent e)
  {
  }

  /**
   * Stub.
   * 
   * @param e
   *          mouseevent
   */
  @Override
  public void mouseExited(MouseEvent e)
  {
  }

  /**
   * Stub.
   * 
   * @param e
   *          mouseevent
   */
  @Override
  public void mousePressed(MouseEvent e)
  {
  }

  /**
   * Stub.
   * 
   * @param e
   *          mouseevent
   */
  @Override
  public void mouseReleased(MouseEvent e)
  {
  }

  /**
   * Handles physical keyboard copy/pasting.
   * 
   * CTRL + C = copy; CTRL + V = paste.
   * 
   * @param e
   *          keyevent
   */
  @Override
  public void keyPressed(KeyEvent e)
  {
    if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.isControlDown())))
    {
      if (selected != null)
      {
        String frac = selected.getText();
        frac = replaceScript(frac);
        frac = frac.replaceAll("\\D+", " ");
        String[] fracArray = frac.split(" ");
        int whole = Integer.parseInt(fracArray[0]);
        int numer;
        int denom;
        if (fracArray.length == 1)
        {
          numer = 0;
          denom = 1;
        }
        else
        {
          numer = Integer.parseInt(fracArray[1]);
          denom = Integer.parseInt(fracArray[2]);
        }
        Fraction fraction = new Fraction(whole, numer, denom);
        saved = fraction;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(saved.toString(3));
        clipboard.setContents(strSel, null);
      }
    }
    if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.isControlDown())))
    {
      if (saved != null)
      {
        CalculatorWindow.setLeftOperand(saved);
        CalculatorWindow.setResultNull();
        // Physical keyboard pasting defaults to left operand.
      }
    }
  }

  /**
   * Stub.
   * 
   * @param e
   *          keyevent
   */
  @Override
  public void keyReleased(KeyEvent e)
  {
  }

  /**
   * Stub.
   * 
   * @param e
   *          keyevent
   */
  @Override
  public void keyTyped(KeyEvent e)
  {
  }

  /**
   * Performs button copy/pasting for history class.
   * 
   * @param e
   *          action event
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    JButton item = (JButton) e.getSource();
    switch (item.getText())
    {
      case "Copy":
        if (selected != null)
        {
          String frac = selected.getText();
          frac = replaceScript(frac);
          frac = frac.replaceAll("\\D+", " ");
          String[] fracArray = frac.split(" ");
          int whole = Integer.parseInt(fracArray[0]);
          int numer;
          int denom;
          if (fracArray.length == 1)
          {
            numer = 0;
            denom = 1;
          }
          else
          {
            numer = Integer.parseInt(fracArray[1]);
            denom = Integer.parseInt(fracArray[2]);
          }
          Fraction fraction = new Fraction(whole, numer, denom);
          saved = fraction;
          Toolkit toolkit = Toolkit.getDefaultToolkit();
          Clipboard clipboard = toolkit.getSystemClipboard();
          StringSelection strSel = new StringSelection(saved.toString(3));
          clipboard.setContents(strSel, null);
        }
        break;
      case "Paste Left":
        if (saved != null)
        {
          CalculatorWindow.setLeftOperand(saved);
          CalculatorWindow.setResultNull();
        }
        break;
      case "Paste Right":
        if (saved != null)
        {
          CalculatorWindow.setRightOperand(saved);
          CalculatorWindow.setResultNull();
        }
        break;
      default:
        break;
    }
  }

  /**
   * Replace Script is used to replace text on the history pane.
   * @param s is the desired string.
   * @return the new string to replace.
   */
  public String replaceScript(String s)
  {
    String frac = s;
    frac = frac.replaceAll("\u2070", "0");
    frac = frac.replaceAll("\u00B9", "1");
    frac = frac.replaceAll("\u00B2", "2");
    frac = frac.replaceAll("\u00B3", "3");
    frac = frac.replaceAll("\u2074", "4");
    frac = frac.replaceAll("\u2075", "5");
    frac = frac.replaceAll("\u2076", "6");
    frac = frac.replaceAll("\u2077", "7");
    frac = frac.replaceAll("\u2078", "8");
    frac = frac.replaceAll("\u2079", "9");

    frac = frac.replaceAll("\u2080", "0");
    frac = frac.replaceAll("\u2081", "1");
    frac = frac.replaceAll("\u2082", "2");
    frac = frac.replaceAll("\u2083", "3");
    frac = frac.replaceAll("\u2084", "4");
    frac = frac.replaceAll("\u2085", "5");
    frac = frac.replaceAll("\u2086", "6");
    frac = frac.replaceAll("\u2087", "7");
    frac = frac.replaceAll("\u2088", "8");
    frac = frac.replaceAll("\u2089", "9");

    return frac;
  }
}
