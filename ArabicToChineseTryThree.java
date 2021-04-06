
/**
 * <h1>Number convertion between Arabic and Chinese</h1>
 * <p>This class is mainly used to make convertions between Arabic numbers
 * and Chinese characters.</p>
 * <p>It gets userinput from command line,and output whatever you want</p>
 *
 * @author Tiger Wang
 * @version 1.0
 * @since 2021-04-05
 */
public class ArabicToChineseTryThree {
  //Elements in the UnitsOfInnteger will be appended to a StringBuilder
  //and the append() method takes strings as parameter,so it's ok to use an array of strings
  //String[] UnitsOfInteger={       0     1     2       3     4     5     6      7    8     9     10    11
  String[] UnitsOfInteger = {"元整","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟"};
  String[] UnitsOfDecimal = {"角","分","厘"};
  String[] SpecialUnit = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};

  /**
   * This is the main entrance of our program.It creates a new object of the class
   * and invoke the go() method.
   * @param args input default arguments for main method
   *
   */
  public static void main(String[] args) {
    ArabicToChineseTryThree attt = new ArabicToChineseTryThree();
    attt.go();
  }

  /**
  * This method trigers the main process of our program.<p>First print out some greetig
  * messages,second asks for an input and checks its validility.If it's invalid
  * asks for reinput,else start the convertion and pring out the result.</p>
  *
  *
  */
  public void go() {
    while(true){
    //the rules info tells user how to input correctly
    printRulesInfo();

    //get user input,treat it as a string
    java.util.Scanner sc = new java.util.Scanner(System.in);
    String inputString = sc.nextLine();

    //check for validality,if failed ask the user to input again and print out the rules info.
    if (!isValid(inputString)) {
      printRulesInfo();
      // break;
    } else {
      arabicToChinese(inputString);
      break;//after convertion,break out the while loop
    }

    } // close while
  }//close go()

  /**
   * This method print out the basic rules for input.
   *
   *
   */
  public void printRulesInfo() {
    System.out.println("1.Up to 12 integer numbers and 3 decimal numbers are allowed.");
    System.out.println("2.Only digits and the decimal dot are allowed.");
    System.out.println("3.The decimal cannot be placed either at the start or the end of the number.");
  }//close printRulesInfo()

  /**
   * This method checks if the input is valid.
   * @param inputString the string representation to which the method is to convert
   * @return Returns ture if it's valid,false otherwise
   */
  public boolean isValid(String inputString) {
    /*1. only 0-9 and '.' is allowed
     *2. the first digit cannot be 0 and '.' cannot be at either the first or the last position
     *3. up to 12 digits in integer part and 3 digits in decimal part
     */
     boolean flag=false;
     int integerCount=0;
     int decimalCount=0;
     /*
      * First we need to check if all of the characters in the inputString are allowed here.
     */
    for(int i=0;i<inputString.length();i++) {
      char chi = inputString.charAt(i);
      /*0-9 '.' is allowed
       * As long as there's one invalid character,we should set flage to false and break out for loop
       * No,we don't need to set flag to false,because it's false by default.Just quit the loop is enough.
       * No,don't quit the loop,return flag instead.
       *
       * But when we break out the for loop,how do you know wether it's because an invalid character is encoutered
       * or it's just the end of the loop?
       * Don't worry,this is just the very first check-up.If it fails,we directly return flag.If it passes
       * the following check-ups are waiting for it.
       */
      // if((chi < '0' && chi != '.') || chi > '9') {
      //   return flag;
      // }
      if(chi >= '0' && chi <= '9' || chi == '.') {
        continue;
      } else {
        return flag;
      }
    } // close for loop

    /*
     * Now,we've passed the first check-up,all of the characters in the inputString are allowed,
     * but that doesn't mean each of the digit is on the right position.
    */
     char headCh = inputString.charAt(0);
     char tailCh = inputString.charAt(inputString.length() - 1);
     if(headCh == '0' || headCh == '.' || tailCh == '.') {
        return flag;//Violated rule No.2.
      }

    //Now check the length
    if(inputString.indexOf(".") == -1) {
      if(inputString.length() > 12) {
        return flag;
      } else {
        flag = true;
        /*
         * If the number is an integer and its length is less than 12,we should set flag to true
         * otherwise it will remain false.
         *
         * Now we can definately know that the number is valid,because it's an integer
         * after passing all of the tests above,only the length shold be checked.
         * Since the length is valid ,the number is valid.
        */
      }
    } else {
      decimalCount = inputString.length() - 1 - inputString.indexOf(".");
      if(decimalCount > 3) {
        return flag;
      } else {
        integerCount = inputString.length() - 1 - decimalCount;
        if(integerCount > 12) {
          return flag;
        } else {
          flag = true;
        }
      }
    }
    return flag;
  } //close isValid()

  /**
   * This is the main part of the program.Converting Arabic numbers into Chinese.
   * It uses two seperate methods to process two main kinds of number:Integer numbers
   * and Decimal numbers.
   * @param inputString the string representation to which the method is to convert
   *
   */
  public void arabicToChinese(String inputString) {
    StringBuilder result = new StringBuilder();
    if(inputString.contains(".")){
      result = decimalConvertionToChinese(inputString);
    } else {
      result = integerConvertionToChinese(inputString);
    } //close if
    System.out.println(result);
  } //close arabicToChinese()

  /**
   * This is the method that deals with the decimal numbers.
   * <p> Main process:convert the decimal part and append the result to the converted integer part
   * returned by the integerConvertionToChinese() method.</p>
   * @param inputString the string representation of the number to which
   * the method is to deal with
   * @return returns the result as a StringBuilder
   */
  public StringBuilder decimalConvertionToChinese(String inputString){
    int numOfConcecutiveZero=0;
    /* First in first,we split the inputString into two parts,integer part and decimal part.
     * The substring() method returns a string that is a substring of this string.The substring begins
     * at the specified beginInndex and extends to the character at index endIndex - 1.
     */
    String integerPart = inputString.substring(0,inputString.indexOf("."));
    String decimalPart = inputString.substring(inputString.indexOf(".")+1,inputString.length());
    //The converted decimal part will bestored in the decimalResult
    StringBuilder decimalResult = new StringBuilder();
    //This is the final result to which the method is to return
    StringBuilder result = new StringBuilder();

    //Now we are beginning to convert the decimal part.
    for(int i=0;i<decimalPart.length();i++) {
      int charToInt = (int) decimalPart.charAt(i) - 48;
      int correspondingIndex = i;
      if(decimalPart.charAt(i) == '0') {
        numOfConcecutiveZero++;
        /*
         * If all of the digits are 0,we still need to add "zheng" to decimalResult.
         *
         * To increase the useage of our code,we use another method to help us deal with the integer part,
         * and we just need to deal with the decimal part here.And in the end,we connect them toghther as our
         * final result.That's convenient and easy to do,but convenience comes with its price.
         *
         * There's always be a "yuanzheng" at the end of the convertion.Since we are going to use the replace()
         * method of the StringBuilder,and we're going to use our converted decimal part to replace the
         * "zheng" at the end.What if all of the digits in the decimal part is 0?The number should be an
         * integer in Chinese,so the last unit should still be "yuanzheng".That means under this case,the
         * "zheng" at the end should not be replaced.
         *
         * So wether or not the "zheng" should be replaced depends on wether the decimal part is all of 0 or not.
         * Actually,we always use the replace() method,but change the content.If it's all 0,we replace "zheng" at
         * the end with "zheng",so it seems that it hasn't been replaced,but it's actually not.
        */
        if(numOfConcecutiveZero == decimalPart.length()) {
          decimalResult.append("整");
        }
        /*
          no matter how long the decimalPart is,if the last digit is 0,
          then you'll continue,reach the end of decimalPart and the loop is over
          so we don't need an extra break
        */
        // if(i == decimalPart.length()-1){
        //   break;
        // }
        continue;
      } else if(numOfConcecutiveZero == 0){
        decimalResult.append(SpecialUnit[charToInt]);
        /*
         * decimalResult.append(UnitsOfDecimal[i]);
         * The variable "i" in the above statement works as the correspondingIndex to help us locate
         * the right unit in the decimal Unit string.Surprisingly,the sequence of the digits in the
         * decimal part is right the same as the sequence of the decimal unit hierarchy,both start from
         * 0.
         *
         * Although it might be more convenient to just use the variable "i", to add more readibility to
         * our code,here we still use "correspondingIndex" instead of "i".
        */
        decimalResult.append(UnitsOfDecimal[correspondingIndex]);
      } else {
        numOfConcecutiveZero=0;
        decimalResult.append(SpecialUnit[0]);
        decimalResult.append(SpecialUnit[charToInt]);
        decimalResult.append(UnitsOfDecimal[correspondingIndex]);
      }
    }
    // System.out.println(integerPart +"ddd"+ decimalPart);
    //we should only pass the integer part of the inputString
    StringBuilder temporaryResult = integerConvertionToChinese(integerPart);
    String decimalResultString = decimalResult.substring(0);
    result = temporaryResult.replace(temporaryResult.indexOf("整"),temporaryResult.length(),decimalResultString);
  return result;

  } //close of decimalConvertionToChinese()

  /**
   * This is the method that deals with the integer numbers.
   * <h3>  int numOfConcecutiveZero=0;</h3>
   * <p>One of the key point of this method is that we have a variable numOfConcecutiveZero,clearly
   * seen from the name,it's used to store the number of concecutive zeros in the string representation
   * of the number.If no zeros encountered in the whole string,it's easy to just add the number and
   * its corresponding unit to the result.</p>
   *
   * <p>When it comes to zero,thing's got a little bit complicated.If no concecutive zeros occur,
   * that means numOfConcecutiveZero=1,we just skip the zero and add the special unit "ling".If
   * numOfConncecutiveZero greater than 1,that means we have multiple concecutive zeros,usually we just skip them
   * all and add the special unit "ling".But the unit "yi","wan","yuanzheng" should always be added
   * wether it's zero or not.</p>
   *
   * @param inputString this is the string representation of the number to which the method is to deal with
   * @return returns the result as a StringBuilder
   */
  public StringBuilder integerConvertionToChinese(String inputString) {

    int numOfConcecutiveZero=0;
    //to store the result of the convertion
    StringBuilder result = new StringBuilder();
    /* We use the variable 'length' to connect the index of each digit with its corresponding unit.
     * In Chinese the first digit of a number always gets the highest position in the unit system
     * according to its actual length.And so the highest position must change with the length of the
     * number,the longer the length,the higher it will be.It's neither meaningless nor practical to
     * restrict the length of the number that the user can input.
     *
     * As we can see,Chinese way of spelling numbers is strongly connected with the length of the number.
     * And the last digit always gets the lowest position in the unit system:"yuanzheng",because that's
     * where the unit hierarchy starts from.
     *
     * So do we!We should also start from the last digit,and that's how the very first version of the
     * program really implement.It's perfect to work with a non-zero senario,but once we encounter a zero
     * it sucks!It's really hard to deal with zeros,especially concecutive zeros.And the crucial part is
     * wether we should add "ling" or add its corresponding unit or just skip to the next digit.Since
     * Chinese way of spelling numbers is from the highest position to the lowest,so now we have the
     * following improved version:
     *
     * We start from the highest position.But how do we know its corresponding unti?Because this is where
     * the unit hierarchy ends,not starts from.Don't worry,we can get the length of the string representation
     * of the input nubmer,and use that length to locate its corresponding unit.Perfect!It also helps a lot
     * to deal with multiple concecutive zeros.
     */
    int length = inputString.length();

    for(int i=0;i<length;i++) {
      int charToInt = inputString.charAt(i) - 48;
      /*
       * Clearly seen from its name,this is right the corresponding index in the unit string of the current
       * digit.Since we've already got its length,we could exactly know the corresponding unit of each digit.
       * That's what the variable correspondingIndex is used for,to help us get the right unit from the unit string.
       */
      int correspondingIndex = length-1-i;
        //NOTE:the method charAt() returns a result of type char,don't use 0, use '0' instead.
      if(inputString.charAt(i) == '0'){
        numOfConcecutiveZero++;

        if (correspondingIndex == 8 || correspondingIndex == 4){
          /*
           * In Chinese,we divide the number with 4 digits as a group,each group is named by its lowest
           * unit.When we say lowest,we say the right edge,because we spell a number from left to right.
           * So the following if statement is designed when we encounter 4 concecutive zeros in the middle group
           * named "wan",in this case,we should omit the zeros ,add nothing and continue to the next one.
          */
          if(numOfConcecutiveZero == 4) {
            numOfConcecutiveZero = 0;
            continue;
          }
          /*
           * Usually,the unit "yi" and "wan" should always be added wether its corresponding digit is 0 or not.
           * Special condition has been considered above.
          */
          result.append(UnitsOfInteger[correspondingIndex]);
          /*
           * After adding the unit "yi",we should set the variable numOfConcecutiveZero to 0,because all the
           * digits have already been processed,no need to store its information anymore,so we should refresh it
           * and set it to 0.
           */
          numOfConcecutiveZero=0;
        }
        /*
         * Since we're now dealing with integers we should always add "yuanzheg" at the end of the result.
        */
        if (correspondingIndex == 0) {
          result.append(UnitsOfInteger[correspondingIndex]);
          continue;
        }

        continue;

      } else if(numOfConcecutiveZero == 0){
        //The first digit cannot be 0,which is promised by isValid(),so we don't need to check
        result.append(SpecialUnit[charToInt]);
        //no matter what happends,the first digit's corresponding unit should always be printed out right
        //after itself
        result.append(UnitsOfInteger[correspondingIndex]);
      } else {
        numOfConcecutiveZero=0;
        result.append(SpecialUnit[0]);
        //The first digit cannot be 0,which is promised by isValid(),so we don't need to check
        result.append(SpecialUnit[charToInt]);
        //no matter what happends,the first digit's corresponding unit should always be printed out right
        //after itself
        result.append(UnitsOfInteger[correspondingIndex]);
      } // close if
    } //close for loop
    result.insert(0,"人民币");
    return result;
  } //close of integerConvertionToChinese()


} //close class
