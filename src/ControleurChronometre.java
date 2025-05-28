import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre.
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {

    /**
     * Temps enregistré lors du dernier événement.
     */
    private long tempsPrec;

    /**
     * Temps écoulé depuis le début de la mesure.
     */
    private long tempsEcoule;

    /**
     * Référence au chronomètre à mettre à jour.
     */
    private Chronometre chrono;

    /**
     * Constructeur du contrôleur du chronomètre.
     * Le modèle du chronomètre est intégré ici.
     * @param chrono Vue du chronomètre
     */
    public ControleurChronometre(Chronometre chrono) {
        this.chrono = chrono;
        this.tempsPrec = System.currentTimeMillis();
        this.tempsEcoule = 0;
    }

    /**
     * Appelé toutes les 50 ms par la KeyFrame.
     * Calcule le temps écoulé et met à jour l'affichage.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        long maintenant = System.currentTimeMillis();
        long delta = maintenant - tempsPrec;
        tempsEcoule += delta;
        chrono.setTime(tempsEcoule);
        tempsPrec = maintenant;
    }

    /**
     * Remet la durée écoulée à 0.
     */
    public void reset() {
        this.tempsEcoule = 0;
        this.tempsPrec = System.currentTimeMillis();
        chrono.setTime(0);
    }
}
