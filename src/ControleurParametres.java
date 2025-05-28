import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurParametres implements EventHandler<ActionEvent> {
    private Pendu vue;

    public ControleurParametres(Pendu vue) {
        this.vue = vue;
    }

    @Override
    public void handle(ActionEvent event) {
        vue.modeParametres();
        

    }
}