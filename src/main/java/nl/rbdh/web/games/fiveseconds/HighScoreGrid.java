package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import nl.rbdh.web.games.fiveseconds.backend.Player;
import nl.rbdh.web.games.fiveseconds.backend.PlayerService;

@UIScope
@SpringComponent
public class HighScoreGrid extends VerticalLayout {
    private PlayerService playerService = PlayerService.getInstance();
    private Player player;
    private MainView mainView;
    private Grid<Player> grid = new Grid<>();


    public HighScoreGrid() {
        grid.setSizeUndefined();

        grid.addColumn(Player::getVoornaam).setHeader("Voornaam");
//           grid.addColumn(Player::getRandomNaam).setHeader("Nickname");
        grid.addColumn(Player::getScore).setHeader("Score");
         updateHighScoreList();


        add(grid);


    }
    public void updateHighScoreList() {
        grid.setItems(playerService.findAll());
    }
}

