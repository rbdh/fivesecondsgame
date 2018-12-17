package nl.rbdh.web.games.fiveseconds.backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private static FileReader instance = null;

    public FileReader() {

    }

    public static synchronized FileReader getInstance() {
        if (instance == null) {
            instance = new FileReader();
        }
        return instance;
    }

    public ArrayList<Question> readText(String filename) {

        File file = new File(getClass().getResource(filename).getFile());
        ArrayList<Question> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                list.add(new Question(s.nextLine().toLowerCase()));
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }

    public ArrayList<String> readTextasString(String filename) {

        File file = new File(getClass().getResource(filename).getFile());
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }


}