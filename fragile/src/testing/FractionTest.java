package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculations.Fraction;

/**
 * FractionTest is a testing class for the Fraction class.
 * 
 * @author Joe McCuen and Jeremy Seute
 *
 */
class FractionTest
{

	Fraction expected = new Fraction(2, 3, 4);

	/**
	 * Test the Fraction constructor.
	 */
	@Test
	void testConstructor()
	{
		assertEquals(expected.getWhole(), 2);
		assertEquals(expected.getNumer(), 3);
		assertEquals(expected.getDenom(), 4);
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParse()
	{
		// Test with expected String length
		String testString = "2 3/4";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// Test with the String length being 1
		testString = "2";
		// actual = Fraction.parseFraction(testString);
		// assertEquals(expected.getWhole(), actual.getWhole());
		// assertEquals(0, actual.getNumer());
		// assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseFullZeroDenom()
	{
		// Test with expected String length
		String testString = "2 2/0";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseFullMissingDenom()
	{
		// Test with expected String length
		String testString = "2 3/";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseFullInvalidDenom()
	{
		// Test with expected String length
		String testString = "2 3/invalid";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseSimpleZeroDenom()
	{
		// Test with expected String length
		String testString = "2/0";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testSimpleParseFullMissingDenom()
	{
		// Test with expected String length
		String testString = "3/";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseSimpleInvalidDenom()
	{
		// Test with expected String length
		String testString = "3/invalid";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseFullMissingNumer()
	{
		// Test with expected String length
		String testString = "2 /3";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseFullInvalidNumer()
	{
		// Test with expected String length
		String testString = "2 Invalid/3";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testSimpleParseFullMissingNumer()
	{
		// Test with expected String length
		String testString = "/3";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseSimpleInvalidNumer()
	{
		// Test with expected String length
		String testString = "invalid/";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseFullInvalidWhole()
	{
		// Test with expected String length
		String testString = "Invalid 1/3";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseSimpleInvalidWhole()
	{
		// Test with expected String length
		String testString = "invalid";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(0, actual.getWhole());
		assertEquals(0, actual.getNumer());
		assertEquals(1, actual.getDenom());
	}

	/**
	 * Test the parseFraction method.
	 */
	@Test
	void testParseSpacedFormatting()
	{
		// Test with expected String length
		String testString = "3 6 / 5";
		Fraction actual = Fraction.parseFraction(testString);
		assertEquals(3, actual.getWhole());
		assertEquals(6, actual.getNumer());
		assertEquals(5, actual.getDenom());
	}

	/**
	 * Test toString with bar style.
	 */
	@Test
	void testToStringBar()
	{
		String expect = "<html><u>3</u><br>4</html>";
		String actual = expected.toString(1);
		assertEquals(actual, expect);
	}

	/**
	 * Test toString with solace style.
	 */
	@Test
	void testToStringSolace()
	{
		String expect = "2 \u00B9/\u2084";
		Fraction test = new Fraction(2, 1, 4);
		String actual = test.toString(2);
		assertEquals(actual, expect);
	}

	/**
	 * Test toString with slash style.
	 */
	@Test
	void testToStringSlash()
	{
		String expect = "2 3/4";
		String actual = expected.toString(3);
		assertEquals(actual, expect);
	}

	/**
	 * Test toString method with invalid Fraction.
	 */
	@Test
	void testToStringInvalid()
	{
		Fraction invalid = new Fraction(1, 3, 0);
		String actual = invalid.toString(1);
		assertEquals("", actual);
	}

	/**
	 * Test toString method with invalid numer solice style.
	 */
	@Test
	void testToStringInvalidNumerSolice()
	{
		Fraction invalid = new Fraction(1, 0, 5);
		String actual = invalid.toString(2);
		assertEquals("1", actual);
	}

	/**
	 * Test toString method with invalid numer solice style.
	 */
	@Test
	void testToStringInvalidNumerSlash()
	{
		Fraction invalid = new Fraction(1, 0, 5);
		invalid.setWhole(2);
		String actual = invalid.toString(3);
		assertEquals("2", actual);
	}

	/**
	 * Test superscript to make sure it converts ints.
	 */
	@Test
	void testSuperscript()
	{
		String[] expectedA =
		{"\u2070", "\u00B9", "\u00B2", "\u2073", "\u2074", "\u2075", "\u2076", "\u2077", "\u2078",
				"\u2079"};
		String[] actual = new String[10];
		for (int i = 0; i < 10; i++)
		{
			actual[i] = Fraction.superscript(i);
		}
		assertEquals(expectedA[0], actual[0]);
		assertEquals(expectedA[1], actual[1]);
		assertEquals(expectedA[2], actual[2]);
		assertEquals(expectedA[4], actual[4]);
		assertEquals(expectedA[5], actual[5]);
		assertEquals(expectedA[6], actual[6]);
	}

	/**
	 * Test to make sure subscript correctly converts ints.
	 */
	@Test
	void testsubscript()
	{
		String[] expectedA =
		{"\u2080", "\u2081", "\u2082", "\u2083", "\u2084", "\u2085", "\u2086", "\u2087", "\u2088",
				"\u2089"};
		String[] actual = new String[10];
		for (int i = 0; i < 10; i++)
		{
			actual[i] = Fraction.subscript(i);
		}
		for (int i = 0; i < 10; i++)
			assertEquals(expectedA[i], actual[i]);

	}

}
