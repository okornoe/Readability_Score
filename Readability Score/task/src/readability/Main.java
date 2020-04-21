package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    static String str = "";
    public static void main(String[] args) throws IOException {
        str = readFile(args[0]);
        printAll();
    }

    private static void printAll() {
        System.out.println("Words: " + numberOfWords());
        System.out.println("Sentences: " + numberOfSentences());
        System.out.println("Characters: " + numberOfCharacters());
        System.out.print("The score is: ");
        System.out.format("%.2f", calculateReadableScore());
        System.out.println();
        System.out.println("This text should be understood by " + readableAge((int) Math.ceil(calculateReadableScore())) + " year olds.");
    }

    //calculates number of syllables in a file

    /**
     * 1. Count the number of vowels in the word.
     * 2. Do not count double-vowels.
     *    (e.g "rain" had 2 vowels but is only 1 syllable)
     * 3. If the last letter in the word is 'e' do not count it as a vowel
     *    (e.g "side" is 1 syllable)
     * 4. If at the end it turns out that the word contains 0 vowels,
     *    then consider this word as 1-syllable.
     */
    private static void numberOfSyllables () {

    }

    //reads the file
    private static String readFile(String fileName) throws IOException {
        //String path = "/moocs/jetbrains/Readability Score/in2.txt";
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
    private static float calculateReadableScore() {
        final float constVal4_71 = 4.71f;
        final float constVal0_5 = 0.5f;
        final float constVal321_43 = 21.43f;
        float numCharDivideNumWords;
        float numWordsDivideNumSentence;
        numCharDivideNumWords = (float) numberOfCharacters() / numberOfWords();
        numWordsDivideNumSentence = (float) numberOfWords() / numberOfSentences();
        return (constVal4_71 * numCharDivideNumWords) + ((constVal0_5 * numWordsDivideNumSentence) - constVal321_43);
    }

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
}

