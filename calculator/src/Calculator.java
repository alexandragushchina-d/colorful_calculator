import java.math.BigInteger;

/**
 * Calculator with operators div, mul, sub, add
 */

public class Calculator {
  private String input;
  private int inputCounter = 0;
  private int operation = 0;
  // codeException = 1 => ArithmeticException and codeException = 2 => NumberFormatException
  private int codeException = 0;
  private boolean endOfString = false;
  private BigInteger res = BigInteger.ZERO;

  public Calculator(String input) {
    this.input = input;
  }

  public int getCodeException() {
    return codeException;
  }

  public BigInteger calc() {
    try {
      if (Character.isDigit(input.charAt(0))) {
        res = term();
      } else {
        operation = '-';
      }
      while (!endOfString) {
        switch (operation) {
          case '+':
            res = res.add(term());
            break;
          case '-':
            res = res.subtract(term());
            break;
        }
      }
      return res;
    } catch (ArithmeticException e) {
      codeException = 1;
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return BigInteger.ONE;
  }

  private BigInteger term() {
    BigInteger res = operand();
    while (!endOfString) {
      operation = sign();
      if (operation == '*') {
        res = res.multiply(operand());
      } else if (operation == '/') {
        res = res.divide(operand());
      } else if (operation == '+' || operation == '-') {
        return res;
      }
    }
    return res;
  }

  private BigInteger operand() {
    try {
      int count;
      if (isOperationSymbol()) {
        inputCounter++;
      }
      count = inputCounter;
      while (Character.isDigit(input.charAt(count)) && count < input.length() - 1) {
        count++;
      }
      if (count == input.length() - 1) {
        endOfString = true;
      }
      if (inputCounter == count || endOfString) {
        count++;
      }
      BigInteger operand = BigInteger.ZERO;
      return operand.add(new BigInteger(input.substring(inputCounter, count)));
    } catch (NumberFormatException e) {
      codeException = 2;
      e.printStackTrace();
    }
    return BigInteger.ONE;
  }

  private char sign() {
    try {
      if (isOperationSymbol()) {
        return input.charAt(inputCounter);
      } else {
        inputCounter++;
        return sign();
      }
    } catch (NumberFormatException e) {
      codeException = 2;
      e.printStackTrace();
    }
    return 0;
  }

  private boolean isOperationSymbol() {
    if (input.charAt(inputCounter) == '+' || input.charAt(inputCounter) == '-'
      || input.charAt(inputCounter) == '*' || input.charAt(inputCounter) == '/') {
      return true;
    }
    return false;
  }
}

