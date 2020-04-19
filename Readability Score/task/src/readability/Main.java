package readability;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // use args[0] to store the file name and append it to the path
        //String argsParam = args[0];
        String path = "/moocs/jetbrains/Readability Score/in.txt";
        String sentence;
        int sentenceCount = 0;
        int wordCount = 0;
        int charCount = 0;
        final float constVal4_71 = 4.71f;
        final float constVal0_5 = 0.5f;
        final float constVal321_43 = 21.43f;
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        //System.out.println(file.exists());

        while (scanner.hasNextLine()) {
            sentence = scanner.nextLine();
            String[] strArr = sentence.split("[!?.]");
            sentenceCount = sentenceCount + strArr.length;
            //sentenceCount++;

            //debugging
            /*BufferedWriter fileWriter = new BufferedWriter(new FileWriter("/moocs/jetbrains/Readability Score/debug.txt"));
            for (String s : strArr) {
                fileWriter.write(s);
                fileWriter.newLine();
            }
            fileWriter.close();*/
/*
            for (String s : strArr) {
                String[] strwords = s.trim().split("\\s");
                wordCount = wordCount + strwords.length;

                //String[] charSplit = strArr.split("\\s+");
                for (int i = 0; i<wordCount; i++) {
                    charCount = charCount + strwords[i].length();
                }
            }*/
            //String[] strwords = strArr.split("\\s");
            /*for (int i = 0; i <strArr.length; i++) {
                String[] strwords = strArr[i].trim().split("\\s+");
                wordCount = wordCount + strwords.length;
                //charCount = charCount + strwords[i].toCharArray().length;
            }*/

            String[] ser = sentence.split("\\s+");
            wordCount = wordCount + ser.length;
            for (String i : ser) {
                charCount = charCount + i.length();
            }
        }

        float charDivWords =  (float)charCount/wordCount;
        float wordsDivSentences = (float) wordCount/sentenceCount;

        double score = (constVal4_71 * charDivWords) + ((constVal0_5 * wordsDivSentences) - constVal321_43);

        System.out.println("sentence: " + sentenceCount);
        System.out.println("words: " + wordCount);
        System.out.println("character: " + charCount);
        System.out.print("Score: ");
        System.out.printf("%.2f",score);



/*      Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();
        int countWord = 0;

        String[] strArr = sentence.split("[!?.]");
        int sentenceLen = strArr.length;

        for (String s: strArr) {
          // String[] st = s.split("\\u00a0");
            String[] st = s.split("\\s");
           countWord += st.length;
        }

       if(countWord/sentenceLen<=10) {
           System.out.println("EASY");
       } else {
           System.out.println("HARD");
       }*/
    }
}

