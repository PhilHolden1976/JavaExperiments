import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class ByteTest 
{

  public static void main(String[] args) 
  {
      System.out.println();
      System.out.println("Old method...");
      runTest(false);

      System.out.println();
      System.out.println();
      System.out.println("New method...");
      runTest(true);
  }

  private static void runTest(boolean newMethod)
  {
      int[] integers = { 0, 1, 2, 50, 100, 150, 200, 253, 254, 255 };

      for (int i : integers)
      {
          if (i == 250) 
          {
              i = 255;
          }

          byte b = (byte) i;

          String initialSummary = getSummary(b, newMethod);

          for (int j = 0; j <= 7; j ++)
          {
              boolean bitState = getBitValueFromByte(b, j, newMethod);
              b = setBitValueInByte(b, j, !bitState, newMethod);
          }
          
          String finalSummary = getSummary(b, newMethod);

          System.out.println(initialSummary + " ----> " + finalSummary);
      }
  }
 
  private static boolean getBitValueFromByte(byte b, int bitPosition, boolean newMethod)
  {
      if (newMethod)
      {
          int i = convertByteToInteger(b);
          
          return (i & (1 << bitPosition)) != 0;
      }
      else
      {
          return (b & (1 << bitPosition)) != 0;
      }
  }

  private static byte setBitValueInByte(byte b, int bitPosition, boolean bitValue, boolean newMethod)
  {
      if (newMethod)
      {
          int i = convertByteToInteger(b);

          if (bitValue)
          {
              return (byte) (i | (1 << bitPosition));
          }
          else
          {
              return (byte) (i & ~(1 << bitPosition));
          }
      }
      else
      {
          if (bitValue)
          {
              return (byte) (b | (1 << bitPosition));
          }
          else
          {
              return (byte) (b & ~(1 << bitPosition));
          }
      }
  }

  private static int convertByteToInteger(byte b)
  {
      return ((int) b) & 0xff;
  }

  private static String getSummary(byte b, boolean newMethod)
  {
      int i = convertByteToInteger(b);
      String binaryString = decodeByteToBinaryString(b);
      String booleanString = createBooleanString(b, newMethod);
      return intToPaddedString(i) + " --> " + binaryString + " --> " + booleanString;
  }

  private static String decodeByteToBinaryString(byte b)
  {
      return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
  }

  private static String createBooleanString(byte b, boolean newMethod)
  {
      return booleanToLetter(getBitValueFromByte(b, 7, newMethod)) + " " +
             booleanToLetter(getBitValueFromByte(b, 6, newMethod)) + " " +
             booleanToLetter(getBitValueFromByte(b, 5, newMethod)) + " " +
             booleanToLetter(getBitValueFromByte(b, 4, newMethod)) + " " +  
             booleanToLetter(getBitValueFromByte(b, 3, newMethod)) + " " + 
             booleanToLetter(getBitValueFromByte(b, 2, newMethod)) + " " + 
             booleanToLetter(getBitValueFromByte(b, 1, newMethod)) + " " + 
             booleanToLetter(getBitValueFromByte(b, 0, newMethod)); 
  }

  private static String booleanToLetter(boolean state)
  {
      return state ? "X" : "_";
  }

  private static String intToPaddedString(int i)
  {
      String s = String.valueOf(i);
      if (s.length() == 1) return "  " + s;
      else if (s.length() == 2) return " " + s;
      else return s;
  }

}
