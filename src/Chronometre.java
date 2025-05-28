import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé.
 */
public class Chronometre extends Text {

    /**
     * Timeline qui va gérer le temps.
     */
    private Timeline timeline;

    /**
     * Fenêtre de temps (intervalle de mise à jour).
     */
    private KeyFrame keyFrame;

    /**
     * Contrôleur associé au chronomètre.
     */
    private ControleurChronometre actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0".
     */
    public Chronometre() {
        super("0:0:0"); // texte initial
        this.setFont(Font.font("Arial", 20));
        this.setTextAlignment(TextAlignment.CENTER);

        // Création du contrôleur
        this.actionTemps = new ControleurChronometre(this);

        // Création de la KeyFrame (appel du contrôleur toutes les 50 ms)
        this.keyFrame = new KeyFrame(Duration.millis(50), this.actionTemps);

        // Création de la Timeline avec cette KeyFrame
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(Animation.INDEFINITE); // répétition infinie
    }

    /**
     * Permet au contrôleur de mettre à jour le texte.
     * La durée est affichée sous la forme m:s:ms
     * @param tempsMillisec durée écoulée en millisecondes
     */
    public void setTime(long tempsMillisec) {
        long minutes = (tempsMillisec / 1000) / 60;
        long secondes = (tempsMillisec / 1000) % 60;
        long millisecondes = tempsMillisec % 1000;
        this.setText(minutes + ":" + secondes + ":" + millisecondes);
    }

    /**
     * Démarre le chronomètre.
     */
    public void start() {
        this.timeline.play();
    }

    /**
     * Arrête le chronomètre.
     */
    public void stop() {
        this.timeline.stop();
    }

    /**
     * Réinitialise le chronomètre à 0.
     */
    public void resetTime() {
        this.timeline.stop();
        this.actionTemps.reset();
    }

    // pour quand on a gagné
    public String getTemps() {
        return this.getText();
    }

}
