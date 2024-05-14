# JcaSpringFramework

Ceci est framework inspirer de spring pour implementer un systeme MVC dans un projet web

## Prerequis

- le fichier jar du framework **jca-spring-framework.jar** doit etre dans inclue dans la lib du projet ou considerer dans le classpath
- un servlet doit etre declarer dans web.xml qui va traiter toutes les requetes entrantes :
  - servlet-class  : **jca.springframework.controller.FrontController**
  - url-pattern : " / "
- Declarer dans le web.xml init-param le nom du package de controller en tant que **package-name**
