import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class ControleurLangue implements EventHandler<ActionEvent> {
    private Pendu vue;
    private ComboBox<String> comboLangue;

    public ControleurLangue(Pendu vue, ComboBox<String> comboLangue) {
        this.vue = vue;
        this.comboLangue = comboLangue;
    }

    @Override
    public void handle(ActionEvent event) {
        String langue = comboLangue.getValue();
        vue.changeLangue(langue);
    }
}