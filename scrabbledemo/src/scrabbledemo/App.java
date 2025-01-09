package scrabbledemo;

import com.boardgame.core.GameManagerFactory;
import com.boardgame.core.GameServer;

import java.io.File;
import java.io.IOException;

class App{
    public static void main(String[] args) {
        GameBuilder builder = new GameBuilder();
        String frontEndPath = new File("src/view").getAbsolutePath();
        GameServer server = new GameServer(builder, "localhost", 8080, frontEndPath);

        try {
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}