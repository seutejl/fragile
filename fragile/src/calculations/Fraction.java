package calculations;

/**
 * Class to represent a fraction.
 * 
 * @author Tinh, Will, Teddy.
 */
public class Fraction
{
  private int whole;
  private int numer;
  private int denom;

  /**
   * Constructor.
   * 
   * @param whole
   *          The whole number.
   * @param numer
   *          The numerator.
   * @param denom
   *          The denominator.
   */
  public Fraction(int whole, int numer, int denom)
  {
    this.whole = whole;
    this.numer = numer;
    this.denom = denom;
    if (denom == 0)
    {
      this.whole = 0;
      this.numer = 0;
      this.denom = 1;
    }
  }

  /**
   * Parse a string of the format: whole numer/denom to a Fraction.
   * 
   * @param toParse
   *          The string to be parsed.
   * @return The fraction.
   */
  public static Fraction parseFraction(String toParse)
  {
    String toParseTemp = toParse.trim();
    String temp[] = toParseTemp.split("\\s", 2);
    Integer whole = 0;
    Integer numer = 0;
    Integer denom = 1;
    if (temp.length == 1)
    {
      if (temp[0].contains("/"))
      {
        temp[0] = temp[0].replaceAll("\\s", "");
        numer = parseNum(temp[0].substring(0, temp[0].indexOf("/")));
        denom = parseNum(temp[0].substring(temp[0].indexOf("/") + 1, temp[0].length()));
      }
      else
      {
        whole = parseNum(temp[0]);
      }
    }
    else
    {
      whole = parseNum(temp[0]);
      temp[1] = temp[1].replaceAll("\\s", "");
      numer = parseNum(temp[1].substring(0, temp[1].indexOf("/")));
      denom = parseNum(temp[1].substring(temp[1].indexOf("/") + 1, temp[1].length()));
    }
    if (whole == null || numer == null || denom == null || denom == 0)
    {
      return new Fraction(0, 0, 1);
    }
    return new Fraction(whole, numer, denom);
  }

  /**
   * parseNum parses an Integer from a String.
   * 
   * @param val
   *          is the value String.
   * @return the parsed Integer.
   */
  private static Integer parseNum(String val)
  {
    int retval = 0;
    try
    {
      retval = Integer.parseInt(val);
    }
    catch (NumberFormatException e)
    {
      return null;
    }
    return Integer.valueOf(retval);
  }

  /**
   * Getter for the whole number.
   * 
   * @return The whole number.
   */
  public int getWhole()
  {
    return whole;
  }

  /**
   * Getter for numerator.
   * 
   * @return The numerator.
   */
  public int getNumer()
  {
    return numer;
  }

  /**
   * Getter for denominator.
   * 
   * @return The denominator.
   */
  public int getDenom()
  {
    return denom;
  }

  /**
   * Setter method for the whole number.
   * 
   * @param whole
   *          is the whole number.
   */
  public void setWhole(int whole)
  {
    this.whole = whole;
  }

  /**
   * String representation of the fraction.
   * 
   * @param style
   *          The style that the user has selected. (bar, slash, or solice)
   * @return The string representation.
   */
  public String toString(int style)
  {
    String toStr = "";

    switch (style)
    {
      case 1: // Bar style
        if (getNumer() == 0)
        {
          return "";
        }
        StringBuilder sb = new StringBuilder();
        String fs = String.format("<html><u>%d</u><br>%d</html>", getNumer(), getDenom());
        sb.append(fs);
        return sb.toString();
      // break;
      case 2: // Solice style
        if (getNumer() == 0)
          toStr = "" + getWhole();
        else
          toStr = getWhole() + " " + superscript(getNumer()) + "/" + subscript(getDenom());
        break;
      case 3: // Slash style
        if (getNumer() == 0)
          toStr = "" + getWhole();
        else
          toStr = getWhole() + " " + getNumer() + "/" + getDenom();
        break;
      default:
        break;
    }
    return toStr;
  }

  /**
   * A helper method for getting the superscript variants of numbers.
   * 
   * @param num
   *          The number to turn into a superscript.
   * @return the unicode of the number.
   */
  public static String superscript(int num)
  {
    String temp = "" + num;
    char[] array = temp.toCharArray();
    String str = "";
    for (char cur : array)
    {
      int numTemp = Integer.parseInt("" + cur);
      // The code above this is to encode numbers with more than one char
      // ie 12 instead of 1.
      switch (numTemp)
      {
        case 0:
          str += "\u2070";
          break;
        case 1:
          str += "\u00B9";
          break;
        case 2:
          str += "\u00B2";
          break;
        case 3:
          str += "\u00B3";
          break;
        case 4:
          str += "\u2074";
          break;
        case 5:
          str += "\u2075";
          break;
        case 6:
          str += "\u2076";
          break;
        case 7:
          str += "\u2077";
          break;
        case 8:
          str += "\u2078";
          break;
        case 9:
          str += "\u2079";
          break;
        default:
          str += " "; // should not get here
          break;
      }
    }
    return str;
  }

  /**
   * A helper method for getting the subscript variants of numbers.
   * 
   * @param num
   *          The number to turn into its subscript version.
   * @return the unicode of the number
   */
  public static String subscript(int num)
  {
    String temp = "" + num;
    char[] array = temp.toCharArray();
    String str = "";
    for (char cur : array)
    {
    	int numTemp = Integer.parseInt("" + cur);
      // The code above this is to encode numbers with more than one char
      // ie 12 instead of 1.
      switch (numTemp)
      {
        case 0:
          str += "\u2080";
          break;
        case 1:
          str += "\u2081";
          break;
        case 2:
          str += "\u2082";
          break;
        case 3:
          str += "\u2083";
          break;
        case 4:
          str += "\u2084";
          break;
        case 5:
          str += "\u2085";
          break;
        case 6:
          str += "\u2086";
          break;
        case 7:
          str += "\u2087";
          break;
        case 8:
          str += "\u2088";
          break;
        case 9:
          str += "\u2089";
          break;
        default:
          str += " "; // should not get here
          break;
      }
    }
    return str;
  }

}
