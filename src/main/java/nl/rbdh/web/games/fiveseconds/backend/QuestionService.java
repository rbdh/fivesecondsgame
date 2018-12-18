package nl.rbdh.web.games.fiveseconds.backend;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class QuestionService {

    private static QuestionService instance = null;

    public QuestionService() {
        FileReader fileReader = FileReader.getInstance();
        setEasyQuestionList(fileReader.readText("/easy.txt"));
        setHardQuestionList(fileReader.readText("/hard.txt"));
    }

    public static synchronized QuestionService getInstance() {
        if (instance == null) {
            instance = new QuestionService();
        }
        return instance;
    }

    ArrayList<Question> easyQuestionList = new ArrayList();
    ArrayList<Question> hardQuestionList = new ArrayList();

    public ArrayList<Question> getEasyQuestionList() {
        return easyQuestionList;
    }

    public void setEasyQuestionList(ArrayList<Question> easyQuestionList) {
        this.easyQuestionList = easyQuestionList;
    }

    public ArrayList<Question> getHardQuestionList() {
        return hardQuestionList;
    }

    public void setHardQuestionList(ArrayList<Question> hardQuestionList) {
        this.hardQuestionList = hardQuestionList;
    }

    public String getRandomQuestion(ArrayList<Question> questionList) {
        StringBuilder volledigeVraag = new StringBuilder();
        volledigeVraag.append("Noem drie ");
        volledigeVraag.append(questionList.get(getRandomNumberInRange(0, questionList.size())).toString());
        return volledigeVraag.toString();
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max)).findFirst().getAsInt();

    }
}
