package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String ARI = "ARI";
    private static final String FK = "FK";
    private static final String SMOG = "SMOG";
    private static final String CL = "CL";
    private static final String ALL = "all";
    private static String str;

    //If you want to pass the filename directly uncomment line 22 and comment line 21.
    public static void main(String[] args) throws IOException {
        //final String filename = args[0];
         String filename = "/jetBrains/Readability_Score/newTest.txt";
        str = readFile(filename);

        print();
    }

    //reads the file
    private static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    // This method is responsible for printing final result to the screen
    private static void print() {
        System.out.println("Words: " + numberOfWords());
        System.out.println("Sentences: " + numberOfSentences());
        System.out.println("Characters: " + numberOfCharacters());
        System.out.println("Syllables: " + syllables());
        System.out.println("Polysyllables: " + polySyllables());
        readabilityScoreSelection();
        System.out.println();
        averageReadableAge();
    }

    private static void printFormat(String readabilityType, double scoreValue, int readableAge) {
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    //prints the automatic readability index
    private static void printARI() {
        String readabilityType = "Automated Readability Index: ";
        double scoreValue = authomaticReadabilityIndex();
        int readableAge = readableAge((int)Math.round(scoreValue));
        printFormat(readabilityType,scoreValue,readableAge);
        System.out.println();
    }

    // responsible for printing flesch-kincaid
    private static void printFK() {
        String readabilityType = "Flesch–Kincaid readability tests: ";
        double scoreValue = fleschKincaidReadTest();
        int readableAge = readableAge((int)Math.round(scoreValue));
        printFormat(readabilityType,scoreValue,readableAge);
        System.out.println();
    }

    //responsible for printing the simple-measure og gobbledygook.
    private static void printSMOG() {
        String readabilityType = "Simple Measure of Gobbledygook: ";
        double scoreValue = simpleMeasureOfGobbledygook();
        int readableAge = readableAge((int)Math.round(scoreValue));
        printFormat(readabilityType,scoreValue,readableAge);
        System.out.println();
    }

    //responsible for printing the coleman-liau index
    private static void printCL() {
        String readabilityType = "Coleman–Liau index: ";
        double scoreValue = colemanLauIndex();
        int readableAge = readableAge((int)Math.round(scoreValue));
        printFormat(readabilityType,scoreValue,readableAge);
    }

    // responsible for printing all index score when all is selected.
    private static void printAll() {
        printARI();
        printFK();
        printSMOG();
        printCL();
    }

    //determines which readability is printed based on user selection.
    private static void readabilityScoreSelection() {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        String scoreType = new Scanner(System.in).next();
        System.out.println();

        switch (scoreType) {
            case ARI -> printARI();
            case FK -> printFK();
            case SMOG -> printSMOG();
            case CL -> printCL();
            case ALL -> printAll();
            default -> System.out.println("unknown score type selection");
        }
    }

    //splits the file into sentences
    private static String[] sentence() {
        return str.replaceAll("\\s+", "").split("[!?.]");
    }

    //splits the file into words
    private static String[] words() {
        return str.split("\\s+");
    }

    //calculates the number of sentences in the file
    private static int numberOfSentences() {
        return sentence().length;
    }

    //calculates the number of words in the file
    private static int numberOfWords() {
        return words().length;
    }

    //calculates the number characters in the file
    private static int numberOfCharacters() {
        int len = 0;
        for (String word : words()) {
            len += word.length();
        }
        return len;
    }

    //calculates the number syllables in a word.
    private static int syllables() {
        int sum = 0;
        for (String word : words()) {
            int numOfVowelsInWord = countVowelsInWord(word);
            numOfVowelsInWord = (numOfVowelsInWord == 0) ? 1 : numOfVowelsInWord;
            sum = sum + numOfVowelsInWord;
        }
        return sum;
    }

    //calculates the number of syllables in the word greater than two
    private static int polySyllables() {
        int pSyllable = 0;
        for (String word : words()) {
            int numOfVowelsInWord = countVowelsInWord(word);
            if (numOfVowelsInWord > 2) {
                pSyllable = pSyllable + 1;
            }
        }
        return pSyllable;
    }

    //count vowels in the word.
    private static int countVowelsInWord(String word) {
        final Pattern p = Pattern.compile("([ayeiou]+)");
        String lowerCase = word.toLowerCase();
        if (lowerCase.endsWith(".") || lowerCase.endsWith("!") || lowerCase.endsWith("?") || lowerCase.endsWith(",")) {
            lowerCase = lowerCase.substring(0, lowerCase.length() - 1);
        }
        final Matcher m = p.matcher(lowerCase);
        int count = 0;
        while (m.find()) {
            count++;
        }
        if (lowerCase.endsWith("e")){
            count--;
        }
        return count;
    }

    //calculate the average readable age
    private static void averageReadableAge() {
        double averageAge = (readableAge((int) Math.round(authomaticReadabilityIndex())) +
                readableAge((int) Math.round(fleschKincaidReadTest())) +
                readableAge((int) Math.round(simpleMeasureOfGobbledygook())) +
                readableAge((int) Math.round(colemanLauIndex()))) / 4.0;
                System.out.println("This text should be understood in average by " + averageAge +
                " year olds.");
    }

    //calculates the score of the text.
    private static double authomaticReadabilityIndex() {
        double numCharDivideNumWords;
        double numWordsDivideNumSentence;
        numCharDivideNumWords = (double) numberOfCharacters() / numberOfWords();
        numWordsDivideNumSentence = (double) numberOfWords() / numberOfSentences();
        return Math.round((4.71 * numCharDivideNumWords +
                (0.5 * numWordsDivideNumSentence - 21.43)) * 100.0) / 100.0;
    }

    //calculates the Flesch Kincaid score.
    private static double fleschKincaidReadTest() {
        return Math.round((0.39 * ((double) numberOfWords() / numberOfSentences()) + 11.8 *
                ((double) syllables() / numberOfWords()) - 15.59) * 100.0) / 100.0;
    }

    //calculates the Simple Measure Of Gobbledygook.
    private static double simpleMeasureOfGobbledygook() {
        return Math.round((1.043 * Math.sqrt(polySyllables() * ((double) 30 / numberOfSentences()))
                + 3.1291) * 100.0) / 100.0;
    }

    //calculates the coleman-Lau Index
    private static double colemanLauIndex() {
        double l = ((double) numberOfCharacters() / (double) numberOfWords()) * 100;
        double s = (double) numberOfSentences() / (double) numberOfWords() * 100;
        return Math.round((0.0588 * l - 0.296 * s - 15.8) * 100.0) / 100.0;
    }

    //This method determines which age range will be able to read the text.
    private static int readableAge(int score) {
        int [] age = {6,7,9,10,11,12,13,14,15,16,17,18,24,25};
        return age[score -1];
    }
}

