import java.util.HashSet;
import java.util.Set;

/**
 * ModÃ¨le pour le jeu du pendu.
 */
public class MotMystere {
    // constantes pour gÃ©rer les diffÃ©rents niveaux de jeu
    /** Niveau FACILE : la premiÃ¨re lettre et la derniÃ¨re lettre du mot Ã  trouver sont donnÃ©es ainsi que les Ã©ventuels caractÃ¨res non alphabÃ©tiques (traits d'union par exemple)*/
    public final static int FACILE = 0;
    /** Niveau MOYEN : la premiÃ¨re lettre du mot Ã  trouver est donnÃ©e ainsi que les traits d'union si le mot Ã  trouver en comporte */
    public final static int MOYEN = 1;
    /** Niveau DIFFICILE : seuls les traits d'union si le mot Ã  trouver en comporte */
    public final static int DIFFICILE = 2;
    /** Niveau EXPERT : rien n'est donnÃ©, ni lettre ni trait d'union */
    public final static int EXPERT = 3;
   

    /**
     * le mot Ã  trouver
     */
    private String motATrouver;
    /**
     * le niveau de jeu
     */
    private int niveau;
    /**
     * chaine contenant les lettres dÃ©jÃ  trouvÃ©es et des * Ã  la place des lettres non encore trouvÃ©es
     */
    private String motCrypte;
    /**
     * chaine contenant l'ensemble des lettres dÃ©jÃ  essayÃ©es
     */
    private Set<String> lettresEssayees;
    /**
     * entier inquant le nombre de lettres restant Ã  trouver
     */
    private int nbLettresRestantes;
    /**
     * le nombre d'essais dÃ©jÃ  effectuÃ©s
     */
    private int nbEssais;
    /**
     * le nombre d'erreurs encore possibles
     */
    private int nbErreursRestantes;
    /**
     * le nombre total de tentatives autorisÃ©es
     */
    private int nbEerreursMax;
    /**
     * dictionnaire dans lequel on choisit les mots
     */
    private Dictionnaire dict;


    /**
     * constructeur dans lequel on impose le mot Ã  trouver
     * @param motATrouve mot Ã  trouver
     * @param niveau niveau du jeu
     * @param nbErreursMax le nombre total d'essais autorisÃ©s
     */
    public MotMystere(String motATrouver, int niveau, int nbErreursMax) {
        super();
        this.initMotMystere(motATrouver, niveau, nbErreursMax);
    }

    /**
     * Constructeur dans lequel on va initialiser un dictionnaire pour choisir les mots Ã  trouver
     * @param nomFichier est le chemin vers le dictionnaire utilisÃ© qui est un fichier texte
     *  contenant une liste de mots (un mot par ligne)
     * @param longMin longueur minimale des mots retenus dans le dictionnaire
     * @param longMax longueur maximale des mots retenus dans le dictionnaire
     * @param niveau niveau initial de jeu
     * @param nbErreursMax le nombre total d'essais autorisÃ©s
     */
    public MotMystere(String nomFichier, int longMin, int longMax, int niveau, int nbErreursMax) {
        super();
        this.dict = new Dictionnaire(nomFichier,longMin,longMax);
        String motATrouver = dict.choisirMot();
        this.initMotMystere(motATrouver, niveau, nbErreursMax);
    }

    /**
     * initialisation du jeu
     * @param motATrouver le mot Ã  trouver
     * @param niveau le niveau de jeu
     * @param nbErreursMax  le nombre total d'essais autorisÃ©s
     */
    private void initMotMystere(String motATrouver, int niveau, int nbErreursMax){
        this.niveau =niveau;
        this.nbEssais=0;
        this.motATrouver = Dictionnaire.sansAccents(motATrouver).toUpperCase();
        this.motCrypte = "";
        this.lettresEssayees = new HashSet<>();

        nbLettresRestantes=0;
        
        if (niveau == MotMystere.EXPERT || niveau == MotMystere.DIFFICILE){
            motCrypte = "*"; // premiere lettre cachÃ©e
            this.nbLettresRestantes+=1;
        }
        else{
            motCrypte += this.motATrouver.charAt(0); // premiere lettre rÃ©vÃ©lÃ©e
        }
        
        for (int i=1; i<motATrouver.length()-1; i++){
            char lettre = this.motATrouver.charAt(i);
            if (this.niveau == MotMystere.EXPERT || Character.isAlphabetic(lettre)){
                motCrypte += "*"; // lettre cachÃ©e
                this.nbLettresRestantes += 1;
            }   
            else{
                motCrypte += lettre; // lettre rÃ©vÃ©lÃ©e si c'est un trait d'union ET qu'on n'est pas en mode Expert
            }
        }
        
        if (niveau != MotMystere.FACILE){ // derniÃ¨re lettre rÃ©vÃ©lÃ©e
            motCrypte += "*";
            this.nbLettresRestantes += 1;
        }
        else{
            motCrypte += this.motATrouver.charAt(motATrouver.length()-1);
            // derniÃ¨re lettre cachÃ©e
        }
        this.nbEerreursMax = nbErreursMax;
         this.nbErreursRestantes = nbErreursMax;
    }

    /**
     * @return le mot Ã  trouver
     */
    public String getMotATrouve() {
        return this.motATrouver;
    }

    /**
     * @return le niveau de jeu
     */
    public int getNiveau(){
        return this.niveau;
    }

    /** rÃ©initialise le jeu avec un nouveau Ã  trouver
     * @param motATrouver le nouveau mot Ã  trouver
     */
    public void setMotATrouver(String motATrouver) {
        this.initMotMystere(motATrouver, this.niveau, this.nbEerreursMax);
    }

    /**
     * RÃ©initialise le jeu avec un nouveau mot Ã  trouver choisi au hasard dans le dictionnaire
     */
    public void setMotATrouver() {
        this.initMotMystere(this.dict.choisirMot(), this.niveau, this.nbEerreursMax);
    }

    /**
     * change le niveau de jeu (n'a pas d'effet en cours de partie)
     * @param niveau le nouveau niveu de jeu
     */
    public void setNiveau(int niveau){
        this.niveau = niveau;
    }

    /**
     * @return le mot avec les lettres trouvÃ©es affichÃ©es et des Ã©toiles pour les lettres non trouvÃ©es
     */
    public String getMotCrypte() {
        return this.motCrypte;
    }

    /**
     * @return les lettres dÃ©jÃ  essayÃ©es
     */
    public Set<String> getLettresEssayees() {
        return this.lettresEssayees;
    }

    /**
     * @return le nombre de lettres restant Ã  trouver
     */
    public int getNbLettresRestantes() {
        return this.nbLettresRestantes;
    }

    /**
     * @return le nombre d'essais dÃ©jÃ  effectuÃ©s
     */
    public int getNbEssais(){
        return this.nbEssais;
    }

    /**
     * @return le nombre total de tentatives autorisÃ©es
     */
    public int getNbErreursMax(){
        return this.nbEerreursMax;
    }

    /**
     * @return le nombre d'erreurs encore autorisÃ©es
     */
    public int getNbErreursRestants(){
        return this.nbErreursRestantes;
    }

    /**
     * @return un boolÃ©en indiquant si le joueur a perdu
     */
    public boolean perdu(){
        return this.nbErreursRestantes == 0;
    }

    /**
     * @return un boolÃ©en indiquant si le joueur a gangÃ©
     */
    public boolean gagne(){
        return this.nbLettresRestantes == 0;
    }

    /**
     * permet au joueur d'essayer une lettre
     * @param lettre la lettre essayÃ©e par le joueur
     * @return le nombre de fois oÃ¹ la lettre apparait dans le mot Ã  trouver
     */
    public int essaiLettre(char lettre){
        int nbNouvelles = 0;
        char[] aux = this.motCrypte.toCharArray();
        for (int i=0; i<this.motATrouver.length(); i++){
            if (this.motATrouver.charAt(i) == lettre && this.motCrypte.charAt(i) == '*'){
                nbNouvelles += 1;
                aux[i] = lettre;
            }
        }
        this.motCrypte = String.valueOf(aux);
        this.nbLettresRestantes -= nbNouvelles;
        this.lettresEssayees.add(lettre+"");
        // Le nombre d'essais augmente de 1
        this.nbEssais += 1;
        // Si aucune lettre n'a Ã©tÃ© trouvÃ©e, le nombre d'erreurs restante diminue de 1
        if (nbNouvelles == 0){
            this.nbErreursRestantes-=1;
        }
        return nbNouvelles;
    }

    /**
     * @return une chaine de caractÃ¨re donnant l'Ã©tat du jeu
     */
    public String toString(){
        return "Mot a trouve: "+this.motATrouver+" Lettres trouvees: "+
               this.motCrypte+" nombre de lettres restantes "+this.nbLettresRestantes+
               " nombre d'essais restents: "+this.nbErreursRestantes;
    }

}
