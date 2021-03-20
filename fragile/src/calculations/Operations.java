package calculations;

/**
 * Utility class for operating on fractions.
 * 
 * @author Jeremy and Joe McCuen.
 */
public final class Operations
{
	/**
	 * Adds the two given fractions.
	 * 
	 * @param f1 fraction 1
	 * @param f2 fraction 2
	 * @return result in mixed form
	 */
	public static Fraction add(Fraction f1, Fraction f2)
	{
		// Convert to improper if not already
		Fraction improperf1 = toImproper(f1);
		Fraction improperf2 = toImproper(f2);

		int commonDenom = improperf1.getDenom() * improperf2.getDenom();
		Fraction commonf1 = new Fraction(0, improperf2.getDenom() * improperf1.getNumer(),
				commonDenom);
		Fraction commonf2 = new Fraction(0, improperf1.getDenom() * improperf2.getNumer(),
				commonDenom);
		return toMixed(new Fraction(0, commonf1.getNumer() + commonf2.getNumer(), commonDenom));
	}

	/**
	 * Subtracts the two given fractions.
	 * 
	 * @param f1 fraction 1
	 * @param f2 fraction 2
	 * @return result in mixed form
	 */
	public static Fraction subtract(Fraction f1, Fraction f2)
	{
		// Convert to improper if not already
		Fraction improperf1 = toImproper(f1);
		Fraction improperf2 = toImproper(f2);

		int commonDenom = improperf1.getDenom() * improperf2.getDenom();
		Fraction commonf1 = new Fraction(0, improperf2.getDenom() * improperf1.getNumer(),
				commonDenom);
		Fraction commonf2 = new Fraction(0, improperf1.getDenom() * improperf2.getNumer(),
				commonDenom);
		return toMixed(new Fraction(0, commonf1.getNumer() - commonf2.getNumer(), commonDenom));
	}

	/**
	 * Multiplies the two given fractions.
	 * 
	 * @param f1 fraction 1
	 * @param f2 fraction 2
	 * @return result in mixed form
	 */
	public static Fraction multiply(Fraction f1, Fraction f2)
	{
		// Check if either number is 0
		if ((f1.getWhole() == 0 && f1.getNumer() == 0 && f1.getDenom() == 1)
				|| (f2.getWhole() == 0 && f2.getNumer() == 0 && f2.getDenom() == 1))
		{
			return new Fraction(0, 0, 1);
		}

		// Convert to improper if not already
		Fraction improperf1 = toImproper(f1);
		Fraction improperf2 = toImproper(f2);

		int numer = improperf1.getNumer() * improperf2.getNumer();
		int denom = improperf1.getDenom() * improperf2.getDenom();
		return toMixed(new Fraction(0, numer, denom));
	}

	/**
	 * Divides the two given fractions.
	 * 
	 * @param f1 fraction 1
	 * @param f2 fraction 2
	 * @return result in mixed form
	 */
	public static Fraction divide(Fraction f1, Fraction f2)
	{
		// Check if either number is 0
		if ((f1.getWhole() != 0 && f1.getNumer() != 0 && f1.getDenom() != 1)
				&& (f2.getWhole() == 0 && f2.getNumer() == 0 && f2.getDenom() == 1))
		{
			return new Fraction(0, 0, 1);
		}
		// Convert to improper if not already
		Fraction improperf1 = toImproper(f1);
		Fraction inversef2 = toImproper(inverse(f2));

		return multiply(improperf1, inversef2);
	}

	/**
	 * Gets the inverse of a given fraction.
	 * 
	 * @param f given fraction
	 * @return inverse in mixed form
	 */
	public static Fraction inverse(Fraction f)
	{
		Fraction improperf = toImproper(f);
		int numer = improperf.getDenom();
		int denom = improperf.getNumer();
		if (denom < 0)
		{
			denom = Math.abs(denom);
			numer = numer * -1;
		}
		return toMixed(new Fraction(0, numer, denom));

	}

	/**
	 * Gets the mediant of two fractions.
	 * 
	 * @param f1 left operand
	 * @param f2 right operand
	 * @return resulting fraction in mixed form
	 */
	public static Fraction mediant(Fraction f1, Fraction f2)
	{
		// Convert to improper form
		Fraction improperf1 = toImproper(f1);
		Fraction improperf2 = toImproper(f2);
		// a/b mediant c/d = a+c/b+d
		int numer = improperf1.getNumer() + improperf2.getNumer();
		int denom = improperf1.getDenom() + improperf2.getDenom();

		return toMixed(new Fraction(0, numer, denom));
	}

	/**
	 * Takes the integer power of a fraction.
	 * 
	 * @param f fraction given
	 * @param exp exponent
	 * @return resulting fraction in mixed form
	 */
	public static Fraction pow(Fraction f, int exp)
	{
		if (exp == 0)
			return new Fraction(1, 0, 1);
		Fraction improper = toImproper(f);
		if (exp < 0)
			improper = inverse(improper);
		if (exp == -1)
			return improper;
		Fraction tempFrac = new Fraction(improper.getWhole(), improper.getNumer(),
				improper.getDenom());
		for (int i = 1; i < Math.abs(exp); i++)
		{
			tempFrac = toImproper(multiply(tempFrac, improper));
		}

		return toMixed(tempFrac);
	}

	/**
	 * Method to get the next farey number in a sequence given.
	 * 
	 * @param f1 1st previous number
	 * @param f2 2nd previous number
	 * @param order The farey's sequence order.
	 * @return next farey number
	 */
	public static Fraction farey(Fraction f1, Fraction f2, int order)
	{
		int a = f1.getNumer();
		int b = f1.getDenom();
		int c = f2.getNumer();
		int d = f2.getDenom();
		int resultNumer = (int) (Math.floor((order + b) / d)) * c - a;
		int resultDenom = (int) (Math.floor((order + b) / d)) * d - b;
		return new Fraction(0, resultNumer, resultDenom);
	}

	/**
	 * Simplify the current mixed fraction to simplest form.
	 * 
	 * @param f1 is the fraction to be simplified.
	 * @return f1, with the fraction in simplest form.
	 */
	public static Fraction simplify(Fraction f1)
	{
		Fraction temp = f1;
		if (temp.getNumer() > temp.getDenom())
		{
			temp = toImproper(temp);
			temp = toMixed(temp);
		}

		int numer = temp.getNumer();
		int denom = temp.getDenom();
		int lcd = largestCommonDenom(numer, denom);
		return new Fraction(temp.getWhole(), numer / lcd, denom / lcd);
	}

	/**
	 * Finds the largest common denominator of 2 integers.
	 * 
	 * @param a integer 1.
	 * @param b integer 2.
	 * @return the largest common denominator.
	 */
	private static int largestCommonDenom(int a, int b)
	{
		return b == 0 ? a : largestCommonDenom(b, a % b);
	}

	/**
	 * Converts fraction to improper form.
	 * 
	 * @param f fraction given
	 * @return fraction in improper form
	 */
	public static Fraction toImproper(Fraction f)
	{
		int numer;
		if (f.getWhole() >= 0)
		{
			numer = (f.getWhole() * f.getDenom()) + f.getNumer();
		} else
		{
			numer = ((f.getWhole() * -1) * f.getDenom()) + f.getNumer();
			numer = numer * -1;
		}
		int denom = f.getDenom();
		int whole = 0;
		return new Fraction(whole, numer, denom);
	}

	/**
	 * Converts fraction to mixed form.
	 * 
	 * @param f fraction given
	 * @return fraction in mixed form
	 */
	public static Fraction toMixed(Fraction f)
	{
		int whole = f.getNumer() / f.getDenom();
		int numer = whole != 0 ? Math.abs(f.getNumer() % f.getDenom())
				: f.getNumer() % f.getDenom();
		int denom = f.getDenom();
		return new Fraction(whole, numer, denom);
	}

	/**
	 * Returns the least common multiple of 2 numbers.
	 * 
	 * @param x is int a.
	 * @param y is int b.
	 * @return the least common multiple.
	 */
	public static int leastCommonMult(int x, int y)
	{
		return (x * y) / largestCommonDenom(x, y);
	}

}
