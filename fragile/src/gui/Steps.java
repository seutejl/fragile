package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import calculations.Fraction;
import calculations.Operations;

/**
 * Steps displays the intermediate steps of each operation.
 * 
 * @author Joe McCuen and Tinh Tran
 *
 */
public class Steps
{
  private static JPanel iPanel;
  private static int iCount;

  /**
   * Initialized the intermediateSteps panel.
   * 
   * @param panel
   *          Panel passed in from main app window.
   */
  public static void init(JPanel panel)
  {
    iPanel = panel;
  }

  /**
   * Method to choose which one of intermediate steps methods to run based on what operation was
   * passed in.
   * 
   * @param left
   *          Left fraction.
   * @param op
   *          Operation.
   * @param right
   *          Right fraction.
   * @param result
   *          The result of the whole expression.
   * @param style
   *          The style to be printed in.
   * @param fareysOrder
   *          Order of Farey's sequence. Used for printing Fareys sequence.
   */
  public static void eval(Fraction left, String op, Fraction right, Fraction result, int style,
      Integer fareysOrder)
  {
    reset();
    if (op.equals("+") || op.equals("-"))
    {
      addOrSubSteps(left, op, right, result, style);
    }
    else if (op.equals("F"))
    {
      farey(left, op, right, result, style, fareysOrder);
    }
    else if (op.equals("x") || op.equals("\u00F7"))
    {
      mulOrDivSteps(left, op, right, style, result);
    }
    else if (op.equals("&"))
    {
      mediant(left, op, right, result, style);
    }
    else if (op.equals("^"))
    {
      powSteps(left, right.getWhole(), style);
    }
  }

  /**
   * Clears everything in history panel after user pressed reset button.
   */
  public static void reset()
  {
    iPanel.removeAll();
    iCount = 0;
    ((GridLayout) iPanel.getLayout()).setRows(10);
    iPanel.repaint();
  }

  /**
   * Toggled the visibility of passed in window.
   * 
   * @param window
   *          The window to toggle.
   */
  public static void toggle(JWindow window)
  {
    if (window.isVisible())
      window.setVisible(false);
    else
      window.setVisible(true);
  }

  /**
   * Shows the intermediate steps of addition or subtraction.
   * 
   * @param left
   *          is the left operand.
   * @param op
   *          is the operation.
   * @param right
   *          is the right operand.
   * @param style
   *          is the style
   * @param result
   *          is the result used.
   */
  private static void print(Fraction left, String op, Fraction right, Fraction result, int style)
  {
    JPanel temp = new JPanel();
    Fraction res = result;
    switch (op)
    {
      case "+":
        res = Operations.add(left, right);
        break;
      case "-":
        res = Operations.subtract(left, right);
        break;
      default:
        break;
    }
    for (int i = 0; i < 8; i++)
    {
      JLabel temp2 = new JLabel("" + i);
      temp2.setText("");
      temp.add(temp2);
    }
    iPanel.add(temp);
    JLabel temp2 = (JLabel) temp.getComponent(3);
    if (style == 1)
    {
      ((JLabel) temp.getComponent(0)).setText("" + left.getWhole());
      ((JLabel) temp.getComponent(1)).setText("" + left.toString(style));
    }
    else
    {
      temp2.setText(temp2.getText() + left.toString(style));
    }

    if (style == 1)
    {
      ((JLabel) temp.getComponent(2)).setText("" + op);
      ((JLabel) temp.getComponent(3)).setText("" + right.getWhole());
      ((JLabel) temp.getComponent(4)).setText("" + right.toString(style));
      ((JLabel) temp.getComponent(5)).setText(" = ");
      ((JLabel) temp.getComponent(6)).setText("" + res.getWhole());
      ((JLabel) temp.getComponent(7)).setText("" + res.toString(style));
    }
    else
    {
      temp2.setText(temp2.getText() + " " + op + " ");
      temp2.setText(temp2.getText() + right.toString(style));
      temp2.setText(temp2.getText() + " = ");
      temp2.setText(temp2.getText() + res.toString(style));
    }
    iCount++;
    if (iCount > 10)
    {
      ((GridLayout) iPanel.getLayout()).setRows(iCount);
    }

  }

  /**
   * Basic printing text helper method.
   * 
   * @param text
   *          is the text to be printed.
   */
  private static void printText(String text)
  {
    JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel label = new JLabel(text);
    label.setAlignmentX(JLabel.LEFT);
    temp.add(new JLabel(text));
    iPanel.add(temp);
    iCount++;
    if (iCount > 10)
    {
      ((GridLayout) iPanel.getLayout()).setRows(iCount);
    }
  }

  /**
   * Helper method, to print bar style.
   * 
   * @param panel
   *          is the panel.
   * @param fraction
   *          is the fraction to print.
   * @param text
   *          is the desired text.
   * @param done
   *          if this is the last panel.
   */
  private static void printPanel(JPanel panel, Fraction fraction, String text, boolean done)
  {
    JLabel label = null;
    if (fraction != null)
      label = new JLabel(fraction.toString(1));
    else
      label = new JLabel(text);
    label.setAlignmentX(JLabel.LEFT);
    panel.add(label);
    if (!done)
      return;
    iPanel.add(panel);
    iCount++;
    if (iCount > 10)
      ((GridLayout) iPanel.getLayout()).setRows(iCount);
  }

  /**
   * Shows the intermediate steps of addition.
   * 
   * @param left
   *          is the left operand.
   * @param op
   *          is the operation.
   * @param right
   *          is the
   * @param style
   *          is the style used.
   * @param result
   *          is the result used.
   */
  private static void addOrSubSteps(Fraction left, String op, Fraction right, Fraction result,
      int style)
  {
    String opVerbose = "";
    if (op.equals("+"))
      opVerbose = "Add";
    else
      opVerbose = "Subtract";
    // For whole numbers only
    if (left.getNumer() == 0 && right.getNumer() == 0)
    {
      print(left, op, right, result, style);
      printText("\nSTEP 1: " + opVerbose + " the two whole numbers:" + "\n" + "\n");
      printText(left.getWhole() + " " + op + " " + right.getWhole() + " = " + result.getWhole());
      printText("\n\n" + "Final Answer: " + "\n" + "\n");
      printText(String.valueOf(result.getWhole()));
      return;
    }
    String leftFrac = left.getNumer() + "/" + left.getDenom();
    String rightFrac = right.getNumer() + "/" + right.getDenom();
    int lcd = Operations.leastCommonMult(left.getDenom(), right.getDenom());
    int leftMult = lcd / left.getDenom();
    int rightMult = lcd / right.getDenom();
    int leftNumer = left.getNumer() * leftMult;
    int rightNumer = right.getNumer() * rightMult;
    print(left, op, right, result, style);
    printText("<HTML><U>STEP 1: Find The Smallest Common Denominator:<HTML><U>");
    String leftStep1 = "" + leftNumer + "/" + lcd;
    String rightStep1 = "" + rightNumer + "/" + lcd;
    if (style == 2)
    {
      leftFrac = Fraction.superscript(left.getNumer()) + "/" + Fraction.subscript(left.getDenom());
      rightFrac = Fraction.superscript(right.getNumer()) + "/"
          + Fraction.subscript(right.getDenom());
      leftStep1 = Fraction.superscript(leftNumer) + "/" + Fraction.subscript(lcd);
      rightStep1 = Fraction.superscript(rightNumer) + "/" + Fraction.subscript(lcd);
    }
    if (left.getNumer() != 0)
      if (style == 1)
      {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel, left, "", false);
        printPanel(panel, null, " X " + String.valueOf(leftMult) + " = ", false);
        printPanel(panel, new Fraction(0, leftNumer, lcd), "", true);
      }
      else
        printText(leftFrac + " X " + String.valueOf(leftMult) + " = " + leftStep1);
    if (right.getNumer() != 0)
      if (style == 1)
      {
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel2, right, "", false);
        printPanel(panel2, null, " X " + String.valueOf(rightMult) + " = ", false);
        printPanel(panel2, new Fraction(0, rightNumer, lcd), "", true);
      }
      else
        printText(rightFrac + " X " + String.valueOf(rightMult) + " = " + rightStep1);

    printText("<HTML><U>So now we have:<HTML><U>");
    if (style == 1)
    {
      JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      printPanel(panel3, null, "" + left.getWhole(), false);
      printPanel(panel3, new Fraction(0, leftNumer, lcd), "", false);
      printPanel(panel3, null, " " + op + " " + right.getWhole(), false);
      printPanel(panel3, new Fraction(0, rightNumer, lcd), "", true);
    }
    else
      printText(left.getWhole() + " " + leftStep1 + " " + op + " " + right.getWhole() + " "
          + rightStep1 + "\n");

    printText("<HTML><U>STEP 2: " + opVerbose + " the left's whole number with the<HTML><U>");
    printText("<HTML><U>right's whole number<HTML><U>");
    int step2Result = 0;
    if (op.equals("+"))
      step2Result = left.getWhole() + right.getWhole();
    else
      step2Result = left.getWhole() - right.getWhole();
    printText(left.getWhole() + " " + op + " " + right.getWhole() + " = " + step2Result);

    printText("<HTML><U>STEP 3: " + opVerbose + " the left numerator with the right<HTML><U>");
    printText("<HTML><U>numerator, denominator stays the same<HTML><U>");
    int step3Result = 0;
    if (op.equals("+"))
      step3Result = leftNumer + rightNumer;
    else
      step3Result = leftNumer - rightNumer;

    printText(leftNumer + " " + op + " " + rightNumer + " = " + step3Result);
    printText("<HTML><U>Final Answer: <HTML><U>");
    if (style == 1)
    {
      JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      printPanel(panel4, null, "" + step2Result, false);
      printPanel(panel4, new Fraction(0, step3Result, lcd), "", false);
      printPanel(panel4, null, ", reduced as needed to = " + result.getWhole(), false);
      printPanel(panel4, result, "", true);
    }
    else
      printText(new Fraction(step2Result, step3Result, lcd).toString(style)
          + ", reduced as needed to = " + result.toString(style));
  }

  /**
   * Show the intermediate steps of power.
   * 
   * @param left
   *          is the left operand.
   * @param exp
   *          is the exponent.
   * @param style
   *          is the style.
   */
  private static void powSteps(Fraction left, int exp, int style)
  {
    Fraction result = Operations.pow(left, exp);
    int tempWhole = left.getWhole();
    Fraction tempFrac = left;
    boolean ret = false;
    if (left.getWhole() == 0 && left.getNumer() == 0)
    {
      printText("<HTML><U>0 to any power always equals 0.<HTML><U>");
      printText("<HTML>Final Answer: 0 <HTML>");
      ret = true;
    }
    else if (left.getWhole() == 1 && left.getNumer() == 0)
    {
      printText("<HTML><U>1 to any power always equals 1.<HTML><U>");
      printText("<HTML>Final Answer: 1 <HTML>");
      ret = true;
    }
    else if (exp == 0)
    {
      printText("<HTML><U>Anything to the power of 0 is always 1<HTML><U>");
      printText("<HTML>Final Answer: 1 <HTML>");
      ret = true;
    }
    if (ret)
    {
      return;
    }
    else if (exp < 0)
    {
      printText("<HTML><U>When you have a negative power, you need to <HTML><U>");
      printText("<HTML><U>invert the fraction first<HTML><U>");
      Fraction lef = Operations.inverse(left);
      if (style == 2)
      {
        printText(tempFrac.getWhole() + " " + Fraction.superscript(tempFrac.getNumer()) + "/"
            + Fraction.subscript(tempFrac.getDenom()) + " = " + lef.getWhole() + " "
            + Fraction.superscript(lef.getNumer()) + "/" + Fraction.subscript(lef.getDenom()));
      }
      else if (style == 1)
      {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel, null, "" + tempFrac.getWhole(), false);
        printPanel(panel, tempFrac, "", true);
        printPanel(panel, null, " = " + lef.getWhole(), false);
        printPanel(panel, lef, "", true);
      }
      else
      {
        printText(tempFrac.toString(3) + " = " + lef.toString(3));
      }
      tempFrac = lef;
      tempWhole = lef.getWhole();
    }
    if (left.getNumer() == 0)
    {
      printText("<HTML><U>Multiply " + left.getWhole() + " by itself " + (Math.abs(exp) - 1)
          + " time(s) <HTML><U>");
      int i = 1;
      while (i < Math.abs(exp))
      {
        printText(tempWhole + " X " + left.getWhole() + " = " + (tempWhole * left.getWhole()));
        tempWhole *= left.getWhole();
        i++;
      }
      printText("Final Answer: " + result.getWhole());
    }
    else
    {
      printText("<HTML><U>Multiply your fraction by itself " + (Math.abs(exp) - 1)
          + " time(s) <HTML><U>");
      int i = 1;
      Fraction tempStr;
      while (i < Math.abs(exp))
      {
        if (style == 2)
        {
          tempStr = Operations.multiply(tempFrac, left);
          printText(tempFrac.getWhole() + " " + Fraction.superscript(tempFrac.getNumer()) + "/"
              + Fraction.subscript(tempFrac.getDenom()) + " X " + left.getWhole() + " "
              + Fraction.superscript(left.getNumer()) + "/" + Fraction.subscript(left.getDenom())
              + " = " + tempStr.getWhole() + " " + Fraction.superscript(tempStr.getNumer()) + "/"
              + Fraction.subscript(tempStr.getDenom()));
          tempFrac = Operations.multiply(tempFrac, left);
        }
        else if (style == 1)
        {
          JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
          printPanel(panel, null, "" + tempFrac.getWhole(), false);
          printPanel(panel, tempFrac, "", true);
          printPanel(panel, null, " X " + left.getWhole(), false);
          printPanel(panel, left, "", true);
          tempFrac = Operations.multiply(tempFrac, left);
          printPanel(panel, null, " = " + tempFrac.getWhole(), false);
          printPanel(panel, tempFrac, "", true);
        }
        else
        {
          tempStr = Operations.multiply(tempFrac, left);
          printText(tempFrac.toString(3) + " X " + left.toString(3) + " = " + tempStr.toString(3));
          tempFrac = Operations.multiply(tempFrac, left);
        }
        i++;
      }
      printText("<HTML><U>Final Answer: <HTML><U>");
      if (style == 2)
      {
        printText(result.getWhole() + " " + Fraction.superscript(result.getNumer()) + "/"
            + Fraction.subscript(result.getDenom()));
      }
      else if (style == 1)
      {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel, null, "" + result.getWhole(), false);
        printPanel(panel, result, "", true);
      }
      else
      {
        printText(result.toString(3));
      }
    }
  }

  /**
   * Show the intermediate steps of multiplication and division.
   * 
   * @param left
   *          is the left fraction.
   * @param op
   *          is the operation.
   * @param right
   *          is the right fraction.
   * @param result
   *          is the result of the operation.
   * @param style is the style.
   */
  private static void mulOrDivSteps(Fraction left, String op, Fraction right, int style,
      Fraction result)
  {
    String opVerbose = "";
    boolean ret = false;
    if (op.equals("x"))
      opVerbose = "Multiply";
    else
      opVerbose = "Divide";
    // Check for undefined
    if (opVerbose.equals("Divide") && right.getWhole() == 0 && right.getNumer() == 0
        && right.getDenom() == 1)
    {
      printText("<HTML><U>Anything divided by zero is undefined.<HTML><U>");
      printText("<HTML>Final Answer: Undefined Value<HTML>");
      printText("<HTML> <HTML>");
      printText("<HTML>Please reset the calculator with the (R) <HTML>");
      printText("<HTML>button to clear the undefined value.<HTML>");
      ret = true;
    }
    else if (opVerbose.equals("Divide") && left.getNumer() == 0 && right.getNumer() == 0)
    {
      if (left.getWhole() == 0 || right.getWhole() == 0)
      {
        printText("<HTML><U>Zero divided by anything is zero.<HTML><U>");
        printText("<HTML>Final Answer: 0<HTML>");
        ret = true;
      }
    }
    else if (opVerbose.equals("Multiply") && left.getNumer() == 0 && right.getNumer() == 0)
    {
      if (left.getWhole() == 0 || right.getWhole() == 0)
      {
        printText("<HTML><U>Zero multiplied by anything, or anything multiplied<HTML><U>");
        printText("<HTML><U>by zero is zero.<HTML><U>");
        printText("<HTML>Final Answer: 0<HTML>");
        ret = true;
      }
    }
    if (ret)
    {
      return;
    }

    // Divide whole numbers only.
    if (opVerbose.equals("Divide") && left.getNumer() == 0 && right.getNumer() == 0)
    {
      printText("<HTML><U>STEP 1: Place the left number over the right:<HTML><U>");
      if (style == 1)
      {
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel1, new Fraction(0, left.getWhole(), right.getWhole()), "", true);
      }
      else if (style == 2)
        printText(
            Fraction.superscript(left.getWhole()) + "/" + Fraction.subscript(right.getWhole()));
      else if (style == 3)
        printText(left.getWhole() + "/" + right.getWhole());
      printText("<HTML><U>Final Result: <HTML><U>");
      if (style == 1)
      {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel, null, "" + result.getWhole(), false);
        printPanel(panel, result, "", true);
      }
      else
        printText(result.toString(style));
      Fraction res = Operations.simplify(result);
      String resStr;
      if (res.getWhole() == 0)
        resStr = res.toString(style).substring(2);
      else
        resStr = res.toString(style);
      if (style == 1)
      {
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel2, null, "Reduced as needed: " + res.getWhole(), false);
        printPanel(panel2, res, "", true);
      }
      else
        printText("Reduced as needed: " + resStr);
    }
    // Multiply whole numbers only.
    else if (opVerbose.equals("Multiply") && left.getNumer() == 0 && right.getNumer() == 0)
    {
      printText("<HTML><U>STEP 1: Multiply the two operands:<HTML><U>");
      printText(left.getWhole() + " X " + right.getWhole());
      printText("<HTML><U>Final Result: <HTML><U>");
      printText("" + result.getWhole());
    }
    else
    {

      int lcd = Operations.leastCommonMult(left.getDenom(), right.getDenom());
      String leftFrac = left.getNumer() + "/" + left.getDenom();
      String rightFrac = right.getNumer() + "/" + right.getDenom();
      int leftMult = lcd / left.getDenom();
      int rightMult = lcd / right.getDenom();
      int leftNumer = left.getNumer() * leftMult;
      int rightNumer = right.getNumer() * rightMult;
      print(left, op, right, result, style);
      printText("<HTML><U>STEP 1: Convert each operand to improper form:<HTML><U>");
      Fraction impleft = Operations.toImproper(left);
      Fraction impright = Operations.toImproper(right);
      String leftStep1 = impleft.getNumer() + "/" + impleft.getDenom();
      String rightStep1 = impright.getNumer() + "/" + impright.getDenom();
      String rightStep1Reverse = impright.getDenom() + "/" + impright.getNumer();

      if (style == 2)
      {
        leftFrac = Fraction.superscript(left.getNumer()) + "/"
            + Fraction.subscript(left.getDenom());
        rightFrac = Fraction.superscript(right.getNumer()) + "/"
            + Fraction.subscript(right.getDenom());
        leftStep1 = Fraction.superscript(impleft.getNumer()) + "/"
            + Fraction.subscript(impleft.getDenom());
        rightStep1 = Fraction.superscript(impright.getNumer()) + "/"
            + Fraction.subscript(impleft.getDenom());
        rightStep1Reverse = Fraction.superscript(impright.getDenom()) + "/"
            + Fraction.subscript(impleft.getNumer());
      }
      if (left.getNumer() != 0)
        if (style == 1)
        {
          JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
          printPanel(panel, null, String.valueOf(left.getWhole()), false);
          printPanel(panel, left, "", false);
          printPanel(panel, null, " = ", false);
          printPanel(panel, impleft, "", true);
        }
        else
          printText(left.getWhole() + " " + leftFrac + " = " + leftStep1);
      if (right.getNumer() != 0)
        if (style == 1)
        {
          JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
          printPanel(panel2, null, String.valueOf(right.getWhole()), false);
          printPanel(panel2, right, "", false);
          printPanel(panel2, null, " = ", false);
          printPanel(panel2, impright, "", true);
        }
        else
          printText(right.getWhole() + " " + rightFrac + " = " + rightStep1);

      printText("<HTML><U>So now we have:<HTML><U>");
      if (style == 1)
      {
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printPanel(panel3, impleft, "", false);
        printPanel(panel3, null, " " + op + " ", false);
        printPanel(panel3, impright, "", true);
      }
      else
        printText(leftStep1 + " " + op + " " + rightStep1 + "\n");
      if (opVerbose.equals("Divide"))
      {
        printText("<HTML><U>STEP 2: Change the operation to multiplication<HTML><U>");
        printText("<HTML><U>and flip the right fraction<HTML><U>");
        if (style == 1)
        {
          JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
          printPanel(panel3, impleft, "", false);
          printPanel(panel3, null, " X ", false);
          printPanel(panel3, new Fraction(0, impright.getDenom(), impright.getNumer()), "", true);
        }
        else
        {
          printText(leftStep1 + " X " + rightStep1Reverse + "\n");
        }
        printText("<HTML><U>STEP 3: Multiply the numerator and denominator of<HTML><U>");
        printText("<HTML><U> each fraction<HTML><U>");
        int divStep3top = impleft.getNumer() * impright.getDenom();
        int divStep3bottom = impleft.getDenom() * impright.getNumer();
        printText(impleft.getNumer() + " X " + impright.getDenom() + " = " + divStep3top);
        printText(impleft.getDenom() + " X " + impright.getDenom() + " = " + divStep3bottom);

        printText("<HTML><U>Final Answer: <HTML><U>");
        if (style == 1)
        {
          JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
          printPanel(panel4, new Fraction(0, divStep3top, divStep3bottom), "", false);
          printPanel(panel4, null, ", reduced as needed to = " + result.getWhole(), false);
          printPanel(panel4, result, "", true);
        }
        else if (style == 2)
        {
          printText(Fraction.subscript(divStep3top) + "/" + Fraction.subscript(divStep3bottom) + " "
              + ", reduced as needed to = " + result.toString(style));
        }
        else
          printText(divStep3top + "/" + divStep3bottom + " " + ", reduced as needed to = "
              + result.toString(style));
      }
      else
      {
        printText("<HTML><U>STEP 2: " + opVerbose + " the left's numerator with the<HTML><U>");
        printText("<HTML><U>right's numerator<HTML><U>");
        int step2 = impleft.getNumer() * impright.getNumer();
        printText(impleft.getNumer() + " " + op + " " + impright.getNumer() + " = " + step2);
        printText(
            "<HTML><U>STEP 3: " + opVerbose + " the left denominator with the right<HTML><U>");
        printText("<HTML><U>denominator<HTML><U>");
        int step3Result = 0;
        switch (op)
        {
          case "x":
            step3Result = impleft.getDenom() * impright.getDenom();
            break;
          case "\u00F7":
            step3Result = leftNumer / rightNumer;
            break;
          default:
            break;
        }
        printText(impleft.getDenom() + " " + op + " " + impright.getDenom() + " = " + step3Result);
        printText("<HTML><U>Final Answer: <HTML><U>");
        if (style == 1)
        {
          JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
          printPanel(panel4, new Fraction(0, step2, step3Result), "", false);
          printPanel(panel4, null, ", reduced as needed to = " + result.getWhole(), false);
          printPanel(panel4, result, "", true);
        }
        else if (style == 2)
        {
          printText(Fraction.subscript(step2) + "/" + Fraction.subscript(step3Result) + " "
              + ", reduced as needed to = " + result.toString(style));
        }
        else
          printText(step2 + "/" + step3Result + " " + ", reduced as needed to = "
              + result.toString(style));
      }
    }
  }

  /**
   * Farey computes a farey sequence.
   * 
   * @param left
   *          is the left operand.
   * @param op
   *          is the operation.
   * @param right
   *          is the right operand.
   * @param result
   *          is the result.
   * @param style
   *          is the style type.
   * @param fareysOrder
   *          is the farey order.
   */
  private static void farey(Fraction left, String op, Fraction right, Fraction result, int style,
      int fareysOrder)
  {
    print(left, op, right, result, style);
    int a = left.getNumer();
    int b = left.getDenom();
    int c = right.getNumer();
    int d = right.getDenom();
    printText("Given the two fractions: ");
    if (style == 1)
    {
      JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      printPanel(panel1, left, "", false);
      printPanel(panel1, null, " and ", false);
      printPanel(panel1, right, "", false);
      printPanel(panel1, null, ", and the Farey's sequence of " + fareysOrder, true);
    }
    else if (style == 2)
    {
      printText(
          "" + Fraction.superscript(left.getNumer()) + "/" + Fraction.subscript(left.getDenom()));
      printText(" and " + Fraction.superscript(right.getNumer()) + "/"
          + Fraction.subscript(right.getDenom()));
      printText(", and the Farey's sequence of " + fareysOrder);
    }
    else if (style == 3)
    {
      printText("" + left.getNumer() + "/" + left.getDenom());
      printText(" and " + right.getNumer() + "/" + right.getDenom());
      printText(", and the Farey's sequence of " + fareysOrder);
    }
    printText("\nFind the next fraction in the sequence.");
    printText("Let the order of the sequence be denoted as N");
    printText("Let the first fraction be denoted as: A / B");
    printText("Let the second fraction be denoted as: C / D");
    printText("Let the resulting fraction be denoted as: P / Q");
    printText("To get the next number in Farey's sequence, ");
    printText("simply follow this formula:\n");
    printText("P = The floor of  [ (N + B) / D ] * C - A\n");
    printText("P = The floor of  [ (" + fareysOrder + " + " + b + ") / " + d + " ] * " + c + " - "
        + a + " = " + result.getNumer());
    printText("Q = The floor of  [ (N + B) / D ] * D - B\n");
    printText("Q = The floor of  [ (" + fareysOrder + " + " + b + ") / " + d + " ] * " + d + " - "
        + b + " = " + result.getDenom());
    printText("Therefore, the next fraction in the Farey's");
    if (style == 1)
    {
      JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      printPanel(panel2, null, "sequence of " + fareysOrder + " is: ", false);
      printPanel(panel2, result, "", true);
    }
    else if (style == 2)
      printText("sequence of " + fareysOrder + " is: " + Fraction.superscript(result.getNumer())
          + "/" + Fraction.subscript(result.getDenom()));
    else if (style == 3)
      printText(
          "sequence of " + fareysOrder + " is: " + result.getNumer() + "/" + result.getDenom());
  }

  /**
   * Intermediate steps for mediant.
   * 
   * @param left
   *          is the left fraction.
   * @param op
   *          is the operation.
   * @param right
   *          is the right fraction.
   * @param result
   *          the result of the expression.
   * @param style
   *          the style to be printed in.
   */
  private static void mediant(Fraction left, String op, Fraction right, Fraction result, int style)
  {
    print(left, op, right, result, style);
    printText("STEP 1: Find the improper forms of each of the ");
    printText("two fractions using the following formula:");
    printText("The new numerator = ");
    printText("(denominator * whole number) + old numerator");
    printText("The denominator stays the same");
    printText("The whole number goes away");
    printText("");
    printText("After doing these we get:");
    Fraction improperLeft = Operations.toImproper(left);
    Fraction improperRight = Operations.toImproper(right);
    if (style == 1)
    {
      JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      printPanel(panel1, null, "Left operand changes from: " + left.getWhole(), false);
      printPanel(panel1, left, "", false);
      printPanel(panel1, null, ", to ", false);
      printPanel(panel1, improperLeft, "", true);

      JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      printPanel(panel2, null, "Right operand changes from: " + right.getWhole(), false);
      printPanel(panel2, right, "", false);
      printPanel(panel2, null, ", to ", false);
      printPanel(panel2, improperRight, "", true);
    }
    else
    {
      printText("Left operand changes from " + left.toString(style) + " to "
          + improperLeft.toString(style));
      printText("Right operand changes from " + right.toString(style) + " to "
          + improperRight.toString(style));
    }
    printText("");
    printText("Step 2:To get the mediant of these two fractions,");
    printText("we can simply follow this formula:\n");
    printText("The mediant numerator = ");
    printText("Left's numerator + Right's numerator = ");
    printText("" + improperLeft.getNumer() + " + " + improperRight.getNumer() + " = "
        + (improperLeft.getNumer() + improperRight.getNumer()));
    printText("The mediant denominator = ");
    printText("Left's denominator + Right's denominator = ");
    printText("" + improperLeft.getDenom() + " + " + improperRight.getDenom() + " = "
        + (improperLeft.getDenom() + improperRight.getDenom()));
    printText("");
    printText("Then finally we can choose to turn the result");
    printText("back into a mixed fraction or not:");
    int resNumer = improperLeft.getNumer() + improperRight.getNumer();
    int resDenom = improperLeft.getDenom() + improperRight.getDenom();
    Fraction res = new Fraction(0, resNumer, resDenom);
    if (style == 1)
    {
      JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      printPanel(panel3, res, "", false);
      printPanel(panel3, null, " = " + result.getWhole(), false);
      printPanel(panel3, result, "", true);
    }
    else
    {
      printText("" + res.toString(style) + " = " + result.toString(style));
    }
  }
}
