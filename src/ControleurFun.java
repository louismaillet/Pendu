import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurFun implements EventHandler<ActionEvent> {
    private Pendu vue;
    private boolean mode;

    public ControleurFun(Pendu vue, boolean mode) {
        this.vue = vue;
        this.mode = mode;
    }

    @Override
    public void handle(ActionEvent event) {
        vue.modeFun(mode);
    }
}