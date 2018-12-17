package nl.rbdh.web.games.fiveseconds.backend;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Serializable, Cloneable {
    Long id;
    String voornaam = "";
    String randomNaam;
    int score = 0;

    InsultService insultService = InsultService.getInstance();

    public Player() {
        setRandomNaam(insultService.getRandomInsult());
    }

    public Player(String voornaam) {
        setVoornaam(voornaam);
        setRandomNaam(insultService.getRandomInsult());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getRandomNaam() {
        return randomNaam;
    }

    private void setRandomNaam(String randomNaam) {
        this.randomNaam = randomNaam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof Player && obj.getClass().equals(getClass())) {
            return this.id.equals(((Player) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (id == null ? 0 : id.hashCode());
        return hash;
    }

    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }


    @Override
    public String toString() {
        StringBuilder volledigeNaam = new StringBuilder();
        volledigeNaam.append(voornaam + " " + randomNaam);
        return volledigeNaam.toString();
    }
}
