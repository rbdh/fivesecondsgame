package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import nl.rbdh.web.games.fiveseconds.backend.Player;
import nl.rbdh.web.games.fiveseconds.backend.PlayerService;
import nl.rbdh.web.games.fiveseconds.backend.QuestionService;

import java.util.TimerTask;


@Route(value = "game")
@PageTitle("5SG")
@StyleSheet("/VAADIN/animate.css")
public class GameView extends VerticalLayout {

    private PlayerService playerService = PlayerService.getInstance();
    private QuestionService questionService = QuestionService.getInstance();
    private DefaultMenu menuBar = new DefaultMenu();
    private HighScoreGrid highScoreGrid = new HighScoreGrid();

    private Player currentPlayer = null;
    public static int interval;


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

        player.getStyle().set("text-transform", "small-caps").set("font-size", "16px").set("letter-spacing", "2px").set("font-weight", "300");
        if (currentPlayer != null) {
            updatePlayerName();
        }

        HorizontalLayout mainLayout = new HorizontalLayout(highScore, gameLayout);
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.START);
        highScore.setAlignItems(Alignment.START);
        highScore.add(highScoreGrid);

        add(mainLayout);

        Label beurt = new Label("Beurt");
        beurt.getStyle().set("text-transform", "uppercase").set("font-size", "16px").set("letter-spacing", "5px").set("font-weight", "400");

        gameLayout.add(beurt, player, loadQuestions());
        gameLayout.setPadding(true);
        gameLayout.setMargin(false);


    }

    public VerticalLayout loadQuestions() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        question.getStyle().set("text-transform", "small-caps").set("font-size", "48px").set("letter-spacing", "5px").set("font-weight", "700");
        VerticalLayout textLayout = new VerticalLayout(question);
//        textLayout.setHeight("300px");

        Button easyButton = new Button("Makkelijk");
        Button hardButton = new Button("Moeilijk");
        easyButton.setWidth("150px");
        easyButton.setHeight("75px");
        hardButton.setWidth("150px");
        hardButton.setHeight("75px");

        HorizontalLayout easyHardQuestion = new HorizontalLayout(easyButton, hardButton);


        Button ja = new Button("", VaadinIcon.CHECK_CIRCLE_O.create());
        ja.setWidth("75px");
        ja.setHeight("75px");
        ja.setEnabled(false);
        ja.getStyle().set("font-size", "42px").set("padding", "0px");
        ja.getStyle().set("background-color", "#c8ffdb");

        Button nee = new Button("", VaadinIcon.CLOSE_CIRCLE_O.create());
        nee.setWidth("75px");
        nee.setHeight("75px");
        nee.setEnabled(false);
        nee.getStyle().set("font-size", "42px").set("padding", "0px");
        nee.getStyle().set("background-color", "#ff9c9c");

        ja.addClickListener(e -> {
            updateScore(true);
            ja.setEnabled(false);
            nee.setEnabled(false);
            easyButton.setEnabled(true);
            hardButton.setEnabled(true);
        });
        nee.addClickListener(e -> {
            updateScore(false);
            ja.setEnabled(false);
            nee.setEnabled(false);
            easyButton.setEnabled(true);
            hardButton.setEnabled(true);
        });

        easyButton.addClickListener(e -> {
            question.setText(questionService.getRandomQuestion(questionService.getEasyQuestionList()));
            ja.setEnabled(true);
            nee.setEnabled(true);
            easyButton.setEnabled(false);
            hardButton.setEnabled(false);
            getTimer(5);
        });
        hardButton.addClickListener(e -> {
            question.setText(questionService.getRandomQuestion(questionService.getHardQuestionList()));
            ja.setEnabled(true);
            nee.setEnabled(true);
            easyButton.setEnabled(false);
            hardButton.setEnabled(false);
        });
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

    private void getTimer(int timer) {

        interval = timer;
        int delay = 1000;
        int period = 1000;
        final java.util.Timer time = new java.util.Timer();
        System.out.println(interval);
        time.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (interval == 0) {
                    System.out.println("work finished");
                    time.cancel();
                    time.purge();
                } else {
                    System.out.println(setInterval());
                    showTimerNotification();
                }
            }
        }, delay, period);


    }

    private static int setInterval() {

        return --interval;
    }

    private void showTimerNotification() {
        Notification.show(String.valueOf(interval), 1, Notification.Position.MIDDLE);


    }


}
