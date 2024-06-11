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

**O B J E C T I F** : Récupérer la classe et la méthode associées à une URL donnée

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

**O B J E C T I F** : Arriver a executer la methode correspondant a l'url demander

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

## Sprint 4

**O B J E C T I F**:
Envoyer des données du controller vers view

### Côté Framework

- créer une classe ModelView qui aura pour attributs:
- String url[url de destination après l'exécution de la méthode],
  - HashMap<String : nom de la variable, Object: sa valeur> data [donnée à envoyer vers cette view],
    - créer une fonction "AddObject" qui a comme type de retour void pour pouvoir mettre les données dans HashMap
- Dans FrontController,
 dans ProcessRequest, récupérer les données issues de la méthode annotée Get
  - si les data sont de type string, retourner la valeur directement
  - si les données sont de type ModelView, récupérer le url et dispatcher les données vers cet url
  - boucle de data: y faire request.setAttribute
    - si autre, retourner "non reconnu"

### Test

Les méthodes des controlleurs qui seront annotées ont pour type de retour "String" ou "ModelView"

## Sprint 5

**O B J E C T I F** : Gestion des exceptions

### Côté Framework

- Erreur pour un url demander qui n'existe pas
- Erreur pour un url gerer par plusieurs controlleurs
- Erreur pour package inexistant ou ne contient aucun controlleur
- Erreur pour type de retour non valide

### Test

- Creation de methodes avec un meme url
- web.xml
  - package inexistant
  - package sans controlleur
- Methode avec un type de retour invalid

## Sprint 6

**O B J E C T I F** : envoyer des donner depuis view vers controller

### FRAMEWORK

- Creation d'annotation Request Param
  - Rerpresente le nom du parametre a recuperer
- Scanner le parametre des methodes
- Recuperer le nombre de parametre et leurs nom lors du scan
- Recuperer a partir de l'annotation
- Front Controller
  - Recuperer les parametres dans le request grace au noms
  - Executer la methode du controller avec les parameters envoyees
