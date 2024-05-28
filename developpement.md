# TODO for developpment

Le developpement du jca-sping-framework se fera en plusiers etapes de fonctionalites qui sont les **Sprint#**
Ci-desous vont etre detailler les fonctionalites requis pour chaque sprint

## Sprint 0

**Objectifs**: Créer un servlet qui réceptionnera toutes les requêtes clients et qui les traitera

### Git

- Créer un projet Git
- Cloner localement
- Creer une branche pour le Sprint actuel nommé: sprint[n° sprint]-[etu]
- A la fin du sprint, envoyer un "Merge request" du sprint et supprimer la branche

### Coté Framework

- Créer un servlet FrontController dont la methode processRequest affichera l'url dans
lequel on se trouve

### Coté Test

- Associer le FrontController à l'url pattern "/" dans le web.xml du projet
- Tester n'importe quel url associé au projet web

## Sprint 1

Creer une nouvelle branche "sprint1-ETU"
et envoyer le framework sur git.

### Coté Framework

- Creer AnnotationController
- Annoter mes controleurs avec AnnotationController
- Mettre mes controleurs dans le meme package
- FrontController :
  - Prendre le nom du package où se trouvent les controleurs
  - Tester si j'ai déjà scanner mes controleurs
    - Si oui, afficher la liste des noms de mes controleurs
    - Sinon scanner, puis afficher la liste des noms de mes controleurs
- Creer un ReadMe file pour décrire précisément les configs à faire pour utiliser mon framework (envoyer le ReadMe file avec mon framework sur Git)

### Coté Test

- Web.xml
  - declarer  le nom du package (misy ny controller rehetra) (using init-param)
  - declarer mon frontServlet

## Sprint 2

**Objectif** : Récupérer la classe et la méthode associées à une URL donnée

### Coté Framework

- Créer une annotation GET pour annoter les méthodes dans les contrôleurs
- Créer la classe Mapping qui aura pour attributs :
  - String className
  - String methodName
- Dans FrontController :
  - Enlever l'attribut boolean
  - Créer un HashMap (String url, Mapping)
  - init :
    - Faire les scans pour avoir les contrôleurs
    - Pour chaque contrôleur, prendre toutes les méthodes et voir s'il y a l'annotation GET
    - S'il y en a, créer un nouveau Mapping : (controller.name, method.name)
    - HashMap.associer(annotation.value, Mapping)
- ProcessRequest
  - Prendre le Mapping associé au chemin URL de la requête
  - Si on trouve le Mapping associé, afficher le chemin URL et le Mapping
  - Sinon, afficher qu'il n'y a pas de méthode associée à ce chemin

## Sprint 3

**Objectif** : Arriver a executer la methode correspondant a l'url demander

### Coté Framework

- Execution de la methode du mapping
  - Cree une instance de la class du controller
  - Executer la methode avec la reflecion
    - Appeller la methode avec l'instance du controller
    - Recuperer la valeur de retour ( String )
    - Afficher le contenu du String
- Readme.md
  - Indiquer que la variable de retour des methodes controller doivent etre des strings

### Coté Test

- Changer le type de retour des methodes en String a afficher
