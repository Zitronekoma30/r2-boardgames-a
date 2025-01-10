package scrabbledemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class ScrabbleWordValidator {
    private Set<String> validWords;

    public ScrabbleWordValidator(String filePath) throws Exception {
        validWords = new HashSet<>();
        loadWordList(filePath);
    }

    private void loadWordList(String filePath) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                validWords.add(line.trim().toUpperCase());
            }
        }
    }

    public boolean isValidWord(String word) {
        return validWords.contains(word.toUpperCase());
    }

    public static void main(String[] args) {
        try {
            ScrabbleWordValidator validator = new ScrabbleWordValidator("path/to/scrabble-words.txt");
            System.out.println(validator.isValidWord("example")); // Replace with actual word to test
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}