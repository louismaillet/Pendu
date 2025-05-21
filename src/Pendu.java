import javafx.scene.control.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.scene.control.ButtonType ;
import java.util.List;

import javax.swing.border.Border;

import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.*;



/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    private Button boutonInfo;

    @Override
    public void init() {
        this.boutonInfo = new Button();
        this.boutonParametres = new Button();
        this.boutonMaison = new Button();
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.panelCentral = this.fenetreAccueil();
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);

        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        BorderPane banniere = new BorderPane();
        banniere.setStyle("-fx-background-color:rgb(188, 163, 255);"); // rose violet clair
        Label titre = new Label("Jeu Du Pendu");       
        titre.setFont(new Font("", 48));
        titre.setPadding(new Insets(10)); 

        HBox boutons = new HBox();
        ImageView imageHome = new ImageView(new Image("file:./img/home.png"));     
        ImageView imageInfo = new ImageView(new Image("file:./img/info.png"));    
        ImageView imageParametre = new ImageView(new Image("file:./img/parametres.png")); 
        imageHome.setFitHeight(50); //changez la taille de l'image
        imageHome.setFitWidth(50);
        imageInfo.setFitHeight(50);
        imageInfo.setFitWidth(50);
        imageParametre.setFitHeight(50);
        imageParametre.setFitWidth(50);
        this.boutonMaison.setGraphic(imageHome);
        this.boutonParametres.setGraphic(imageParametre);
        this.boutonInfo.setGraphic(imageInfo);
        boutons.setPadding(new Insets(10));
        boutons.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutonInfo);

        banniere.setLeft(titre);
        banniere.setRight(boutons);

        return banniere;
    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    // private Pane fenetreJeu(){
        // A implementer
        // Pane res = new Pane();
        // return res;
    // }

    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
    private BorderPane fenetreAccueil(){
        Button bJouer = new Button("Lancer une partie");


        VBox choixNiveau = new VBox();
        choixNiveau.setPadding(new Insets(10, 10, 10, 10));
        choixNiveau.setSpacing(10);
        
        RadioButton niveauFacile = new RadioButton("Facile");
        RadioButton niveauMoyen = new RadioButton("Moyen");
        RadioButton niveauDifficile = new RadioButton("Difficile");
        RadioButton niveauExpert = new RadioButton("Expert");

        ToggleGroup group = new ToggleGroup();
        niveauFacile.setToggleGroup(group);
        niveauMoyen.setToggleGroup(group);
        niveauDifficile.setToggleGroup(group);
        niveauExpert.setToggleGroup(group);

        choixNiveau.getChildren().addAll(niveauFacile, niveauMoyen, niveauDifficile, niveauExpert);

        TitledPane niveauDeDifficulte = new TitledPane("Niveau de difficulté", choixNiveau);
        niveauDeDifficulte.setCollapsible(false); //permet de ne pas derouler 
        bJouer.setPadding(new Insets(10));
        VBox centre = new VBox(bJouer, niveauDeDifficulte);
        centre.setSpacing(20);
        centre.setPadding(new Insets(10));

        BorderPane milieu = new BorderPane();

        milieu.setCenter(centre);
        return milieu;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"./img/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        // A implementer
    }
    
    public void modeJeu(){
        // A implementer
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
