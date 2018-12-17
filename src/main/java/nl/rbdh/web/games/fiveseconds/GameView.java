package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import nl.rbdh.web.games.fiveseconds.backend.Player;
import nl.rbdh.web.games.fiveseconds.backend.PlayerService;
import nl.rbdh.web.games.fiveseconds.backend.QuestionService;


@Route(value = "game")
@PageTitle("5SG")
public class GameView extends VerticalLayout {

    private PlayerService playerService = PlayerService.getInstance();
    private QuestionService questionService = QuestionService.getInstance();
    private DefaultMenu menuBar = new DefaultMenu();
    private HighScoreGrid highScoreGrid = new HighScoreGrid();

    private Player currentPlayer = null;

    Label player = new Label("");

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private Label question = new Label("");

    public GameView() {

        if (playerService.findAll().isEmpty()) {
            getUI().ifPresent(ui -> ui.navigate(""));
        }

        if (!playerService.findAll().isEmpty()) {
            setCurrentPlayer(playerService.getNextPlayer());
        }

        setSizeFull();
        setPadding(true);


        add(menuBar);
        VerticalLayout gameLayout = new VerticalLayout();
        VerticalLayout highScore = new VerticalLayout();
        highScore.setSizeUndefined();
        gameLayout.setSizeFull();
        highScore.setWidth("20%");
        gameLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        player.getStyle().set("text-transform", "small-caps").set("font-size", "32px").set("letter-spacing", "2px").set("font-weight", "300");
        if (currentPlayer != null) {
            updatePlayerName();
        }

        HorizontalLayout mainLayout = new HorizontalLayout(highScore, gameLayout);
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.START);
        highScore.setAlignItems(Alignment.START);
        highScore.add(highScoreGrid);

        add(mainLayout);

        gameLayout.add(player, loadQuestions());


    }

    public VerticalLayout loadQuestions() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        Label beurt = new Label("Beurt:");
        beurt.getStyle().set("text-transform", "uppercase").set("font-size", "36px").set("letter-spacing", "5px").set("font-weight", "700");

        question.getStyle().set("text-transform", "small-caps").set("font-size", "48px").set("letter-spacing", "5px").set("font-weight", "700");
        VerticalLayout textLayout = new VerticalLayout(beurt, question);
        textLayout.setHeight("300px");

        Button easyButton = new Button("Makkelijk");
        Button hardButton = new Button("Moeilijk");
        easyButton.setWidth("150px");
        easyButton.setHeight("75px");
        hardButton.setWidth("150px");
        hardButton.setHeight("75px");

        easyButton.addClickListener(e -> question.setText(questionService.getRandomQuestion(questionService.getEasyQuestionList())));
        hardButton.addClickListener(e -> question.setText(questionService.getRandomQuestion(questionService.getHardQuestionList())));
        HorizontalLayout easyHardQuestion = new HorizontalLayout(easyButton, hardButton);


        Button ja = new Button("", VaadinIcon.CHECK_CIRCLE_O.create());
        ja.setWidth("75px");
        ja.setHeight("75px");
        ja.getStyle().set("font-size", "42px").set("padding", "0px");
        ja.getStyle().set("background-color", "#c8ffdb");
        ja.addClickListener(e -> updateScore(true));
        Button nee = new Button("", VaadinIcon.CLOSE_CIRCLE_O.create());
        nee.setWidth("75px");
        nee.setHeight("75px");
        nee.getStyle().set("font-size", "42px").set("padding", "0px");
        nee.getStyle().set("background-color", "#ff9c9c");
        nee.addClickListener(e -> updateScore(false));
        HorizontalLayout puntenJa = new HorizontalLayout(ja, nee);

        verticalLayout.add(question, easyHardQuestion, puntenJa);

        return verticalLayout;
    }

    private void updateScore(boolean correct) {
        if (correct) {
            getCurrentPlayer().setScore(getCurrentPlayer().getScore() + 1);
        }
        highScoreGrid.updateHighScoreList();
        updatePlayer();
        question.setText("");

    }

    private void updatePlayer() {
        setCurrentPlayer(playerService.getNextPlayer());
        updatePlayerName();

    }

    private void updatePlayerName() {
        player.setText(currentPlayer.getVoornaam() + " " + currentPlayer.getRandomNaam());
    }
}
