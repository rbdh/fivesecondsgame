package nl.rbdh.web.games.fiveseconds.backend;

public class Question {

    Integer id;
    String question;

    public Question () {

    }

    public Question (String question) {
        setQuestion(question);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return question;
    }


}
