package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import nl.rbdh.web.games.fiveseconds.backend.PlayerService;

public class DefaultMenu extends HorizontalLayout {

    private PlayerService playerService = PlayerService.getInstance();


    public DefaultMenu(){
        setSizeUndefined();
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.START);
        Button players = new Button("Players");
        players.addClickListener(e -> {
            players.getUI().ifPresent(ui -> ui.navigate(""));
        });

        Button testPlayers = new Button("TestData");
        players.addClickListener(e -> {
            playerService.loadTestData();
        });

        add(players,testPlayers);
    }
}

