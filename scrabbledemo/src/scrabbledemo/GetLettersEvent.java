package scrabbledemo;

import com.boardgame.core.GameManager;
import com.boardgame.core.Player;
import com.boardgame.core.model.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetLettersEvent extends Event {

    public GetLettersEvent(String name, GameManager manager) {
        super(name, manager);
    }

    @Override
    public String onTrigger(Player player, String str) {
        List<Integer> positions = str.chars()
                .mapToObj(Character::getNumericValue)
                .toList();

        for (int pos : positions){
            ((ScrabblePlayer) player).replaceLetterAt(pos);
        }

        return "";
    }
}
