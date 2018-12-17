package nl.rbdh.web.games.fiveseconds.backend;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PlayerService {

    private static PlayerService instance = null;
    private static final Logger LOGGER = Logger.getLogger(PlayerService.class.getName());

    private long nextId = 0;

    private int turnId = 0;

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    private final HashMap<Long, Player> playerHashMap = new HashMap<>();
    List<Player> playerList = new ArrayList<>();

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public PlayerService() {
        InsultService insultService = InsultService.getInstance();
        FileReader fileReader = FileReader.getInstance();
        insultService.setInsultList(fileReader.readTextasString("/insult.txt"));
//        load test data
//        loadTestData();
    }

    public static synchronized PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    /**
     * @return all available Customer objects.
     */
    public synchronized List<Player> findAll() {
        return findAll(null);
    }

    /**
     * Finds all Customer's that match given filter.
     *
     * @param stringFilter filter that returned objects should match or null/empty string
     *                     if all objects should be returned.
     * @return list a Customer objects
     */
    public synchronized List<Player> findAll(String stringFilter) {
        ArrayList<Player> arrayList = new ArrayList<>();
        for (Player contact : playerHashMap.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Player>() {

            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    /**
     * Finds all Customer's that match given filter and limits the resultset.
     *
     * @param stringFilter filter that returned objects should match or null/empty string
     *                     if all objects should be returned.
     * @param start        the index of first result
     * @param maxresults   maximum result count
     * @return list a Customer objects
     */
    public synchronized List<Player> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Player> arrayList = new ArrayList<>();
        for (Player contact : playerHashMap.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Player>() {

            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    /**
     * Persists or updates customer in the system. Also assigns an identifier
     * for new Customer instances.
     *
     * @param entry
     */
    public synchronized void save(Player entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
            return;
        }
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Player) entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        playerHashMap.put(entry.getId(), entry);
        playerList.add(entry);
    }

/*
    public void addPlayer(Player player) {
        playerList.add(player);
    }
*/
    public void removePlayer(Player player) {
        playerList.removeIf(s -> s.getVoornaam().equals(player.getVoornaam()));
    }


    public Player getRandomPlayer() {
        List<Player> playerList = new ArrayList<Player>(playerHashMap.values());
        int randomIndex = new Random().nextInt(playerList.size());

        return playerList.get(randomIndex);
    }

    public Player getNextPlayer() {
        Player player = null;
        if (turnId > playerList.size() - 1) {
            setTurnId(0);
        }
        player = playerList.get(turnId);
        setTurnId(turnId + 1);
        return player;


    }

    /**
     * @return the amount of all customers in the system
     */
    public synchronized long count() {
        return playerHashMap.size();
    }


    /**
     * Deletes a customer from a system
     *
     * @param value the Customer to be deleted
     */
    public synchronized void delete(Player value) {
        playerHashMap.remove(value.getId());
        removePlayer(value);
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        int maxCheck = (max == 1) ? 0 : max;
        return r.ints(min, (maxCheck)).findFirst().getAsInt();

    }

    public void loadTestData() {
        save(new Player("Anjuli"));
        save(new Player("Deepa"));
        save(new Player("Fano"));
        save(new Player("Shanand"));
    }

    public void resetScore() {
        for(Map.Entry<Long, Player> playerSel : playerHashMap.entrySet()) {
            playerSel.getValue().setScore(0);
        }
    }

    public void clearPlayerList() {
        playerHashMap.clear();
        playerList.clear();
    }

}
