import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class xkcdpwgen{
  
  static String readAppointedLineNumber(File sourceFile, int lineNumber)
      throws IOException {
    FileReader in = new FileReader(sourceFile);
    LineNumberReader reader = new LineNumberReader(in);
    String s = "";
    int lines = 0;
    while (s != null) {
      lines++;
      s = reader.readLine();
      if(lines == lineNumber) {
        return s;
      }

    }
    reader.close();
    in.close();
    return "";
  }

  
  
  public static String changetoNumber(String str) {
    return str.replaceAll("[^0-9]", "");
  }
  
  public static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase()+s.substring(1);
  }
  
  public static String wordGenerator(int w, int c, int n, int s) throws IOException {
    ArrayList<String> symbol = new ArrayList<String>();
    symbol.add("~");
    symbol.add("!");
    symbol.add("@");
    symbol.add("#");
    symbol.add("$");
    symbol.add("%");
    symbol.add("^");
    symbol.add("&");
    symbol.add("*");
    symbol.add(".");
    symbol.add(":");
    symbol.add(";");
    ArrayList<String> output = new ArrayList<String>();
    ArrayList<Integer> capitalpos = new ArrayList<Integer>();
    ArrayList<String> insert = new ArrayList<String>();
    ArrayList<String> finaloutput = new ArrayList<String>();
    File source = new File ("test.txt");
    Random random = new Random();
    // generate n words
    for (int i=0; i<w; i++) {
      output.add(readAppointedLineNumber(source, random.nextInt(58110)+1));
    }
    for (int i=0; i<=w; i++) {
      insert.add("");
    }
    // add c capitalization
    if (c>=w) {
      for (int i=0;i<output.size(); i++) {
        output.set(i, capitalize(output.get(i)));
      }
    }
    else {
      while(capitalpos.size()<c) {
        int a = random.nextInt(w);
        if (!capitalpos.contains(a)) {
          capitalpos.add(a);
        }
      }
      for (int i=0;i<capitalpos.size(); i++) {
        output.set(capitalpos.get(i), capitalize(output.get(capitalpos.get(i))));
      }
    }
    
    // add n number
    for (int i=0; i<n; i++) {
      int rand = random.nextInt(insert.size());
      insert.set(rand, random.nextInt(10)+insert.get(rand));
    }
    
    // add s symbol
    for (int i=0; i<s; i++) {
      int rand1 = random.nextInt(insert.size());
      insert.set(rand1, symbol.get(random.nextInt(12))+insert.get(rand1));
    }
    
    finaloutput = insert;
    for (int i=0;i<output.size();i++) {
      finaloutput.add(2*i, output.get(i));
    }
    
    String outputstring = String.join("", finaloutput);
    return outputstring;
  }
    

  public static void main(String[] args) throws IOException {
    String command = "";
    for (int i=0; i<args.length; i++) {
      command = command+args[i];
    }
      Integer numberofWord = 4;
      Integer numberofCaps = 0;
      Integer numberofNumbers = 0;
      Integer numberofSymbols = 0;
      if (command.equals("-h")||command.equals("--help")) {
        System.out.println("usage: xkcdpwgen [-h] [-w WORDS] [-c CAPS] [-n NUMBERS] [-s SYMBOLS]\r\n"
            + "                \r\n"
            + "Generate a secure, memorable password using the XKCD method\r\n"
            + "                \r\n"
            + "optional arguments:\r\n"
            + "    -h, --help            show this help message and exit\r\n"
            + "    -w WORDS, --words WORDS\r\n"
            + "                          include WORDS words in the password (default=4)\r\n"
            + "    -c CAPS, --caps CAPS  capitalize the first letter of CAPS random words\r\n"
            + "                          (default=0)\r\n"
            + "    -n NUMBERS, --numbers NUMBERS\r\n"
            + "                          insert NUMBERS random numbers in the password\r\n"
            + "                          (default=0)\r\n"
            + "    -s SYMBOLS, --symbols SYMBOLS\r\n"
            + "                          insert SYMBOLS random symbols in the password\r\n"
            + "                          (default=0)");
      }
      
      else if (command.equals("")) {
        System.out.println(wordGenerator(4, 0, 0, 0));
      }
      //  generate random word
      else if (command.contains("-w") || command.contains("-c") 
          || command.contains("-n") || command.contains("-s") 
          || command.contains("--words") || command.contains("--caps")
          || command.contains("--numbers") || command.contains("-symbols")) {
        String[] seperatecom = command.split("-");
        ArrayList<String> sepcomarr = new ArrayList<String>(Arrays.asList(seperatecom));
        sepcomarr.removeAll(Arrays.asList("", null));
        for (int i=0; i<sepcomarr.size(); i++) {
          if (sepcomarr.get(i).contains("words") || sepcomarr.get(i).contains("w")) {
            numberofWord = Integer.valueOf(changetoNumber(sepcomarr.get(i)));
          }
          else if (sepcomarr.get(i).contains("caps") || sepcomarr.get(i).contains("c")) {
            numberofCaps = Integer.valueOf(changetoNumber(sepcomarr.get(i)));
          }
          else if (sepcomarr.get(i).contains("numbers") || sepcomarr.get(i).contains("n")) {
            numberofNumbers = Integer.valueOf(changetoNumber(sepcomarr.get(i)));
          }
          else if (sepcomarr.get(i).contains("symbols") || sepcomarr.get(i).contains("s")) {
            numberofSymbols = Integer.valueOf(changetoNumber(sepcomarr.get(i)));
          }
        }
        System.out.println(wordGenerator(numberofWord, numberofCaps, numberofNumbers, numberofSymbols));
      }
      else {
        System.out.println("error");
      }
  }
}
