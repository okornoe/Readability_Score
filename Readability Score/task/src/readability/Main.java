package readability;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String ageRange;

    public static void main(String[] args) throws IOException {
        // use args[0] to store the file name and append it to the path
        //String path = args[0];

        int numOfSentence = 0;
        int numOfWords = 0;
        int numOfCharacters = 0;
        int roundedIntScore;
        float numCharDivideNumWords;
        float numWordsDivideNumSentence;
        double score;


        final float constVal4_71 = 4.71f;
        final float constVal0_5 = 0.5f;
        final float constVal321_43 = 21.43f;

        String path = "/moocs/jetbrains/Readability Score/in.txt";
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        //System.out.println(file.exists());

        while (scanner.hasNextLine()) {
            String readLine = scanner.nextLine();
            String[] sentence = readLine.split("[!?.]");
            numOfSentence = numOfSentence + sentence.length;

            String[] words = readLine.split("\\s+");
            numOfWords = numOfWords + words.length;
            for (String word : words) {
                numOfCharacters = numOfCharacters + word.length();
            }
        }

        //calculates the score of the text.
        numCharDivideNumWords = (float) numOfCharacters / numOfWords;
        numWordsDivideNumSentence = (float) numOfWords / numOfSentence;
        score = (constVal4_71 * numCharDivideNumWords) + ((constVal0_5 * numWordsDivideNumSentence) - constVal321_43);

        roundedIntScore = (int) Math.ceil(score);
        readableAge(roundedIntScore);

        System.out.println("Words: " + numOfWords);
        System.out.println("Sentences: " + numOfSentence);
        System.out.println("Characters: " + numOfCharacters);
        System.out.print("The score is: ");
        System.out.format("%.2f", score);
        System.out.println();
        System.out.println("This text should be understood by " + ageRange + " year olds.");
    }

    //This method determines which age range will be able to read the text.
    private static void readableAge(int score) {
        switch (score) {
            case 1:
                ageRange = "5-6";
                break;
            case 2:
                ageRange = "6-7";
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
    }
}

