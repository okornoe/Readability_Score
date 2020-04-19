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

            String[] ser = sentence.split("\\s+");
            wordCount = wordCount + ser.length;
            for (String i : ser) {
                charCount = charCount + i.length();
            }
        }

        float charDivWords =  (float)charCount/wordCount;
        float wordsDivSentences = (float) wordCount/sentenceCount;

        double score = (constVal4_71 * charDivWords) + ((constVal0_5 * wordsDivSentences) - constVal321_43);


        System.out.println("Words: " + wordCount);
        System.out.println("Sentences: " + sentenceCount);
        System.out.println("Characters: " + charCount);
        System.out.print("The score is: ");
        System.out.format("%.2f",score);
       System.out.println();
        int switchScore = (int)Math.ceil(score);

        switch (switchScore) {
            case 1:
                System.out.print("This text should be understood by 5-6 year olds.");
                break;
            case 2:
                System.out.print("This text should be understood by 6-7 year olds.");
                break;
            case 3:
                System.out.print("This text should be understood by 7-9 year olds.");
                break;
            case 4:
                System.out.print("This text should be understood by 9-10 year olds.");
                break;
            case 5:
                System.out.print("This text should be understood by 10-11 year olds.");
                break;
            case 6:
                System.out.print("This text should be understood by 11-12 year olds.");
                break;
            case 7:
                System.out.print("This text should be understood by 12-13 year olds.");
                break;
            case 8:
                System.out.print("This text should be understood by 13-14 year olds.");
                break;
            case 9:
                System.out.print("This text should be understood by 14-15 year olds.");
                break;
            case 10:
                System.out.print("This text should be understood by 15-16 year olds.");
                break;
            case 11:
                System.out.print("This text should be understood by 16-17 year olds.");
                break;
            case 12:
                System.out.print("This text should be understood by 17-18 year olds.");
                break;
            case 13:
                System.out.print("This text should be understood by 18-24 year olds.");
                break;
            case 14:
                System.out.print("This text should be understood by 24+ year olds.");
                break;
            default:
                System.out.print("Unknown");
                break;
        }



    }
}

