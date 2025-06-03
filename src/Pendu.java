import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {

    private String langue;

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
    private List<String> couleur;
    

    @Override
    public void init() {
        this.couleur = new ArrayList<>(List.of("FFFFFF", "FF0000", "00FF00", "0000FF")); // exemple de couleurs
        this.langue = "french"; 
        this.boutonInfo = new Button();
        this.boutonParametres = new Button();
        this.boutonMaison = new Button();
        this.modelePendu = new MotMystere("dico/french", 3, 10, MotMystere.FACILE, 10);        
        this.chrono = new Chronometre();
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        this.lesImages = new ArrayList<Image>();
        this.leNiveau = new Text();
        this.chargerImages("./img");
        this.panelCentral = new BorderPane();
        this.pg = new ProgressBar();
        this.pg.setProgress(0);
        this.dessin = new ImageView(new Image("file:./img/pendu0.png"));
        this.clavier = null;
        this.chargerImages("./img");
        this.boutonParametres.setOnAction(new ControleurParametres(this));
        


        //this.panelCentral = this.fenetreJeu();
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
        
        banniere.setStyle("-fx-background-color:rgb(188, 163, 255);"); // rose violet clair bizarre
        Label titre = new Label("Jeu Du Pendu");
        titre.setFont(new Font(48));
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
        this.boutonMaison.setOnAction(new RetourAccueil(this.modelePendu, this));
        this.boutonInfo.setOnAction(new ControleurInfos(this));
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
    private TitledPane leChrono(){
        TitledPane res = new TitledPane("Chronomètre", this.chrono);
        res.setCollapsible(false);
        return res;
    }

    // /**
    // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
    // *         de progression et le clavier
    // */
        private VBox fenetreJeu() {
            this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new ControleurLettres(this.modelePendu, this));
            // Mot mystère en haut
            this.motCrypte.setFont(new Font(30));
            HBox motCrypteBox = new HBox(this.motCrypte);
            motCrypteBox.setAlignment(Pos.CENTER);

            this.clavier.setAlignment(Pos.CENTER);
            
            // Tout dans un VBox centré
            VBox centre = new VBox(40,motCrypteBox, this.dessin, this.pg, this.clavier);
            centre.setAlignment(Pos.TOP_CENTER);
            centre.setFillWidth(true);

            return centre;
}

    private VBox fenetreJeuxDroit() {
        VBox coteDroit = new VBox(50);
        coteDroit.setAlignment(Pos.TOP_RIGHT);
        coteDroit.setPadding(new Insets(20));
        this.leNiveau.setFont(new Font(20));
        Button nouveauMot = new Button("Nouveau mot");
        TitledPane boxChrono = this.leChrono();
        coteDroit.setPrefWidth(250);
        HBox niveauBox = new HBox(this.leNiveau); // obliger de faire un box sinon ca centre pas
        niveauBox.setAlignment(Pos.CENTER);
        HBox boutonBox = new HBox(nouveauMot); // idem
        boutonBox.setAlignment(Pos.CENTER);
        coteDroit.getChildren().addAll(niveauBox, boxChrono, boutonBox);
        nouveauMot.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        nouveauMot.setPrefHeight(50);
        nouveauMot.setPrefWidth(200);
        return coteDroit;
    }
    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
    private BorderPane fenetreAccueil(){
        Button bJouer = new Button("Lancer une partie");
        bJouer.setOnAction(new ControleurLancerPartie(this.modelePendu, this));



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
            File file = new File(repertoire+"/pendu"+i+".png");
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.panelCentral.setCenter(this.fenetreAccueil());
        this.boutonMaison.setDisable(true);
        this.panelCentral.setDisable(false);
        this.panelCentral.setRight(null);
        this.panelCentral.setTop(null);
        this.chrono.resetTime();
    }
    
    public void modeJeu(){
        this.panelCentral.setCenter(this.fenetreJeu());
        this.panelCentral.setRight(this.fenetreJeuxDroit());
        this.boutonMaison.setDisable(false);
        this.boutonParametres.setDisable(true);
    }

    /** lance une partie */
    public void lancePartie(){
    int niveau;
    switch (this.modelePendu.getNiveau()) {
        case 0:
            niveau = MotMystere.FACILE;
            this.leNiveau.setText("le Niveau : Facile");
            break;
        case 1:
            niveau = MotMystere.MOYEN;
            this.leNiveau.setText("le Niveau : Moyen");
            break;
        case 2:
            niveau = MotMystere.DIFFICILE;
            this.leNiveau.setText("le Niveau : Difficile");
            break;
        case 3:
            niveau = MotMystere.EXPERT;
            this.leNiveau.setText("le Niveau : Expert");
            break;
        default:
            niveau = MotMystere.FACILE;
    }
    this.modelePendu = new MotMystere("dico/"+this.langue, 3, 10, niveau, 10);
    this.motCrypte = new Text(this.modelePendu.getMotCrypte());
    
    this.modeJeu();
    this.chrono.resetTime();
    this.chrono.start();
    }


        
    

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
       if (this.motCrypte == null){
            this.motCrypte = new Text(this.modelePendu.getMotCrypte());
            this.motCrypte.setFont(new Font(20));
            this.motCrypte.setTextAlignment(TextAlignment.CENTER);
            this.panelCentral.setTop(this.motCrypte);
        }
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        this.dessin.setImage(this.lesImages.get(this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants()));
        this.pg.setProgress(this.modelePendu.getNbErreursRestants() / (double)this.modelePendu.getNbErreursMax());
        if (this.modelePendu.gagne()) {
            this.chrono.stop();
            this.popUpMessageGagne().showAndWait();
            
        }
        else if (this.modelePendu.perdu()) {
            this.chrono.stop();
            this.popUpMessagePerdu().showAndWait();

        }
        System.out.println(this.modelePendu.getMotATrouve());


    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        return this.chrono;

    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Règles du jeu");
    alert.setHeaderText("Bienvenue dans le jeu du Pendu !");
    alert.setContentText(
        "Devine le mot mystère en proposant des lettres.\n" +
        "Chaque erreur dessine une partie du pendu.\n" +
        "Tu gagnes si tu trouves le mot avant la fin du dessin.\n" +
        "Bonne chance !"
    );
    return alert;
}
    
    
    public Alert popUpMessageGagne() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bravo !", ButtonType.OK);
        alert.setHeaderText("Vous avez gagné !");
        alert.setContentText("Le mot à trouver était : " + this.modelePendu.getMotATrouve() + "\n" +
                             "Vous avez mis " + this.chrono.getTemps() + " secondes");
        alert.setTitle("GG !");

        return alert;
    }
    

    public Alert popUpMessagePerdu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RIP");
        alert.setHeaderText("Vous avez perdu !");
        alert.setContentText("Le mot à trouver était : " + this.modelePendu.getMotATrouve());
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


    public void modeParametres() {
        this.panelCentral.setCenter(this.fenetreParametres());
        this.panelCentral.setRight(null);
        this.panelCentral.setTop(null);
    }


    private HBox fenetreParametres() {
        HBox para = new HBox();
        para.setAlignment(Pos.CENTER);
        para.setSpacing(40);

        Label labelLangue = new Label("Choisir la langue :");
        ComboBox<String> comboLangue = new ComboBox<>();
        comboLangue.getItems().addAll("Français", "English", "Español");

        HBox langueBox = new HBox(10, labelLangue, comboLangue);
        langueBox.setAlignment(Pos.CENTER);

        para.getChildren().addAll(langueBox);

        comboLangue.setOnAction(new ControleurLangue(this, comboLangue));
        
        return para;
    }




    public void changeLangue(String langue) {
    String fichier;
    switch (langue) {
        case "Français":
            fichier = "french";
            break;
        case "English":
            fichier = "english";
            break;
        case "Español":
            fichier = "spanish";
            break;
        default:
            fichier = "french";
    }
    this.langue = fichier;
    String chemin = "dico/" + fichier;
    this.modelePendu = new MotMystere(chemin, 3, 10, MotMystere.FACILE, 10);
    this.lancePartie();
    System.out.println("Langue sélectionnée : " + langue + " (" + chemin + ")");
    System.out.println(this.modelePendu.getMotATrouve());
}

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
    
    
}
