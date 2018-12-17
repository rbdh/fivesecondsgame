package nl.rbdh.web.games.fiveseconds.backend;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class InsultService {

    private static InsultService instance = null;

    public InsultService(){

    }

    public static synchronized InsultService getInstance() {
        if (instance == null) {
            instance = new InsultService();
        }
        return instance;
    }

    ArrayList<String> insultList = new ArrayList();

    public ArrayList<String> getInsultList() {
        return insultList;
    }

    public void setInsultList(ArrayList<String> insultList) {
        this.insultList = insultList;
    }

    public String getRandomInsult() {
        StringBuilder volledigeVraag = new StringBuilder();
        volledigeVraag.append(insultList.get(getRandomNumberInRange(0, insultList.size())));
        return volledigeVraag.toString();
    }

    private int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max)).findFirst().getAsInt();
    }
}
