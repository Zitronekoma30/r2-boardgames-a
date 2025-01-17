package scrabbledemo;

import com.boardgame.core.Player;

import java.util.*;

public class LetterPouch {
    private static HashMap<Character, Integer> letterCounts;

    static {
        letterCounts = new HashMap<>();
        letterCounts.put('A', 9);
        letterCounts.put('B', 2);
        letterCounts.put('C', 2);
        letterCounts.put('D', 4);
        letterCounts.put('E', 12);
        letterCounts.put('F', 2);
        letterCounts.put('G', 3);
        letterCounts.put('H', 2);
        letterCounts.put('I', 9);
        letterCounts.put('J', 1);
        letterCounts.put('K', 1);
        letterCounts.put('L', 4);
        letterCounts.put('M', 2);
        letterCounts.put('N', 6);
        letterCounts.put('O', 8);
        letterCounts.put('P', 2);
        letterCounts.put('Q', 1);
        letterCounts.put('R', 6);
        letterCounts.put('S', 4);
        letterCounts.put('T', 6);
        letterCounts.put('U', 4);
        letterCounts.put('V', 2);
        letterCounts.put('W', 2);
        letterCounts.put('X', 1);
        letterCounts.put('Y', 2);
        letterCounts.put('Z', 1);
        // letterCounts.put(' ', 2); TODO: Add Blanks
    }

    private Stack<Character> letterStack;

    private void generateLetters() {
        List<Character> letters = new ArrayList<>();
        for (var entry : letterCounts.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                letters.add(entry.getKey());
            }
        }

        Collections.shuffle(letters);
        letterStack = new Stack<>();
        letterStack.addAll(letters);
    }

    public LetterPiece getNextLetter(Player player){
        var letter = letterStack.pop();
        if (letterStack.isEmpty()) player.endGame(); // TODO: look this over some more
        return new LetterPiece(player, letter);
    }
}
