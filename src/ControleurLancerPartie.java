import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une partie
 */
public class ControleurLancerPartie implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param p vue du jeu
     */
    public ControleurLancerPartie(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
    String motCrypte = this.modelePendu.getMotCrypte();
    boolean partieEnCours = motCrypte.contains("*") && this.modelePendu.getNbEssais() > 0;
    if (!partieEnCours || this.modelePendu.getMotATrouve().equals(this.modelePendu.getMotCrypte())) {
        this.vuePendu.lancePartie();
        this.vuePendu.majAffichage();
    } else {
        Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait();
        if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
            this.vuePendu.lancePartie();
            this.vuePendu.majAffichage();
        }
    }
    }
}
