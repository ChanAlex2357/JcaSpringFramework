package jca.springframework.scanner;

import java.io.File;
import jca.springframework.annotations.Controller;
import jca.springframework.scanner.exception.InvalidPackageException;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PackageScanner {
    /**
     * La fonction fincClasses retrouve les classes d'un package specifier en parametre
     * @param package_name le nom du package a scanner
     * @return la liste des classes portant le package
     */
  static public List<Class<?>> findClasses(String package_name)throws InvalidPackageException {
    List<Class<?>> classes = new ArrayList<Class<?>>();
    try {
      /// Recuperer la classLoader du contexte du projet
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      /// Recuperer les resources correspondant du package dans le contexte actuelle
      Enumeration<URL> resources = classLoader.getResources(package_name.replace('.', '/'));
      /// Gerer l'exception dans le cas du 
      if (!resources.hasMoreElements()) {
        throw new InvalidPackageException(package_name);
      }
      /// Tant que la resource possede un element on va le scanner 
      while (resources.hasMoreElements()) {
          /// Recuperer l'element present dans la ressource
          URL resource = resources.nextElement();
          /// Tester si le protocol du ressource est fichier
          if (resource.getProtocol().equals("file")) {
              /// scanner 
              File file = new File(resource.toURI());
              scanDirectoriesClasses(file, package_name, classes);
          }
      }
    } catch (Exception e) {
        throw new InvalidPackageException(package_name);
    }
    return classes;
  }
  /**
   * La fonction findClasseesNames retrouve le nom des classes
   * qui portent le package specifier en parametre
   * Elle retouve d'abord la liste des classes avec findClasses 
   * pour ensuite recuperer le nom de chacune d'entre elles 
   * @param package_name le nom du package a scanner
   * @return la liste des noms des classes
   */
  static public List<String> findClassesNames(String package_name) throws InvalidPackageException {
    /// recuperer la liste des classes
    List<Class<?>> classes = PackageScanner.findClasses(package_name);
    return findClassesNames(classes);
  }
  static public List<String> findClassesNames(List<Class<?>> classes){
    List<String> classes_names =  new ArrayList<String>();
    /// Recuperer le nom de chaque classe
    for (Class<?> class1 : classes) {
        classes_names.add( class1.getName() );
    }
    return classes_names;
  }

  private static void scanDirectoriesClasses(File directory, String packageName , List<Class<?>> classes) throws InvalidPackageException {
      /// Tester si le dossier du package existe
      if (!directory.exists()) {
          return;
      }
      /// Recuperer la liste des fichiers du dossier
      File[] files = directory.listFiles();
      if (files == null) {
          return;
      }
      /// Pour chaque fichier retrouver on recupere la classe si elle n'est pas un sous package
      for (File file : files) {
          if (file.isDirectory()) {
              /// faire le scann pour le sous package
              scanDirectoriesClasses(file,packageName+"." + file.getName(),classes);
          } else if (file.getName().endsWith(".class")) {
              /// recuperer la class 
              String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
              try {
                  Class<?> clazz = Class.forName(className);
                  classes.add(clazz);
              } catch (ClassNotFoundException e) {
                  e.printStackTrace();
              }
          }
      }
  }

  static public List<Class<?>> findAnnotedClasses(String package_name , Class<? extends Annotation> annotation)throws InvalidPackageException {
    List<Class<?>> classes = findClasses(package_name);
    List<Class<?>> annoted = new ArrayList<Class<?>>();
    for (Class<?> class1 : classes) {
      if (class1.isAnnotationPresent(annotation)) {
        annoted.add(class1);
      }
    }
    return annoted;
  }

  static public List<String> findAnnotedClassesNames(String package_name , Class<? extends Annotation> annotation)throws InvalidPackageException {
    return findClassesNames( findAnnotedClasses(package_name, annotation));
  }

  static public List<Class<?>> findControllerCasses(String package_name)throws InvalidPackageException {
    return findAnnotedClasses(package_name, Controller.class);
  }
}
