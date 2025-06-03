# Bonjour et bienvenue dans le projet du jeu du Pendu !

pour lancer le jeu, il vous suffit de cloner le dépôt

```bash
git clone https://github.com/louismaillet/Pendu.git 
```

puis de vous rendre dans le dossier `Pendu` et activer les permissions d'exécution du script `lancer.sh` :

```bash
cd Pendu
chmod +x lancer.sh
```
et enfin d'exécuter le script :

```bash
./lancer.sh
```

## Répartition des classes selon le modèle MVC

- **Modèle** : `MotMystere`, `Dictionnaire`, `Chronometre`
- **Vue** : `Pendu`, `Clavier`
- **Contrôleurs** : `ControleurLettres`, `ControleurLancerPartie`, `ControleurParametres`, `ControleurLangue`, `ControleurInfos`, `ControleurChronometre`, `ControleurNiveau`, `RetourAccueil`


### Liste des tâches effectuées

- [x] Mettre en place le projet (projet VSCode + dépôt git)
- [x] Coder la classe `ControleurLettres`
- [x] Coder la classe `ControleurParametres`
- [x] Coder la classe `ControleurLancerPartie`
- [x] Coder la classe `ControleurNiveau`
- [x] Coder la classe `ControleurChronometre`
- [x] Coder la classe `ControleurInfos`
- [x] Coder la classe `Clavier`
- [x] Coder le constructeur de `Clavier`
- [x] Coder la méthode `desactiveTouches()` de `Clavier`
- [x] Coder la classe `Chronometre`
- [x] Coder la classe `Pendu`
- [x] Afficher la page d’accueil
- [x] Afficher la page de jeu
- [x] La barre de progression doit indiquer l’avancement du jeu
- [x] Coder la méthode `init()` de `Pendu`
- [x] Coder la méthode `titre()` de `Pendu`
- [x] Coder la méthode `modeAccueil()` de `Pendu`
- [x] Coder la méthode `modeJeu()` de `Pendu`
- [x] Coder la méthode `titre()` de `Pendu`
- [x] Coder la méthode `leChrono()` de `Pendu`
- [x] Coder la méthode `majAffichage()` de `Pendu`
- [x] Coder la méthode `getChrono()` de `Pendu`
- [x] Coder la méthode `popUpReglesDuJeu()` de `Pendu`
- [x] Coder la méthode `popUpMessageGagne()` de `Pendu`
- [x] Coder la méthode `popUpMessagePerdu()` de `Pendu`
- [x] Coder la méthode `modeParametres()` de `Pendu`
- [x] Rendre tous les boutons fonctionnels
- [x] Rendre le chronomètre fonctionnel
- [x] Ajouter une infobulle sur les boutons "Home", "Parametres" et "Info"
- [x] Griser le bouton "Home" sur la page d’accueil
- [x] Griser le bouton "Parametres" sur la page de jeu
- [x] Refactoriser le code
- [x] Faire en sorte que les lettres déjà proposées par l’utilisateur soient désactivées
- [x] Gérer la fin de partie (gagné ou perdue)
- [ ] Ajouter la possibilité de jouer à un autre jeu : le