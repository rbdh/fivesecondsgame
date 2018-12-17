package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import nl.rbdh.web.games.fiveseconds.backend.Player;
import nl.rbdh.web.games.fiveseconds.backend.PlayerService;

@UIScope
@SpringComponent
public class PlayerForm extends FormLayout {
    private TextField firstName = new TextField("Naam");
    private PlayerService service = PlayerService.getInstance();
    private Player player;
    private MainView mainView;
    private Binder<Player> binder = new Binder<>(Player.class);
    private Button save = new Button("Toevoegen");
    private Button delete = new Button("Verwijderen");

    public PlayerForm(MainView mainView) {
        this.mainView = mainView;
        add(firstName);
        binder.bind(firstName, Player::getVoornaam, Player::setVoornaam);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        add(firstName, buttons);
        save.getElement().setAttribute("theme", "primary");
        setPlayer(null);
        save.addClickListener(e -> {
            this.save();
            setPlayer(new Player());
        });
        delete.addClickListener(e -> {
            this.delete();
            setPlayer(new Player());
        });
    }

    public void setPlayer(Player player) {
        this.player = player;
        binder.setBean(player);
        boolean enabled = player != null;
        save.setEnabled(enabled);
        delete.setEnabled(enabled);
        if (enabled) {
            firstName.focus();
        }
    }

    private void delete() {
        service.delete(player);
        mainView.updateList();
        setPlayer(null);
    }

    private void save() {
        service.save(player);
        mainView.updateList();
        setPlayer(null);
    }
}

