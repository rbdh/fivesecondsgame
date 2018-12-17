package nl.rbdh.web.games.fiveseconds.backend;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class GameService {
    private static GameService instance = null;
    private static final Logger LOGGER = Logger.getLogger(GameService.class.getName());


    public GameService() {
        QuestionService questionService = QuestionService.getInstance();
        PlayerService playerService = PlayerService.getInstance();

    }

    public static synchronized GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

}
