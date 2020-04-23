package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    static String str = "";
    static String path = "/moocs/jetbrains/Readability Score/newTest.txt";
    public static void main(String[] args) throws IOException {
        //str = readFile(args[0]); // uncomment this line when you want pass the file name from the console
        str = readFile(path);  // use this when you want to pass the file path to the program directly.

        print();
    }

    private static void print() throws IOException {
        System.out.println("Words: " + numberOfWords());
        System.out.println("Sentences: " + numberOfSentences());
        System.out.println("Characters: " + numberOfCharacters());
        System.out.println("Syllables is: " + syllables());
        System.out.println("Poly Syllables is: " + polySyllables());
        scoreSelection();
    }

    private static void printARI(){
         /*String readabilityType;
         double scoreValue;
         String readableAge;*/
        String readabilityType = "Automated Readability Index: ";
        double scoreValue = calculateReadableScore();
        String readableAge = readableAge((int)calculateReadableScore());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printFK() throws IOException {
         /*String readabilityType;
         double scoreValue;
         String readableAge;*/
        String readabilityType = "Flesch-Kincaid readability tests: ";
        double scoreValue = fleschKincaidReadTest();
        String readableAge = readableAge((int)fleschKincaidReadTest());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printSMOG() {
        /*String readabilityType;
        double scoreValue;
        String readableAge ;*/
        String readabilityType = "Simple Measure of Gobbledygook: ";
        double scoreValue = simpleMeasureOfGobbledygook();
        String readableAge = readableAge((int)simpleMeasureOfGobbledygook());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printCL() {
        //String readabilityType;
        //double scoreValue;
        //String readableAge;
        String readabilityType = "Coleman-Liau index: ";
        double scoreValue = colemanLauIndex();
        String readableAge = readableAge((int)colemanLauIndex());
        System.out.println(readabilityType + scoreValue + " (about " + readableAge + " year olds).");
    }

    private static void printAll() throws IOException {
        printARI();
        printFK();
        printSMOG();
        printCL();
    }

    private static void scoreSelection() throws IOException {
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

    /**
     * 1. Count the number of vowels in the word.
     * 2. Do not count double-vowels.
     *    (e.g "rain" had 2 vowels but is only 1 syllable)
     * 3. If the last letter in the word is 'e' do not count it as a vowel
     *    (e.g "side" is 1 syllable)
     * 4. If at the end it turns out that the word contains 0 vowels,
     *    then consider this word as 1-syllable.
     */
    //count syllables proposed implementation.
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

    private static double simpleMeasureOfGobbledygook() {

        return 1.043 * Math.sqrt(polySyllables() * ((double)30 / numberOfSentences())) + 3.1291;
    }

    private static double colemanLauIndex () {
        double l = ((double) numberOfCharacters() / (double) numberOfWords()) * 100;
        double s = (double) numberOfSentences() / (double) numberOfWords() * 100;
        return 0.0588 * l - 0.296 * s - 15.8;
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
    private static double calculateReadableScore() {
        final double constVal4_71 = 4.71;
        final double constVal0_5 = 0.5;
        final double constVal321_43 = 21.43;
        float numCharDivideNumWords;
        float numWordsDivideNumSentence;
        numCharDivideNumWords = (float) numberOfCharacters() / numberOfWords();
        numWordsDivideNumSentence = (float) numberOfWords() / numberOfSentences();
        return ((double)constVal4_71 * numCharDivideNumWords) + ((constVal0_5 * numWordsDivideNumSentence) - constVal321_43);
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

    private static double fleschKincaidReadTest() throws IOException {
      return  0.39 * ((double) numberOfWords()/numberOfSentences()) + 11.8 *  ((double) syllables()/numberOfWords()) - 15.59;
    }
}

