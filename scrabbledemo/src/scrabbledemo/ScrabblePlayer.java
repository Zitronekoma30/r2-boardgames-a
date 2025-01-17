package scrabbledemo;

import com.boardgame.core.Player;

public class ScrabblePlayer extends Player {
    private int score = 0;

    public ScrabblePlayer(String name, int[] directions) {
        super(name, directions);
    }

    public int getScore() {
        return score;
    }

    public int addScore(int amount){
        score += amount;
        return score;
    }
}
