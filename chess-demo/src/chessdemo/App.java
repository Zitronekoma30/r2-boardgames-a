package chessdemo;

import chessdemo.pieces.*;
import com.boardgame.core.*;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        GameBuilder builder = new GameBuilder();
        String frontEndPath = new File("view").getAbsolutePath();
        GameServer server = new GameServer(builder, "localhost", 8080, frontEndPath);

        try {
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

