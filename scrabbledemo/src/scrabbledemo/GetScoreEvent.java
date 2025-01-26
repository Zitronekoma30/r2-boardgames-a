package scrabbledemo;

import com.boardgame.core.GameManager;
import com.boardgame.core.Player;
import com.boardgame.core.model.event.Event;

public class GetScoreEvent extends Event {

    public GetScoreEvent(String name) {
        super(name);
    }

    @Override
    public String onTrigger(Player player, String s) {
        return String.valueOf(((ScrabblePlayer) player).getScore());
    }
}
