package nl.rbdh.web.games.fiveseconds;

import nl.rbdh.web.games.fiveseconds.backend.*;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {

    public static void main(String args[]) {
        QuestionService questionService = QuestionService.getInstance();
        PlayerService playerService = PlayerService.getInstance();

        playerService.save(new Player("Deepa"));
        playerService.save(new Player("Fano"));
        playerService.save(new Player("Shanand"));
        System.out.println(questionService.getRandomQuestion(questionService.getEasyQuestionList()));
        System.out.println(questionService.getRandomQuestion(questionService.getHardQuestionList()));
        playerService.findAll().forEach(s -> System.out.println(s));

    }

}
