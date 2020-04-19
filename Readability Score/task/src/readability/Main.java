package readability;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // use args[0] to store the file name and append it to the path
        //String path = args[0];
        String path = "/moocs/jetbrains/Readability Score/in2.txt";
        String sentence;
        int sentenceCount = 0;
        int wordCount = 0;
        int charCount = 0;
        String ageRange = "";
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
                 ageRange = "5-6";
                break;
            case 2:
                 ageRange =  "6-7";
                break;
            case 3:
                ageRange = "7-9";
                break;
            case 4:
                ageRange = "9-10";
                break;
            case 5:
                ageRange = "10-11";
                break;
            case 6:
                ageRange = "11-12";
                break;
            case 7:
                ageRange = "12-13";
                break;
            case 8:
                ageRange = "13-14";
                break;
            case 9:
                ageRange = "14-15";
                break;
            case 10:
                ageRange = "15-16";
                break;
            case 11:
                ageRange = "16-17";
                break;
            case 12:
                ageRange = "17-18";
                break;
            case 13:
                ageRange = "18-24";
                break;
            case 14:
                ageRange = "24+";
                break;
            default:
                 ageRange = "Unknown";
                break;
        }
        System.out.println("This text should be understood by " + ageRange + " year olds.");
    }
}

