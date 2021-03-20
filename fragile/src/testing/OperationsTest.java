package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculations.Fraction;
import calculations.Operations;

/**
 * OperationTest is a test class for the Operations class.
 * 
 * @author Joe McCuen and Jeremy Seute
 *
 */
class OperationsTest
{
	static final Fraction ZERO = new Fraction(0, 0, 1); // 0
	static final Fraction ONE_HALF = new Fraction(0, 1, 2); // 0 1/2
	static final Fraction ONE_AND_HALF = new Fraction(1, 1, 2); // 1 1/2
	static final Fraction ONE_AND_THIRD = new Fraction(1, 1, 3); // 1 1/3

	Fraction actual;
	Fraction expected;

	/**
	 * Test the addition method with positive values.
	 */
	@Test
	void testAdditionBasicPositive()
	{
		// 1 1/2 + 1 1/2 = 3
		expected = new Fraction(3, 0, 4);
		actual = Operations.add(ONE_AND_HALF, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 3 0/4 + 1 1/2 = 4 1/2
		expected = new Fraction(4, 4, 8);
		actual = Operations.add(actual, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 4 4/8 + 1 1/3 = 5 20/24
		expected = new Fraction(5, 20, 24);
		actual = Operations.add(actual, ONE_AND_THIRD);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the addition method with adding zero.
	 */
	@Test
	void testAdditionBasicPositiveZero()
	{
		expected = ZERO;
		actual = Operations.add(ZERO, ZERO);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the addition method with positive and negative values.
	 */
	@Test
	void testAdditionBasicNegative()
	{
		Fraction positive = new Fraction(2, 1, 2);
		Fraction negative = new Fraction(-1, 1, 2);
		actual = Operations.add(positive, negative);
		expected = new Fraction(1, 0, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		actual = Operations.add(negative, negative);
		expected = new Fraction(-3, 0, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		actual = Operations.add(negative, positive);
		expected = new Fraction(1, 0, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		negative = new Fraction(0, -1, 2);
		actual = Operations.add(positive, negative);
		expected = new Fraction(2, 0, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

	}

	/**
	 * Test the subtraction method.
	 */
	@Test
	void testSubtractionBasicPositive()
	{
		// 1 1/2 + 1 1/2 = 3 0/4
		expected = new Fraction(3, 0, 4);
		actual = Operations.add(ONE_AND_HALF, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 3 - 1 1/2 = 1 4/8
		expected = new Fraction(1, 4, 8);
		actual = Operations.subtract(actual, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 1 4/8 - 1 1/2 = 0 0/16
		expected = new Fraction(0, 0, 16);
		actual = Operations.subtract(actual, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the subtraction method with adding zero.
	 */
	@Test
	void testSubtractionBasicPositiveZero()
	{
		// Need to add / by zero handling in "toMixed" in Operations Class.
		expected = ONE_AND_HALF;
		actual = Operations.subtract(ONE_AND_HALF, ZERO);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the subtract method with positive and negative values.
	 */
	@Test
	void testSubtractionBasicNegative()
	{
		Fraction positive = new Fraction(2, 1, 2);
		Fraction negative = new Fraction(-1, 1, 2);
		actual = Operations.subtract(positive, negative);
		expected = new Fraction(4, 0, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		actual = Operations.subtract(negative, negative);
		expected = new Fraction(0, 0, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		actual = Operations.subtract(negative, positive);
		expected = new Fraction(-4, 0, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

	}

	/**
	 * Test the multiplication method.
	 */
	@Test
	void testMultiplicationBasicPositive()
	{
		// 1 1/2 * 1 1/2 = 2 1/4
		expected = new Fraction(2, 1, 4);
		actual = Operations.multiply(ONE_AND_HALF, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 2 1/4 * 1 1/2 = 3 3/8
		expected = new Fraction(3, 3, 8);
		actual = Operations.multiply(actual, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the multiply method with zero.
	 */
	@Test
	void testMultiplicationBasicPositiveZero()
	{
		// Need to add / by zero handling in "toMixed" in Operations Class.
		expected = ZERO;
		actual = Operations.multiply(ONE_AND_HALF, ZERO);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the multiply method with positive and negative values.
	 */
	@Test
	void testMultiplyBasicNegative()
	{
		Fraction positive = new Fraction(2, 1, 2);
		Fraction negative = new Fraction(-1, 1, 2);
		actual = Operations.multiply(positive, negative);
		expected = new Fraction(-3, 3, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		actual = Operations.multiply(negative, negative);
		expected = new Fraction(2, 1, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		actual = Operations.multiply(negative, positive);
		expected = new Fraction(-3, 3, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

	}

	/**
	 * Test the division method.
	 */
	@Test
	void testDivisionBasicPositive()
	{
		// 1 1/2 / 1 1/2 = 1 0/6
		expected = new Fraction(1, 0, 6);
		actual = Operations.divide(ONE_AND_HALF, ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 1 / 0 1/2 = 2 0/6
		expected = new Fraction(2, 0, 6);
		actual = Operations.divide(actual, ONE_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the divide method with zero.
	 */
	@Test
	void testDivisionBasicPositiveZero()
	{
		// Need to add / by zero handling in "toMixed" in Operations Class.
		expected = ZERO;
		actual = Operations.divide(ONE_AND_HALF, ZERO);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the divide method with positive and negative values.
	 */
	@Test
	void testDivisionBasicNegative()
	{
		Fraction positive = new Fraction(4, 1, 2);
		Fraction negative = new Fraction(-1, 1, 2);
		actual = Operations.divide(positive, negative);
		expected = new Fraction(-3, 0, 6);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		actual = Operations.divide(negative, negative);
		expected = new Fraction(1, 0, 6);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

	}

	/**
	 * Test the simplify method.
	 */
	@Test
	void testSimplifySimple()
	{

		Fraction f1 = new Fraction(1, 3, 6);
		f1 = Operations.simplify(f1);
		assertEquals(1, f1.getWhole());
		assertEquals(1, f1.getNumer());
		assertEquals(2, f1.getDenom());

		f1 = ONE_AND_HALF;
		f1 = Operations.simplify(f1);
		assertEquals(1, f1.getWhole());
		assertEquals(1, f1.getNumer());
		assertEquals(2, f1.getDenom());

		f1 = new Fraction(2, 50, 100);
		f1 = Operations.simplify(f1);
		assertEquals(2, f1.getWhole());
		assertEquals(1, f1.getNumer());
		assertEquals(2, f1.getDenom());

		f1 = new Fraction(13, 1000, 10000);
		f1 = Operations.simplify(f1);
		assertEquals(13, f1.getWhole());
		assertEquals(1, f1.getNumer());
		assertEquals(10, f1.getDenom());

		f1 = new Fraction(5, 20, 24);
		f1 = Operations.simplify(f1);
		assertEquals(5, f1.getWhole());
		assertEquals(5, f1.getNumer());
		assertEquals(6, f1.getDenom());

		f1 = new Fraction(1, 3, 2);
		f1 = Operations.simplify(f1);
		assertEquals(2, f1.getWhole());
		assertEquals(1, f1.getNumer());
		assertEquals(2, f1.getDenom());

		f1 = new Fraction(1, 0, 2);
		f1 = Operations.simplify(f1);
		assertEquals(1, f1.getWhole());
		assertEquals(0, f1.getNumer());
		assertEquals(1, f1.getDenom());

	}

	/**
	 * Test the inverse method.
	 */
	@Test
	void testInverseSimple()
	{
		// Test positive inverse
		expected = new Fraction(0, 2, 3);
		actual = Operations.inverse(ONE_AND_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// Test negative inverse
		expected = new Fraction(0, -2, 3);
		actual = Operations.inverse(new Fraction(-1, 1, 2));
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

	}

	/**
	 * Test the mediant method.
	 */
	@Test
	void testMediant()
	{
		// 1/2 mediant 1/2 = 2/4
		expected = new Fraction(0, 2, 4);
		actual = Operations.mediant(ONE_HALF, ONE_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 1 1/2 (3/2) mediant 1/2 = 4/4 -> 1 0/4
		expected = new Fraction(1, 0, 4);
		actual = Operations.mediant(ONE_AND_HALF, ONE_HALF);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the power method.
	 */
	@Test
	void testPow()
	{
		// 1/2 to the 3rd power = 1/8
		expected = new Fraction(0, 1, 8);
		actual = Operations.pow(ONE_HALF, 3);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 1 1/2 to the 4th power = 5 1/16
		expected = new Fraction(5, 1, 16);
		actual = Operations.pow(ONE_AND_HALF, 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// -1/2 to the 2nd power = 1/4
		expected = new Fraction(0, 1, 4);
		actual = Operations.pow(new Fraction(0, -1, 2), 2);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// -1/2 to the 3rd power = -1/8
		expected = new Fraction(0, -1, 8);
		actual = Operations.pow(new Fraction(0, -1, 2), 3);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 3 to the 3rd power = 27 0/1
		expected = new Fraction(27, 0, 1);
		actual = Operations.pow(new Fraction(3, 0, 1), 3);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 9 to the 5th power = 59049 0/1
		expected = new Fraction(59049, 0, 1);
		actual = Operations.pow(new Fraction(9, 0, 1), 5);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test negative powers.
	 */
	@Test
	void testPowNegative()
	{
		// 1/2 to the -1st power = 2 0/1
		expected = new Fraction(2, 0, 1);
		actual = Operations.pow(ONE_HALF, -1);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 1/2 to the -3rd power = 8 0/1
		expected = new Fraction(8, 0, 1);
		actual = Operations.pow(ONE_HALF, -3);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// 1 1/2 to the -2nd power = 4/9
		expected = new Fraction(0, 4, 9);
		actual = Operations.pow(ONE_AND_HALF, -2);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

	}

	/**
	 * Tests the power method with 0.
	 */
	@Test
	void testPowZero()
	{
		// 1/2 to the 0th power = 1 0/1
		expected = new Fraction(1, 0, 1);
		actual = Operations.pow(ONE_HALF, 0);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

	/**
	 * Test the farey method.
	 */
	@Test
	void testFarey()
	{
		// order 5, 2/5 and 1/2 -> 3/5
		expected = new Fraction(0, 3, 5);
		actual = Operations.farey(new Fraction(0, 2, 5), ONE_HALF, 5);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// order 4, 1/2 and 2/3 -> 3/4
		expected = new Fraction(0, 3, 4);
		actual = Operations.farey(ONE_HALF, new Fraction(0, 2, 3), 4);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());

		// order 1, 0/1 and 1/2 -> 1/1
		expected = new Fraction(0, 1, 1);
		actual = Operations.farey(ZERO, ONE_HALF, 1);
		assertEquals(expected.getWhole(), actual.getWhole());
		assertEquals(expected.getNumer(), actual.getNumer());
		assertEquals(expected.getDenom(), actual.getDenom());
	}

}
