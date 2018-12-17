package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import nl.rbdh.web.games.fiveseconds.backend.*;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;


/**
 * The main view contains a button and a click listener.
 */
@Route
@PageTitle("Spelers Pagina")
public class MainView extends VerticalLayout {
    private PlayerService playerService = PlayerService.getInstance();

    private Grid<Player> grid = new Grid<>();
    private TextField filterText = new TextField();
    private DefaultMenu menuBar = new DefaultMenu();
    Button startButton = new Button("Start");


    public MainView() {
        startButton.setEnabled(false);
        setWidth("80%");
        setMargin(false);
        setPadding(true);

        PlayerForm form = new PlayerForm(this);
        form.setPlayer(new Player());
        filterText.setPlaceholder("Filter by name...");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        Button clearFilterTextBtn = new Button(
                new Icon(VaadinIcon.CLOSE_CIRCLE));

        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        HorizontalLayout filtering = new HorizontalLayout(filterText,
                clearFilterTextBtn);

        Button clearPlayerList = new Button("Reset");
        clearPlayerList.addClickListener(e -> {
            playerService.clearPlayerList();
            updateList();
        });

        Button clearScore = new Button("Reset Score");
        clearScore.addClickListener(e -> {
            playerService.resetScore();
            updateList();
        });

        Button addCustomerBtn = new Button("Speler toevoegen");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setPlayer(new Player());
        });

        add(menuBar);
//        add(filtering);

        HorizontalLayout crudActions = new HorizontalLayout(clearScore, clearPlayerList);

        VerticalLayout toolbar = new VerticalLayout(form);


        grid.setWidth("500px");

        grid.addColumn(Player::getVoornaam).setHeader("Voornaam");
        grid.addColumn(Player::getRandomNaam).setHeader("Nickname");
        grid.addColumn(Player::getScore).setHeader("Score");

        VerticalLayout main = new VerticalLayout(grid, crudActions);
        main.setAlignItems(FlexComponent.Alignment.START);
        main.setSizeUndefined();
        main.setHeight("500px");

        add(toolbar, main);
//        add(filtering, main);
        setHeight("100vh");
        updateList();

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setPlayer(event.getValue());
        });
        Icon startIcon = VaadinIcon.STAR.create();
        startButton.setIcon(startIcon);
        startButton.setSizeFull();
        startButton.addClickListener(e -> {
            startButton.getUI().ifPresent(ui -> ui.navigate("game"));
        });
        startButton.getStyle().set("text-transform", "uppercase").set("font-size", "32px").set("letter-spacing", "5px").set("font-weight", "700");
        startButton.setWidth("200px");
        HorizontalLayout start = new HorizontalLayout(startButton);
        add(start);

    }

    public void updateList() {
        /**
         * Note that filterText.getValue() might return null; in this case, the backend
         * takes care of it for us
         */
        grid.setItems(playerService.findAll());
        if (playerService.findAll().size() > 0) {
            startButton.setEnabled(true);
            startButton.getStyle().set("text-transform", "uppercase").set("font-size", "32px").set("letter-spacing", "5px").set("font-weight", "700").set("background-color", "#c8ffdb");
        } else {
            startButton.setEnabled(false);
            startButton.getStyle().set("text-transform", "uppercase").set("font-size", "32px").set("letter-spacing", "5px").set("font-weight", "700").set("background-color", "#ff9c9c");
        }
    }

}
