package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ToDo's
 * work on the age readable.
 * work on the average age able to understand the text.
 * refactor the syllable and the poly syllable method to avoid repitition.
 */

public class Main {

    static String str = "";
    static String path = "/moocs/jetbrains/Readability Score/newTest.txt";
    public static void main(String[] args) throws IOException {
        //str = readFile(args[0]); // uncomment this line when you want pass the file name from the console
        str = readFile(path);  // use this when you want to pass the file path to the program directly.

        print();
    }

    private static void print() {
        System.out.println("Words: " + numberOfWords());
        System.out.println("Sentences: " + numberOfSentences());
        System.out.println("Characters: " + numberOfCharacters());
        System.out.println("Syllables: " + syllables());
        System.out.println("Polysyllables: " + polySyllables());
        scoreSelection();

    }

    private static void printARI(){
        String readabilityType = "Automated Readability Index: ";
        double scoreValue = authomaticReadabilityIndex();
        String readableAge = readableAge((int) authomaticReadabilityIndex());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printFK() {
        String readabilityType = "Flesch-Kincaid readability tests: ";
        double scoreValue = fleschKincaidReadTest();
        String readableAge = readableAge((int)fleschKincaidReadTest());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printSMOG() {
        String readabilityType = "Simple Measure of Gobbledygook: ";
        double scoreValue = simpleMeasureOfGobbledygook();
        String readableAge = readableAge((int)simpleMeasureOfGobbledygook());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printCL() {
        String readabilityType = "Coleman-Liau index: ";
        double scoreValue = colemanLauIndex();
        String readableAge = readableAge((int)colemanLauIndex());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printAll() {
        printARI();
        printFK();
        printSMOG();
        printCL();
    }

    private static void scoreSelection() {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        String scoreType = new Scanner(System.in).next();

        switch (scoreType) {
            case "ARI":
                printARI();
                break;
            case "FK":
                printFK();
                break;
            case "SMOG":
                printSMOG();
                break;
            case "CL":
                printCL();
                break;
            case "all":
                printAll();
                break;
            default:
                System.out.println("unknown score type selection");
                break;
        }
    }

    //calculates the number syllables in a word.
    private static int syllables(){
        String[] words = str.split("\\s+");
        int sum = 0;
        for (String word : words) {
            final Pattern p = Pattern.compile("([ayeiou]+)");
            String lowerCase = word.toLowerCase();
            if (lowerCase.endsWith(".") || lowerCase.endsWith("!") || lowerCase.endsWith("?") || lowerCase.endsWith(",")) {
                lowerCase = lowerCase.substring(0,lowerCase.length()-1);
            }
            final Matcher m = p.matcher(lowerCase);
            int count = 0;
            while (m.find())
                count++;
            if (lowerCase.endsWith("e"))
                count--;
            count = count == 0 ? 1 : count;
            sum = sum + count;
        }
        return sum;
    }

    //calculates the number of syllables in the word greater than two
    private static int polySyllables() {
        int pSyllable = 0;
        //BufferedWriter writer = new BufferedWriter(new FileWriter("/moocs/jetbrains/Readability Score/debug_ploySyllable.txt"));
        String[] words = str.split("\\s+");

        for (String word : words) {
            final Pattern p = Pattern.compile("([ayeiou]+)");
            String lowerCase = word.toLowerCase();
            if (lowerCase.endsWith(".") || lowerCase.endsWith("!") || lowerCase.endsWith("?") || lowerCase.endsWith(",")) {
                lowerCase = lowerCase.substring(0,lowerCase.length()-1);
            }
            final Matcher m = p.matcher(lowerCase);
            int count = 0;
            while (m.find())
                count++;
            if (lowerCase.endsWith("e"))
                count--;

            if (count > 2 ) {
                pSyllable = pSyllable + 1;
            }
        }
        return pSyllable;
    }

    //calculates the Simple Measure Of Gobbledygook.
    private static double simpleMeasureOfGobbledygook() {

        return Math.round((1.043 * Math.sqrt(polySyllables() * ((double)30 / numberOfSentences())) + 3.1291) * 100.0 )/100.0;
    }

    //calculates the coleman-Lau Index
    private static double colemanLauIndex () {
        double l = ((double) numberOfCharacters() / (double) numberOfWords()) * 100;
        double s = (double) numberOfSentences() / (double) numberOfWords() * 100;
        return Math.round((0.0588 * l - 0.296 * s - 15.8) * 100.0) / 100.0;
    }

    //reads the file
    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    //calculates the number of sentences in the file
    private static int numberOfSentences()  {
        String[] s = str.replaceAll("\\s+", "").split("[!?.]");
        return s.length;
    }

    //calculates the number of words in the file
    private static int numberOfWords(){
        String[] words = str.split("\\s+");
        return words.length;
    }

    //calculates the number characters in the file
    private static int numberOfCharacters()  {
        String words = str.replaceAll("\\s+", "");
        return words.toCharArray().length;
    }

    //calculates the score of the text.
    private static double authomaticReadabilityIndex() {
        final double constVal4_71 = 4.71;
        final double constVal0_5 = 0.5;
        final double constVal321_43 = 21.43;
        float numCharDivideNumWords;
        float numWordsDivideNumSentence;
        numCharDivideNumWords = (float) numberOfCharacters() / numberOfWords();
        numWordsDivideNumSentence = (float) numberOfWords() / numberOfSentences();
        return  Math.round((((constVal4_71 * numCharDivideNumWords) + ((constVal0_5 * numWordsDivideNumSentence) - constVal321_43)) * 100.0))/100.0;
    }

    //work to be done here to return maximum age.
    //This method determines which age range will be able to read the text.
    private static String readableAge(int score) {
        switch (score) {
            case 1:
                return "5-6";
            case 2:
                return "6-7";
            case 3:
                return "7-9";
            case 4:
                return "9-10";
            case 5:
                return "10-11";
            case 6:
                return "11-12";
            case 7:
                return "12-13";
            case 8:
                return "13-14";
            case 9:
                return "14-15";
            case 10:
                return "15-16";
            case 11:
                return "16-17";
            case 12:
                return "17-18";
            case 13:
                return "18-24";
            case 14:
                return "24+";
            default:
                return "Unknown";
        }
    }

    //calculates the Flesch Kincaid score.
    private static double fleschKincaidReadTest(){
        return  Math.round((0.39 * ((double) numberOfWords()/numberOfSentences()) + 11.8 *  ((double) syllables()/numberOfWords()) - 15.59) *100.0)/100.0;
    }
}

