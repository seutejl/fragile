package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

/**
 * PieChart is a class that generates a Pie Chart object.
 * 
 * @author Will
 *
 */
public class PieChart extends JPanel
{

  private static final long serialVersionUID = 1L;
  private int num1;
  private int denom1;
  private int num2;
  private int denom2;
  private int num3;
  private int denom3;
  private String op;

  /**
   * Constructor for a pie Chart.
   * 
   * @param num1
   *          Numerator of the right operand
   * @param denom1
   *          Denominator of the right operand
   * @param num2
   *          Numerator of the left operand
   * @param denom2
   *          Denominator of the left operand
   * @param num3
   *          Numerator of the result
   * @param denom3
   *          Denominator of the left operand
   * @param op
   *          is the operation.
   */
  public PieChart(int num1, int denom1, int num2, int denom2, int num3, int denom3, String op)
  {
    super();
    this.num1 = num1;
    this.denom1 = denom1;
    this.num2 = num2;
    this.denom2 = denom2;
    this.num3 = num3;
    this.denom3 = denom3;
    this.op = op;
  }

  /**
   * Paints three pie charts as specified by the construcotr onto the screen.
   * 
   * @param g
   *          Graphics object to display the pie chart.
   */
  public void paintComponent(Graphics g)
  {
    this.setLayout(null);
    Graphics2D g2 = (Graphics2D) g;
    Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
    arc.setFrame(50, 10, 150, 150);
    arc.setAngleStart(0);
    int angle = (int) (((1.0 * this.num1) / this.denom1) * 360);
    arc.setAngleExtent(angle);
    g2.setColor(Color.gray);
    g2.draw(arc);
    g2.setColor(Color.red);
    g2.fill(arc);

    Arc2D.Float arc2 = new Arc2D.Float(Arc2D.PIE);
    arc2.setFrame(300, 10, 150, 150);
    arc2.setAngleStart(0);
    int angle2 = (int) (((1.0 * this.num2) / this.denom2) * 360);
    arc2.setAngleExtent(angle2);
    g2.setColor(Color.gray);
    g2.draw(arc2);
    g2.setColor(Color.blue);
    g2.fill(arc2);

    Arc2D.Float arc3 = new Arc2D.Float(Arc2D.PIE);
    arc3.setFrame(600, 10, 150, 150);
    arc3.setAngleStart(0);
    int angle3 = (int) (((1.0 * this.num3) / this.denom3) * 360);
    arc3.setAngleExtent(angle3);
    g2.setColor(Color.gray);
    g2.draw(arc3);
    g2.setColor(Color.green);
    g2.fill(arc3);

    g2.setFont(new Font("Verdana", 10, 50));
    g2.setColor(Color.black);
    g2.drawString(op, 235, 90);

    g2.setColor(Color.black);
    g2.drawString("=", 525, 90);

  }

}
