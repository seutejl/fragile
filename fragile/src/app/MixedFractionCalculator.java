package app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import gui.CalculatorWindow;

/**
 * 
 * Main class to run calculator.
 *
 */
public class MixedFractionCalculator implements Runnable
{

	/**
	 * Main method to run the application.
	 * 
	 * @param args are command line arguments.
	 */
	public static void main(String[] args)
	{
		try
		{
			SwingUtilities.invokeAndWait(new MixedFractionCalculator());
		} catch (InterruptedException | InvocationTargetException e)
		{
			System.out.println("Unable to start the GUI.");
		}

	}

	/**
	 * Runner method for the gui.
	 */
	public void run()
	{
		CalculatorWindow window = new CalculatorWindow();
		window.setVisible(true);
	}

}
